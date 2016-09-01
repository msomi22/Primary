/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.admin.school;

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
import ke.co.fastech.primaryschool.bean.staff.StaffCategory;
import ke.co.fastech.primaryschool.bean.staff.user.Users;
import ke.co.fastech.primaryschool.persistence.staff.StaffDAO;
import ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO;
import ke.co.fastech.primaryschool.persistence.user.UserDAO;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import ke.co.fastech.primaryschool.server.session.AdminSessionConstants;

/**
 * @author peter
 *
 */
public class AddHeadTeacher extends HttpServlet{

	final String ERROR_SELECT_CATEGORY = "Please select a category.";
	final String ERROR_PROVIDE_EMP_NO = "Please provide the employee number.";
	final String ERROR_PROVIDE_USERNAME = "Please provide a username.";
	final String ERROR_PROVIDE_PASSWORD = "Please provide a password.";
	final String ERROR_PROVIDE_STAFF_NAME = "Please provide staff full name.";
	final String ERROR_PROVIDE_STAFF_PHONE = "Please provide phone number.";
	final String ERROR_PROVIDE_STAFF_EMAIL = "please provide the email address.";
	final String ERROR_SELECT_STAFF_GENDER = "Please select the gender.";
	final String ERROR_GENDER_INCORRECT = "Gender can either be M for Male or F for Female.";
	final String ERROR_PHONE_INVALID = "The phone is invalid, the number must have 10 digits (e.g. 0718953974).";

	final String ERROR_STAFF_EMP_NO_EXIST = "The employee number provided already exist.";
	final String ERROR_STAFF_NOT_ADDED = "Something went wrong while adding the Headteacher.";
	final String SUCCESS_STAFF_ADDED = "Headteacher added successfully.";

	private static StaffCategoryDAO staffCategoryDAO;
	private static StaffDAO staffDAO;
	private static UserDAO userDAO;

	private String[] genderArray;
	private List<String> genderList;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		staffCategoryDAO = StaffCategoryDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		userDAO = UserDAO.getInstance();
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

		String accountUuid  = StringUtils.trimToEmpty(request.getParameter("accountUuid"));
		String categoryuuid  = StringUtils.trimToEmpty(request.getParameter("categoryuuid"));
		String employeeNo  = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
		String username  = StringUtils.trimToEmpty(request.getParameter("username"));
		String password  = StringUtils.trimToEmpty(request.getParameter("password"));
		String staffname  = StringUtils.trimToEmpty(request.getParameter("staffname"));
		String phone  = StringUtils.trimToEmpty(request.getParameter("phone"));
		String email  = StringUtils.trimToEmpty(request.getParameter("email"));
		String gender  = StringUtils.trimToEmpty(request.getParameter("gender"));


		if(StringUtils.isBlank(categoryuuid)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_SELECT_CATEGORY); 

		}else if(StringUtils.isBlank(employeeNo)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_EMP_NO); 

		}else if(StringUtils.isBlank(username)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_USERNAME); 

		}else if(StringUtils.isBlank(password)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_PASSWORD); 

		}else if(StringUtils.isBlank(staffname)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_STAFF_NAME); 

		}else if(StringUtils.isBlank(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_STAFF_PHONE); 

		}else if(!isNumeric(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_INVALID); 

		}else if(!lengthValid(phone)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PHONE_INVALID); 

		}/*else if(StringUtils.isBlank(email)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_PROVIDE_STAFF_EMAIL); 

		}*/else if(StringUtils.isBlank(gender)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_SELECT_STAFF_GENDER); 

		}else if(!genderList.contains(gender)){ 
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_GENDER_INCORRECT); 

		}else if(StringUtils.isBlank(accountUuid)){
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_STAFF_NOT_ADDED);  

		}else if(staffDAO.getStaff(accountUuid, employeeNo) != null){  
			session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_STAFF_EMP_NO_EXIST);  

		}else{
			Staff staff = new Staff();
			staff.setAccountUuid(accountUuid);
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

			Users users = new Users();
			users.setStaffUuid(staff.getUuid()); 
			users.setUsername(username);
			users.setPassword(password);  

			StaffCategory staffCategory = new StaffCategory();
			staffCategory.setStaffUuid(staff.getUuid());
			staffCategory.setCategoryUuid(categoryuuid);

			if(staffDAO.putStaff(staff)){ 
				userDAO.putUser(users);
				staffCategoryDAO.putStaffCategory(staffCategory); 
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_SUCCESS, SUCCESS_STAFF_ADDED);  
			}else{
				session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, ERROR_STAFF_NOT_ADDED);  
			}

		}
		response.sendRedirect("adminIndex.jsp");  
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
