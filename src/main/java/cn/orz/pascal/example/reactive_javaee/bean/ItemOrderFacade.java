/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import cn.orz.pascal.example.reactive_javaee.bean.entity.ItemOrder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author koduki
 */
@Stateless
public class ItemOrderFacade extends AbstractFacade<ItemOrder> {
    @PersistenceContext(unitName = "cn.orz.pascal_example-reactive-javaee_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemOrderFacade() {
        super(ItemOrder.class);
    }
    
}
