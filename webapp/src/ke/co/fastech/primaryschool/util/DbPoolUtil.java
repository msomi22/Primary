/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.util;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.persistence.DBCredentials;

/**
 * Utility dealing with database connection pooling.
 * <p>
 *  
 * @author <a href="mailto:michael@tawi.mobi">Michael Wakahe</a>
 */
public class DbPoolUtil extends HttpServlet {

	
	private static ke.co.fastech.primaryschool.persistence.DBCredentials dBCredentials; 
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	/**
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        dBCredentials = new DBCredentials();
    }
    
    
    /**
     * @return the database credentials class
     */
    public static DBCredentials getDBCredentials() {
    	return dBCredentials;
    }
    
    
    /**
     * 
     */
    @Override
    public void destroy() {
		logger.info("Now shutting down database pools.");
    	
		dBCredentials.closeConnections();		
	} 
    
    
    private static final long serialVersionUID = -7899535368789138778L;
}
