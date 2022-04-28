package com.visionvera.live.module.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.visionvera.library.widget.view.CircleImageView;
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
 * @Desc 专家简介页面
 * @Author yemh
 * @Date 2019/6/17 14:34
 */
public class ExpertIntroductionFragment extends BaseFragment implements Contract.IExpertIntroduction.IView {

    private static final String TAG = "ExpertIntroductionFragm";

    @BindView(R2.id.iv_expert_head)
    CircleImageView ivExpertHead;

    @BindView(R2.id.tv_expert_name)
    TextView tvExpertName;

    @BindView(R2.id.tv_hospital)
    TextView tvHospital;

    @BindView(R2.id.tv_expert_desc)
    TextView tvExpertDesc;

    @BindView(R2.id.introduction_container)
    LinearLayout introduction_container;

    @BindView(R2.id.introduction_linearlayout)
    LinearLayout introduction_linearlayout;

    private ExpertPresenter mPresenter;
    private boolean isRequest = false;

    private String[] names={"樊富珉","姜长青","西英俊","张起淮"};
    private String[] profressions={"知名心理学专家","知名心理学专家","知名心理学专家","知名法学专家"};
    private String[] contents={"        中国心理卫生协会常务理事，清华大学教授、博士生导师，中国科协全国临床与咨询心理学首席科学传播专家，2019健康中国十大年度人物，教育部普通高等学校学生心理健康教育专家指导委员会委员，国家卫生健康委员会精神医学与心理健康专家委员会委员。",
            "        中国心理卫生协会常务理事、中国心理卫生协会心理咨询师专业委员会主任委员，主任心理师，首都医科大学附属北京安定医院临床心理科主任，首都医科大学临床心理学系系务委员。中国心理卫生协会危机干预专业委员会常务委员，中国心理卫生协会心理评估专业委员会委员。",
            "        国家卫健委防控组驻武汉市心理救援专家工作队副组长，中国心理卫生协会理事，中国心理卫生协会青年工作委员会副主任委员，北京安定医院临床心理中心病区主任。",
            "        北京市人大常委会立法咨询专家、中国国际经济贸易仲裁委资深仲裁员、中国政法大学航空与空间法研究中心研究员、北京理工大学兼职法学教授。多次参加“焦点访谈”、“东方时空”、“大家看法”、“今日说法”、“法制在线”栏目。"};

    public static ExpertIntroductionFragment newInstance() {
        return new ExpertIntroductionFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_expertintroduction_layout;
    }

    @Override
    public void init(Bundle savedInstanceState) {
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

        if (vo.courseId==28 ||vo.courseId==29 ||vo.title.contains("构建积极心态")){
            introduction_linearlayout.setVisibility(View.GONE);
            tvExpertDesc.setVisibility(View.GONE);

            for (int i = 0; i < 4; i++) {
                View introductionItemView=LayoutInflater.from(getActivity()).inflate(R.layout.expert_introduction_item,null);
                TextView nameTV=introductionItemView.findViewById(R.id.expert_introduction_item_name);
                TextView profressionTV=introductionItemView.findViewById(R.id.expert_introduction_item_profression);
                TextView contentTV=introductionItemView.findViewById(R.id.expert_introduction_item_content);
                nameTV.setText(names[i]);
                profressionTV.setText(profressions[i]);
                contentTV.setText(contents[i]);
                introduction_container.addView(introductionItemView);
            }

        }else{
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
//            map.put(HttpInterface.ParamKeys.EXPERT_ID, vo.expertId + "");
            Log.e(TAG, "requestExpertIntroduction: "+vo.courseId );
            map.put(HttpInterface.ParamKeys.EXPERT_ID, vo.courseId + "");
            mPresenter.getExpertIntroduction(act, map, this);
        }
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (!isRequest) {
            requestExpertIntroduction();
        }
    }

    @Override
    public void showexpertIntroductionResult(ResBean<ExpertBean> bean) {
        if (bean.isSuccess()) {
             setExportIntroductionData(bean);

        }
    }

    public void setExportIntroductionData(ResBean<ExpertBean> bean) {
        isRequest = true;
        ExpertBean result = bean.getResult();
        tvExpertName.setText(result.getName());
        tvHospital.setText(result.getHospitalName()+"  |  "+result.getDeptName());
        tvExpertDesc.setText(result.getIntroduction());
        String headUrl = result.getProfilePicture();
        if (!TextUtils.isEmpty(headUrl)) {
            Glide.with(this).load(headUrl)
                    .placeholder(R.drawable.ic_head_default)
                    .dontAnimate()
                    .into(ivExpertHead);
        }

    }


}
