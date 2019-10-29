package Jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
//1.加载驱动
import java.util.ResourceBundle;
public class JDBC正常流程 {
//	private static String USERNAMR = "system";
//    private static String PASSWORD = "root";
//    private static String DRVIER = "oracle.jdbc.OracleDriver";
//    private static String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; 
	
	private static String USERNAMR = "root";
	private static String PASSWORD = "root";
	private static String DRVIER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/sms?characterEncoding=utf8"; 
	
    private static String sql = "select * from All_users";
    private static String sql1 = "select * from student";
    
    
	public static void main(String[] args) {
		try {
			Class.forName(DRVIER);
			Connection connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
			PreparedStatement ps = connection.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
            	for (int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getObject(i)+"\t");
				}
            	System.out.println();
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
