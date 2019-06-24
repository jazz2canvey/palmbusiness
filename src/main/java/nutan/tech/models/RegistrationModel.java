package nutan.tech.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nutan.tech.utilities.APIUtilities;

public class RegistrationModel {

	int sr_no;
	String enterprise_id, enterprise_logo, enterprise_name, fiscal_year, date_format, address_street_1, address_street_2, address_city, address_state, address_country, 
	address_pincode, phone, email_main, password, email_ccc, website;
	boolean enterprise_logged_in = false;
	
	public RegistrationModel() {
		
	}
	
	public RegistrationModel(int sr_no, String enterprise_id, String enterprise_logo, String enterprise_name, String fiscal_year, String date_format, String address_street_1, String address_street_2, String address_city, String address_state, String address_country, String address_pincode, String phone, String email_main, String password, String email_ccc, String website, boolean enterprise_logged_in) {

		this.sr_no = sr_no;
		this.enterprise_id = "ENP" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
		this.enterprise_logo = enterprise_logo;
		this.enterprise_name = enterprise_name;
		this.fiscal_year = fiscal_year;
		this.date_format = date_format;
		this.address_street_1 = address_street_1;
		this.address_street_2 = address_street_2;
		this.address_city = address_city;
		this.address_state = address_state;
		this.address_country = address_country;
		this.address_pincode = address_pincode;
		this.phone = phone;
		this.email_main = email_main;
		this.password = password;
		this.email_ccc = email_ccc;
		this.website = website;
		this.enterprise_logged_in = enterprise_logged_in;
	}

	public int getSr_no() {
		return sr_no;
	}

	public void setSr_no(int sr_no) {
		this.sr_no = sr_no;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEnterprise_logo() {
		return enterprise_logo;
	}

	public void setEnterprise_logo(String enterprise_logo) {
		this.enterprise_logo = enterprise_logo;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getFiscal_year() {
		return fiscal_year;
	}

	public void setFiscal_year(String fiscal_year) {
		this.fiscal_year = fiscal_year;
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

	public String getAddress_street_1() {
		return address_street_1;
	}

	public void setAddress_street_1(String address_street_1) {
		this.address_street_1 = address_street_1;
	}

	public String getAddress_street_2() {
		return address_street_2;
	}

	public void setAddress_street_2(String address_street_2) {
		this.address_street_2 = address_street_2;
	}

	public String getAddress_city() {
		return address_city;
	}

	public void setAddress_city(String address_city) {
		this.address_city = address_city;
	}

	public String getAddress_state() {
		return address_state;
	}

	public void setAddress_state(String address_state) {
		this.address_state = address_state;
	}

	public String getAddress_country() {
		return address_country;
	}

	public void setAddress_country(String address_country) {
		this.address_country = address_country;
	}

	public String getAddress_pincode() {
		return address_pincode;
	}

	public void setAddress_pincode(String address_pincode) {
		this.address_pincode = address_pincode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail_main() {
		return email_main;
	}

	public void setEmail_main(String email_main) {
		this.email_main = email_main;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail_ccc() {
		return email_ccc;
	}

	public void setEmail_ccc(String email_ccc) {
		this.email_ccc = email_ccc;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public boolean isEnterprise_logged_in() {
		return enterprise_logged_in;
	}

	public void setEnterprise_logged_in(boolean enterprise_logged_in) {
		this.enterprise_logged_in = enterprise_logged_in;
	}
	
}
