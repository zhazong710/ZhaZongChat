package xyz.zhazong710.chat.util;

import java.util.Vector;

//表格
public class TableDTO {
	
	private Vector<Vector<Object>> rowDate;
	private int totalCount;
	
	public Vector<Vector<Object>> getRowDate() {
		return rowDate;
	}
	public void setRowDate(Vector<Vector<Object>> rowDate) {
		this.rowDate = rowDate;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
