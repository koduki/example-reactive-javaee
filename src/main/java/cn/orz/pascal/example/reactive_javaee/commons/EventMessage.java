/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.commons;

import java.io.Serializable;
import javax.persistence.Transient;
import lombok.Data;

/**
 * Event message common interface.
 *
 * @author koduki
 */
@Data
public class EventMessage implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;
    private String type;
    private String user;
    private String body;
}
