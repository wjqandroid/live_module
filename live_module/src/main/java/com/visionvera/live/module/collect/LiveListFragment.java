package com.visionvera.live.module.collect;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Desc 直播列表
 *
 * @Author yemh
 * @Date 2019/6/17 14:34
 *
 */
public class LiveListFragment extends BaseFragment {
    @BindView(R2.id.tab_collect_live)
    TabLayout tabLayoutLive;

    @BindView(R2.id.vp_collect_live)
    ViewPager mViewPagerLive;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabPageAdapter mAdapter;
    private LiveingFragment liveingFragment;
    private NoStartedLiveFragment noStartedLiveFragment;
    private OverLiveFragment overLiveFragment;

    public static LiveListFragment newInstance() {
        return new LiveListFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_live_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void loadData() {
        initViewPager();
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager(){
        Resources rs = getResources();
        titles.add(rs.getString(R.string.liveings));
        titles.add(rs.getString(R.string.no_starteds));
        titles.add(rs.getString(R.string.live_overs));

        liveingFragment = LiveingFragment.newInstance();
        noStartedLiveFragment = NoStartedLiveFragment.newInstance();
        overLiveFragment = OverLiveFragment.newInstance();

        fragmentList.add(liveingFragment);
        fragmentList.add(noStartedLiveFragment);
        fragmentList.add(overLiveFragment);

        mAdapter = new TabPageAdapter(getChildFragmentManager());
        mViewPagerLive.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPagerLive.setOffscreenPageLimit(1);
        setTabs(tabLayoutLive, titles);
        mViewPagerLive.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutLive));
        tabLayoutLive.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPagerLive.setCurrentItem(position);
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

    public void refresh(){
        refreshData(mViewPagerLive.getCurrentItem());
    }

    /**
     * 请求数据
     *
     * @param position
     */
    private void refreshData(int position) {
        switch (position) {
            case 0:
                if (liveingFragment != null) {
                    liveingFragment.refreshData();
                }
                break;

            case 1:
                if (noStartedLiveFragment != null) {
                    noStartedLiveFragment.refreshData();
                }
                break;

            case 2:
                if (overLiveFragment != null) {
                    overLiveFragment.refreshData();
                }
                break;

            default:
                break;
        }
    }
}
