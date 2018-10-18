package com.example.chaozhou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yfj on 2018-4-12.
 */
public class MobilePhone {
    /**
     * 判断手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
}
