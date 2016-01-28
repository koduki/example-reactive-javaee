/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.websocket.Session;

/**
 *
 * @author koduki
 */
@Singleton
@Named
public class WSManager {

    private final Map<Session, String> sessions = Collections.synchronizedMap(new HashMap<Session, String>());

    public void add(Session session) {
        sessions.put(session, "");
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void broadcast(String message) {
        for (Map.Entry<Session, String> session : sessions.entrySet()) {
            session.getKey().getAsyncRemote().sendText(message + ", " + session.getValue());
        }
    }

    public void send(String user, String message) {
        for (Map.Entry<Session, String> session : sessions.entrySet()) {
            if (session.getValue().equals(user)) {
                session.getKey().getAsyncRemote().sendText(message + ", only " + session.getValue());
            }
        }
    }

    public void login(Session session, String user) {
        sessions.put(session, user);
    }

}
