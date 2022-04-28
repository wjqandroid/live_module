package com.visionvera.live.module.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.tbruyelle.rxpermissions2.RxPermissions;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.bean.SHARE_MEDIA;
import com.visionvera.library.arouter.ARouterConstant;
import com.visionvera.library.util.GlideImageLoader;
import com.visionvera.library.util.OneClickUtils;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseActivity;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.collect.contract.CollectContract;
import com.visionvera.live.module.collect.presenter.CollectPresenter;
import com.visionvera.live.module.home.bean.IntentArouterBean;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.network.HttpInterface;
import com.visionvera.live.player.bean.VideoModel;
import com.visionvera.live.share.ShareBean;
import com.visionvera.live.share.ShareHandler;
import com.visionvera.live.share.ShareUtils;
import com.visionvera.live.utils.Loger;
import com.visionvera.live.utils.ToastUtils;
import com.visionvera.live.utils.WindowSoftModeAdjustResizeExecutor;
import com.visionvera.live.widget.PopupWindowCreator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

//import com.tencent.rtmp.TXLiveConstants;
/*import com.visionvera.live.player.SuperPlayerConst;
import com.visionvera.live.player.SuperPlayerGlobalConfig;
import com.visionvera.live.player.SuperPlayerModel;
import com.visionvera.live.player.SuperPlayerView;*/

/**
 * @Desc 直播页面
 * @Author yemh
 * @Date 2019-08-16 10:40
 * 2021.11.22   由于此处腾讯直播播放器与腾讯视频语音聊天冲突，解决方案：替换为节操播放器
 */
