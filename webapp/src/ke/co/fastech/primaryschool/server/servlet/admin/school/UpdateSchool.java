/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.admin.school;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.account.AcountDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.session.AdminSessionConstants;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class UpdateSchool extends HttpServlet{

	private final String ERROR_PHONE_INVALID = "Phone number is invalid, the number must have 10 digits (e.g. 0718953974).";
	private final String ERROR_EMPTY_POSTAL_ADDRESS = "School postal address can't be empty";
	private final String ERROR_EMPTY_SCHOOL_USERNAME = "School username can't be empty";
	private final String ERROR_EMPTY_SCHOOL_PHONE = "School phone number can't be empty";
	private final String ERROR_EMPTY_TOWN = "School home town address can't be empty";
	private final String ERROR_EMPTY_POSTAL_MOTTO = "School motto can't be empty";
	private final String SCHOOL_ADD_SUCCESS = "School account updated successfully";
	private final String ERROR_EMPTY_SCHOOL_NAME = "School Name can't be empty";
	//private final String ERROR_EMPTY_SCHOOL_DB = "Select Day/Boarding status";
	private final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	private final String SCHOOL_ADD_ERROR = "School account Not Updated";
	private final String NAME_ERROR = "Data format error/incorrent lenght.";

	private CacheManager cacheManager;

	private static AcountDAO acountDAO;
	
	/**   
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cacheManager = CacheManager.getInstance();
		acountDAO = AcountDAO.getInstance();
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String schoolname = StringUtils.trimToEmpty(request.getParameter("schoolname"));
		String username = StringUtils.trimToEmpty(request.getParameter("username"));
		String phone = StringUtils.trimToEmpty(request.getParameter("phone"));
		String email = StringUtils.trimToEmpty(request.getParameter("email"));
		String dayBoarding = StringUtils.trimToEmpty(request.getParameter("dayBoarding"));
		String address = StringUtils.trimToEmpty(request.getParameter("address"));
		String hometown = StringUtils.trimToEmpty(request.getParameter("hometown"));
		String motto = StringUtils.trimToEmpty(request.getParameter("motto"));
		String accountuuid = StringUtils.trimToEmpty(request.getParameter("accountuuid"));

		if(StringUtils.isBlank(schoolname)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_NAME); 

		}else if(!Wordlength(schoolname)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, NAME_ERROR); 

		}else if(StringUtils.isBlank(username)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_USERNAME); 

		}else if(!Wordlength(username)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, NAME_ERROR); 

		}else if(StringUtils.isBlank(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_PHONE); 

		}else if(!isNumeric(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_NUMERIC); 

		}else if(!lengthValid(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_INVALID); 

		}/*else if(StringUtils.isBlank(dayBoarding)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_DB); 

		}*/else if(StringUtils.isBlank(email)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_POSTAL_ADDRESS); 

		}else if(StringUtils.isBlank(hometown)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_TOWN); 

		}else if(StringUtils.isBlank(address)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_POSTAL_ADDRESS); 

		}else if(StringUtils.isBlank(motto)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_POSTAL_MOTTO); 

		}else if(StringUtils.isBlank(accountuuid)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, SCHOOL_ADD_ERROR); 

		}else{
			Account account = acountDAO.getAccount(accountuuid);
			account.setSchoolName(StringUtils.capitalize(schoolname)); 
			account.setUsername(username);
			account.setSchoolPhone(phone); 
			account.setSchoolEmail(email); 
			//account.setDayBoarding(dayBoarding); 
			account.setSchoolAddres(address); 
			account.setSchoolHomeTown(StringUtils.capitalize(hometown)); 
			account.setSchoolMotto(motto); 
			
			
			if(acountDAO.update(account)){	
				updateStudentCache(account);
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_SUCCESS, SCHOOL_ADD_SUCCESS);
				
			}else{
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, SCHOOL_ADD_ERROR);  
			}

		}

		response.sendRedirect("adminIndex.jsp");
		return;
	}



	private void updateStudentCache(Account accnt) {
		cacheManager.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME).put(new Element(accnt.getUsername(), accnt));
		cacheManager.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID).put(new Element(accnt.getUuid(), accnt));
	}


	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			double d = Double.parseDouble(str);  

		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}

	/**
	 * @param mystring
	 * @return
	 */
	private static boolean lengthValid(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		if(length<10 ||length>10){
			isvalid = false;
		}
		return isvalid;
	}


	/**
	 * @param mystring
	 * @return
	 */
	private static boolean Wordlength(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		if(length<3){
			isvalid = false;
		}
		return isvalid;
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1176743144090333411L;


}
