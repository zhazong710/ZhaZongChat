package xyz.zhazong710.chat.util;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

//聊天线程
public class TalkThread extends Thread {

	Socket socket;
	JTextArea jTextArea;
	boolean isRun = true;
	
	public TalkThread(Socket socket, JTextArea jTextArea) {
		this.socket = socket;
		this.jTextArea = jTextArea;
	}
	
	@Override
	public void run() {
		
		while(isRun){
			try {
				Message talkMessage = SocketUtil.getSocketUtil().readMessage(socket);
				
				if(talkMessage.getMessageType() == MessageType.TALK_NORMAL) {
					jTextArea.append(talkMessage.getContent() + "\n");
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
