package pkgslRenderEngine;

import pkgSlRenderer.slRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

public class SlPolygonRenderer extends slRenderer {
    // Default properties
    private float radius = 0.05f;
    private int sides = 3;
    private int polygonsToRender = 10;

    //Setters for polygon properties
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setSides(int sides) {
        this.sides = Math.max(3, sides);  // Ensure minimum of 3 sides
    }

    public void setPolygonsToRender(int polygons) {
        this.polygonsToRender = polygons;
    }

    // Method to generate a single polygon at a given center with specified properties
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

    // Helper method to compute radius based on rows and columns
    private float computeRadius(int rows, int cols) {
        float stepX = 2.0f / cols;
        float stepY = 2.0f / rows;
        return Math.min(stepX, stepY) / 2 * 0.9f;  // 0.9 to add padding between polygons
    }

    // Generate an array of polygons based on rows and columns
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
                // Color is set once per shape, so we don't set it here
                generatePolygon(x, y, sides, adjustedRadius);
            }
        }
    }

    // Overloaded method to generate array with specified radius
    public void generatePolygonArray(float radius, int sides) {
        this.radius = radius;
        int possibleRows = (int) (2 / (2 * radius));
        int possibleCols = possibleRows;  // Square grid
        generatePolygonArray(possibleRows, possibleCols, sides);
    }

    // Implement the abstract render methods
    @Override
    public void render(int frameDelay, int rows, int cols) {
        int currentSides = 3;  // Start with triangles
        int maxSides = 20;     // Maximum number of sides
        while (!windowManager.isGLFWWindowClosed()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Generate a consistent color for the current number of sides
            Random colorRandom = new Random(currentSides); // Seed is the number of sides
            float r = colorRandom.nextFloat();
            float g = colorRandom.nextFloat();
            float b = colorRandom.nextFloat();
            glColor3f(r, g, b);

            // Render the array of polygons with the current number of sides
            generatePolygonArray(rows, cols, currentSides);

            windowManager.swapBuffers();
            glfwPollEvents();

            // Frame delay
            try {
                Thread.sleep(frameDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (windowManager.isGLFWWindowClosed()) {
                break;
            }

            // Increment the number of sides, loop back to 3 after reaching maxSides
            currentSides++;
            if (currentSides > maxSides) {
                currentSides = 3;
            }
        }
    }


    public void render(float radius) {
        int currentSides = 3;  // Start with triangles
        int maxSides = 20;     // Maximum number of sides
        while (!windowManager.isGLFWWindowClosed()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Generate a consistent color for the current number of sides
            Random colorRandom = new Random(currentSides); // Seed is the number of sides
            float r = colorRandom.nextFloat();
            float g = colorRandom.nextFloat();
            float b = colorRandom.nextFloat();
            glColor3f(r, g, b);

            // Render the array of polygons with the current number of sides
            generatePolygonArray(radius, currentSides);

            windowManager.swapBuffers();
            glfwPollEvents();

            // Frame delay
            try {
                Thread.sleep(500);  // Default frame delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (windowManager.isGLFWWindowClosed()) {
                break;
            }

            // Increment the number of sides, loop back to 3 after reaching maxSides
            currentSides++;
            if (currentSides > maxSides) {
                currentSides = 3;
            }
        }
    }


    public void render() {
        render(500, 30, 30);  // Default frame delay and grid size
    }
}
