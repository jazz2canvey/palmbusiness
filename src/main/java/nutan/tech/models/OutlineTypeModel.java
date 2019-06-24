package nutan.tech.models;

public class OutlineTypeModel {

	int outline_id, outline_type_code;
	String outline_name;
	
	public OutlineTypeModel() {
		
	}
	
	public OutlineTypeModel(int outline_id, int outline_type_code, String outline_name) {
		
		this.outline_id = outline_id;
		this.outline_type_code = outline_type_code;
		this.outline_name = outline_name;
	}

	public int getOutline_id() {
		return outline_id;
	}

	public void setOutline_id(int outline_id) {
		this.outline_id = outline_id;
	}

	public int getOutline_type_code() {
		return outline_type_code;
	}

	public void setOutline_type_code(int outline_type_code) {
		this.outline_type_code = outline_type_code;
	}

	public String getOutline_name() {
		return outline_name;
	}

	public void setOutline_name(String outline_name) {
		this.outline_name = outline_name;
	}
	
}
