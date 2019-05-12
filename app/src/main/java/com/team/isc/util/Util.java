package com.team.isc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZX on 2019/5/2.
 */

public class Util {
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
