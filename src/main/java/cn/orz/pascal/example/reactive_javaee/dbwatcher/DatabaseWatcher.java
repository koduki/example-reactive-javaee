/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.dbwatcher;

import cn.orz.pascal.example.reactive_javaee.commons.ItemOrderFacade;
import cn.orz.pascal.example.reactive_javaee.commons.EventMessage;
import cn.orz.pascal.example.reactive_javaee.pushnotification.MessageSender;
import cn.orz.pascal.example.reactive_javaee.commons.ItemOrder;
import cn.orz.pascal.example.reactive_javaee.commons.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * EventMessage Publisher. This function is re-make by DBMS_ALERT or TopLink
 * Data Service in production.
 *
 * @author koduki
 */
@Singleton
public class DatabaseWatcher {

    @Inject
    MessageSender sender;

    @Inject
    ItemOrderFacade itemOrderFacade;

    @Inject
    Logger logger;

    Date oldDate;

    @PostConstruct
    public void init() {
        oldDate = new Date();
    }

    @Schedule(hour = "*", minute = "*", second = "*")
    public void watch() {
        try {

            List<ItemOrder> items = itemOrderFacade.findByUpdated(oldDate);
            logger.info("updated is " + items.size());

            ObjectMapper mapper = new ObjectMapper();

            for (ItemOrder order : items) {
                EventMessage event = new EventMessage();
                event.setType(order.getClass().getSimpleName());
                event.setUser(order.getUserName());
                event.setBody(mapper.writeValueAsString(order));

                String json = mapper.writeValueAsString(event);
                sender.send(json);
            }

            this.oldDate = new Date();
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
