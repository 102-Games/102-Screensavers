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

import com.jogamp.opengl.awt.GLCanvas;
import view.Canvas;

import javax.swing.*;
import java.awt.*;

public class AppStart {

    public static void main(String[] args) {
        int width = 800;
        int height = 550;
        int fps = 60;

        // The canvas
        final GLCanvas glcanvas = new Canvas(width, height, fps);

        // Creating frame
        final JFrame form = new JFrame("Across Stars - Screensaver");
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Adding canvas to frame
        form.getContentPane().add(glcanvas);

        form.setSize(form.getContentPane().getPreferredSize());
        form.setVisible(true);
        (glcanvas).requestFocus();

        form.getContentPane().setFocusable(true);
        form.getContentPane().requestFocusInWindow();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();
        dev.setFullScreenWindow(form);
    }

}
