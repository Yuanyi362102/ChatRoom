package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    public static Connection connecDB(String DBName, String id, String pass){
        Connection con = null;
        String uri = "jdbc:mysql://localhost:3306/demo ?useSSL=false &allowPublicKeyRetrieval=true &serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("驱动成功");
        } catch (Exception e) {}
        try {
            con = DriverManager.getConnection(uri, id, pass);
            System.out.println("连接成功");
        } catch (SQLException e) {System.out.println("连接失败");}
        return con;
    }

   
}

