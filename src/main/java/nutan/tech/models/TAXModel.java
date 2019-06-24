package nutan.tech.models;

public class TAXModel {

	int gst_affiliation_id; 
	float composition_charge;
	String enterprise_id, gst_scheme_id, gstin;
	
	public TAXModel() {
		
	}
	
	public TAXModel(int gst_affiliation_id, float composition_charge, String enterprise_id, String gst_scheme_id, String gstin) {
		
		this.gst_affiliation_id = gst_affiliation_id;
		this.enterprise_id = enterprise_id;
		this.composition_charge = composition_charge;
		this.gst_scheme_id = gst_scheme_id;
		this.gstin = gstin;
	}

	public int getGst_affiliation_id() {
		return gst_affiliation_id;
	}

	public void setGst_affiliation_id(int gst_affiliation_id) {
		this.gst_affiliation_id = gst_affiliation_id;
	}

	public float getComposition_charge() {
		return composition_charge;
	}

	public void setComposition_charge(float composition_charge) {
		this.composition_charge = composition_charge;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getGst_scheme_id() {
		return gst_scheme_id;
	}

	public void setGst_scheme_id(String gst_scheme_id) {
		this.gst_scheme_id = gst_scheme_id;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	
}
