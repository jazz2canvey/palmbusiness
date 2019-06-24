package nutan.tech.models;

public class AccountsModel {

	int payment_receipt_id;
	float amount;
	String invoice_number, name, entry_date, party, mode_of_payment, via_payment, note;
	
	public AccountsModel() {
		
	}
	
	public AccountsModel(int payment_receipt_id, float amount, String invoice_number, String name, String entry_date, String party, String mode_of_payment, String via_payment, String note) {
		
		this.payment_receipt_id = payment_receipt_id;
		this.amount = amount;
		this.invoice_number = invoice_number;
		this.name = name;
		this.entry_date = entry_date;
		this.party = party;
		this.mode_of_payment = mode_of_payment;
		this.via_payment = via_payment;
		this.note = note;
	}

	public int getPayment_receipt_id() {
		return payment_receipt_id;
	}

	public void setPayment_receipt_id(int payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
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
