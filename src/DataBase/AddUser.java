package DataBase;
import java.sql.*;

public class AddUser {
    public static void add(int id,String uname,String usex,String uhead,String upass) {
        Connection con;
        Statement sql;
        ResultSet rs;
        System.out.println(3);
        con = JDBC.connecDB("demo", "root", "Aa360781@@");
        if(con == null) return;
        try {
            sql = con.createStatement();
            String sqlStr = "INSERT INTO user VALUES " +
             "('" + id +"','"+ uname + "','" + usex + "','" + uhead + "','" + upass + "')";
            int ok = sql.executeUpdate(sqlStr);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}

class A{
    public static void main(String[] args) {
        String name="",sex="",head="",pass="";
        //AddUser.add(id,name, sex, head, pass);
    }
}
