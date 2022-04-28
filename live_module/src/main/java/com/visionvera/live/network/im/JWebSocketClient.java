package com.visionvera.live.network.im;


import com.visionvera.live.utils.Loger;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @Desc websocket客户端
 *
 * @Author yemh
 * @Date 2019-08-01 10:39
 *
 */
public class JWebSocketClient extends WebSocketClient {
    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Loger.e("JWebSocketClient onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Loger.e("JWebSocketClient onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Loger.e("JWebSocketClient onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Loger.e("JWebSocketClient onError()");
    }
}
