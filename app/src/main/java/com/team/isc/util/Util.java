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
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     *
     * @return true没问题;false信息不完全
     */
    public static boolean checkUserAccount(){
        if((SPUtil.getInt("uno")+"").isEmpty()
                ||SPUtil.getString("username","").isEmpty()
                ||SPUtil.getString("realname","").isEmpty()
                ||SPUtil.getString("sex","").isEmpty()
                ||SPUtil.getString("dept","").isEmpty()
                ||SPUtil.getString("class","").isEmpty()
                ||SPUtil.getString("num","").isEmpty()
                ||SPUtil.getString("phone","").isEmpty()){
            return false;
        }else {
            return true;
        }
    }

}