@Route(path = ARouterConstant.Live.watchLiveActivity)
public class WatchLiveActivity extends BaseActivity implements CollectContract.ICollect.IView,
        View.OnClickListener {
    @BindView(R2.id.tab_im)
    TabLayout mTabLayout;

    @BindView(R2.id.vp_im)
    ViewPager mViewPager;

    @BindView(R2.id.rl_ver_cover)
    RelativeLayout rlVerCover;

    @BindView(R2.id.iv_ver_cover)
    ImageView ivVerCover;

    @BindView(R2.id.tv_ver_desc)
    TextView tvVerDesc;

    @BindView(R2.id.rl_hor_cover)
    RelativeLayout rlHorCover;

    @BindView(R2.id.iv_hor_cover)
    ImageView ivHorCover;

    @BindView(R2.id.tv_hor_desc)
    TextView tvHorDesc;

    @BindView(R2.id.iv_collects)
    ImageView ivCollect;

    @BindView(R2.id.jc_videoplayer)
    JZVideoPlayerStandard jcVideoplayer;

    @Autowired(name = "intentBean")
    public IntentArouterBean intentArouterBean;
    private IntentBean intentBean = new IntentBean();

    //    private SuperPlayerView mSuperPlayerView;
    private PopupWindowCreator verPopWindow;
    private View verPopView;

    private PopupWindowCreator horPopWindow;
    private View horPopView;
    private CollectPresenter mPresenter;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabPageAdapter mAdapter;
//    private LiveInteractionFragment liveInteractionFragment;
    private ScheduleFragment scheduleFragment;
    private ConferenceIntroductionFragment conferenceIntroductionFragment;
    private CourseContentFragment courseContentFragment;
    private ExpertIntroductionFragment expertIntroductionFragment;
    private Unbinder mBind;
    private VideoModel videoModel = new VideoModel();

    private Disposable disposable;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private LoadingPopupView loadingPopupView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_watchlive_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        WindowSoftModeAdjustResizeExecutor.assistActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //初始化黄油刀控件绑定框架
        mBind = ButterKnife.bind(this);
        initResource();

    }

    @Override
    public void initToolBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.COLOR_BLACK_000000)
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    public void setListener() {
        ivCollect.setOnClickListener(this);
        rlVerCover.setOnClickListener(this);
        rlHorCover.setOnClickListener(this);
        ivVerCover.setOnClickListener(this);
        ivHorCover.setOnClickListener(this);
        tvVerDesc.setOnClickListener(this);
        tvHorDesc.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ImmersionBar.with(this)
                    .fitsSystemWindows(false)
                    .hideBar(BarHide.FLAG_HIDE_BAR)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .hideBar(BarHide.FLAG_SHOW_BAR)
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.COLOR_BLACK_000000)
                    .statusBarDarkFont(false)
                    .init();
        }
    }

    private void initResource() {


//        mSuperPlayerView = findViewById(R.id.superPlayerView);

//        mSuperPlayerView.setPlayerViewCallback(this);

        if (mPresenter == null) {
            mPresenter = new CollectPresenter(this);
        }


        if (intentArouterBean == null) {
            //从直播模块进入直播页面
            IntentBean bean = (IntentBean) getIntent().getSerializableExtra("IntentBean");
            if (bean != null) {
                intentBean = bean;
            }
        } else {
            //从心理模块进入直播页面
            intentBean.courseId = intentArouterBean.courseId;
            intentBean.description = intentArouterBean.description;
            intentBean.duration = intentArouterBean.duration;
            intentBean.endTime = intentArouterBean.endTime;
            intentBean.expertId = intentArouterBean.expertId;
            intentBean.hospital = intentArouterBean.hospital;
            intentBean.imageUrl = intentArouterBean.imageUrl;
            intentBean.liveStatus = intentArouterBean.liveStatus;
            intentBean.playModel = intentArouterBean.playModel;
            intentBean.startTime = intentArouterBean.startTime;
            intentBean.title = intentArouterBean.title;
            intentBean.type = intentArouterBean.type;
            intentBean.url = intentArouterBean.url;
            intentBean.webUrl = intentArouterBean.webUrl;
        }

        initViewPager();

        initSharePop();
//        initSuperVodGlobalSetting();

        loadingPopupView = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asLoading("正在加载中");


    }

    /**
     * 初始化分享弹窗
     */
    private void initSharePop() {
        verPopWindow = new PopupWindowCreator(this, 0);
        //此句用于处理华为等底部有
        verPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        verPopView = View.inflate(this, R.layout.pop_ver_share_layout, null);
        LinearLayout verShareQQ = verPopView.findViewById(R.id.ll_share_qq);
        verShareQQ.setOnClickListener(alertClick);
        LinearLayout verShareWX = verPopView.findViewById(R.id.ll_share_weixin);
        verShareWX.setOnClickListener(alertClick);
        LinearLayout verShareMoment = verPopView.findViewById(R.id.ll_share_moment);
        verShareMoment.setOnClickListener(alertClick);
        ImageView shareCancle = verPopView.findViewById(R.id.iv_share_cancle);
        shareCancle.setOnClickListener(alertClick);
        verPopWindow.setPopView(verPopView, 0);

        horPopWindow = new PopupWindowCreator(this, 1);
        //此句用于处理华为等底部有
        horPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        horPopView = View.inflate(this, R.layout.pop_hor_share_layout, null);
        RelativeLayout horShareQQ = horPopView.findViewById(R.id.rl_share_qq);
        horShareQQ.setOnClickListener(alertClick);
        RelativeLayout horShareWX = horPopView.findViewById(R.id.rl_share_weixin);
        horShareWX.setOnClickListener(alertClick);
        RelativeLayout horShareMoment = horPopView.findViewById(R.id.rl_share_moment);
        horShareMoment.setOnClickListener(alertClick);
        horPopWindow.setPopView(horPopView, 1);

        verPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                verPopWindow.setAlph(1.0f);
            }
        });
    }

    @Override
    public void loadData() {
       /* if (intentBean.isCollect) {
            mSuperPlayerView.updateCollect(true);
            ivCollect.setImageResource(R.drawable.ic_had_collect);
        } else {
            mSuperPlayerView.updateCollect(false);
            ivCollect.setImageResource(R.drawable.ic_collect);
        }*/

        videoModel.duration = (Integer.parseInt(intentBean.duration)) * 60;
        videoModel.title = intentBean.title;

        videoModel.videoURL = intentBean.url;


        /**
         * 判断是否直播
         */
        if (intentBean.playModel == 1) {
            playVideoModel(videoModel);
        } else {
            if (intentBean.liveStatus == 0) {
//                ivCollect.setVisibility(View.VISIBLE);
                setCover(0);
                interval();
            } else if (intentBean.liveStatus == 2) {
                setCover(1);
            } else if (intentBean.liveStatus == 3) {
                setCover(2);
            }
            playVideoModel(videoModel);
        }


    }

