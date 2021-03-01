package com.core.common;

import com.gui.Controller;
import com.gui.Main;
import org.opencv.core.CvException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 基本模块类
 *
 * @author Celphis
 */
@SuppressWarnings({"BusyWait", "ResultOfMethodCallIgnored"})
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
                logError("线程异常", e);
                break;
            } catch (CvException e) {
                logError("图片异常", e);
                break;
            } catch (Exception e) {
                logError("未知异常", e);
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
     * @throws Exception 未处理异常
     */
    protected abstract void model() throws Exception;

    private void logError(String msg, Exception e) {
        controller.log(msg);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        System.out.println(str);
        controller.log(str);
    }
}
