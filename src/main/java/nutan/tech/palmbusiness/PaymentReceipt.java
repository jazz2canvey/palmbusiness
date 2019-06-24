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

import nutan.tech.creator.PaymentReceiptCreator;
import nutan.tech.listmodel.PaymentsReceiptsListModel;
import nutan.tech.models.AccountsModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PaymentReceiptModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/payment_receipt")
public class PaymentReceipt {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newItem(PaymentsReceiptsListModel paymentReceiptList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<PaymentReceiptModel> paymentsReceiptsModelList = PaymentReceiptCreator.getPaymentReceipts();
		paymentsReceiptsModelList.addAll(paymentReceiptList.getPaymentReceiptsList());
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		PreparedStatement preparedStmt = null;
		
		String query = " INSERT INTO palm_business.payments_receipts_17 (enterprise_id, party_type_code, vendor_customer, invoice_number, entry_date, amount, payment_mode, payment_via, note)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < paymentsReceiptsModelList.size(); i++) {
				
			PaymentReceiptModel paymentReceiptModel = paymentsReceiptsModelList.get(i);

			    preparedStmt.setInt (1, paymentReceiptModel.getEnterprise_id());
			    preparedStmt.setInt (2, paymentReceiptModel.getParty_type_code());
			    preparedStmt.setInt (3, paymentReceiptModel.getId_vendor_customer());
			    preparedStmt.setString (4, paymentReceiptModel.getInvoice_number());
			    preparedStmt.setString (5, paymentReceiptModel.getEntry_date());
			    preparedStmt.setFloat (6, paymentReceiptModel.getAmount());
			    preparedStmt.setInt (7, paymentReceiptModel.getPayment_mode());
			    preparedStmt.setInt (8, paymentReceiptModel.getPayment_via());
			    preparedStmt.setString (9, paymentReceiptModel.getNote());
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
	
	@GET
	@Path("{enterprise_id}/{party_type_code}")
	public List<PaymentReceiptModel> getPaymentReceipt(@PathParam("enterprise_id") int enterpriseId, @PathParam("party_type_code") int partyTypeCode) {
		
		List<PaymentReceiptModel> paymentReceiptList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT palm_business.payments_receipts_17.payment_receipt_id, palm_business.payments_receipts_17.party_type_code, palm_business.payments_receipts_17.vendor_customer,\n" + 
				"palm_business.payments_receipts_17.payment_mode, palm_business.payments_receipts_17.payment_via, palm_business.payments_receipts_17.enterprise_id, palm_business.payments_receipts_17.invoice_number, (CASE WHEN palm_business.payments_receipts_17.party_type_code = 1 THEN palm_business.vendors_4.enterprise_name WHEN (palm_business.payments_receipts_17.party_type_code = 2 AND palm_business.customers_11.enterprise_name IS NOT NULL) THEN palm_business.customers_11.enterprise_name WHEN (palm_business.payments_receipts_17.party_type_code = 2 AND palm_business.customers_11.person_name IS NOT NULL) THEN palm_business.customers_11.person_name END) AS name, palm_business.payments_receipts_17.entry_date, (CASE WHEN palm_business.payments_receipts_17.party_type_code = 1 THEN palm_business.parties_14.party_type WHEN palm_business.payments_receipts_17.party_type_code = 2 THEN palm_business.parties_14.party_type END) AS party, (CASE WHEN palm_business.payments_receipts_17.payment_mode = 1 THEN palm_business.payment_modes_15.payment_mode WHEN palm_business.payments_receipts_17.payment_mode = 2 THEN palm_business.payment_modes_15.payment_mode WHEN\n" + 
				"palm_business.payments_receipts_17.payment_mode = 3 THEN palm_business.payment_modes_15.payment_mode END) AS mode_of_payment, (CASE WHEN palm_business.payments_receipts_17.payment_via = 1 THEN palm_business.payment_via_16.payment_via WHEN palm_business.payments_receipts_17.payment_via = 2 THEN palm_business.payment_via_16.payment_via WHEN palm_business.payments_receipts_17.payment_via = 3 THEN palm_business.payment_via_16.payment_via END) AS via_payment, amount, palm_business.payments_receipts_17.note\n" + 
				"FROM palm_business.payments_receipts_17 LEFT JOIN palm_business.parties_14 ON palm_business.payments_receipts_17.party_type_code = palm_business.parties_14.party_type_code LEFT JOIN palm_business.payment_modes_15 ON palm_business.payments_receipts_17.payment_mode = palm_business.payment_modes_15.payment_code LEFT JOIN palm_business.payment_via_16 ON palm_business.payments_receipts_17.payment_via = palm_business.payment_via_16.payment_via_code LEFT JOIN palm_business.vendors_4 ON palm_business.payments_receipts_17.vendor_customer = palm_business.vendors_4.vendor_id LEFT JOIN palm_business.customers_11 ON palm_business.payments_receipts_17.vendor_customer = palm_business.customers_11.customer_id WHERE palm_business.payments_receipts_17.enterprise_id = " + enterpriseId + " AND palm_business.payments_receipts_17.party_type_code = " + partyTypeCode;
		
		System.out.println("Query: " + query);
		
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			paymentReceiptList.add(new PaymentReceiptModel(resultSet.getInt("payment_receipt_id"), resultSet.getInt("enterprise_id"), resultSet.getInt("party_type_code"), resultSet.getInt("vendor_customer"), resultSet.getInt("payment_mode"),resultSet.getInt("payment_via"), resultSet.getString("invoice_number"), resultSet.getString("name"), resultSet.getString("entry_date"), resultSet.getString("party"), resultSet.getString("mode_of_payment"), resultSet.getString("via_payment"), resultSet.getFloat("amount"), resultSet.getString("note")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
	
	    return paymentReceiptList;
	}
	
	@GET
	@Path("/accounts/{enterprise_id}/{payment_mode}")
	public List<AccountsModel> getAccounts(@PathParam("enterprise_id") int enterpriseId, @PathParam("payment_mode") int paymentMode) {

		List<AccountsModel> accountsList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT palm_business.payments_receipts_17.payment_receipt_id, palm_business.payments_receipts_17.invoice_number, (CASE WHEN palm_business.payments_receipts_17.party_type_code = 1 THEN palm_business.vendors_4.enterprise_name WHEN (palm_business.payments_receipts_17.party_type_code = 2 AND palm_business.customers_11.enterprise_name IS NOT NULL) THEN palm_business.customers_11.enterprise_name WHEN (palm_business.payments_receipts_17.party_type_code = 2 AND palm_business.customers_11.person_name IS NOT NULL) THEN palm_business.customers_11.person_name END) AS name, palm_business.payments_receipts_17.entry_date, (CASE WHEN palm_business.payments_receipts_17.party_type_code = 1 THEN palm_business.parties_14.party_type WHEN palm_business.payments_receipts_17.party_type_code = 2 THEN palm_business.parties_14.party_type END) AS party, (CASE WHEN palm_business.payments_receipts_17.payment_mode = 1 THEN palm_business.payment_modes_15.payment_mode WHEN palm_business.payments_receipts_17.payment_mode = 2 THEN palm_business.payment_modes_15.payment_mode WHEN\n" + 
				"palm_business.payments_receipts_17.payment_mode = 3 THEN palm_business.payment_modes_15.payment_mode END) AS mode_of_payment, (CASE WHEN palm_business.payments_receipts_17.payment_via = 1 THEN palm_business.payment_via_16.payment_via WHEN palm_business.payments_receipts_17.payment_via = 2 THEN palm_business.payment_via_16.payment_via WHEN palm_business.payments_receipts_17.payment_via = 3 THEN palm_business.payment_via_16.payment_via END) AS via_payment, amount, palm_business.payments_receipts_17.note\n" + 
				"FROM palm_business.payments_receipts_17 LEFT JOIN palm_business.parties_14 ON palm_business.payments_receipts_17.party_type_code = palm_business.parties_14.party_type_code LEFT JOIN palm_business.payment_modes_15 ON palm_business.payments_receipts_17.payment_mode = palm_business.payment_modes_15.payment_code LEFT JOIN palm_business.payment_via_16 ON palm_business.payments_receipts_17.payment_via = palm_business.payment_via_16.payment_via_code LEFT JOIN palm_business.vendors_4 ON palm_business.payments_receipts_17.vendor_customer = palm_business.vendors_4.vendor_id LEFT JOIN palm_business.customers_11 ON palm_business.payments_receipts_17.vendor_customer = palm_business.customers_11.customer_id WHERE palm_business.payments_receipts_17.enterprise_id = " + enterpriseId + " AND palm_business.payments_receipts_17.payment_mode = " + paymentMode;
		
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			accountsList.add(new AccountsModel(resultSet.getInt("payment_receipt_id"), resultSet.getFloat("amount"), resultSet.getString("invoice_number"), resultSet.getString("name"), resultSet.getString("entry_date"), resultSet.getString("party"), resultSet.getString("mode_of_payment"), resultSet.getString("via_payment"), resultSet.getString("note")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
	
	    return accountsList;
	}
	
	@PUT
	@Path("{enterprise_id}/{party_type_code}/{customer_id}/{vendor_id}/{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPaymentReceipt(@PathParam("enterprise_id") int enterpriseId, @PathParam("party_type_code") int partyTypeCode, @PathParam("customer_id") int customerId, @PathParam("vendor_id") int vendorId, @PathParam("invoice_number") String invoiceNumber, PaymentReceiptModel paymentReceiptModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		String query = null;
		
		if (customerId > 0) {
		
			query = "UPDATE palm_business.payments_receipts_17 SET enterprise_id = ?, party_type_code = ?, customer_id = ?, vendor_id = ?, invoice_number = ?, entry_date = ?, amount = ?, payment_mode = ?, payment_via = ?, note = ? WHERE payments_receipts_17.enterprise_id = " + enterpriseId + " and payments_receipts_17.party_type_code = " + partyTypeCode + " and payments_receipts_17.customer_id = " + customerId + " and payments_receipts_17.vendor_id = " + vendorId + " and payments_receipts_17.invoice_number = " + invoiceNumber;
			
		} else if (vendorId > 0) {
			
			query = "UPDATE palm_business.payments_receipts_17 SET enterprise_id = ?, party_type_code = ?, customer_id = ?, vendor_id = ?, invoice_number = ?, entry_date = ?, amount = ?, payment_mode = ?, payment_via = ?, note = ? WHERE payments_receipts_17.enterprise_id = " + enterpriseId + " and payments_receipts_17.party_type_code = " + partyTypeCode + " and payments_receipts_17.customer_id = " + customerId + " and payments_receipts_17.vendor_id = " + vendorId + " and payments_receipts_17.invoice_number = " + invoiceNumber;
			
		} else {
		
			callResultModelList.add(new CallResultModel(true, false));
			return callResultModelList;
		}
		
		PreparedStatement preparedStmt = null;

		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, paymentReceiptModel.getEnterprise_id());
		    preparedStmt.setInt (2, paymentReceiptModel.getParty_type_code());
		    preparedStmt.setInt (3, paymentReceiptModel.getId_vendor_customer());
		    preparedStmt.setInt (4, paymentReceiptModel.getPayment_mode());
		    preparedStmt.setInt (5, paymentReceiptModel.getPayment_via());
		    preparedStmt.setString (6, paymentReceiptModel.getInvoice_number());
		    preparedStmt.setString (7, paymentReceiptModel.getEntry_date());
		    preparedStmt.setFloat (8, paymentReceiptModel.getAmount());
		    preparedStmt.setString (9, paymentReceiptModel.getNote());
				    
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
	@Path("{item_id}/{enterprise_id}")
	public List<CallResultModel> deleteItem(@PathParam("item_id") int itemId, @PathParam("enterprise_id") int enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.items_5 WHERE item_id = ? and enterprise_id =  ?";

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
	
}
