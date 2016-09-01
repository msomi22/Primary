/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.admin.school;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.exam.Exam;
import ke.co.fastech.primaryschool.bean.exam.GradingSystem;
import ke.co.fastech.primaryschool.bean.school.Comment;
import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.account.AcountDAO;
import ke.co.fastech.primaryschool.persistence.exam.ExamDAO;
import ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO;
import ke.co.fastech.primaryschool.persistence.school.CommentDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.session.AdminSessionConstants;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class AddSchool extends HttpServlet{

	private final String ERROR_PHONE_INVALID = "Phone number is invalid, the number must have 10 digits (e.g. 0718953974).";
	private final String ERROR_SCHOOL_USERNAME_EXIST = "The School username already exists in the system";
	private final String ERROR_EMPTY_POSTAL_ADDRESS = "School postal address can't be empty";
	private final String ERROR_EMPTY_SCHOOL_USERNAME = "School username can't be empty";
	private final String ERROR_EMPTY_SCHOOL_PHONE = "School phone number can't be empty";
	private final String ERROR_EMPTY_TOWN = "School home town address can't be empty";
	private final String ERROR_EMPTY_POSTAL_MOTTO = "School motto can't be empty";
	private final String SCHOOL_ADD_SUCCESS = "School account created successfully";
	private final String ERROR_EMPTY_SCHOOL_NAME = "School Name can't be empty";
	private final String ERROR_EMPTY_SCHOOL_DB = "Select Day/Boarding status";
	private final String ERROR_PHONE_NUMERIC = "phone can only be numeric";
	private final String SCHOOL_ADD_ERROR = "School account Not Created";
	private final String NAME_ERROR = "Data format error/incorrent lenght.";

	private CacheManager cacheManager;

	private static AcountDAO acountDAO;
	private static SystemConfigDAO systemConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static StreamDAO streamDAO;
	private static CommentDAO commentDAO;
	private static ExamDAO examDAO;




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
		systemConfigDAO = SystemConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		commentDAO = CommentDAO.getInstance();
		examDAO = ExamDAO.getInstance();

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

		// This is used to store parameter names and values from the form.
		Map<String, String> paramHash = new HashMap<>();    	
		paramHash.put("schoolname", schoolname);
		paramHash.put("username", username);
		paramHash.put("phone", phone);
		paramHash.put("email", email);
		paramHash.put("address", address);
		paramHash.put("hometown", hometown);
		paramHash.put("motto", motto);

		if(StringUtils.isBlank(schoolname)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_NAME); 

		}else if(!Wordlength(schoolname)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, NAME_ERROR); 

		}else if(StringUtils.isBlank(username)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_USERNAME); 

		}else if(!Wordlength(username)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, NAME_ERROR); 

		}else if(acountDAO.getAccountByusername(username) !=null){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_SCHOOL_USERNAME_EXIST); 

		}else if(StringUtils.isBlank(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_PHONE); 

		}else if(!isNumeric(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_NUMERIC); 

		}else if(!lengthValid(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_INVALID); 

		}else if(StringUtils.isBlank(dayBoarding)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_SCHOOL_DB); 

		}else if(StringUtils.isBlank(hometown)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_TOWN); 

		}else if(StringUtils.isBlank(address)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_POSTAL_ADDRESS); 

		}else if(StringUtils.isBlank(motto)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_EMPTY_POSTAL_MOTTO); 

		}else{
			Account account = new Account();
			account.setUuid(account.getUuid());    
			account.setSchoolName(StringUtils.capitalize(schoolname)); 
			account.setUsername(username);
			account.setSchoolPhone(phone); 
			account.setSchoolEmail(email); 
			account.setDayBoarding(dayBoarding); 
			account.setSchoolAddres(address); 
			account.setSchoolHomeTown(StringUtils.capitalize(hometown)); 
			account.setSchoolMotto(motto); 
			

			Calendar calendar = Calendar.getInstance();
			final int YEAR = calendar.get(Calendar.YEAR);
			SystemConfig systemConfig = new SystemConfig(); 
			systemConfig.setAccountUuid(account.getUuid());
			systemConfig.setTerm("1");
			systemConfig.setYear(""+YEAR);
			systemConfig.setExamcode("OPENER");
			systemConfig.setSmsSend("ON");
			systemConfig.setOpenningDate("Wed, 05/2016");
			systemConfig.setClosingDate("Wed, 05/2016"); 

			GradingSystem gradingSystem = new GradingSystem();
			gradingSystem.setAccountUuid(account.getUuid()); 
			gradingSystem.setGradeAplain(Integer.parseInt("83"));
			gradingSystem.setGradeAminus(Integer.parseInt("71"));
			gradingSystem.setGradeBplus(Integer.parseInt("67"));
			gradingSystem.setGradeBplain(Integer.parseInt("62"));
			gradingSystem.setGradeBminus(Integer.parseInt("54"));
			gradingSystem.setGradeCplus(Integer.parseInt("50"));
			gradingSystem.setGradeCplain(Integer.parseInt("45"));
			gradingSystem.setGradeCminus(Integer.parseInt("40"));
			gradingSystem.setGradeDplus(Integer.parseInt("35"));
			gradingSystem.setGradeDplain(Integer.parseInt("30"));
			gradingSystem.setGradeDminus(Integer.parseInt("25"));
			gradingSystem.setGradeE(Integer.parseInt("0")); 

			String [] defaultstreams = {"STD 4 T","STD 5 T","STD 6 T","STD 7 T","STD 8 T"};

			if(acountDAO.put(account) && systemConfigDAO.putSystemConfig(systemConfig) && gradingSystemDAO.putGradingSystem(gradingSystem)){	

				updateStudentCache(account);
				
				for(int i=0;i<defaultstreams.length;i++){
					Stream stream = new Stream();
					stream.setAccountUuid(account.getUuid());
					stream.setStreamName(defaultstreams[i]);
					streamDAO.putStream(stream);
				}


				Comment comment = new Comment();
				comment.setAccountUuid(account.getUuid()); 
				comment.setHeadTeacherCom(" for the fantastic term, it has been awesome to see you grow and develop, hope you have a wonderful holiday.For your performance, all we can say is ...");
				comment.setGradeAplaincom("");
				comment.setGradeAminuscom("");
				comment.setGradeBpluscom("");
				comment.setGradeBplaincom("");
				comment.setGradeBminuscom("");
				comment.setGradeCpluscom("");
				comment.setGradeCplaincom("");
				comment.setGradeCminuscom("");
				comment.setGradeDpluscom("");
				comment.setGradeDplaincom("");
				comment.setGradeDminuscom("");
				comment.setGradeEcom(""); 
				commentDAO.putComment(comment);



				paramHash.clear();
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_SUCCESS, SCHOOL_ADD_SUCCESS);
				
			}else{
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, SCHOOL_ADD_ERROR);  
			}



		}

		session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM, paramHash);
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
