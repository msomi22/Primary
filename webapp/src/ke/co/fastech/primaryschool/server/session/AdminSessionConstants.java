/**
 * 
 */
package ke.co.fastech.primaryschool.server.session;

/**
 * @author peter
 *
 */
public class AdminSessionConstants {
	
	final public static int SESSION_TIMEOUT = 500; 

	//Admin sessions
	public static final String ADMIN_SIGN_IN_ERROR_KEY = "Error Login";
	final public static String ADMIN_SESSION_KEY = "Admin Session Key";

	public static final String ADMIN_SIGN_IN_KEY = "admin Signin Key";
	public static final String ADMIN_SIGN_IN_TIME = "Admin Signin Time";

	final public static String ADMIN_SIGN_IN_ERROR_VALUE = "Sorry, the administrator username and/or " +
			"password are incorrect. Please try again.";

	final public static String ACCOUNT_ADD_SUCCESS = "Account Account Added Successfully";
	final public static String ACCOUNT_ADD_ERROR = "Account Account Add Error";
	
	final public static String ACCOUNT_ADD_KEY = "Account Add Key";
	
	final public static String ACCOUNT_UPDATE_ERROR = "Account Update Error";
	final public static String ACCOUNT_UPDATE_SUCCESS = "Account Update Success";
	
	final public static String SCHOOL_ACCOUNT_PARAM = "Account Parameters";

	final public static String HEAD_TEACHER_ADD_ERROR = "Head teacher add error";
	final public static String HEAD_TEACHER_ADD_SUCCESS = "Head teacher added Successfully";
}
