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
import nutan.tech.models.ItemsModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class Items {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	public List<ItemsModel> newItems(final ItemsModel itemsModel) {
		
		final List<ItemsModel> itemsList = new ArrayList<>();
		
		Thread insert2Database = new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						
						Timestamp lastModified = new Timestamp(new Date().getTime());
						databaseFunctions = new DatabaseFuntions();
						Connection connection = databaseFunctions.connect2DB();
						String query = " insert into palm_business.items_5 (enterprise_id, item_name, item_description, item_type, units, purchase_price, sgst, cgst, igst, hsn_o_sac, last_modified)"
						        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

						PreparedStatement preparedStmt = null;
						try {
							preparedStmt = connection.prepareStatement(query);
							
						    preparedStmt.setInt (1, itemsModel.getEnterprise_id());
						    preparedStmt.setString (2, itemsModel.getItem_name());
						    preparedStmt.setString (3, itemsModel.getItem_description());
						    preparedStmt.setInt (4, itemsModel.getItem_type());
						    preparedStmt.setDouble (5, itemsModel.getUnits());
						    preparedStmt.setDouble (6, itemsModel.getPurchase_price());
						    preparedStmt.setFloat (7, itemsModel.getSgst());
						    preparedStmt.setFloat (8, itemsModel.getCgst());
						    preparedStmt.setFloat (9, itemsModel.getIgst());
						    preparedStmt.setString (10, itemsModel.getHsn_o_sac());
						    preparedStmt.setTimestamp(11, lastModified);
								    
						    preparedStmt.execute();
						    
						    String[] dateTime = lastModified.toString().split("\\.");
						    
						    String getQuery = "SELECT * FROM palm_business.items_5 where items_5.enterprise_id = " + itemsModel.getEnterprise_id() + " and items_5.item_name = " + "\"" + itemsModel.getItem_name() + "\"" + " and items_5.last_modified = " + "\"" + dateTime[0] + "\"";
						    
						    Statement st = connection.createStatement();
						    ResultSet resultSet = st.executeQuery(getQuery);
						    
						      while (resultSet.next())
						      {
						    		itemsList.add(new ItemsModel(resultSet.getInt("item_id"), resultSet.getInt("enterprise_id"), resultSet.getInt("item_type"), resultSet.getFloat("sgst"), resultSet.getFloat("cgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("item_description"), resultSet.getString("hsn_o_sac"), resultSet.getDouble("units"), resultSet.getDouble("purchase_price")));
						      }
						      st.close();
						    
							databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
						
						} catch (SQLException e) {
							e.printStackTrace();
							itemsList.add(new ItemsModel());
							return;
						}
						
					}
				});

			try {
				insert2Database.start();
				insert2Database.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				itemsList.add(new ItemsModel());
			}
				
		return itemsList;
	}
	
	
/*	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newItems(ItemsListModel itemsList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<ItemsModel> itemsModelList = ItemCreator.getItems();
		itemsModelList.addAll(itemsList.getItemsList());

		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		PreparedStatement preparedStmt = null;

		String query = " INSERT INTO palm_business.items_5 (enterprise_id, item_name, item_description, item_type, units, purchase_price, hsn_o_sac)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < itemsModelList.size(); i++) {
				
				ItemsModel itemsModel = itemsModelList.get(i);

				preparedStmt.setInt(1, itemsModel.getEnterprise_id());
				preparedStmt.setString(2, itemsModel.getItem_name());
				preparedStmt.setString(3, itemsModel.getItem_description());
				preparedStmt.setInt(4, itemsModel.getItem_type());
				preparedStmt.setDouble(5, itemsModel.getUnits());
				preparedStmt.setDouble(6, itemsModel.getPurchase_price());
				preparedStmt.setString(7, itemsModel.getHsn_o_sac());
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
	}		*/
	
	@PUT
	@Path("{enterprise_id}/{item_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyItem(@PathParam("enterprise_id") int enterpriseId, @PathParam("item_id") int itemId, ItemsModel itemsModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.items_5 SET item_name = ?, item_description = ?, item_type = ?, units = ?, purchase_price = ?, sgst = ?, cgst = ?, igst = ?, hsn_o_sac = ? WHERE items_5.enterprise_id = " + enterpriseId + " and items_5.item_id = " + itemId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, itemsModel.getItem_name());
		    preparedStmt.setString (2, itemsModel.getItem_description());
		    preparedStmt.setInt (3, itemsModel.getItem_type());
		    preparedStmt.setDouble (4, itemsModel.getUnits());
		    preparedStmt.setDouble (5, itemsModel.getPurchase_price());
		    preparedStmt.setDouble (6, itemsModel.getSgst());
		    preparedStmt.setDouble (7, itemsModel.getCgst());
		    preparedStmt.setDouble (8, itemsModel.getIgst());
		    preparedStmt.setString (9, itemsModel.getHsn_o_sac());
				    
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
	
	@DELETE
	@Path("{enterprise_id}/{item_id}")
	public List<CallResultModel> deleteItem(@PathParam("enterprise_id") int enterpriseId, @PathParam("item_id") int itemId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.items_5 WHERE item_id = ? and enterprise_id =  ?";

		System.out.println("Query: " + query);
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, itemId);
		    preparedStmt.setInt (2, enterpriseId);
				    
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
	public List<ItemsModel> getAllItems(@PathParam("enterprise_id") int enterpriseId) {
		
		List<ItemsModel> itemsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT * FROM palm_business.items_5 WHERE items_5.enterprise_id = " + enterpriseId;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			itemsList.add(new ItemsModel(resultSet.getInt("item_id"), resultSet.getInt("enterprise_id"), resultSet.getInt("item_type"), resultSet.getFloat("sgst"), resultSet.getFloat("cgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("item_description"), resultSet.getString("hsn_o_sac"), resultSet.getDouble("units"), resultSet.getDouble("purchase_price")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}

    return itemsList;
	    
	}
	
	@GET
	@Path("{enterprise_id}/{item_id}")
	public List<ItemsModel> getItem(@PathParam("enterprise_id") int enterpriseId, @PathParam("item_id") int itemId) {
		
		List<ItemsModel> itemsList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT * FROM palm_business.items_5 WHERE items_5.enterprise_id = " + enterpriseId + " and items_5.item_id = " + itemId;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			itemsList.add(new ItemsModel(resultSet.getInt("item_id"), resultSet.getInt("enterprise_id"), resultSet.getInt("item_type"), resultSet.getFloat("sgst"), resultSet.getFloat("cgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("item_description"), resultSet.getString("hsn_o_sac"), resultSet.getDouble("units"), resultSet.getDouble("purchase_price")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}

    return itemsList;
	    
	}
	
}
