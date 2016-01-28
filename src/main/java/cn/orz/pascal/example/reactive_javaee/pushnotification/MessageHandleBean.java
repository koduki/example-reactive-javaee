/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.pushnotification;

import cn.orz.pascal.example.reactive_javaee.commons.EventMessage;
import cn.orz.pascal.example.reactive_javaee.commons.ItemOrder;
import cn.orz.pascal.example.reactive_javaee.commons.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Event message handler.
 *
 * @author koduki
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "jms/myQueue"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class MessageHandleBean implements MessageListener {

    @Inject
    WSManager wsManager;
    @Inject
    Logger logger;

    @Resource
    private MessageDrivenContext context;

    public MessageHandleBean() {
    }

    /**
     * Handle event.
     *
     * @param event event message.
     * @throws IOException
     */
    private void handle(EventMessage event) throws IOException {
        switch (event.getType()) {
            case "ItemOrder":
                ObjectMapper mapper = new ObjectMapper();
                ItemOrder order = mapper.readValue(event.getBody(), ItemOrder.class);

                wsManager.send(event.getUser(), mapper.writeValueAsString(order));
                break;
            default:
                logger.info("MessageEvent of wrong type: {0}", event.getType());
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String msg = message.getBody(String.class);
                logger.info("MESSAGE BEAN: Message received: {0}", msg);

                ObjectMapper mapper = new ObjectMapper();
                EventMessage event = mapper.readValue(msg, EventMessage.class);
                handle(event);

            } else {
                logger.warn("Message of wrong type: {0}", message.getClass().getName());
            }
        } catch (JMSException | IOException e) {
            e.printStackTrace();
            context.setRollbackOnly();
        }
    }
}
