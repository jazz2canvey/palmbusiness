package nutan.tech.models;

public class OutlinesModel {

	int outline_id, enterprise_id, customer_id, outlined_item_id, outline_type_code;
	double grand_total;
	String customer_name, outline_number, entry_date, valid_date, sales_rep_name, note; 
	
	public OutlinesModel() {
		
	}

	public OutlinesModel(int outline_id, int enterprise_id, int customer_id, int outlined_item_id, int outline_type_code, String outline_number, String entry_date, String valid_date, String sales_rep_name, String note) {

		this.outline_id = outline_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.outlined_item_id = outlined_item_id;
		this.outline_type_code = outline_type_code;

		this.outline_number = outline_number;
		this.entry_date = entry_date;
		this.valid_date = valid_date;
		this.sales_rep_name = sales_rep_name;
		this.note = note;		
	}
	
	public OutlinesModel(int outline_id, int enterprise_id, int customer_id, int outlined_item_id, int outline_type_code, double grand_total, String customer_name,  String outline_number, String entry_date, String valid_date, String sales_rep_name, String note) {

		this.outline_id = outline_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.outlined_item_id = outlined_item_id;
		this.outline_type_code = outline_type_code;
		this.grand_total = grand_total;

		this.customer_name = customer_name;
		this.outline_number = outline_number;
		this.entry_date = entry_date;
		this.valid_date = valid_date;
		this.sales_rep_name = sales_rep_name;
		this.note = note;		
	}

	public int getOutline_id() {
		return outline_id;
	}

	public void setOutline_id(int outline_id) {
		this.outline_id = outline_id;
	}

	public int getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(int enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getOutlined_item_id() {
		return outlined_item_id;
	}

	public void setOutlined_item_id(int outlined_item_id) {
		this.outlined_item_id = outlined_item_id;
	}

	public int getOutline_type_code() {
		return outline_type_code;
	}

	public void setOutline_type_code(int outline_type_code) {
		this.outline_type_code = outline_type_code;
	}

	public double getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(double grand_total) {
		this.grand_total = grand_total;
	}
	
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getOutline_number() {
		return outline_number;
	}

	public void setOutline_number(String outline_number) {
		this.outline_number = outline_number;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getValid_date() {
		return valid_date;
	}

	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}

	public String getSales_rep_name() {
		return sales_rep_name;
	}

	public void setSales_rep_name(String sales_rep_name) {
		this.sales_rep_name = sales_rep_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
