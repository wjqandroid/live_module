package com.visionvera.live.network;


import androidx.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.bean.AddChatterBean;
import com.visionvera.live.module.home.bean.ChannelBean;
import com.visionvera.live.module.home.bean.ChatMessageBean;
import com.visionvera.live.module.home.bean.ChatterBean;
import com.visionvera.live.module.home.bean.CourseBean;
import com.visionvera.live.module.home.bean.ExpertBean;
import com.visionvera.live.module.home.bean.GroupChatterBean;
import com.visionvera.live.module.home.bean.GroupIdBean;
import com.visionvera.live.module.home.bean.MettingScheduleBean;
import com.visionvera.live.module.login.bean.LoginBean;
import com.visionvera.live.network.api.ApiService;
import com.visionvera.live.network.factory.RetrofitClient;
import com.visionvera.live.network.factory.RetrofitClientForIm;
import com.visionvera.live.network.helper.HttpResultFunction;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * @Desc 网络请求帮助类
 *
 * @Author yemh
 * @Date 2019/4/15 16:54
 */
public class HttpHelper {

    private HttpHelper() {
    }

    private static class SingletonHolder {
        private static HttpHelper singleton = new HttpHelper();
    }

    public static HttpHelper getInstance() {
        return SingletonHolder.singleton;
    }

    /**
     * 得到retrofit代理接口(for Java)
     *
     * @return
     */
    @NonNull
    public ApiService getApiService() {
        return RetrofitClient.INSTANCE.getRetrofit().create(ApiService.class);
    }

    /**
     * 得到retrofit代理接口(for im)
     *
     * @return
     */
    @NonNull
    public ApiService getApiServiceForIM() {
        return RetrofitClientForIm.INSTANCE.getRetrofit().create(ApiService.class);
    }

    /***************************************************以下是各个接口的网络请求**********************************************************/
    /**
     * 登录
     *
     * @param observer
     */
    public void login(LifecycleProvider provider, Observer<ResBean<LoginBean>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .login()
                .compose(provider.<ResBean<LoginBean>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取课程分类
     * @param params
     * @param provider
     * @param observer
     */
    public void getChannelResult(Map<String, String> params, LifecycleProvider provider, Observer<ResBean<List<ChannelBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getChannelResult(params)
                .compose(provider.<ResBean<List<ChannelBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取首页轮播图
     * @param params
     * @param provider
     * @param observer
     */
    public void getBannerResult(Map<String, String> params, LifecycleProvider provider, Observer<ResBean<List<CourseBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getBannerResult(params)
                .compose(provider.<ResBean<List<CourseBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取直播列表
     *
     * @param params
     * @param observer
     */
    public void getSourceResult(RequestBody params, LifecycleProvider provider, Observer<ResBean<List<CourseBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getSourceResult(params)
                .compose(provider.<ResBean<List<CourseBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取搜索列表
     *
     * @param params
     * @param observer
     */
    public void getSearchResult(RequestBody params, LifecycleProvider provider, Observer<ResBean<List<CourseBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .searchCourse(params)
                .compose(provider.<ResBean<List<CourseBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



    /**
     * 收藏
     *
     * @param params
     * @param observer
     */
    public void getCollectResult(Map<String, String> params, LifecycleProvider provider, Observer<ResBean> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getCollectResult(params)
                .compose(provider.<ResBean>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消收藏
     *
     * @param params
     * @param observer
     */
    public void getCancelCollectResult(Map<String, String> params, LifecycleProvider provider, Observer<ResBean> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getCancelCollectResult(params)
                .compose(provider.<ResBean>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取收藏列表
     *
     * @param params
     * @param observer
     */
    public void getCollectListResult(RequestBody params, LifecycleProvider provider, Observer<ResBean<List<CourseBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getCollectListResult(params)
                .compose(provider.<ResBean<List<CourseBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



    /**
     * 根据业务ID获取聊天群
     *
     * @param params
     * @param observer
     */
    public void getGroupByBusinessId(Map<String, Integer> params, LifecycleProvider provider, Observer<ResBean<GroupIdBean>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getGroupByBusinessId(params)
                .compose(provider.<ResBean<GroupIdBean>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 根据登录帐号获取聊天人
     *
     * @param params
     * @param observer
     */
    public void getByLoginName(Map<String, Object> params, LifecycleProvider provider, Observer<ResBean<ChatterBean>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getByLoginName(params)
                .compose(provider.<ResBean<ChatterBean>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 加入群聊天
     *
     * @param params
     * @param observer
     */
    public void addChatter(RequestBody params, LifecycleProvider provider, Observer<ResBean<AddChatterBean>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .addChatter(params)
                .compose(provider.<ResBean<AddChatterBean>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 判断是否启用第三方IM
     *
     * @param params
     * @param observer
     */
    public void getThirdPartIm(Map<String, Integer> params, LifecycleProvider provider, Observer<ResBean<Boolean>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getThirdPartIm(params)
                .compose(provider.<ResBean<Boolean>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取第三方IM用户签名
     *
     * @param params
     * @param observer
     */
    public void getUserSign(Map<String, Integer> params, LifecycleProvider provider, Observer<ResBean<String>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getUserSign(params)
                .compose(provider.<ResBean<String>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取历史聊天消息
     *
     * @param params
     * @param observer
     */
    public void getHistoryMessage(String url, RequestBody params, LifecycleProvider provider, Observer<ResBean<List<ChatMessageBean>>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getHistoryMessage(url,params)
                .compose(provider.<ResBean<List<ChatMessageBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取群成员列表
     *
     * @param params
     * @param observer
     */
    public void getGroupChatter(RequestBody params, LifecycleProvider provider, Observer<ResBean<List<GroupChatterBean>>> observer) {
        if (null == getApiServiceForIM()) {
            return;
        }
        getApiServiceForIM()
                .getGroupChatter(params)
                .compose(provider.<ResBean<List<GroupChatterBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取日程安排
     *
     * @param params
     * @param observer
     */
    public void getMettingSchedule(Map<String, String> params, LifecycleProvider provider, Observer<ResBean<List<MettingScheduleBean>>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getMettingSchedule(params)
                .compose(provider.<ResBean<List<MettingScheduleBean>>>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取专家简介
     *
     * @param params
     * @param observer
     */
    public void getExpertIntroduction(Map<String, String> params, LifecycleProvider provider, Observer<ResBean<ExpertBean>> observer) {
        if (null == getApiService()) {
            return;
        }
        getApiService()
                .getExpertIntroduction(params)
                .compose(provider.<ExpertBean>bindToLifecycle())
                .onErrorResumeNext(new HttpResultFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
