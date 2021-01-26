package com.model;


import com.core.common.BaseModel;
import com.core.util.MouseUtil;
import com.core.util.PointUtil;
import com.core.util.TimeAndRandomUtil;
import org.opencv.core.CvException;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 阴阳师探索模块
 *
 * @author Celphis
 */
@SuppressWarnings({"SpellCheckingInspection", "BusyWait", "AlibabaUndefineMagicConstant"})
public class TanSuo extends BaseModel {
    private static int times = 0;
    private static int tanSuoTimes;
    private static final String IMG_PATH = "tansuo/";
    private static final String JJTP_PATH = "jjtp/";
    private boolean gouLiangFalg = (int) controller.tanSuoGouLiang.getSelectedToggle().getUserData() == 0;
    private final boolean jieJieFalg = (int) controller.tanSuoJieJie.getSelectedToggle().getUserData() == 0;
    private final boolean modelFalg = (int) controller.tanSuoModel.getSelectedToggle().getUserData() == 0;
    String[] images = new String[]{"28", "tansuo", "jujue", "jiesuan0", "jiesuan1", "jiesuan2"};

    public TanSuo() {
        try {
            String time = controller.tanSuoTimes.getText();
            if ("0".equals(time) || "".equals(time)) {
                tanSuoTimes = 0;
            } else {
                tanSuoTimes = Integer.parseInt(time);
            }
        } catch (RuntimeException e) {
            controller.log("请输入数字");
            controller.stop(null);
        }
    }

    @Override
    protected void model() throws InterruptedException {
        try {
            while (MouseUtil.click(IMG_PATH + "jiangli1")) {
                Thread.sleep(TimeAndRandomUtil.random(100, 50));
                MouseUtil.click(PointUtil.floatPoint(new Point(1100, 400)));
            }
            if (PointUtil.findOriginPoints(IMG_PATH + "tu").size() >= 1) {
                while (MouseUtil.click(IMG_PATH + "jiangli2")) {
                    Thread.sleep(TimeAndRandomUtil.random(100, 50));
                    MouseUtil.click(PointUtil.floatPoint(new Point(1100, 400)));
                }
                if (MouseUtil.click(IMG_PATH + "boss")) {
                    Thread.sleep(TimeAndRandomUtil.random(300, 50));
                    controller.log("进入战斗");
                    times++;
                } else if (MouseUtil.click(IMG_PATH + "xiaoguai")) {
                    Thread.sleep(TimeAndRandomUtil.random(300, 50));
                    controller.log("进入战斗");
                } else {
                    MouseUtil.click(PointUtil.floatPoint(new Point(850, 520)));
                    Thread.sleep(TimeAndRandomUtil.random(50, 30));
                }
            }
            if (jieJieFalg) {
                jjtp();
            }
            if (gouLiangFalg) {
                checkGouLiang();
            } else {
                MouseUtil.click(IMG_PATH + "zhunbei");
                checkTimes();
                Thread.sleep(TimeAndRandomUtil.random(2500, 50));
            }
            for (String img : images) {
                if (MouseUtil.click(IMG_PATH + img)) {
                    Thread.sleep(TimeAndRandomUtil.random(50, 50));
                }
            }
        } catch (CvException e) {
            controller.log("图片读取错误");
            e.printStackTrace();
        } catch (NullPointerException e) {
            controller.log("空指针错误");
            e.printStackTrace();
        } catch (RuntimeException e) {
            controller.log("未知错误");
            e.printStackTrace();
        }
    }

    private void checkGouLiang() throws InterruptedException {
        if (PointUtil.findOriginPoints(IMG_PATH + "man").size() <= 1) {
            MouseUtil.click(IMG_PATH + "zhunbei");
            checkTimes();
            Thread.sleep(TimeAndRandomUtil.random(2500, 50));
            return;
        }
        if (MouseUtil.doubleClick(IMG_PATH + "shenkan")) {
            Thread.sleep(TimeAndRandomUtil.random(600, 100));
        }
        List<Point> mans = PointUtil.findFloatPoints(IMG_PATH + "man", new Point(500, 0), new Point(1200, 500));
        List<Point> gouLiangs;
        if (mans.size() >= 1) {
            if (MouseUtil.click(IMG_PATH + "quanbu")) {
                Thread.sleep(TimeAndRandomUtil.random(300, 50));
                if (MouseUtil.click(IMG_PATH + "ncard")) {
                    Thread.sleep(TimeAndRandomUtil.random(300, 50));
                }
            }
            Point gundongtiao = null;
            gouLiangs = PointUtil.findFloatPoints(IMG_PATH + "gouliang");
            while (!Thread.currentThread().isInterrupted() && gouLiangs.size() <= 1) {
                while (!Thread.currentThread().isInterrupted() && (gundongtiao = PointUtil.getPoint(IMG_PATH + "gundongtiao")) == null) {
                }
                if (Objects.requireNonNull(gundongtiao).x > 1100) {
                    controller.log("当前已无狗粮");
                    gouLiangFalg = false;
                    break;
                }
                MouseUtil.dragTo(gundongtiao, new Point(gundongtiao.x + 20, gundongtiao.y));
                Thread.sleep(TimeAndRandomUtil.random(50, 50));
                gouLiangs = PointUtil.findFloatPoints(IMG_PATH + "gouliang");
            }
            Thread.sleep(TimeAndRandomUtil.random(60, 20));
            mans = getMan();
            while (!Thread.currentThread().isInterrupted() && mans.size() >= 1) {
                gouLiangs = PointUtil.findFloatPoints(IMG_PATH + "gouliang");
                Point gouLiang = gouLiangs.get(new Random().nextInt(gouLiangs.size()));
                gouLiang.x = gouLiang.x + 10;
                gouLiang.y = gouLiang.y + 10;
                mans = getMan();
                Point man = mans.get(0);
                man.y = man.y + 10;
                MouseUtil.dragTo(gouLiang, man);
            }
        }
    }

    private void jjtp() throws InterruptedException {
        if (PointUtil.getPoint(JJTP_PATH + "jjtpman") != null) {
            controller.log("结界突破开始");
            new JieJie().jjtp();
            controller.log("结界突破结束");
        }
    }

    private List<Point> getMan() {
        return PointUtil.findFloatPoints(IMG_PATH + "man", new Point(500, 0), new Point(1200, 500));
    }

    private void checkTimes() {
        if (tanSuoTimes != 0) {
            if (times >= tanSuoTimes) {
                controller.log("已探索" + times + "次");
                times = 0;
                controller.stop(null);
            }
        }
    }
}
