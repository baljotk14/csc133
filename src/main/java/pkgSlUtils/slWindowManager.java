package pkgSlUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class slWindowManager {

    private long glfw_win = 0;
    private static slWindowManager my_window = null;
    private int[] windowSize = new int[2];

    private slWindowManager() {}

    public static slWindowManager get() {
        if (my_window == null) {
            my_window = new slWindowManager();
        }
        return my_window;
    }

    public long initGLFWWindow(int width, int height, String label) {
        if (glfw_win == 0) {
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }

            glfw_win = glfwCreateWindow(width, height, label, NULL, NULL);
            if (glfw_win == NULL) {
                throw new RuntimeException("Failed to create the GLFW window");
            }

            glfwMakeContextCurrent(glfw_win);
            windowSize[0] = width;
            windowSize[1] = height;
        }
        return glfw_win;
    }

    public int[] getWindowSize() {
        return windowSize;
    }

    public long getWindowID() {
        return glfw_win;
    }

    public void updateContextToThis() {
        if (glfw_win != 0) {
            glfwMakeContextCurrent(glfw_win);
        }
    }

    public void swapBuffers() {
        if (glfw_win != 0) {
            glfwSwapBuffers(glfw_win);
        }
    }

    public boolean isGLFWWindowClosed() {
        return glfwWindowShouldClose(glfw_win);
    }

    public void destroyGlfwWindow() {
        if (glfw_win != 0) {
            glfwDestroyWindow(glfw_win);
            glfw_win = 0;
        }
    }

    public int[] getCurrentWindowSize() {
        return new int[]{windowSize[0], windowSize[1]};
    }
}
