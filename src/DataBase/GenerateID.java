package DataBase;
import java.sql.*;
import java.util.Random;

public class GenerateID {
    public static int generate() {
        int ID = 0;
        Connection con;
        PreparedStatement pstmt; // 使用PreparedStatement
        Statement sql;
        ResultSet rs;
        Boolean Continue = true;
        con = JDBC.connecDB("demo", "root", "Aa360781@@");
        if (con == null) {
            System.out.println("con==null");
            return -1;
        }

        /*随机生成生成一个8位数的id */
        ID = RandomEightDigitNumber.RandomGenerate();

        try {
            do {
                String query = "SELECT id FROM user WHERE id = ?"; // 使用占位符
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, ID); // 设置参数值
                rs = pstmt.executeQuery();
                
                if(rs.next()) { // 如果存在至少一个结果
                    ID = RandomEightDigitNumber.RandomGenerate();
                } else {
                    Continue = false;
                }
            } while (Continue == true);

        } catch (Exception e) {
            System.out.println("数据库操作出错: " + e.getMessage());
            e.printStackTrace();
        }
        // 其他清理工作...
        return ID;
    }
}

class B{
    public static void main(String[] args) {
        GenerateID.generate();
    }
}

