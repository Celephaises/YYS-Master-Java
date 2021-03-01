package com.model;


import com.core.common.BaseModel;
import com.core.common.Config;
import com.core.util.MouseUtil;
import com.core.util.MusicUtil;
import com.core.util.PointUtil;
import com.core.util.TimeAndRandomUtil;
import org.opencv.core.CvException;

import java.awt.*;

/**
 * 阴阳师结界突破模块
 *
 * @author Celphis
 */
@SuppressWarnings({"BusyWait", "AlibabaUndefineMagicConstant", "SpellCheckingInspection", "AlibabaLowerCamelCaseVariableNaming"})
public class JieJie extends BaseModel {
    private static final String IMG_PATH = "jjtp/";
    public String[] imgs = new String[]{"jjtpjiesuan0", "jjtpjiesuan1", "jjtpjiesuan2", "jujue", "jjtpshibai", "jjtpjingong", "zhunbei"};
    public String[] jjtp = new String[]{"jjtp", "jjtp1", "jjtp2", "jjtpgua"};
    Point point = null;

    @Override
    protected void model() throws Exception {
        jjtp();
        controller.log("结界突破结束");
        controller.stop(null);
        if (Config.BEEP_SOUND == 1) {
            MusicUtil.playMusic("end.mp3");
        }
    }

    public void jjtp() throws InterruptedException {
        if (MouseUtil.click(IMG_PATH + "jjtpkaishi")) {
            Thread.sleep(TimeAndRandomUtil.random(50, 30));
        }
        //没有突破券时停止
        while (PointUtil.getPoint(IMG_PATH + "jjtpjieshu", 0.95) == null && !Thread.currentThread().isInterrupted()) {
            if ((point = getJJTP()) != null) {
                MouseUtil.click(IMG_PATH + "quxiao");
                MouseUtil.click(point);
                Thread.sleep(TimeAndRandomUtil.random(100, 50));
            } else if (PointUtil.getPoint(IMG_PATH + "jjtpjiangli") == null && (point = getJJTP()) == null) {
                for (String img : imgs) {
                    MouseUtil.click(IMG_PATH + img);
                }
                Thread.sleep(TimeAndRandomUtil.random(150, 20));
                if (PointUtil.getPoint(IMG_PATH + "jjtpjiangli") == null && (point = getJJTP()) == null) {
                    //当前页面没有打得过的，直接刷新
                    if (MouseUtil.click(IMG_PATH + "jjtpshuaxin")) {
                        Thread.sleep(TimeAndRandomUtil.random(80, 20));
                    }
                    MouseUtil.click(IMG_PATH + "shuaxinqueren");
                }
            }
            for (String img : imgs) {
                MouseUtil.click(IMG_PATH + img);
            }
        }
    }

    private Point getJJTP() throws InterruptedException {
        for (String img : jjtp) {
            Point point = PointUtil.getPoint(IMG_PATH + img, 0.92);
            if (point != null) {
                return point;
            }
        }
        return null;
    }
}
