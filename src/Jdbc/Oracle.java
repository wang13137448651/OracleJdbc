package Jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Oracle {
    // 定义连接所需的字符串
    // 172.16.5.112是本机地址(要改成自己的IP地址)，1521端口号，XE是精简版Oracle的默认数据库名
    private static String USERNAMR = "system";
    private static String PASSWORD = "root";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:orcl";// loc(本地)

    // 创建一个数据库连接
    Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    // 创建一个结果集对象
    ResultSet rs = null;
    /**
     * 获取Connection对象
     * 
     * @return
     */
    public Connection getConnection() {
        if (connection == null) {
            synchronized (Oracle.class) {
                if (connection == null) {
                    try {
                        Class.forName(DRVIER);
                        connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
                        // System.out.println("成功连接数据库");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("class not find !--找不到类异常", e);
                    } catch (SQLException e) {
                        throw new RuntimeException("get connection error!--获取连接错误", e);
                    }
                }
            }
        }
        return connection;
    }
    /**
     * 释放资源
     */
    public void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
       Oracle oo = new Oracle();
        oo.selectData();
    }
    /**
     * 向数据库中查询数据
     */
    public void selectData() {
        connection = getConnection();
        String sql = "select * from student";
        try {
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
            	for (int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getObject(i)+"\t");
				}
            	System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

}