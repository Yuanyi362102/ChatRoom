package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifyUser {
    public static boolean Verify(int id, String pass) {
        Connection con = null;
        PreparedStatement sql = null;
        ResultSet rs = null;
        String sqlStr = "SELECT id, pass FROM user WHERE id = ? AND pass = ?";
        
        try {
            con = JDBC.connecDB("demo", "root", "Aa360781@@");
            sql = con.prepareStatement(sqlStr);
            sql.setInt(1, id);
            sql.setString(2, pass);
            rs = sql.executeQuery();
            
            // 如果查询结果集有数据，表示验证成功
            return rs.next();
        } catch (SQLException e) {
            System.out.println("数据库操作出错: " + e.getMessage());
            // 根据需要决定是否需要重新抛出异常或返回false
        } finally {
            // 最后，关闭资源
            try {
                if (rs != null) rs.close();
                if (sql != null) sql.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                // 打印关闭资源时发生的异常
                System.out.println("关闭资源时发生错误: " + e.getMessage());
            }
        }
        // 如果没有找到匹配的记录或发生异常，则返回false
        return false;
    }
}