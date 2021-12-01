package xyz.zhazong710.chat.util;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableModelUtil extends DefaultTableModel {
	
	static Vector<String> columnName = new Vector<String>();
	static {
		columnName.add("id");
		columnName.add("昵称");
		columnName.add("密码");
		columnName.add("头像");
		columnName.add("在线");
	}
	
	private TableModelUtil() {
		super(null, columnName);
	}
	
	private static TableModelUtil tableModelUtil = new TableModelUtil();
	
	public static TableModelUtil assembleModel(Vector<Vector<Object>> rowDate) {
		tableModelUtil.setDataVector(rowDate, columnName);
		return tableModelUtil;
	}
	
	public static void updateModel(Vector<Vector<Object>> rowDate) {
		tableModelUtil.setDataVector(rowDate, columnName);
	}
	
	public static Vector<String> getColumnName() {
		return columnName;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
