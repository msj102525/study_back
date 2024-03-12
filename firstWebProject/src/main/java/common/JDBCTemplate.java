package common;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

//싱글톤 디자인패턴 적용
public class JDBCTemplate {
	//driver.properties 파일을 읽어와서, 값을 적용
	//static 메소드 내에서는 this 사용 못하므로, 별도의 파일 읽기용 내부클래스 작성해서 사용하도록 함
	//내부클래스의 외부클래스의 멤버이므로, 클래스이지만 static 을 사용할 수 있음
	//static 메소드 내에서 사용해야 되므로, 내부클래스도 static 이어야 함
	private static class ReadProperties {
		private Properties prop;
	
		//생성자
		public ReadProperties() {
			prop = new Properties();
			
			try {
				prop.load(new InputStreamReader(this.getClass().getResourceAsStream("driver.properties")));
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		public Properties getProp() {
			return prop;
		}
	} //내부클래스
	
	
	public static Connection getConnection() {
		Connection conn = null;		
		
		try {			 
			Properties prop = new ReadProperties().getProp();
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(
					prop.getProperty("url"), 
					prop.getProperty("user"), prop.getProperty("passwd"));
			
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
