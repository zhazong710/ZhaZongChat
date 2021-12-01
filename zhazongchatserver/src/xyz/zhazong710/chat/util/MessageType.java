package xyz.zhazong710.chat.util;

//消息类型
public interface MessageType {
	
	public static final int LOGIN = 1;
	public static final int LOGIN_SUCCESS = 2;
	public static final int LOGIN_FAILURE = 3;
	
	public static final int REGISTER = 4;
	public static final int REGISTER_SUCCESS = 5;
	public static final int REGISTER_FAILURE = 6;
	
	public static final int GET_USER_LIST = 7;
	public static final int GET_USER_LIST_SUCCESS = 8;
	public static final int REFRESH_FRIEND = 9;
	public static final int REFRESH_FRIEND_EXIT = 10;
	
	public static final int TALK_NORMAL = 11;
	public static final int TALK_CONNECTION = 12;
	public static final int TALK_CLOSE = 13;
	public static final int TALK_LEAVE = 14;
	public static final int GET_TALK_LEAVE = 15;
	
	public static final int EXIT = 16;
	
}
