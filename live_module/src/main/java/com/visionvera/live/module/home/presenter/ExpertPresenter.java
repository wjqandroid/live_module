package com.visionvera.live.module.home.presenter;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.bean.ExpertBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.module.home.model.ExpertModel;
import com.visionvera.live.network.helper.ApiException;
import com.visionvera.live.network.helper.HttpRxObserver;

import java.util.Map;

/**
 * @Desc 专家简介的presenter实现类 * * @Author yemh * @Date 2019/4/15 17:35
 */
public class ExpertPresenter implements Contract.IExpertIntroduction.IPresenter {
    Contract.IExpertIntroduction.IModel mModel;
    Contract.IExpertIntroduction.IView mView;

    public ExpertPresenter(Contract.IExpertIntroduction.IView mView) {
        this.mView = mView;
        mModel = new ExpertModel();
    }

    @Override
    public void getExpertIntroduction(Context context, Map<String, String> params, LifecycleProvider provider) {
        mModel.expertIntroductionResult(params, provider, new HttpRxObserver<ResBean<ExpertBean>>(context, true) {
            @Override
            protected void onError(ApiException e) {
                mView.showError(e.getMessage());
            }

            @Override
            protected void onSuccess(ResBean<ExpertBean> data) {
                mView.showexpertIntroductionResult(data);
            }
        });
    }
}
