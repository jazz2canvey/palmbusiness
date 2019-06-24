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

import nutan.tech.creator.PurchaseOrderCreator;
import nutan.tech.listmodel.PurchaseOrderListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.PurchaseOrderModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/purchase_order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrder {

	DatabaseFuntions databaseFunctions;
	
	@POST
	public List<CallResultModel> newPurchaseOrder(PurchaseOrderListModel purchaseOrdersList) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		List<PurchaseOrderModel> purchaseOrdersModelList = PurchaseOrderCreator.getPurchaseOrder();
		purchaseOrdersModelList.addAll(purchaseOrdersList.getPurchaseOrdersList());
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		PreparedStatement preparedStmt = null;
		
		String query = " INSERT INTO palm_business.purchase_orders_8 (enterprise_id, vendor_id, purchase_order_item_id, purchase_order_number, entry_date, shipping_address, msg_to_vendor)"
		        + " VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStmt = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			
			for (int i = 0; i < purchaseOrdersModelList.size(); i++) {
				
				PurchaseOrderModel purchaseOrderModel = purchaseOrdersModelList.get(i);
				
				System.out.println("EnterpriseId: " + purchaseOrderModel.getEnterprise_id());
				System.out.println("VendorId: " + purchaseOrderModel.getVendor_id());
				System.out.println("OrderItemId: " + purchaseOrderModel.getPurchase_order_item_id());
				System.out.println("PurchaseOrderNumber: " + purchaseOrderModel.getPurchase_order_number());
				System.out.println("EntryDate: " + purchaseOrderModel.getEntry_date());
				System.out.println("ShippingAddress: " + purchaseOrderModel.getShipping_address());
				System.out.println("Message2Vendor: " + purchaseOrderModel.getMsg_to_vendor());
				
			    preparedStmt.setInt (1, purchaseOrderModel.getEnterprise_id());
			    preparedStmt.setInt (2, purchaseOrderModel.getVendor_id());
			    preparedStmt.setInt (3, purchaseOrderModel.getPurchase_order_item_id());
			    preparedStmt.setString (4, purchaseOrderModel.getPurchase_order_number());
			    preparedStmt.setString (5, purchaseOrderModel.getEntry_date());
			    preparedStmt.setString (6, purchaseOrderModel.getShipping_address());
			    preparedStmt.setString (7, purchaseOrderModel.getMsg_to_vendor());
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
	@Path("{purchase_order_id}/{enterprise_id}/{vendor_id}/{purchase_order_number}")
	public List<CallResultModel> modifyMyVendor(@PathParam("purchase_order_id") int purchaseOrderId, @PathParam("enterprise_id") int enterpriseId, @PathParam("vendor_id") int vendorId, @PathParam("purchase_order_number") String purchaseOrderNumber, PurchaseOrderModel purchaseOrderModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE palm_business.purchase_orders_8 SET enterprise_id = ?, vendor_id = ?, purchase_order_item_id = ?, purchase_order_number = ?, entry_date = ?, shipping_address = ?, quantity = ?, unit_price = ?, gst = ?, igst = ?, sub_total = ?, grand_total = ?, msg_to_vendor = ? WHERE purchase_orders_8.purchase_order_id = " + purchaseOrderId + " and enterprise_id = " + enterpriseId + " and vendor_id = " + vendorId + " and purchase_order_number = " + purchaseOrderNumber;
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, purchaseOrderModel.getEnterprise_id());
		    preparedStmt.setInt (2, purchaseOrderModel.getVendor_id());
		    preparedStmt.setInt (3, purchaseOrderModel.getPurchase_order_item_id());
		    preparedStmt.setString (4, purchaseOrderModel.getPurchase_order_number());
		    preparedStmt.setString (5, purchaseOrderModel.getEntry_date());
		    preparedStmt.setString (6, purchaseOrderModel.getShipping_address());
		    preparedStmt.setString (7, purchaseOrderModel.getMsg_to_vendor());
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
	public List<PurchaseOrderModel> getAllPurchaseOrders(@PathParam("enterprise_id") int enterpriseId) {
		
		List<PurchaseOrderModel> purchaseOrderList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		Set<String> keys = new HashSet<>();
		MultivaluedHashMap<String, PurchaseOrderModel> map = new MultivaluedHashMap<>();
		Statement statement = null;
		ResultSet resultSet = null;
		
		String query = "SELECT palm_business.purchase_orders_8.enterprise_id, palm_business.purchase_orders_8.vendor_id, palm_business.purchase_orders_8.purchase_order_item_id, palm_business.purchase_orders_8.purchase_order_number, palm_business.purchase_orders_8.entry_date, palm_business.purchase_orders_8.shipping_address, palm_business.vendors_4.enterprise_name, (CASE WHEN palm_business.purchase_order_items_25.igst > 0 THEN ((((palm_business.purchase_order_items_25.quantity * palm_business.purchase_order_items_25.purchase_price) * palm_business.purchase_order_items_25.igst) / 100) + (palm_business.purchase_order_items_25.quantity * palm_business.purchase_order_items_25.purchase_price)) ELSE ((((palm_business.purchase_order_items_25.quantity * palm_business.purchase_order_items_25.purchase_price) * (palm_business.purchase_order_items_25.sgst + palm_business.purchase_order_items_25.cgst) / 100) + (palm_business.purchase_order_items_25.quantity * palm_business.purchase_order_items_25.purchase_price))) END) AS grand_total FROM palm_business.purchase_orders_8 LEFT JOIN palm_business.vendors_4 ON palm_business.purchase_orders_8.vendor_id = palm_business.vendors_4.vendor_id LEFT JOIN palm_business.purchase_order_items_25 ON palm_business.purchase_orders_8.purchase_order_item_id = palm_business.purchase_order_items_25.purchase_order_item_id WHERE palm_business.purchase_orders_8.enterprise_id = " + enterpriseId;

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			int enterprise_id = resultSet.getInt("enterprise_id");
			int vendor_id = resultSet.getInt("vendor_id");
			int purchase_order_item_id = resultSet.getInt("purchase_order_item_id");
			String purchase_order_number = resultSet.getString("purchase_order_number");
			String entry_date = resultSet.getString("entry_date");
			String shipping_address = resultSet.getString("shipping_address");
			String enterprise_name = resultSet.getString("enterprise_name");
			float grand_total = Float.parseFloat(resultSet.getString("grand_total"));
								
			PurchaseOrderModel model = new PurchaseOrderModel(enterprise_id, vendor_id, purchase_order_item_id, grand_total, enterprise_name, purchase_order_number, entry_date, shipping_address, "");
			String key = String.valueOf(vendor_id + ", " + purchase_order_number);
			map.add(key, model);
			keys.add(key);
	      }
		
		for (int i = 0; i < keys.size(); i++) {

			double grandTotal = 0;
			List<PurchaseOrderModel> list = map.get(new ArrayList<>(keys).get(i));
			
			for (int z = 0; z < list.size(); z++)
				grandTotal = grandTotal + list.get(z).getGrand_total();				
			
			purchaseOrderList.add(new PurchaseOrderModel(list.get(0).getEnterprise_id(), list.get(0).getVendor_id(), list.get(0).getPurchase_order_item_id(), grandTotal, list.get(0).getEnterprise_name(), list.get(0).getPurchase_order_number(), list.get(0).getEntry_date(),list.get(0).getShipping_address(), list.get(0).getMsg_to_vendor()));
		}
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}
		return purchaseOrderList;
		
	}
	
	@DELETE
	@Path("{purchase_order_id}/{enterprise_id}")
	public List<CallResultModel> modifyItem(@PathParam("purchase_order_id") int purchaseOrderId, @PathParam("enterprise_id") int enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM palm_business.purchase_orders_8 WHERE purchase_order_id = ? and enterprise_id =  ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, purchaseOrderId);
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
