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

import ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class DeleteClassTeacher extends HttpServlet{
	
	final String ERROR_DATA_NOT_REMOVED = "Something went wrong while removing teacher-class data.";
	final String SUCCESS_DATA_REMOVED = "The teacher-class data was removed successfully.";
	
	private static TeacherClassDAO teacherClassDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		teacherClassDAO = TeacherClassDAO.getInstance();
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
		
		String teacheruuid  = StringUtils.trimToEmpty(request.getParameter("teacheruuid"));
		

		if(StringUtils.isBlank(teacheruuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_DATA_NOT_REMOVED); 

		}else{
			if(teacherClassDAO.deleteClassTeacher(teacheruuid)){ 
				session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_DATA_REMOVED); 
			}else{
				session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_DATA_NOT_REMOVED); 
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
	private static final long serialVersionUID = -7601182159808563538L;
}
