package com.visionvera.live.module.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.adapter.MettingScheduleAdapter;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.module.home.bean.MettingScheduleBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.module.home.presenter.MettingSchedulePresenter;
import com.visionvera.live.network.HttpInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Desc 日程安排页面
 * @Author yemh
 * @Date 2019-07-31 11:01
 */
public class ScheduleFragment extends BaseFragment implements Contract.IMettingSchedule.IView {
    @BindView(R2.id.rv_schedule)
    RecyclerView mRecyclerView;

    private TextView tvName;
    private TextView tvTime;

    private MettingSchedulePresenter mPresenter;
    private MettingScheduleAdapter mAdapter;
    private boolean isRequest = false;
    private TextView tv_content;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_schedule_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = new MettingSchedulePresenter(this);
        }
        initRecyclerView();
    }

    @Override
    public void setListener() {
    }

    @Override
    public void loadData() {
        IntentBean vo = ((WatchLiveActivity) act).getIntentBean();
        if (vo != null) {
            tvName.setText(vo.title);
            if (vo.playModel==1){
                tvTime.setText(vo.startTime);
            }else if (vo.playModel==2){
                tvTime.setText(vo.startTime + " - " + vo.endTime);
            }
            tv_content.setText(TextUtils.isEmpty(vo.description)?"":vo.description);
        }

        requestMettingSchedule();
    }

    /**
     * 初始化adapter
     */
    private void initRecyclerView() {
        View scheduleHead = LayoutInflater.from(act).inflate(R.layout.layout_schedule_head, null);
        tvName = scheduleHead.findViewById(R.id.tv_name);
        tvTime = scheduleHead.findViewById(R.id.tv_time);
        tv_content = scheduleHead.findViewById(R.id.tv_content);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(act));
        mAdapter = new MettingScheduleAdapter(act);
        mAdapter.addHeaderView(scheduleHead, 0);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 请求日程安排
     */
    private void requestMettingSchedule() {
        IntentBean vo = ((WatchLiveActivity) act).getIntentBean();
        if (vo != null) {
            Map<String, String> map = new HashMap<>();
            map.put(HttpInterface.ParamKeys.METTING_ID, vo.courseId + "");
            mPresenter.getMettingSchedule(act, map, this);
        }
    }

    /**
     * 刷新数据
     */
   /* public void refreshData() {
        if (!isRequest) {
            requestMettingSchedule();
        }
    }*/

    @Override
    public void showMettingScheduleResult(ResBean<List<MettingScheduleBean>> bean) {
        if (bean.isSuccess()) {
            isRequest = true;
            List<MettingScheduleBean> result = bean.getResult();
            if (result != null && result.size() > 0) {
                mAdapter.addData(result);
            }
        }
    }
}
