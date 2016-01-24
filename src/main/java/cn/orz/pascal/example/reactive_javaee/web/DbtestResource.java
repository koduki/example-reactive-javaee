/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.web;

import cn.orz.pascal.example.reactive_javaee.bean.Sender;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author koduki
 */
@Path("/dbtest")
@RequestScoped
public class DbtestResource {

    @Inject
    private Sender sender;


    @GET
    public void get() {
        
    }
}
