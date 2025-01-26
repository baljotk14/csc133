package pkgSlUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class slWindowManager {

    private long glfw_win = 0;
    private static slWindowManager instance = null;
    private int[] windowSize = new int[2];


    private slWindowManager() {}

    public static slWindowManager get() {
        if (instance == null) {
            instance = new slWindowManager();
        }
        return instance;
    }


    public long initGLFWWindow(int width, int height, String title) {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfw_win = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfw_win == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(glfw_win);  // Set the OpenGL context to the window
        windowSize[0] = width;
        windowSize[1] = height;

        return glfw_win;
    }


    public int[] getWindowSize() {
        return windowSize;
    }


    public long getWindowID() {
        return glfw_win;
    }


    public boolean isGLFWWindowClosed() {
        return glfwWindowShouldClose(glfw_win);
    }


    public void swapBuffers() {
        glfwSwapBuffers(glfw_win);
    }


    public void updateContextToThis() {
        glfwMakeContextCurrent(glfw_win);
    }
}
