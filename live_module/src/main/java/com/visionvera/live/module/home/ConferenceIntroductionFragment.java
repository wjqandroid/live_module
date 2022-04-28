package com.visionvera.live.module.home;

import android.os.Bundle;
import android.widget.TextView;

import com.visionvera.live.R;
import com.visionvera.live.R2;
import com.visionvera.live.base.BaseFragment;
import com.visionvera.live.module.home.bean.IntentBean;

import butterknife.BindView;

/**
 * @Desc 大会介绍页面
 *
 * @Author yemh
 * @Date 2019/6/17 14:34
 */
public class ConferenceIntroductionFragment extends BaseFragment {
    @BindView(R2.id.tv_content)
    TextView tvContent;

    public static ConferenceIntroductionFragment newInstance() {
        return new ConferenceIntroductionFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_conferenceintroduction_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void loadData() {
        IntentBean vo = ((WatchLiveActivity) act).getIntentBean();
        if (vo != null) {
            tvContent.setText(vo.description);
        }
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
    }

}
