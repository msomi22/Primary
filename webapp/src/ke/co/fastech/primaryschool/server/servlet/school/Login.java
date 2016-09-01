/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.staff.user.Users;
import ke.co.fastech.primaryschool.persistence.account.AcountDAO;
import ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO;
import ke.co.fastech.primaryschool.persistence.user.UserDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.util.FontImageGenerator;
//import ke.co.fastech.primaryschool.server.servlet.util.SecurityUtil;
import ke.co.fastech.primaryschool.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class Login extends HttpServlet{

	// Error message provided
	final String ACCOUNT_SIGN_IN_BAD_CAPTCHA = "The characters you entered did not match those provided in the image.";
	final String ERROR_WRONG_USER_DETAIL = "Incorrect staff credentials.";

	private BasicTextEncryptor textEncryptor;
	private String hiddenCaptchaStr = "";
	private Cache schoolCache;
	private Logger logger;

	Map<String,String> onlineUsersMap;
	ServletContext context;

	private static UserDAO userDAO;
	private static AcountDAO acountDAO;
	private static StaffCategoryDAO staffCategoryDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		CacheManager mgr = CacheManager.getInstance();
		schoolCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(FontImageGenerator.SECRET_KEY);
		logger = Logger.getLogger(this.getClass());
		onlineUsersMap = new HashMap<String,String>();
		context = getServletContext();
		userDAO = UserDAO.getInstance();
		acountDAO = AcountDAO.getInstance();
		staffCategoryDAO = StaffCategoryDAO.getInstance();

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);//current session


		if (session != null) {
			session.invalidate();  
		}
		session = request.getSession(true);

		String schoolusername = StringUtils.trimToEmpty(request.getParameter("schoolusername"));
		String staffposition = StringUtils.trimToEmpty(request.getParameter("staffposition"));
		String staffusername = StringUtils.trimToEmpty(request.getParameter("staffusername"));
		String staffpassword = StringUtils.trimToEmpty(request.getParameter("staffpassword"));
		
		hiddenCaptchaStr = request.getParameter("captchaHidden");
		String captchaAnswer = request.getParameter("captchaAnswer").trim();

		Users user = new Users();
		if(userDAO.getUserByUsername(staffusername) !=null){
			user = userDAO.getUserByUsername(staffusername); 
		}//acountDAO

		if(userDAO.getUserByUsername(staffusername) == null){
			session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL); 
			response.sendRedirect("index.jsp");

		}else  if (userDAO.getUserPassword(staffusername, staffpassword) == null) { 
			session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
			response.sendRedirect("index.jsp");

		}else  if (acountDAO.getAccountByusername(schoolusername) == null) {  
			session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
			response.sendRedirect("index.jsp");

		}else if(staffCategoryDAO.getStaffCategory(user.getStaffUuid(), staffposition) == null){  
			session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, ERROR_WRONG_USER_DETAIL);
			response.sendRedirect("index.jsp");

		}else  if (!validateCaptcha(hiddenCaptchaStr, captchaAnswer)) {
			session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, ACCOUNT_SIGN_IN_BAD_CAPTCHA);
			response.sendRedirect("index.jsp");

		} else{
			
			    
				Account school = new Account(); 
				Element element;
				if ((element = schoolCache.get(schoolusername)) != null) {  
					school = (Account) element.getObjectValue(); 
				}
				
				onlineUsersMap.put(user.getStaffUuid(),session.getId());
				context.setAttribute("onlineUsersMap", onlineUsersMap);

				updateCache(school.getUuid(),user.getStaffUuid());
				SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, YYYY: h-m-s");
				System.out.println("\""+user.getUsername() + "\" form  \"" + school.getSchoolName() + "\" has logged in on \"" + dateFormatter.format(new Date()) +"\""); 
		          

				session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_ACCOUNTUUID, school.getUuid());
				session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY, school.getUsername());  
				session.setAttribute(SessionConstants.ACCOUNT_LOGIN_SUCCESS, SessionConstants.ACCOUNT_LOGIN_SUCCESS); 
				session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_TIME, String.valueOf(new Date().getTime()));
				request.getSession().setAttribute(SessionConstants.STAFF_SIGN_IN_USERNAME, user.getUsername());    
				request.getSession().setAttribute(SessionConstants.STAFF_SIGN_IN_ID, user.getStaffUuid());
				request.getSession().setAttribute(SessionConstants.STAFF_SIGN_IN_POSITION, staffposition);
				
				response.sendRedirect("primary/schoolIndex.jsp"); 

		}


	}


	/**
	 * @param encodedSystemCaptcha
	 * @param userCaptcha
	 * @return
	 */
	private boolean validateCaptcha(String encodedSystemCaptcha, String userCaptcha) {
		boolean valid = false;
		String decodedHiddenCaptcha = "";

		try {
			decodedHiddenCaptcha = textEncryptor.decrypt(URLDecoder.decode(encodedSystemCaptcha, "UTF-8"));

		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException while trying to validate captcha.");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		if (StringUtils.equalsIgnoreCase(decodedHiddenCaptcha, userCaptcha)) {
			valid = true;
		}

		return valid;
	}


	/**
	 * @param accountuuid
	 */
	private void updateCache(String accountuuid,String staffuuid) {
		//statisticsCache.put(new Element(accountuuid, statistics));

	}




	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5788905673784003036L;

}
