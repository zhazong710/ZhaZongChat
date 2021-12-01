package xyz.zhazong710.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.util.JdbcUtil;
import xyz.zhazong710.chat.util.PropertiesUtil;
import xyz.zhazong710.chat.util.SearchRequest;
import xyz.zhazong710.chat.util.TableDTO;

//数据库增删改查类
public class UserCrudDao {
	
	//查找方法
	public TableDTO retrieveUser(SearchRequest searchRequest) {
		
		StringBuilder tablesql = new StringBuilder();
		tablesql.append("select id,username,pwd,profile,isonline from zha_user ");
		
		if (searchRequest.getSearchKey() != null && !"".equals(searchRequest.getSearchKey().trim())) {
			tablesql.append(" where username like '%" + searchRequest.getSearchKey().trim() + "%' ");
		}
		tablesql.append(" order by id asc limit " + searchRequest.getStart() + "," + searchRequest.getPageSize());
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		TableDTO tableDTO = new TableDTO();
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			preparedStatement = connection.prepareStatement(tablesql.toString());
			resultSet = preparedStatement.executeQuery();
			tableDTO.setRowDate(fillDate(resultSet));
			
			tablesql.setLength(0);
			tablesql.append("select count(*) from zha_user ");
			if (searchRequest.getSearchKey() != null && !"".equals(searchRequest.getSearchKey().trim())) {
				tablesql.append(" where username like '%" + searchRequest.getSearchKey().trim() + "%' ");
			}
			preparedStatement = connection.prepareStatement(tablesql.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int count = resultSet.getInt(1);
				tableDTO.setTotalCount(count);
			}
			
			return tableDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		
		return null;
	}
	
	//查找方法数据内容添加
	private Vector<Vector<Object>> fillDate(ResultSet resultSet) throws SQLException {
		
		Vector<Vector<Object>> rowDate = new Vector<Vector<Object>>();
		while(resultSet.next()) {
			Vector<Object> oneRecord = new Vector<Object>();
			int id = resultSet.getInt("id");
			String username = resultSet.getString("username");
			String pwd = resultSet.getString("pwd");
			String profile = resultSet.getString("profile");
			int isonline = resultSet.getInt("isonline");
			
			oneRecord.add(id);
			oneRecord.add(username);
			oneRecord.add(pwd);
			oneRecord.add(profile);
			oneRecord.add(isonline);
			rowDate.add(oneRecord);
		}
		
		return rowDate;
	}
	
	//增加方法
	public boolean addUser(User user) {
		
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
			
			return preparedStatement.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
		
		return false;
	}
	
	//数据库用户名查重
	public boolean getUserByUsername(String username) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("select id,username,pwd from zha_user where username = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return false;
			}else{
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		
		return false;
	}
	
	//修改界面查找用户回调显示
	public User getUserById(int selectedIds) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("select * from zha_user where id = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setInt(1, selectedIds);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String pwd = resultSet.getString("pwd");
				String profile = resultSet.getString("profile");
				user.setId(id);
				user.setUsername(username);
				user.setPwd(pwd);
				user.setProfile(profile);
			}
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		
		return null;
	}
	
	//修改用户数据方法
	public boolean updateUser(User user) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("update zha_user set username=?,pwd=encode(?,?),profile=? where id=?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPwd());
			preparedStatement.setString(3, PropertiesUtil.getPropertiesUtil().getValue("zhakey"));
			preparedStatement.setString(4, user.getProfile());
			preparedStatement.setInt(5, user.getId());
			
			return preparedStatement.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
		
		return false;
	}
	
	//删除用户
	public boolean deleteUser(int[] selectedIds) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			connection.setAutoCommit(false);
			
			StringBuffer stringBuffer = new StringBuffer("delete from zha_user where id in ( ");
			int length = selectedIds.length;
			for(int i = 0; i < length; i++) {
				if(i == (length - 1)) {
					stringBuffer.append(" ? ");
				}else {
					stringBuffer.append(" ?, ");
				}
			}
			stringBuffer.append(" ) ");
			
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			for(int i = 0; i < length; i++) {
				preparedStatement.setInt(i + 1, selectedIds[i]);
			}
			
			boolean isSucceed = preparedStatement.executeUpdate() == length;
			connection.commit();
			return isSucceed;
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
		
		return false;
	}

}
