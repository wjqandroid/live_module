//package com.visionvera.live.module.home;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.google.gson.Gson;
////import com.tencent.imsdk.TIMConversation;
////import com.tencent.imsdk.TIMConversationType;
////import com.tencent.imsdk.TIMElem;
////import com.tencent.imsdk.TIMElemType;
////import com.tencent.imsdk.TIMManager;
////import com.tencent.imsdk.TIMMessage;
////import com.tencent.imsdk.TIMMessageListener;
////import com.tencent.imsdk.TIMTextElem;
////import com.tencent.imsdk.v2.V2TIMManager;
//import com.visionvera.live.R;
////import com.visionvera.live.R2;
//import com.visionvera.live.base.BaseFragment;
//import com.visionvera.live.base.bean.ResBean;
//import com.visionvera.live.constant.Globe;
//import com.visionvera.live.manager.UserManager;
//import com.visionvera.live.module.home.adapter.ChatAdapter;
//import com.visionvera.live.module.home.bean.AddChatterBean;
//import com.visionvera.live.module.home.bean.ChatMessageBean;
//import com.visionvera.live.module.home.bean.GroupChatterBean;
//import com.visionvera.live.module.home.bean.GroupIdBean;
//import com.visionvera.live.module.home.bean.RequestAddChatterBean;
//import com.visionvera.live.module.home.bean.RequestChatMessageBean;
//import com.visionvera.live.module.home.bean.RequestGroupChatterBean;
//import com.visionvera.live.module.home.bean.SendBean;
//import com.visionvera.live.module.home.contract.Contract;
//import com.visionvera.live.module.home.presenter.ImPresenter;
//import com.visionvera.live.network.HttpInterface;
//import com.visionvera.live.network.im.JWebSocketClient;
//import com.visionvera.live.utils.Loger;
//import com.visionvera.live.utils.RxPartMapUtils;
//import com.visionvera.live.utils.SharedPreferenceUtils;
//import com.visionvera.live.utils.ToastUtils;
//import com.visionvera.live.widget.recycler.SmoothScrollLayoutManager;
//import com.visionvera.psychologist.account_module.R2;
//
//import org.java_websocket.handshake.ServerHandshake;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//
///**
// * @Desc 直播互动页面
// * @Author yemh
// * @Date 2019-07-31 11:01
// */
//public class LiveInteractionFragment extends BaseFragment implements Contract.IIm.IView, View.OnClickListener {
//    @BindView(R2.id.chat_swipe)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//
//    @BindView(R2.id.chat_recycler_view)
//    RecyclerView mRecyclerView;
//
//    @BindView(R2.id.cb_question)
//    CheckBox mCheckBox;
//
//    @BindView(R2.id.et_im_content)
//    EditText etImContent;
//
//    @BindView(R2.id.tv_send)
//    TextView tvSend;
//
//    @BindView(R2.id.tv_login_tip)
//    TextView tvLoginTip;
//
//    private ImPresenter mPresenter;
//    private int groupId = 0;
//    private int chatterId = 0;
//    private int start = 0;
//    private static final long HEART_BEAT_RATE = 30 * 1000;//每隔30秒进行一次对长连接的心跳检测
//    private static Handler mHandler = new Handler();
//    private JWebSocketClient client;
//    private ChatAdapter mAdapter;
//    private HashMap<Integer, GroupChatterBean> chattersHashMap;
//    private Gson mGson;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public static LiveInteractionFragment newInstance() {
//        return new LiveInteractionFragment();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_liveinteraction_layout;
//    }
//
//    @Override
//    public void init(Bundle savedInstanceState) {
//        initAdapter();
//    }
//
//    @Override
//    public void setListener() {
//        tvSend.setOnClickListener(this);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestHistoryMessage();
//            }
//        });
//    }
//
//    @Override
//    public void loadData() {
//        mGson = new Gson();
//        mPresenter = new ImPresenter(this);
//        requestGroupId();
//        boolean isLogin = SharedPreferenceUtils.getBoolean(Globe.SP_ISLOGIN, false);
//        if (!isLogin) {
//            tvLoginTip.setText(R.string.im_login_tip);
//        }
//    }
//
//    /**
//     * 初始化adapter
//     */
//    private void initAdapter() {
//        mRecyclerView.setLayoutManager(new SmoothScrollLayoutManager(act));
//        mAdapter = new ChatAdapter(act);
//        mRecyclerView.setAdapter(mAdapter);
//    }
//
//    /**
//     * 请求群ID
//     */
//    public void requestGroupId() {
//        chatterId = UserManager.getInstence().getChatterId();
//        mAdapter.setChatterId(chatterId);
//        Map<String, Integer> map = new HashMap<>();
//        map.put(HttpInterface.ParamKeys.BUSINESS_ID, ((WatchLiveActivity) act).getIntentBean().courseId);
//        map.put(HttpInterface.ParamKeys.SYSTEM_ID, 21);
//        mPresenter.getGroupByBusinessId(act, map, this);
//    }
//
//    /**
//     * 请求加入群聊
//     */
//    private void requestAddChatter() {
//        RequestAddChatterBean bean = new RequestAddChatterBean();
//        bean.setGroupId(groupId);
//        bean.setChatterId(chatterId);
//        bean.setName(UserManager.getInstence().getUserPhone());
//        bean.setRoleId(2);
//        bean.setStatus(1);
//        String json = new Gson().toJson(bean);
//        mPresenter.addChatter(act, RxPartMapUtils.toRequestBodyOfString(json), this);
//    }
//
//    /**
//     * 群成员请求
//     */
//    public void requestGroupChatter() {
//        RequestGroupChatterBean bean = new RequestGroupChatterBean();
//        bean.setSize(10000);
//        bean.setStart(start);
//        RequestGroupChatterBean.WhereBean vo = new RequestGroupChatterBean.WhereBean();
//        vo.setGroupId(groupId);
//        bean.setWhere(vo);
//        String json = new Gson().toJson(bean);
//        mPresenter.getGroupChatter(act, RxPartMapUtils.toRequestBodyOfString(json), this);
//    }
//
//    /**
//     * 历史消息请求
//     */
//    private void requestHistoryMessage() {
//        RequestChatMessageBean bean = new RequestChatMessageBean();
//        bean.setSize(100);
//        bean.setStart(start);
//        RequestChatMessageBean.WhereBean vo = new RequestChatMessageBean.WhereBean();
//        vo.setSystemId(21);
//        vo.setReceiverId(groupId);
//        vo.setReceiverType(2);
//        bean.setWhere(vo);
//        String json = new Gson().toJson(bean);
//        mPresenter.getHistoryMessage(act, HttpInterface.IMApi.GET_HISTORY, RxPartMapUtils.toRequestBodyOfString(json), this);
//    }
//
//    /**
//     * 刷新数据
//     */
//    public void refreshData() {
//
//    }
//
//    @Override
//    public void showGroupByBusinessIdResult(ResBean<GroupIdBean> bean) {
//        if (bean.isSuccess()) {
//            GroupIdBean result = bean.getResult();
//            if (result != null) {
//                groupId = result.getId();
//                requestAddChatter();
//            }
//        }else{
//            tvLoginTip.setText(R.string.no_groupid);
//        }
//    }
//
//    @Override
//    public void showAddChatterResult(ResBean<AddChatterBean> bean) {
//        if (bean.isSuccess()) {
//            AddChatterBean result = bean.getResult();
//            if (result != null) {
//                requestGroupChatter();
//            }
//        }
//    }
//
//    @Override
//    public void showHistoryMessageResult(ResBean<List<ChatMessageBean>> bean) {
//        mSwipeRefreshLayout.setRefreshing(false);
//        if (bean.isSuccess()) {
//            List<ChatMessageBean> result = bean.getResult();
//            if (result != null && result.size() > 0) {
//                Collections.reverse(result);
//                if (start == 0) {
//                    //给集合进行倒序处理
//                    mAdapter.addData(result);
//                    if (mAdapter.getItemCount() > 0) {
//                        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
//                    }
//                } else {
//                    mAdapter.addData(0, result);
//                }
//                start = start + result.size();
////                start = start + result.size() + 1;
//            }
//        }
//    }
//
//    @Override
//    public void showGroupChatterResult(ResBean<List<GroupChatterBean>> bean) {
//        if (bean.isSuccess()) {
//            List<GroupChatterBean> result = bean.getResult();
//            if (result != null) {
//                chattersHashMap = new HashMap<>();
//                for (int i = 0; i < result.size(); i++) {
//                    GroupChatterBean vo = result.get(i);
//                    chattersHashMap.put(vo.getChatterId(), vo);
//                }
//                mAdapter.setChattersHashMap(chattersHashMap);
//                tvLoginTip.setText("");
//                ToastUtils.showShort(act, R.string.im_login_success);
//                mSwipeRefreshLayout.setRefreshing(true);
//                requestHistoryMessage();
//                initSocketClient();
//                mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
////                receiveTecentMessage();
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.tv_send){
//            String content = etImContent.getText().toString().trim();
//            if (TextUtils.isEmpty(content)) {
//                ToastUtils.showShort(act, R.string.tip_question);
//            } else {
//                SendBean bean = new SendBean();
//                if (mCheckBox.isChecked()) {
//                    bean.setTypeId(101);
//                } else {
//                    bean.setTypeId(1);
//                }
//                bean.setSystemId(21);
//                bean.setSenderId(chatterId);
//                bean.setContent(content);
//                SendBean.ReceiversBean vo = new SendBean.ReceiversBean();
//                vo.setReceiverId(groupId);
//                vo.setReceiverType(2);
//                ArrayList<SendBean.ReceiversBean> data = new ArrayList<>();
//                data.add(vo);
//                bean.setReceivers(data);
//                String json = new Gson().toJson(bean);
//                sendMessage(json);
//            }
//            etImContent.setText(null);
//        }
//    }
//
//    private Handler msgHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (mAdapter != null && mRecyclerView != null) {
//                ChatMessageBean bean = (ChatMessageBean) msg.obj;
//                int typeId = bean.getTypeId();//101提问 11上线 12下线
//                if (typeId == 11) {
//
//                } else if (typeId == 12) {
//
//                } else {
//                    mAdapter.addData(bean);
//                    if (mAdapter.getItemCount() > 0) {
//                        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
//                    }
//                }
//            }
//        }
//    };
//
//    /**
//     * 通过腾讯IM接收消息
//     */
////    private void receiveTecentMessage() {
////
////
////        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
////            @Override
////            public boolean onNewMessages(List<TIMMessage> data) {
////                if (data != null && data.size() > 0) {
////                    for (int i = 0; i < data.size(); i++) {
////                        TIMMessage msg = data.get(i);
////                        TIMConversation conversation = msg.getConversation();
////                        TIMConversationType type = conversation.getType();
////                        if (type == TIMConversationType.Group) {
////                            if (msg.getElementCount() > 0) {
////                                TIMElem ele = msg.getElement(0);
////                                TIMElemType t = ele.getType();
////                                if (t == TIMElemType.Text) {
////                                    TIMTextElem txtEle = (TIMTextElem) ele;
////                                    String text = txtEle.getText();
////                                    if (!TextUtils.isEmpty(text)) {
////                                        ChatMessageBean bean = mGson.fromJson(text, ChatMessageBean.class);
////                                        Message message = new Message();
////                                        message.obj = bean;
////                                        message.what = 0;
////                                        msgHandler.sendMessage(message);
////                                    }
////                                }
////                            }
////                        }
////                    }
////                }
////                return false;
////            }
////        });
////    }
//
//    /**
//     * 处理websocket
//     */
//    private void initSocketClient() {
//        URI uri = URI.create(HttpInterface.WS);
//        client = new JWebSocketClient(uri) {
//            @Override
//            public void onMessage(String message) {
//                Loger.e("收到的消息：" + message);
//                if (!TextUtils.isEmpty(message)) {
//                    ChatMessageBean bean = new Gson().fromJson(message, ChatMessageBean.class);
//                    Message msg = new Message();
//                    msg.obj = bean;
//                    msg.what = 0;
//                    msgHandler.sendMessage(msg);
//                }
//            }
//
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                super.onOpen(handshakedata);
//                Loger.e("WS连接成功");
//                try {
//                    JSONObject obj = new JSONObject();
//                    obj.put("typeId", 11);
//                    obj.put("systemId", 21);
//                    obj.put("content", UserManager.getInstence().getUserId());
//                    sendMessage(obj.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        connect();
//    }
//
//    /**
//     * 发送消息
//     *
//     * @param msg
//     */
//    private void sendMessage(String msg) {
//        if (client != null) {
//            if (client.isClosed()) {
//                reconnectWs();
//            } else {
//                client.send(msg);
//            }
//        }
//    }
//
//    /**
//     * websocket心跳检测
//     */
//    private Runnable heartBeatRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Loger.e("WS心跳检测");
//            if (client != null) {
//                if (client.isClosed()) {
//                    reconnectWs();
//                }
//            } else {
//                //如果client已为空，重新初始化连接
//                client = null;
//                initSocketClient();
//            }
//            //每隔一定的时间，对长连接进行一次心跳检测
//            mHandler.postDelayed(this, HEART_BEAT_RATE);
//        }
//    };
//
//    /**
//     * 开启重连
//     */
//    private void reconnectWs() {
//        mHandler.removeCallbacks(heartBeatRunnable);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Loger.e("WS开启重连");
//                    client.reconnectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * 连接websocket
//     */
//    private void connect() {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
//                    client.connectBlocking();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * 断开连接
//     */
//    private void closeConnect() {
//        try {
//            if (null != client) {
//                client.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            client = null;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        closeConnect();
//        mHandler.removeCallbacks(heartBeatRunnable);
//        super.onDestroy();
//    }
//}
