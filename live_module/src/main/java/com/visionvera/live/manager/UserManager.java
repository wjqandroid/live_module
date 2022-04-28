package com.visionvera.live.manager;

import android.text.TextUtils;

import com.visionvera.live.constant.Globe;
import com.visionvera.live.utils.SharedPreferenceUtils;


/**
 * @Desc 用户相关信息管理类
 * @Author yemh
 * @Date 2019/4/22 13:44
 */
public class UserManager {
    public interface UserSpKeys {
        /**
         * 用户ID
         */
        String SP_USER_ID = "user_id";

        /**
         * 用户手机号
         */
        String SP_USER_PHONE = "user_phone";

        /**
         * 用户昵称
         */
        String SP_USER_NICKNAME = "user_nickname";

        /**
         * 用户头像
         */
        String SP_USER_HEAD_IMG = "user_head_img";

        /**
         * 登录TOKEN
         */
        String SP_AUTH_TOKEN = "auth_token";

        /**
         * 所在科室
         */
        String SP_USER_DEPARTMENT = "user_department";

        /**
         * 医院名称
         */
        String SP_USER_HOSPITAL = "user_hospital";

        /**
         * 职称
         */
        String SP_USER_JOBTITLE = "user_jobtitle";

        /**
         * chatterid
         */
        String SP_CHATTER_ID = "chatterId";
    }

    private String userId = "";             // 用户ID
    private String userPhone = "";          // 用户手机号
    private String userNickname = "";       // 用户昵称
    private String userHeadImg = "";        // 用户头像
    private String authToken = "";          // 登录token
    private String userDepartMent = "";     // 所在科室
    private String userHospital = "";       // 医院名称
    private String userJobTitle = "";       // 职称
    private int chatterId = 0;             // 聊天人ID

    public static UserManager mUserManage;


    public static UserManager getInstence() {
        if (mUserManage == null) {
            synchronized (UserManager.class) {
                if (mUserManage == null) {
                    mUserManage = new UserManager();
                }
            }
        }
        return mUserManage;
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(String userId, String userPhone, String userNickname, String userHeadImg,
                               String authToken, String depart, int chatId, String hospital, String jobTitle) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.userNickname = userNickname;
        this.userHeadImg = userHeadImg;
        this.authToken = authToken;
        this.userDepartMent = depart;
        this.userHospital = hospital;
        this.userJobTitle = jobTitle;
        this.chatterId = chatId;
        updateUserPhone(userPhone);
        updateUserHead(userHeadImg);
        updateUserDepart(depart);
        updateUserHospital(hospital);
        updateUserNickName(userNickname);
        updateUserJobTitle(jobTitle);
        save();
    }

    /**
     * 清除用户信息
     */
    public void cleanUserInfo() {
        this.userId = "";
        this.userNickname = "";
        this.userHeadImg = "";
        this.authToken = "";
        this.userDepartMent = "";
        this.userHospital = "";
        this.userJobTitle = "";
        this.chatterId = 0;
        updateUserHead("");
        updateUserDepart("");
        updateUserHospital("");
        updateUserNickName("");
        updateUserJobTitle("");
        SharedPreferenceUtils.putBoolean(Globe.SP_ISLOGIN, false);
        save();
    }

    /**
     * 保存
     */
    public void save() {
        updateUserID(userId);
        updateUserToken(authToken);
        updateChatterId();
    }

    /**
     * 更新用户ID
     */
    public void updateUserID(String id) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_ID, id);
    }

    /**
     * 更新用户手机号
     *
     */
    public void updateUserPhone(String phone) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_PHONE, phone);
    }

    /**
     * 更新用户昵称
     *
     */
    public void updateUserNickName(String nickName) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_NICKNAME, nickName);
    }

    /**
     * 更新用户所在科室
     *
     */
    public void updateUserDepart(String departMent) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_DEPARTMENT, departMent);
    }

    /**
     * 更新用户所在医院名称
     *
     */
    public void updateUserHospital(String hospital) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_HOSPITAL, hospital);
    }

    /**
     * 更新用户职称
     *
     */
    public void updateUserJobTitle(String jobTitle) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_JOBTITLE, jobTitle);
    }

    /**
     * 更新聊天人ID
     *
     */
    public void updateChatterId() {
        SharedPreferenceUtils.putInt(UserSpKeys.SP_CHATTER_ID, chatterId);
    }

    /**
     * 更新用户头像
     *
     */
    public void updateUserHead(String headImg) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_USER_HEAD_IMG, headImg);
    }

    /**
     * 更新用户登录token
     *
     */
    public void updateUserToken(String token) {
        SharedPreferenceUtils.putString(UserSpKeys.SP_AUTH_TOKEN, token);
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserId() {
        if (TextUtils.isEmpty(userId)) {
            userId = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_ID, "");
        }
        return userId;
    }

    /**
     * 获取用户手机号
     *
     * @return
     */
    public String getUserPhone() {
        if (TextUtils.isEmpty(userPhone)) {
            userPhone = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_PHONE, "");
        }
        return userPhone;
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public String getUserNickname() {
        if (TextUtils.isEmpty(userNickname)) {
            userNickname = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_NICKNAME, "");
        }
        return userNickname;
    }

    /**
     * 获取用户所在科室
     *
     * @return
     */
    public String getUserDepartMent() {
        if (TextUtils.isEmpty(userDepartMent)) {
            userDepartMent = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_DEPARTMENT, "");
        }
        return userDepartMent;
    }

    /**
     * 获取用户所在医院名称
     *
     * @return
     */
    public String getUserHospital() {
        if (TextUtils.isEmpty(userHospital)) {
            userHospital = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_HOSPITAL, "");
        }
        return userHospital;
    }

    /**
     * 获取用户职称
     *
     * @return
     */
    public String getUserJobTitle() {
        if (TextUtils.isEmpty(userJobTitle)) {
            userJobTitle = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_JOBTITLE, "");
        }
        return userJobTitle;
    }

    /**
     * 获取聊天人ID
     *
     * @return
     */
    public int getChatterId() {
        chatterId = SharedPreferenceUtils.getInt(UserSpKeys.SP_CHATTER_ID, 0);
        return chatterId;
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String getUserHeadImg() {
        if (TextUtils.isEmpty(userHeadImg)) {
            userHeadImg = SharedPreferenceUtils.getString(UserSpKeys.SP_USER_HEAD_IMG, "");
        }
        return userHeadImg;
    }

    /**
     * 获取用户登录token
     *
     * @return
     */
    public String getAuthToken() {
        if (TextUtils.isEmpty(authToken)) {
            authToken = SharedPreferenceUtils.getString(UserSpKeys.SP_AUTH_TOKEN, "");
        }
        return authToken;
    }
}
