package pkgDriver;

import pkgslRenderEngine.SlPolygonRenderer;
//import pkgSlRenderer.slRenderEngine;
import pkgSlRenderer.slRenderer;

import static pkgDriver.slSpot.*;

import pkgSlUtils.slWindowManager;

public class csc133Driver {

    public static void main(String[] my_args) {
        slRenderer my_re = new SlPolygonRenderer();
      //  slRenderEngine my_re = new slRenderEngine();
       slWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(slWindowManager.get());

       final int FRAME_DELAY = 700, NUM_ROWS = 30, NUM_COLS = 30;
        my_re.render(FRAME_DELAY, NUM_ROWS, NUM_COLS);
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)
