package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

public class slRenderEngine {

    private final int NUM_3D_COORDS = 3;
    private final int MAX_CIRCLES = 100;
    private final int TRIANGLES_PER_CIRCLE = 40;
    private final int NUM_RGBA = 4;
    private final float C_RADIUS = 0.05f;
    private final int UPDATE_INTERVAL = 80;

    private final float[][] rand_colors = new float[MAX_CIRCLES][NUM_RGBA];
    private final float[][] rand_coords = new float[MAX_CIRCLES][NUM_3D_COORDS];

    private slWindowManager my_wm;
    private Random my_rand = new Random();
    private long lastUpdateTime = System.nanoTime();

    public slRenderEngine() {

    }

    public void initOpenGL(slWindowManager windowManager) {
        this.my_wm = windowManager;
        my_wm.updateContextToThis();

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glViewport(0, 0, windowManager.getWindowSize()[0], windowManager.getWindowSize()[1]);
        updateRandVertices();
    }

    private void updateRandVertices() {
        final int CR = 0, CG = 1, CB = 2, CA = 3;
        final int CX = 0, CY = 1, CZ = 2;

        for (int cur_circle = 0; cur_circle < MAX_CIRCLES; cur_circle++) {
            rand_colors[cur_circle][CR] = my_rand.nextFloat();
            rand_colors[cur_circle][CG] = my_rand.nextFloat();
            rand_colors[cur_circle][CB] = my_rand.nextFloat();
            rand_colors[cur_circle][CA] = 1.0f;

            rand_coords[cur_circle][CX] = (my_rand.nextFloat() * 2 - 1) * (1 - C_RADIUS);
            rand_coords[cur_circle][CY] = (my_rand.nextFloat() * 2 - 1) * (1 - C_RADIUS);
            rand_coords[cur_circle][CZ] = 0.0f;
        }
    }

    private void generateCircleSegmentVertices(float[] coord, float radius, float[] color, float theta, float deltaTheta, int triangles) {
        float CX = coord[0];
        float CY = coord[1];

        glColor4f(color[0], color[1], color[2], color[3]);

        glBegin(GL_TRIANGLES);
        float VX0 = CX + radius;
        float VY0 = CY;

        for (int i = 0; i <= triangles; i++) {
            theta += deltaTheta;

            float VX1 = CX + radius * (float) Math.cos(theta);
            float VY1 = CY + radius * (float) Math.sin(theta);

            glVertex3f(CX, CY, 0.0f);  // Center vertex
            glVertex3f(VX0, VY0, 0.0f);  // Previous vertex
            glVertex3f(VX1, VY1, 0.0f);  // Current vertex

            VX0 = VX1;
            VY0 = VY1;
        }

        glEnd();
    }

    public void render() {
        long win_id = my_wm.getWindowID();

        while (!my_wm.isGLFWWindowClosed()) {

            long currentTime = System.nanoTime();


            if ((currentTime - lastUpdateTime) / 1_000_000 >= UPDATE_INTERVAL) {
                updateRandVertices();
                lastUpdateTime = currentTime;
            }

            glClear(GL_COLOR_BUFFER_BIT);


            for (int i = 0; i < MAX_CIRCLES; i++) {
                generateCircleSegmentVertices(rand_coords[i], C_RADIUS, rand_colors[i], 0.0f, (float) (2 * Math.PI / TRIANGLES_PER_CIRCLE), TRIANGLES_PER_CIRCLE);
            }

            my_wm.swapBuffers();


            glfwPollEvents();
        }
    }
}
