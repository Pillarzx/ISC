package com.team.isc.base;

public abstract class BasePresenter<T extends BaseViewInterface> {
    private T mView;

    protected void onCreate() {
    }

    /**
     * 销毁耗时操作
     */
    protected void onDestroy() {
    }

    void onAttachView(T view) {
        mView = view;
    }

    void onDetachView() {
        onDestroy();
        mView = null;
    }

    public T getView() {
        return mView;
    }
}
