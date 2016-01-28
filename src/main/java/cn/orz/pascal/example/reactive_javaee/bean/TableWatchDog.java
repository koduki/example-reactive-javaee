package cn.orz.pascal.example.reactive_javaee.bean;

import java.sql.*;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * OracleのDBMS_ALERTパッケージを用いて非同期にディクショナリの更新を
 *
 * 監視するスレッド
 *
 * @author koduki
 *
 */
//@Singleton
//@Startup
public class TableWatchDog {
    @EJB
    Watch watch;
    
    /**
     *
     * マスタテーブルの開始を監視する
     *
     */
    @PostConstruct
    public void run() {
        System.out.println("start load.");
        watch.run();
    }

}
