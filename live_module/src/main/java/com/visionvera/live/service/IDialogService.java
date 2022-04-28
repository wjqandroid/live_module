package com.visionvera.live.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.visionvera.live.R;
import com.visionvera.live.constant.Globe;
import com.visionvera.live.listener.IDialogListener;
import com.visionvera.live.manager.UserManager;
//import com.visionvera.live.utils.ImUtils;
import com.visionvera.live.utils.ScreenUtils;
import com.visionvera.live.widget.CustomDialog;


/**
 * @Desc 用于处理全局dialog的service
 *
 * @Author yemh
 * @Date 2019-08-12 15:27
 */
public class IDialogService extends Service implements IDialogListener {
    private CustomDialog dialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DialogUtils.getInstance().setListener(this);//绑定
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }
    }

    /**
     * 下线通知
     *
     * @return
     */
    private CustomDialog offlineAppDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_offline_app_layout, null);
        dialog = new CustomDialog(Globe.mNowContext, R.style.Dialog);
        dialog.addContentView(view, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        TextView confirm = view.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                ImUtils.loginOutTecentIm();
                UserManager.getInstence().cleanUserInfo();

                //清空登录信息
            /*    SPUtils.getInstance(Constant.SP.userInfo).put(Constant.SP.isLogin, false);
                SPUtils.getInstance(Constant.SP.userInfo).put(Constant.SP.token, "");
                SPUtils.getInstance(Constant.SP.userInfo).put(Constant.SP.password, "");
*/
                //TODO 暂时注释掉
                /*//打开登录界面
                Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.disMiss();*/
            }
        });

        Window window = dialog.getWindow();
        int w = (int) ScreenUtils.dp2px(Globe.mNowContext, 250);
        int h = (int) ScreenUtils.dp2px(Globe.mNowContext, 130);
        window.setLayout(w, h);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void show() {
        offlineAppDialog().show();
    }

    @Override
    public void cancel() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
