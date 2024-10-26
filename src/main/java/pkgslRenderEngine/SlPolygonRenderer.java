package pkgslRenderEngine;

import pkgSlRenderer.slRenderer;
import static org.lwjgl.opengl.GL11.*;

public class SlPolygonRenderer extends slRenderer {
    // Default properties
    private float radius = 0.05f;
    private int sides = 3;
    private int polygonsToRender = 10;

    // Setters for polygon properties
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

    // Render randomly placed polygons within the window
    public void renderRandomPolygons() {
        for (int i = 0; i < polygonsToRender; i++) {
            float x = random.nextFloat() * 2 - 1;  // [-1, 1]
            float y = random.nextFloat() * 2 - 1;  // [-1, 1]

            // Adjust positions to ensure the entire polygon fits within the window
            float adjustedRadius = radius;
            if (x + adjustedRadius > 1) x = 1 - adjustedRadius;
            if (x - adjustedRadius < -1) x = -1 + adjustedRadius;
            if (y + adjustedRadius > 1) y = 1 - adjustedRadius;
            if (y - adjustedRadius < -1) y = -1 + adjustedRadius;

            // Color is set in the render loop of slRenderer, so we don't set it here

            generatePolygon(x, y, sides, adjustedRadius);
        }
    }

    // Helper method to compute radius based on rows and columns
    private float computeRadius(int rows, int cols) {
        float stepX = 2.0f / cols;
        float stepY = 2.0f / rows;
        return Math.min(stepX, stepY) / 2 * 0.9f;  // 0.9 to add padding between polygons
    }

    // Generate an array of polygons based on rows and columns
    public void generatePolygonArray(int rows, int cols) {
        setSides(3);  // Default to triangle if sides not set
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

    // Overloaded method to generate array with specified radius
    public void generatePolygonArray(float radius) {
        this.radius = radius;
        int possibleRows = (int) (2 / (2 * radius));
        int possibleCols = possibleRows;  // Square grid
        generatePolygonArray(possibleRows, possibleCols);
    }

    // Overloaded method to generate array with specified rows, cols, and sides
    public void generatePolygonArray(int rows, int cols, int sides) {
        setSides(sides);
        generatePolygonArray(rows, cols);
    }

    // Implement the abstract render methods
    @Override
    public void render(int frameDelay, int rows, int cols) {
        while (!windowManager.isGLFWWindowClosed()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Loop from 3 to 20 sides
            for (int numSides = 3; numSides <= 20; numSides++) {
                setSides(numSides);

                // Randomly compute color on the fly
                glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());

                generatePolygonArray(rows, cols);

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
            }
        }
    }

    @Override
    public void render(float radius) {
        while (!windowManager.isGLFWWindowClosed()) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Randomly compute color on the fly
            glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());

            generatePolygonArray(radius);

            windowManager.swapBuffers();
            glfwPollEvents();

            // Frame delay
            try {
                Thread.sleep(500);  // Default frame delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void glfwPollEvents() {
    }

    @Override
    public void render() {
        render(500, 30, 30);  // Default frame delay and grid size
    }
}
