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

        }
    }

}
