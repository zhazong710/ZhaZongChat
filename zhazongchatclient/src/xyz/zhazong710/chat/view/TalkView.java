package xyz.zhazong710.chat.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xyz.zhazong710.chat.entity.Information;
import xyz.zhazong710.chat.util.Message;
import xyz.zhazong710.chat.util.MessageType;
import xyz.zhazong710.chat.util.PropertiesUtil;
import xyz.zhazong710.chat.util.SocketUtil;
import xyz.zhazong710.chat.util.TalkThread;

//聊天窗口界面
public class TalkView extends JFrame {
	
	private String hostName;
	private String friendName;
	
	JTextArea jTextArea;
	JScrollPane jScrollPane;
	
	JPanel southJPanel;
	JTextField jTextField;
	JButton sendButton;
	
	Socket socket;
	TalkThread talkThread;
	List<Information> informations;
	
	public TalkView(String hostName, String friendName, List<Information> informations) {
		this.hostName = hostName;
		this.friendName = friendName;
		this.informations = informations;
	}

	public void createFrame() {
		
		this.setTitle("当前用户:" + this.hostName + "与用户:" + this.friendName + "聊天");
		this.setBounds(660, 300, 700, 500);
		this.setResizable(false);
		
		URL imageUrl = TalkView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		jTextArea = new JTextArea();
		jTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		jTextArea.setEditable(false);
		jScrollPane = new JScrollPane(jTextArea);
		this.add(jScrollPane, BorderLayout.CENTER);
		
		if(informations != null) {
			for(int i = 0; i < informations.size(); i++) {
				jTextArea.append(informations.get(i).getContent());
			}
		}
		
		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jTextField = new JTextField(25);
		jTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		sendButton = new JButton("发送");
		sendButton.setPreferredSize(new Dimension(75,40));
		southJPanel.add(jTextField);
		southJPanel.add(sendButton);
		this.add(southJPanel, BorderLayout.SOUTH);
		
		try {
			socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
			Message requestMessage = new Message();
			requestMessage.setMessageType(MessageType.TALK_CONNECTION);
			requestMessage.setHostName(hostName);
			requestMessage.setFriendName(friendName);
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			
			talkThread = new TalkThread(socket, jTextArea);
			talkThread.start();
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.sEvent();
		this.setVisible(true);
	}
	
	//监听事件
	private void sEvent() {
		
		//发送按钮
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					
					Message requestMessage = new Message();
					requestMessage.setMessageType(MessageType.TALK_NORMAL);
					requestMessage.setHostName(hostName);
					requestMessage.setFriendName(friendName);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					
					requestMessage.setContent(dateFormat.format(new Date()) + "——" + hostName + "说:" + "\n" + jTextField.getText());
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
					
					jTextArea.append(dateFormat.format(new Date()) + "——" + hostName + "说:" + "\n" + jTextField.getText() + "\n");
					jTextField.setText("");
					
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
		
		//窗口关闭
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Message requestMessage = new Message();
				requestMessage.setMessageType(MessageType.TALK_CLOSE);
				requestMessage.setHostName(hostName);
				requestMessage.setFriendName(friendName);
				
				try {
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				talkThread.shutDown();
			}
		});
			
	}
	
}
