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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.models.SoldItemsModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/sold_items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SoldItems {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Path("/add")
	public List<SoldItemsModel> newSoldItem(SoldItemsModel soldItemsModel) {
		
		List<SoldItemsModel> soldItemsList = new ArrayList<>();
		
		Timestamp lastModified = new Timestamp(new Date().getTime());
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		String query = " insert into palm_business.sold_items_24 (item_id, units, sale_price, sgst, cgst, igst, hsn_o_sac, last_modified)"
		        + " values (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, soldItemsModel.getItem_id());
		    preparedStmt.setFloat (2, soldItemsModel.getUnits());
		    preparedStmt.setFloat (3, soldItemsModel.getSale_price());
		    preparedStmt.setFloat (4, soldItemsModel.getSgst());
		    preparedStmt.setFloat (5, soldItemsModel.getCgst());
		    preparedStmt.setFloat (6, soldItemsModel.getIgst());
		    preparedStmt.setString (7, soldItemsModel.getHsn_o_sac());
		    preparedStmt.setTimestamp(8, lastModified);
				    
		    preparedStmt.execute();
		    
		    String[] dateTime = lastModified.toString().split("\\.");
		    
	    		String getQuery = "SELECT palm_business.sold_items_24.sold_item_id, palm_business.sold_items_24.item_id, palm_business.sold_items_24.units, palm_business.sold_items_24.sale_price, palm_business.sold_items_24.sgst, palm_business.sold_items_24.cgst, palm_business.sold_items_24.igst, palm_business.sold_items_24.hsn_o_sac, palm_business.items_5.item_name FROM palm_business.sold_items_24 LEFT JOIN palm_business.items_5 ON palm_business.sold_items_24.item_id = palm_business.items_5.item_id where palm_business.sold_items_24.item_id = " + soldItemsModel.getItem_id() + " and sold_items_24.last_modified = " + "\"" + dateTime[0] + "\"";
		    		
		    Statement st = connection.createStatement();
		    ResultSet resultSet = st.executeQuery(getQuery);
		    
		      while (resultSet.next())
		      {
		    	  	soldItemsList.add(new SoldItemsModel(resultSet.getInt("sold_item_id"), resultSet.getInt("item_id"), resultSet.getFloat("units"), resultSet.getFloat("sale_price"), resultSet.getFloat("sgst"), resultSet.getFloat("cgst"), resultSet.getFloat("igst"), resultSet.getString("item_name"), resultSet.getString("hsn_o_sac")));
		      }
		      st.close();
		    
			databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
		
		} catch (SQLException e) {
			e.printStackTrace();
			soldItemsList.add(new SoldItemsModel());
		}

		return soldItemsList;
	}
	
}
