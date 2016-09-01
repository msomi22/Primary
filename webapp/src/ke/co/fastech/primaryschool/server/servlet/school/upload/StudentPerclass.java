/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO;
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
public class StudentPerclass extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3950960967830109538L;
	
	public final static String UPLOAD_FEEDBACK = "UploadFeedback";
	public final static String UPLOAD_SUCCESS = "Student's successfully uploaded.";
	
	public String USER = "";
	public String UPLOAD_DIR = "";
	
	private Cache schoolCache;
	private StudentExcelUtil studentExcelUtil;
	
	private Logger logger;
	private String  username = "";
	private String schooluuid = "";
	
	
	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	private static SystemConfigDAO systemConfigDAO;
	private static StudentSubjectDAO studentSubjectDAO;
	
	String classuuid = "";
	String room = "";
	
	   public void init(ServletConfig config) throws ServletException {
	       super.init(config);
	       
	       // Create a factory for disk-based file items
	       DiskFileItemFactory factory = new DiskFileItemFactory();

	       File repository = FileUtils.getTempDirectory();
	       factory.setRepository(repository);
	       
	       USER = System.getProperty("user.name");
	       UPLOAD_DIR =  "/home/"+USER+"/school/exams"; 
	    
	       CacheManager mgr = CacheManager.getInstance();
	       schoolCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
	       studentDAO = StudentDAO.getInstance();
	       streamDAO = StreamDAO.getInstance();
	       subjectDAO = SubjectDAO.getInstance();
	       systemConfigDAO = SystemConfigDAO.getInstance();
	       studentSubjectDAO = StudentSubjectDAO.getInstance();
	       studentExcelUtil = new StudentExcelUtil();
	       
	   }
	   

		/**
	    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 	*/
		
		@Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
			File uploadedFile = null;
			HttpSession session = request.getSession(false);
			

			String schoolusername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);
			
			  Account school = new Account();
		       Element element;
		       if ((element = schoolCache.get(schoolusername)) != null) {
		    	   school = (Account) element.getObjectValue();
		    	   schooluuid = school.getUuid();
		    	   username = school.getUsername();
		        }
			 
			// Create a factory for disk-based file items
	       DiskFileItemFactory factory = new DiskFileItemFactory();

	       // Set up where the files will be stored on disk
	       File repository = new File(UPLOAD_DIR);
	       FileUtils.forceMkdir(repository); 
	       factory.setRepository(repository);
	       
	       // Create a new file upload handler
	       ServletFileUpload upload = new ServletFileUpload(factory);
	       // Parse the request    
	       
	       try {
	    	
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			
			FileItem item;
			
			while (iter.hasNext()) {
			    item = iter.next();

			    if (!item.isFormField()) {
			    	if(item!=null){
			    		  uploadedFile = processUploadedFiles(item);
			    		  String feedback = "";
			    	      feedback = studentExcelUtil.processUploadedFiles(uploadedFile,schooluuid,studentDAO,streamDAO);
			    	     
			    		   session.setAttribute(UPLOAD_FEEDBACK,"<p class='error'>"+feedback+"<p>");
			    		    // Process the file into the database if it is ok
			    	       if(StringUtils.equals(feedback, UPLOAD_SUCCESS)) {
			    	    		studentExcelUtil.saveResults(uploadedFile,school,streamDAO,studentDAO, studentSubjectDAO, subjectDAO, systemConfigDAO);
			    	         }
			    	      
			    	}	
			    } 
			}// end 'while (iter.hasNext())'
	    
		    } catch (FileUploadException e) {
		    	logger.error("FileUploadException while getting File Items.");
				logger.error(e);
		   } catch (InvalidFormatException e) {
				e.printStackTrace();
			} 
		 
	      
		   response.sendRedirect("importexcel.jsp");
	       return;
		   
		}
		

		
		/**
		 * @param item
		 * @return the file handle
		 */
		private File processUploadedFiles(FileItem item) {
			// A specific folder in the system
			String folder = UPLOAD_DIR + File.separator + username;
			File file = null;
			
	        try {
				FileUtils.forceMkdir(new File(folder));
				file = new File(folder + File.separator + item.getName());
				item.write(file); 
				
			} catch (IOException e) {
				logger.error("IOException while processUploadedFile: " + item.getName());
				logger.error(e);
				
			} catch (Exception e) {
				logger.error("Exception while processUploadedFile: " + item.getName());
				logger.error(e);
			} 
	        //System.out.println("Saved File = "+file+"\n");
	        return file;
		}

}
