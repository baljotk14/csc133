package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import static org.lwjgl.opengl.GL11.*;

public abstract class slRenderer {

  //  protected Random random = new Random();
    protected slWindowManager windowManager;


    public abstract void render(int frameDelay, int rows, int cols);
   // public abstract void render(float radius);
    //public abstract void render();


    public void initOpenGL(slWindowManager windowManager) {
        this.windowManager = windowManager;
        windowManager.updateContextToThis();
        GL.createCapabilities();



        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glViewport(0, 0, windowManager.getWindowSize()[0], windowManager.getWindowSize()[1]);
    }


}
