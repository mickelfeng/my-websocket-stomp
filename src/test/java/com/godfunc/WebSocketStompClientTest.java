package com.godfunc;

import com.godfunc.handler.MyStompSessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Slf4j
class WebSocketStompClientTest {


    @Test
    void clientTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        StompSession session1 = createSession("1");
        StompSession session2 = createSession("2");
        countDownLatch.await();
    }

    private StompSession createSession(String user) {
        // create WebSocket client
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new StringMessageConverter());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("id", user);
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders(httpHeaders);
        ListenableFuture<StompSession> session = stompClient.connect(
                "http://localhost:8080/myEndPoint",
                headers,
                new MyStompSessionHandler());
        try {
            StompSession stompSession = session.get();
            // 发送消息到服务端
            stompSession.send("/app/hello", "有新的用户登陆哦！");
            // 订阅user消息
            stompSession.subscribe("/user/message", new MyStompSessionHandler());
            // 订阅广播消息
            stompSession.subscribe("/common", new MyStompSessionHandler());
            return stompSession;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
