package haron.aimshoot.vo;

import org.json.JSONObject;

public class CategoryVO {
	private int category_cnt;
	private int user_no;
	private String category_list;
	private String create_date;
	private String categoryList;
	private String modified_date;
	
	public int getCategory_cnt() {
		return category_cnt;
	}
	public void setCategory_cnt(int category_cnt) {
		this.category_cnt = category_cnt;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getCategory_list() {
		return category_list;
	}
	public void setCategory_list(String category_list) {
		this.category_list = category_list;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
}
