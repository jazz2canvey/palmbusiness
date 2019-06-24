package nutan.tech.palmbusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import nutan.tech.models.CallResultModel;
import nutan.tech.models.PaymentViaModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/payment_via")
public class PaymentVia {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newPaymentVia(PaymentViaModel paymentViaModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "INSERT INTO palm_business.payment_via_15 (payment_via, payment_via_code)" + " VALUES (?, ?)";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, paymentViaModel.getPayment_via());
		    preparedStmt.setInt (2, paymentViaModel.getPayment_via_code());
				    
		    // execute the preparedstatement
		    preparedStmt.execute();
		    
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
	@Path("{payment_via_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPaymentVia(@PathParam("payment_via_id") int paymentViaId, PaymentViaModel partiesModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.payment_via_15 SET payment_via = ?, payment_via_code = ? WHERE payment_via_15.payment_via_id = " + paymentViaId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, partiesModel.getPayment_via());
		    preparedStmt.setInt (2, partiesModel.getPayment_via_code());
				    
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
	@Path("{payment_via_id}")
	public List<CallResultModel> deleteCustomerType(@PathParam("payment_via_id") int paymentViaId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.payment_via_15 WHERE payment_via_15.payment_via_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, paymentViaId);

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
