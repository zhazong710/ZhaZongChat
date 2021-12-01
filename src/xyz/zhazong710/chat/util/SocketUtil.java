package xyz.zhazong710.chat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

//网络传输封装类
public class SocketUtil {
	
	private static SocketUtil socketUtil = null;
	
	private SocketUtil() {
		
	}
	
	public static SocketUtil getSocketUtil() {
		if(socketUtil == null) {
			socketUtil = new SocketUtil();
		}
		return socketUtil;
	}
	
	public void writeMessage(Socket socket, Message requestMessage) throws IOException  {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(requestMessage);
	}
	
	public Message readMessage(Socket socket) throws IOException, ClassNotFoundException {
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		Message responseMessage = (Message)objectInputStream.readObject();
		return responseMessage;
	}
	
}
