package com.model;


import com.core.common.BaseModel;
import com.core.common.Config;
import com.core.util.MouseUtil;
import com.core.util.MusicUtil;
import com.core.util.TimeAndRandomUtil;

/**
 * 阴阳师御魂模块
 *
 * @author Celphis
 */
@SuppressWarnings("SpellCheckingInspection")
public class YuHun extends BaseModel {
    private static final String IMG_PATH = "yuhun/";
    public String[] imgs = new String[]{"jieshou", "jiesuan0", "jiesuan1", "jiesuan2", "jiesuan3", "jiesuan4", "shibai", "tiaozhan", "tiaozhan1", "jujue", "zhunbei"};
    private static int count;

    public YuHun() {
        count = 0;
    }

    @Override
    protected void model() throws Exception {
        if (count > 5) {
            controller.log("等待超时");
            controller.stop(null);
            if (Config.BEEP_SOUND == 1) {
                MusicUtil.playMusic("end.mp3");
            }
        }
        yuHun();
    }

    private void yuHun() throws InterruptedException {
        for (String img : imgs) {
            if (MouseUtil.click(IMG_PATH + img) && "tiaozhan".equals(img)) {
                if (count >= 2) {
                    Thread.sleep(TimeAndRandomUtil.random(500, 200));
                }
                count++;
            } else if (MouseUtil.click(IMG_PATH + img)) {
                count = 0;
            }
        }
        if (MouseUtil.click(IMG_PATH + "yaoqing")) {
            MouseUtil.click(IMG_PATH + "queding");
        }
    }
}
