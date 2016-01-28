/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.commons;

import java.util.logging.Level;
import javax.inject.Singleton;

/**
 * Simple Logger.
 *
 * @author koduki
 */
@Singleton
public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("ReactiveJavaEE");

    public void info(String message, Object param1) {
        this.logger.log(Level.INFO, message, param1);
    }

    public void info(String message) {
        this.logger.log(Level.INFO, message);
    }

    public void warn(String message, Object param1) {
        this.logger.log(Level.WARNING, message, param1);
    }
}
