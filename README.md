MEMO
=============

```bash
$ curl http://localhost:8080/example-reactive-javaee/rs/queue -X POST -d "message=hello"
```


http://localhost:8080/example-reactive-javaee/faces/index.xhtml

docker run -d -p 49160:22 -p 49161:1521 wnameless/oracle-xe-11g

### image

https://cacoo.com/diagrams/QQDpmy1JFdN6cuac


 DELIMITER @@
 CREATE OR REPLACE TRIGGER T_CHGITEMORDER AFTER INSERT OR UPDATE OR DELETE ON ITEMORDER
 BEGIN
     DBMS_ALERT.SIGNAL('ITEMORDER','UPDATED!');
 END; @@
 DELIMITER ;
