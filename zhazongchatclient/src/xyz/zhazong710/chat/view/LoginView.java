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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xyz.zhazong710.chat.entity.User;
import xyz.zhazong710.chat.service.UserService;

/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月19日
 * @version 1.5.3
 * 
 * main方法入口
 * 登录界面
 */
public class LoginView extends JFrame {
	
	JPanel northJPanel = null;
	JLabel photoJLabel = null;
	
	JPanel centerJPanel = null;
	JLabel usernameJLabel = null;
	JTextField usernameJTextField = null;
	JLabel pwdJLabel = null;
	JPasswordField pwdJPasswordField = null;
	
	JPanel southJPanel = null;
	JButton loginJButton = null;
	JButton registerJButton = null;
	JButton helpJButton = null;
	
	public static void main(String[] args) {
		
		LoginView loginView = new LoginView();
		loginView.createFrame();
		
	}
	
	public void createFrame() {
		
		this.setTitle("登录-ZhaZongChat");
		this.setBounds(720, 180, 380, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URL imageUrl = LoginView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		ImageIcon imageIcon = new ImageIcon(LoginView.class.getClassLoader().getResource("img/logo.png"));
		photoJLabel = new JLabel("", imageIcon, JLabel.CENTER);
		northJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		northJPanel.add(photoJLabel);
		this.add(northJPanel, BorderLayout.NORTH);
		
		usernameJLabel = new JLabel("账号:");
		usernameJTextField = new JTextField(20);
		usernameJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		pwdJLabel = new JLabel("密码:");
		pwdJPasswordField = new JPasswordField(20);
		pwdJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerJPanel.add(usernameJLabel);
		centerJPanel.add(usernameJTextField);
		centerJPanel.add(pwdJLabel);
		centerJPanel.add(pwdJPasswordField);
		this.add(centerJPanel, BorderLayout.CENTER);
		
		loginJButton = new JButton("登录");
		loginJButton.setPreferredSize(new Dimension(100,55));
		registerJButton = new JButton("注册");
		registerJButton.setPreferredSize(new Dimension(100,55));
		helpJButton = new JButton("帮助");
		helpJButton.setPreferredSize(new Dimension(100,55));
		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		southJPanel.add(loginJButton);
		southJPanel.add(registerJButton);
		southJPanel.add(helpJButton);
		this.add(southJPanel, BorderLayout.SOUTH);
		
		this.sEvent();
		this.setVisible(true); 
	}
	
	//监听事件
	private void sEvent() {
		
		//登录按钮
		loginJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				User user = new User();
				user.setUsername(usernameJTextField.getText());
				user.setPwd(new String(pwdJPasswordField.getPassword()));
				UserService userService = new UserService();
				
				if(userService.login(user)) {
					
					LoginView.this.dispose();
					HomeView homeView = new HomeView(user.getUsername());
					homeView.createFrame();
					
				}else {
					JOptionPane.showMessageDialog(LoginView.this, "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		//注册按钮
		registerJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				LoginView.this.dispose();
				RegisterView registerView = new RegisterView();
				registerView.createFrame();
				
			}
		});
		
		//帮助按钮
		helpJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(LoginView.this, "此程序为zhazong710开发，欢迎访问闸总博客\n需要帮助欢迎留言\n闸总博客地址：www.zhazong710.xyz\n\n\u002A若你觉得此项目对你有所帮助\n\u002A并且愿意请我喝一杯咖啡?\n\u002A如此本站的维护费用得到赞助,将提高本站生存时间\n\u002A感谢你对此项目的认可和支持\n\u002A赞助支持可在闸总博客站内商店进行赞助\n\n此项目会在GitHub进行开源\n博客会对此项目做详细说明解释", "软件说明", JOptionPane.WARNING_MESSAGE);
			}
		});
		
	}

}
