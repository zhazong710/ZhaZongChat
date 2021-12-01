package xyz.zhazong710.chat.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import xyz.zhazong710.chat.dao.InformationDao;
import xyz.zhazong710.chat.dao.UserDao;
import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.util.Message;
import xyz.zhazong710.chat.util.MessageType;
import xyz.zhazong710.chat.util.SocketUtil;
import xyz.zhazong710.chat.util.TalkThread;
import xyz.zhazong710.chat.util.ThreadManage;

//开启服务
public class ServerService implements Runnable {
	
	UserDao userDao = new UserDao();
	InformationDao informationDao = new InformationDao();
	public static ServerSocket serverSocket = null;
	public static boolean flag = true;
	
	//开启服务器方法
	public void server() throws IOException, ClassNotFoundException {
		
		serverSocket = new ServerSocket(9710);
		flag = true;
		while(flag) {
			
			Socket socket = serverSocket.accept();
			Message requestMessage = SocketUtil.getSocketUtil().readMessage(socket);
			
			switch(requestMessage.getMessageType()) {
				case MessageType.LOGIN: {
					
					Message responseMessage = new Message();
					
					if(userDao.login(requestMessage.getUser().getUsername(), requestMessage.getUser().getPwd()) != null) {
						
						responseMessage.setMessageType(MessageType.LOGIN_SUCCESS);
						
						Message refreshFriendMessage = new Message();
						refreshFriendMessage.setMessageType(MessageType.REFRESH_FRIEND);
						refreshFriendMessage.setUser(requestMessage.getUser());
						
						Set<String> keySet = ThreadManage.socketMap.keySet();
						Iterator<String> keys = keySet.iterator();
						while(keys.hasNext()) {
							String key = keys.next();
							Socket friendLisetSocket = ThreadManage.socketMap.get(key);
							SocketUtil.getSocketUtil().writeMessage(friendLisetSocket, refreshFriendMessage);
						}
						
					}else {
						responseMessage.setMessageType(MessageType.LOGIN_FAILURE);
					}
					
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					
					break;
				}
				case MessageType.REGISTER: {
					
					Message responseMessage = new Message();
					User registerUser = requestMessage.getUser();
					
					if(userDao.getUserByUsername(registerUser.getUsername()) == null) {
						
						userDao.regUser(registerUser);
						responseMessage.setMessageType(MessageType.REGISTER_SUCCESS);
						responseMessage.setContent("注册成功");
						
					}else {
						responseMessage.setMessageType(MessageType.REGISTER_FAILURE);
						responseMessage.setContent("用户名已被注册");
					}
					
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					
					break;
				}
				case MessageType.GET_USER_LIST: {
					
					List<User> users = userDao.getUserList(requestMessage.getHostName());
					Message responseMessage = new Message();
					responseMessage.setUsers(users);
					responseMessage.setMessageType(MessageType.GET_USER_LIST_SUCCESS);
					
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					ThreadManage.socketMap.put(requestMessage.getHostName(), socket);
					
					break;
				}
				case MessageType.TALK_CONNECTION: {
					
					TalkThread talkThread = new TalkThread(socket);
					talkThread.start();
					
					ThreadManage.threadMap.put(requestMessage.getHostName() + "-" + requestMessage.getFriendName(), talkThread);
					
					break;
				}
				case MessageType.EXIT: {
					
					ThreadManage.socketMap.remove(requestMessage.getHostName());
					userDao.exit(requestMessage.getHostName());
					
					Message refreshFriendMessage = new Message();
					refreshFriendMessage.setMessageType(MessageType.REFRESH_FRIEND_EXIT);
					refreshFriendMessage.setFriendName(requestMessage.getHostName());
					
					Set<String> keySet = ThreadManage.socketMap.keySet();
					Iterator<String> keys = keySet.iterator();
					while(keys.hasNext()) {
						String key = keys.next();
						Socket friendLisetSocket = ThreadManage.socketMap.get(key);
						SocketUtil.getSocketUtil().writeMessage(friendLisetSocket, refreshFriendMessage);
					}
					
					break;
				}
				case MessageType.GET_TALK_LEAVE: {
					
					Message responseMessage = new Message();
					responseMessage.setInformations(informationDao.getInformation(requestMessage.getHostName(), requestMessage.getFriendName()));
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					
					break;
				}
			}
			
		}
		
	}

	@Override
	public void run() {
		try {
			ServerService.this.server();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
