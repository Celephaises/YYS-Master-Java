package com.model;


import com.core.common.BaseModel;
import com.core.util.MouseUtil;
import com.core.util.TimeAndRandomUtil;
import org.opencv.core.CvException;

/**
 * 阴阳师御魂模块
 *
 * @author Celphis
 */
@SuppressWarnings("SpellCheckingInspection")
public class YuHun extends BaseModel {
    private static final String IMG_PATH = "yuhun/";
    public String[] imgs = new String[]{"jieshou1", "jieshou2", "jiesuan0", "jiesuan1", "jiesuan2", "jiesuan3", "tiaozhan", "jujue"};

    @Override
    protected void model() {
        try {
            yuHun();
        } catch (InterruptedException e) {
            controller.log("线程异常");
            e.printStackTrace();
        } catch (CvException e) {
            controller.log("图片读取错误");
            e.printStackTrace();
        } catch (RuntimeException e) {
            controller.log("未知错误");
            e.printStackTrace();
        }
    }

    private void yuHun() throws InterruptedException {
        for (String img : imgs) {
            if (MouseUtil.click(IMG_PATH + img)) {
                Thread.sleep(TimeAndRandomUtil.random(100, 100));
            }
        }
    }
}
