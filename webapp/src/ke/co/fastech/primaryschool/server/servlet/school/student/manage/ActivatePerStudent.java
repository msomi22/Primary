/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.student.manage;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/** 
 * 
 * @author peter
 *
 */
public class ActivatePerStudent extends HttpServlet{
	
	
	private final String ERROR_ADMNO_NOT_FOUND = "No student with the given admission number, try again";
	private final String ERROR_STUDENT_ACTIVE = "This student is already active";
	private final String ERROR_ADMNO_BLANK = "Please provide admission number and try again";
	private final String ERROR_NOT_ACTIVATED = "Something went wrong while activating the student, try again";
	private final String SUCCESS_ACTIVATED = "Student activated successfully";
	
	private static StudentDAO studentDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentDAO = StudentDAO.getInstance();
	
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
		
		String admissionNo = request.getParameter("admissionNo");
		String schoolid = request.getParameter("schoolid");
		
		if(StringUtils.isBlank(admissionNo)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_ADMNO_BLANK); 
			
		}else if(StringUtils.isBlank(schoolid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_ACTIVATED); 
			
		}else if(studentDAO.getStudentByADMNO(admissionNo, schoolid) == null){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_ADMNO_NOT_FOUND); 
			
		}else if(studentDAO.getStudentBystatus(admissionNo, ExamConstants.STATUS_ACTIVE, schoolid) != null){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_STUDENT_ACTIVE); 
			
		}else{
			
			Student student = studentDAO.getStudentByADMNO(admissionNo, schoolid); 
			student.setStatusUuid(ExamConstants.STATUS_ACTIVE); 
			
			if(studentDAO.updateStudent(student)){ 
				session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_ACTIVATED); 
			}else{
				session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_ACTIVATED); 
			}
			
		}
		response.sendRedirect("deactivate.jsp");  
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
	private static final long serialVersionUID = 3192007667267128908L;
}
