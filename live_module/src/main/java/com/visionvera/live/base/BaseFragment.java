package com.visionvera.live.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.visionvera.live.base.event.EventBusUtils;
import com.visionvera.live.base.event.MessageEvent;
import com.visionvera.live.base.mvp.IBaseView;
import com.visionvera.live.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 *
 * @author yemh
 * @date 2017/7/20 15:42
 */

public abstract class BaseFragment extends RxFragment implements IBaseView {
    protected View parentView;
    public Activity act;
    private Unbinder mBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 注册事件
        EventBusUtils.register(this);
        parentView = inflater.inflate(getLayoutId(), container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBind = ButterKnife.bind(this, view);
        init(savedInstanceState);
        setListener();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取layout文件id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 界面初始化
     *
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 设置监听
     */
    public abstract void setListener();

    /**
     * 处理数据
     */
    public abstract void loadData();

    @Override
    public void showError(String errorMsg) {
        if (!TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showShort(act, errorMsg);
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(act, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        act.startActivity(intent);
    }

    /**
     * 带有返回结果的跳转
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int code) {
        Intent intent = new Intent();
        intent.setClass(act, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        act.startActivityForResult(intent, code);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 注销事件
        EventBusUtils.unregister(this);
        if (mBind != null) {
            mBind.unbind();
        }
    }

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(MessageEvent event) {
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(MessageEvent event) {
    }
}
