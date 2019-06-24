package nutan.tech.models;

public class PaymentViaModel {

	int payment_via_id, payment_via_code;
	String payment_via;
	
	public PaymentViaModel() {
		
	}
	
	public PaymentViaModel(int payment_via_id, int payment_via_code, String payment_via) {
		
		this.payment_via_id = payment_via_id;
		this.payment_via_code = payment_via_code;
		this.payment_via = payment_via;
	}

	public int getPayment_via_id() {
		return payment_via_id;
	}

	public void setPayment_via_id(int payment_via_id) {
		this.payment_via_id = payment_via_id;
	}

	public int getPayment_via_code() {
		return payment_via_code;
	}

	public void setPayment_via_code(int payment_via_code) {
		this.payment_via_code = payment_via_code;
	}

	public String getPayment_via() {
		return payment_via;
	}

	public void setPayment_via(String payment_via) {
		this.payment_via = payment_via;
	}
	
}
