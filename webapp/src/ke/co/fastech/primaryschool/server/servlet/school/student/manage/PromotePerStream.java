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
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/** 
 * 
 * @author peter
 *
 */
public class PromotePerStream extends HttpServlet{
	
	private final String ERROR_NO_STREAM_FROM = "Please select a class and try again";
	private final String ERROR_NO_STREAM_TO = "Please select a destination class and try again";
	private final String ERROR_NOT_PROMOTED = "Something went wrong, try again";
	private final String SUCCESS_PROMOTED = "Transaction successful  ";
	
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
		
		String oldStreamuuid = request.getParameter("oldStreamuuid");
		String newStreamuuid = request.getParameter("newStreamuuid");
		String schoolid = request.getParameter("schoolid");
		
		if(StringUtils.isBlank(oldStreamuuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NO_STREAM_FROM); 
			
		}else if(StringUtils.isBlank(newStreamuuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NO_STREAM_TO); 
			
		}else if(StringUtils.isBlank(schoolid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_NOT_PROMOTED); 
			
		}else{
			List<Student> studentlist = new ArrayList<>();
			if(studentDAO.getStudentListByStreamUuid(oldStreamuuid, schoolid) !=null){
			 studentlist = studentDAO.getStudentListByStreamUuid(oldStreamuuid, schoolid);
			 int count = 0;
			for(Student student : studentlist){
				student.setStreamUuid(newStreamuuid); 
				studentDAO.updateStudent(student);
				count++;
			}
			session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, SUCCESS_PROMOTED + count + " Student found"); 
			
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
	private static final long serialVersionUID = -710547865251199622L;
}
