package nutan.tech.models;

public class CustomersModel {

	int customer_id, customer_type_code;
	String enterprise_id, enterprise_name, person_name, gstin, mobile_number, address_country, address_state, address_city, address_street,  address_pincode_zipcode, address_landline, address_email_main, address_email_ccc;

	public CustomersModel() {
		
	}
	
	public CustomersModel(int customer_id, String enterprise_id, int customer_type_code, String enterprise_name, String person_name, String gstin, String mobile_number, String address_country, String address_state, String address_street, String address_city, String address_pincode_zipcode, String address_landline, String address_email_main, String address_email_ccc) {
		
		this.customer_id = customer_id;
		this.enterprise_id = enterprise_id ;
		this.customer_type_code = customer_type_code;
		this.enterprise_name = enterprise_name;
		this.person_name = person_name;
		this.gstin = gstin;
		this.mobile_number = mobile_number;
		this.address_country = address_country;
		this.address_state = address_state;
		this.address_street = address_street;
		this.address_city = address_city;
		this.address_pincode_zipcode = address_pincode_zipcode;
		this.address_landline = address_landline;
		this.address_email_main = address_email_main;
		this.address_email_ccc = address_email_ccc;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public int getCustomer_type_code() {
		return customer_type_code;
	}

	public void setCustomer_type_code(int customer_type_code) {
		this.customer_type_code = customer_type_code;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getAddress_country() {
		return address_country;
	}

	public void setAddress_country(String address_country) {
		this.address_country = address_country;
	}

	public String getAddress_state() {
		return address_state;
	}

	public void setAddress_state(String address_state) {
		this.address_state = address_state;
	}

	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}

	public String getAddress_city() {
		return address_city;
	}

	public void setAddress_city(String address_city) {
		this.address_city = address_city;
	}

	public String getAddress_pincode_zipcode() {
		return address_pincode_zipcode;
	}

	public void setAddress_pincode_zipcode(String address_pincode_zipcode) {
		this.address_pincode_zipcode = address_pincode_zipcode;
	}

	public String getAddress_landline() {
		return address_landline;
	}

	public void setAddress_landline(String address_landline) {
		this.address_landline = address_landline;
	}

	public String getAddress_email_main() {
		return address_email_main;
	}

	public void setAddress_email_main(String address_email_main) {
		this.address_email_main = address_email_main;
	}

	public String getAddress_email_ccc() {
		return address_email_ccc;
	}

	public void setAddress_email_ccc(String address_email_ccc) {
		this.address_email_ccc = address_email_ccc;
	}
	
}