//    /**
//     * 初始化超级播放器全局配置
//     */
//    private void initSuperVodGlobalSetting() {
//        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
//        // 开启悬浮窗播放
//        prefs.enableFloatWindow = false;
//        // 播放器默认缓存个数
//        prefs.maxCacheItem = 5;
//        // 设置播放器渲染模式
//        prefs.enableHWAcceleration = true;
//        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
//    }

    private void playVideoModel(VideoModel videoModel) {
        //腾讯播放器
//        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
//        if (!TextUtils.isEmpty(videoModel.videoURL)) {
//            superPlayerModelV3.title = videoModel.title;
//            superPlayerModelV3.url = videoModel.videoURL;
//            superPlayerModelV3.qualityName = "原画";
//
//            superPlayerModelV3.multiURLs = new ArrayList<>();
//            if (videoModel.multiVideoURLs != null) {
//                for (VideoModel.VideoPlayerURL modelURL : videoModel.multiVideoURLs) {
//                    superPlayerModelV3.multiURLs.add(new SuperPlayerModel.SuperPlayerURL("rtmp://haoyisheng.51vision.com/live/zuozhongfei", modelURL.title));
//                }
//            }
//        } else if (!TextUtils.isEmpty(videoModel.fileid)) {
//            superPlayerModelV3.videoId = new SuperPlayerVideoId();
//            superPlayerModelV3.videoId.fileId = videoModel.fileid;
//        }
//        mSuperPlayerView.playWithModel(superPlayerModelV3);


        //替换为节操播放器
        //url、准备播放模式  标题名称
        jcVideoplayer.setUp(videoModel.videoURL
                , JZVideoPlayerStandard.CURRENT_STATE_PREPARING, videoModel.title);
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        //设置自动播放
        jcVideoplayer.startVideo();

    }

    private void play() {
        try {
            Date dt1 = df.parse(intentBean.startTime);
            Date dt2 = df.parse(intentBean.endTime);
            String currentTime = df.format(new Date());
            Date dt3 = df.parse(currentTime);

            long start = dt1.getTime();
            long end = dt2.getTime();
            long current = dt3.getTime();

            if (start > current) {//未开始
                setCover(0);
                return;
            } else {
                if (end > current) {
                    //直播中
                    rlVerCover.setVisibility(View.GONE);
                    rlHorCover.setVisibility(View.GONE);
                    ivCollect.setVisibility(View.GONE);
                } else {
                    //已结束
                    setCover(1);
//                    mSuperPlayerView.release();
//                    if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//                        mSuperPlayerView.resetPlayer();
//                    }
//                    return;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        playVideoModel(videoModel);
        if (disposable != null) {
            disposable.dispose();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
//            mSuperPlayerView.onResume();
//            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
//                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
//            }
//        }
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//            mSuperPlayerView.onPause();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mSuperPlayerView.release();
//        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//            mSuperPlayerView.resetPlayer();
//        }

        if (mBind != null) {
            mBind.unbind();
        }

        if (disposable != null) {
            disposable.dispose();
        }
    }

    private View.OnClickListener alertClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (OneClickUtils.isFastClick()) {
//                return;
//            }
//            ShareBean bean = new ShareBean();
//            bean.setUrl(HttpInterface.SHARE_URL + intentBean.courseId);
//            bean.setTitle(getString(R.string.evenote_title) + " | " + intentBean.title);
//            bean.setDesc(getString(R.string.share_content));
//            bean.setThumb(intentBean.imageUrl);
//            int id = v.getId();
//            if (id == R.id.ll_share_qq) {
//                verPopWindow.verDismiss();
//                bean.setShareMedia(SHARE_MEDIA.QQ);
//                checkPermissionToshare(bean);
//            } else if (id == R.id.ll_share_weixin) {
//                verPopWindow.verDismiss();
//                bean.setShareMedia(SHARE_MEDIA.WEIXIN);
//                checkPermissionToshare(bean);
//            } else if (id == R.id.ll_share_moment) {
//                verPopWindow.verDismiss();
//                bean.setShareMedia(SHARE_MEDIA.WEIXIN_CIRCLE);
//                checkPermissionToshare(bean);
//            } else if (id == R.id.iv_share_cancle) {
//                verPopWindow.verDismiss();
//            } else if (id == R.id.rl_share_qq) {
//                horPopWindow.horDismiss();
//                bean.setShareMedia(SHARE_MEDIA.QQ);
//                checkPermissionToshare(bean);
//            } else if (id == R.id.rl_share_weixin) {
//                horPopWindow.horDismiss();
//                bean.setShareMedia(SHARE_MEDIA.WEIXIN);
//                checkPermissionToshare(bean);
//            } else if (id == R.id.rl_share_moment) {
//                horPopWindow.horDismiss();
//                bean.setShareMedia(SHARE_MEDIA.WEIXIN_CIRCLE);
//                checkPermissionToshare(bean);
//            }
        }
    };

    private void share(ShareBean bean) {
        ShareUtils shareUtils = new ShareUtils(this);
        shareUtils.beginShare(bean, new ShareHandler() {
            @Override
            public void shareSuccess() {
                ToastUtils.showShort(WatchLiveActivity.this, "分享成功");
            }

            @Override
            public void shareFailed(String msg) {
                ToastUtils.showShort(WatchLiveActivity.this, "分享失败:" + msg);
            }

            @Override
            public void shareCancle() {
                ToastUtils.showShort(WatchLiveActivity.this, "分享取消");
            }
        });
    }

    public void checkPermissionToshare(final ShareBean bean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断该应用是否有写SD卡权限，如果没有再去申请
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    share(bean);
                                } else {
                                    ToastUtils.showShort(WatchLiveActivity.this, "分享取消");
                                }
                            }
                        });
            } else {
                share(bean);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 收藏请求
     */
    private void collectRequest() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.COURSE_ID, intentBean.courseId + "");
        mPresenter.getCollect(this, map, this);
    }

    /**
     * 取消收藏请求
     */
    private void cancleCollectRequest() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.COURSE_ID, intentBean.courseId + "");
        mPresenter.getCancelCollect(this, map, this);
    }

    @Override
    public void showCollectResult(ResBean bean) {
        if (bean.isSuccess()) {
            intentBean.isCollect = true;
//            mSuperPlayerView.updateCollect(true);
            ivCollect.setImageResource(R.drawable.ic_had_collect);
            ToastUtils.showShort(this, bean.getMsg());
        }
    }

    @Override
    public void showCancelCollectResult(ResBean bean) {
        if (bean.isSuccess()) {
            intentBean.isCollect = false;
//            mSuperPlayerView.updateCollect(false);
            ivCollect.setImageResource(R.drawable.ic_collect);
            ToastUtils.showShort(this, bean.getMsg());
        }
    }


    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        Resources rs = getResources();
