package xyz.zhazong710.chat.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.service.UserService;

public class RegisterView extends JFrame {
	
	JPanel centerJPanel = null;
	JLabel usernameJLabel = null;
	JTextField usernameJTextField = null;
	JLabel pwdJLabel = null;
	JPasswordField pwdJPasswordField = null;
	JLabel pwdConfirmJLabel = null;
	JPasswordField pwdConfirmJPasswordField = null;
	JLabel profileJLabel = null;
	JComboBox<String> profileJComboBox = null;
	
	JPanel southJPanel = null;
	JButton submitJButton = null;
	JButton backJButton = null;
	JButton helpJButton = null;
	
	public void createFrame() {
		
		this.setTitle("注册-ZhaZongChat");
		this.setBounds(720, 180, 380, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URL imageUrl = RegisterView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		usernameJLabel = new JLabel("账号:");
		usernameJTextField = new JTextField(20);
		usernameJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		pwdJLabel = new JLabel("密码:");
		pwdJPasswordField = new JPasswordField(20);
		pwdJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		pwdConfirmJLabel  = new JLabel("重复:");
		pwdConfirmJPasswordField = new JPasswordField(20);
		pwdConfirmJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		profileJLabel = new JLabel("头像:");
		String[] profileUrl = {"img/q1.png", "img/q2.png", "img/q3.png", "img/q4.png"};
		profileJComboBox = new JComboBox<String>(profileUrl);
		profileJComboBox.setSize(new Dimension(20, 5));
		profileJComboBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		centerJPanel.add(usernameJLabel);
		centerJPanel.add(usernameJTextField);
		centerJPanel.add(pwdJLabel);
		centerJPanel.add(pwdJPasswordField);
		centerJPanel.add(pwdConfirmJLabel);
		centerJPanel.add(pwdConfirmJPasswordField);
		centerJPanel.add(profileJLabel);
		centerJPanel.add(profileJComboBox);
		this.add(centerJPanel, BorderLayout.CENTER);
		
		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		submitJButton = new JButton("确认注册");
		submitJButton.setPreferredSize(new Dimension(100,55));
		backJButton = new JButton("回返登录");
		backJButton.setPreferredSize(new Dimension(100,55));
		helpJButton = new JButton("帮助");
		helpJButton.setPreferredSize(new Dimension(100,55));
		southJPanel.add(submitJButton);
		southJPanel .add(backJButton);
		southJPanel.add(helpJButton);
		this.add(southJPanel, BorderLayout.SOUTH);
		
		this.sEvent();
		this.setVisible(true);
	}
	
	//监听事件
	private void sEvent() {
		
		//确认注册按钮
		submitJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String pwd = new String(pwdJPasswordField.getPassword());
				String pwdConfirm = new String(pwdConfirmJPasswordField.getPassword());
				
				if(pwd.equals(pwdConfirm)) {
					
					User user = new User();
					user.setUsername(usernameJTextField.getText());
					user.setPwd(pwd);
					user.setProfile(profileJComboBox.getSelectedItem().toString());
					user.setIsonline(0);
					user.setIsLeaveMsg(0);
					
					
					UserService userService = new UserService();
					if(userService.register(user)) {
						RegisterView.this.dispose();
						LoginView loginView = new LoginView();
						loginView.createFrame();
						JOptionPane.showMessageDialog(RegisterView.this, "注册成功", "注册成功", JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(RegisterView.this, "用户名已被注册", "注册失败", JOptionPane.ERROR_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(RegisterView.this, "两次密码不一致", "注册失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		//帮助按钮
		helpJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(RegisterView.this, "此程序为zhazong710开发，欢迎访问闸总博客\n需要帮助欢迎留言\n闸总博客地址：www.zhazong710.xyz\n\n\u002A若你觉得此项目对你有所帮助\n\u002A并且愿意请我喝一杯咖啡?\n\u002A如此本站的维护费用得到赞助,将提高本站生存时间\n\u002A感谢你对此项目的认可和支持\n\u002A赞助支持可在闸总博客站内商店进行赞助\n\n此项目会在GitHub进行开源\n博客会对此项目做详细说明解释", "软件说明", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		//返回登录按钮
		backJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterView.this.dispose();
				LoginView loginView = new LoginView();
				loginView.createFrame();
			}
		});
			
	}

}
