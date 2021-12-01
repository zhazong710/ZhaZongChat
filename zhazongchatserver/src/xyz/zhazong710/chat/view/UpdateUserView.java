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

import xyz.zhazong710.chat.dao.UserCrudDao;
import xyz.zhazong710.chat.entity.User;

public class UpdateUserView extends JFrame {
	
	JPanel centerJPanel = null;
	JLabel idJLabel = null;
	JTextField idJTextField = null;
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
	
	private ServerView serverView = null;
	
	public UpdateUserView(ServerView serverView) {
		this.serverView = serverView;
	}
	
	public void createFrame(int selectedIds) {
		
		UserCrudDao userCrudDao = new UserCrudDao();
		User selectedUser = userCrudDao.getUserById(selectedIds);
		
		this.setTitle("修改用户-ZhaZongChat");
		this.setBounds(100, 70, 380, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		URL imageUrl = AddUserView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		idJLabel = new JLabel("ID：");
		idJTextField = new JTextField(20);
		idJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		idJTextField.setText(selectedUser.getId() + "");
		idJTextField.setEnabled(false);
		usernameJLabel = new JLabel("账号:");
		usernameJTextField = new JTextField(20);
		usernameJTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		usernameJTextField.setText(selectedUser.getUsername());
		pwdJLabel = new JLabel("密码:");
		pwdJPasswordField = new JPasswordField(20);
		pwdJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		pwdJPasswordField.setText(selectedUser.getPwd());
		pwdConfirmJLabel  = new JLabel("重复:");
		pwdConfirmJPasswordField = new JPasswordField(20);
		pwdConfirmJPasswordField.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		pwdConfirmJPasswordField.setText(selectedUser.getPwd());
		profileJLabel = new JLabel("头像:");
		String[] profileUrl = {"img/q1.png", "img/q2.png", "img/q3.png", "img/q4.png"};
		profileJComboBox = new JComboBox<String>(profileUrl);
		profileJComboBox.setSize(new Dimension(20, 5));
		profileJComboBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		profileJComboBox.setSelectedItem(selectedUser.getProfile());
		centerJPanel.add(idJLabel);
		centerJPanel.add(idJTextField);
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
		submitJButton = new JButton("确认");
		submitJButton.setPreferredSize(new Dimension(100,55));
		backJButton = new JButton("取消");
		backJButton.setPreferredSize(new Dimension(100,55));
		southJPanel.add(submitJButton);
		southJPanel .add(backJButton);
		this.add(southJPanel, BorderLayout.SOUTH);

		this.sEvent();
		this.setVisible(true);
	}
	
	//监听事件
	private void sEvent() {
			
		//取消按钮
		backJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UpdateUserView.this.dispose();
			}
		});	
		
		//确认按钮
		submitJButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String pwd = new String(pwdJPasswordField.getPassword());
				String pwdConfirm = new String(pwdConfirmJPasswordField.getPassword());
				
				if(pwd.equals(pwdConfirm)) {
					
					User user = new User();
					user.setId(Integer.parseInt(idJTextField.getText()));
					user.setUsername(usernameJTextField.getText());
					user.setPwd(pwd);
					user.setProfile(profileJComboBox.getSelectedItem().toString());
					
					UserCrudDao userCrudDao = new UserCrudDao();
					
					if(userCrudDao.getUserByUsername(user.getUsername())) {
						
						if(userCrudDao.updateUser(user)) {
							
							serverView.reloadTable();
							UpdateUserView.this.dispose();
							JOptionPane.showMessageDialog(UpdateUserView.this, "修改成功", "修改成功", JOptionPane.WARNING_MESSAGE);
							
						}else {
							JOptionPane.showMessageDialog(UpdateUserView.this, "修改失败", "修改失败", JOptionPane.ERROR_MESSAGE);
						}
					
					}else {
						JOptionPane.showMessageDialog(UpdateUserView.this, "已有重名用户", "修改失败", JOptionPane.ERROR_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(UpdateUserView.this, "两次密码不一致", "修改失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
	}

}
