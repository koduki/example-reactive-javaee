/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.example.reactive_javaee.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author koduki
 */
@Stateless
public class Watch {

    @PersistenceContext(unitName = "cn.orz.pascal_example-reactive-javaee_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private boolean complete = false;

    private CallableStatement cstmt2 = null;

    /**
     *
     * マスタテーブルの開始を監視する
     *
     */
    @Asynchronous
    public void run() {
        System.out.println("start watch.");
        String dicttype;

        try {
            Connection con = em.unwrap(java.sql.Connection.class);
            con.setAutoCommit(false);

            // DBMS_ALERT.REGISTER
            //   - 指定した名前のアラートをこのセッションで受け取ることを定義する
            CallableStatement cstmt1 = con.prepareCall("{call DBMS_ALERT.REGISTER(?)}");
            cstmt1.setString(1, "ITEMORDER");
            cstmt1.executeUpdate();

            // DBMS_ALERT.WAITANY
            //   - メッセージの受信待ち
            //     ここでは、一つの待ちプロセスで複数の種類のメッセージを受け取り、
            //     メッセージの内容で処理を分ける。
            cstmt2 = con.prepareCall("{call DBMS_ALERT.WAITANY(?,?,?,?)}");
            // 第1パラメータ(OUT) - アラートの名前
            cstmt2.registerOutParameter(1, Types.VARCHAR);
            // 第2パラメータ(OUT) - メッセージの内容
            cstmt2.registerOutParameter(2, Types.VARCHAR);
            // 第3パラメータ(OUT) - 実行結果。0ならアラート受信。1ならタイムアウト。
            cstmt2.registerOutParameter(3, Types.INTEGER);
            // 第4パラメータ(IN)  - タイムアウト時間(秒数)
            cstmt2.setInt(4, 1);

            System.err.println("WAIT START!");
            while (true) {
                try {
                    // アラートはトランザクションがコミットした時点で送受信される
                    con.commit();
                    // 上で指定したDBMS_ALERT.WAITANYプロシジャの実行
                    cstmt2.executeUpdate();

                    if (cstmt2.getInt(3) == 1) {

                        //タイムアウトによって終了した場合の処理
                        System.err.println("TABLES NO UPDATED!");

                    } else {
                        System.out.println("****************************");
                        System.out.println("****************************");
                        System.out.println("Update");
                        System.out.println("****************************");
                        System.out.println("****************************");

                        //アラート受信で終了した場合、アラートの名前で処理を分ける
                        dicttype = cstmt2.getString(1);
                        if (dicttype.equals("ITEMORDER") == true) {
                            // チーム配列の初期化処理がここに入る
                            System.err.println("ITEMORDER TABLE UPDATED!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
