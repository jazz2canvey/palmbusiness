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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import nutan.tech.creator.PurchaseInvoiceCreator;
import nutan.tech.listmodel.PurchaseInvoicesListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.InvoiceNumberModel;
import nutan.tech.models.PurchaseInvoiceModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/purchase_invoice")
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseInvoice {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Path("{enterprise_id}/{vendor_id}/{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newPurchaseInvoice(@PathParam("enterprise_id") final int enterpriseId, @PathParam("vendor_id") final int vendorId, @PathParam("invoice_number") final String invoiceNumber, final PurchaseInvoicesListModel purchaseInvoicesList) {
		
		final List<CallResultModel> callResultModelList = new ArrayList<>();
		
		Thread insert2Database = new Thread(
				new Runnable() {
					
					@Override
					public void run() {

						List<PurchaseInvoiceModel> purchaseInvoicesModelList = PurchaseInvoiceCreator.getPurchaseInvoices();
						purchaseInvoicesModelList.addAll(purchaseInvoicesList.getPuchaseInvoicesList());
						
						databaseFunctions = new DatabaseFuntions();
						Connection connection = databaseFunctions.connect2DB();
						PreparedStatement preparedStmt = null;
						
						String query = " INSERT INTO palm_business.purchase_invoices_7 (enterprise_id, vendor_id, item_id, status, invoice_number, reference, entry_date, due_date, reverse_charge, note)"
						        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

						try {
							preparedStmt = connection.prepareStatement(query);
							connection.setAutoCommit(false);
							
							for (int i = 0; i < purchaseInvoicesModelList.size(); i++) {
								
								PurchaseInvoiceModel purchaseInvoiceModel = purchaseInvoicesModelList.get(i);

								preparedStmt.setInt (1, purchaseInvoiceModel.getEnterprise_id());
							    preparedStmt.setInt (2, purchaseInvoiceModel.getVendor_id());
							    preparedStmt.setInt (3, purchaseInvoiceModel.getItem_id());
							    preparedStmt.setBoolean (4, purchaseInvoiceModel.getStatus());
							    preparedStmt.setString (5, purchaseInvoiceModel.getInvoice_number());
							    preparedStmt.setString (6, purchaseInvoiceModel.getReference());
							    preparedStmt.setString (7, purchaseInvoiceModel.getEntry_date());
							    preparedStmt.setString (8, purchaseInvoiceModel.getDue_date());
							    preparedStmt.setBoolean (9, purchaseInvoiceModel.getReverse_charge());
							    preparedStmt.setString (10, purchaseInvoiceModel.getNote());
							    preparedStmt.addBatch();
							}    
							
							preparedStmt.executeBatch();
							connection.commit();
							callResultModelList.add(new CallResultModel(false, true));
							databaseFunctions.closeDBOperations(connection, preparedStmt, null);
						
						} catch (SQLException e) {
							e.printStackTrace();
							callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
							databaseFunctions.closeDBOperations(connection, preparedStmt, null);
						}
						
					}
				});
		
		Thread isExistThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				databaseFunctions = new DatabaseFuntions();
				Connection connection = databaseFunctions.connect2DB();
			    String getQuery = "SELECT * FROM palm_business.purchase_invoices_7 where purchase_invoices_7.enterprise_id = " + enterpriseId + " and purchase_invoices_7.vendor_id = " + vendorId + " and purchase_invoices_7.invoice_number = " + "\"" + invoiceNumber + "\"";
			    
				try {
					
					String enterprise_id = null, vendor_id = null, invoice_number = null;
					Statement st = connection.createStatement();
				    ResultSet resultSet = st.executeQuery(getQuery);
				    
				      while (resultSet.next())
				      {
				    	  	enterprise_id = resultSet.getString("enterprise_id");
				    	  	vendor_id = resultSet.getString("vendor_id");
				    	  	invoice_number = resultSet.getString("invoice_number");
				      }
				      
				      st.close();
					databaseFunctions.closeDBOperations(connection, st, null);

					if (enterprise_id != null && vendor_id != null && invoice_number != null) {
						
						if (Integer.parseInt(enterprise_id) == enterpriseId && Integer.parseInt(vendor_id) == vendorId && invoice_number.matches(invoiceNumber)) {

							callResultModelList.add(new CallResultModel(true, false, "This Vendor, Invoice Number Already Exist"));
							return;
						}
						
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
					return;
				}
			    
			}
		});
		

		try {
			isExistThread.start();
			isExistThread.join();

			if (callResultModelList.isEmpty()) {
				
				insert2Database.start();				
				insert2Database.join();
			} 
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false, "Oops! Something Went Wrong, Try Again"));
		}
		
		return callResultModelList;
	}
	
	@PUT
	@Path("{purchase_invoice_id}/{enterprise_id}/{vendor_id}/{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> modifyPurchaseInvoice(@PathParam("purchase_invoice_id") int purchaseInvoiceId, @PathParam("enterprise_id") int enterpriseId, @PathParam("vendor_id") int vendorId, @PathParam("invoice_number") String invoiceNumber, PurchaseInvoiceModel purchaseInvoiceModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.purchase_invoices_7 SET enterprise_id = ?, vendor_id = ?, item_id = ?, invoice_number = ?, reference = ?, entry_date = ?, due_date = ?, quantity = ?, unit_price = ?, gst = ?, igst = ?, reverse_charge = ?, tax_under_rc = ?, sub_total = ?, grand_total = ?, note = ? WHERE purchase_invoices_7.purchase_invoice_id = " + purchaseInvoiceId + " and enterprise_id = " + enterpriseId + " and vendor_id = " + vendorId + " and invoice_number = " + invoiceNumber;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
			preparedStmt.setInt (1, purchaseInvoiceModel.getEnterprise_id());
		    preparedStmt.setInt (2, purchaseInvoiceModel.getVendor_id());
		    preparedStmt.setInt (3, purchaseInvoiceModel.getItem_id());
		    preparedStmt.setBoolean (4, purchaseInvoiceModel.getStatus());
		    preparedStmt.setString (5, purchaseInvoiceModel.getInvoice_number());
		    preparedStmt.setString (6, purchaseInvoiceModel.getReference());
		    preparedStmt.setString (7, purchaseInvoiceModel.getEntry_date());
		    preparedStmt.setString (8, purchaseInvoiceModel.getDue_date());
		    preparedStmt.setBoolean (9, purchaseInvoiceModel.getReverse_charge());
		    preparedStmt.setString (10, purchaseInvoiceModel.getNote());
				    
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
	public List<PurchaseInvoiceModel> getAllPurchaseInvoices(@PathParam("enterprise_id") int enterpriseId) {
		
		List<PurchaseInvoiceModel> purchaseInvoiceList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		Set<String> keys = new HashSet<>();
		MultivaluedHashMap<String, PurchaseInvoiceModel> map = new MultivaluedHashMap<>();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT palm_business.purchase_invoices_7.enterprise_id, palm_business.purchase_invoices_7.vendor_id, palm_business.purchase_invoices_7.item_id, palm_business.purchase_invoices_7.invoice_number, palm_business.purchase_invoices_7.entry_date, palm_business.purchase_invoices_7.due_date, palm_business.purchase_invoices_7.reference, palm_business.purchase_invoices_7.reverse_charge, palm_business.purchase_invoices_7.note, (CASE WHEN palm_business.items_5.igst > 0 THEN ((((palm_business.items_5.units * palm_business.items_5.purchase_price) * palm_business.items_5.igst) / 100) + (palm_business.items_5.units * palm_business.items_5.purchase_price)) ELSE ((((palm_business.items_5.units * palm_business.items_5.purchase_price) * (palm_business.items_5.sgst + palm_business.items_5.cgst) / 100) + (palm_business.items_5.units * palm_business.items_5.purchase_price))) END) AS grand_total, palm_business.vendors_4.enterprise_name FROM palm_business.purchase_invoices_7 LEFT JOIN palm_business.items_5 ON palm_business.purchase_invoices_7.item_id = palm_business.items_5.item_id LEFT JOIN palm_business.vendors_4 ON palm_business.purchase_invoices_7.vendor_id = palm_business.vendors_4.vendor_id WHERE palm_business.purchase_invoices_7.enterprise_id = " + enterpriseId;

		System.out.println("Query: " + query);
		
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			int enterprise_id = resultSet.getInt("enterprise_id");
			int vendor_id = resultSet.getInt("vendor_id");
			int item_id = resultSet.getInt("item_id");
			float grand_total = Float.parseFloat(resultSet.getString("grand_total"));
			boolean reverse_charge = Boolean.parseBoolean(resultSet.getString("reverse_charge"));
			String enterprise_name = resultSet.getString("enterprise_name");
			String invoice_number = resultSet.getString("invoice_number");
			String reference = resultSet.getString("reference");
			String note = resultSet.getString("note");
			String entry_date = resultSet.getString("entry_date");
			String due_date = resultSet.getString("due_date");
					
			PurchaseInvoiceModel model = new PurchaseInvoiceModel(enterprise_id, vendor_id, item_id, grand_total, reverse_charge, enterprise_name, invoice_number, reference, note, entry_date, due_date);
			String key = String.valueOf(vendor_id + ", " + invoice_number);
			map.add(key, model);
			keys.add(key);
	      }
		
		for (int i = 0; i < keys.size(); i++) {

			float grandTotal = 0;
			List<PurchaseInvoiceModel> list = map.get(new ArrayList<>(keys).get(i));
			
			for (int z = 0; z < list.size(); z++)
				grandTotal = grandTotal + list.get(z).getGrand_total();				
			
			purchaseInvoiceList.add(new PurchaseInvoiceModel(list.get(0).getEnterprise_id(), list.get(0).getVendor_id(), 0, grandTotal, list.get(0).getReverse_charge(), list.get(0).getEnterprise_name(),list.get(0).getInvoice_number(), list.get(0).getReference(), "", list.get(0).getEntry_date(), list.get(0).getDue_date()));
		}
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}
		return purchaseInvoiceList;
	}
	
	@GET
	@Path("{enterprise_id}/{vendor_id}/{status}")
	public List<InvoiceNumberModel> ordersCompletIncomplet(@PathParam("enterprise_id") int enterpriseId, @PathParam("vendor_id") int vendorId, @PathParam("status") boolean status) {
		
		List<InvoiceNumberModel> invoiceNumbersList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT invoice_number FROM palm_business.purchase_invoices_7 WHERE palm_business.purchase_invoices_7.enterprise_id = " + enterpriseId + " AND palm_business.purchase_invoices_7.vendor_id = " + vendorId + " AND palm_business.purchase_invoices_7.status = " + status;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			invoiceNumbersList.add(new InvoiceNumberModel(resultSet.getString("invoice_number")));
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}

    return invoiceNumbersList;
	    
	}
	
	@GET
	@Path("/invoicedue/{enterprise_id}/{customer_id}/{invoice_number}")
	@Produces(MediaType.TEXT_PLAIN)
	public float invoiceDueAmount(@PathParam("enterprise_id") int enterpriseId, @PathParam("customer_id") int customerId, @PathParam("invoice_number") String invoiceNumber) {

		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

		float payableAmount = 0;
		float subTotal = 0;
		float totalSGST = 0;
		float totalCGST = 0;
		float totalIGST = 0;
		
	    String query = "SELECT palm_business.purchase_invoices_7.purchased_item_id, palm_business.purchase_invoices_7.reverse_charge, palm_business.purchased_items_25.units, palm_business.purchased_items_25.purchased_price, palm_business.purchased_items_25.sgst, palm_business.purchased_items_25.cgst, palm_business.purchased_items_25.igst FROM palm_business.purchase_invoices_7 LEFT JOIN palm_business.purchased_items_25 ON palm_business.purchase_invoices_7.purchased_item_id = palm_business.purchased_items_25.purchased_item_id WHERE palm_business.purchase_invoices_7.enterprise_id = " + enterpriseId + " AND palm_business.purchase_invoices_7.vendor_id = " + customerId + " AND palm_business.purchase_invoices_7.invoice_number = '" + invoiceNumber + "'";
		
	    System.out.println("Query: " + query);
	    
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			
			boolean reverseCharge = resultSet.getBoolean("reverse_charge");
			float units = resultSet.getFloat("units");
			float purchasedPrice = resultSet.getFloat("purchased_price");
			float sgst = resultSet.getFloat("sgst");
			float cgst = resultSet.getFloat("cgst");
			float igst = resultSet.getFloat("igst");
			
			subTotal = units * purchasedPrice;

			if (reverseCharge) {
				
				payableAmount = payableAmount + subTotal;
				
			} else if (!reverseCharge){
				
				if (igst != 0) {

					totalIGST = (subTotal * igst) / 100;

					payableAmount = payableAmount + (subTotal + totalIGST);
					
				} else if (sgst != 0 || cgst != 0) {

					totalSGST = (subTotal * sgst) / 100;
					totalCGST = (subTotal * cgst) / 100;					
					
					payableAmount = payableAmount + (subTotal + totalSGST + totalCGST);					
				}
				
			}
			
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return 0;
	}

    return payableAmount;
	}

	@DELETE
	@Path("{purchase_invoice_id}/{enterprise_id}")
	public List<CallResultModel> deleteItem(@PathParam("purchase_invoice_id") int purchaseInvoiceId, @PathParam("enterprise_id") int enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.purchase_invoices_7 WHERE palm_business.purchase_invoices_7.purchase_invoice_id = ? and palm_business.purchase_invoices_7.enterprise_id =  ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, purchaseInvoiceId);
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
