/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.student.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class ActivatePerStream extends HttpServlet{
	
	private final String ERROR_NO_STREAM = "Please select a class to activate and try again";
	private final String ERROR_NOT_ACTIVATED = "Something went wrong while activating the students, try again";
	private final String SUCCESS_ACTIVATED = "Students activated successfully  ";
	
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
		
		String streamuuid = request.getParameter("streamuuid");
		String schoolid = request.getParameter("schoolid");
		
		if(StringUtils.isBlank(streamuuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NO_STREAM); 
			
		}else if(StringUtils.isBlank(schoolid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_ACTIVATED); 
			
		}else{
			
			List<Student> studentlist = new ArrayList<>();
			if(studentDAO.getStudentListByStreamUuid(streamuuid, schoolid) !=null){
			 studentlist = studentDAO.getStudentListByStreamUuid(streamuuid, schoolid);
			 int count = 0;
			for(Student student : studentlist){
				student.setStatusUuid(ExamConstants.STATUS_ACTIVE);
				studentDAO.updateStudent(student);
				count++;
			}
			session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_ACTIVATED + count + " Student activated"); 
			
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
	private static final long serialVersionUID = -8799550488658112509L;

}