//        titles.add(rs.getString(R.string.interactive));
//        liveInteractionFragment = LiveInteractionFragment.newInstance();
//        fragmentList.add(liveInteractionFragment);

        if (intentBean.type == 2) {
            titles.add(rs.getString(R.string.schedule));
            scheduleFragment = ScheduleFragment.newInstance();
            fragmentList.add(scheduleFragment);

            titles.add(rs.getString(R.string.conference_introduction));
            conferenceIntroductionFragment = ConferenceIntroductionFragment.newInstance();
            fragmentList.add(conferenceIntroductionFragment);
        } else {
            titles.add(rs.getString(R.string.course_content));
            courseContentFragment = CourseContentFragment.newInstance();
            fragmentList.add(courseContentFragment);

            titles.add(rs.getString(R.string.expert_introduction));
            expertIntroductionFragment = ExpertIntroductionFragment.newInstance();
            fragmentList.add(expertIntroductionFragment);
        }


        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(2);
        setTabs(mTabLayout, titles);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position);
                refreshData(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 设置tab
     */
    private void setTabs(TabLayout tabLayout, List<String> tabTitles) {
        for (int i = 0; i < tabTitles.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabTitles.get(i));
            tabLayout.addTab(tab);
        }
    }

    @Override
    public void showError(String errorMsg) {
    }

//    @Override
//    public void onStartFullScreenPlay() {
//    }
//
//    @Override
//    public void onStopFullScreenPlay() {
//    }
//
//    @Override
//    public void onClickFloatCloseBtn() {
//    }
//
//    @Override
//    public void onClickSmallReturnBtn() {
//
//        onBackPressed();
//    }
//
//    @Override
//    public void onStartFloatWindowPlay() {
//    }
//
//    @Override
//    public void onShareSmall() {
//        verPopWindow.verShow();
//    }
//
//    @Override
//    public void onShareLarge() {
//        horPopWindow.horShow();
//    }
//
//    @Override
//    public void onCollectSmall() {
//        toCollect();
//    }
//
//    @Override
//    public void onCollectLarge() {
//        toCollect();
//    }
//


