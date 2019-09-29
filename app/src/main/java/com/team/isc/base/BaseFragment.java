package com.team.isc.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.isc.common.Const;

public abstract class BaseFragment<P extends BasePresenter,V extends ViewDataBinding> extends Fragment implements BaseViewInterface {
    private V mBinding;
    private P mPresenter;
    protected abstract P initPresenter();
    protected abstract int initLayout();
    protected abstract void setupView();
    private int mPushListenMode = 0;
    private boolean mHasRegistered = false;
    private boolean mLastVisibleState = false;//页面跳转时保存状态
    public P getPresenter() {
        if (mPresenter == null) {
            throw new UnsupportedOperationException("Please make sure that there was not null return in method \"initPresenter()\", which use to instantiate one Presenter.");
        }
        return mPresenter;
    }

    public V getBinding() {
        return mBinding;
    }

    protected void initView(View view) {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false);
        mPresenter = initPresenter();
        if (mBinding != null) {
            return mBinding.getRoot();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //自动获取fragment参数，设置监听模式，一直监听/可见时监听
        Bundle bundle = getArguments();
        initView(view);
        if (bundle == null) {
            setPushListenMode(Const.PUSH_LISTEN_MODE_ONLY_VISIBLE);
        } else {
            setPushListenMode(bundle.getInt(Const.PUSH_LISTEN_MODE, Const.PUSH_LISTEN_MODE_ONLY_VISIBLE));
        }
        //初始化页面和mPresenter
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
            setupView();
            mPresenter.onCreate();
        } else {
            setupView();
        }
        if (mPushListenMode == Const.PUSH_LISTEN_MODE_ALWAYS) {
            registerListener();
        }

    }
    protected void registerListener() {

    }
    protected void unregisterListener() {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //在创建实例时会调用一次false，这时view还没创建好
        if (getBinding() == null) {
            return;
        }
        if (mPushListenMode == Const.PUSH_LISTEN_MODE_ONLY_VISIBLE) {
            if (isVisibleToUser) {
                if (!mHasRegistered) {
                    mHasRegistered = true;
                    registerListener();
                }
            } else {
                if (mHasRegistered) {
                    mHasRegistered = false;
                    unregisterListener();
                }
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint() || mLastVisibleState) {
            setUserVisibleHint(true);
        }
    }
    @Override
    public void onStop() {
        //当前页手动设置
        mLastVisibleState = getUserVisibleHint();
        if (mLastVisibleState) {
            setUserVisibleHint(false);
        }
        super.onStop();
    }
    public void showLoadingTip() {
        if (getUserVisibleHint()) {
            ((BaseActivity) getActivity()).showLoadingTip();
        }
    }

    public void showLoadingTip(String msg) {
        if (getUserVisibleHint()) {
            ((BaseActivity) getActivity()).showLoadingTip(msg);
        }
    }
    public void dismissLoadingTip() {
        ((BaseActivity) getActivity()).dismissLoadingTip();
    }

    protected void initToolbar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }
    /**
     * 在fragment创建时的调用<br/>
     * 设置监听模式，一直监听/可见时监听
     *
     * @param pushListenMode 看Const.PUSH_LISTEN_MODE_ALWAYS/Const.PUSH_LISTEN_MODE_ONLY_VISIBLE
     */
    public void setPushListenMode(int pushListenMode) {
        this.mPushListenMode = pushListenMode;
    }
}
