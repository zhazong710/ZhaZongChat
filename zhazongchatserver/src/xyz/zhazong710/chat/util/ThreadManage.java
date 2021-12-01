package xyz.zhazong710.chat.util;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//线程管理 
public class ThreadManage {
	
	public static Map<String, TalkThread> threadMap = new HashMap<String, TalkThread>();
	public static Map<String, Socket> socketMap = new HashMap<String, Socket>();

}
