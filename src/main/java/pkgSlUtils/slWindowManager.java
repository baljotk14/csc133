package pkgSlUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class slWindowManager {

    private static long win_id = 0;


    private static slWindowManager mywm = null;


    private slWindowManager() {}


    public static slWindowManager get() {
        if (mywm == null) {
            mywm = new slWindowManager();
        }
        return mywm;
    }


    public void initGLFWWindow(int width, int height, String label) {
        if (win_id == 0) {
            // Initialize GLFW
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }


            win_id = glfwCreateWindow(width, height, label, NULL, NULL);
            if (win_id == NULL) {
                throw new RuntimeException("Failed to create the GLFW window");
            }


            glfwMakeContextCurrent(win_id);
        }
    }


    public void makeContextCurrent() {
        if (win_id != 0) {
            glfwMakeContextCurrent(win_id);
        }
    }


    public long getWindowID() {
        return win_id;
    }
}
