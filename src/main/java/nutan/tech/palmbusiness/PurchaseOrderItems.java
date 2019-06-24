package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import nutan.tech.models.CallResultModel;
import nutan.tech.models.PurchaseOrderItemsModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/purchase_order_items")
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseOrderItems {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PurchaseOrderItemsModel> newPurchaseOrderItem(PurchaseOrderItemsModel purchaseOrderItemsModel) {

		List<PurchaseOrderItemsModel> purchaseOrderItemsList = new ArrayList<>();		
		
		Timestamp lastModified = new Timestamp(new Date().getTime());
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		String query = " insert into palm_business.purchase_order_items_25 (item_type, item_name, quantity, purchase_price, cgst, sgst, igst, hsn_o_sac, item_description, last_modified)"
		        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt (1, purchaseOrderItemsModel.getItem_type());
		    preparedStmt.setString (2, purchaseOrderItemsModel.getItem_name());
		    preparedStmt.setFloat (3, purchaseOrderItemsModel.getQuantity());
		    preparedStmt.setDouble (4, purchaseOrderItemsModel.getPurchase_price());
		    preparedStmt.setFloat (5, purchaseOrderItemsModel.getCgst());
		    preparedStmt.setFloat (6, purchaseOrderItemsModel.getSgst());
		    preparedStmt.setFloat (7, purchaseOrderItemsModel.getIgst());
		    preparedStmt.setString (8, purchaseOrderItemsModel.getHsn_o_sac());
		    preparedStmt.setString (9, purchaseOrderItemsModel.getItem_description());
		    preparedStmt.setTimestamp (10, lastModified);
				    
		    preparedStmt.execute();
		    
		    String[] dateTime = lastModified.toString().split("\\.");
		    String getQuery = "SELECT * FROM palm_business.purchase_order_items_25 where palm_business.purchase_order_items_25.item_name = '" + purchaseOrderItemsModel.getItem_name() + "' and palm_business.purchase_order_items_25.last_modified = '" + dateTime[0] + "'";

		    Statement st = connection.createStatement();
		    ResultSet resultSet = st.executeQuery(getQuery);
		    
		      while (resultSet.next())
		      {
		    	  	purchaseOrderItemsList.add(new PurchaseOrderItemsModel(resultSet.getInt("purchase_order_item_id"), resultSet.getInt("item_type"), resultSet.getFloat("quantity"), resultSet.getDouble("purchase_price"), resultSet.getFloat("cgst"), resultSet.getFloat("sgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("hsn_o_sac"), resultSet.getString("item_description")));
		      }
		      st.close();
		    
			databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
		
		} catch (SQLException e) {
			e.printStackTrace();
			purchaseOrderItemsList.add(new PurchaseOrderItemsModel());
		}

		return purchaseOrderItemsList;
	}
	
	@PUT
	@Path("{purchase_order_item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PurchaseOrderItemsModel> updatePurchaseOrderItem(@PathParam("purchase_order_item_id") int purchaseOrderItemId, PurchaseOrderItemsModel purchaseOrderItemsModel) {

		List<PurchaseOrderItemsModel> purchaseOrderItemsList = new ArrayList<>();		
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "UPDATE palm_business.purchase_order_items_25 SET palm_business.purchase_order_items_25.item_name = ?, palm_business.purchase_order_items_25.quantity = ?, palm_business.purchase_order_items_25.purchase_price = ?, palm_business.purchase_order_items_25.cgst = ?, palm_business.purchase_order_items_25.sgst, palm_business.purchase_order_items_25.igst = ?, palm_business.purchase_order_items_25.hsn_o_sac = ? WHERE palm_business.purchase_order_items_25.purchase_order_item_id = " + purchaseOrderItemId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, purchaseOrderItemsModel.getItem_name());
		    preparedStmt.setFloat (2, purchaseOrderItemsModel.getQuantity());
		    preparedStmt.setDouble (3, purchaseOrderItemsModel.getPurchase_price());
		    preparedStmt.setFloat (4, purchaseOrderItemsModel.getCgst());
		    preparedStmt.setFloat (5, purchaseOrderItemsModel.getSgst());
		    preparedStmt.setFloat (6, purchaseOrderItemsModel.getIgst());
		    preparedStmt.setString (7, purchaseOrderItemsModel.getHsn_o_sac());
		    preparedStmt.executeUpdate();
		    
		    String getQuery = "SELECT * FROM palm_business.purchase_order_items_25 where palm_business.purchase_order_items_25.purchase_order_item_id = " + purchaseOrderItemId;
		    Statement st = connection.createStatement();
		    ResultSet resultSet = st.executeQuery(getQuery);
		    
		      while (resultSet.next())
		      {
		    	  	purchaseOrderItemsList.add(new PurchaseOrderItemsModel(resultSet.getInt("purchase_order_item_id"), resultSet.getInt("item_type"), resultSet.getFloat("quantity"), resultSet.getDouble("purchase_price"), resultSet.getFloat("cgst"), resultSet.getFloat("sgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("hsn_o_sac"), resultSet.getString("item_description")));
		      }
		      st.close();
		    
			databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
		
		} catch (SQLException e) {
			e.printStackTrace();
			purchaseOrderItemsList.add(new PurchaseOrderItemsModel());
		}

		return purchaseOrderItemsList;
	}
	
	@GET
	@Path("{purchase_order_item_id}")
	public List<PurchaseOrderItemsModel> getSingleItem(@PathParam("purchase_order_item_id") int purchaseOrderItemId) {
		
		List<PurchaseOrderItemsModel> purchaseOrderItemsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT * FROM palm_business.purchase_order_items_25 where palm_business.purchase_order_items_25.purchase_order_item_id = " + purchaseOrderItemId;
		
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			purchaseOrderItemsList.add(new PurchaseOrderItemsModel(resultSet.getInt("purchase_order_item_id"), resultSet.getInt("item_type"), resultSet.getFloat("quantity"), resultSet.getDouble("purchase_price"), resultSet.getFloat("cgst"), resultSet.getFloat("sgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("hsn_o_sac"), resultSet.getString("item_description")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
	
	    return purchaseOrderItemsList;
	}
	
	@DELETE
	@Path("{purchase_order_item_id}")
	public List<CallResultModel> deleteItem(@PathParam("purchase_order_item_id") int itemId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.purchase_order_items_25 WHERE purchase_order_items_25.purchase_order_item_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setInt (1, itemId);
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
