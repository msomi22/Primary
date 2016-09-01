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
public class PromotePerStudent extends HttpServlet {
	
	private final String ERROR_ADMNO_NOT_FOUND = "No student with the given admission number, try again";
	private final String ERROR_STUDENT_INACTIVE = "This student is inactive, try again later";
	private final String ERROR_ADMNO_BLANK = "Please provide admission number and try again";
	private final String ERROR_STUDENT_IN_THE_STREAM = "The student is already a member of the class specified";
	private final String ERROR_NO_STREAM = "Please select a class and try again";
	private final String ERROR_NOT_PROMOTED = "Something went wrong, try again";
	private final String SUCCESS_PROMOTED = "Transaction successful";
	
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
		String newStreamuuid = request.getParameter("newStreamuuid");
		String schoolid = request.getParameter("schoolid");
		
		if(StringUtils.isBlank(admissionNo)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_ADMNO_BLANK); 
			
		}else if(StringUtils.isBlank(newStreamuuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NO_STREAM); 
			
		}else if(StringUtils.isBlank(schoolid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_PROMOTED); 
			
		}else if(studentDAO.getStudentByADMNO(admissionNo, schoolid) == null){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_ADMNO_NOT_FOUND); 
			
		}else if(studentDAO.getStudentBystatus(admissionNo, ExamConstants.STATUS_IN_ACTIVE, schoolid) != null){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_STUDENT_INACTIVE); 
			
		}else if(studentDAO.getStudentBystream(admissionNo, newStreamuuid, schoolid) != null){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_STUDENT_IN_THE_STREAM); 
			
		}else{
			Student student = studentDAO.getStudentByADMNO(admissionNo, schoolid); 
			student.setStreamUuid(newStreamuuid); 
			
			if(studentDAO.updateStudent(student)){ 
				session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_PROMOTED); 
			}else{
				session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_PROMOTED); 
			}
			
		}
		response.sendRedirect("promote.jsp");  
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
	private static final long serialVersionUID = -981667272160321542L;

}
