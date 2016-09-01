/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.account;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;


/** 
 * @author peter
 *
 */
public class UpdateSystemConfig extends HttpServlet{

	final String ERROR_YEAR_OUTSIDE_RANGE = "Confirm that the year you entered is correct and try again";
	final String ERROR_TERM_NOT_ALLOWED = "Term value can't be greater that three (3)";
	final String ERROR_TERM_NUMERIC = "Term can only be numeric";
	final String ERROR_YEAR_NUMERIC = "Year can only be numeric";
	
	final String ERROR_EMPTY_TERM = "Please provide the term"; 
	final String ERROR_EMPTY_YEAR = "Please provide the year"; 
	final String ERROR_EMPTY_EXAM_CODE = "Please provide the exam code"; 
	final String ERROR_EMPTY_EXAM_CODE_INVALID = "The exam code provided is invalid"; 
	final String ERROR_EMPTY_EXAM_SMS_SEND = "Please provide the smssend code"; 
	final String ERROR_EMPTY_EXAM_SMS_SEND_INVALID = "The smssend code provided is invalid"; 
	final String ERROR_EMPTY_EXAM_OPENNING_DATE = "Please provide the opening date"; 
	final String ERROR_EMPTY_EXAM_CLOSING_DATE = "Please provide the closing date"; 

	final String ERROR_SYS_UPDATE_ERROR = "An error occured while updating System configurations"; 
	final String ERROR_SYS_UPDATE_SUCCESS = "System configurations updated successfully"; 

	private static SystemConfigDAO systemConfigDAO;

	private String[] examcodeArray;
	private List<String> examcodeList;
	
	private String[] smssendArray;
	private List<String> smssendList;

	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		systemConfigDAO = SystemConfigDAO.getInstance();

		examcodeArray = new String[] {"OPENER", "MIDTERM", "ENDTERM"};
		examcodeList = Arrays.asList(examcodeArray);
		
		smssendArray = new String[] {"ON","OFF"};
		smssendList = Arrays.asList(smssendArray);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
		String term = StringUtils.trimToEmpty(request.getParameter("term"));
		String year = StringUtils.trimToEmpty(request.getParameter("year"));
		String examcode = StringUtils.trimToEmpty(request.getParameter("examcode"));
		String smssend = StringUtils.trimToEmpty(request.getParameter("smssend"));
		String openningdate = StringUtils.trimToEmpty(request.getParameter("openningdate"));
		String closingdate = StringUtils.trimToEmpty(request.getParameter("closingdate"));
		
		Calendar calendar = Calendar.getInstance();
		final int YEAR = calendar.get(Calendar.YEAR);
		int currentYearplusone = YEAR+1;



		if(StringUtils.isEmpty(term)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_TERM); 

		}else if(StringUtils.isEmpty(year)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_YEAR); 
		}
		else if(!isNumeric(term)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_TERM_NUMERIC); 

		}else if(Integer.parseInt(term) >3 || Integer.parseInt(term) ==0){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_TERM_NOT_ALLOWED); 

		}else if(!isNumeric(year)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_YEAR_NUMERIC); 

		}
		else if(Integer.parseInt(year)>currentYearplusone || Integer.parseInt(year)<YEAR){ 
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_YEAR_OUTSIDE_RANGE); 

		}else if(StringUtils.isEmpty(examcode)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_CODE); 

		}else if(!examcodeList.contains(examcode)){ 
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_CODE_INVALID); 

		}else if(StringUtils.isEmpty(smssend)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_SMS_SEND); 

		}else if(!smssendList.contains(smssend)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_SMS_SEND_INVALID); 

		}else if(StringUtils.isEmpty(openningdate)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_OPENNING_DATE); 

		}else if(StringUtils.isEmpty(closingdate)){
			session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_EMPTY_EXAM_CLOSING_DATE); 

		}else{
			SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schooluuid); 
			systemConfig.setAccountUuid(schooluuid);
			systemConfig.setTerm(term);
			systemConfig.setYear(year);
			systemConfig.setExamcode(examcode);
			systemConfig.setSmsSend(smssend);
			systemConfig.setOpenningDate(openningdate);
			systemConfig.setClosingDate(closingdate); 

			if(systemConfigDAO.updateSystemConfig(systemConfig)){  
				session.setAttribute(SessionConstants.SCHOOL_SUCCESS, ERROR_SYS_UPDATE_SUCCESS); 
			}else{
				session.setAttribute(SessionConstants.SCHOOL_ERROR, ERROR_SYS_UPDATE_ERROR);
			}

		}

		response.sendRedirect("controlPanel.jsp");  
		return;

	}
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			Double.parseDouble(str);  

		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -2495275430951644100L;

}
