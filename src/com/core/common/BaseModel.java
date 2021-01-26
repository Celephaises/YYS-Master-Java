package com.core.common;

import com.gui.Controller;
import com.gui.Main;

/**
 * 基本模块类
 *
 * @author Celphis
 */
public abstract class BaseModel extends Thread {
    protected final Controller controller = Main.getController();

    @Override
    public void run() {
        super.run();
        while (controller.runFlag) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
                model();
            } catch (InterruptedException e) {
                break;
            }
        }
        if (controller.runFlag) {
            controller.stop(null);
        }
    }

    /**
     * 自定义模块核心代码编写处
     *
     * @throws InterruptedException 线程阻塞异常
     */
    protected abstract void model() throws InterruptedException;
}
