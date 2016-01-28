/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.commons;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author koduki
 */
@Stateless
public class ItemOrderFacade extends AbstractFacade<ItemOrder> {

    @PersistenceContext(unitName = "example-reactive-javaee_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemOrderFacade() {
        super(ItemOrder.class);
    }

    public List<ItemOrder> findByUpdated(Date date) {
        TypedQuery<ItemOrder> query = em.createQuery(
                "select t from ItemOrder t where t.updatedAt >= :date",
                ItemOrder.class
        );
        query.setParameter("date", date);
        List<ItemOrder> result = query.getResultList();

        return result;
    }

}
