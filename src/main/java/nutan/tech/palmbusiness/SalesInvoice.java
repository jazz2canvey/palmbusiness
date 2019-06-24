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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import nutan.tech.creator.SalesInvoiceCreator;
import nutan.tech.listmodel.SalesInvoiceListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.InvoiceNumberModel;
import nutan.tech.models.SalesInvoiceModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/sales_invoice")
@Produces(MediaType.APPLICATION_JSON)
public class SalesInvoice {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Path("{invoice_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newSalesInvoice(@PathParam("invoice_number") final String invoiceNumber, final SalesInvoiceListModel salesInvoicesList) {
		
		final List<CallResultModel> callResultModelList = new ArrayList<>();

		Thread insert2Database = new Thread(new Runnable() {
					
			@Override
			public void run() {
			
				List<SalesInvoiceModel> salesInvoiceModelList = SalesInvoiceCreator.getSalesInvoices();
				salesInvoiceModelList.addAll(salesInvoicesList.getSalesInvoicesList());
				
				databaseFunctions = new DatabaseFuntions();
				Connection connection = databaseFunctions.connect2DB();
				PreparedStatement preparedStmt = null;
				
				String query = "INSERT INTO palm_business.sales_invoice_9 (enterprise_id, customer_id, sold_item_id, status, invoice_number, reference, entry_date, due_date, discount_type, discount, msg_to_customer)"
				        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
				try {
					preparedStmt = connection.prepareStatement(query);
					connection.setAutoCommit(false);

					for (int i = 0; i < salesInvoiceModelList.size(); i++) {
						
						SalesInvoiceModel salesInvoiceModel = salesInvoiceModelList.get(i);

						preparedStmt.setInt (1, salesInvoiceModel.getEnterprise_id());
					    preparedStmt.setInt (2, salesInvoiceModel.getCustomer_id());
					    preparedStmt.setInt (3, salesInvoiceModel.getSold_item_id());
					    preparedStmt.setBoolean (4, salesInvoiceModel.isStatus());
					    preparedStmt.setString (5, salesInvoiceModel.getInvoice_number());
					    preparedStmt.setString (6, salesInvoiceModel.getReference());
					    preparedStmt.setString (7, salesInvoiceModel.getEntry_date());
					    preparedStmt.setString (8, salesInvoiceModel.getDue_date());
					    preparedStmt.setInt (9, salesInvoiceModel.getDiscount_type());
					    preparedStmt.setFloat (10, salesInvoiceModel.getDiscount());
					    preparedStmt.setString (11, salesInvoiceModel.getMsg_to_customer());
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
			    String getQuery = "SELECT * FROM palm_business.sales_invoice_9 where sales_invoice_9.invoice_number = " + "\"" + invoiceNumber + "\"";
			    
				try {
					String invoice_number = null;
					Statement st = connection.createStatement();
				    ResultSet resultSet = st.executeQuery(getQuery);
				    
				      while (resultSet.next())
				      {
				    	  	invoice_number = resultSet.getString("invoice_number");
				      }
				      
				      st.close();
				      databaseFunctions.closeDBOperations(connection, st, null);

					if (invoice_number != null) {
						
						if (invoice_number.matches(invoiceNumber)) {

							callResultModelList.add(new CallResultModel(true, false, "Invoice Number Already Exist"));
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
		float discountedTotal = 0;
		float totalSGST = 0;
		float totalCGST = 0;
		float totalIGST = 0;
		
	    String query = "SELECT palm_business.sales_invoice_9.sold_item_id, palm_business.sales_invoice_9.invoice_number, palm_business.sold_items_24.units, palm_business.sold_items_24.sale_price, palm_business.sold_items_24.discount_type, palm_business.sold_items_24.discount, palm_business.sold_items_24.sgst, palm_business.sold_items_24.cgst, palm_business.sold_items_24.igst FROM palm_business.sales_invoice_9 LEFT JOIN palm_business.sold_items_24 ON palm_business.sales_invoice_9.sold_item_id = palm_business.sold_items_24.sold_item_id WHERE palm_business.sales_invoice_9.enterprise_id = " + enterpriseId + " AND palm_business.sales_invoice_9.customer_id = " + customerId + " AND palm_business.sales_invoice_9.invoice_number = '" + invoiceNumber + "'";
		
	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			
			float units = resultSet.getFloat("units");
			float soldPrice = resultSet.getFloat("sale_price");
			int discountType = resultSet.getInt("discount_type");
			float discount = resultSet.getFloat("discount");
			float sgst = resultSet.getFloat("sgst");
			float cgst = resultSet.getFloat("cgst");
			float igst = resultSet.getFloat("igst");
			
			subTotal = units * soldPrice;
			
			switch(discountType) {
			case 0:
				
				if (igst != 0) {

					totalIGST = (subTotal * igst) / 100;
				
					payableAmount = payableAmount + (subTotal + totalIGST);
					
				} else if (sgst != 0 || cgst != 0) {

					totalSGST = (subTotal * sgst) / 100;
					totalCGST = (subTotal * cgst) / 100;					

					payableAmount = payableAmount + (subTotal + totalSGST + totalCGST);
				}
								
				break;
				
			case 1:

				discountedTotal = subTotal - discount;
				
				if (igst != 0) {

					totalIGST = (discountedTotal * igst) / 100;
				
					payableAmount = payableAmount + (discountedTotal + totalIGST);
					
				} else if (sgst != 0 || cgst != 0) {

					totalSGST = (discountedTotal * sgst) / 100;
					totalCGST = (discountedTotal * cgst) / 100;					

					payableAmount = payableAmount + (discountedTotal + totalSGST + totalCGST);
				}
				
				break;
				
			case 2:

				discountedTotal = subTotal - (subTotal * discount) / 100;
				
				System.out.println("discounted total: " + discountedTotal);
				
				if (igst != 0) {

					totalIGST = (discountedTotal * igst) / 100;
				
					payableAmount = payableAmount + (discountedTotal + totalIGST);
					
				} else if (sgst != 0 || cgst != 0) {

					totalSGST = (discountedTotal * sgst) / 100;
					totalCGST = (discountedTotal * cgst) / 100;					

					payableAmount = payableAmount + (discountedTotal + totalSGST + totalCGST);
				}
				
				break;
			
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
	
	@GET
	@Path("{enterprise_id}/{customer_id}/{status}")
	public List<InvoiceNumberModel> ordersCompletIncomplet(@PathParam("enterprise_id") int enterpriseId, @PathParam("customer_id") int customerId, @PathParam("status") boolean status) {
		
		List<InvoiceNumberModel> invoiceNumbersList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT invoice_number FROM palm_business.sales_invoice_9 WHERE palm_business.sales_invoice_9.enterprise_id = " + enterpriseId + " AND palm_business.sales_invoice_9.customer_id = " + customerId + " AND palm_business.sales_invoice_9.status = " + status;

	    System.out.println("Query: " + query);
	    
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
	@Path("{enterprise_id}")
	public List<SalesInvoiceModel> getAllInvoices(@PathParam("enterprise_id") int entrepriseId) {
		
		List<SalesInvoiceModel> salesInvoiceList = new ArrayList<>();
		Set<String> keys = new HashSet<>();
		MultivaluedHashMap<String, SalesInvoiceModel> map = new MultivaluedHashMap<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

	    String query = "SELECT palm_business.sales_invoice_9.status, palm_business.sales_invoice_9.invoice_number, palm_business.sales_invoice_9.reference, palm_business.sales_invoice_9.entry_date, palm_business.sales_invoice_9.due_date, palm_business.sales_invoice_9.discount_type, palm_business.sales_invoice_9.discount, palm_business.customers_11.customer_id, (CASE WHEN palm_business.customers_11.enterprise_name != null THEN palm_business.customers_11.enterprise_name ELSE palm_business.customers_11.person_name END) AS customer_name, palm_business.items_5.item_name, palm_business.sold_items_24.units, palm_business.sold_items_24.sale_price, palm_business.sold_items_24.sgst, palm_business.sold_items_24.cgst, palm_business.sold_items_24.igst, palm_business.sold_items_24.item_discount_type, palm_business.sold_items_24.item_discount, palm_business.sold_items_24.hsn_o_sac, (CASE WHEN palm_business.sold_items_24.igst > 0 THEN ((((palm_business.sold_items_24.units * palm_business.sold_items_24.sale_price) * palm_business.sold_items_24.igst) / 100) + (palm_business.sold_items_24.units * palm_business.sold_items_24.sale_price)) ELSE ((((palm_business.sold_items_24.units * palm_business.sold_items_24.sale_price) * (palm_business.sold_items_24.sgst + palm_business.sold_items_24.cgst) / 100) + (palm_business.sold_items_24.units * palm_business.sold_items_24.sale_price))) END) AS total_amount, palm_business.sales_invoice_9.msg_to_customer FROM palm_business.sales_invoice_9 LEFT JOIN palm_business.customers_11 ON palm_business.sales_invoice_9.customer_id = palm_business.customers_11.customer_id LEFT JOIN palm_business.sold_items_24 ON palm_business.sales_invoice_9.sold_item_id = palm_business.sold_items_24.sold_item_id INNER JOIN palm_business.items_5 ON palm_business.sold_items_24.item_id = palm_business.items_5.item_id WHERE palm_business.sales_invoice_9.enterprise_id = " + entrepriseId;
		
	    System.out.println("Query: " + query);
	    
	    try {

		    	statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				
				boolean status = resultSet.getBoolean("status");
				String invoiceNumber = resultSet.getString("invoice_number");
				String reference = resultSet.getString("reference");
				String entryDate = resultSet.getString("entry_date");
				String dueDate = resultSet.getString("due_date");
				int discountType = resultSet.getInt("discount_type");
				float discount = resultSet.getFloat("discount");
				int customerId = resultSet.getInt("customer_id");
				String customerName = resultSet.getString("customer_name");
				float units = resultSet.getFloat("units");
				float salePrice = resultSet.getFloat("sale_price");
				float sgst = resultSet.getFloat("sgst");
				float cgst = resultSet.getFloat("cgst");
				float igst = resultSet.getFloat("igst");
				int itemDiscountType = resultSet.getInt("item_discount_type");
				float itemDiscount = resultSet.getFloat("item_discount");
				String hsnOsac = resultSet.getString("hsn_o_sac");
				String itemName = resultSet.getString("item_name");
				float itemTotalAmount = 0;
				
				switch (itemDiscountType) {
				
					case 0:
							
						itemTotalAmount = resultSet.getFloat("total_amount");							
						
						break;
						
					case 1:
						
						itemTotalAmount = resultSet.getFloat("total_amount") - itemDiscount;
						
						break;
						
					case 2:
						
						itemTotalAmount = resultSet.getFloat("total_amount") - (resultSet.getFloat("total_amount") * (itemDiscount / 100));
						
						break;
				}

				String msg2customer = resultSet.getString("msg_to_customer");

				SalesInvoiceModel salesInvoiceModel = new SalesInvoiceModel(status, invoiceNumber, reference, entryDate, dueDate, discountType, discount, customerId, customerName, itemName, units, salePrice, sgst, cgst, igst, itemDiscountType, itemDiscount, hsnOsac, itemTotalAmount, msg2customer);
				String key = String.valueOf(customerId + ", " + invoiceNumber);
				map.add(key, salesInvoiceModel);
				keys.add(key);	
			}
			
			for (int i = 0; i < keys.size(); i++) {

				float invoiceTotalAmount = 0;
				
				List<SalesInvoiceModel> list = map.get(new ArrayList<>(keys).get(i));
				
					for (SalesInvoiceModel model : list) 
						invoiceTotalAmount = invoiceTotalAmount + model.getGrand_total();						
									
					switch (list.get(0).getDiscount_type()) {
					
					case 1:
						
						invoiceTotalAmount = invoiceTotalAmount - list.get(0).getDiscount(); 
						
						break;
						
					case 2:
						
						invoiceTotalAmount = invoiceTotalAmount * (list.get(0).getDiscount() / 100);
						
						break;
										
					}	
					
					if (list.get(0).getDue_date() == null) 
						list.get(0).setDue_date("");
					
					System.out.println("Due date: " + list.get(0).getDue_date());

				salesInvoiceList.add(new SalesInvoiceModel(list.get(0).isStatus(), list.get(0).getInvoice_number(), list.get(0).getReference(), list.get(0).getEntry_date(), list.get(0).getDue_date(), list.get(0).getDiscount_type(),list.get(0).getDiscount(), list.get(0).getCustomer_id(), list.get(0).getCustomer_name(), list.get(0).getItem_name(), list.get(0).getUnits(), list.get(0).getSale_price(), list.get(0).getSgst(), list.get(0).getCgst(), list.get(0).getIgst(), list.get(0).getItem_discount_type(), list.get(0).getItem_discount(), list.get(0).getHsn_o_sac(), invoiceTotalAmount, list.get(0).getMsg_to_customer()));
					
			}
			
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}
		return salesInvoiceList;
	}
	
	@DELETE
	@Path("{sales_invoice_id}/{enterprise_id}")
	public List<CallResultModel> deleteSalesInvoice(@PathParam("sales_invoice_id") int salesInvoiceId, @PathParam("enterprise_id") int enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.sales_invoice_9 WHERE sales_invoice_9.sales_invoice_id = ? and sales_invoice_9.enterprise_id =  ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, salesInvoiceId);
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
