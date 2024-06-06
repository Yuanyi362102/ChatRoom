package DataBase;
import java.sql.*;

public class AddUser {
    public static int add(int id,String uname,String usex,String uhead,String upass) {
        Connection con;
        Statement sql;
        System.out.println(3);
        con = JDBC.connecDB("demo", "root", "Aa360781@@");
        if(con == null) return -1;
        try {
            sql = con.createStatement();
            String sqlStr = "INSERT INTO user VALUES " +
             "('" + id +"','"+ uname + "','" + usex + "','" + uhead + "','" + upass + "')";
            int ok = sql.executeUpdate(sqlStr);
            con.close();
            return ok;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 1;
    }
}
