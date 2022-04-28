package com.visionvera.live.module.home.model;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.visionvera.live.base.bean.ResBean;
import com.visionvera.live.module.home.bean.ExpertBean;
import com.visionvera.live.module.home.contract.Contract;
import com.visionvera.live.network.HttpHelper;

import java.util.Map;

import io.reactivex.Observer;

/**
 * @Desc 专家简介的model实现类 * * @Author yemh * @Date 2019/4/15 17:42
 */
public class ExpertModel implements Contract.IExpertIntroduction.IModel {
    @Override
    public void expertIntroductionResult(Map<String, String> params, LifecycleProvider provider, Observer<ResBean<ExpertBean>> observer) {
        HttpHelper.getInstance().getExpertIntroduction(params, provider, observer);
    }
}
