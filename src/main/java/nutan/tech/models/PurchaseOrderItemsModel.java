package nutan.tech.models;

public class PurchaseOrderItemsModel {

	int purchase_order_item_id, item_type;
	float quantity, cgst, sgst, igst;
	double purchase_price;
	String item_name, hsn_o_sac, item_description;

	public PurchaseOrderItemsModel() {
		
	}

	public PurchaseOrderItemsModel(int purchase_order_item_id, int item_type, float quantity, double purchase_price, float cgst, float sgst, float igst, String item_name, String hsn_o_sac, String item_description) {

		this.purchase_order_item_id = purchase_order_item_id;
		this.item_type = item_type;
		this.quantity = quantity;
		this.purchase_price = purchase_price;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.item_name = item_name;
		this.hsn_o_sac = hsn_o_sac;
		this.item_description = item_description;
	}

	public int getPurchase_order_item_id() {
		return purchase_order_item_id;
	}

	public void setPurchase_order_item_id(int purchase_order_item_id) {
		this.purchase_order_item_id = purchase_order_item_id;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getHsn_o_sac() {
		return hsn_o_sac;
	}

	public void setHsn_o_sac(String hsn_o_sac) {
		this.hsn_o_sac = hsn_o_sac;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	
}
