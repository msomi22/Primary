/**
 * 
 */
package ke.co.fastech.primaryschool.server.session;

/**
 * @author peter
 *
 */
public class SessionConstants {


	//session management
	final public static int SESSION_TIMEOUT = 500; 
	public static final String SIGN_ON_STATUS = "Online";

	//Account Management
	public static final String ACCOUNT_SIGN_IN_ERROR_KEY = "Error Login";
	public static final String ACCOUNT_SIGN_IN_ACCOUNTUUID = "Account Signin AccountUuid";
	public static final String ACCOUNT_SIGN_IN_KEY = "Account Signin Key";
	public static final String ACCOUNT_SIGN_IN_TIME = "Account Signin Time";

	//Staff management
	public static final String STAFF_SIGN_IN_USERNAME = "Staff signin username"; 
	public static final String STAFF_SIGN_IN_ID = "Staff signin ID";
	public static final String STAFF_SIGN_IN_POSITION = "Staff signin Position";
	
	
	//Account login
	public static final String ACCOUNT_LOGIN_SUCCESS = "Login Success";
	public static final String ACCOUNT_LOGIN_ERROR = "Login Error";
	
	//update student
	public static final String STUDENT_UPDATE_ERROR = "Student update error";
	public static final String STUDENT_UPDATE_SUCCESS = "Student update success";
	
	//staff
	public static final String STAFF_ERROR = "Staff error";
	public static final String STAFF_SUCCESS = "Staff success";
	//school
	public static final String SCHOOL_ERROR = "School error";
	public static final String SCHOOL_SUCCESS = "School success";
	
	//hash map parameters
	public static final String STAFF_PARAM = "Staff parameters";
	public static final String GRADE_PARAM = "Grade parameters";
	
	

}
