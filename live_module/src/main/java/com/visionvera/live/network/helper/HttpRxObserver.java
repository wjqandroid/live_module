package com.visionvera.live.network.helper;import android.content.Context;import android.util.Log;import com.visionvera.library.base.bean.BaseResponseBean2;import com.visionvera.live.R;import com.visionvera.live.base.bean.ResBaseBean;import com.visionvera.live.utils.NetWorkUtils;import com.visionvera.live.utils.ToastUtils;import io.reactivex.annotations.NonNull;import io.reactivex.observers.DisposableObserver;/** * @Desc 自定义观察者 * @Author yemh * @Date 2019/4/15 17:27 */public abstract class HttpRxObserver<T> extends DisposableObserver<T> {    private Context mContext;//    private ProgressDialog progressDialog;    /**     * 是否需要显示默认Loading     */    private boolean showProgress = true;    public HttpRxObserver(Context context, boolean showProgress) {        this.mContext = context;        this.showProgress = showProgress;//        if (progressDialog == null) {//            progressDialog = new ProgressDialog(context);//            progressDialog.setMessage(context.getString(R.string.brvah_loading));//            progressDialog.setCancelable(false);//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//        }    }    /**     * 错误/异常回调     *     * @param e     */    protected abstract void onError(ApiException e);    /**     * 成功回调     *     * @param t     */    protected abstract void onSuccess(T t);    @Override    protected void onStart() {        super.onStart();        showProgressDialog();        if (!NetWorkUtils.isNetworkAvailable()) {            ToastUtils.showShort(mContext, R.string.please_check_your_network);            onComplete();            return;        }    }    @Override    public void onNext(T t) {        if (t instanceof ResBaseBean){            ResBaseBean bean = ((ResBaseBean) t);            int code = bean.getCode();            switch (code) {                case 492://账号在别处登录                    onSuccess(t);                    break;                default:                    onSuccess(t);                    break;            }        }else if(t instanceof BaseResponseBean2){            BaseResponseBean2 bean2= (BaseResponseBean2) t;            int code = bean2.getCode();            switch (code) {                case 492://账号在别处登录                    onSuccess(t);                    break;                default:                    onSuccess(t);                    break;            }        }    }    @Override    public void onError(@NonNull Throwable e) {        Log.e("tag", "onError: "+e );        dismissProgressDialog();        if (e instanceof ApiException) {            onError((ApiException) e);        } else {            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));        }    }    @Override    public void onComplete() {        dismissProgressDialog();    }    /**     * 显示加载进度框     */    private void showProgressDialog() {//        if (showProgress && progressDialog != null && !progressDialog.isShowing()) {//            progressDialog.show();//        }    }    /**     * 隐藏加载进度框     */    private void dismissProgressDialog() {//        if (showProgress && progressDialog != null && progressDialog.isShowing()) {//            progressDialog.dismiss();//        }    }}