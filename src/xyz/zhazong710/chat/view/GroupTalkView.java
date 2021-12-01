package xyz.zhazong710.chat.view;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//群聊界面
public class GroupTalkView extends JFrame {
	
	public static JTextArea cta;  //文本显示
	private JLabel cjl2;   //当前在线标题
	public static JList<String> cuser;  //当前在线用户
	private JScrollPane cp1;  //用户滚动条
	private JLabel cjl3;   //当前信息标题
	private JScrollPane cp2;  //文本滚动条
	private JTextField ctf;  //发生框
	private JButton csend;   //发送按钮
	private JButton chelp;  //帮助按钮
	
	public void createFrame() {
		
		this.setTitle("闸总聊天室客户端-zhazong chat client");
		this.setLayout(null);
		this.setBounds(550, 250, 700, 500);
		this.setResizable(false); 
		
		URL imageUrl = GroupTalkView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		cjl2 = new JLabel("当前在线用户");
		cjl2.setBounds(560, 4, 80, 30);
		this.add(cjl2);
		
		cuser = new JList<String>();
		cp1 = new JScrollPane(cuser);
		cp1.setBounds(530, 30, 140, 300);
		this.add(cp1);
		
		cjl3 = new JLabel("当前信息");
		cjl3.setBounds(230, 4, 80, 30);
		this.add(cjl3);
		
		cta = new JTextArea();
		cta.setBounds(35, 30, 450, 300);
		cta.setEditable(false);
		cta.setLineWrap(true);
		cta.setWrapStyleWord(true);
		cp2 = new JScrollPane(cta);
		cp2.setBounds(35, 30, 450, 300);
		this.add(cp2);
		
		ctf = new JTextField();
		ctf.setBounds(35, 360, 450, 80);
		ctf.setText("请输入内容");
		this.add(ctf);
		
		csend = new JButton("发送");
		csend.setBounds(530, 360, 140, 35);
		this.add(csend);
		
		chelp = new JButton("帮助");
		chelp.setBounds(530, 405, 140, 35);
		this.add(chelp);
		
		this.setVisible(true);
	}

}
