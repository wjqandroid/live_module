package com.visionvera.live.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.visionvera.library.arouter.ARouterConstant;
import com.visionvera.library.util.GlideImageLoader;
import com.visionvera.library.util.OneClickUtils;
import com.visionvera.library.widget.banner.Banner;
import com.visionvera.library.widget.banner.BannerConfig;
import com.visionvera.library.widget.banner.listener.OnBannerListener;
import com.visionvera.library.widget.banner.transformer.StackTransformer;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseActivity;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.base.mvp.IBaseView;
import com.visionvera.live.module.collect.CollectHomeFragment;
import com.visionvera.live.module.home.bean.ChannelBean;
import com.visionvera.live.module.home.bean.CourseBean;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.module.home.presenter.BannerPresenter;
import com.visionvera.live.module.home.presenter.ChannelPresenter;
import com.visionvera.live.module.search.SearchActivity;
import com.visionvera.live.network.HttpInterface;
import com.visionvera.psychologist.account_module.util.AccountManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Desc 直播列表页面
 * @Author yemh
 * @Date 2019/4/12 16:35
 */
@Route(path = ARouterConstant.Live.liveHomeActivity)
public class LiveHomeFragment extends BaseActivity implements OnBannerListener, Contract.IChannel.IView, Contract.IBanner.IView, View.OnClickListener {

    @BindView(R2.id.tab_live)
    TabLayout mTabLayout;

    @BindView(R2.id.vp_live)
    ViewPager mViewPager;

    @BindView(R2.id.banner)
    Banner banner;

    @BindView(R2.id.iv_back)
    ImageView ivBack;

    @BindView(R2.id.iv_search)
    ImageView ivSearch;

    @BindView(R2.id.iv_collect)
    ImageView ivCollect;

    @BindView(R2.id.tv_title)
    TextView tvTitle;

    @BindView(R2.id.tv_error_net_reload)
    TextView tv_error_net_reload;

    @BindView(R2.id.net_error_layout)
    LinearLayout net_error_layout;

    private List<SourceFragment> fragmentList = new ArrayList<>();
    private List<ChannelBean> mChannelList = new ArrayList<>();
    private TabPageAdapter mAdapter;
    private List<CourseBean> hotCourse = new ArrayList<>();
    private ChannelPresenter channelPresenter;
    private BannerPresenter bannerPresenter;

    private static final String TAG = "LiveHomeFragment";

