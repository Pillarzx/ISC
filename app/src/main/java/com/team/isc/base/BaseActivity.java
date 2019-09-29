package com.team.isc.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

import java.lang.ref.WeakReference;

import com.team.isc.widget.dialog.LoadingTip;

import com.team.isc.R;

public abstract class BaseActivity<P extends BasePresenter, V extends ViewDataBinding> extends AppCompatActivity implements BaseViewInterface{
    private Toolbar toolbar;
    private V mBinding;
    private P mPresenter;
    private LoadingShowTask loadingShowTask;

    protected abstract P initPresenter();

    protected abstract int initLayout();

    protected abstract void setupView();

    public P getPresenter() {
        if (mPresenter == null) {
            throw new UnsupportedOperationException("Please make sure that there was not null return in method \"initPresenter()\", which use to instantiate one Presenter.");
        }
        return mPresenter;
    }

    /**
     * @return DataBinding实例
     */
    public V getBinding() {
        return mBinding;
    }

    public Context getContext() {
        return this;
    }

    public android.support.v7.widget.Toolbar getToolbar() {
        if (toolbar == null) {
            throw new UnsupportedOperationException("You should call setToolbar(Toolbar) to set up a toolbar first.");
        }
        return toolbar;
    }

    protected void setToolbar(android.support.v7.widget.Toolbar toolbar, boolean showBack) {
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);

        if (showBack) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 布局中使用默认ID的Toolbar时直接调用该方法可设置是否显示返回按钮
     */
    protected void setToolbar(boolean showBack) {
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar, showBack);
    }

    /**
     * 处理子线程调用，自动切换到主线程
     */
    public final void showLoadingTip() {
        showLoadingTip(null);
    }

    public final void showLoadingTip(final String msg) {
        if (null == loadingShowTask) {
            loadingShowTask = new LoadingShowTask(BaseActivity.this);
        }
        loadingShowTask.setMessage(msg);
        runOnUiThread(loadingShowTask);
    }

    public final void dismissLoadingTip() {
        if (null != loadingShowTask) {
            loadingShowTask.destroy();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, initLayout());
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
            setupView();
            mPresenter.onCreate();
        } else {
            setupView();
        }
    }

    private static final class LoadingShowTask implements Runnable {

        private String message;
        private WeakReference<Context> contextRef;
        private LoadingTip mLoadingTip;

        public LoadingShowTask(Context context) {
            contextRef = new WeakReference<>(context);
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void destroy() {
            if (mLoadingTip != null) {
                mLoadingTip.dismiss();
            }
        }

        @Override
        public void run() {
            Context context = contextRef.get();
            if (context == null) {
                return;
            }

            if (mLoadingTip == null) {
                mLoadingTip = new LoadingTip(context);
                mLoadingTip.setCancelable(true);
            }
            mLoadingTip.show(message);
        }
    }
}
