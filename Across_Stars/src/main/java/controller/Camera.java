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

package controller;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import settings.Settings;

public class Camera {
    // The GL unit (helper class).
    private GLU glu;
    private GLCanvas glcanvas;

    public Camera(GLCanvas glcanvas) {
        this.glcanvas = glcanvas;
        // Create glu object
        glu = new GLU();
    }

    public void setCamera(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        final int width = glcanvas.getWidth();
        final int height = glcanvas.getHeight();
        // Perspective.
        final float widthHeightRatio = (float) width / (float) height;
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, Settings.getDistanceFromCenter(), 0, 0, 0, 0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
