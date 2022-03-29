package com.noobzy.webSocket;

import com.noobzy.config.WebSocketConfig;
import com.noobzy.entity.Task;
import com.noobzy.taskHandler.TaskEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@ServerEndpoint(value = "/webSocket", configurator = WebSocketConfig.class, encoders = {TaskEncoder.class})
public class WebSocket {

    final static ConcurrentHashMap<Session, HttpSession> socketHttpSessionMap = new ConcurrentHashMap();

    final static ConcurrentHashMap<HttpSession, List<Session>> httpSessionSocketMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("open");
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        socketHttpSessionMap.put(session, httpSession);
        httpSessionSocketMap.putIfAbsent(httpSession, new Vector<>());
        List<Session> socketSessions = httpSessionSocketMap.get(httpSession);
        socketSessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("close");
        HttpSession httpSession = socketHttpSessionMap.get(session);
        List<Session> socketSessions = httpSessionSocketMap.get(httpSession);
        if (socketSessions != null) {
            socketSessions.remove(session);
            if (socketSessions.isEmpty()) {
                httpSessionSocketMap.remove(httpSession);
            }
        }
        socketHttpSessionMap.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        System.err.println("error with session:" + session);
    }

    public static void sendTaskInfo(HttpSession httpSession, Task task) {
        List<Session> webSockets = httpSessionSocketMap.get(httpSession);
        if (webSockets != null) {
            for (Session session : webSockets) {
                try {
                    session.getBasicRemote().sendObject(task);
                } catch (Exception e) {
                    //TODO
                }
            }
        }
    }
}
