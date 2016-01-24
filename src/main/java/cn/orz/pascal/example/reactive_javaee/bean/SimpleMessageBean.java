/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author koduki
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "jms/myQueue"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class SimpleMessageBean implements MessageListener {
    @Inject
    WSManager wsManager;
    
    @Resource
    private MessageDrivenContext context;
    static final Logger logger = Logger.getLogger("SimpleMessageBean");

    public SimpleMessageBean() {
    }

    @Override
    public void onMessage(Message message) {

        try {
            if (message instanceof TextMessage) {
                String msg = message.getBody(String.class);
                logger.log(Level.INFO,
                        "MESSAGE BEAN: Message received: {0}",
                        msg);
                wsManager.broadcast(msg);
                
            } else {
                logger.log(Level.WARNING,
                        "Message of wrong type: {0}",
                        message.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "SimpleMessageBean.onMessage: JMSException: {0}",
                    e.toString());
            context.setRollbackOnly();
        }
    }
}
