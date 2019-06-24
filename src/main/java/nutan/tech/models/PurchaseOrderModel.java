package nutan.tech.models;

public class PurchaseOrderModel {

	int enterprise_id, vendor_id, purchase_order_item_id;
	double grand_total;
	String enterprise_name, purchase_order_number, entry_date, shipping_address, msg_to_vendor;
	
	public PurchaseOrderModel() {
		
	}
	
	public PurchaseOrderModel(int enterprise_id, int vendor_id, int purchase_order_item_id, double grand_total, String enterprise_name, String purchase_order_number, String entry_date, String shipping_address, String msg_to_vendor) {
		
		this.enterprise_id = enterprise_id;
		this.vendor_id = vendor_id;
		this.purchase_order_item_id = purchase_order_item_id;
		this.grand_total = grand_total;
		this.enterprise_name = enterprise_name;
		this.purchase_order_number = purchase_order_number;
		this.entry_date = entry_date;
		this.shipping_address = shipping_address;
		this.msg_to_vendor = msg_to_vendor;
	}

	public int getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(int enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public int getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}

	public int getPurchase_order_item_id() {
		return purchase_order_item_id;
	}

	public void setPurchase_order_item_id(int purchase_order_item_id) {
		this.purchase_order_item_id = purchase_order_item_id;
	}

	public double getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(double grand_total) {
		this.grand_total = grand_total;
	}
	
	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getPurchase_order_number() {
		return purchase_order_number;
	}

	public void setPurchase_order_number(String purchase_order_number) {
		this.purchase_order_number = purchase_order_number;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getMsg_to_vendor() {
		return msg_to_vendor;
	}

	public void setMsg_to_vendor(String msg_to_vendor) {
		this.msg_to_vendor = msg_to_vendor;
	}
	
}
