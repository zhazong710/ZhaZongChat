package xyz.zhazong710.chat.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import xyz.zhazong710.chat.dao.UserCrudDao;
import xyz.zhazong710.chat.service.ServerService;
import xyz.zhazong710.chat.util.CellRendererUtil;
import xyz.zhazong710.chat.util.SearchRequest;
import xyz.zhazong710.chat.util.TableDTO;
import xyz.zhazong710.chat.util.TableModelUtil;

/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月19日
 * @version 1.5.3
 * 
 * main方法入口
 * 服务端主界面
 */
public class ServerView extends JFrame {
	
	JPanel southJPanel = null;
	JButton startJButton = null;
	JButton stopJButton = null;
	
	JTable jTable = null;
	JScrollPane jScrollPane = null;
	
	JPanel northJPanel = null;
	JButton addJButton = null;
	JButton deleteJButton = null;
	JButton updateJButton = null;
	JButton searchJButton = null;
	JTextField searchJTextField = null;
	JButton preButton = null;
	JButton nextButton = null;
	
	private int pageNow = 1;
	private int pageSize = 10;
	
	public static void main(String[] args) {
		
		ServerView serverView = new ServerView();
		serverView.createFrame();
		
	}
	
	public void createFrame() {
		
		this.setTitle("ZhaZongChat——服务端");
		this.setBounds(100, 70, 700, 600);
		this.setResizable(false);
		
		URL imageUrl = ServerView.class.getClassLoader().getResource("img/logo.png");
		this.setIconImage(new ImageIcon(imageUrl).getImage());
		
		startJButton = new JButton("开启服务");
		startJButton.setPreferredSize(new Dimension(100,55));
		stopJButton = new JButton("关闭服务");
		stopJButton.setPreferredSize(new Dimension(100,55));
		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		southJPanel.add(startJButton);
		southJPanel.add(stopJButton);
		this.add(southJPanel, BorderLayout.SOUTH);
		
		UserCrudDao userCrudDao = new UserCrudDao();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setPageNow(pageNow);
		searchRequest.setPageSize(pageSize);
//		searchRequest.setSearchKey(searchJTextField.getText().trim());
		TableDTO tableDTO = userCrudDao.retrieveUser(searchRequest);
		Vector<Vector<Object>> rowDate = tableDTO.getRowDate();
		
		TableModelUtil tableModelUtil = TableModelUtil.assembleModel(rowDate);
		jTable = new JTable(tableModelUtil);
		jTable.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
		jTable.setForeground(Color.BLACK);
		jTable.setGridColor(Color.BLACK);
		jTable.setRowHeight(25);
		ServerView.this.renderRule();
		
		JTableHeader jTableHeader = jTable.getTableHeader();
		jTableHeader.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		jTableHeader.setForeground(Color.MAGENTA);
		jScrollPane = new JScrollPane(jTable);
		this.add(jScrollPane, BorderLayout.CENTER);
		
		addJButton = new JButton("增加");
		deleteJButton = new JButton("删除");
		updateJButton = new JButton("修改");
		searchJButton = new JButton("查询");
		searchJTextField = new JTextField(15);
		preButton = new JButton("上一页");
		nextButton = new JButton("下一页");
		northJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		northJPanel.add(addJButton);
		northJPanel.add(deleteJButton);
		northJPanel.add(updateJButton);
		northJPanel.add(searchJTextField);
		northJPanel.add(searchJButton);
		northJPanel.add(preButton);
		northJPanel.add(nextButton);
		this.add(northJPanel, BorderLayout.NORTH);
		ServerView.this.showPreNext(tableDTO.getTotalCount());
				
		this.sEvent();
		this.setVisible(true); 
	}
	
