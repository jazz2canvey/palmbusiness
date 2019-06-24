package nutan.tech.models;

public class InvoiceDueModel {

	int sold_item_id;
	String invoice_number;
	float units, unit_price, discount_type, discount, sgst, cgst, igst;
	
	public InvoiceDueModel() {
		
	}

	public InvoiceDueModel(int sold_item_id, String invoice_number, float units, float unit_price, float discount_type, float discount, float sgst, float cgst, float igst) {

		this.sold_item_id = sold_item_id;
		this.invoice_number = invoice_number;
		this.units = units;
		this.unit_price = unit_price;
		this.discount_type = discount_type;
		this.discount = discount;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
	}

	public int getSold_item_id() {
		return sold_item_id;
	}

	public void setSold_item_id(int sold_item_id) {
		this.sold_item_id = sold_item_id;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public float getUnits() {
		return units;
	}

	public void setUnits(float units) {
		this.units = units;
	}

	public float getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}

	public float getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(float discount_type) {
		this.discount_type = discount_type;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
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
	
}
