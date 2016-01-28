/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.pushnotification;

import java.io.Serializable;
import javax.persistence.Transient;
import lombok.Data;

/**
 * WebSocket common interface.
 *
 * @author koduki
 */
@Data
public class WebEvent implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;
    private String type;
    private String body;
}
