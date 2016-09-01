/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.staff;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.staff.Staff;
import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;
import ke.co.fastech.primaryschool.persistence.staff.StaffDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class NewTeacherSubject extends HttpServlet{


	final String ERROR_EMPLOYEE_NUMBER_NOTFOUND = "The employee number was not found in the system.";
	final String ERROR_SUBJECT_CLASS_ENGANGED = "The subject is already assigned to another teacher."; 
	final String ERROR_SUBJECT_NOT_ADDED = "Something went wrong while assigning the subject.";
	final String ERROR_NO_EMPLOYEE_NUMBER = "please provide employee number and try again.";
	final String ERROR_SUBJECT_EMPTY = "Please select a subject and try again.";
	final String ERROR_CLASS_EMPTY = "please select a class and try again.";
	final String SUCCESS_SUBJECT_ADDED = "Subject assigned successfully.";

	private static TeacherSubjectDAO teacherSubjectDAO;
	private static StaffDAO staffDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		teacherSubjectDAO = TeacherSubjectDAO.getInstance(); 
		staffDAO = StaffDAO.getInstance();
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

		String employeeNo  = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
		String subjectuuid  = StringUtils.trimToEmpty(request.getParameter("subjectuuid"));
		String streamuuid  = StringUtils.trimToEmpty(request.getParameter("streamuuid"));
		String schooluuid  = StringUtils.trimToEmpty(request.getParameter("schooluuid"));

		if(StringUtils.isBlank(employeeNo)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_NO_EMPLOYEE_NUMBER); 

		}else if(StringUtils.isBlank(subjectuuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_EMPTY); 

		}else if(StringUtils.isBlank(streamuuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_CLASS_EMPTY); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_ADDED); 

		}else if(staffDAO.getStaff(schooluuid, employeeNo) == null){ 
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_EMPLOYEE_NUMBER_NOTFOUND); 

		}else if(teacherSubjectDAO.getTeacherSubject(subjectuuid, streamuuid) != null){  
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_CLASS_ENGANGED); 

		}else{
			Staff staff = new Staff();
			if(staffDAO.getStaff(schooluuid, employeeNo) !=null){ 
				 staff = staffDAO.getStaff(schooluuid, employeeNo);
				if(teacherSubjectDAO.getTeacherSubject(staff.getUuid(),subjectuuid,streamuuid) == null){ 
					TeacherSubject teacherSubject = new TeacherSubject();
					teacherSubject.setTeacherUuid(staff.getUuid()); 
					teacherSubject.setSubjectUuid(subjectuuid);
					teacherSubject.setStreamUuid(streamuuid); 
					if(teacherSubjectDAO.putTeacherSubject(teacherSubject)){ 
						session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_SUBJECT_ADDED);  
					}else{
						session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_ADDED); 
					}
				}else{
					session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_CLASS_ENGANGED); 
				}
			}
		}
		response.sendRedirect("staff.jsp");  
		return;
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
