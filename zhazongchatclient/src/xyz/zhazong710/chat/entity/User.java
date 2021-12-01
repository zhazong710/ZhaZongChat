package xyz.zhazong710.chat.entity;

import java.io.Serializable;

//用户数据实体类
public class User implements Serializable {

	private int id;
	private String username;
	private String pwd;
	private String profile;
	private int isonline;
	private int isLeaveMsg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public int getIsonline() {
		return isonline;
	}
	public void setIsonline(int isonline) {
		this.isonline = isonline;
	}
	public int getIsLeaveMsg() {
		return isLeaveMsg;
	}
	public void setIsLeaveMsg(int isLeaveMsg) {
		this.isLeaveMsg = isLeaveMsg;
	}
	
}
