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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nutan.tech.creator.ExpenseCreator;
import nutan.tech.listmodel.ExpensesListModel;
import nutan.tech.models.CallResultModel;
import nutan.tech.models.ExpensesModel;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/expense")
@Produces(MediaType.APPLICATION_JSON)
public class Expense {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> newExpense(final ExpensesListModel expensesList) {
		
		final List<CallResultModel> callResultModelList = new ArrayList<>();
			List<ExpensesModel> expensesModelList = ExpenseCreator.getExpenses();
					expensesModelList.addAll(expensesList.getExpensesList());
					
					databaseFunctions = new DatabaseFuntions();
					Connection connection = databaseFunctions.connect2DB();
					PreparedStatement preparedStmt = null;
					
					String query = " INSERT INTO db_palm_business.expenses (enterprise_id, entry_date, expense_name, expense_head, amount, payed_via_cash, payed_via_bank, bank_payment_mode, description)"
					        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

					try {
						preparedStmt = connection.prepareStatement(query);
						connection.setAutoCommit(false);
						
						for (int i = 0; i < expensesModelList.size(); i++) {
							
							ExpensesModel expensesModel = expensesModelList.get(i);

							preparedStmt.setString (1, expensesModel.getEnterprise_id());
						    preparedStmt.setString (2, expensesModel.getEntry_date());
						    preparedStmt.setString (3, expensesModel.getExpense_name());
						    preparedStmt.setString (4, expensesModel.getExpense_head());
						    preparedStmt.setDouble (5, expensesModel.getAmount());
						    preparedStmt.setBoolean (6, expensesModel.isPayed_via_cash());
						    preparedStmt.setBoolean (7, expensesModel.isPayed_via_bank());
						    preparedStmt.setInt (8, expensesModel.getBank_payment_mode());
						    preparedStmt.setString (9, expensesModel.getDescription());
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

		return callResultModelList;
	}
	
	@GET
	@Path("{enterprise_id}")
	public List<ExpensesModel> getAllExpenses(@PathParam("enterprise_id") String enterpriseId) {
		
		List<ExpensesModel> expensesList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT * FROM db_palm_business.expenses WHERE db_palm_business.expenses.enterprise_id = '" + enterpriseId + "'";

	    try {

	    	statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
		
		while (resultSet.next())
	      {
			int expense_id = resultSet.getInt("expense_id");
			String enterprise_id = resultSet.getString("enterprise_id");
			String entry_date = resultSet.getString("entry_date");
			String expense_name = resultSet.getString("expense_name"); 
			String expense_head = resultSet.getString("expense_head");
			double amount = resultSet.getDouble("amount");
			boolean payed_via_cash = resultSet.getBoolean("payed_via_cash");
			boolean payed_via_bank = resultSet.getBoolean("payed_via_bank");
			int bank_payment_mode = resultSet.getInt("bank_payment_mode");
			String description = resultSet.getString("description");

			ExpensesModel model = new ExpensesModel(expense_id, bank_payment_mode, amount, enterprise_id, entry_date, expense_name, expense_head, description, payed_via_cash, payed_via_bank);
			expensesList.add(model);
	      }
		
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		
		statement.close();

	} catch (SQLException e) {
		e.printStackTrace();
		databaseFunctions.closeDBOperations(connection, statement, resultSet);
		return null;
	}
		return expensesList;
	}
	
	@PUT
	@Path("{enterprise_id}/{expense_id}")
	public List<CallResultModel> updateExpenses(@PathParam("enterprise_id") String enterpriseId, @PathParam("expense_id") int expenseId, ExpensesModel expensesModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.expenses SET expenses.enterprise_id = ?, expenses.entry_date = ?, expenses.expense_name = ?, expenses.expense_head = ?, expenses.amount = ?, expenses.payed_via_cash = ?, expenses.payed_via_bank = ?, expenses.bank_payment_mode = ?, expenses.description = ? WHERE expenses.enterprise_id = '" + enterpriseId + "' and  expenses.expense_id = " + expenseId;

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, expensesModel.getEnterprise_id());
		    preparedStmt.setString (2, expensesModel.getEntry_date());
		    preparedStmt.setString (3, expensesModel.getExpense_name());
		    preparedStmt.setString (4, expensesModel.getExpense_head());
		    preparedStmt.setDouble (5, expensesModel.getAmount());
		    preparedStmt.setBoolean (6, expensesModel.isPayed_via_cash());
		    preparedStmt.setBoolean (7, expensesModel.isPayed_via_bank());
		    preparedStmt.setInt (8, expensesModel.getBank_payment_mode());
		    preparedStmt.setString (9, expensesModel.getDescription());
				    
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
	@Path("{enterprise_id}/{expense_id}")
	public List<CallResultModel> deleteCustomer(@PathParam("enterprise_id") String enterpriseId, @PathParam("expense_id") int expenseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.expenses WHERE expenses.enterprise_id = ? and expenses.expense_id =  ?";

		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
			preparedStmt.setString (1, enterpriseId);
			preparedStmt.setInt (2, expenseId);
				    
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