//    @Override
//    public void onLinkError() {
//        try {
//            Date dt2 = df.parse(intentBean.endTime);
//            String currentTime = df.format(new Date());
//            Date dt3 = df.parse(currentTime);
//
//            long end = dt2.getTime();
//            long current = dt3.getTime();
//
//            if (end <= current) {
//                //已结束
//                setCover(1);
//                mSuperPlayerView.release();
//                if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
//                    mSuperPlayerView.resetPlayer();
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }

    /**
     * 去收藏
     */
    private void toCollect() {
        if (intentBean.isCollect) {
            cancleCollectRequest();
        } else {
            collectRequest();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_ver_cover || id == R.id.rl_hor_cover || id == R.id.iv_ver_cover || id == R.id.iv_hor_cover || id == R.id.tv_ver_desc || id == R.id.tv_hor_desc) {
            Loger.i("5555555555555555555555");
        } else if (id == R.id.iv_collects) {
            toCollect();
        }
    }

    private class TabPageAdapter extends FragmentPagerAdapter {
        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public long getItemId(int position) {
            //确保返回的值唯一即可
            return fragmentList.get(position).hashCode();
        }

        @Override
        public int getCount() {
            if (fragmentList != null && fragmentList.size() > 0) {
                return fragmentList.size();
            } else {
                return 0;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            //此Item不再显示
            return POSITION_NONE;
        }
    }

    /**
     * 请求数据
     *
     * @param position
     */
    private void refreshData(int position) {
        //原来有三个tab,现在把第一个聊天tab去掉了.所以0,1,2,现在对应-1,0,1
        //tab加1,变回虚拟的三个tab
        position = position + 1;
        switch (position) {
            case 0:
//                if (liveInteractionFragment != null) {
//                    liveInteractionFragment.refreshData();
//                }
                break;

            case 1:
                if (intentBean.type == 2) {
                    if (scheduleFragment != null) {
//                        scheduleFragment.refreshData();
                    }
                } else {
                    if (courseContentFragment != null) {
                        courseContentFragment.refreshData();
                    }
                }
                break;

            case 2:
                if (intentBean.type == 2) {
                    if (conferenceIntroductionFragment != null) {
                        conferenceIntroductionFragment.refreshData();
                    }
                } else {
                    if (expertIntroductionFragment != null) {

                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 获取IntentBean
     *
     * @return
     */
    public IntentBean getIntentBean() {
        return intentBean;
    }

    public void interval() {
        setCover(0);
        Observable.interval(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        try {
                            Date dt1 = df.parse(intentBean.startTime);
                            Date dt2 = df.parse(intentBean.endTime);
                            String currentTime = df.format(new Date());
                            Date dt3 = df.parse(currentTime);

                            long start = dt1.getTime();
                            long end = dt2.getTime();
                            long current = dt3.getTime();

                            if (start > current) {//未开始
                                setCover(0);
                            } else {
                                if (end > current) {
                                    //直播中
                                    rlVerCover.setVisibility(View.GONE);
                                    rlHorCover.setVisibility(View.GONE);
                                    ivCollect.setVisibility(View.GONE);
                                    play();
                                } else {
                                    setCover(1);
                                }
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 设置封面
     */
    private void setCover(int code) {
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rlVerCover.setVisibility(View.VISIBLE);
            rlHorCover.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(intentBean.imageUrl)) {
                GlideImageLoader.loadImage(this, intentBean.imageUrl, ivVerCover);
            }

            if (code == 0) {
                tvVerDesc.setText("即将开始 开始时间：" + intentBean.startTime);
            } else if (code == 1) {
                ivCollect.setVisibility(View.GONE);
                if (intentBean.type == 2) {
                    tvVerDesc.setText("直播已结束，更多视频请查看“日程安排”");
                } else {
                    tvVerDesc.setText("直播已结束，更多视频请查看“课程内容”相关推荐");
                }
            } else {
                tvVerDesc.setText("直播已暂停!");
            }
        } else {
            rlVerCover.setVisibility(View.GONE);
            rlHorCover.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(intentBean.imageUrl)) {
                GlideImageLoader.loadImage(this, intentBean.imageUrl, ivHorCover);
            }

            if (code == 0) {
                tvHorDesc.setText("即将开始 开始时间：" + intentBean.startTime);
            } else if (code == 1) {
                ivCollect.setVisibility(View.GONE);
                if (intentBean.type == 2) {
                    tvHorDesc.setText("直播已结束，更多视频请查看“日程安排”");
                } else {
                    tvHorDesc.setText("直播已结束，更多视频请查看“课程内容”相关推荐");
                }
            } else {
                tvVerDesc.setText("直播已暂停!");
            }
        }
    }

    /**
     * backPress函数判断了点击回退按钮的相应，
     * 如果全屏会退出全屏播放，如果不是全屏则会交给Activity
     */
    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 暂停 /失去焦点
     * Activity或者按Home键之后会视频就会releas(释放)
     */
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }

}