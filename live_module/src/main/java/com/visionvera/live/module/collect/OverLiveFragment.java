package com.visionvera.live.module.collect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.collect.adapter.OverLiveAdapter;
import com.visionvera.live.module.collect.contract.CollectContract;
import com.visionvera.live.module.collect.presenter.CollectListPresenter;
import com.visionvera.live.module.home.WatchLiveActivity;
import com.visionvera.live.module.home.bean.CourseBean;
import com.visionvera.live.module.home.bean.IntentBean;
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

/**
 * @Desc 已结束
 *
 * @Author yemh
 * @Date 2019/6/17 14:34
 */
public class OverLiveFragment extends BaseFragment implements CollectContract.ICollectList.IView {
    @BindView(R2.id.swipeLayout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R2.id.rv_live)
    RecyclerView mRecyclerView;

    @BindView(R2.id.tv_nodata)
    TextView tvNoData;

    private int pageNum = 1;
    private OverLiveAdapter mAdapter;
    private CollectListPresenter mPresenter;

    public static OverLiveFragment newInstance() {
        return new OverLiveFragment();
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
        mPresenter = new CollectListPresenter(this);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(act));
        mAdapter = new OverLiveAdapter(act);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 请求直播列表
     */
    private void requestLiveList() {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.PLAY_MODEL, "5");
        map.put(HttpInterface.ParamKeys.PAGE_NUM, pageNum + "");
        map.put(HttpInterface.ParamKeys.PAGE_SIZE, "10");
        mPresenter.getCollectList(act, RxPartMapUtils.toRequestBodyOfMap(map), this);
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (mAdapter != null) {
            mRefreshLayout.setRefreshing(true);
            pageNum = 1;
            requestLiveList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void showCollectListResult(ResBean<List<CourseBean>> bean) {
        if (bean.isSuccess()) {
            List<CourseBean> dataList = bean.getResult();
            if (pageNum == 1) {
                mAdapter.setEnableLoadMore(true);
                mRefreshLayout.setRefreshing(false);
                if (dataList != null) {
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
                tvNoData.setVisibility(View.GONE);
            }else{
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }
}
