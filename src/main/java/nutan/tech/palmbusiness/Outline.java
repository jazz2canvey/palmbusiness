package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import nutan.tech.creator.OutlineCreator;
import nutan.tech.listmodel.OutlinesListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.OutlinesModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/outlines")
public class Outline {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newOutline(OutlinesListModel outlinesList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<OutlinesModel> outlinesModelList = OutlineCreator.getOutlines();
		outlinesModelList.addAll(outlinesList.getOutlinesList());
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		PreparedStatement preparedStmt = null;
		
		String query = " INSERT INTO palm_business.outlines_13 (enterprise_id, customer_id, outlined_item_id, outline_type_code, outline_number, entry_date, valid_date, sales_rep_name, note)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < outlinesModelList.size(); i++) {
				
				OutlinesModel outlineModel = outlinesModelList.get(i);

			    preparedStmt.setInt (1, outlineModel.getEnterprise_id());
			    preparedStmt.setInt (2, outlineModel.getCustomer_id());
			    preparedStmt.setInt (3, outlineModel.getOutlined_item_id());
			    preparedStmt.setInt (4, outlineModel.getOutline_type_code());
			    preparedStmt.setString (5, outlineModel.getOutline_number());
			    preparedStmt.setString (6, outlineModel.getEntry_date());
			    preparedStmt.setString (7, outlineModel.getValid_date());
			    preparedStmt.setString (8, outlineModel.getSales_rep_name());
			    preparedStmt.setString (9, outlineModel.getNote());				    
			    preparedStmt.addBatch();
			}
			
			preparedStmt.executeBatch();
			connection.commit();
			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
	@PUT
	@Path("{enterprise_id}/{outline_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyOutline(@PathParam("enterprise_id") int enterpriseId, @PathParam("outline_id") int outlineId, OutlinesModel outlineModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.outlines_13 SET enterprise_id = ?, customer_id = ?, item_id = ?, outline_type_code = ?, outline_number = ?, entry_date = ?, valid_till = ?, sales_rep_name = ?, quantity = ?, unit_price = ?, discount = ?, gst = ?, igst = ?, sub_total = ?, grand_total = ?, note = ? WHERE outlines_13.enterprise_id = " + enterpriseId + " and outlines_13.outline_id = " + outlineId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, outlineModel.getEnterprise_id());
		    preparedStmt.setInt (2, outlineModel.getCustomer_id());
		    preparedStmt.setInt (3, outlineModel.getOutlined_item_id());
		    preparedStmt.setInt (4, outlineModel.getOutline_type_code());
		    preparedStmt.setString (5, outlineModel.getOutline_number());
		    preparedStmt.setString (6, outlineModel.getEntry_date());
		    preparedStmt.setString (7, outlineModel.getValid_date());
		    preparedStmt.setString (8, outlineModel.getSales_rep_name());
		    preparedStmt.setString (16, outlineModel.getNote());
				    
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
	@Path("{enterprise_id}/{outline_type}")
	public List<OutlinesModel> getAllOutlines(@PathParam("outline_type") int outlineType, @PathParam("enterprise_id") int enterpriseId) {

		List<OutlinesModel> outlinesList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		Set<String> keys = new HashSet<>();
		MultivaluedHashMap<String, OutlinesModel> map = new MultivaluedHashMap<>();
		Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT palm_business.outlines_13.enterprise_id, palm_business.outlines_13.customer_id, palm_business.outlines_13.outline_id, palm_business.outlines_13.outlined_item_id, palm_business.outlines_13.outline_type_code, palm_business.outlines_13.outline_number, palm_business.outlines_13.entry_date, palm_business.outlines_13.valid_date, palm_business.outlines_13.sales_rep_name, palm_business.outlines_13.note, (CASE WHEN palm_business.customers_11.enterprise_name != null THEN palm_business.customers_11.enterprise_name ELSE palm_business.customers_11.person_name END) AS customer_name, (CASE WHEN palm_business.outline_items_26.igst > 0 THEN ((((palm_business.outline_items_26.quantity * palm_business.outline_items_26.sale_price) * palm_business.outline_items_26.igst) / 100) + (palm_business.outline_items_26.quantity * palm_business.outline_items_26.sale_price)) ELSE ((((palm_business.outline_items_26.quantity * palm_business.outline_items_26.sale_price) * (palm_business.outline_items_26.sgst + palm_business.outline_items_26.cgst) / 100) + (palm_business.outline_items_26.quantity * palm_business.outline_items_26.sale_price))) END) AS grand_total FROM palm_business.outlines_13 LEFT JOIN palm_business.customers_11 ON palm_business.outlines_13.customer_id = palm_business.customers_11.customer_id LEFT JOIN palm_business.outline_items_26 ON palm_business.outlines_13.outlined_item_id = palm_business.outline_items_26.outline_item_id WHERE palm_business.outlines_13.enterprise_id = " + 4 + " AND palm_business.outlines_13.outline_type_code = " + outlineType;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			int enterprise_id = resultSet.getInt("enterprise_id");
			int customer_id = resultSet.getInt("customer_id");
			int outline_id = resultSet.getInt("outline_id");
			int outlined_item_id = resultSet.getInt("outlined_item_id");
			int outline_type_code = resultSet.getInt("outline_type_code");
			String customer_name = resultSet.getString("customer_name");
			String outline_number = resultSet.getString("outline_number");
			String entry_date = resultSet.getString("entry_date");
			String valid_date = resultSet.getString("valid_date");
			String sales_rep_name = resultSet.getString("sales_rep_name");
			String note = resultSet.getString("note");
			double grand_total = Double.parseDouble(resultSet.getString("grand_total"));
					
			OutlinesModel model = new OutlinesModel(outline_id, enterprise_id, customer_id, outlined_item_id, outline_type_code, grand_total, customer_name, outline_number, entry_date, valid_date, sales_rep_name, note);
			String key = String.valueOf(customer_id + ", " + outline_number);
			map.add(key, model);
			keys.add(key);
	      }
		
		for (int i = 0; i < keys.size(); i++) {

			double grandTotal = 0;
			List<OutlinesModel> list = map.get(new ArrayList<>(keys).get(i));
			
			for (int z = 0; z < list.size(); z++)
				grandTotal = grandTotal + list.get(z).getGrand_total();				
			
			outlinesList.add(new OutlinesModel(list.get(0).getOutline_id(), list.get(0).getEnterprise_id(), list.get(0).getCustomer_id(), list.get(0).getOutlined_item_id(), list.get(0).getOutline_type_code(), grandTotal, list.get(0).getCustomer_name(), list.get(0).getOutline_number(), list.get(0).getEntry_date(), list.get(0).getValid_date(), list.get(0).getSales_rep_name(), list.get(0).getNote()));
		}
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
			return outlinesList;
		
	}
	
	@DELETE
	@Path("{enterprise_id}/{outline_id}")
	public List<CallResultModel> deleteOutline(@PathParam("outline_id") int outlineId, @PathParam("enterprise_id") int enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.outlines_13 WHERE outlines_13.enterprise_id = ? and outlines_13.outline_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, enterpriseId);
		    preparedStmt.setInt (2, outlineId);
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

