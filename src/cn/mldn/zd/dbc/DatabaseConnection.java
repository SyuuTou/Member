package cn.mldn.zd.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";
	private static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl11g";
	private static final String USER="scott";
	private static final String PASS="tiger";
	
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	
	private static Connection connectionDatabase() {
		Connection conn=null;
		try {
			Class.forName(DBDRIVER);
			conn=DriverManager.getConnection(DBURL,USER,PASS);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static Connection getConnection() {
		Connection conn=threadLocal.get(); //取出的是threadLocal中的副本？什么是副本？
		if(conn==null) {
			conn=connectionDatabase();
			threadLocal.set(conn);
		}
		return conn;
	}
	
	public static void close() {
		Connection conn=threadLocal.get();
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadLocal.remove();
		}
	}
	public static void main(String[] args) {
		Connection conn=DatabaseConnection.getConnection();
		System.out.println(conn);
	}
}
