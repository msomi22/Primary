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
public class DeactivatePerStream extends HttpServlet{
	
	private final String ERROR_NO_STREAM = "Please select a class to deactivate and try again";
	private final String ERROR_NOT_DEACTIVATED = "Something went wrong while deactivating the students, try again";
	private final String SUCCESS_DEACTIVATED = "Students deactivated successfully  ";
	
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
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_DEACTIVATED); 
			
		}else{

			List<Student> studentlist = new ArrayList<>();
			if(studentDAO.getStudentListByStreamUuid(streamuuid, schoolid) !=null){
			 studentlist = studentDAO.getStudentListByStreamUuid(streamuuid, schoolid);
			 int count = 0;
			for(Student student : studentlist){
				student.setStatusUuid(ExamConstants.STATUS_IN_ACTIVE);
				studentDAO.updateStudent(student);
				count++;
			}
			session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_DEACTIVATED + count + " Student deactivated"); 
			
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
	private static final long serialVersionUID = 6161676157208644352L;
}
