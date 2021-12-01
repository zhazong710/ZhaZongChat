package xyz.zhazong710.chat.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.util.JdbcUtil;
import xyz.zhazong710.chat.util.PropertiesUtil;

//数据库用户表交互类
public class UserDao {
	
	//数据库登录查找用户
	public User login(String username, String pwd) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			connection.setAutoCommit(false);
			
			StringBuffer stringBuffer = new StringBuffer("select id,username,pwd from zha_user where username = ? and decode(pwd,?) = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, PropertiesUtil.getPropertiesUtil().getValue("zhakey"));
			preparedStatement.setString(3, pwd);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				
			}
			
			if(user != null) {
				StringBuffer sqlBuffer = new StringBuffer("update zha_user set isonline = 1 where username = ?");
				preparedStatement = connection.prepareStatement(sqlBuffer.toString());
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.executeUpdate();
			}
			
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		
		return user;
	}
	
	//数据库注册用户名查重
	public User getUserByUsername(String username) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("select id,username,pwd from zha_user where username = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		
		return user;
	}
	
	
	//数据库注册插入用户
	public void regUser(User user) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("insert into zha_user(username,pwd,profile) values(?,encode(?,?),?)");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPwd());
			preparedStatement.setString(3, PropertiesUtil.getPropertiesUtil().getValue("zhakey"));
			preparedStatement.setString(4, user.getProfile());
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
	}
	
	//数据库全部好友列表查询
	public List<User> getUserList(String username) {
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		List<User> users = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("{call get_user_list(?)}");
			callableStatement = connection.prepareCall(stringBuffer.toString());
			callableStatement.setString(1, username);
			
			resultSet = callableStatement.executeQuery();
			if(!resultSet.wasNull()){
				users = new ArrayList<User>();
			}
			
			while(resultSet.next()) {
				
				User user = new User();
				user.setId(resultSet.getInt(1));
				user.setUsername(resultSet.getString(2));
				user.setProfile(resultSet.getString(3));
				user.setIsonline(resultSet.getInt(4));
				user.setIsLeaveMsg(resultSet.getInt(5));
				
				users.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, callableStatement, connection);
		}
		
		return users;
	}
	
	//下线更新
	public void exit(String username) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			
			StringBuffer sqlBuffer = new StringBuffer("update zha_user set isonline = 0 where username = ?");
			preparedStatement = connection.prepareStatement(sqlBuffer.toString());
			preparedStatement.setString(1, username);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
	}
}
