package nutan.tech.models;

import javax.validation.constraints.Null;

public class PaymentReceiptModel {

	int payment_receipt_id, enterprise_id, party_type_code, id_vendor_customer, payment_mode, payment_via;
	float amount;
	String invoice_number, name, entry_date, party, mode_of_payment, via_payment, note;
	
	public PaymentReceiptModel() {
		
	}
	
	@Null
	public PaymentReceiptModel(int payment_receipt_id, int enterprise_id, int party_type_code, int id_vendor_customer, int payment_mode, int payment_via, float amount, String invoice_number, String entry_date, String note) {
		
		this.payment_receipt_id = payment_receipt_id;
		this.enterprise_id = enterprise_id;
		this.party_type_code = party_type_code;
		this.id_vendor_customer = id_vendor_customer;
		this.payment_mode = payment_mode;
		this.payment_via = payment_via;
		this.amount = amount;
		this.invoice_number = invoice_number;
		this.entry_date = entry_date;
		this.note = note;		
	}		
	
	@Null
	public PaymentReceiptModel(int payment_receipt_id, int enterprise_id, int party_type_code, int id_vendor_customer, int payment_mode, int payment_via, String invoice_number, String name, String entry_date, String party, String mode_of_payment, String via_payment, float amount, String note) {

		this.payment_receipt_id = payment_receipt_id;
		this.enterprise_id = enterprise_id;
		this.party_type_code = party_type_code;
		this.id_vendor_customer = id_vendor_customer;
		this.payment_mode = payment_mode;
		this.payment_via = payment_via;
		this.amount = amount;
		this.invoice_number = invoice_number;
		this.name = name;
		this.entry_date = entry_date;
		this.party = party;
		this.mode_of_payment = mode_of_payment;
		this.via_payment = via_payment;
		this.note = note;		
	}

	public int getParty_type_code() {
		return party_type_code;
	}

	public void setParty_type_code(int party_type_code) {
		this.party_type_code = party_type_code;
	}

	public int getId_vendor_customer() {
		return id_vendor_customer;
	}

	public void setId_vendor_customer(int id_vendor_customer) {
		this.id_vendor_customer = id_vendor_customer;
	}

	public int getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(int payment_mode) {
		this.payment_mode = payment_mode;
	}

	public int getPayment_via() {
		return payment_via;
	}

	public void setPayment_via(int payment_via) {
		this.payment_via = payment_via;
	}

	public int getPayment_receipt_id() {
		return payment_receipt_id;
	}

	public void setPayment_receipt_id(int payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
	}

	public int getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(int enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getMode_of_payment() {
		return mode_of_payment;
	}

	public void setMode_of_payment(String mode_of_payment) {
		this.mode_of_payment = mode_of_payment;
	}

	public String getVia_payment() {
		return via_payment;
	}

	public void setVia_payment(String via_payment) {
		this.via_payment = via_payment;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
