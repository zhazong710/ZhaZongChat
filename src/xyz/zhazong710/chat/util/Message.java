package xyz.zhazong710.chat.util;

import java.io.Serializable;
import java.util.List;

import xyz.zhazong710.chat.entity.Information;
import xyz.zhazong710.chat.entity.User;

//消息类型
public class Message implements Serializable {
	
	private int messageType;
	private User user;
	private String content;
	private List<User> users;
	private String hostName;
	private String friendName;
	private List<Information> informations;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

}
