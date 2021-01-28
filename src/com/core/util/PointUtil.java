package com.core.util;

import com.core.common.Config;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Celphis
 * 基于opencv的图片查找工具类
 * 返回图片在屏幕中的坐标
 */
public class PointUtil {
    private static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static List<Point> points;
    private static final String PATH;
    private static Mat target;
    private static Mat screen;
    private static final int IMREAD_TYPE;

    static {
        PATH = new File("").getAbsolutePath() + "/img/";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        IMREAD_TYPE = Config.IMREAD_TYPE;
    }

    public static List<Point> findFloatPoints(String img) {
        prepare(img);
        find(true);
        return points;
    }

    public static List<Point> findOriginPoints(String img) {
        prepare(img);
        find(false);
        return points;
    }

    public static List<Point> findFloatPoints(String img, Point upLeft, Point downRight) {
        prepare(img, upLeft, downRight);
        find(true);
        for (Point point : points) {
            point.x = point.x + upLeft.x;
            point.y = point.y + upLeft.y;
        }
        return points;
    }

    public static List<Point> findOriginPoints(String img, Point upLeft, Point downRight) {
        prepare(img, upLeft, downRight);
        find(false);
        for (Point point : points) {
            point.x = point.x + upLeft.x;
            point.y = point.y + upLeft.y;
        }
        return points;
    }

    public static Point toPoint(org.opencv.core.Point point) {
        return new Point((int) point.x, (int) point.y);
    }

    private static void prepare(String img) {
        points = new ArrayList<>();
        BufferedImage screenshot;
        try {
            screenshot = (new Robot()).createScreenCapture(
                    new Rectangle(0, 0, (int) DIMENSION.getWidth(), (int) DIMENSION.getHeight()));
            ImageIO.write(screenshot, "jpg", new File(PATH + "screen.jpg"));
            target = Imgcodecs.imread(PATH + img + ".jpg", IMREAD_TYPE);
            screen = Imgcodecs.imread(PATH + "screen.jpg", IMREAD_TYPE);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void prepare(String img, Point upLeft, Point downRight) {
        points = new ArrayList<>();
        BufferedImage screenshot;
        try {
            screenshot = (new Robot()).createScreenCapture(
                    new Rectangle(0, 0, (int) DIMENSION.getWidth(), (int) DIMENSION.getHeight()));
            ImageIO.write(screenshot, "jpg", new File("img/screen.jpg"));
            target = Imgcodecs.imread(PATH + "/" + img + ".jpg", IMREAD_TYPE);
            screen = Imgcodecs.imread("img/screen.jpg", IMREAD_TYPE).submat(upLeft.y, downRight.y, upLeft.x, downRight.x);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void find(boolean isFloat) {
        Mat result = new Mat();
        Imgproc.matchTemplate(target, screen, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult matchResult = Core.minMaxLoc(result);
        if (matchResult.maxVal >= Config.PRECISION) {
            points.add(matchPoint(matchResult, isFloat));
            find(isFloat);
        }
    }

    private static void find(boolean isFloat, double precision) {
        Mat result = new Mat();
        Imgproc.matchTemplate(target, screen, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult matchResult = Core.minMaxLoc(result);
        if (matchResult.maxVal >= precision) {
            points.add(matchPoint(matchResult, isFloat));
            find(isFloat, precision);
        }
    }

    private static Point matchPoint(Core.MinMaxLocResult matchResult, boolean isFloat) {
        Point point = PointUtil.toPoint(matchResult.maxLoc);
        Imgproc.rectangle(screen, matchResult.maxLoc, new org.opencv.core.Point(point.x + target.width(), point.y + target.height()), new Scalar(0), -1);
        if (isFloat) {
            point = floatPoint(target, point);
        }
        return point;
    }

    private static Point floatPoint(Mat target, Point point) {
        return new Point(point.x + new Random().nextInt(target.width()), point.y + new Random().nextInt(target.height()));
    }

    public static Point floatPoint(org.opencv.core.Point point) {
        return new Point((int) point.x + new Random().nextInt(30), (int) point.y + new Random().nextInt(30));
    }

    public static Point floatPoint(Point point) {
        return new Point(point.x + new Random().nextInt(30), point.y + new Random().nextInt(30));
    }

    public static Point getPoint(String img) {
        List<Point> points = PointUtil.findFloatPoints(img);
        if (points.size() >= 1) {
            return points.get(0);
        }
        return null;
    }

    public static Point getPoint(String img, double precision) {
        List<Point> points = PointUtil.findFloatPoints(img, precision);
        if (points.size() >= 1) {
            return points.get(new Random().nextInt(points.size()));
        }
        return null;
    }

    private static List<Point> findFloatPoints(String img, double precision) {
        prepare(img);
        find(true, precision);
        return points;
    }
}
