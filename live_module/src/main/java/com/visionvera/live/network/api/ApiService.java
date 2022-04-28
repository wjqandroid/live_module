package com.visionvera.live.network.api;


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
import com.visionvera.live.network.HttpInterface;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Desc App API
 *
 * @Author yemh
 * @Date 2019/4/15 17:18
 */
public interface ApiService {

    /**
     * 登录
     *
     * @return
     */
    @GET(HttpInterface.Login.LOGIN)
    Observable<ResBean<LoginBean>> login();

    /**
     * 课程分类
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.LiveApi.CHANNEL)
    Observable<ResBean<List<ChannelBean>>> getChannelResult(@QueryMap Map<String, String> params);

    /**
     * 首页轮播图
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.LiveApi.BANNER)
    Observable<ResBean<List<CourseBean>>> getBannerResult(@QueryMap Map<String, String> params);

    /**
     * 课程列表
     *
     * @param params
     * @return
     */
    @POST(HttpInterface.LiveApi.COURSE_BY_TYPE)
    Observable<ResBean<List<CourseBean>>> getSourceResult(@Body RequestBody params);

    /**
     * 搜索
     *
     * @param params
     * @return
     */
    @POST(HttpInterface.LiveApi.SEARCH_COURSE)
    Observable<ResBean<List<CourseBean>>> searchCourse(@Body RequestBody params);


    /**
     * 收藏
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.CollectApi.COLLECT_COURSE)
    Observable<ResBean> getCollectResult(@QueryMap Map<String, String> params);

    /**
     * 取消收藏
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.CollectApi.CANCLE_COLLECT)
    Observable<ResBean> getCancelCollectResult(@QueryMap Map<String, String> params);

    /**
     * 收藏列表
     *
     * @param params
     * @return
     */
    @POST(HttpInterface.CollectApi.MY_COLLECTS)
    Observable<ResBean<List<CourseBean>>> getCollectListResult(@Body RequestBody params);

    /**
     * 根据业务ID获取聊天群
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.IMApi.GET_GROUP_BY_BUSINESSID)
    Observable<ResBean<GroupIdBean>> getGroupByBusinessId(@QueryMap Map<String, Integer> params);

    /**
     * 根据登录帐号获取聊天人
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.IMApi.GET_BY_LOGINNAME)
    Observable<ResBean<ChatterBean>> getByLoginName(@QueryMap Map<String, Object> params);

    /**
     * 加入群聊天
     *
     * @param params
     * @return
     */
    @POST(HttpInterface.IMApi.ADD_CHATTER)
    Observable<ResBean<AddChatterBean>> addChatter(@Body RequestBody params);

    /**
     * 判断是否启用第三方IM
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.IMApi.IS_THIRDPARTIM_ENABLE)
    Observable<ResBean<Boolean>> getThirdPartIm(@QueryMap Map<String, Integer> params);

    /**
     * 获取历史聊天消息
     *
     * @param params
     * @return
     */
    @POST
    Observable<ResBean<List<ChatMessageBean>>> getHistoryMessage(@Url String url, @Body RequestBody params);

    /**
     * 判断是否启用第三方IM
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.IMApi.GET_USER_SIGN)
    Observable<ResBean<String>> getUserSign(@QueryMap Map<String, Integer> params);

    /**
     * 获取群成员
     *
     * @param params
     * @return
     */
    @POST(HttpInterface.IMApi.GET_GROUP_CHATTERS)
    Observable<ResBean<List<GroupChatterBean>>> getGroupChatter(@Body RequestBody params);

    /**
     * 获取日程安排
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.LiveApi.METTING_SCHEDULE)
    Observable<ResBean<List<MettingScheduleBean>>> getMettingSchedule(@QueryMap Map<String, String> params);

    /**
     * 获取专家详情
     *
     * @param params
     * @return
     */
    @GET(HttpInterface.ExpertApi.EXPETT_DETAIL)
    Observable<ResBean<ExpertBean>> getExpertIntroduction(@QueryMap Map<String, String> params);


}