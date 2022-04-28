package com.visionvera.live.network;


import com.visionvera.library.base.Constant;

/**
 * @Desc 接口类
 * @Author yemh
 * @Date 2019/4/15 17:10
 */
public class HttpInterface {

    public static final String CHECK_URL = Constant.Url.request_base_url+"/rmcp/";
    public static final String CHECK_SHARE_URL = Constant.Url.request_base_url+"/wxnew/index.php/Home/WebChatShare/index.html?course_id=";


    /********************************IM开始**************************************/
//    ws://58.30.9.142:43148/chat/websocket/
    public static final String WS ="ws:" +Constant.Url.request_base_url.substring(5)+"chat/chat/websocket/";
    public static final String IM_BASE_URL = Constant.Url.request_base_url+"chat/chat/";
    /********************************IM结束**************************************/


    /**
     * 线上环境
     * Constant.Url.request_base_url
     */

    public static final String BASE_URL = CHECK_URL;

    /**
     * 分享链接
     * Constant.Url.request_base_url
     */
    public static final String SHARE_URL = CHECK_SHARE_URL;

    public interface ParamKeys {

        /**
         * token
         */
        String TOKEN = "token";

        /**
         * 手机
         */
        String MOBILE = "mobile";

        /**
         * 密码
         */
        String PASSWORD = "password";

        /**
         * 每页请求数量
         */
        String PAGE_SIZE = "pageSize";

        /**
         * userid
         */
        String USERID = "userId";

        /**
         * 关键字
         */
        String KEY_NAME = "name";

        /**
         * 播放类型
         */
        String PLAY_MODEL = "playModel";

        /**
         * 开始页数
         */
        String PAGE_NUM = "pageNum";

        /**
         * 课程类型
         */
        String TYPE_ID = "typeId";

        /**
         * 课程ID
         */
        String COURSE_ID = "courseId";

        /**
         * 业务ID(对应课程ID)
         */
        String BUSINESS_ID = "businessId";

        /**
         * 业务类型ID(固定18)
         */
        String SYSTEM_ID = "systemId";

        /**
         * 聊天人ID
         */
        String CHATTER_ID = "chatterId";

        /**
         * 会议ID
         */
        String METTING_ID = "meetingId";

        /**
         * 专家id
         */
        String EXPERT_ID = "expertId";
    }

    /**
     * 直播相关
     */
    public interface LiveApi {
        /**
         * 获取课程分类
         */
        String CHANNEL = "appCourse/getCourseTypes";

        /**
         * 首页轮播图
         */
        String BANNER = "appCourse/getBanners";

        /**
         * 课程列表
         */
        String COURSE_BY_TYPE = "appCourse/queryCourseByType";

        /**
         * 搜索课程列表
         */
        String SEARCH_COURSE = "appCourse/queryCourseBySearch";

        /**
         * 日程安排
         */
        String METTING_SCHEDULE = "appMeeting/getMeetingSchedule";
    }

    /**
     * 收藏相关
     */
    public interface CollectApi {
        /**
         * 收藏课程
         */
        String COLLECT_COURSE = "appCourse/collectCourse";

        /**
         * 取消收藏
         */
        String CANCLE_COLLECT = "appCourse/cancelCollectCourse";

        /**
         * 我的收藏
         */
        String MY_COLLECTS = "appCourse/queryCourseByCollect";
    }

    /**
     * 专家相关
     */
    public interface ExpertApi {
        /**
         * 关注专家
         */
        String ATTENTION_EXPERT = "appExpert/collectExpert";

        /**
         * 取消关注专家
         */
        String CANCLE_ATTENTION_EXPERT = "appExpert/cancelCollectExpert";

        /**
         * 专家列表
         */
        String EXPERTS = "appExpert/getCollectExpertList";

        /**
         * 专家详情
         */
        String EXPETT_DETAIL = "appExpert/getExpertDetailsByCourse";
    }

    /**
     * Member相关
     */
    public interface MemberApi {
        /**
         * 修改密码
         */
        String UPDATE_PASSWORD = "appUser/updatePassword";

        /**
         * 设置昵称
         */
        String UPDATE_NICKNAME = "appUser/changeNikeName";

        /**
         * 修改手机号
         */
        String UPDATE_PHONE = "appUser/changeMobile";

        /**
         * 版本更新
         */
        String VERSION_UPDATE = "appConfig/checkVersion";

        /**
         * 消息中心
         */
        String MESSAGE_CENTER = "";

        /**
         * 医院名称
         */
        String CHANGE_HOSPITAL = "appUser/changeHospital";

        /**
         * 职务名称
         */
        String CHANGE_TITLE = "appUser/changeTitle";
    }

    /**
     * 登录相关
     */
    public interface Login {
        /**
         * 发送验证码
         */
        String SEND_CODE_URL = "appUser/sendCode";

        /**
         * 验证验证码
         */
        String CHECK_CODE_URL = "appUser/checkCode";

        /**
         * 登录
         */
        String LOGIN = "appUser/login";

        /**
         * 注册
         */
        String REGISTER = "appUser/register";

        /**
         * 快速登录
         */
        String FAST_LOGIN = "appUser/quickLogin";

        /**
         * 找回密码
         */
        String FIND_PASSWORD = "appUser/findPassword";

    }

    /**
     * 个人信息相关
     */
    public interface PersonalInfo {
        /**
         * 获取个人信息
         */
        String GET_USER_INFO = "appUser/getUserInfo";

        /**
         * 获取科室列表
         */
        String GET_DEPART_LIST = "appCourse/getCourseDepts";

        /**
         * 修改所在科室
         */
        String SELECT_DEPART = "appUser/changeDept";

        /**
         * 更新头像
         */
        String UPDATE_HEAD = "appUser/changePicture";
    }

    public interface IMApi {
        /**
         * 根据业务ID获取聊天群
         */
        String GET_GROUP_BY_BUSINESSID = "group/getGroupByBusinessId";

        /**
         * 根据登录帐号获取聊天人id
         */
        String GET_BY_LOGINNAME = "chatter/getByLoginName";

        /**
         * 加入聊天群
         */
        String ADD_CHATTER = "group/addChatter";

        /**
         * 判断是否使用腾讯IM
         */
        String IS_THIRDPARTIM_ENABLE = "thirdparty/isThirdPartyIMEnabled";

        /**
         * 获取第三方IM用户签名
         */
        String GET_USER_SIGN = "thirdparty/getUserSign";

        /**
         * 获取群成员
         */
        String GET_GROUP_CHATTERS = "group/getGroupChatters";

        /**
         * 获取历史聊天消息
         */
        String GET_HISTORY = IM_BASE_URL + "message/getList";
    }
}
