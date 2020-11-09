/*
Copyright (C) 2020 102-Games.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package view;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import controller.Camera;
import model.Star;
import settings.Settings;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends GLCanvas implements GLEventListener {
    // FPS
    private int fps = 60;
    // Animator
    private FPSAnimator animator;
    // Stars flying across camera
    private List<Star> stars = new ArrayList<>();
    private Camera camera;


    public Canvas(int width, int height, int fps) {
        super(createGLCapabilities());
        setSize(width, height);
        addGLEventListener(this);

        this.fps = fps;
    }


    @Override
    public void init(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Global settings.
        // 3D
        gl.glEnable(gl.GL_DEPTH_TEST);                                           // Enable z- (depth) buffer for hidden surface removal.
        gl.glDepthFunc(gl.GL_LEQUAL);
        gl.glClearDepthf(1.0f);                                                  // Depth Buffer Setup
        gl.glShadeModel(gl.GL_SMOOTH);                                           // Enable smooth shading.
        gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);              // We want a nice perspective.
        gl.glClearColor(0f, 0f, 0f, 1f);                   // Define "clear" color.
        gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE);

        // Load textures
        Texture[] textures = null;
        try {
            textures = loadGLTextures(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create camera object
        camera = new Camera(this);

        // Create initial lines
        for (int i = 0; i < Settings.getStarsCount(); i++) {
            stars.add(new Star(textures[0]));
        }

        animator = new FPSAnimator(this, fps, true);
        animator.start();
    }

    @Override
    public void display(final GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Set camera in default position
        camera.setCamera(drawable);
        // Draw GL scene
        drawGLScene(drawable);
    }

    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(x, y, width, height);
    }

    @Override
    public void dispose(final GLAutoDrawable drawable) {

    }


    private static GLCapabilities createGLCapabilities() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        return capabilities;
    }

    private Texture[] loadGLTextures(final GLAutoDrawable drawable) throws IOException {
        GL2 gl = drawable.getGL().getGL2();
        Texture[] textures = new Texture[1];

        textures[0] = loadTexture("src/main/resources/star.bmp");

        return textures;
    }

    public static Texture loadTexture(final String file) throws GLException, IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new File(file)), "png", os);
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        return TextureIO.newTexture(fis, true, TextureIO.PNG);
    }

    private void drawGLScene(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        for (int i = 0; i < stars.size(); i++) {
            Star s = stars.get(i);
            s.update(drawable);
            s.draw(drawable);
        }
    }
}
