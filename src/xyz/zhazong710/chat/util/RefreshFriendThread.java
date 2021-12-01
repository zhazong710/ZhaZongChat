package xyz.zhazong710.chat.util;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;

import xyz.zhazong710.chat.view.HomeView;

//刷新用户列表线程
public class RefreshFriendThread extends Thread {
	
	Socket socket;
	HomeView homeView;
	boolean isRun = true;

	public RefreshFriendThread(Socket socket, HomeView homeView) {
		this.socket = socket;
		this.homeView = homeView;
	}

	@Override
	public void run() {
		while(isRun) {
			try {
				Message message = SocketUtil.getSocketUtil().readMessage(socket);
				
				if(message.getMessageType() == MessageType.REFRESH_FRIEND) {
					JLabel[] jLabels = homeView.jLabels;
					
					for(int i = 0; i < jLabels.length; i++) {
						if(message.getUser().getUsername().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.BLACK);
							jLabels[i].setEnabled(true);
							jLabels[i].setVisible(true);
							homeView.validate();
						}
					}
					
				}else if(message.getMessageType() == MessageType.REFRESH_FRIEND_EXIT) {
					JLabel[] jLabels = homeView.jLabels;
					
					for(int i = 0; i < jLabels.length; i++) {
						if(message.getFriendName().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.GRAY);
							jLabels[i].setVisible(true);
							jLabels[i].setEnabled(false);
							homeView.validate();
						}
					}
					
				}else if(message.getMessageType() == MessageType.TALK_LEAVE) {
					JLabel[] jLabels = homeView.jLabels;
					
					for(int i = 0; i < jLabels.length; i++) {
						if(message.getFriendName().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.BLUE);
							homeView.validate();
						}
					}
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
