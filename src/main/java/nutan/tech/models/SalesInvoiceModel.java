package nutan.tech.models;

public class SalesInvoiceModel {

	int sales_invoice_id, enterprise_id, customer_id, sold_item_id, discount_type, item_discount_type;
	float discount, item_discount, units, sale_price, sgst, cgst, igst, grand_total;
	boolean status = false;
	String customer_name, invoice_number, reference, entry_date, due_date, item_name, hsn_o_sac, msg_to_customer;
	
	public SalesInvoiceModel() {
		
	}
	
	public SalesInvoiceModel(int sales_invoice_id, int enterprise_id, int customer_id, int sold_item_id, int discount_type, float discount, boolean status, String invoice_number, String reference, String entry_date, String due_date, String msg_to_customer) {

		this.sales_invoice_id = sales_invoice_id;
		this.enterprise_id = enterprise_id;
		this.customer_id = customer_id;
		this.sold_item_id = sold_item_id;
		this.discount_type = discount_type;
		
		this.discount = discount;
		
		this.status = status;
		
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.msg_to_customer = msg_to_customer;		
	}
	
	public SalesInvoiceModel(boolean status, String invoice_number, String reference, String entry_date, String due_date, int discount_type, float discount, int customer_id, String customer_name, String item_name, float units, float sale_price, float sgst, float cgst, float igst, int item_discount_type, float item_discount, String hsn_o_sac, float grand_total, String msg_to_customer) {
		
		this.status = status;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.entry_date = entry_date;
		this.due_date = due_date;
		this.discount_type = discount_type;
		this.discount = discount;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.units = units;
		this.sale_price = sale_price;
		this.sgst = sgst;
		this.cgst = cgst;
		this.igst = igst;
		this.item_discount_type = item_discount_type;
		this.item_discount = item_discount;
		this.hsn_o_sac = hsn_o_sac;
		this.item_name = item_name;
		this.grand_total = grand_total;
		this.msg_to_customer = msg_to_customer;
	}

	public int getSales_invoice_id() {
		return sales_invoice_id;
	}

	public void setSales_invoice_id(int sales_invoice_id) {
		this.sales_invoice_id = sales_invoice_id;
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

	public int getSold_item_id() {
		return sold_item_id;
	}

	public void setSold_item_id(int sold_item_id) {
		this.sold_item_id = sold_item_id;
	}

	public int getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(int discount_type) {
		this.discount_type = discount_type;
	}

	public int getItem_discount_type() {
		return item_discount_type;
	}

	public void setItem_discount_type(int item_discount_type) {
		this.item_discount_type = item_discount_type;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getItem_discount() {
		return item_discount;
	}

	public void setItem_discount(float item_discount) {
		this.item_discount = item_discount;
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

	public float getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(float grand_total) {
		this.grand_total = grand_total;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
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

	public String getMsg_to_customer() {
		return msg_to_customer;
	}

	public void setMsg_to_customer(String msg_to_customer) {
		this.msg_to_customer = msg_to_customer;
	}
	
}
