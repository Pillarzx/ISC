package com.team.isc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.isc.R;

public class LoadingTip extends Dialog {

    private TextView textView;
    private String message;
    private String defaultMessage;
    public LoadingTip(@NonNull Context context) {
        super(context);
    }

    /**
     * 显示自定义提示
     *
     * @param message
     */
    public void show(String message) {
        if (!isShowing()) {
            super.show();
        }
        setMessage(message);
    }
    /**
     * 设置提示文字
     */
    public void setMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            this.message = defaultMessage;
        } else {
            this.message = message;
        }
        if (textView != null) {
            textView.setText(this.message);
        }
    }
}
