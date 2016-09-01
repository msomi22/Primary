/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.account;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class NewStream extends HttpServlet{

	final String ERROR_STREAM_INVALID = "The class name provided is invalid, try a phrase like \"STD 7 G\" , with spaces and without quotes."; 
	final String ERROR_STREAM_EXIST = "The class name provided already exist in the system.";
	final String ERROR_STREAM_NOT_ADDED = "Something went wrong while adding the new class.";
	final String SUCCESS_STREAM_ADDED = "The class was added successfully.";

	private static StreamDAO streamDAO;

	private String [] validClassNames;
	private String [] A_Z;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		streamDAO = StreamDAO.getInstance(); 
		validClassNames = new String[] {"STD 1","STD 2","STD 3","STD 4","STD 5","STD 6","STD 7","STD 8"}; 
		A_Z = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
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

		String streamname  = StringUtils.trimToEmpty(request.getParameter("streamname"));
		String schooluuid  = StringUtils.trimToEmpty(request.getParameter("schooluuid"));


		if(StringUtils.isBlank(streamname)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_INVALID); 

		}else if(StringUtils.isBlank(schooluuid)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_NOT_ADDED); 

		}else if(!roomNameValid(streamname)){ 
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_INVALID); 

		}else if(!lengthValid(streamname)){
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_INVALID); 

		}else if(streamDAO.getStream(streamname,schooluuid) != null){   
			session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_EXIST); 

		}else{
			Stream stream = new Stream();
			stream.setAccountUuid(schooluuid); 
			stream.setStreamName(streamname); 
			if(streamDAO.putStream(stream)){ 
				session.setAttribute(SessionConstants.STAFF_SUCCESS, SUCCESS_STREAM_ADDED); 
			}else{
				session.setAttribute(SessionConstants.STAFF_ERROR, ERROR_STREAM_NOT_ADDED); 
			}
		}
		response.sendRedirect("academic.jsp");  
		return;
	}

	/**
	 * @param roomname
	 * @return
	 */
	private boolean roomNameValid(String roomname) {
		boolean valid = false;
		String classname = "";
		for(int j=0;j<validClassNames.length;j++){
			for(int i=0;i<A_Z.length;i++){
				classname = validClassNames[j]+" "+A_Z[i];
				if(classname.contains(roomname)){
					valid = true;
				}
			}
		}

		return valid;
	}


	/**
	 * @param mystring
	 * @return
	 */
	private static boolean lengthValid(String mystring) {
		boolean isvalid = true;
		int length = 0;
		length = mystring.length();
		if(length<7 ||length>7){  
			isvalid = false;
		}
		return isvalid;
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
