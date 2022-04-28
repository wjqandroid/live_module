package com.visionvera.live.module.home;

import android.os.Bundle;
import android.widget.TextView;

import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.bean.ExpertBean;
import com.visionvera.live.module.home.bean.IntentBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.module.home.presenter.ExpertPresenter;
import com.visionvera.live.network.HttpInterface;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @Desc 课程内容页面
 * @Author yemh
 * @Date 2019-07-31 11:01
 */
public class CourseContentFragment extends BaseFragment implements Contract.IExpertIntroduction.IView  {
    @BindView(R2.id.tv_name)
    TextView tvName;

    @BindView(R2.id.tv_time)
    TextView tvTime;

    @BindView(R2.id.tv_hospital)
    TextView tvHospital;

    @BindView(R2.id.tv_content)
    TextView tvContent;
    private ExpertPresenter mPresenter;

    public static CourseContentFragment newInstance() {
        return new CourseContentFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coursecontent_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initAdapter();
        if (mPresenter == null) {
            mPresenter = new ExpertPresenter(this);
        }

    }

    @Override
    public void setListener() {
    }

    @Override
    public void loadData() {
        IntentBean vo = ((WatchLiveActivity) act).getIntentBean();
        if (vo != null) {
            setCouseContentData(vo);

            requestExpertIntroduction();
        }


    }

    /**
     * 请求专家简介
     */
    private void requestExpertIntroduction() {
        IntentBean vo = ((WatchLiveActivity) act).getIntentBean();

        if (vo != null) {
            Map<String, String> map = new HashMap<>();
            map.put(HttpInterface.ParamKeys.EXPERT_ID, vo.courseId + "");
            mPresenter.getExpertIntroduction(act, map, this);
        }
    }

    private void setCouseContentData(IntentBean vo) {
        tvName.setText(vo.title);
        tvHospital.setText(vo.hospital);
        tvContent.setText(vo.description);
        if (vo.playModel==1){
            tvTime.setText(vo.startTime);
        }else if (vo.playModel==2){
            tvTime.setText(vo.startTime + " - " + vo.endTime);
        }
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {

    }

    /**
     * 刷新数据
     */
    public void refreshData() {
    }



    @Override
    public void showexpertIntroductionResult(ResBean<ExpertBean> bean) {
        if (bean.isSuccess()) {

            ExpertBean result = bean.getResult();

            tvHospital.setText(result.getHospitalName());
        }
    }
}
