package xyz.zhazong710.chat.entity;

import java.io.Serializable;

//留言信息实体类
public class Information implements Serializable {
	
	private int id;
	private String hosterName;
	private String friendName;
	private String content;
	private String sendDateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHosterName() {
		return hosterName;
	}
	public void setHosterName(String hosterName) {
		this.hosterName = hosterName;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	
}
