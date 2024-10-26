package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public abstract class slRenderer {

    protected Random random = new Random();  // Random object for generating colors
    protected slWindowManager windowManager;

    // Abstract methods to be implemented by the subclass
    public abstract void render(int frameDelay, int rows, int cols);
    public abstract void render(float radius);
    public abstract void render();  // Default render method

    // Method to initialize OpenGL
    public void initOpenGL(slWindowManager windowManager) {
        this.windowManager = windowManager;
        windowManager.updateContextToThis();
        GL.createCapabilities();

        // Set the clear color for the window

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Set the viewport to cover the new window
        glViewport(0, 0, windowManager.getWindowSize()[0], windowManager.getWindowSize()[1]);
    }

    // Method to check if the window should close
    protected boolean shouldClose() {
        return windowManager.isGLFWWindowClosed();
    }

    // Method to swap buffers and poll events
    protected void endFrame() {
        windowManager.swapBuffers();
        glfwPollEvents();
    }
}
