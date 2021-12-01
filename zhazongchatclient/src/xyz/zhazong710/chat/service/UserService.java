package xyz.zhazong710.chat.service;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.util.Message;
import xyz.zhazong710.chat.util.MessageType;
import xyz.zhazong710.chat.util.PropertiesUtil;
import xyz.zhazong710.chat.util.RefreshFriendThread;
import xyz.zhazong710.chat.util.SocketUtil;
import xyz.zhazong710.chat.util.ThreadManage;
import xyz.zhazong710.chat.view.HomeView;

//用户业务逻辑
public class UserService {
	
	//登录到服务器方法，判断是否登录成功
	public boolean login(User user) {
		
		try {
			
			Socket socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
			Message requestMessage = new Message();
			requestMessage.setMessageType(MessageType.LOGIN);
			requestMessage.setUser(user);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage = SocketUtil.getSocketUtil().readMessage(socket);
			socket.close();
			
			if(responseMessage.getMessageType() == MessageType.LOGIN_SUCCESS) {
				return true;
			}else {
				return false;
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//注册到服务器方法，判断是否登录成功
	public boolean register(User user) {
		
		try {
			
			Socket socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
			Message requestMessage = new Message();
			requestMessage.setMessageType(MessageType.REGISTER);
			requestMessage.setUser(user);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage = SocketUtil.getSocketUtil().readMessage(socket);
			socket.close();
			
			if(responseMessage.getMessageType() == MessageType.REGISTER_SUCCESS) {
				return true;
			}else {
				return false;
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}
	
	//获取聊天室用户列表
	public List<User> getUserList(HomeView homeView) {
		
		List<User> users = null;
		
		try {
			
			Socket socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
			Message requestMessage = new Message();
			requestMessage.setHostName(homeView.username);
			requestMessage.setMessageType(MessageType.GET_USER_LIST);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage = SocketUtil.getSocketUtil().readMessage(socket);
			
			if(responseMessage.getMessageType() == MessageType.GET_USER_LIST_SUCCESS) {
				users = responseMessage.getUsers();
			}
			
			RefreshFriendThread friendThread = new RefreshFriendThread(socket, homeView);
			friendThread.start();
			
			ThreadManage.threadMap.put(homeView.username, friendThread);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return users;
	}

}
