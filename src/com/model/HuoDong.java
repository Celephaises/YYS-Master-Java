package com.model;

import com.core.common.BaseModel;
import com.core.util.MouseUtil;
import com.core.util.MusicUtil;
import com.core.util.PointUtil;

/**
 * @author Celphis
 * 活动模块
 */
public class HuoDong extends BaseModel {
    private static final String IMG_PATH = "huodong/";
    public String[] imgs = new String[]{"jiesuan", "jiesuan1", "tiaozhan"};

    @Override
    protected void model() throws Exception {
        if (PointUtil.getPoint(IMG_PATH + "qiehuan", 0.92) != null && PointUtil.getPoint(IMG_PATH + "jiacheng", 0.92) != null) {
            MouseUtil.click(IMG_PATH + "jiacheng");
            controller.log("加成已刷完");
        }
        if (PointUtil.getPoint(IMG_PATH + "tinzhi") != null) {
            controller.log("次数已刷完");
            controller.stop(null);
            MusicUtil.playMusic("end.mp3");
        }
        for (String img : imgs) {
            MouseUtil.click(IMG_PATH + img);
        }
    }
}
