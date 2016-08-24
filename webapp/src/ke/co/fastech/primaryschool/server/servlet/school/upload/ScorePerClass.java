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

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;
import ke.co.fastech.primaryschool.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author peter
 *
 */
public class ScorePerClass extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3828305434810949182L;
	public final static String UPLOAD_FEEDBACK = "UploadFeedback";
	public final static String UPLOAD_SUCCESS = "You have successfully uploaded your results.";

	public String USER = "";
	public String UPLOAD_DIR = "";

	private Cache schoolCache;

	private Logger logger;
	String staffUsername;
	String schooluuid = "";
	private PerClassExcelUtil perClassExcelUtil;

	String classuuid = "";
	String room = "";
	
	private static TeacherSubjectDAO teacherSubjectDAO;
	private static SystemConfigDAO systemConfigDAO;
	public static ExamEngineDAO examEngineDAO;
	private static SubjectDAO subjectDAO;
	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();
		systemConfigDAO = SystemConfigDAO.getInstance();
		examEngineDAO = ExamEngineDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		streamDAO = StreamDAO.getInstance();

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		File repository = FileUtils.getTempDirectory();
		factory.setRepository(repository);

		USER = System.getProperty("user.name");
		UPLOAD_DIR =  "/home/"+USER+"/school/exams"; 
		perClassExcelUtil = new PerClassExcelUtil();
		
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
		staffUsername = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_USERNAME);
		
		Account school = new Account();
		Element element;
		if ((element = schoolCache.get(schoolusername)) != null) {
			school = (Account) element.getObjectValue();
			schooluuid = school.getUuid();
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set up where the files will be stored on disk
		File repository = new File(UPLOAD_DIR + File.separator + "user");
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
						uploadedFile = processUploadedFile(item);
						String feedback = "";

						if(uploadedFile !=null){
							feedback = perClassExcelUtil.inspectResultFile(uploadedFile,schooluuid,streamDAO,subjectDAO,teacherSubjectDAO,studentDAO);
						}
						session.setAttribute(UPLOAD_FEEDBACK,"<p class='error'>"+feedback+"<p>");

						// Process the file into the database if it is ok
						if(StringUtils.equals(feedback, UPLOAD_SUCCESS)) {
							if(uploadedFile !=null){
								perClassExcelUtil.saveResults(uploadedFile,school,examEngineDAO,studentDAO,streamDAO,subjectDAO,systemConfigDAO); 
							}
						}


					}	
				} 
			}// end 'while (iter.hasNext())'

		} catch (FileUploadException e) {
			logger.error("FileUploadException while getting File Items.");
			logger.error(e);
		} 
		
		 response.sendRedirect("perclassUpload.jsp");
	     return;

	}



	/**
	 * @param item
	 * @return the file handle
	 */
	private File processUploadedFile(FileItem item) {
		// A specific folder in the system
		String folder = UPLOAD_DIR + File.separator +staffUsername;
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
		return file;
	}
}
