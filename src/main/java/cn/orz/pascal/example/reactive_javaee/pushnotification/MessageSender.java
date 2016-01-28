/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.pushnotification;

import cn.orz.pascal.example.reactive_javaee.commons.ItemOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

/**
 * Event message sender to JMS.
 *
 * @author koduki
 */
@Stateless
public class MessageSender {

    @Inject
    private JMSContext context;

    @Resource(name = "jms/myQueue")
    private Destination myQueue;

    public void send(String message) {
        context.createProducer().send(myQueue, message);
    }

    public void send(ItemOrder message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);

            context.createProducer().send(myQueue, json);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
