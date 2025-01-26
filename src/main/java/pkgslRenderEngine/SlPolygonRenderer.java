package pkgslRenderEngine;

import pkgSlRenderer.slRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

public class SlPolygonRenderer extends slRenderer {

//   private float radius = 0.05f;


    public void generatePolygon(float cx, float cy, int sides, float radius) {
        glBegin(GL_POLYGON);
        for (int i = 0; i < sides; i++) {
            double angle = 2 * Math.PI * i / sides;
            float x = cx + radius * (float) Math.cos(angle);
            float y = cy + radius * (float) Math.sin(angle);
            glVertex2f(x, y);
        }
        glEnd();
    }


    private float computeRadius(int rows, int cols) {
        float stepX = 2.0f / cols;
        float stepY = 2.0f / rows;
        return Math.min(stepX, stepY) / 2 * 0.99f;
    }


    public void generatePolygonArray(int rows, int cols, int sides) {
        float adjustedRadius = computeRadius(rows, cols);

        float startX = -1 + adjustedRadius;
        float startY = -1 + adjustedRadius;
        float stepX = (2 - 2 * adjustedRadius) / (cols - 1);
        float stepY = (2 - 2 * adjustedRadius) / (rows - 1);

        for (int row = 0; row < rows; row++) {
            float y = startY + row * stepY;
            for (int col = 0; col < cols; col++) {
                float x = startX + col * stepX;

                generatePolygon(x, y, sides, adjustedRadius);
            }
        }
    }


    //Implement the abstract render methods

    public void render(int frameDelay, int rows, int cols) {
        int currentSides = 3;  // Start with triangles
        int maxSides = 20;     // Maximum number of sides
        while (!windowManager.isGLFWWindowClosed()) {
            glClear(GL_COLOR_BUFFER_BIT);


            Random colorRandom = new Random(currentSides);
            float r = colorRandom.nextFloat();
            float g = colorRandom.nextFloat();
            float b = colorRandom.nextFloat();
            glColor3f(r, g, b);


            generatePolygonArray(rows, cols, currentSides);

            windowManager.swapBuffers();
            glfwPollEvents();


            try {
                Thread.sleep(frameDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (windowManager.isGLFWWindowClosed()) {
                break;
            }


            currentSides++;
            if (currentSides > maxSides) {
                currentSides = 3;
            }
        }
    }


}
