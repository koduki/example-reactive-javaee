package cn.orz.pascal.example.reactive_javaee.order_simulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cn.orz.pascal.example.reactive_javaee.commons.ItemOrderFacade;
import cn.orz.pascal.example.reactive_javaee.commons.ItemOrder;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

/**
 * Managed Bean for order simulator.
 *
 * @author koduki
 */
@Named
@SessionScoped
@Transactional
public class ItemOrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ItemOrderFacade itemOrderFacade;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    private String user;

    public String doOrder() {
        ItemOrder item = new ItemOrder(name, price, user);
        itemOrderFacade.create(item);

        return "order-simulator";
    }

    public List<ItemOrder> getItemOrders() {
        return itemOrderFacade.findAll();
    }
}
