/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.student;

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
import ke.co.fastech.primaryschool.server.session.SessionConstants;


/**
 * @author peter
 *
 */
public class UpdateStudent extends HttpServlet{
	
	final String ERROR_ADMNO_EXIST = "Admission number already exist in the system.";
	final String STUDENT_UPDATE_ERROR = "An error occured while updating student.";
	final String STUDENT_UPDATE_SUCCESS = "Student updated successfully.";
	final String ERROR_EMPTY_ADM_NO = "Admission number can't be empty.";
	final String ERROR_EMPTY_MIDDLE_NAME = "Middlename can't be empty.";
	final String ERROR_EMPTY_FIRST_NAME = "Firstname can't be empty.";
	final String ERROR_EMPTY_LASTNAME = "Lastname can't be empty.";
	
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

		String admnumber = StringUtils.trimToEmpty(request.getParameter("admissionNumber"));
		String firstname = StringUtils.trimToEmpty(request.getParameter("firstname"));
		String middlename = StringUtils.trimToEmpty(request.getParameter("middlename"));
		String lastname = StringUtils.trimToEmpty(request.getParameter("lastname"));
		String studentUuid = StringUtils.trimToEmpty(request.getParameter("studentUuid"));
		String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schoolUuid"));
		
		if(StringUtils.isBlank(admnumber)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_ADM_NO); 
			
		}else if(StringUtils.isBlank(firstname)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FIRST_NAME); 
			
		}else if(StringUtils.isBlank(middlename)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MIDDLE_NAME); 
			
		}else if(StringUtils.isBlank(lastname)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_LASTNAME); 
			
		}else if(StringUtils.isBlank(studentUuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_UPDATE_ERROR); 
			
		}else if(StringUtils.isBlank(schoolUuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_UPDATE_ERROR); 
			
		}else{
			
			Student student = new Student();
			if(studentDAO.getStudentByUuid(studentUuid) !=null){
				student = studentDAO.getStudentByUuid(studentUuid); 
				student.setAdmmissinNo(admnumber); 
				student.setFirstname(firstname);
				student.setMiddlename(middlename);
				student.setLastname(lastname); 
			}
			
			if(studentDAO.updateStudent(student)){ 
				session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, STUDENT_UPDATE_SUCCESS); 
			}else{
				session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_UPDATE_ERROR); 
			}
			
		}
		response.sendRedirect("schoolIndex.jsp");  
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
	private static final long serialVersionUID = -2873623657889649695L;


}
