package nutan.tech.models;

public class DiscountTypeModel {

	String discount_type;
	int discount_type_id;
	
	public DiscountTypeModel() {
		
	}
	
	public DiscountTypeModel(String discount_type, int discount_type_id) {
		
		this.discount_type = discount_type;
		this.discount_type_id = discount_type_id;
	}

	public String getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(String discount_type) {
		this.discount_type = discount_type;
	}

	public int getDiscount_type_id() {
		return discount_type_id;
	}

	public void setDiscount_type_id(int discount_type_id) {
		this.discount_type_id = discount_type_id;
	}
	
}
