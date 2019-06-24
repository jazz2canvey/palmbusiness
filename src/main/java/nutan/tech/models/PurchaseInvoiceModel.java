package nutan.tech.models;

public class PurchaseInvoiceModel {

	int purchase_invoice_id, enterprise_id, vendor_id, item_id;
	float grand_total;
	String enterprise_name, invoice_number, reference, note, entry_date, due_date;
	Boolean status, reverse_charge;

	public PurchaseInvoiceModel() {
		
	}
	
	public PurchaseInvoiceModel(int enterprise_id, int vendor_id, int item_id, float grand_total, Boolean reverse_charge, String enterprise_name, String invoice_number, String reference, String note, String entry_date, String due_date) {

		this.enterprise_id = enterprise_id;
		this.vendor_id = vendor_id; 
		this.item_id = item_id;
		this.grand_total = grand_total;
		this.reverse_charge = reverse_charge;
		this.enterprise_name = enterprise_name;
		this.invoice_number = invoice_number;
		this.reference = reference;
		this.note = note;
		this.entry_date = entry_date;
		this.due_date = due_date; 
		
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

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public float getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(float grand_total) {
		this.grand_total = grand_total;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(Boolean reverse_charge) {
		this.reverse_charge = reverse_charge;
	}
	
}
