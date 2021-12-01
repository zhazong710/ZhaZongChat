package xyz.zhazong710.chat.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import xyz.zhazong710.chat.entity.Information;
import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.service.UserService;
import xyz.zhazong710.chat.util.Message;
import xyz.zhazong710.chat.util.MessageType;
import xyz.zhazong710.chat.util.PropertiesUtil;
import xyz.zhazong710.chat.util.SocketUtil;
import xyz.zhazong710.chat.util.ThreadManage;

//主界面
public class HomeView extends JFrame {
	
	public String username;
	UserService userService = new UserService();
	
	JPanel northJPanel = null;
	JButton northJButton = null;
	JButton northJButton2 = null;
	JButton northJButton3 = null;
	
	JScrollPane jScrollPane = null;
	JPanel jPanel = null;
	public JLabel[] jLabels;
	
	SystemTray systemTray;
	TrayIcon trayIcon;
	
	public HomeView(String username) {
		this.username = username;
	}
	
	public void createFrame() {
		
		this.setTitle("zzc");
		this.setBounds(1300, 60, 250, 600);
		this.setResizable(false);
		
		URL imageUrl = HomeView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		northJButton2 = new JButton("点击进入群聊");
		northJButton2.setPreferredSize(new Dimension(250,30));
		northJButton3 = new JButton("当前用户：" + this.username);
		northJButton3.setPreferredSize(new Dimension(250,30));
		northJButton = new JButton("ZhaZongChat聊天室用户列表");
		northJButton.setPreferredSize(new Dimension(250,30));
		northJPanel = new JPanel(new GridLayout(3,1));
		northJPanel.add(northJButton2);
		northJPanel.add(northJButton3);
		northJPanel.add(northJButton);
		this.add(northJPanel, BorderLayout.NORTH);
		
		List<User> users = userService.getUserList(this);
		jPanel = new JPanel(new GridLayout(users.size(), 1, 5, 5));
		jLabels = new JLabel[users.size()];
		
		if(users != null) {
			for(int i = 0; i < users.size(); i++) {
				
				User user = users.get(i);
				ImageIcon imageIcon = new ImageIcon(HomeView.class.getClassLoader().getResource((user.getProfile())));
				JLabel jLabel = new JLabel(user.getUsername(), imageIcon, JLabel.LEFT);
				jLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
				
				if(user.getIsonline() == 0) {
					jLabel.setForeground(Color.GRAY);
					jLabel.setVisible(true);
					jLabel.setEnabled(false);
				}
				
				if(user.getIsLeaveMsg() == 1) {
					jLabel.setForeground(Color.BLUE);
				}
				
				if(user.getUsername().equals(this.username)) {
					jLabel.setForeground(Color.GREEN);
					jLabel.setVisible(true);
					jLabel.setEnabled(true);
				}
				
				jLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 2) {
							
							JLabel currentJLabel = (JLabel)e.getSource();
							if(currentJLabel.getForeground() == Color.BLUE) {
								currentJLabel.setForeground(Color.BLACK);
								jLabel.setVisible(true);
							}
							
							List<Information> informations = null;
							
							Message requestMessage = new Message();
							requestMessage.setMessageType(MessageType.GET_TALK_LEAVE);
							requestMessage.setHostName(currentJLabel.getText());
							requestMessage.setFriendName(username);
							
							try {
								Socket socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
								SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
								
								Message responseMessage = SocketUtil.getSocketUtil().readMessage(socket);
								informations = responseMessage.getInformations();
								
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							
							TalkView talkView = new TalkView(username, currentJLabel.getText(), informations);
							talkView.createFrame();
						}
					}
				});
				jPanel.add(jLabel);
				jLabels[i] = jLabel;
			}
		}
		
		jScrollPane = new JScrollPane(jPanel);
		this.add(jScrollPane, BorderLayout.CENTER);
		
		northJButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GroupTalkView groupTalkView = new GroupTalkView();
				groupTalkView.createFrame();
			}
		});
		
		if(SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();
			trayIcon = new TrayIcon(new ImageIcon(imageUrl).getImage());
			trayIcon.setImageAutoSize(true);
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			
			this.addWindowListener(new  WindowAdapter() {
				@Override
				public void windowIconified(WindowEvent e) {
					HomeView.this.setVisible(false);
				}
			});
			
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 1) {
						HomeView.this.setExtendedState(JFrame.NORMAL);
					}
					HomeView.this.setVisible(true);
				}
			});
		}
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Message requestMessage = new Message();
				requestMessage.setMessageType(MessageType.EXIT);
				requestMessage.setHostName(username);
				try {
					Socket socket = new Socket(PropertiesUtil.getPropertiesUtil().getValue("server_host").trim(), 9710);
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
					socket.close();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ThreadManage.threadMap.get(username).shutDown();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
				
		this.setVisible(true); 
	}
	
}
