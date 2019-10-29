package Jdbc;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//src文件目录下加载配置文件
    
public class OracleUtil {
	
	private static String DRIVER;
	private static String URL;
	private  static String USERNAME ;
	private static String PASSWORD ;
	static{
		try{
		ResourceBundle rb=ResourceBundle.getBundle("jdbc");
		OracleUtil.URL=rb.getString("URL");
		OracleUtil.DRIVER = rb.getString("DRIVER");
		OracleUtil.USERNAME = rb.getString("USERNAME");
		OracleUtil.PASSWORD = rb.getString("PASSWORD");
		}catch(Exception e){
			System.out.println("DRIVER:"+DRIVER);
			System.out.println("URL:"+URL);
			System.out.println("USERNAME:"+USERNAME);
			System.out.println("PASSWORD:"+PASSWORD);
			System.out.println("加载配置文件出错");
		}
	}
	public static void setPro(String DRIVER , String URL, String USERNAME, String PASSWORD){
			OracleUtil.URL=URL;
			OracleUtil.DRIVER = DRIVER;
			OracleUtil.USERNAME = USERNAME;
			OracleUtil.PASSWORD = PASSWORD;
	}
	
	public static <E> ArrayList<E> dbDQLWithSQL(String sql, Class<E> class1, Object... objects) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ArrayList<E> list = new ArrayList<>();
		ResultSet set = null;
		try {
			statement = connection.prepareStatement(sql);
			for (int i = 1; i <= objects.length; i++) {
				Object o = objects[i - 1];
				statement.setObject(i, o);
			}
			ResultSetMetaData data = statement.getMetaData();
			String[] names = new String[data.getColumnCount()];
			for (int i = 1; i <= data.getColumnCount(); i++) {
				names[i - 1] = data.getColumnLabel(i);
			}
			set = statement.executeQuery();
			while (set.next()) {
				E oE = class1.newInstance();
				for (String name : names) {
					Object value = set.getObject(name);
					Field field = class1.getDeclaredField(name);
					field.setAccessible(true);
					field.set(oE, value);
				}
				list.add(oE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(connection, statement, set);
		}
		return list;
	}
	public static boolean dbDMLWithSQL(String sql, Object... objects) {

		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			for (int i = 1; i <= objects.length; i++) {
				Object object = objects[i - 1];
				statement.setObject(i, object);
			}
			statement.execute();
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		} finally {
			close(connection, statement);
		}
		return true;
	}

	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (Exception e) {
		//	e.printStackTrace();
		}
		return connection;
	}

	private static void close(Connection connection, Statement statement, ResultSet set) {
		close(set);
		close(statement);
		close(connection);
	}

	private static void close(Connection connection, Statement statement) {
		close(statement);
		close(connection);

	}

	private static void close(ResultSet set) {
		if (set != null) {
			try {
				set.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
