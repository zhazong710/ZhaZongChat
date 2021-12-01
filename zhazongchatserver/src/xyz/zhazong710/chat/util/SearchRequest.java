package xyz.zhazong710.chat.util;

//查找功能封装类
public class SearchRequest {
	
	private int pageNow;
	private int pageSize;
	private String searchKey;
	private int start;
	
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public int getStart() {
		return (pageNow - 1) * pageSize;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
