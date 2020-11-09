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

package model;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Star {
    // Center of star coord
    private float x, y, z;
    private float width;
    private float height;
    private Texture texture;
    private float transparency = 1f;
    private Color color;
    private static List<Color> colors = new ArrayList<>();

    static {
        colors.add(new Color(0, 37, 230));    //Blue
        colors.add(new Color(230, 160, 33));    // Orange
        colors.add(new Color(212, 29, 0));     // Red
        colors.add(new Color(230, 0, 0));     // Red
        colors.add(new Color(119, 141, 255));    //Blue
        colors.add(new Color(37, 72, 255));    //Blue
        colors.add(new Color(255, 255, 255));   // White
        colors.add(new Color(255, 242, 209));   //Grey-Yellow
    }


    public Star(Texture texture) {
        this.texture = texture;

        Random rand = new Random();
        float size = (rand.nextInt(100) / 200f) + (rand.nextInt(100) / 200f - 1f);
        this.width = size;
        this.height = size;

        initStarAtRandomPosition();

        int index = Math.abs(rand.nextInt() % colors.size());
        this.color = colors.get(index);
    }

    public Star(float width, float height, Texture texture) {
        this.width = width;
        this.height = height;
        this.texture = texture;

        initStarAtRandomPosition();
    }

    public Star(float x, float y, float z, float width, float height, Texture texture) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.width = width;
        this.height = height;

        this.texture = texture;
    }


    private void initStarAtRandomPosition() {
        int m = 5;
        Random rand = new Random();
        this.x = rand.nextInt((int) Settings.getDistanceFromCenter()) - rand.nextInt((int) Settings.getDistanceFromCenter());
        this.y = rand.nextInt((int) Settings.getDistanceFromCenter()) - rand.nextInt((int) Settings.getDistanceFromCenter());
        this.z = -Settings.getDistanceFromCenter() + rand.nextInt((int) Settings.getDistanceFromCenter());
        this.transparency = 0.05f;
    }

    public void update(GLAutoDrawable drawable) {
        if (z > Settings.getDistanceFromCenter()) {
            initStarAtRandomPosition();
            this.transparency = 0.05f;
        }
        z += 0.1f;

        this.transparency = z / Settings.getDistanceFromCenter();
    }

    public void draw(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        float hw = width / 2f;
        float hh = height / 2f;

        gl.glLoadIdentity();

        texture.enable(gl);
        texture.bind(gl);

        gl.glDisable(gl.GL_DEPTH_TEST);
        gl.glEnable(gl.GL_BLEND);

        gl.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), transparency);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex3f(x - hw, y - hh, z);
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex3f(x - hw, y + hh, z);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(x + hw, y + hh, z);
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex3f(x + hw, y - hh, z);
        gl.glEnd();

        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glDisable(gl.GL_BLEND);

        texture.disable(gl);
    }
}

