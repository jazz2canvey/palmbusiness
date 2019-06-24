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
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.CustomerCreator;
import nutan.tech.listmodel.CustomersListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.CustomersModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/customer")
public class Customers {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newCustomer(CustomersListModel customersList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<CustomersModel> customersModelList = CustomerCreator.getCustomers();
		customersModelList.addAll(customersList.getCustomersList());
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();

		PreparedStatement preparedStmt = null;

		String query = "INSERT INTO db_palm_business.customers (enterprise_id, customer_type_code, enterprise_name, person_name, gstin, mobile_number, address_country, address_state, address_street, address_city, address_pincode_zipcode, address_landline, address_email_main, address_email_ccc)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < customersModelList.size(); i++) {
				
				CustomersModel customersModel = customersModelList.get(i);

				preparedStmt.setString(1, customersModel.getEnterprise_id());
				preparedStmt.setInt(2, customersModel.getCustomer_type_code());
				preparedStmt.setString(3, customersModel.getEnterprise_name());
				preparedStmt.setString(4, customersModel.getPerson_name());
				preparedStmt.setString(5, customersModel.getGstin());
				preparedStmt.setString(6, customersModel.getMobile_number());
				preparedStmt.setString(7, customersModel.getAddress_country());
				preparedStmt.setString(8, customersModel.getAddress_state());
				preparedStmt.setString(9, customersModel.getAddress_street());
				preparedStmt.setString(10, customersModel.getAddress_city());
				preparedStmt.setString(11, customersModel.getAddress_pincode_zipcode());
				preparedStmt.setString(12, customersModel.getAddress_landline());
				preparedStmt.setString(13, customersModel.getAddress_email_main());
				preparedStmt.setString(14, customersModel.getAddress_email_ccc());
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
	@Path("{enterprise_id}/{customer_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_id") int customerId, CustomersModel customersModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.customers SET customers.enterprise_id = ?, customers.customer_type_code = ?, customers.enterprise_name = ?, customers.person_name = ?, customers.gstin = ?, customers.mobile_number = ?, customers.address_country = ?, customers.address_state = ?, customers.address_street = ?, customers.address_city = ?, customers.address_pincode_zipcode = ?, customers.address_landline = ?, customers.address_email_main = ?, customers.address_email_ccc = ? WHERE customers.enterprise_id = '" + enterpriseId + "' and  customers.customer_id = " + customerId;

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, customersModel.getEnterprise_id());
		    preparedStmt.setInt (2, customersModel.getCustomer_type_code());
		    preparedStmt.setString (3, customersModel.getEnterprise_name());
		    preparedStmt.setString (4, customersModel.getPerson_name());
		    preparedStmt.setString (5, customersModel.getGstin());
		    preparedStmt.setString (6, customersModel.getMobile_number());
		    preparedStmt.setString (7, customersModel.getAddress_country());
		    preparedStmt.setString (8, customersModel.getAddress_state());
		    preparedStmt.setString (9, customersModel.getAddress_street());
		    preparedStmt.setString (10, customersModel.getAddress_city());
		    preparedStmt.setString (11, customersModel.getAddress_pincode_zipcode());
		    preparedStmt.setString (12, customersModel.getAddress_landline());
		    preparedStmt.setString (13, customersModel.getAddress_email_main());
		    preparedStmt.setString (14, customersModel.getAddress_email_ccc());
				    
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
	public List<CustomersModel> getCustomers(@PathParam("enterprise_id") String enterpriseId) {
		
		List<CustomersModel> customersList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT * FROM db_palm_business.customers WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "'";

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);		

		while (resultSet.next())	{

			int customer_id = resultSet.getInt("customer_id");
			String enterprise_id = resultSet.getString("enterprise_id");
			int customer_type_code = resultSet.getInt("customer_type_code");
			String enterprise_name = resultSet.getString("enterprise_name");
			String person_name = resultSet.getString("person_name");
			String gstin = resultSet.getString("gstin");
			String mobile_number = resultSet.getString("mobile_number");
			String address_country = resultSet.getString("address_country");
			String address_state = resultSet.getString("address_state");
			String address = resultSet.getString("address_street");
			String address_city = resultSet.getString("address_city");
			String address_pincode = resultSet.getString("address_pincode_zipcode");
			String address_landline = resultSet.getString("address_landline");
			String address_email_main = resultSet.getString("address_email_main");
			String address_email_ccc = resultSet.getString("address_email_ccc");
			
			customersList.add(new CustomersModel(customer_id, enterprise_id, customer_type_code, enterprise_name, person_name, gstin, mobile_number, address_country, address_state, address, address_city, address_pincode, address_landline, address_email_main, address_email_ccc));
		}
	
		databaseFunctions.closeDBOperations(connection, statement, resultSet);		

		} catch (SQLException e) {
			
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}		
	
	    return customersList;		
	}

	@GET
	@Path("{enterprise_id}/{customer_type}")
	public List<CustomersModel> getCustomersByType(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_type") int customerType) {
		
		List<CustomersModel> customersList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT * FROM db_palm_business.customers WHERE db_palm_business.customers.enterprise_id = '" + enterpriseId + "' AND db_palm_business.customers.customer_type_code = " + customerType;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);		

		while (resultSet.next())	{

			int customer_id = resultSet.getInt("customer_id");
			String enterprise_id = resultSet.getString("enterprise_id");
			int customer_type_code = resultSet.getInt("customer_type_code");
			String enterprise_name = resultSet.getString("enterprise_name");
			String person_name = resultSet.getString("person_name");
			String gstin = resultSet.getString("gstin");
			String mobile_number = resultSet.getString("mobile_number");
			String address_country = resultSet.getString("address_country");
			String address_state = resultSet.getString("address_state");
			String address = resultSet.getString("address_street");
			String address_city = resultSet.getString("address_city");
			String address_pincode = resultSet.getString("address_pincode_zipcode");
			String address_landline = resultSet.getString("address_landline");
			String address_email_main = resultSet.getString("address_email_main");
			String address_email_ccc = resultSet.getString("address_email_ccc");
			
			customersList.add(new CustomersModel(customer_id, enterprise_id, customer_type_code, enterprise_name, person_name, gstin, mobile_number, address_country, address_state, address, address_city, address_pincode, address_landline, address_email_main, address_email_ccc));
		}
	
		databaseFunctions.closeDBOperations(connection, statement, resultSet);		

		} catch (SQLException e) {
			
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}		
	
	    return customersList;		
	}
	
	@DELETE
	@Path("{enterprise_id}/{customer_id}")
	public List<CallResultModel> deleteCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("customer_id") int customerId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.customers WHERE customers.customer_id = ? and customers.enterprise_id =  ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, customerId);
		    preparedStmt.setString (2, enterpriseId);
				    
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
	
}
