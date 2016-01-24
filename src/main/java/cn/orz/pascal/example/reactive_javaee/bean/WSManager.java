/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import java.util.Collections;
import java.util.HashSet;
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

    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    public void add(Session session) {
        sessions.add(session);
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void broadcast(String message) {
        for (Session s : sessions) {
            s.getAsyncRemote().sendText(message);
        }
    }
}
