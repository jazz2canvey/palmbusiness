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
import nutan.tech.models.PaymentModeModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/payment_mode")
public class PaymentMode {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newPaymentMode(PaymentModeModel paymentModeModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();

		String query = "INSERT INTO palm_business.payment_mode_16 (payment_mode, payment_mode_code)" + " VALUES (?, ?)";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, paymentModeModel.getPayment_mode());
		    preparedStmt.setInt (2, paymentModeModel.getPayment_mode_code());
				    
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
	@Path("{payment_mode_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPaymentMode(@PathParam("payment_mode_id") int paymentModeId, PaymentModeModel paymentModeModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.payment_mode_16 SET payment_mode = ?, payment_mode_code = ? WHERE payment_mode_16.payment_mode_id = " + paymentModeId;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, paymentModeModel.getPayment_mode());
		    preparedStmt.setInt (2, paymentModeModel.getPayment_mode_code());
				    
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
	@Path("{payment_mode_id}")
	public List<CallResultModel> deletePaymentMode(@PathParam("payment_mode_id") int paymentModeId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.payment_mode_16 WHERE payment_mode_16.payment_mode_id = ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, paymentModeId);

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
