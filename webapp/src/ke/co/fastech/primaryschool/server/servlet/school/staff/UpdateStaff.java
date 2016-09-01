/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.staff;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.staff.Staff;
import ke.co.fastech.primaryschool.persistence.staff.StaffDAO;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class UpdateStaff extends HttpServlet{

	final String ERROR_SELECT_CATEGORY = "Please select a category.";
	final String ERROR_PROVIDE_EMP_NO = "Please provide the employee number.";
	final String ERROR_PROVIDE_USERNAME = "Please provide a username.";
	final String ERROR_PROVIDE_STAFF_NAME = "Please provide staff full name.";
	final String ERROR_PROVIDE_STAFF_PHONE = "Please provide phone number.";
	final String ERROR_PROVIDE_STAFF_EMAIL = "please provide the email address.";
	final String ERROR_SELECT_STAFF_GENDER = "Please select the gender.";
	final String ERROR_GENDER_INCORRECT = "Gender can either be M for Male or F for Female.";
	final String ERROR_PHONE_INVALID = "Staff phone is invalid, the number must have 10 digits (e.g. 0718953974).";

	//final String ERROR_STAFF_EMP_NO_EXIST = "The employee number provided already exist.";
	final String ERROR_STAFF_NOT_UPDATED = "Something went wrong while updating the staff.";
	final String SUCCESS_STAFF_AUPDATED = "Staff updated successfully.";

	private static StaffDAO staffDAO;
	
	private String[] genderArray;
	private List<String> genderList;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		staffDAO = StaffDAO.getInstance();
		genderArray = new String[] {"M", "F","m","f"}; 
		genderList = Arrays.asList(genderArray);
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

		HttpSession session = request.getSession(true);

		//String categoryuuid  = StringUtils.trimToEmpty(request.getParameter("categoryuuid"));
		String employeeNo  = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
		String staffname  = StringUtils.trimToEmpty(request.getParameter("staffname"));
		String phone  = StringUtils.trimToEmpty(request.getParameter("phone"));
		String email  = StringUtils.trimToEmpty(request.getParameter("email"));
		String gender  = StringUtils.trimToEmpty(request.getParameter("gender"));
		String schooluuid  = StringUtils.trimToEmpty(request.getParameter("schooluuid"));
/*
		if(StringUtils.isBlank(categoryuuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SELECT_CATEGORY); 

		}else*/ if(StringUtils.isBlank(employeeNo)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PROVIDE_EMP_NO); 

		}else if(StringUtils.isBlank(staffname)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PROVIDE_STAFF_NAME); 

		}else if(StringUtils.isBlank(phone)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PROVIDE_STAFF_PHONE); 

		}else if(!isNumeric(phone)){
	    	   session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PHONE_INVALID); 
	    	   
	       }else if(!lengthValid(phone)){
		 	   session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PHONE_INVALID); 
			   
		   }/*else if(StringUtils.isBlank(email)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_PROVIDE_STAFF_EMAIL); 

		}*/else if(StringUtils.isBlank(gender)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SELECT_STAFF_GENDER); 

		}else if(!genderList.contains(gender)){   
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_GENDER_INCORRECT); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STAFF_NOT_UPDATED);  

		}/*else if(staffDAO.getStaff(schooluuid, employeeNo) != null){  
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STAFF_EMP_NO_EXIST);  

		}*/else{
			Staff staff = new Staff();
			if(staffDAO.getStaff(schooluuid, employeeNo) !=null){

				staff = staffDAO.getStaff(schooluuid, employeeNo); 
				staff.setAccountUuid(schooluuid);
				staff.setStatusUuid(ExamConstants.STATUS_ACTIVE); 
				staff.setEmployeeNo(employeeNo);
				staff.setName(staffname); 
				staff.setPhone(phone);
				staff.setEmail(email);
				staff.setGender(gender);
				staff.setDob("00/00/00");
				staff.setCountry("Kenya"); 
				staff.setCounty(" ");
				staff.setWard(" ");
                
				if(staffDAO.updateStaff(staff)){
					session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_STAFF_AUPDATED);   
				}else{
					session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STAFF_NOT_UPDATED);  
				}
			}
		}
		response.sendRedirect("staff.jsp");  
		return;
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
	private static final long serialVersionUID = -4618256381040595175L;
}
