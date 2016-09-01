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

import ke.co.fastech.primaryschool.bean.staff.ClassTeacher;
import ke.co.fastech.primaryschool.bean.staff.Staff;
import ke.co.fastech.primaryschool.persistence.staff.StaffDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class NewClassTeacher extends HttpServlet{

	final String ERROR_STREAM_ENGANGED = "The class selected is already assigned to another teacher , confirm this and try again.";
	final String ERROR_TEACHER_ENGANGED = "The teacher provided is already assigned to another class , confirm this and try again.";
	final String ERROR_STAFF_NO_NOTEXIST = "The employee number provided was not found in the sysyetm , try again."; 
	final String ERROR_STAFF_NO_EMPTY = "Please provide teacher's employee number and try again.";
	final String ERROR_STREAM_NOT_ASSIGNED = "Something went wrong, try again later.";
	final String ERROR_STREAM_NOT_SELECTED = "Please select a class and try again.";
	final String SUCCESS_STREAM_ASSIGNED = "The class was assigned successfully.";

	private static TeacherClassDAO teacherClassDAO;
	private static StaffDAO staffDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		teacherClassDAO = TeacherClassDAO.getInstance();
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

		String employeeNo = StringUtils.trimToEmpty(request.getParameter("employeeNo"));
		String streamuuid = StringUtils.trimToEmpty(request.getParameter("streamuuid"));
		String schooluuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));

		if(StringUtils.isBlank(employeeNo)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STAFF_NO_EMPTY); 

		}else if(StringUtils.isBlank(streamuuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_NOT_SELECTED); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_NOT_ASSIGNED); 

		}else if(teacherClassDAO.getTeacherClassByClassid(streamuuid) !=null){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_ENGANGED); 

		}else if(staffDAO.getStaff(schooluuid, employeeNo) == null){ 
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STAFF_NO_NOTEXIST); 

		}else{
			Staff staff = staffDAO.getStaff(schooluuid, employeeNo); 
			if(teacherClassDAO.getTeacherClass(staff.getUuid()) == null){
				ClassTeacher classTeacher = new ClassTeacher();
				classTeacher.setAccountUuid(schooluuid);
				classTeacher.setStreamUuid(streamuuid);
				classTeacher.setTeacherUuid(staff.getUuid()); 
				if(teacherClassDAO.putClassTeacher(classTeacher)){
					session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_STREAM_ASSIGNED);
				}else{
					session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_NOT_ASSIGNED); 
				}
			}else{
				session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_TEACHER_ENGANGED); 
			}

		}
		response.sendRedirect("classTeacher.jsp");  
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
	private static final long serialVersionUID = 4595093586446260443L;

}
