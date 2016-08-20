/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.exam.std4_8;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO;
import ke.co.fastech.primaryschool.persistence.school.ClassroomDAO;
import ke.co.fastech.primaryschool.persistence.school.CommentDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class ReportCard extends HttpServlet{
	
	private Cache accountCache;
	private Logger logger;
	
	private static SystemConfigDAO systemConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static CommentDAO commentDAO;
	private static ClassroomDAO classroomDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger = Logger.getLogger(this.getClass());
		CacheManager mgr = CacheManager.getInstance();
		accountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID); 
		
		systemConfigDAO = SystemConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		commentDAO = CommentDAO.getInstance();
		classroomDAO = ClassroomDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
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

		HttpSession session = request.getSession(false);
		//response.setContentType("application/pdf");
		
		String streamuuid = request.getParameter("streamuuid");
		String accountuuid =request.getParameter("accountuuid");
		//String schoolId = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_ACCOUNTUUID); 
		
		Account school = new Account();

		Element element;
		if ((element = accountCache.get(accountuuid)) != null) {
			school = (Account) element.getObjectValue();
		}
		
		SystemConfig systemConfig = systemConfigDAO.getSystemConfig(school.getUuid()); 
		systemConfig.getEndTerm();
		systemConfig.geteTMidTerm();
		systemConfig.getExamAll();
		systemConfig.getTerm();
		systemConfig.getYear();
		
		OutputStream out = response.getOutputStream();

		response.setContentType("application/json;charset=UTF-8");

		// Instantiate the JSon
		// The '=' sign is encoded to \u003d. Hence you need to use
		// disableHtmlEscaping().
		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();
		
		out.write(gson.toJson(getSource(accountuuid,streamuuid)).getBytes());
		out.flush();
		out.close();
		
		//populatePDFDocument(systemConfig);
	
      }
	

	private String getSource(String accountuuid, String streamuuid) {
		return accountuuid + " " + streamuuid; 
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


}
