package xyz.zhazong710.chat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//jdbc封装
public class JdbcUtil {
	
	private static JdbcUtil jdbcUtil = null;
	
	private JdbcUtil() {
		
	}
	
	//获取封装jdbc对象
	public static JdbcUtil getJdbcUtil() {
		
		if(null == jdbcUtil) {
			jdbcUtil = new JdbcUtil();
		}
		
		return jdbcUtil;
	}
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//获取数据库连接
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(PropertiesUtil.getPropertiesUtil().getValue("url"), PropertiesUtil.getPropertiesUtil().getValue("username"), PropertiesUtil.getPropertiesUtil().getValue("pwd"));
	}
	
	//关闭数据库连接
	public void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
		
		try {
			
			if(null != resultSet) {
				resultSet.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				
				if(null != statement) {
					statement.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				
				try {
					
					if(null != connection) {
						connection.close();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}

}
