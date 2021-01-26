package com.model;


import com.core.common.BaseModel;
import com.core.util.MouseUtil;
import com.core.util.PointUtil;
import com.core.util.TimeAndRandomUtil;
import org.opencv.core.CvException;

/**
 * 阴阳师结界突破模块
 *
 * @author Celphis
 */
@SuppressWarnings({"BusyWait", "AlibabaUndefineMagicConstant", "SpellCheckingInspection"})
public class JieJie extends BaseModel {
    private static final String IMG_PATH = "jjtp/";
    public String[] imgs = new String[]{"jjtpjiesuan0", "jjtpjiesuan1", "jjtpjiesuan2", "jujue", "jjtpshibai"};

    @Override
    protected void model() {
        try {
            jjtp();
            controller.log("结界突破结束");
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

    public void jjtp() throws InterruptedException {
        if (MouseUtil.click(IMG_PATH + "jjtpkaishi")) {
            Thread.sleep(TimeAndRandomUtil.random(400, 100));
        }
        while (PointUtil.getPoint(IMG_PATH + "jjtpjieshu", 0.95) == null) {
            if (MouseUtil.click(IMG_PATH + "jjtpjingong")) {
                Thread.sleep(TimeAndRandomUtil.random(15000, 2000));
            } else if (MouseUtil.click(IMG_PATH + "jjtp", 0.95)) {
                Thread.sleep(TimeAndRandomUtil.random(100, 50));
            } else if (MouseUtil.click(IMG_PATH + "jjtp1", 0.95)) {
                Thread.sleep(TimeAndRandomUtil.random(100, 50));
            } else if (PointUtil.getPoint(IMG_PATH + "jjtp", 0.95) == null && (PointUtil.getPoint(IMG_PATH + "jjtp1", 0.95) == null)) {
                if (MouseUtil.click(IMG_PATH + "jjtpshuaxin")) {
                    Thread.sleep(TimeAndRandomUtil.random(80, 20));
                }
                MouseUtil.click(IMG_PATH + "shuaxinqueren");
            }
            for (String img : imgs) {
                if (MouseUtil.click(IMG_PATH + img)) {
                    Thread.sleep(TimeAndRandomUtil.random(50, 50));
                }
            }
        }
    }
}
