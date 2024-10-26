package pkgSlUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class slWindowManager {

    private long glfw_win = 0;  // Window ID
    private static slWindowManager instance = null;  // Singleton instance
    private int[] windowSize = new int[2];  // Store window dimensions

    // Private constructor for singleton
    private slWindowManager() {}

    // Get the singleton instance of the window manager
    public static slWindowManager get() {
        if (instance == null) {
            instance = new slWindowManager();
        }
        return instance;
    }

    // Initialize a GLFW window with the specified dimensions and title
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

    // Get the window size as an array
    public int[] getWindowSize() {
        return windowSize;
    }

    // Get the window ID
    public long getWindowID() {
        return glfw_win;
    }

    // Check if the GLFW window should close
    public boolean isGLFWWindowClosed() {
        return glfwWindowShouldClose(glfw_win);
    }

    // Swap the buffers to display the rendered frame
    public void swapBuffers() {
        glfwSwapBuffers(glfw_win);
    }

    // Set the current OpenGL context to this window
    public void updateContextToThis() {
        glfwMakeContextCurrent(glfw_win);
    }
}
