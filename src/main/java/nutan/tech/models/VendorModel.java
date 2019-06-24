package nutan.tech.models;

public class VendorModel {

	int vendor_id;
	String enterprise_id, honorific, full_name, enterprise_name, gstin, country, state_province, city, street, pincode_zipcode, email_main, email_ccc, landline, mobile, note;
	
	public VendorModel() {
		
	}
	
	public VendorModel(int vendor_id, String enterprise_id, String honorific, String full_name, String enterprise_name, String gstin, String country, String state_province, String city, String street, String pincode_zipcode, String email_main, String email_ccc, String landline, String mobile, String note) {
		
		this.vendor_id = vendor_id;
		this.enterprise_id = enterprise_id;
		this.honorific = honorific;
		this.full_name = full_name;
		this.enterprise_name = enterprise_name;
		this.gstin = gstin;
		this.country = country;
		this.state_province = state_province;
		this.city = city;
		this.street = street;
		this.pincode_zipcode = pincode_zipcode;
		this.email_main = email_main;
		this.email_ccc = email_ccc;
		this.landline = landline;
		this.mobile = mobile;
		this.note = note;
	}
	
	public int getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getHonorific() {
		return honorific;
	}

	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState_province() {
		return state_province;
	}

	public void setState_province(String state_province) {
		this.state_province = state_province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPincode_zipcode() {
		return pincode_zipcode;
	}

	public void setPincode_zipcode(String pincode_zipcode) {
		this.pincode_zipcode = pincode_zipcode;
	}

	public String getEmail_main() {
		return email_main;
	}

	public void setEmail_main(String email_main) {
		this.email_main = email_main;
	}

	public String getEmail_ccc() {
		return email_ccc;
	}

	public void setEmail_ccc(String email_ccc) {
		this.email_ccc = email_ccc;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
