package nutan.tech.models;

public class SoldItemsModel {
	
	int sold_item_id, item_id;
	float units, sale_price, sgst, cgst, igst;
	String item_name, hsn_o_sac;
	
	public SoldItemsModel() {
		
	}

	public SoldItemsModel(int sold_item_id, int item_id, float units, float sale_price, float sgst, float cgst, float igst, String hsn_o_sac) {
		
		this.sold_item_id = sold_item_id;
		this.item_id = item_id;
		this.units = units;
		this.sale_price = sale_price;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.hsn_o_sac = hsn_o_sac; 
	}

	public SoldItemsModel(int sold_item_id, int item_id, float units, float sale_price, float sgst, float cgst, float igst, String item_name, String hsn_o_sac) {
		
		this.sold_item_id = sold_item_id;
		this.item_id = item_id;
		this.units = units;
		this.sale_price = sale_price;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.item_name = item_name;
		this.hsn_o_sac = hsn_o_sac; 
	}
	
	public int getSold_item_id() {
		return sold_item_id;
	}

	public void setSold_item_id(int sold_item_id) {
		this.sold_item_id = sold_item_id;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public float getUnits() {
		return units;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public float getSale_price() {
		return sale_price;
	}

	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
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

	public String getHsn_o_sac() {
		return hsn_o_sac;
	}

	public void setHsn_o_sac(String hsn_o_sac) {
		this.hsn_o_sac = hsn_o_sac;
	}
	
}
