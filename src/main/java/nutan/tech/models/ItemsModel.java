package nutan.tech.models;

public class ItemsModel {

	int item_id, enterprise_id, item_type;
	float sgst, cgst, igst;
	double units, purchase_price;
	String item_name, item_description, hsn_o_sac; 
	
	public ItemsModel() {
		
	}
	
	public ItemsModel(int item_id, int enterprise_id, int item_type, float sgst, float cgst, float igst, String item_name, String item_description, String hsn_o_sac, double units, double purchase_price) {
		
		this.item_id = item_id;
		this.enterprise_id = enterprise_id;
		this.item_type = item_type;
		
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		
		this.item_name = item_name;
		this.item_description = item_description;
		this.hsn_o_sac = hsn_o_sac;
		this.units = units;
		this.purchase_price = purchase_price;
	}
	
	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(int enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}
	
	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public String getHsn_o_sac() {
		return hsn_o_sac;
	}

	public void setHsn_o_sac(String hsn_o_sac) {
		this.hsn_o_sac = hsn_o_sac;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(double purchase_price) {
		this.purchase_price = purchase_price;
	}
	
}
