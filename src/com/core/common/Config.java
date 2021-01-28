package com.core.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Period;
import java.util.Properties;

/**
 * 配置类
 *
 * @author Celphis
 */
public class Config {
    /**
     * 图片模糊匹配精度
     * 数值越高精度越高
     */
    public static Properties config;
    public static double PRECISION;
    public static String ICON_PATH;
    public static String GIF_PATH;
    public static Integer IMREAD_TYPE;
    public static Integer CLICK_WAIT;

    public static void init() throws NumberFormatException, IOException {
        config = new Properties();
        config.load(new FileInputStream(new File("").getAbsolutePath() + "\\config.properties"));
        PRECISION = new Double(config.getProperty("PRECISION", "0.88"));
        PRECISION = PRECISION >= 0 && PRECISION <= 1 ? PRECISION : 0.88;
        ICON_PATH = config.getProperty("ICON_PATH", "https://i0.hdslb.com/bfs/album/16a1b7ee8d6d9d547d1be4501e2fbe025b8b846a.jpg");
        GIF_PATH = config.getProperty("GIF_PATH", "https://i0.hdslb.com/bfs/album/9cc284cffc643fc8794d731991a275f0921354ff.gif");
        IMREAD_TYPE = new Integer(config.getProperty("IMREAD_TYPE", "0"));
        IMREAD_TYPE = IMREAD_TYPE == 1 || IMREAD_TYPE == 0 ? IMREAD_TYPE : 0;
        CLICK_WAIT = new Integer(config.getProperty("CLICK_WAIT", "50"));
    }
}
