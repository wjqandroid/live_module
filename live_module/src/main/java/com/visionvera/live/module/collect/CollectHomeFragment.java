package com.visionvera.live.module.collect;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.visionvera.library.arouter.ARouterConstant;
import com.visionvera.library.util.OneClickUtils;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Desc 收藏主页面
 * @Author yemh
 * @Date 2019/6/13 15:39
 */
@Route(path = ARouterConstant.Live.collectHomeActivity)
public class CollectHomeFragment extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.tv_live)
    TextView tvLive;

    @BindView(R2.id.tv_record)
    TextView tvRecord;

    @BindView(R2.id.vp_collect)
    ViewPager mViewPager;

    @BindView(R2.id.iv_back)
    ImageView ivBack;

    @BindView(R2.id.tv_title)
    TextView tvTitle;

    private TabPageAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private LiveListFragment liveListFragment;
    private RecordListFragment recordListFragment;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_home_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.my_collects);
    }

    @Override
    public void setListener() {
        tvLive.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.COLOR_WHITE_FFFFFF)
                .init();
        initViewPager();
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        liveListFragment = LiveListFragment.newInstance();
        recordListFragment = RecordListFragment.newInstance();

        fragmentList.add(liveListFragment);
        fragmentList.add(recordListFragment);

        mAdapter = new TabPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    liveListFragment.refresh();
                }else{
                    recordListFragment.refreshData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

    @Override
    public void onClick(View v) {
        if (OneClickUtils.isFastClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }else if (id == R.id.tv_live){
            tvLive.setTextColor(getResources().getColor(R.color.COLOR_WHITE_FFFEFE));
            tvLive.setBackgroundColor(getResources().getColor(R.color.COLOR_BLUE_21A3F0));
            tvRecord.setTextColor(getResources().getColor(R.color.COLOR_BLACK_333333));
            tvRecord.setBackground(getResources().getDrawable(R.drawable.bg_solid_ffffff_stroke_1_cccccc));
            mViewPager.setCurrentItem(0);
        }else if (id == R.id.tv_record){
            tvLive.setTextColor(getResources().getColor(R.color.COLOR_BLACK_333333));
            tvLive.setBackground(getResources().getDrawable(R.drawable.bg_solid_ffffff_stroke_1_cccccc));
            tvRecord.setTextColor(getResources().getColor(R.color.COLOR_WHITE_FFFFFF));
            tvRecord.setBackgroundColor(getResources().getColor(R.color.COLOR_BLUE_21A3F0));
            mViewPager.setCurrentItem(1);
        }
    }

    public void refreshData(){
        liveListFragment.refresh();
    }
}
