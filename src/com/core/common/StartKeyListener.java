package com.core.common;


import com.gui.Controller;
import com.gui.Main;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.event.KeyEvent;

/**
 * @author Celphis
 */
public class StartKeyListener {
    private static Controller CONTROLLER;
    private static final int KEY_START = 90;

    static {
        CONTROLLER = Main.getController();
    }

    public static void register() {
        JIntellitype.getInstance().registerHotKey(KEY_START, 0, KeyEvent.VK_SPACE);
    }

    public static void unregister() {
        JIntellitype.getInstance().unregisterHotKey(90);
    }

    public static void addListener() {
        JIntellitype.getInstance().addHotKeyListener(key -> {
            if (key == KEY_START) {
                if (!CONTROLLER.runFlag) {
                    CONTROLLER.start(null);
                }
            }
        });
    }

    public static void setController(Controller controller) {
        CONTROLLER = controller;
    }

}
