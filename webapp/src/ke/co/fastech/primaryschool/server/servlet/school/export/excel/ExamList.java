/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.export.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import ke.co.fastech.primaryschool.server.session.SessionConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**  examList
 * @author peter
 *
 */
public class ExamList extends HttpServlet{

	private static final long serialVersionUID = 3896751907947782599L;

	private HashMap<String, String> streamHash = new HashMap<String, String>();
	private Cache schoolaccountCache;
	private SystemConfig systemConfig;


	private static StudentDAO studentDAO;
	private static StreamDAO streamDAO;
	private static SystemConfigDAO systemConfigDAO;
	private String streamuuid = "";
	private String schoolusername = "";
	private ServletOutputStream out;

	private String classCode = "";
	private String examCode = "";


	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);       
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		studentDAO = StudentDAO.getInstance();
		systemConfigDAO = SystemConfigDAO.getInstance();
		streamDAO = StreamDAO.getInstance();

	}

	/**    
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 * @throws java.io.IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "cache, must-revalidate");
		response.setHeader("Pragma", "public");

		HttpSession session = request.getSession(false); 

		streamuuid = StringUtils.trimToEmpty(request.getParameter("streamuuid"));
		
		Account school = new Account();
		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);

		}
		net.sf.ehcache.Element element;
		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (Account) element.getObjectValue();

		}

		systemConfig = systemConfigDAO.getSystemConfig(school.getUuid());


		List<Student> studentList = new ArrayList<>();
		if(studentDAO.getStudentListByStreamUuid(streamuuid,school.getUuid()) !=null){
			studentList = studentDAO.getStudentListByStreamUuid(streamuuid,school.getUuid());
		}

		List<Stream> classroomList = new ArrayList<Stream>(); 
		classroomList = streamDAO.getStreamList(school.getUuid()); 
		for(Stream st : classroomList){
			streamHash.put(st.getUuid(),st.getStreamName());
		}

		classCode = streamHash.get(streamuuid).replaceAll(" ", "_");  
		examCode = systemConfig.getExamcode(); 

		response.setHeader("Content-Disposition","attachment; filename="+classCode+"."+examCode+".xlsx");

		createExcelSheets(studentList,school);
	}



	/**
	 * Returns MS Excel file of the data specified for exporting.
	 * @param school 
	 * @param List<IncomingLog>
	 * Method create excelSheets and sends them
	 ****/    
	public void createExcelSheets(List<Student>studentList,Account school) throws IOException{    	

		XSSFWorkbook xf = new XSSFWorkbook();
		XSSFCreationHelper ch =xf.getCreationHelper();

		XSSFSheet s =xf.createSheet();
		s.setColumnWidth(0, 4000); 
		s.setColumnWidth(1, 1700); 
		s.setColumnWidth(2, 1700); 
		s.setColumnWidth(3, 1700); 
		s.setColumnWidth(4, 1700); 
		s.setColumnWidth(5, 1700); 
		s.setColumnWidth(6, 1700); 
		s.setColumnWidth(7, 1700); 
		s.setColumnWidth(8, 1700); 
		s.setColumnWidth(9, 1700); 


		CellStyle style = xf.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);

		CellStyle style2 = xf.createCellStyle();
		style2.setAlignment(CellStyle.ALIGN_LEFT);

		XSSFFont font = xf.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME); 
		font.setFontHeightInPoints((short)12);
		style.setFont(font); 

		//create the first row
		XSSFRow r1 = s.createRow(0);
		XSSFCell c111 = r1.createCell(0);
		c111.setCellValue(ch.createRichTextString("NAME")); 
		c111.setCellStyle(style2);

		XSSFCell c12 = r1.createCell(1);
		c12.setCellValue(ch.createRichTextString("ADMNO"));
		c12.setCellStyle(style2);

		XSSFCell c13 = r1.createCell(2);
		c13.setCellValue(ch.createRichTextString("ENG"+"/50")); 
		c13.setCellStyle(style2);

		XSSFCell c14 = r1.createCell(3);
		c14.setCellValue(ch.createRichTextString("COMP"+"/40"));
		c14.setCellStyle(style2);

		XSSFCell c15 = r1.createCell(4);
		c15.setCellValue(ch.createRichTextString("KISW"+"/50"));
		c15.setCellStyle(style2);

		XSSFCell c16 = r1.createCell(5);
		c16.setCellValue(ch.createRichTextString("INSH"+"/40"));
		c16.setCellStyle(style2);

		XSSFCell c17 = r1.createCell(6);
		c17.setCellValue(ch.createRichTextString("MATH"+"/50"));
		c17.setCellStyle(style2);

		XSSFCell c18 = r1.createCell(7);
		c18.setCellValue(ch.createRichTextString("SCI"+"/50"));
		c18.setCellStyle(style2);

		XSSFCell c19 = r1.createCell(8);
		c19.setCellValue(ch.createRichTextString("SS"+"/60"));
		c19.setCellStyle(style2);

		XSSFCell c20 = r1.createCell(9);
		c20.setCellValue(ch.createRichTextString("CRE"+"/30"));
		c20.setCellStyle(style2);


		int i=1;

		String formatedFirstname = "";
		String formatedLastname = "";
		//create other rows
		if(studentList != null){
			for(Student stu :studentList){
				final String STATUS_ACTIVE = ExamConstants.STATUS_ACTIVE;
				if(StringUtils.equals(stu.getStatusUuid(), STATUS_ACTIVE)){

					formatedFirstname =  StringUtils.capitalize(stu.getFirstname().toLowerCase());
					formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());

					XSSFRow r = s.createRow(i);
					//row number
					XSSFCell c1 = r.createCell(0);
					c1.setCellValue(formatedFirstname + " " + formatedLastname);

					//get message  
					XSSFCell c2 = r.createCell(1);        	
					c2.setCellValue(ch.createRichTextString(stu.getAdmmissinNo()));
					//languages
					XSSFCell c3 = r.createCell(2);
					c3.setCellValue(" ");        		   	     

					XSSFCell c4 = r.createCell(3);
					c4.setCellValue(" ");

					XSSFCell c5 = r.createCell(4);
					c5.setCellValue(" "); 
					//sciences
					XSSFCell c6 = r.createCell(5);    
					c6.setCellValue(" ");

					XSSFCell c7 = r.createCell(6); 
					c7.setCellValue(" "); 

					XSSFCell c8 = r.createCell(7); 
					c8.setCellValue(" "); 
					//technical
					XSSFCell c9 = r.createCell(8); 
					c9.setCellValue(" "); 

					XSSFCell c10 = r.createCell(9); 
					c10.setCellValue(" "); 

					i++;   	  
				}//end if status
			}//end for
		}//end if
		xf.write(out);
		out.flush();          
		out.close();
	}





	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 * @throws java.io.IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
