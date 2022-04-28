package com.visionvera.live.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.visionvera.library.widget.decoration.SpaceItemDecoration;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.adapter.SourceAdapter;
import com.visionvera.live.module.home.bean.CourseBean;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.module.home.presenter.LivePresenter;
import com.visionvera.live.network.HttpInterface;
import com.visionvera.live.utils.NetWorkUtils;
import com.visionvera.live.utils.RxPartMapUtils;
import com.visionvera.live.utils.ToastUtils;
import com.visionvera.live.widget.recycler.BaseQuickAdapter;
import com.visionvera.live.widget.recycler.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Desc 直播列表页面
 * @Author yemh
 * @Date 2019/4/12 16:35
 */
public class SourceFragment extends BaseFragment implements Contract.ILive.IView {
    @BindView(R2.id.swipeLayout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R2.id.rv_live)
    RecyclerView mRecyclerView;

    @BindView(R2.id.empty_data_layout)
    LinearLayout empty_data_layout;

    @BindView(R2.id.net_error_layout)
    LinearLayout net_error_layout;

    private static final String CHANNEL_ID = "SourceFragment.ChannelId";
    private static final String CHANNEL_NAME = "SourceFragment.ChannelName";
    private static final String CHANNEL_TYPE = "SourceFragment.ChannelType";
    private int channelId;
    private boolean isCreated;

    private int pageNum = 1;
    private SourceAdapter mAdapter;
    private LivePresenter mPresenter;

    public static SourceFragment newInstance(int channelId, String channelName, String channelType) {
        SourceFragment fragment = new SourceFragment();
        Bundle args = new Bundle();
        args.putInt(CHANNEL_ID, channelId);
        args.putString(CHANNEL_NAME, channelName);
        args.putString(CHANNEL_TYPE, channelType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channelId = getArguments().getInt(CHANNEL_ID);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }


    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_live_child_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();

    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtils.isNetworkAvailable()) {
                    pageNum = 1;
                    mAdapter.setEnableLoadMore(false);
                    requestLiveList();
                    ((LiveHomeFragment)act).requestBanner();
                } else {
                    mAdapter.setEnableLoadMore(true);
                    mRefreshLayout.setRefreshing(false);
                    ToastUtils.showShort(act, R.string.please_check_your_network);
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (NetWorkUtils.isNetworkAvailable()) {
                    requestLiveList();
                } else {
                    ToastUtils.showShort(act, R.string.please_check_your_network);
                }
            }
        }, mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {

                Log.e(TAG, "onSimpleItemClick:数据有多少条： "+mAdapter.getData().size());

                IntentBean intentBean = new IntentBean();
                CourseBean bean = mAdapter.getItem(position);
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

                Intent intent = new Intent(getContext(), WatchLiveActivity.class);
                intent.putExtra("IntentBean", intentBean);
                startActivity(intent);

            }
        });
    }

    @Override
    public void loadData() {
        if (mPresenter == null) {
            mPresenter = new LivePresenter(this);
        }

        requestLiveList();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(act));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter = new SourceAdapter(act);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 请求直播列表
     */
    private void requestLiveList() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.TYPE_ID, channelId + "");
        map.put(HttpInterface.ParamKeys.PAGE_NUM, pageNum + "");
        map.put(HttpInterface.ParamKeys.PAGE_SIZE, "10");
        mPresenter.getSource(act, RxPartMapUtils.toRequestBodyOfMap(map), this);
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (mAdapter != null) {
            pageNum = 1;
            mRefreshLayout.setRefreshing(true);
            requestLiveList();
        }
    }

    @Override
    public void showSourceResult(ResBean<List<CourseBean>> bean) {

        if (bean.isSuccess()) {
            List<CourseBean> dataList = bean.getResult();
            if (pageNum == 1) {
                mAdapter.setEnableLoadMore(true);
                mRefreshLayout.setRefreshing(false);
                if (dataList != null && dataList.size() > 0) {
                    mAdapter.setNewData(dataList);
                    if (dataList.size() > 0) {
                        pageNum++;
                    }
                    if (dataList.size() < 10) {
                        mAdapter.loadMoreEnd(true);
                    }
                }
            } else {
                mAdapter.loadMoreComplete();
                if (dataList != null) {
                    if (dataList.size() > 0) {
                        mAdapter.addData(dataList);
                        pageNum++;
                    } else {
                        mAdapter.loadMoreEnd(false);
                        ToastUtils.showShort(act, getString(R.string.brvah_load_end));
                    }
                }

            }
        }
        noData();
    }

    /**
     * 设置显示暂无数据提示
     */
    private void noData() {
        if (mAdapter != null) {
            int itemCount = mAdapter.getItemCount();
            if (itemCount > 0) {
                mRefreshLayout.setVisibility(View.VISIBLE);
                empty_data_layout.setVisibility(View.GONE);
                net_error_layout.setVisibility(View.GONE);
            } else {
                mRefreshLayout.setVisibility(View.GONE);
                empty_data_layout.setVisibility(View.VISIBLE);
                net_error_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showError(String errorMsg) {
        super.showError(errorMsg);
        if (mRefreshLayout != null){
            mRefreshLayout.setRefreshing(false);
        }

        net_error_layout.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.GONE);
    }

    @OnClick({R2.id.tv_error_net_reload,R2.id.tv_data_empty_reload})
    public void onClick(View view){
        if (view.getId()==R.id.tv_error_net_reload || view.getId()==R.id.tv_data_empty_reload){
            requestLiveList();
        }
    }
}
