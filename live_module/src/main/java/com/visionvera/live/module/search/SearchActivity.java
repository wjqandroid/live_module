package com.visionvera.live.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.visionvera.library.arouter.ARouterConstant;
import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseActivity;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.WatchLiveActivity;
import com.visionvera.live.module.home.bean.CourseBean;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.module.search.adapter.SearchAdapter;
import com.visionvera.live.module.search.contract.SearchContract;
import com.visionvera.live.module.search.presenter.SearchPresenter;
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
 * @Desc 搜索页面
 * @Author yemh
 * @Date 2019/6/17 14:30
 */
@Route(path = ARouterConstant.Live.searchActivity)
public class SearchActivity extends BaseActivity implements OnClickListener, SearchContract.IView {
    @BindView(R2.id.et_query_condition)
    EditText etQueryCondition;

    @BindView(R2.id.iv_clear)
    ImageView ivClear;

    @BindView(R2.id.tv_search_cancle)
    TextView tvSearchCancle;

    @BindView(R2.id.swipeLayout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView;

    @BindView(R2.id.tv_nodata)
    TextView tvNoData;

    @BindView(R2.id.iv_back)
    ImageView iv_back;

    private SearchAdapter mAdapter;
    private SearchPresenter mPresenter;
    private int pageNum = 1;
    private boolean load = false;//是否正在请求
    private String searchVal;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = new SearchPresenter(this);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        ivClear.setOnClickListener(this);
        tvSearchCancle.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        etQueryCondition.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NEXT) {
                    //软盘搜索键
                    String text = etQueryCondition.getText().toString().trim();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtils.showShort(SearchActivity.this, getResources().getString(R.string.hint_search));
                    } else {
                        pageNum = 1;
                        searchCourse(text);
                    }
                    return true;
                }
                return false;
            }
        });

        etQueryCondition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    tvSearchCancle.setText(getResources().getString(R.string.cancle));
                } else {
                    tvSearchCancle.setText(getResources().getString(R.string.search));
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtils.isNetworkAvailable()) {
                    pageNum = 1;
                    mAdapter.setEnableLoadMore(false);
                    searchCourse(etQueryCondition.getText().toString().trim());
                } else {
                    mAdapter.setEnableLoadMore(true);
                    mRefreshLayout.setRefreshing(false);
                    ToastUtils.showShort(SearchActivity.this, R.string.please_check_your_network);
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (NetWorkUtils.isNetworkAvailable()) {
                    searchCourse(etQueryCondition.getText().toString().trim());
                } else {
                    ToastUtils.showShort(SearchActivity.this, R.string.please_check_your_network);
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

                Intent intent = new Intent(SearchActivity.this, WatchLiveActivity.class);
                intent.putExtra("IntentBean", intentBean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initToolBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.COLOR_WHITE_FFFFFF)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void loadData() {
        mRefreshLayout.setRefreshing(true);
        searchCourse("");
    }

    private void searchCourse(String searchVal) {
        Map<String, String> map = new HashMap<>();
        map.put(HttpInterface.ParamKeys.KEY_NAME, searchVal);
        map.put(HttpInterface.ParamKeys.PAGE_NUM, pageNum + "");
        map.put(HttpInterface.ParamKeys.PAGE_SIZE, "10");
        mPresenter.getSearchCourse(this, RxPartMapUtils.toRequestBodyOfMap(map), this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_clear) {
            etQueryCondition.setText("");
        } else if (id == R.id.tv_search_cancle) {
            String text = etQueryCondition.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                finish();
            } else {
                //搜索操作
                pageNum = 1;
                searchCourse(text);
            }
        }else if (id==R.id.iv_back){
            finish();
        }
    }

    @Override
    public void showSearchResult(ResBean<List<CourseBean>> bean) {
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
                        ToastUtils.showShort(SearchActivity.this, getString(R.string.brvah_load_end));
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
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }
}
