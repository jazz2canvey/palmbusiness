package nutan.tech.models;

public class PaymentModeModel {

	int payment_mode_id, payment_mode_code;
	String payment_mode;
	
	public PaymentModeModel() {
		
	}
	
	public PaymentModeModel(int payment_mode_id, int payment_mode_code, String payment_mode) {
		
		this.payment_mode_id = payment_mode_id;
		this.payment_mode_code = payment_mode_code;
		this.payment_mode = payment_mode;
	}

	public int getPayment_mode_id() {
		return payment_mode_id;
	}

	public void setPayment_mode_id(int payment_mode_id) {
		this.payment_mode_id = payment_mode_id;
	}

	public int getPayment_mode_code() {
		return payment_mode_code;
	}

	public void setPayment_mode_code(int payment_mode_code) {
		this.payment_mode_code = payment_mode_code;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	
}
