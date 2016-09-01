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

import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class DeleteTeacherSubject extends HttpServlet{
	
	final String ERROR_SUBJECT_NOT_DELETED = "Something went wrong while deleting the subject.";
	final String SUCCESS_SUBJECT_DELETED = "The subject was deleted successfully.";
	
	private static TeacherSubjectDAO teacherSubjectDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		teacherSubjectDAO = TeacherSubjectDAO.getInstance(); 
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
		
		String streamuuid  = StringUtils.trimToEmpty(request.getParameter("streamuuid"));
		String subjectuuid  = StringUtils.trimToEmpty(request.getParameter("subjectuuid"));
		String teacheruuid  = StringUtils.trimToEmpty(request.getParameter("teacheruuid"));
		

		if(StringUtils.isBlank(teacheruuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_DELETED); 

		}else if(StringUtils.isBlank(subjectuuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_DELETED); 

		}else if(StringUtils.isBlank(teacheruuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_DELETED); 

		}{
			if(teacherSubjectDAO.deleteTeacherSubject(teacheruuid, subjectuuid, streamuuid)){  
				session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_SUBJECT_DELETED); 
			}else{
				session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_SUBJECT_NOT_DELETED); 
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
	private static final long serialVersionUID = -7601182159808563538L;
}
