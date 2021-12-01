package xyz.zhazong710.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xyz.zhazong710.chat.entity.Information;
import xyz.zhazong710.chat.util.JdbcUtil;

//数据库留言信息
public class InformationDao {
	
	//写入留言
	public void insertInformation(Information information) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer("insert into zha_information(hostername,friendname,content) values(?,?,?)");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, information.getHosterName());
			preparedStatement.setString(2, information.getFriendName());
			preparedStatement.setString(3, information.getContent());
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
	}
	
	//查看并删除留言
	public List<Information> getInformation(String hostername, String friendname) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Information> informations = null;
		
		try {
			
			connection = JdbcUtil.getJdbcUtil().getConnection();
			connection.setAutoCommit(false);
			
			StringBuffer stringBuffer = new StringBuffer("select id,hostername,friendname,content from zha_information where hostername = ? and friendname = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, hostername);
			preparedStatement.setString(2, friendname);
			
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.wasNull()){
				informations = new ArrayList<Information>();
			}
			
			while(resultSet.next()) {
				
				Information information = new Information();
				information.setId(resultSet.getInt("id"));
				information.setHosterName(resultSet.getString("hosterName"));
				information.setFriendName(resultSet.getString("friendName"));
				information.setContent(resultSet.getString("content"));
				
				informations.add(information);
			}
			
			StringBuffer sqlBuffer = new StringBuffer("delete from zha_information where hostername = ? and friendname = ?");
			preparedStatement = connection.prepareStatement(sqlBuffer.toString());
			preparedStatement.setString(1, hostername);
			preparedStatement.setString(2, friendname);
			preparedStatement.executeUpdate();
			
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
		
		return informations;
	}

}
