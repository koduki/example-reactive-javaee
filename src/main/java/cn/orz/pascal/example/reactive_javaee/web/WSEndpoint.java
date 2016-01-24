/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.web;

import cn.orz.pascal.example.reactive_javaee.bean.WSManager;
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

    @OnMessage
    public void onMessage(String message) {
        manager.broadcast(message);
    }

    @OnOpen
    public void open(Session session) {
        manager.add(session);
    }

    @OnClose
    public void close(Session session) {
        manager.remove(session);
    }

}
