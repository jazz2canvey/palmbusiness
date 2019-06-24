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

import nutan.tech.models.OutlineItemsModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/outline_items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OutlineItems {

	DatabaseFuntions databaseFunctions;

	@POST
	public List<OutlineItemsModel> newOutlineItem(OutlineItemsModel outlineItemsModel) {
		
		List<OutlineItemsModel> outlineItemsList = new ArrayList<>();
		
		Timestamp lastModified = new Timestamp(new Date().getTime());
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		String query = " insert into palm_business.outline_items_26 (item_type, item_id, quantity, sale_price, cgst, sgst, igst, hsn_o_sac, item_description, last_modified)"
		        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, outlineItemsModel.getItem_type());
		    preparedStmt.setInt (2, outlineItemsModel.getItem_id());
		    preparedStmt.setFloat (3, outlineItemsModel.getQuantity());
		    preparedStmt.setDouble (4, outlineItemsModel.getSale_price());
		    preparedStmt.setFloat (5, outlineItemsModel.getCgst());
		    preparedStmt.setFloat (6, outlineItemsModel.getSgst());		    
		    preparedStmt.setFloat (7, outlineItemsModel.getIgst());
		    preparedStmt.setString (8, outlineItemsModel.getHsn_o_sac());
		    preparedStmt.setString (9, outlineItemsModel.getItem_description());
		    preparedStmt.setTimestamp(10, lastModified);				    
		    preparedStmt.execute();
		    
		    String[] dateTime = lastModified.toString().split("\\.");
		    
		    String getQuery = "SELECT * FROM palm_business.outline_items_26 where outline_items_26.item_id = " + outlineItemsModel.getItem_id() + " and outline_items_26.last_modified = " + "\"" + dateTime[0] + "\"";
		    
		    Statement st = connection.createStatement();
		    ResultSet resultSet = st.executeQuery(getQuery);
		    
		      while (resultSet.next())
		      {
		    	  	outlineItemsList.add(new OutlineItemsModel(resultSet.getInt("outline_item_id"), resultSet.getInt("item_type"), resultSet.getInt("item_id"), resultSet.getFloat("quantity"), resultSet.getFloat("cgst"), resultSet.getFloat("sgst"), resultSet.getFloat("igst"), resultSet.getDouble("sale_price"), resultSet.getString("hsn_o_sac"), resultSet.getString("item_description")));
		      }
		      st.close();
		    
			databaseFunctions.closeDBOperations(connection, preparedStmt, resultSet);
		
		} catch (SQLException e) {
			e.printStackTrace();
			outlineItemsList.add(new OutlineItemsModel());
		}

		return outlineItemsList;
	}
	
}
