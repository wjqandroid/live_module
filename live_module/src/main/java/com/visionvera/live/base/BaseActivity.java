package com.visionvera.live.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.visionvera.live.base.event.EventBusUtils;
import com.visionvera.live.base.event.MessageEvent;
import com.visionvera.live.base.mvp.IBaseView;
import com.visionvera.live.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity基类
 *
 * @author yemh
 * @date 2017/7/20 15:41
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {
    private ProgressDialog mDialog;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        mBind = ButterKnife.bind(this);
        // 注册EventBus事件
        EventBusUtils.register(this);
        init(savedInstanceState);
        initToolBar();
        initStatusColor();
        setListener();
        loadData();
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
     * 初始化ToolBar
     */
    public abstract void initToolBar();

    /**
     * 设置监听
     */
    public abstract void setListener();

    /**
     * 处理数据
     */
    public abstract void loadData();

    private void initStatusColor() {
        ImmersionBar.with(this).init();
    }

    /**
     * 创建并show加载进度框
     *
     * @param msg
     * @param cancelAble
     */
    public void showDialog(String msg, boolean cancelAble) {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(msg);
        mDialog.setCancelable(cancelAble);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
    }

    /**
     * 隐藏加载进度框
     */
    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    public void showError(String errorMsg) {
        if (!TextUtils.isEmpty(errorMsg)){
        ToastUtils.showShort(this,errorMsg);}
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 需要返回结果的跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
