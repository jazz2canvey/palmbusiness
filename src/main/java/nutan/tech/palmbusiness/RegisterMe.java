package nutan.tech.palmbusiness;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import nutan.tech.models.LoginModel;
import nutan.tech.models.RegistrationModel;
import nutan.tech.models.TAXModel;
import nutan.tech.utilities.APIUtilities;
import nutan.tech.utilities.DatabaseFuntions;

@Path("/enterprise")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterMe {

	DatabaseFuntions databaseFunctions;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> registerEnterprise(RegistrationModel registrationModel) throws InterruptedException, IOException {

		String directoryPath = "/Users/jawedtahasildar/eclipse-workspace/palmbusiness/target/";
		String fileName = registrationModel.getEnterprise_name() + ".txt";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final List<CallResultModel> callResultModelList = new ArrayList<>();
		final String logoData = registrationModel.getEnterprise_logo();
		Date fiscalDate = null;
		final File file = new File(directoryPath, fileName);
		file.createNewFile();
		
		String[] givenFiscal = registrationModel.getFiscal_year().split(" ");
		int monthIndex = Arrays.asList(APIUtilities.months).indexOf(givenFiscal[1]);
		
		String givenDateStr = Calendar.getInstance().get(Calendar.YEAR) + "-" + String.valueOf(monthIndex + 1) + "-" + givenFiscal[0];
		String currentDateStr = dateFormat.format(new Date());
		
		try {
			
			Date givenDate = dateFormat.parse(givenDateStr);
			Date currentDate = dateFormat.parse(currentDateStr);
			
			if (currentDate.after(givenDate)) {
				
				fiscalDate = dateFormat.parse(Calendar.getInstance().get(Calendar.YEAR) + "-" + String.valueOf(monthIndex + 1) + "-" + givenFiscal[0]);
				
			} else if (currentDate.before(givenDate)) {
				
				Calendar prevYear = Calendar.getInstance();
			    prevYear.add(Calendar.YEAR, -1);
			    fiscalDate = dateFormat.parse(prevYear.get(Calendar.YEAR) + "-" + String.valueOf(monthIndex + 1) + "-" + givenFiscal[0]);

			} else if (currentDate.equals(givenDate)) {
			
				fiscalDate = dateFormat.parse(Calendar.getInstance().get(Calendar.YEAR) + "-" + String.valueOf(monthIndex + 1) + "-" + givenFiscal[0]);
			}
			
		} catch (ParseException e1) {
			e1.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false, "Failed to Register, Try Again"));
			
		}
		
		registrationModel.setFiscal_year(dateFormat.format(fiscalDate));
		registrationModel.setEnterprise_logo(file.getAbsolutePath());
		
		final RegistrationModel model = registrationModel;

		Thread isExistThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				databaseFunctions = new DatabaseFuntions();
				Connection connection = databaseFunctions.connect2DB();
			    String getQuery = "SELECT db_palm_business.enterprises.email_main FROM db_palm_business.enterprises where enterprises.email_main = " + "\"" + model.getEmail_main() + "\"";
			  
				try {
					String userName = null;
					Statement st = connection.createStatement();
				    ResultSet resultSet = st.executeQuery(getQuery);
				    
				      while (resultSet.next())
				      {
				    	  	userName = resultSet.getString("email_main");
				      }
				      st.close();
				      
					databaseFunctions.closeDBOperations(connection, st, null);

					if (userName != null && userName.matches(model.getEmail_main())) {
						callResultModelList.add(new CallResultModel(true, false, "Username Already Exist"));
						return;
					}

				} catch (SQLException e) {
					e.printStackTrace();
					callResultModelList.add(new CallResultModel(true, false, "Failed to Register, Try Again"));
					return;
				}
			    
			}
		});
		
		Thread fileUploadThread = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							FileWriter fileWriter = new FileWriter(file);
							
							if (logoData != null)
								fileWriter.write(logoData);

							if (fileWriter != null) {

								fileWriter.flush();
								fileWriter.close();
							}		
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
		});
		
		Thread insert2Database = new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						databaseFunctions = new DatabaseFuntions();
						Connection connection = databaseFunctions.connect2DB();
						String query = " insert into db_palm_business.enterprises (enterprise_id, enterprise_logo, enterprise_name, fiscal_year, date_format, address_street_1, address_street_2, address_city, address_state, address_country, address_pin_zip_code, phone, email_main, password, email_ccc, website, enterprise_logged_in)"
						        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement preparedStmt = null;
						try {
							preparedStmt = connection.prepareStatement(query);
						
							String enterpriseId = "ENP" + new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime()) + APIUtilities.generateRandom(2);
							preparedStmt.setString(1, enterpriseId);
							preparedStmt.setString (2, model.getEnterprise_logo());
						    preparedStmt.setString (3, model.getEnterprise_name());
						    preparedStmt.setString (4, model.getFiscal_year());
						    preparedStmt.setString (5, model.getDate_format());
						    preparedStmt.setString (6, model.getAddress_street_1());
						    preparedStmt.setString (7, model.getAddress_street_2());
						    preparedStmt.setString (8, model.getAddress_city());
						    preparedStmt.setString (9, model.getAddress_state());
						    preparedStmt.setString (10, model.getAddress_country());
						    preparedStmt.setString (11, model.getAddress_pincode());
						    preparedStmt.setString (12, model.getPhone());
						    preparedStmt.setString (13, model.getEmail_main());
						    preparedStmt.setString (14, model.getPassword());
						    preparedStmt.setString (15, model.getEmail_ccc());
						    preparedStmt.setString (16, model.getWebsite());
						    preparedStmt.setBoolean (17, model.isEnterprise_logged_in());
								    
						    // execute the preparedstatement
						    preparedStmt.execute();
						    
						    String getQuery = "SELECT db_palm_business.enterprises.enterprise_id FROM db_palm_business.enterprises where enterprises.email_main = " + "\"" + model.getEmail_main() + "\"";
						    
						    Statement st = connection.createStatement();
						    ResultSet rs = st.executeQuery(getQuery);
						    
						      while (rs.next())
						      {
						    	  	callResultModelList.add(new CallResultModel(false, true, rs.getString("enterprise_id")));
						      }
						      st.close();
						    
							databaseFunctions.closeDBOperations(connection, preparedStmt, null);
						
						} catch (SQLException e) {
							e.printStackTrace();
							if (e.toString().contains("Duplicate entry")) {
								
								callResultModelList.add(new CallResultModel(true, false, "Username Already Registered"));
								databaseFunctions.closeDBOperations(connection, preparedStmt, null);
							} else {

								callResultModelList.add(new CallResultModel(true, false, e.toString()));
								databaseFunctions.closeDBOperations(connection, preparedStmt, null);
							}
						}
						
					}
				});	
	
				isExistThread.start();
				isExistThread.join();
				
				if (callResultModelList.isEmpty()) {
					
					fileUploadThread.start();
					insert2Database.start();
					
					fileUploadThread.join();
					insert2Database.join();
				} else {
					
					CallResultModel callResultModel = callResultModelList.get(0);				

					if (!callResultModel.getMessage().equals("Username Already Exist") || !callResultModel.getMessage().equals("Failed to Register, Try Again")) {
						
						fileUploadThread.start();
						insert2Database.start();
						
						fileUploadThread.join();
						insert2Database.join();
					} 
				}
				
		return callResultModelList;
	}
	
	
	@POST
	@Path("/tax_scheme")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> registerTaxScheme(TAXModel taxModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "insert into db_palm_business.gst_affiliation (gst_affiliation_id, enterprise_id, gst_scheme_id, composition_charge, gstin)" + " values (?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setInt (1, taxModel.getGst_affiliation_id());
		    preparedStmt.setString (2, taxModel.getEnterprise_id());
		    preparedStmt.setString (3, taxModel.getGst_scheme_id());
		    preparedStmt.setFloat(4, taxModel.getComposition_charge());
		    preparedStmt.setString (5, taxModel.getGstin());
			
		    // execute the preparedstatement
		    preparedStmt.execute();
		    
		    String getQuery = "SELECT db_palm_business.gst_affiliation.gst_affiliation_id from db_palm_business.gst_affiliation where db_palm_business.gst_affiliation.enterprise_id = '" + taxModel.getEnterprise_id() + "'";
		    
		    System.out.println("Query: " + getQuery);

		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(getQuery);
		    
		      while (rs.next())
		      {		    	  
		    	  	callResultModelList.add(new CallResultModel(false, true, rs.getString("gst_affiliation_id")));
		      }
		      st.close();
		    
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		    
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}
	
	@PUT
	@Path("{enterprise_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> updateEnterprise(@PathParam("enterprise_id") int enterpriseId, RegistrationModel registrationModel) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		
		PreparedStatement preparedStmt = null;
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.enterprises SET enterprise_logo = ?, enterprise_name = ?, fiscal_year = ?, date_format = ?, address_street_1 = ?, address_street_2 = ?, address_city = ?, address_state = ?, address_country = ?, address_pin_zip_code = ?, phone = ?, email_main = ?, email_ccc = ?, website = ? WHERE enterprises.enterprise_id = " + enterpriseId;

		try {

			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, registrationModel.getEnterprise_logo());
		    preparedStmt.setString (2, registrationModel.getEnterprise_name());
		    preparedStmt.setString (3, registrationModel.getFiscal_year());
		    preparedStmt.setString (4, registrationModel.getDate_format());
		    preparedStmt.setString (5, registrationModel.getAddress_street_1());
		    preparedStmt.setString (6, registrationModel.getAddress_street_2());
		    preparedStmt.setString (7, registrationModel.getAddress_city());
		    preparedStmt.setString (8, registrationModel.getAddress_state());
		    preparedStmt.setString (9, registrationModel.getAddress_country());
		    preparedStmt.setString (10, registrationModel.getAddress_pincode());
		    preparedStmt.setString (11, registrationModel.getPhone());
		    preparedStmt.setString (12, registrationModel.getEmail_main());
		    preparedStmt.setString (13, registrationModel.getEmail_ccc());
		    preparedStmt.setString (14, registrationModel.getWebsite());
		    
		    // execute the preparedstatement
		    preparedStmt.executeUpdate();

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
	@Path("tax_scheme/{enterprise_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CallResultModel> updateTaxScheme(@PathParam("enterprise_id") int enterpriseId, TAXModel taxModel) {
	
		List<CallResultModel> callResultModelList = new ArrayList<>();
		
		PreparedStatement preparedStmt = null;
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "UPDATE db_palm_business.gst_affiliation SET gst_scheme_id = ?, gstin = ? WHERE gst_affiliation.enterprise_id = " + enterpriseId;
		
		try {

			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, taxModel.getGst_scheme_id());
		    preparedStmt.setString (2, taxModel.getGstin());
		    
		    // execute the preparedstatement
		    preparedStmt.executeUpdate();

			callResultModelList.add(new CallResultModel(false, true));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		
		} catch (SQLException e) {
			e.printStackTrace();
			callResultModelList.add(new CallResultModel(true, false));
			databaseFunctions.closeDBOperations(connection, preparedStmt, null);
		}
		
		return callResultModelList;
	}		
	
	@GET
	@Path("login/{user_name}/{password}")
	public List<LoginModel> loginUser(@PathParam("user_name") String userName, @PathParam("password") String Password) {
		
		List<LoginModel> userDataList = new ArrayList<>();
		
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
	    	Statement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT db_palm_business.enterprises.*, db_palm_business.gst_affiliation.gst_scheme_id, db_palm_business.gst_affiliation.composition_charge, db_palm_business.gst_affiliation.gstin FROM db_palm_business.enterprises LEFT JOIN db_palm_business.gst_affiliation ON db_palm_business.enterprises.enterprise_id = db_palm_business.gst_affiliation.enterprise_id WHERE db_palm_business.enterprises.email_main = '" + userName + "' and db_palm_business.enterprises.password = '" + Password + "'";

	    try {

		    	statement = connection.createStatement();
			resultSet = statement.executeQuery(query);		

			while (resultSet.next())	{
	
				userDataList.add(new LoginModel(resultSet.getString("enterprise_id"), resultSet.getString("enterprise_logo"), resultSet.getString("enterprise_name"), resultSet.getString("fiscal_year"), resultSet.getString("date_format"), resultSet.getString("address_street_1"), resultSet.getString("address_street_2"), resultSet.getString("address_city"), resultSet.getString("address_state"), resultSet.getString("address_country"), resultSet.getString("address_pin_zip_code"), resultSet.getString("phone"), resultSet.getString("email_main"), resultSet.getString("password"), resultSet.getString("email_ccc"), resultSet.getString("website"), resultSet.getString("gst_scheme_id"), resultSet.getString("composition_charge"), resultSet.getString("gstin")));
			}
			
			databaseFunctions.closeDBOperations(connection, statement, resultSet);		

		} catch (SQLException e) {
			
			e.printStackTrace();
			databaseFunctions.closeDBOperations(connection, statement, resultSet);
			return null;
		}		
	
	    return userDataList;		
	}
	
	@DELETE
	@Path("deleteEnterprise/{enterprise_id}")
	public List<CallResultModel> deleteEnterprise(@PathParam("enterprise_id") String enterpriseId) {
		
		List<CallResultModel> callResultModelList = new ArrayList<>();
		databaseFunctions = new DatabaseFuntions();
		Connection connection = databaseFunctions.connect2DB();
		
		String query = "DELETE FROM db_palm_business.enterprises WHERE db_palm_business.enterprises.enterprise_id = ?";

		System.out.println("Query: " + query);
		
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = connection.prepareStatement(query);
			
		    preparedStmt.setString (1, enterpriseId);
				    
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
