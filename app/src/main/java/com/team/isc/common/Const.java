package com.team.isc.common;

public class Const {
    public static final String PUSH_LISTEN_MODE = "push_listen_mode";
    /**
     * fragment推送监听模式<br/>
     * 在后台时仍然保持监听，直到fragment销毁onDestoryView();
     */
    public static final int PUSH_LISTEN_MODE_ALWAYS = 80;
    /**
     * fragment推送监听模式<br/>
     * 只在页面可以见的时候注册监听，不可见时取消
     */
    public static final int PUSH_LISTEN_MODE_ONLY_VISIBLE = 81;

    public static final String MODE_ONLY_VISIBLE = "mode_only_visible";
    public static final String MODE_AFTER_TEN_MINUTE = "mode_after_ten_minute";
    public static final String MODE_ALWAYS = "mode_always";
}
