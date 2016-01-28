/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.web;

import cn.orz.pascal.example.reactive_javaee.bean.ItemOrderFacade;
import cn.orz.pascal.example.reactive_javaee.bean.entity.ItemOrder;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

/**
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

    public String getAge() {
        return "12";
    }

    public String doOrder() {
        ItemOrder item = new ItemOrder(name, price);
        itemOrderFacade.create(item);

        return "index";
    }

    public List<ItemOrder> getItemOrders() {
        return itemOrderFacade.findAll();
    }
}
