/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

@Stateless
public class Sender {

    @Inject
    private JMSContext context;

    @Resource(name = "jms/myQueue")
    private Destination myQueue;

    public void send(String message) {
        context.createProducer().send(myQueue, message);
    }
}