	//监听事件
	private void sEvent() {
		
		//开启按钮
		startJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ServerService.serverSocket != null && !ServerService.serverSocket.isClosed()) {
					JOptionPane.showMessageDialog(ServerView.this, "服务器已启动状态,无需重复启动", "已启动", JOptionPane.WARNING_MESSAGE);
				}else {
					new Thread(new ServerService()).start();
					JOptionPane.showMessageDialog(ServerView.this, "服务器启动成功", "已启动", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		//关闭按钮
		stopJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ServerService.serverSocket == null || ServerService.serverSocket.isClosed()) {
					JOptionPane.showMessageDialog(ServerView.this, "服务器已关闭状态,无需重复关闭", "已关闭", JOptionPane.WARNING_MESSAGE);
				}else {
					try {
						ServerService.flag = false;
						ServerService.serverSocket.close();
						ServerService.serverSocket = null;
						JOptionPane.showMessageDialog(ServerView.this, "服务器关闭成功", "已关闭", JOptionPane.WARNING_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		//关闭
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//查询按钮
		searchJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerView.this.setPageNow(1);
				ServerView.this.reloadTable();
			}
		});
		
		//上一页按钮
		preButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerView.this.setPageNow(ServerView.this.getPageNow() - 1);
				ServerView.this.reloadTable();
			}
		});
		
		//下一页按钮
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerView.this.setPageNow(ServerView.this.getPageNow() + 1);
				ServerView.this.reloadTable();
			}
		});
		
		//增加按钮
		addJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddUserView addUserView = new AddUserView(ServerView.this);
				addUserView.createFrame();
			}
		});
		
		//修改按钮
		updateJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedIds = ServerView.this.getSelectedId();
				if(selectedIds.length != 1) {
					JOptionPane.showMessageDialog(ServerView.this, "每次只能修改一条", "操作错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				UpdateUserView updateUserView = new UpdateUserView(ServerView.this);
				updateUserView.createFrame(selectedIds[0]);
			}
		});
		
		//删除按钮
		deleteJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedIds = ServerView.this.getSelectedId();
				if(selectedIds.length == 0) {
					JOptionPane.showMessageDialog(ServerView.this, "请选择删除的行", "操作错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int option = JOptionPane.showConfirmDialog(ServerView.this, "是否确认删除已选择的" + selectedIds.length +"行?", "删除确认", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					UserCrudDao userCrudDao = new UserCrudDao();
					
					if(userCrudDao.deleteUser(selectedIds)) {
						
						ServerView.this.reloadTable();
						JOptionPane.showMessageDialog(ServerView.this, "删除成功", "删除成功", JOptionPane.WARNING_MESSAGE);
						
					}else {
						JOptionPane.showMessageDialog(ServerView.this, "删除失败", "删除失败", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
	}
	
	//重新加载
	public void reloadTable() {
		UserCrudDao userCrudDao = new UserCrudDao();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setPageNow(pageNow);
		searchRequest.setPageSize(pageSize);
		searchRequest.setSearchKey(searchJTextField.getText().trim());
		TableDTO tableDTO = userCrudDao.retrieveUser(searchRequest);
		Vector<Vector<Object>> rowDate = tableDTO.getRowDate();
		TableModelUtil.updateModel(rowDate);
		ServerView.this.renderRule();
		ServerView.this.showPreNext(tableDTO.getTotalCount());
	}
	
	//渲染列表
	public void renderRule() {
		Vector<String> columnName = TableModelUtil.getColumnName();
		CellRendererUtil cellRendererUtil = new CellRendererUtil();
		for(int i = 0; i < columnName.size(); i++) {
			TableColumn tableColumn = jTable.getColumn(columnName.get(i));
			tableColumn.setCellRenderer(cellRendererUtil);
		}
	}
	
	//上下一页判断
	private void showPreNext(int totalCount) {
		int pageCount;
		
		if(totalCount % pageSize == 0){
			pageCount = totalCount / pageSize;
		}else {
			pageCount = totalCount / pageSize + 1;
		}
		
		if(pageNow == 1) {
			preButton.setVisible(false);
		}else {
			preButton.setVisible(true);
		}
		
		if(pageNow == pageCount) {
			nextButton.setVisible(false);
		}else {
			nextButton.setVisible(true);
		}
		
	}
	
	//获取选中ID行
	public int[] getSelectedId() {
		
		int[] selectedRows = jTable.getSelectedRows();
		int[] ids = new int[selectedRows.length];
		
		for(int i = 0; i < selectedRows.length; i++) {
			int rowIndex = selectedRows[i];
			Object idObject = jTable.getValueAt(rowIndex, 0);
			ids[i] = Integer.valueOf(idObject.toString());
		}
		
		return ids;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	
}
