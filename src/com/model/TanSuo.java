package com.model;


import com.core.common.BaseModel;
import com.core.common.Config;
import com.core.util.MouseUtil;
import com.core.util.MusicUtil;
import com.core.util.PointUtil;
import com.core.util.TimeAndRandomUtil;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 阴阳师探索模块
 *
 * @author Celphis
 */
@SuppressWarnings({"SpellCheckingInspection", "BusyWait", "AlibabaUndefineMagicConstant", "StatementWithEmptyBody"})
public class TanSuo extends BaseModel {
    private static int times = 0;
    private static int tanSuoTimes;
    private static final String IMG_PATH = "tansuo/";
    private static final String JJTP_PATH = "jjtp/";
    private boolean gouLiangFalg = (int) controller.tanSuoGouLiang.getSelectedToggle().getUserData() == 0;
    private final boolean jieJieFalg = (int) controller.tanSuoJieJie.getSelectedToggle().getUserData() == 0;
    private final boolean modelFalg = (int) controller.tanSuoModel.getSelectedToggle().getUserData() == 0;
    String[] images = new String[]{"28", "tansuo", "jujue", "jiesuan0", "jiesuan1", "jiesuan2", "jiesuan3"};

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
    protected void model() throws Exception {
        //获取探索结算奖励
        while (MouseUtil.click(IMG_PATH + "jiangli1")) {
            Thread.sleep(TimeAndRandomUtil.random(80, 20));
            MouseUtil.click(PointUtil.floatPoint(new Point(1100, 400)));
        }
        //检测探索次数
        checkTimes();
        //检测是否处于困28地图中
        if (PointUtil.findOriginPoints(IMG_PATH + "tu").size() >= 1) {
            //获取Boss结算奖励
            while (MouseUtil.click(IMG_PATH + "jiangli2")) {
                Thread.sleep(TimeAndRandomUtil.random(80, 20));
                MouseUtil.click(PointUtil.floatPoint(new Point(1100, 400)));
            }
            if (MouseUtil.click(IMG_PATH + "boss")) {
                Thread.sleep(TimeAndRandomUtil.random(50, 20));
                controller.log("进入战斗");
                times++;
            } else if (MouseUtil.click(IMG_PATH + "xiaoguai")) {
                Thread.sleep(TimeAndRandomUtil.random(50, 20));
                controller.log("进入战斗");
            } else {
                //当前画面内没有怪物游荡，向右移动
                MouseUtil.click(PointUtil.floatPoint(new Point(850, 520)));
            }
        } else {
            //一些无逻辑的重复操作
            for (String img : images) {
                MouseUtil.click(IMG_PATH + img);
            }
        }
        //如果勾选  攻打结界突破
        if (jieJieFalg) {
            jjtp();
        }
        //如果勾选  更换狗粮
        if (gouLiangFalg) {
            checkGouLiang();
        } else {
            MouseUtil.click(IMG_PATH + "zhunbei");
            Thread.sleep(TimeAndRandomUtil.random(2000, 50));
        }
    }

    /**
     * 检测狗粮满级功能
     *
     * @throws InterruptedException 线程等待异常
     */
    private void checkGouLiang() throws InterruptedException, IndexOutOfBoundsException {
        if (PointUtil.findOriginPoints(IMG_PATH + "man").size() <= 1) {
            MouseUtil.click(IMG_PATH + "zhunbei");
            Thread.sleep(TimeAndRandomUtil.random(2000, 50));
            return;
        }
        if (MouseUtil.doubleClick(IMG_PATH + "shenkan")) {
            Thread.sleep(TimeAndRandomUtil.random(600, 100));
        }
        List<Point> mans = PointUtil.findFloatPoints(IMG_PATH + "man", new Point(500, 0), new Point(1200, 500));
        List<Point> gouLiangs;
        if (mans.size() >= 1) {
            if (PointUtil.getPoint(IMG_PATH + "ncard") == null) {
                while (!Thread.currentThread().isInterrupted() && !MouseUtil.click(IMG_PATH + "quanbu")) {
                }
                Thread.sleep(TimeAndRandomUtil.random(300, 50));
                while (!Thread.currentThread().isInterrupted() && !MouseUtil.click(IMG_PATH + "ncard")) {
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

    /**
     * 调用结界突破功能
     *
     * @throws InterruptedException 线程等待异常
     */
    private void jjtp() throws InterruptedException {
        if (PointUtil.getPoint(JJTP_PATH + "jjtpman") != null) {
            controller.log("结界突破开始");
            new JieJie().jjtp();
            controller.log("结界突破结束");
        }
        if (PointUtil.getPoint(JJTP_PATH + "bengzhou") != null) {
            MouseUtil.click(JJTP_PATH + "jjtpguanbi");
        }
    }

    /**
     * 获取满级狗粮坐标
     *
     * @return List<Point>
     */
    private List<Point> getMan() {
        return PointUtil.findFloatPoints(IMG_PATH + "man", new Point(500, 0), new Point(1200, 500));
    }

    /**
     * 检查探索次数
     */
    private void checkTimes() throws Exception {
        if (tanSuoTimes != 0) {
            if (times >= tanSuoTimes && PointUtil.findOriginPoints(IMG_PATH + "28").size() >= 1) {
                controller.log("已探索" + times + "次");
                times = 0;
                controller.stop(null);
                if (Config.BEEP_SOUND == 1) {
                    MusicUtil.playMusic("end.mp3");
                }
            }
        }
    }
}
