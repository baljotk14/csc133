package pkgSlRenderer;

import java.util.Random;

public class slRendererEngine {

    private final int NUM_RGBA = 4;
    private final int NUM_3D_COORDS = 3;
    private final int TRIANGLES_PER_CIRCLE = 40;
    private final float C_RADIUS = 0.05f;
    private final int MAX_CIRCLES = 100;
    private final int UPDATE_INTERVAL - 0;

    private final float [][] rand_colors;
    private final float [][] rand_coords;

    private slWindowManager my_wm;
    Random my_rand = new Random();

    private void updateRandVertices(){
        final int CR = 0, CG = 1, CB =2, CA = 3;
        final int CX = 0, CY = 1, CZ = 2;

        for (int cur_circle = 0; cur_circle < MAX_CIRCLES; cur_circle++) {
            rand_colors[cur_circle][CR] = my_rand.nextFloat();
            rand_coords[cur_circle][CG] = my_rand.nextFloat();
            rand_coords[cur_circle][CB] = my_rand.nextFloat();
            rand_coords[cur_circle][CA] = 1.0f;

            rand_coords[cur_circle][CX] = (my_rand.nextFloat()*2-1)*(1-C_RADIUS);
            rand_coords[cur_circle][CY] = (my_rand.nextFloat()*2-1)*(1-C_RADIUS);
            rand_coords[cur_circle][CZ] = 0.0f;

        }
    }

    public void render() {
        while (!glfwWindowShouldClose(my_wm.getWindow())) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);


            for (int i = 0; i < MAX_CIRCLES; i++) {
                renderCircle(rand_coords[i], rand_colors[i]);
            }

            glfwSwapBuffers(my_wm.getWindow());
        }
    }

}

    private void renderCircle(float[] coord, float[] color) {
        float CX = coord[0];
        float CY = coord[1];
        float R = C_RADIUS;
        float theta = 0.0f;
        float delTheta = (float) (2 * Math.PI / TRIANGLES_PER_CIRCLE);  // Angle step per triangle


        glColor4f(color[0], color[1], color[2], color[3]);

        glBegin(GL_TRIANGLES);

        float VX0 = CX + R;
        float VY0 = CY;

        for (int i = 0; i <= TRIANGLES_PER_CIRCLE; i++) {

            theta += delTheta;

            float VX1 = CX + R * (float) Math.cos(theta);
            float VY1 = CY + R * (float) Math.sin(theta);

            glVertex3f(CX, CY, 0.0f);  // Center of the circle (Z=0.0)
            glVertex3f(VX0, VY0, 0.0f);  // Previous vertex on circumference (Z=0.0)
            glVertex3f(VX1, VY1, 0.0f);  // Current vertex on circumference (Z=0.0)

            VX0 = VX1;
            VY0 = VY1;
        }

        glEnd();
    }



}
