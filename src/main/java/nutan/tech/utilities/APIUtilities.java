package nutan.tech.utilities;

import java.security.SecureRandom;

public class APIUtilities {

	public static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	final static String vars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom secureRandom = new SecureRandom();
	
	public static String generateRandom(int len) {
		
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++ ) 
		      sb.append(vars.charAt(secureRandom.nextInt(vars.length()) ) );

		   return sb.toString();
	}
	
}
