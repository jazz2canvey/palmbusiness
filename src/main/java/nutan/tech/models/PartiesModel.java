package nutan.tech.models;

public class PartiesModel {

	int party_id, party_type_code;
	String party_type;
	
	public PartiesModel() {
		
	}
	
	public PartiesModel(int party_id, int party_type_code, String party_type) {
		
		this.party_id = party_id;
		this.party_type_code = party_type_code;
		this.party_type = party_type;
	}

	public int getParty_id() {
		return party_id;
	}

	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}

	public int getParty_type_code() {
		return party_type_code;
	}

	public void setParty_type_code(int party_type_code) {
		this.party_type_code = party_type_code;
	}

	public String getParty_type() {
		return party_type;
	}

	public void setParty_type(String party_type) {
		this.party_type = party_type;
	}
	
}
