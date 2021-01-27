package com.core.common;


import com.gui.Controller;
import com.gui.Main;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.event.KeyEvent;

/**
 * 键盘监听类
 *
 * @author Celphis
 */
public class StopKeyListener {
    private static final Controller CONTROLLER = Main.getController();
    private static final int KEY_STOP = 89;

    public static void register() {
        JIntellitype.getInstance().registerHotKey(KEY_STOP, 0, KeyEvent.VK_ESCAPE);
    }

    public static void unregister() {
        JIntellitype.getInstance().unregisterHotKey(89);
    }

    public static void addListener() {
        JIntellitype.getInstance().addHotKeyListener(key -> {
            if (key == KEY_STOP) {
                if (CONTROLLER.runFlag) {
                    CONTROLLER.stop(null);
                }
            }
        });
    }
}
