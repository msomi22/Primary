/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class Logout extends HttpServlet{


	private Cache accountsCache;
	Map<String,String> onlineUsersMap;
	ServletContext context;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		CacheManager mgr = CacheManager.getInstance();
		accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		onlineUsersMap = new HashMap<String,String>();
		context = getServletContext();

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		response.sendRedirect("index.jsp");

		if (session != null) {
			// Remove the statistics of this user from cache
			String schoolusername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY); 
			String userId = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_ID);
			String username = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_USERNAME);
			Account school = new Account();

			Element element;
			if ((element = accountsCache.get(schoolusername)) != null) {
				school = (Account) element.getObjectValue();
			}
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, YYYY: h-m-s");

			System.out.println("\""+username + "\" form  \"" + school.getSchoolName() + "\" has logged out on \"" + dateFormatter.format(new Date()) +"\""); 
          
			onlineUsersMap =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
			if(!onlineUsersMap.isEmpty()){  
				onlineUsersMap.remove(userId);
			}
			

			//destroy the session
			session.invalidate();


		} // end 'if (session != null) '

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
	private static final long serialVersionUID = 4723280485295602266L;

}
