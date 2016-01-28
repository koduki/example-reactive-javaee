/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.web;

import cn.orz.pascal.example.reactive_javaee.bean.ItemOrderFacade;
import cn.orz.pascal.example.reactive_javaee.bean.Sender;
import cn.orz.pascal.example.reactive_javaee.bean.entity.ItemOrder;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author koduki
 */
@Singleton
public class DatabaseWatcher {

    @Inject
    Sender sender;

    @Inject
    ItemOrderFacade itemOrderFacade;

    Date oldDate;

    @PostConstruct
    public void init() {
        oldDate = new Date();
    }

    @Schedule(hour = "*", minute = "*", second = "*")
    public void watch() {
        List<ItemOrder> items = itemOrderFacade.findByUpdated(oldDate);
        System.out.println("updated is " + items.size());
        
        
        items.forEach(item -> sender.send(item));
        this.oldDate = new Date();
    }
}
