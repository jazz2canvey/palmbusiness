package nutan.tech.models;

public class InvoiceNumberModel {

	String invoice_number;
	
	public InvoiceNumberModel() {
		
	}

	public InvoiceNumberModel(String invoice_number) {
		
		this.invoice_number = invoice_number;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}
	
}