    /**
     * tab点击监听
     */
    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            TabLayout.Tab tab = mTabLayout.getTabAt(pos);
            if (pos == mViewPager.getCurrentItem()) {
                fragmentList.get(pos).refreshData();
            }
            if (tab != null) {
                tab.select();
            }
        }
    };
    private LoadingPopupView loadingPopupView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_home_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.COLOR_WHITE_FFFFFF)
                .init();
        if (channelPresenter == null) {
            channelPresenter = new ChannelPresenter(this);
        }

        if (bannerPresenter == null) {
            bannerPresenter = new BannerPresenter(this);
        }


        loadingPopupView = new XPopup.Builder(this)
                 .dismissOnBackPressed(false)
                 .dismissOnTouchOutside(false)
                 .asLoading("正在加载中");
    }

    @Override
    public void initToolBar() {
        ivSearch.setVisibility(View.INVISIBLE);
        //心理抗疫版暂时不要收藏课程功能
        ivCollect.setVisibility(View.GONE);
        tvTitle.setText(R.string.live_class);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
        tv_error_net_reload.setOnClickListener(this);
    }

    @Override
    public void loadData() {

        requestBanner();
        requestChannel();
    }

    /**
     * 获取课程分类接口请求
     */
    private void requestChannel() {
        Map<String, String> map = new HashMap<>();
        channelPresenter.getChannel(this, map, this);
    }

    /**
     * 获取轮播图接口请求
     */
    public void requestBanner() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.USERID,  AccountManager.getInstence().getGetAccountInfo().userId+"");
        map.put(HttpInterface.ParamKeys.TOKEN, AccountManager.getInstence().getGetAccountInfo().token);

        bannerPresenter.getBanner(this, map, this);
    }

    /**
     * 初始化viewPager
     *
     * @param data
     */
    private void initViewPager(List<ChannelBean> data) {
        for (int i = 0; i < data.size(); i++) {
            ChannelBean bean = data.get(i);
            fragmentList.add(SourceFragment.newInstance(bean.getId(), bean.getName(), bean.getName()));
        }
        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(1);
        setTabs(mTabLayout, getLayoutInflater(), data);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position);
                updateTabTextView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置粗体
     */
    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {
        TextView tvTab = tab.getCustomView().findViewById(R.id.tv_find_tab);
        if (isSelect) {
            tvTab.setTextSize(18);
        } else {
            tvTab.setTextSize(16);
        }
    }

    /**
     * 设置tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, List<ChannelBean> tabTitles) {
        for (int i = 0; i < tabTitles.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.live_tab_layout, null);
            tab.setCustomView(view);

            TextView tvTitle = view.findViewById(R.id.tv_find_tab);
            tvTitle.setTextSize(16);
            tvTitle.setText(tabTitles.get(i).getName());
            tabLayout.addTab(tab);

            if (i == 0) {
                tvTitle.setTextSize(18);
            }
            view.setTag(i);
            view.setOnClickListener(mTabOnClickListener);
        }
    }

    @Override
    public void OnBannerClick(int position) {
        IntentBean intentBean = new IntentBean();
        CourseBean bean = hotCourse.get(position);
        intentBean.courseId = bean.getCourseId();
        intentBean.playModel = bean.getPlayModel();
        intentBean.url = bean.getVideoUrl();
        intentBean.title = bean.getCourseName();
        intentBean.imageUrl = bean.getMasterPicUrl();
        intentBean.isCollect = bean.isCollectCourse();
        intentBean.type = bean.getCourseTypeType();

        intentBean.startTime = bean.getStartTime();
        intentBean.endTime = bean.getEndTime();
        intentBean.duration = bean.getDuration();
        intentBean.hospital = bean.getExpertHospital();
        intentBean.description = bean.getDescription();
        intentBean.expertId = bean.getExpertId();
        intentBean.liveStatus = bean.getLiveStatus();

        Intent intent = new Intent(LiveHomeFragment.this, WatchLiveActivity.class);
        intent.putExtra("IntentBean", intentBean);
        startActivity(intent);
    }

    @Override
    public void showError(String errorMsg) {
        super.showError(errorMsg);

        if (loadingPopupView!=null){
            loadingPopupView.dismiss();
        }

    }

    @Override
    public void showChannelResult(ResBean<List<ChannelBean>> bean,ResultType resultType) {
        if (loadingPopupView!=null){
            loadingPopupView.dismiss();
        }
        if (resultType==ResultType.SUCCESS){
            net_error_layout.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);

            if (bean.isSuccess()) {
                List<ChannelBean> data = bean.getResult();
                if (data != null && data.size() > 0) {
                    mChannelList.clear();
                    mChannelList.addAll(data);
                    initViewPager(mChannelList);
                }
            }
        }else if (resultType==ResultType.FAIL){
            net_error_layout.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
        }

    }

    @Override
    public void showBannerResult(ResBean<List<CourseBean>> bean, IBaseView.ResultType resultType) {
        if (loadingPopupView!=null){
            loadingPopupView.dismiss();
        }
        if (resultType== ResultType.SUCCESS){
            if (bean.isSuccess()) {
                hotCourse = bean.getResult();
                ArrayList<String> images = new ArrayList<>();
                for (int i = 0; i < hotCourse.size(); i++) {
                    CourseBean vo = hotCourse.get(i);
                    images.add(vo.getMasterPicUrl());
                }
                banner.setImages(images)
                        .setImageLoader(new GlideImageLoader())
                        .setOnBannerListener(this)
                        .setBannerAnimation(StackTransformer.class)
                        .start()
                        .updateBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            }
        }else if (resultType==ResultType.FAIL){

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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            ChannelBean bean = mChannelList.get(position);
            return bean.getName();
        }
    }

    @Override
    public void onClick(View v) {
        if (OneClickUtils.isFastClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_search) {
            startActivity(SearchActivity.class);
        } else if (id == R.id.iv_collect) {
            startActivity(CollectHomeFragment.class);
        }else if (id==R.id.tv_error_net_reload){
            loadingPopupView.show();
            requestChannel();
            requestBanner();
        }
    }

}
