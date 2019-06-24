package nutan.tech.models;

public class OutlineItemsModel {

	int outline_item_id, item_type, item_id;
	float quantity, cgst, sgst, igst;
	double sale_price;
	String hsn_o_sac, item_description;

	public OutlineItemsModel() {
		
	}
	
	public OutlineItemsModel(int outline_item_id, int item_type, int item_id, float quantity, float cgst, float sgst, float igst, double sale_price, String hsn_o_sac, String item_description) {
		
		this.outline_item_id = outline_item_id;
		this.item_type = item_type;
		this.item_id = item_id;
		this.quantity = quantity;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.sale_price = sale_price;
		this.hsn_o_sac = hsn_o_sac;
		this.item_description = item_description;
	}

	public int getOutline_item_id() {
		return outline_item_id;
	}

	public void setOutline_item_id(int outline_item_id) {
		this.outline_item_id = outline_item_id;
	}
	
	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
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

	public double getSale_price() {
		return sale_price;
	}

	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
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
