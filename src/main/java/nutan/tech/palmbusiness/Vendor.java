package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.VendorCreator;
import nutan.tech.listmodel.VendorsListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.VendorModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/vendor")
@Produces(MediaType.APPLICATION_JSON)
public class Vendor {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newVendor(VendorsListModel vendorsList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<VendorModel> vendorsModelList = VendorCreator.getVendors();
		vendorsModelList.addAll(vendorsList.getVendorsList());
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();

		String query = " INSERT INTO db_palm_business.vendors (enterprise_id, honorific, full_name, enterprise_name, gstin, country, state_province, city, street, pincode_zipcode, email_main, email_ccc, landline, mobile, note)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = null;
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < vendorsModelList.size(); i++) {
				
				VendorModel vendorModel = vendorsModelList.get(i);

				preparedStmt.setString(1, vendorModel.getEnterprise_id());
				preparedStmt.setString(2, vendorModel.getHonorific());
				preparedStmt.setString(3, vendorModel.getFull_name());
				preparedStmt.setString(4, vendorModel.getEnterprise_name());
				preparedStmt.setString(5, vendorModel.getGstin());
				preparedStmt.setString(6, vendorModel.getCountry());
				preparedStmt.setString(7, vendorModel.getState_province());
				preparedStmt.setString(8, vendorModel.getCity());
				preparedStmt.setString(9, vendorModel.getStreet());
				preparedStmt.setString(10, vendorModel.getPincode_zipcode());
				preparedStmt.setString(11, vendorModel.getEmail_main());
				preparedStmt.setString(12, vendorModel.getEmail_ccc());
				preparedStmt.setString(13, vendorModel.getLandline());
				preparedStmt.setString(14, vendorModel.getMobile());
				preparedStmt.setString(15, vendorModel.getNote());
				preparedStmt.addBatch();
			}
			
			preparedStmt.executeBatch();
			connection.commit();
			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
			
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
		} 
		
		return callResultModelList;
	}
	
	@PUT
	@Path("/{enterprise_id}/{vendor_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyMyVendor(@PathParam("enterprise_id") String enterpriseId, @PathParam("vendor_id") int vendorId, VendorModel vendorModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.vendors SET db_palm_business.vendors.honorific = ?, db_palm_business.vendors.full_name = ?, db_palm_business.vendors.enterprise_name = ?, db_palm_business.vendors.gstin = ?, db_palm_business.vendors.country = ?, db_palm_business.vendors.state_province = ?, db_palm_business.vendors.city = ?, db_palm_business.vendors.street = ?, db_palm_business.vendors.pincode_zipcode = ?, db_palm_business.vendors.email_main = ?, db_palm_business.vendors.email_ccc = ?, db_palm_business.vendors.landline = ?, db_palm_business.vendors.mobile = ?, db_palm_business.vendors.note = ? WHERE db_palm_business.vendors.vendor_id = " + vendorId + " and db_palm_business.vendors.enterprise_id = '" + enterpriseId + "'";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
			preparedStmt.setString(1, vendorModel.getHonorific());
			preparedStmt.setString(2, vendorModel.getFull_name());
			preparedStmt.setString(3, vendorModel.getEnterprise_name());
			preparedStmt.setString(4, vendorModel.getGstin());
			preparedStmt.setString(5, vendorModel.getCountry());
			preparedStmt.setString(6, vendorModel.getState_province());
			preparedStmt.setString(7, vendorModel.getCity());
			preparedStmt.setString(8, vendorModel.getStreet());
			preparedStmt.setString(9, vendorModel.getPincode_zipcode());
			preparedStmt.setString(10, vendorModel.getEmail_main());
			preparedStmt.setString(11, vendorModel.getEmail_ccc());
			preparedStmt.setString(12, vendorModel.getLandline());
			preparedStmt.setString(13, vendorModel.getMobile());
			preparedStmt.setString(14, vendorModel.getNote());
				    
		    // execute the preparedstatement
		    preparedStmt.executeUpdate();
		    
			callResultModelList.add(new CallResultModel(false, true));
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}	
	
	@GET
	@Path("{enterprise_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<VendorModel> getAllVendors(@PathParam("enterprise_id") String enterpriseId) {

		List<VendorModel> vendorsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultset = null;
		
	    String query = "SELECT * FROM db_palm_business.vendors WHERE db_palm_business.vendors.enterprise_id = '" + enterpriseId + "'";
	  
	    try {

		    	statement = connection.createStatement();
			resultset = statement.executeQuery(query);
			
			while (resultset.next())
		      {
		    	  	vendorsList.add(new VendorModel(resultset.getInt("vendor_id"), resultset.getString("enterprise_id"), resultset.getString("honorific"), resultset.getString("full_name"), resultset.getString("enterprise_name"), resultset.getString("gstin"), resultset.getString("country"), resultset.getString("state_province"), resultset.getString("city"), resultset.getString("street"), resultset.getString("pincode_zipcode"), resultset.getString("email_main"), resultset.getString("email_ccc"), resultset.getString("landline"), resultset.getString("mobile"), resultset.getString("note")));
		      }
			
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultset);
			return null;
		}

        return vendorsList;
	}
	
	@DELETE
	@Path("{enterprise_id}/{vendor_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> deleteVendor(@PathParam("enterprise_id") String enterpriseId, @PathParam("vendor_id") int vendorId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.vendors WHERE db_palm_business.vendors.enterprise_id = ? and db_palm_business.vendors.vendor_id =  ?";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, enterpriseId);
		    preparedStmt.setInt (2, vendorId);
				    
		    preparedStmt.executeUpdate();
			callResultModelList.add(new CallResultModel(false, true));
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
}
