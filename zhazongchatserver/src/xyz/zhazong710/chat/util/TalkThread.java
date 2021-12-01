package xyz.zhazong710.chat.util;

import java.io.IOException;
import java.net.Socket;

import xyz.zhazong710.chat.dao.InformationDao;
import xyz.zhazong710.chat.entity.Information;

//聊天线程
public class TalkThread extends Thread {
	
	Socket socket;
	boolean isRun = true;
	InformationDao informationDao = new InformationDao();
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public TalkThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while(isRun) {
			try {
				Message requestMessage = SocketUtil.getSocketUtil().readMessage(socket);
				
				if(requestMessage.getMessageType() == MessageType.TALK_NORMAL) {
					
					TalkThread talkThread = ThreadManage.threadMap.get(requestMessage.getFriendName() + "-" + requestMessage.getHostName());
					
					if(null == talkThread) {
						
						Information information = new Information();
						information.setHosterName(requestMessage.getHostName());
						information.setFriendName(requestMessage.getFriendName());
						information.setContent(requestMessage.getContent());
						
						informationDao.insertInformation(information);
						
						Socket friendRefreshSocket = ThreadManage.socketMap.get(requestMessage.getFriendName());
						if(friendRefreshSocket != null) {
							Message leaveMessage = new Message();
							leaveMessage.setMessageType(MessageType.TALK_LEAVE);
							leaveMessage.setFriendName(requestMessage.getHostName());
							SocketUtil.getSocketUtil().writeMessage(friendRefreshSocket, leaveMessage);
						}
						
					}else {
						Socket friendSocket = talkThread.getSocket();
						Message responseMessage = new Message();
						responseMessage.setMessageType(MessageType.TALK_NORMAL);
						responseMessage.setContent(requestMessage.getContent());
						SocketUtil.getSocketUtil().writeMessage(friendSocket, requestMessage);
					}
					
				}else if(requestMessage.getMessageType() == MessageType.TALK_CLOSE) {
					this.shutDown();
					ThreadManage.threadMap.remove(requestMessage.getFriendName() + "-" + requestMessage.getHostName());
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void shutDown() {
		isRun = false;
	}
	
}
