/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.web;

import cn.orz.pascal.example.reactive_javaee.bean.Event;
import cn.orz.pascal.example.reactive_javaee.bean.WSManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author koduki
 */
@ServerEndpoint("/endpoint")
public class WSEndpoint {

    @Inject
    WSManager manager;

    private Session current = null;

    private void handle(Event event) {
        switch (event.getType()) {
            case "broadcast":
                manager.broadcast(event.getBody());
                break;
            case "login":
                manager.login(current, event.getBody());
                manager.broadcast("login success.");
                break;
            default:
                manager.broadcast("error: unkown event!");
        }
    }

    @OnMessage
    public void onMessage(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Event event = mapper.readValue(json, Event.class);
            handle(event);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @OnOpen
    public void open(Session session) {
        this.current = session;
        manager.add(session);
    }

    @OnClose
    public void close(Session session) {
        manager.remove(session);
    }

}
