package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Frames.ChatFrame;

public class LoadInfo {
    public static boolean Load(int uid) {
        String uname = "";
        String usex;
        String uhead;
        Connection con = null;
        PreparedStatement sql = null;
        ResultSet rs = null;
        String sqlStr = "SELECT * FROM user WHERE id = ? ";
        
        try {
            con = JDBC.connecDB("demo", "root", "Aa360781@@");
            sql = con.prepareStatement(sqlStr);
            sql.setInt(1, uid);
            rs = sql.executeQuery();
            if(rs.next()){
                uid = rs.getInt(rs.findColumn("id"));
                uname = rs.getString(rs.findColumn("name"));
                usex = rs.getString(rs.findColumn("sex"));
                uhead = rs.getString(rs.findColumn("head"));
                new ChatFrame(uid, uname, usex, uhead);
            }
           
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