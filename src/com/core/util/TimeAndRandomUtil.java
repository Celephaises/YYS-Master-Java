package com.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Celphis
 * 时间工具类
 */
public class TimeAndRandomUtil {
    public static int random(int start, int range) {
        return start + new Random().nextInt(range);
    }

    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());
    }
}
