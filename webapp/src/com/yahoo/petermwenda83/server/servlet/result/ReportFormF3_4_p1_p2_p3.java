/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.BarWeight;
import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.bean.staff.StaffDetails;
import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.BarWeightDAO;
import com.yahoo.petermwenda83.persistence.exam.DeviationDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.result.sms.SendResultSMS;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;
import com.yahoo.petermwenda83.server.util.magic.MiddleNumberFor3;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 * 
 */
public class ReportFormF3_4_p1_p2_p3 extends HttpServlet{

	private Font normalText = new Font(Font.FontFamily.COURIER, 10,Font.BOLD);
	private Font normalText2 = new Font(Font.FontFamily.COURIER, 14,Font.BOLD);
	private Font normalText3 = new Font(Font.FontFamily.COURIER, 14,Font.BOLD);
	private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
	private Font boldFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	 
	private Document document;
	private PdfWriter writer;
	private Cache schoolaccountCache, statisticsCache;

	private Logger logger;
	ExamConfig examConfig;
	GradingSystem gradingSystem;
	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";
	private String  firstnamee = "";

	private static PerfomanceDAO perfomanceDAO;
	private static SubjectDAO subjectDAO;
	private static ClassTeacherDAO classTeacherDAO;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static TermFeeDAO termFeeDAO;
	private static TeacherSubClassDAO teacherSubClassDAO;
	private static StaffDetailsDAO staffDetailsDAO;
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static MiscellanousDAO miscellanousDAO;
	private static BarWeightDAO barWeightDAO;
	private static PrimaryDAO primaryDAO;
	private static DeviationDAO deviationDAO;
	private StudentBalance studentBal;
	private SendResultSMS sendResultSMS;
	private static SmsSendDAO smsSendDAO;
	private static ParentsDAO parentsDAO;
	
	String classroomuuid = "";String schoolusername = "";String stffID = "";

	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> firstnameHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> admYearMap = new HashMap<String, String>();
	
	HashMap<String, String> roomHash = new HashMap<String, String>();


	double score = 0;
	
	double engscore = 0;String engscorestr = "";
	double kswscore = 0;String kswscorestr = "";
	double matscore = 0;String matscorestr = "";
	double physcore = 0;String physcorestr = "";  
	double bioscore = 0;String bioscorestr = "";
	double chemscore = 0;String chemscorestr = "";
	double bsscore = 0;String bsscorestr = "";
	double comscore = 0;String comscorestr = "";
	double hscscore = 0;String hscscorestr = "";
	double agriscore = 0;String agriscorestr = "";
	double geoscore = 0;String geoscorestr = "";
	double crescore = 0;String crescorestr = "";
	double histscore = 0;String histscorestr = "";
	
	 
	   String p1engscorestr = "";
	   String p1kswscorestr = "";
	   String p1matscorestr = "";
	   String p1physcorestr = "";  
	   String p1bioscorestr = "";
	   String p1chemscorestr = "";
	   String p1bsscorestr = "";
	   String p1comscorestr = "";
	   String p1hscscorestr = "";
	   String p1agriscorestr = "";
	   String p1geoscorestr = "";
	   String p1crescorestr = "";
	   String p1histscorestr = "";
	  
	   String p2engscorestr = "";
	   String p2kswscorestr = "";
	   String p2matscorestr = "";
	   String p2physcorestr = "";  
	   String p2bioscorestr = "";
	   String p2chemscorestr = "";
	   String p2bsscorestr = "";
	   String p2comscorestr = "";
	   String p2hscscorestr = "";
	   String p2agriscorestr = "";
	   String p2geoscorestr = "";
	   String p2crescorestr = "";
	   String p2histscorestr = "";
	   
	   String p3engscorestr = "";
	   String p3kswscorestr = "";
	   String p3matscorestr = "";
	   String p3physcorestr = "";  
	   String p3bioscorestr = "";
	   String p3chemscorestr = "";
	   String p3bsscorestr = "";
	   String p3comscorestr = "";
	   String p3hscscorestr = "";
	   String p3agriscorestr = "";
	   String p3geoscorestr = "";
	   String p3crescorestr = "";
	   String p3histscorestr = "";
	

	String grade = "";
	String studeadmno = "";
	String studename = "";
	String admno = "";
	int finalYear = 0;

	double paper1  = 0;
	double paper2  = 0;
	double paper3  = 0;
	double total  = 0;
	double pmean  = 0;

	//languages
	final String ENG_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446";
	final String KISWA_UUID = "66027e51-b1ad-4b10-8250-63af64d23323";
	//sciences
	final String MATH_UUID = "4f59580d-1a16-4669-9ed5-4b89615d6903";
	final String PHY_UUID = "44f23b3c-e066-4b45-931c-0e8073d3a93a";
	final String BIO_UUID = "de0c86be-9bcb-4d3b-8098-b06687536c1f";
	final String CHEM_UUID = "552c0a24-6038-440f-add5-2dadfb9a23bd";
	//techinicals
	final String BS_UUID = "e1729cc2-524a-4069-b4a4-be5aec8473fe";
	final String COMP_UUID = "F1972BF2-C788-4F41-94FE-FBA1869C92BC";
	final String H_S = "C1F28FF4-1A18-4552-822A-7A4767643643";
	final String AGR_UUID = "b9bbd718-b32f-4466-ab34-42f544ff900e";
	//humanities 
	final String GEO_UUID = "0e5dc1c6-f62f-4a36-a1ec-064173332694";
	final String CRE_UUID = "f098e943-26fd-4dc0-b6a0-2d02477004a4";
	final String HIST_UUID = "c9caf109-c27d-4062-9b9f-ac4268629e27";

	//MATH_UUID,PHY_UUID,BIO_UUID,CHEM_UUID,BS_UUID,COMP_UUID,H_S,AGR_UUID,GEO_UUID,CRE_UUID,HIST_UUID

	int position = 1;
	static int Finalposition = 0;

	String USER= "";
	String path ="";
	Date admdate;
	String admTerm;


	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger = Logger.getLogger(this.getClass());
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
		perfomanceDAO = PerfomanceDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		classTeacherDAO = ClassTeacherDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		teacherSubClassDAO = TeacherSubClassDAO.getInstance();
		staffDetailsDAO = StaffDetailsDAO.getInstance();
		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		miscellanousDAO = MiscellanousDAO.getInstance();
		barWeightDAO = BarWeightDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		deviationDAO = DeviationDAO.getInstance();
		studentBal = new StudentBalance();
		sendResultSMS = new SendResultSMS();
		smsSendDAO = SmsSendDAO.getInstance();
		parentsDAO = ParentsDAO.getInstance();

		
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
		admdate = null;
		admTerm = "";
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
		//ServletContext context = getServletContext();
		response.setContentType("application/pdf");
		
		SchoolAccount school = new SchoolAccount();
		HttpSession session = request.getSession(false);



		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
			//stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
			stffID = StringUtils.trimToEmpty(request.getParameter("staffid"));

		}
		String classID = "";
		classID = StringUtils.trimToEmpty(request.getParameter("classID"));
		 
		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}

		    schoolname = school.getSchoolName().toUpperCase()+"\n";
			PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
							+ ""+school.getTown()+" - Kenya\n" 
							+ "" + school.getMobile()+"\n"
							+ "" + school.getEmail()+"\n" ;
			
			title = "_____________________________________ \n"
					+ " End of Term Report Card "+"\n";


		examConfig = examConfigDAO.getExamConfig(school.getUuid());
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid());

		ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByteacherId(stffID);
		if(classTeacher !=null){
			classroomuuid = classTeacher.getClassRoomUuid();
		}

		SessionStatistics statistics = new SessionStatistics();
		if ((element = statisticsCache.get(schoolusername)) != null) {
			statistics = (SessionStatistics) element.getObjectValue();
		}
		
		List<Perfomance> perfomanceListGeneral = new ArrayList<Perfomance>(); 
		List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
		perfomanceListGeneral = perfomanceDAO.getClassPerfomanceListGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());
		pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());
		

		List<Perfomance> perfomanceList = new ArrayList<Perfomance>(); 
		perfomanceList = perfomanceDAO.getPerfomanceList(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());
		List<Perfomance> pDistinctList = new ArrayList<Perfomance>();
		pDistinctList = perfomanceDAO.getPerfomanceListDistinct(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());


		List<Student> studentList = new ArrayList<Student>(); 
		studentList = studentDAO.getAllStudentList(school.getUuid());
		
		 String admYear = "";
         SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("yyyy");
         
        

		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 
			
			 String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			 String formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
			 String formatedsurname = StringUtils.capitalize(stu.getSurname().toLowerCase());
			
			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedLastname = formatedLastname.substring(0, Math.min(formatedLastname.length(), 10));
			formatedsurname = formatedsurname.substring(0, Math.min(formatedsurname.length(), 10));
			
			finalYear = stu.getFinalYear();
			admdate = stu.getAdmissionDate();
			admTerm = stu.getRegTerm();
			admYear = formatter.format(admdate);
			admYearMap.put(stu.getUuid(), admYear); 
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedLastname + " " + formatedsurname +"\n"); 
			firstnameHash.put(stu.getUuid(), formatedFirstname);
		}

		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());
		}
		
		 String fileName = new StringBuffer(StringUtils.trimToEmpty("ReportCards")) 
	               .append("_")
	               .append(roomHash.get(classroomuuid).replaceAll(" ", "_"))
	               .append(".pdf")
	               .toString();
		   response.setHeader("Content-Disposition", "inline; filename=\""+fileName);
		

		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());


			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			if(perfomanceList!=null || pDistinctList!=null || writer ==null || document ==null){
				if(pDistinctListGeneral!=null || perfomanceListGeneral!=null){
				populatePDFDocument(statistics, school,classroomuuid,classID,perfomanceList,pDistinctList,perfomanceListGeneral,pDistinctListGeneral,path);
				  }
			}else{
				 session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, "No data found !");
				 response.sendRedirect("exam.jsp");  
			}


		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		 return;
	}



	/**
	 * @param statistics
	 * @param school
	 * @param classroomuuid
	 * @param classID 
	 * @param perfomanceList
	 * @param pDistinctList
	 * @param pDistinctListGeneral 
	 * @param perfomanceListGeneral 
	 * @param realPath
	 */
	private void populatePDFDocument(SessionStatistics statistics, SchoolAccount school, String classroomuuid, 
			String classID, List<Perfomance> perfomanceList, List<Perfomance> pDistinctList,List<Perfomance> perfomanceListGeneral, List<Perfomance> pDistinctListGeneral, String realPath) {

		Map<String,Double> kswscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> engscorehashgn = new LinkedHashMap<String,Double>(); 

		Map<String,Double> physcoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> matscorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> bioscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> chemscorehashgn = new LinkedHashMap<String,Double>(); 

		Map<String,Double> bsscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> agriscorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> hscscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> comscoreMapgn = new LinkedHashMap<String,Double>();

		Map<String,Double> geoscoreMapgn = new LinkedHashMap<String,Double>();
		Map<String,Double> crescorehashgn = new LinkedHashMap<String,Double>(); 
		Map<String,Double> histscoreMapgn = new LinkedHashMap<String,Double>();
		
		Map<String,Double> grandscoremapgn = new LinkedHashMap<String,Double>();
		
		Map<String,String> POSMapgn = new LinkedHashMap<String,String>();



		

		
		
		

		String totalz = "";
		try {
			document.open();
			document.add(new Paragraph("."));
			
			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130});
			
			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , normalText3));
			content.add(new Paragraph((PDF_SUBTITLE +"") , normalText));
			content.add(new Paragraph((title) , normalText2));

			PdfPCell contentcell = new PdfPCell(content);
			contentcell.setBorder(Rectangle.NO_BORDER); 
			contentcell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph preface = new Paragraph();
			preface.add(createImage(realPath));

			Image imgLogo = null;
			try {
				imgLogo = Image.getInstance(realPath);
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}

			imgLogo.scalePercent(8); 
			imgLogo.setAlignment(Element.ALIGN_LEFT);

			PdfPCell logo = new PdfPCell();
			logo.addElement(new Chunk(imgLogo,15,-50)); // margin left  ,  margin top
			logo.setBorder(Rectangle.NO_BORDER); 
			logo.setHorizontalAlignment(Element.ALIGN_LEFT);

			prefaceTable.addCell(logo); 
			prefaceTable.addCell(contentcell);
			
			
			


			// addEmptyLine(preface, 1);
			//SimpleDateFormat formatter;
			//formatter = new SimpleDateFormat("dd, MMM yyyy");


			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);
			
			//perfomanceListGeneral,pDistinctListGeneral
		    //int Finalposition = 0;
			
			 double humanityScoregn =0,techinicalScoregn = 0;
			 double scienceScoregn = 0;
			 
			
			
		    double engscoregn = 0;
			double kswscoregn = 0;
			double matscoregn = 0;
			double physcoregn = 0;
			double bioscoregn = 0;
			double chemscoregn = 0;
			double bsscoregn = 0;
			double comscoregn = 0;
			double hscscoregn = 0;
			double agriscoregn = 0;
			double geoscoregn = 0;
			double crescoregn = 0;
			double histscoregn = 0;
			
			double  grandscoregn = 0;
			//double totalgrandscoregn = 0;
			
			double  paper1gn  = 0, paper2gn  = 0, paper3gn = 0,totalgn=0;
			double languageScoregn = 0; 
			
				
		   double numbergn = 0.0;
		 //perfomanceListGeneral,pDistinctListGeneral
			int Finalposition = 0;
			
			int mycountgn =1;
			
			List<Perfomance> listGeneral = new ArrayList<>();
			if(pDistinctListGeneral !=null){
				for(Perfomance pD : pDistinctListGeneral){     
					listGeneral = perfomanceDAO.getPerformanceGeneral(school.getUuid(), classID, pD.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());

					engscoregn = 0;	kswscoregn = 0;
					matscoregn = 0;	physcoregn = 0;
					bioscoregn = 0;	chemscoregn = 0;
					bsscoregn = 0;comscoregn = 0;
					hscscoregn = 0;agriscoregn = 0;
					geoscoregn = 0;crescoregn = 0;
					histscoregn = 0;
					
					for(Perfomance pp : listGeneral){
						//Languages
						//Include all the languages
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ENG_UUID) ){

								paper1gn = pp.getPaperOne(); //out of 60
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 60                   
								totalgn = (paper1gn + paper2gn + paper3gn)/2; 
								engscoregn = totalgn; 
								engscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(engscoregn)))));
								engscorehashgn.put(pD.getStudentUuid(),engscoregn);


							}


							if(StringUtils.equals(pp.getSubjectUuid(), KISWA_UUID)){

								paper1gn = pp.getPaperOne(); //out of 60
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 60
								totalgn = (paper1gn + paper2gn + paper3gn)/2; 
								kswscoregn = totalgn;
								kswscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscoregn)))));
								kswscoreMapgn.put(pD.getStudentUuid(),kswscoregn);

								
							}


							languageScoregn = (engscoregn+kswscoregn); 


						}       
						//Sciences
						//Pick best two if the student take the three
						if(true){
							double subjectBiggn = 0;
							double subjectSmallgn = 0;

							if(StringUtils.equals(pp.getSubjectUuid(), PHY_UUID)){

								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
								physcoregn = totalgn;
								physcoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(physcoregn)))));
								physcoreMapgn.put(pD.getStudentUuid(),physcoregn);

								

							}
							if(StringUtils.equals(pp.getSubjectUuid(), BIO_UUID)){
								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
								bioscoregn = totalgn;
								bioscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscoregn)))));
								bioscoreMapgn.put(pD.getStudentUuid(),bioscore);

								

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CHEM_UUID)){

								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = ((paper1gn + paper2gn)/160)*60 + paper3gn;
								chemscoregn = totalgn;
								chemscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscoregn)))));
								chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);

								
							}
							if(StringUtils.equals(pp.getSubjectUuid(), MATH_UUID)){

								paper1gn = pp.getPaperOne(); //out of 100
								paper2gn = pp.getPaperTwo(); //out of 100
								totalgn = (paper1gn + paper2gn)/2;
								matscoregn = totalgn;
								matscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(matscoregn)))));
								matscorehashgn.put(pD.getStudentUuid(),matscoregn);

							

							}
							MiddleNumberFor3 middle = new MiddleNumberFor3();
							subjectBiggn = Math.max( (Math.max(physcoregn, bioscoregn)), Math.max(Math.max(physcoregn, bioscoregn), chemscoregn));
							subjectSmallgn = middle.ComputeMiddle(physcoregn, bioscoregn, chemscoregn);
							scienceScoregn = (subjectBiggn+subjectSmallgn+matscoregn);

						}
						//Technical  
						//Here we pick one subject, the one he/she has performed best, but this subject can be replaced by a science,
						//if the student takes 3 sciences and he/she performed better in the science than in all  the techinicals . 
						if(true){
							double bestTechinicalgn = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), BS_UUID)){
								paper1gn = pp.getPaperOne(); //out of 100
								paper2gn = pp.getPaperTwo(); //out of 100
								totalgn = (paper1gn + paper2gn)/2;
								bsscoregn = totalgn;
								bsscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscoregn)))));
								bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);

							


							}
							if(StringUtils.equals(pp.getSubjectUuid(), AGR_UUID)){

								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = (paper1gn + paper2gn)/2 + paper3gn;
								agriscoregn = totalgn;
								agriscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscoregn)))));
								agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);

							
							}     
							if(StringUtils.equals(pp.getSubjectUuid(), H_S)){

								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = (paper1gn + paper2gn)/2 + paper3gn;
								hscscoregn = totalgn;
								hscscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscoregn)))));
								hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);

							

							}if(StringUtils.equals(pp.getSubjectUuid(), COMP_UUID)){

								paper1gn = pp.getPaperOne(); //out of 80
								paper2gn = pp.getPaperTwo(); //out of 80
								paper3gn = pp.getPaperThree();//out of 40
								totalgn = (paper1gn + paper2gn)/2 + paper3gn;
								comscoregn = totalgn;
								comscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(comscoregn)))));
								comscoreMapgn.put(pD.getStudentUuid(),comscoregn);

								
							} 
							bestTechinicalgn = Math.max( (Math.max(bsscoregn, agriscoregn)), Math.max(hscscoregn, comscoregn));
							techinicalScoregn = bestTechinicalgn; 

						}     

						//Humanities
						//Here we pick only one subject, the one the student has performed best . 
						if(true){  
							double bestHumanitygn = 0;     
							if(StringUtils.equals(pp.getSubjectUuid(), GEO_UUID)){

								paper1gn = pp.getPaperOne(); //out of 100
								paper2gn = pp.getPaperTwo(); //out of 100
								totalgn = (paper1gn + paper2gn)/2;
								geoscoregn = totalgn;    
								geoscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscoregn)))));
								geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);

								

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CRE_UUID)){
								
								paper1gn = pp.getPaperOne(); //out of 100
								paper2gn = pp.getPaperTwo(); //out of 100
								totalgn = (paper1gn + paper2gn)/2;
								crescoregn = totalgn;
								crescoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(crescoregn)))));
								crescorehashgn.put(pD.getStudentUuid(),crescoregn);

								

							}
							if(StringUtils.equals(pp.getSubjectUuid(), HIST_UUID)){                	

								paper1gn = pp.getPaperOne(); //out of 100
								paper2gn = pp.getPaperTwo(); //out of 100
								totalgn = (paper1gn + paper2gn)/2;
								histscoregn = totalgn;          
								histscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(histscoregn)))));
								histscoreMapgn.put(pD.getStudentUuid(),histscoregn);

							

							}


							bestHumanitygn = Math.max( (Math.max(geoscoregn, crescoregn)), Math.max(Math.max(geoscoregn, crescoregn), histscoregn));
							humanityScoregn = bestHumanitygn; 

						} 


					}

					grandscoregn = languageScoregn+scienceScoregn+humanityScoregn+techinicalScoregn;
					languageScoregn = 0; scienceScoregn = 0; humanityScoregn = 0;techinicalScoregn = 0;  
					grandscoremapgn.put(pD.getStudentUuid(), grandscoregn);                        
					grandscoregn = 0;
					
					Finalposition = mycountgn++;

				}
				
				
			
			@SuppressWarnings("unchecked")
			ArrayList<?> as = new ArrayList(grandscoremapgn.entrySet());
			Collections.sort(as,new Comparator(){
				public int compare(Object o1,Object o2){
					Map.Entry e1 = (Map.Entry)o1;
					Map.Entry e2 = (Map.Entry)o2;
					Double f = (Double)e1.getValue();
					Double s = (Double)e2.getValue();
					return s.compareTo(f);
				}
			});
			
			double meangn = 0;
			int counttwogn = 1;
			int positiongn = 1;
		
			String totalzgn = "";
			for(Object o : as){

				String items = String.valueOf(o);
				String [] item = items.split("=");
				String uuid = item[0];
				
				totalzgn = item[1];
				
				double the_grandscoregn = 0;
				the_grandscoregn = Double.parseDouble(totalzgn);
				meangn = the_grandscoregn/7; 
				
                              //			    TODO
								Deviation dev;
								if(deviationDAO.getDev(uuid, examConfig.getYear())==null){
									dev = new Deviation();
								}else{
									dev = deviationDAO.getDev(uuid, examConfig.getYear());
								}
								
								if(StringUtils.equals(examConfig.getTerm(), "1")){
									dev.setStudentUuid(uuid);
									dev.setYear(examConfig.getYear());
									dev.setDevOne(meangn);
									deviationDAO.putDev(dev,uuid,examConfig.getYear());
									
									
								}else if(StringUtils.equals(examConfig.getTerm(), "2")){
									dev.setStudentUuid(uuid);
									dev.setYear(examConfig.getYear());
									dev.setDevTwo(meangn);
									deviationDAO.putDev(dev,uuid,examConfig.getYear());
									
									
								}else if(StringUtils.equals(examConfig.getTerm(), "3")){
									dev.setStudentUuid(uuid);
									dev.setYear(examConfig.getYear());
									dev.setDevThree(meangn); 
									deviationDAO.putDev(dev,uuid,examConfig.getYear());
									
								}


				
				

				String pos = "";
				if(meangn==numbergn){
					 pos = (" " +(positiongn-counttwogn++)+ " / " +Finalposition);
					 POSMapgn.put(uuid,pos);
				}
				else{
					counttwogn=1;
					pos = (" " +positiongn+ " / " +Finalposition);
					POSMapgn.put(uuid,pos);
				}

				positiongn++;
				numbergn=meangn;
				
				Finalposition = mycountgn++;


			}
			
		}
		//System.out.println("pos= "+pos);

        /** end general ###################################################################
         * ################################################################################################################################# */
        
			
			
			
			
			Map<String,Double> kswscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> engscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> physcoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> matscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> bioscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> chemscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> bsscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> agriscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> hscscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> comscoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> geoscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> crescorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> histscoreMap = new LinkedHashMap<String,Double>();
			
			
			 // paper 1
			Map<String,Double> p1kswscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1engscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> p1physcoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1matscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p1bioscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1chemscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> p1bsscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1agriscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p1hscscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1comscoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> p1geoscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p1crescorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p1histscoreMap = new LinkedHashMap<String,Double>();
			
			//paper 2
			
			Map<String,Double> p2kswscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2engscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> p2physcoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2matscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p2bioscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2chemscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> p2bsscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2agriscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p2hscscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2comscoreMap = new LinkedHashMap<String,Double>();

			Map<String,Double> p2geoscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p2crescorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p2histscoreMap = new LinkedHashMap<String,Double>();
			
			//paper3
			Map<String,Double> p3kswscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p3engscorehash = new LinkedHashMap<String,Double>(); 

			Map<String,Double> p3physcoreMap = new LinkedHashMap<String,Double>();
			//Map<String,Double> p2matscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p3bioscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p3chemscorehash = new LinkedHashMap<String,Double>(); 

			//Map<String,Double> p2bsscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p3agriscorehash = new LinkedHashMap<String,Double>(); 
			Map<String,Double> p3hscscoreMap = new LinkedHashMap<String,Double>();
			Map<String,Double> p3comscoreMap = new LinkedHashMap<String,Double>();


			List<Perfomance> list = new ArrayList<>();
			Map<String,Double> grandscoremap = new LinkedHashMap<String,Double>(); 
			double languageScore = 0;double scienceScore = 0;double humanityScore = 0;
			double techinicalScore = 0;double grandscore = 0;double number = 0.0;
			MiddleNumberFor3 middle = new MiddleNumberFor3();
			if(pDistinctList !=null){
				int mycount =1;
				for(Perfomance s : pDistinctList){                              
					list = perfomanceDAO.getPerformance(school.getUuid(), classroomuuid, s.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());

					engscore = 0;kswscore = 0;matscore = 0;physcore = 0;bioscore = 0;chemscore = 0;
					bsscore = 0;comscore = 0;hscscore = 0;agriscore = 0;geoscore = 0;crescore = 0;
					histscore = 0;
					
					paper1 = 0; paper2 = 0; paper3 = 0;
					
					

					for(Perfomance pp : list){
						//Languages
						//Include all the languages
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ENG_UUID) ){

								paper1 = pp.getPaperOne(); //out of 60
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 60                   
								total = (paper1 + paper2 + paper3)/2; 
								engscore = total; 
								engscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(engscore)))));
								engscorehash.put(s.getStudentUuid(),engscore);
								
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1engscorehash.put(s.getStudentUuid(),paper1);
								p2engscorehash.put(s.getStudentUuid(),paper2);
								p3engscorehash.put(s.getStudentUuid(),paper3);
								


							}


							if(StringUtils.equals(pp.getSubjectUuid(), KISWA_UUID)){

								paper1 = pp.getPaperOne(); //out of 60
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 60
								total = (paper1 + paper2 + paper3)/2; 
								kswscore = total;
								kswscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscore)))));
								kswscoreMap.put(s.getStudentUuid(),kswscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1kswscoreMap.put(s.getStudentUuid(),paper1);
								p2kswscoreMap.put(s.getStudentUuid(),paper2);
								p3kswscoreMap.put(s.getStudentUuid(),paper3);

							}


							languageScore = (engscore+kswscore); 


						}       
						//Sciences
						//Pick best two if the student take the three
						if(true){
							double subjectBig = 0;
							double subjectSmall = 0;

							if(StringUtils.equals(pp.getSubjectUuid(), PHY_UUID)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = ((paper1 + paper2)/160)*60 + paper3;

								physcore = total;
								physcore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(physcore)))));
								physcoreMap.put(s.getStudentUuid(),physcore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1physcoreMap.put(s.getStudentUuid(),paper1);
								p2physcoreMap.put(s.getStudentUuid(),paper2);
								p3physcoreMap.put(s.getStudentUuid(),paper3);

							}
							if(StringUtils.equals(pp.getSubjectUuid(), BIO_UUID)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = ((paper1 + paper2)/160)*60 + paper3;
								bioscore = total;
								bioscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscore)))));
								bioscoreMap.put(s.getStudentUuid(),bioscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1bioscoreMap.put(s.getStudentUuid(),paper1);
								p2bioscoreMap.put(s.getStudentUuid(),paper2);
								p3bioscoreMap.put(s.getStudentUuid(),paper3);

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CHEM_UUID)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = ((paper1 + paper2)/160)*60 + paper3;
								chemscore = total;
								chemscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscore)))));
								chemscorehash.put(s.getStudentUuid(),chemscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1chemscorehash.put(s.getStudentUuid(),paper1);
								p2chemscorehash.put(s.getStudentUuid(),paper2);
								p3chemscorehash.put(s.getStudentUuid(),paper3);

							}
							if(StringUtils.equals(pp.getSubjectUuid(), MATH_UUID)){

								paper1 = pp.getPaperOne(); //out of 100
								paper2 = pp.getPaperTwo(); //out of 100
								total = (paper1 + paper2)/2;
								//System.out.println("paper1 ="+paper1 +",paper2 ="+paper2 +",total="+total);
								matscore = total;
								matscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(matscore)))));
								matscorehash.put(s.getStudentUuid(),matscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								//paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1matscorehash.put(s.getStudentUuid(),paper1);
								p2matscorehash.put(s.getStudentUuid(),paper2);
								//p3matscorehash.put(s.getStudentUuid(),paper3);

							}

							subjectBig = Math.max( (Math.max(physcore, bioscore)), Math.max(Math.max(physcore, bioscore), chemscore));
							subjectSmall = middle.ComputeMiddle(physcore, bioscore, chemscore);
							scienceScore = (subjectBig+subjectSmall+matscore);

						}
						//Technical  
						//Here we pick one subject, the one he/she has performed best, but this subject can be replaced by a science,
						//if the student takes 3 sciences and he/she performed better in the science than in all  the techinicals . 
						if(true){
							double bestTechinical = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), BS_UUID)){

								paper1 = pp.getPaperOne(); //out of 100
								paper2 = pp.getPaperTwo(); //out of 100
								total = (paper1 + paper2)/2;
								bsscore = total;
								bsscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscore)))));
								bsscoreMap.put(s.getStudentUuid(),bsscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								//paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1bsscoreMap.put(s.getStudentUuid(),paper1);
								p2bsscoreMap.put(s.getStudentUuid(),paper2);
								//p3bsscoreMap.put(s.getStudentUuid(),paper3);


							}
							if(StringUtils.equals(pp.getSubjectUuid(), AGR_UUID)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = (paper1 + paper2)/2 + paper3;
								agriscore = total;
								agriscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscore)))));
								agriscorehash.put(s.getStudentUuid(),agriscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1agriscorehash.put(s.getStudentUuid(),paper1);
								p2agriscorehash.put(s.getStudentUuid(),paper2);
								p3agriscorehash.put(s.getStudentUuid(),paper3);

							}     
							if(StringUtils.equals(pp.getSubjectUuid(), H_S)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = (paper1 + paper2)/2 + paper3;
								hscscore = total;
								hscscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscore)))));
								hscscoreMap.put(s.getStudentUuid(),hscscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1hscscoreMap.put(s.getStudentUuid(),paper1);
								p2hscscoreMap.put(s.getStudentUuid(),paper2);
								p3hscscoreMap.put(s.getStudentUuid(),paper3);


							}if(StringUtils.equals(pp.getSubjectUuid(), COMP_UUID)){

								paper1 = pp.getPaperOne(); //out of 80
								paper2 = pp.getPaperTwo(); //out of 80
								paper3 = pp.getPaperThree();//out of 40
								total = (paper1 + paper2)/2 + paper3;
								comscore = total;
								comscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(comscore)))));
								comscoreMap.put(s.getStudentUuid(),comscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1comscoreMap.put(s.getStudentUuid(),paper1);
								p2comscoreMap.put(s.getStudentUuid(),paper2);
								p3comscoreMap.put(s.getStudentUuid(),paper3);

							} 
							bestTechinical = Math.max( (Math.max(bsscore, agriscore)), Math.max(hscscore, comscore));
							techinicalScore = bestTechinical; 

						}     

						//Humanities
						//Here we pick only one subject, the one the student has performed best . 
						if(true){  
							double bestHumanity = 0;     
							if(StringUtils.equals(pp.getSubjectUuid(), GEO_UUID)){

								paper1 = pp.getPaperOne(); //out of 100
								paper2 = pp.getPaperTwo(); //out of 100
								total = (paper1 + paper2)/2;
								geoscore = total;  
								geoscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscore)))));
								geoscoreMap.put(s.getStudentUuid(),geoscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								//paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1geoscoreMap.put(s.getStudentUuid(),paper1);
								p2geoscoreMap.put(s.getStudentUuid(),paper2);
								//p3geoscoreMap.put(s.getStudentUuid(),paper3);

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CRE_UUID)){

								paper1 = pp.getPaperOne(); //out of 100
								paper2 = pp.getPaperTwo(); //out of 100
								total = (paper1 + paper2)/2;
								crescore = total; 
								crescore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(crescore)))));
								crescorehash.put(s.getStudentUuid(),crescore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								//paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1crescorehash.put(s.getStudentUuid(),paper1);
								p2crescorehash.put(s.getStudentUuid(),paper2);
								//p3crescorehash.put(s.getStudentUuid(),paper3);

							}
							if(StringUtils.equals(pp.getSubjectUuid(), HIST_UUID)){                	

								paper1 = pp.getPaperOne(); //out of 100
								paper2 = pp.getPaperTwo(); //out of 100
								total = (paper1 + paper2)/2;
								histscore = total;
								histscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(histscore)))));
								histscoreMap.put(s.getStudentUuid(),histscore);
								
								paper1 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper1)))));
								paper2 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper2)))));
								//paper3 = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(paper3)))));
								p1histscoreMap.put(s.getStudentUuid(),paper1);
								p2histscoreMap.put(s.getStudentUuid(),paper2);
								//p3histscoreMap.put(s.getStudentUuid(),paper3);


							}


							bestHumanity = Math.max( (Math.max(geoscore, crescore)), Math.max(Math.max(geoscore, crescore), histscore));
							humanityScore = bestHumanity; 

						} 


					}

					grandscore = languageScore+scienceScore+humanityScore+techinicalScore;
					languageScore = 0; scienceScore = 0; humanityScore = 0;techinicalScore = 0;  
					grandscoremap.put(s.getStudentUuid(), grandscore);                        
					grandscore = 0;




					Finalposition = mycount++;

				}




				@SuppressWarnings("unchecked")
				ArrayList<?> as = new ArrayList(grandscoremap.entrySet());
				Collections.sort(as,new Comparator(){
					public int compare(Object o1,Object o2){
						Map.Entry e1 = (Map.Entry)o1;
						Map.Entry e2 = (Map.Entry)o2;
						Double f = (Double)e1.getValue();
						Double s = (Double)e2.getValue();
						return s.compareTo(f);
					}
				});



				position = 1;
				int counttwo = 1;

				for(Object o : as){
					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];
					totalz = item[1];
					double mean = 0;
					double the_grandscore = 0;
	                   // the_grandscore =+grandscoreT;
					the_grandscore = Double.parseDouble(totalz);
					mean = the_grandscore/7;  
					
					

					studeadmno = studentAdmNoHash.get(uuid);
					studename = studNameHash.get(uuid);  
					firstnamee = firstnameHash.get(uuid);

					TermFee termFee = termFeeDAO.getFee(school.getUuid(),examConfig.getTerm(),examConfig.getYear());




					if(engscorehash.get(uuid)!=null && engscorehash.get(uuid)!=0){
						engscore = engscorehash.get(uuid);  
						engscorestr =  rf2.format(engscore);

					}else{
						engscorestr = "";
					}
					
					//START ENG
					
					//paper 1
					if(p1engscorehash.get(uuid)!=null && p1engscorehash.get(uuid)!=0){
						p1engscorestr =  rf2.format(p1engscorehash.get(uuid));

					}else{
						p1engscorestr = "";
					}
					//paper 2
					if(p2engscorehash.get(uuid)!=null && p2engscorehash.get(uuid)!=0){
						p2engscorestr =  rf2.format(p2engscorehash.get(uuid));

					}else{
						p2engscorestr = "";
					}
					//ppaer 3
					if(p3engscorehash.get(uuid)!=null && p3engscorehash.get(uuid)!=0){
						p3engscorestr =  rf2.format(p3engscorehash.get(uuid));

					}else{
						p3engscorestr = "";
					}
					//END
					


					if(kswscoreMap.get(uuid)!=null && kswscoreMap.get(uuid)!=0){
						kswscore = kswscoreMap.get(uuid);               
						kswscorestr = rf2.format(kswscore);

					}else{
						kswscorestr = "";
					}
					
					
                    // START KIS
					
					//paper 1
					if(p1kswscoreMap.get(uuid)!=null && p1kswscoreMap.get(uuid)!=0){
						p1kswscorestr =  rf2.format(p1kswscoreMap.get(uuid));

					}else{
						p1kswscorestr = "";
					}
					//paper 2
					if(p2kswscoreMap.get(uuid)!=null && p2kswscoreMap.get(uuid)!=0){
						p2kswscorestr =  rf2.format(p2kswscoreMap.get(uuid));

					}else{
						p2kswscorestr = "";
					}
					//ppaer 3
					if(p3kswscoreMap.get(uuid)!=null && p3kswscoreMap.get(uuid)!=0){
						p3kswscorestr =  rf2.format(p3kswscoreMap.get(uuid));

					}else{
						p3kswscorestr = "";
					}
					//END

					
					if(physcoreMap.get(uuid)!=null && physcoreMap.get(uuid)!=0){
						physcore = physcoreMap.get(uuid);              
						physcorestr = rf2.format(physcore);

					}else{
						physcorestr = "";
					}
					
					
                   // START PHY
					
					//paper 1
					if(p1physcoreMap.get(uuid)!=null && p1physcoreMap.get(uuid)!=0){
						p1physcorestr =  rf2.format(p1physcoreMap.get(uuid));

					}else{
						p1physcorestr = "";
					}
					//paper 2
					if(p2physcoreMap.get(uuid)!=null && p2physcoreMap.get(uuid)!=0){
						p2physcorestr =  rf2.format(p2physcoreMap.get(uuid));

					}else{
						p2physcorestr = "";
					}
					//ppaer 3
					if(p3physcoreMap.get(uuid)!=null && p3physcoreMap.get(uuid)!=0){
						p3physcorestr =  rf2.format(p3physcoreMap.get(uuid));

					}else{
						p3physcorestr = "";
					}
					//END
					
					
					
					
					

					if(bioscoreMap.get(uuid)!=null && bioscoreMap.get(uuid)!=0){
						bioscore = bioscoreMap.get(uuid);              
						bioscorestr = rf2.format(bioscore);

					}else{
						bioscorestr = "";
					}
					
					
                    // START BIO
					
					//paper 1
					if(p1bioscoreMap.get(uuid)!=null && p1bioscoreMap.get(uuid)!=0){
						p1bioscorestr =  rf2.format(p1bioscoreMap.get(uuid));

					}else{
						p1bioscorestr = "";
					}
					//paper 2
					if(p2bioscoreMap.get(uuid)!=null && p2bioscoreMap.get(uuid)!=0){
						p2bioscorestr =  rf2.format(p2bioscoreMap.get(uuid));

					}else{
						p2bioscorestr = "";
					}
					//ppaer 3
					if(p3bioscoreMap.get(uuid)!=null && p3bioscoreMap.get(uuid)!=0){
						p3bioscorestr =  rf2.format(p3bioscoreMap.get(uuid));

					}else{
						p3bioscorestr = "";
					}
					//END
					
					


					if(chemscorehash.get(uuid)!=null && chemscorehash.get(uuid)!=0){
						chemscore = chemscorehash.get(uuid);              
						chemscorestr = rf2.format(chemscore);


					}else{
						chemscorestr = "";
					}
					
					
                   // START CHEM
					
					//paper 1
					if(p1chemscorehash.get(uuid)!=null && p1chemscorehash.get(uuid)!=0){
						p1chemscorestr =  rf2.format(p1chemscorehash.get(uuid));

					}else{
						p1chemscorestr = "";
					}
					//paper 2
					if(p2chemscorehash.get(uuid)!=null && p2chemscorehash.get(uuid)!=0){
						p2chemscorestr =  rf2.format(p2chemscorehash.get(uuid));

					}else{
						p2chemscorestr = "";
					}
					//ppaer 3
					if(p3chemscorehash.get(uuid)!=null && p3chemscorehash.get(uuid)!=0){
						p3chemscorestr =  rf2.format(p3chemscorehash.get(uuid));

					}else{
						p3chemscorestr = "";
					}
					//END



					if(matscorehash.get(uuid)!=null && matscorehash.get(uuid)!=0){
						matscore = matscorehash.get(uuid);              
						matscorestr = rf2.format(matscore);

					}else{
						matscorestr = "";
					}
					
					
                    // START MAT
					
					//paper 1
					if(p1matscorehash.get(uuid)!=null && p1matscorehash.get(uuid)!=0){
						p1matscorestr =  rf2.format(p1matscorehash.get(uuid));

					}else{
						p1matscorestr = "";
					}
					//paper 2
					if(p2matscorehash.get(uuid)!=null && p2matscorehash.get(uuid)!=0){
						p2matscorestr =  rf2.format(p2matscorehash.get(uuid));

					}else{
						p2matscorestr = "";
					}
					

					//END
					
					

					if(histscoreMap.get(uuid)!=null && histscoreMap.get(uuid)!=0){
						histscore = histscoreMap.get(uuid);              
						histscorestr = rf2.format(histscore);

					}else{
						histscorestr = "";
					}
					
					
                  // START HISTO
					
					//paper 1
					if(p1histscoreMap.get(uuid)!=null && p1histscoreMap.get(uuid)!=0){
						p1histscorestr =  rf2.format(p1histscoreMap.get(uuid));

					}else{
						p1histscorestr = "";
					}
					//paper 2
					if(p2histscoreMap.get(uuid)!=null && p2histscoreMap.get(uuid)!=0){
						p2histscorestr =  rf2.format(p2histscoreMap.get(uuid));

					}else{
						p2histscorestr = "";
					}
					
					//END


					if(crescorehash.get(uuid)!=null && crescorehash.get(uuid)!=0){
						crescore = crescorehash.get(uuid);              
						crescorestr = rf2.format(crescore);

					}else{
						crescorestr = "";
					}
					
					
                     //START CRE
					
					//paper 1
					if(p1crescorehash.get(uuid)!=null && p1crescorehash.get(uuid)!=0){
						p1crescorestr =  rf2.format(p1crescorehash.get(uuid));

					}else{
						p1crescorestr = "";
					}
					//paper 2
					if(p2crescorehash.get(uuid)!=null && p2crescorehash.get(uuid)!=0){
						p2crescorestr =  rf2.format(p2crescorehash.get(uuid));

					}else{
						p2crescorestr = "";
					}
					
					//END


					if(geoscoreMap.get(uuid)!=null && geoscoreMap.get(uuid)!=0){
						geoscore = geoscoreMap.get(uuid);               
						geoscorestr = rf2.format(geoscore);

					}else{
						geoscorestr = "";
					}
					
                  // START BS
					
					//paper 1
					if(p1geoscoreMap.get(uuid)!=null && p1geoscoreMap.get(uuid)!=0){
						p1geoscorestr =  rf2.format(p1geoscoreMap.get(uuid));

					}else{
						p1geoscorestr = "";
					}
					//paper 2
					if(p2geoscoreMap.get(uuid)!=null && p2geoscoreMap.get(uuid)!=0){
						p2geoscorestr =  rf2.format(p2geoscoreMap.get(uuid));

					}else{
						p2geoscorestr = "";
					}
					
					//END


					if(bsscoreMap.get(uuid)!=null){
						bsscore = bsscoreMap.get(uuid);             
						bsscorestr = rf2.format(bsscore);

					}else{
						bsscorestr = "";
					}
					
                  // START BS
					
					//paper 1
					if(p1bsscoreMap.get(uuid)!=null && p1bsscoreMap.get(uuid)!=0){
						p1bsscorestr =  rf2.format(p1bsscoreMap.get(uuid));

					}else{
						p1bsscorestr = "";
					}
					//paper 2
					if(p2bsscoreMap.get(uuid)!=null && p2bsscoreMap.get(uuid)!=0){
						p2bsscorestr =  rf2.format(p2bsscoreMap.get(uuid));

					}else{
						p2bsscorestr = "";
					}
					
					//END
					


					if(agriscorehash.get(uuid)!=null && agriscorehash.get(uuid)!=0){
						agriscore = agriscorehash.get(uuid);              
						agriscorestr = rf2.format(agriscore);

					}else{
						agriscorestr = "";
					}
					
					
                   // START AGR
					
					//paper 1
					if(p1agriscorehash.get(uuid)!=null && p1agriscorehash.get(uuid)!=0){
						p1agriscorestr =  rf2.format(p1agriscorehash.get(uuid));

					}else{
						p1agriscorestr = "";
					}
					//paper 2
					if(p2agriscorehash.get(uuid)!=null && p2agriscorehash.get(uuid)!=0){
						p2agriscorestr =  rf2.format(p2agriscorehash.get(uuid));

					}else{
						p2agriscorestr = "";
					}
					//ppaer 3
					if(p3agriscorehash.get(uuid)!=null && p3agriscorehash.get(uuid)!=0){
						p3agriscorestr =  rf2.format(p3agriscorehash.get(uuid));

					}else{
						p3agriscorestr = "";
					}
					//END
					

					if(hscscoreMap.get(uuid)!=null && hscscoreMap.get(uuid)!=0){
						hscscore = hscscoreMap.get(uuid);              
						hscscorestr = rf2.format(hscscore);

					}else{
						hscscorestr = "";
					}

					if(comscoreMap.get(uuid)!=null && comscoreMap.get(uuid)!=0){
						comscore = comscoreMap.get(uuid);               
						comscorestr = rf2.format(comscore);

					}else{
						comscorestr = "";
					}   
					
                    //START COMP
					
					//paper 1
					if(p1comscoreMap.get(uuid)!=null && p1comscoreMap.get(uuid)!=0){
						p1comscorestr =  rf2.format(p1comscoreMap.get(uuid));

					}else{
						p1comscorestr = "";
					}
					//paper 2
					if(p2comscoreMap.get(uuid)!=null && p2comscoreMap.get(uuid)!=0){
						p2comscorestr =  rf2.format(p2comscoreMap.get(uuid));

					}else{
						p2comscorestr = "";
					}
					//ppaer 3
					if(p3comscoreMap.get(uuid)!=null && p3comscoreMap.get(uuid)!=0){
						p3comscorestr =  rf2.format(p3comscoreMap.get(uuid));

					}else{
						p3comscorestr = "";
					}
					//END
					
					
					
					
					
					
					BaseColor baseColor = new BaseColor(255,255,255);//while   32,178,170)
					BaseColor Colormagenta = new BaseColor(255,255,255);//  (176,196,222); magenta
					BaseColor Colorgrey = new BaseColor(255,255,255);//  (128,128,128)gray,grey

					Paragraph emptyline = new Paragraph(("                              "));

					//table here
					PdfPCell CountHeader = new PdfPCell(new Paragraph("*",boldFont));
					CountHeader.setBackgroundColor(baseColor);
					CountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell subjectHeader = new PdfPCell(new Paragraph("SUBJECT",boldFont));
					subjectHeader.setBackgroundColor(baseColor);
					subjectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell paper1Header = new PdfPCell(new Paragraph("P 1",boldFont));
					paper1Header.setBackgroundColor(baseColor);
					paper1Header.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell paper2Header = new PdfPCell(new Paragraph("P 2",boldFont));
					paper2Header.setBackgroundColor(baseColor);
					paper2Header.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell paper3Header = new PdfPCell(new Paragraph("P 3",boldFont)); 
					paper3Header.setBackgroundColor(baseColor);
					paper3Header.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell scoreHeader = new PdfPCell(new Paragraph("TOTAL /100",boldFont));
					scoreHeader.setBackgroundColor(baseColor);
					scoreHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell pointsHeader = new PdfPCell(new Paragraph("PNTS /12",boldFont));
					pointsHeader.setBackgroundColor(baseColor);
					pointsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


					PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRADE",boldFont));
					gradeHeader.setBackgroundColor(baseColor);
					gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell remarkHeader = new PdfPCell(new Paragraph("REMARKS",boldFont));
					remarkHeader.setBackgroundColor(baseColor);
					remarkHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					PdfPCell teacherHeader = new PdfPCell(new Paragraph("Teacher",boldFont));
					teacherHeader.setBackgroundColor(baseColor);
					teacherHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					
					
					
					PdfPTable myTable;

					myTable = new PdfPTable(10); 
					myTable.addCell(CountHeader);
					myTable.addCell(subjectHeader);
					myTable.addCell(paper1Header);
					myTable.addCell(paper2Header);
					myTable.addCell(paper3Header);
					myTable.addCell(scoreHeader);
					myTable.addCell(pointsHeader);
					myTable.addCell(gradeHeader);
					myTable.addCell(remarkHeader);
					myTable.addCell(teacherHeader);
					myTable.setWidthPercentage(100); 
					myTable.setWidths(new int[]{15,60,20,20,20,25,20,25,60,20}); 
					myTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					String maxscore = "100";

					String aplain = Integer.toString(gradingSystem.getGradeAplain()-1);
					String aminus = Integer.toString(gradingSystem.getGradeAminus()-1);

					String bplus = Integer.toString(gradingSystem.getGradeBplus()-1);
					String bplain = Integer.toString(gradingSystem.getGradeBplain()-1);
					String bminus = Integer.toString(gradingSystem.getGradeBminus()-1);

					String cplus = Integer.toString(gradingSystem.getGradeCplus()-1);
					String cplain = Integer.toString(gradingSystem.getGradeCplain()-1);
					String cminus = Integer.toString(gradingSystem.getGradeCminus()-1);

					String dplus = Integer.toString(gradingSystem.getGradeDplus()-1);
					String dplain = Integer.toString(gradingSystem.getGradeDplain()-1);
					String dminus = Integer.toString((gradingSystem.getGradeDminus()-1));

					String gradeE = Integer.toString(gradingSystem.getGradeE());


					PdfPCell gradeA = new PdfPCell(new Paragraph(maxscore+"-"+(gradingSystem.getGradeAplain()),boldFont));
					gradeA.setBackgroundColor(baseColor);
					gradeA.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeAminus = new PdfPCell(new Paragraph(aplain+"-"+(gradingSystem.getGradeAminus()),boldFont));
					gradeAminus.setBackgroundColor(baseColor);
					gradeAminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBplus = new PdfPCell(new Paragraph(aminus+"-"+(gradingSystem.getGradeBplus()),boldFont));
					gradeBplus.setBackgroundColor(baseColor);
					gradeBplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBplain = new PdfPCell(new Paragraph(bplus+"-"+(gradingSystem.getGradeBplain()),boldFont));
					gradeBplain.setBackgroundColor(baseColor);
					gradeBplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeBminus = new PdfPCell(new Paragraph(bplain+"-"+(gradingSystem.getGradeBminus()),boldFont));
					gradeBminus.setBackgroundColor(baseColor);
					gradeBminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCplus = new PdfPCell(new Paragraph(bminus+"-"+(gradingSystem.getGradeCplus()),boldFont));
					gradeCplus.setBackgroundColor(baseColor);
					gradeCplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCplain = new PdfPCell(new Paragraph(cplus+"-"+(gradingSystem.getGradeCplain()),boldFont));
					gradeCplain.setBackgroundColor(baseColor);
					gradeCplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeCminus = new PdfPCell(new Paragraph(cplain+"-"+(gradingSystem.getGradeCminus()),boldFont));
					gradeCminus.setBackgroundColor(baseColor);
					gradeCminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDplus = new PdfPCell(new Paragraph(cminus+"-"+(gradingSystem.getGradeDplus()),boldFont));
					gradeDplus.setBackgroundColor(baseColor);
					gradeDplus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDplain = new PdfPCell(new Paragraph(dplus+"-"+(gradingSystem.getGradeDplain()),boldFont));
					gradeDplain.setBackgroundColor(baseColor);
					gradeDplain.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeDminus = new PdfPCell(new Paragraph(dplain+"-"+(gradingSystem.getGradeDminus()),boldFont));
					gradeDminus.setBackgroundColor(baseColor);
					gradeDminus.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell gradeEE = new PdfPCell(new Paragraph(dminus+"-"+gradeE,boldFont));
					gradeEE.setBackgroundColor(baseColor);
					gradeEE.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPTable gradeTable = new PdfPTable(12);  
					gradeTable.addCell(gradeA);
					gradeTable.addCell(gradeAminus);
					gradeTable.addCell(gradeBplus);
					gradeTable.addCell(gradeBplain);
					gradeTable.addCell(gradeBminus);
					gradeTable.addCell(gradeCplus);
					gradeTable.addCell(gradeCplain);
					gradeTable.addCell(gradeCminus);
					gradeTable.addCell(gradeDplus);
					gradeTable.addCell(gradeDplain);
					gradeTable.addCell(gradeDminus);
					gradeTable.addCell(gradeEE);
					gradeTable.setWidthPercentage(100); 
					gradeTable.setWidths(new int[]{20,20,20,20,20,25,20,20,20,20,20,20});   
					gradeTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					gradeTable.addCell(new Paragraph("A",smallBold));
					gradeTable.addCell(new Paragraph("A-",smallBold));
					gradeTable.addCell(new Paragraph("B+",smallBold));
					gradeTable.addCell(new Paragraph("B",smallBold));
					gradeTable.addCell(new Paragraph("B-",smallBold));
					gradeTable.addCell(new Paragraph("C+",smallBold));
					gradeTable.addCell(new Paragraph("C",smallBold));
					gradeTable.addCell(new Paragraph("C-",smallBold));
					gradeTable.addCell(new Paragraph("D+",smallBold));
					gradeTable.addCell(new Paragraph("D",smallBold));
					gradeTable.addCell(new Paragraph("D-",smallBold));
					gradeTable.addCell(new Paragraph("E",smallBold));

					PdfPTable containerTable = new PdfPTable(2);  
					containerTable.setWidthPercentage(100); 
					containerTable.setWidths(new int[]{100,100}); 
					containerTable.setHorizontalAlignment(Element.ALIGN_LEFT);


					PdfPTable titleTable = new PdfPTable(1);  
					titleTable.setWidthPercentage(25); 
					titleTable.setWidths(new int[]{100}); 
					titleTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPTable HeaderTable = new PdfPTable(1);  
					HeaderTable.setWidthPercentage(50); 
					HeaderTable.setWidths(new int[]{100});   
					HeaderTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					

					PdfPCell contheader = new PdfPCell(new Paragraph(("TERM " +examConfig.getTerm() + ": YEAR " + examConfig.getYear() +"\n\n" +("CLASS : " + roomHash.get(classroomuuid) +"\n")) +"",boldFont));
					contheader.setBackgroundColor(Colormagenta);
					contheader.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell bodyheader = new PdfPCell(new Paragraph(("ADMISSION NUMBER : " + studentAdmNoHash.get(uuid) +"\n\n" +("STUDENT NAME : " + studNameHash.get(uuid) +"\n")),boldFont));
					bodyheader.setBackgroundColor(Colormagenta);
					bodyheader.setHorizontalAlignment(Element.ALIGN_LEFT);


					containerTable.addCell(bodyheader);
					containerTable.addCell(contheader);
					
					
					//studentDAO parentsDAO TODO
					if(StringUtils.equals(examConfig.getSendSMS(),"ON")){
						sendResultSMS.sendToParents(smsSendDAO,studentDAO,parentsDAO,uuid,school.getUuid(),engscorestr,kswscorestr,matscorestr,
								physcorestr,bioscorestr,chemscorestr,bsscorestr,comscorestr,
								hscscorestr,agriscorestr,geoscorestr,crescorestr,histscorestr,mean);
					}

					List<Subject> subList = subjectDAO.getAllSubjects();

					int count = 1;
					for(Subject sub : subList){



						myTable.addCell(new Paragraph(" "+count,smallBold));

						if(StringUtils.equals(sub.getUuid(), ENG_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1engscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2engscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3engscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+engscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(engscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(engscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(engscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							engscore = 0;

						}if(StringUtils.equals(sub.getUuid(), KISWA_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1kswscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2kswscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3kswscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+kswscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(kswscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(kswscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(kswscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							kswscore = 0;

						}if(StringUtils.equals(sub.getUuid(), MATH_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1matscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2matscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3matscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+matscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(matscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(matscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(matscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							matscore = 0;

						}if(StringUtils.equals(sub.getUuid(), PHY_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1physcorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2physcorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3physcorestr,smallBold));
							myTable.addCell(new Paragraph(" "+physcorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(physcore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(physcore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(physcore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							physcore = 0;

						}if(StringUtils.equals(sub.getUuid(), BIO_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1bioscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2bioscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3bioscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+bioscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(bioscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(bioscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(bioscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
						
							bioscore = 0;

						}if(StringUtils.equals(sub.getUuid(), CHEM_UUID)){
						
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1chemscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2chemscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3chemscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+chemscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(chemscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(chemscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(chemscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							chemscore = 0;

						}if(StringUtils.equals(sub.getUuid(), BS_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1bsscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2bsscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3bsscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+bsscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(bsscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(bsscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(bsscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							bsscore = 0;

						}if(StringUtils.equals(sub.getUuid(), COMP_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1comscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2comscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3comscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+comscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(comscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(comscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(comscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							comscore = 0;

						}if(StringUtils.equals(sub.getUuid(), H_S)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1hscscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2hscscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3hscscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+hscscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(hscscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(hscscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(hscscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							hscscore = 0;

						}if(StringUtils.equals(sub.getUuid(), AGR_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1agriscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2agriscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3agriscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+agriscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(agriscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(agriscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(agriscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
						
							agriscore = 0;

						}if(StringUtils.equals(sub.getUuid(), GEO_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1geoscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2geoscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3geoscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+geoscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(geoscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(geoscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(geoscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							geoscore = 0;

						}if(StringUtils.equals(sub.getUuid(), CRE_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1crescorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2crescorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3crescorestr,smallBold));
							myTable.addCell(new Paragraph(" "+crescorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(crescore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(crescore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(crescore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							crescore = 0;

						}if(StringUtils.equals(sub.getUuid(), HIST_UUID)){
							
							myTable.addCell(new Paragraph(" "+sub.getSubjectName(),smallBold));
							myTable.addCell(new Paragraph(" "+p1histscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p2histscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+p3histscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+histscorestr,smallBold));
							myTable.addCell(new Paragraph(" "+pointsFinder(histscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeGrade(histscore),smallBold));
							myTable.addCell(new Paragraph(" "+computeRemarks(histscore),smallBold));
							myTable.addCell(new Paragraph(" "+findSubTecher(sub.getUuid(),classroomuuid),smallBold)); 
							
							histscore = 0;
						}


						count++;
					}
                   
					

					//KCSE
					double kcse = 0;
					double newkcse = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcse = Integer.parseInt(primary.getKcpemark()); 
						newkcse = (kcse/500)*12;
					}
					


					
					Paragraph myposition;

					if(mean==number){
						myposition = new Paragraph((" Stream PSTN " +(position-counttwo++)+ " / " +Finalposition)   +    "     Class PSTN " +POSMapgn.get(uuid) +    "      K.C.P.E = " + (int)kcse +" ",smallBold);
					}
					else{
						counttwo=1;
						myposition = new Paragraph((" Stream PSTN " +position+ " / " +Finalposition)   +    "     Class PSTN " +POSMapgn.get(uuid) +  "      K.C.P.E = " + (int)kcse + " ",smallBold);
					}

					PdfPCell positionheader = new PdfPCell(myposition);
					positionheader.setBackgroundColor(Colormagenta);
					positionheader.setHorizontalAlignment(Element.ALIGN_LEFT);  
					
					String str = " ";

					PdfPCell meanheader = new PdfPCell(new Paragraph(("TOTAL MARKS: " + the_grandscore + " "+ str +"MEAN SCORE " + df.format(mean) + " GRADE " +computeGrade(mean)) +"\n\n",smallBold));
					meanheader.setBackgroundColor(Colormagenta);
					meanheader.setHorizontalAlignment(Element.ALIGN_RIGHT);

					PdfPTable bottomTable = new PdfPTable(2);  
					bottomTable.setWidthPercentage(100); 
					bottomTable.setWidths(new int[]{100,100}); 
					bottomTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					bottomTable.addCell(positionheader);
					bottomTable.addCell(meanheader); 

					PdfPTable feeTable = new PdfPTable(2);  
					feeTable.setWidthPercentage(100); 
					feeTable.setWidths(new int[]{100,100}); 
					feeTable.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					//fee balance
					//admdate  admTerm
					
					String currentTermstr = examConfig.getTerm();
					String correctTermstr = "";
					int correctTermint = 0;
					int currentTermint = Integer.parseInt(currentTermstr); 
					int nextTermint = currentTermint+1;
					if(nextTermint >3){
						correctTermint = 1;
						correctTermstr = Integer.toString(correctTermint);
					}else{
						correctTermint = currentTermint+1;
						correctTermstr = Integer.toString(correctTermint);
					}

					TermFee termFeenex = termFeeDAO.getFee(school.getUuid(),correctTermstr,examConfig.getYear()); 
					double nexttermfee = termFeenex.getTermAmount(); 

					Locale locale = new Locale("en","KE"); 
					NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
					double balance = 0;
					String feebalance = "";
	                balance = studentBal.findBalance(termFeeDAO,examConfigDAO,studentFeeDAO,studentOtherMoniesDAO,admdate,admTerm,uuid,school.getUuid(),finalYear); 
	                feebalance = nf.format(balance);
					
	                
					Miscellanous closingDate = new Miscellanous();
					Miscellanous openingDate = new Miscellanous();
					Miscellanous comments = new Miscellanous();
					String cdate = "";
					String odate = "";
					String comment = "";
					
					closingDate = miscellanousDAO.getKey(school.getUuid(),"CLOSING_DATE");
					cdate = closingDate.getValue();
					
					openingDate = miscellanousDAO.getKey(school.getUuid(),"OPENING_DATE");
					odate = openingDate.getValue();
					
					comments = miscellanousDAO.getKey(school.getUuid(),"HEAD_TEACHER_REMARKS");
					comment = comments.getValue();
					


					PdfPCell feeCell = new PdfPCell(new Paragraph("Closing Fee Balance  " + feebalance +" \n\nNext Term Fee = XXXX ." ,smallBold));
					feeCell.setBackgroundColor(Colorgrey);
					feeCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell DateCell = new PdfPCell(new Paragraph(("Closing date : " +cdate+" \n\nNext Term Opening date :" +odate)+"\n",smallBold));
					DateCell.setBackgroundColor(Colorgrey);
					DateCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					feeTable.addCell(feeCell);
					feeTable.addCell(DateCell);
					
                    //save details
					
					String StartYear = "";
					StartYear = admYearMap.get(uuid);
					int Year2Int = 0;
					int Year3Int = 0;
					int Year4Int = 0;
					
					String Year2Str = "";
					String Year3Str = "";
					String Year4Str = "";
					
					Year2Int = Integer.parseInt(StartYear)+1;
					Year3Int = Year2Int + 1;
					Year4Int = Year3Int + 1;
					
					Year2Str = Integer.toString(Year2Int);
					Year3Str = Integer.toString(Year3Int);
					Year4Str = Integer.toString(Year4Int);
					
					
					GraphWeightGenerator(mean,uuid,school.getUuid()); 
					// Create a simple Bar chart start
					BarWeight barWeight1 = new BarWeight();
					BarWeight barWeight2 = new BarWeight();
					BarWeight barWeight3 = new BarWeight();
					BarWeight barWeight4 = new BarWeight();
                    if(GraphWeightGenerator(mean,uuid,school.getUuid())){
                    	if(barWeightDAO.getBarWeight(school.getUuid(), uuid, StartYear) !=null){
                    		barWeight1 = barWeightDAO.getBarWeight(school.getUuid(), uuid, StartYear); 
                    	}
                    	
                    	if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year2Str) !=null){
                    		barWeight2 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year2Str); 
                    	}
                    	
                    	if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year3Str) !=null){
                    		barWeight3 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year3Str); 
                    	}
                    	if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Year4Str) !=null){
                    		barWeight4 = barWeightDAO.getBarWeight(school.getUuid(), uuid, Year4Str); 
                    	}
                    	
                    	
					}
					
					PdfPCell BAFheader = new PdfPCell();
					
					DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					double ChartWeight = 0;
					double ChartWeight2 = 0;
					double ChartWeight3 = 0;
					
					
				
					dataset.setValue(newkcse, "Pnts", " K.C.P.E");
					
					//YEAR 1
				    ChartWeight =  barWeight1.getWeightOne(); 
				    ChartWeight2 =  barWeight1.getWeightTwo(); 	
					ChartWeight3 =  barWeight1.getWeightThree(); 
					
					
					dataset.setValue(ChartWeight, "Pnts",  StartYear+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", StartYear+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", StartYear+"-T 3");
					
					//YEAR 2
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;
					
					ChartWeight =  barWeight2.getWeightOne(); 
				    ChartWeight2 =  barWeight2.getWeightTwo(); 	
					ChartWeight3 =  barWeight2.getWeightThree(); 
					
					dataset.setValue(ChartWeight, "Pnts",  Year2Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year2Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year2Str+"-T 3");
					
					//YEAR 2
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;
					
					ChartWeight =  barWeight3.getWeightOne(); 
				    ChartWeight2 =  barWeight3.getWeightTwo(); 	
					ChartWeight3 =  barWeight3.getWeightThree(); 
					
					dataset.setValue(ChartWeight, "Pnts",  Year3Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year3Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year3Str+"-T 3");
					
					//YEAR 4
					ChartWeight = 0;
					ChartWeight2 = 0;
					ChartWeight3 = 0;
					
					ChartWeight =  barWeight4.getWeightOne(); 
				    ChartWeight2 =  barWeight4.getWeightTwo(); 	
					ChartWeight3 =  barWeight4.getWeightThree(); 
					
					dataset.setValue(ChartWeight, "Pnts",  Year4Str+"-T 1");
					dataset.setValue(ChartWeight2, "Pnts", Year4Str+"-T 2");
					dataset.setValue(ChartWeight3, "Pnts", Year4Str+"-T 3");
					
					//CONTROL
					dataset.setValue(12, "Control ", "Control ");
					
					//END 
					
					JFreeChart chart = ChartFactory.createBarChart("Yearly Performance Analysis", // chart title
																	"Term", // domain axis label (Y axis)
																	"Weight", //  range axis label (X axis)
																	dataset, // data
																	PlotOrientation.VERTICAL, // orientation
																	false, // include legend
																	true, // tooltips?
																	false);// URLs?
					
					ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
					
					try {
						
						ChartUtilities.writeChartAsPNG(byte_out, chart, 1200, 270);
						byte [] data = byte_out.toByteArray();
						byte_out.close();
						Image chartImage = Image.getInstance(data);
						chartImage.scaleToFit(1000,300); 
						//chartPragraph.add(chartImage); 
						BAFheader.addElement(new Chunk(chartImage,15,-90));// margin left  ,  margin top
						BAFheader.setBorder(Rectangle.NO_BORDER); 
						BAFheader.setHorizontalAlignment(Element.ALIGN_LEFT);
						BAFheader.setHorizontalAlignment(Element.ALIGN_LEFT);

						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				
					
					
					//chart end
					
					//QR code start
					Paragraph QRparagraph;
					QRparagraph = new Paragraph(); 
					BarcodeQRCode my_code = new BarcodeQRCode("AdmNo: " + studentAdmNoHash.get(uuid) + 
							"\nName: " + studNameHash.get(uuid) +"Mean: " + df.format(mean) + "\nGrade: "
							+ computeGrade(mean) + "\nFee Bal: "
							+ feebalance,1,1, null);
					
					Image qr_image = my_code.getImage();
					qr_image.scaleToFit(100, 100); 
					QRparagraph.add(qr_image);
					//QR code end
					
					//chart table
					PdfPTable chartTable = new PdfPTable(1);  
					chartTable.setWidthPercentage(100); 
					chartTable.setWidths(new int[]{100}); 
					chartTable.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					//chart table
					PdfPTable QrTable = new PdfPTable(1);  
					QrTable.setWidthPercentage(100); 
					QrTable.setWidths(new int[]{100}); 
					QrTable.setHorizontalAlignment(Element.ALIGN_LEFT);

					
					PdfPCell QRheader = new PdfPCell();
					QRheader.addElement(new Chunk(qr_image,15,-50)); // margin left  ,  margin top
					QRheader.setBackgroundColor(Colormagenta);
					QRheader.setBorder(Rectangle.NO_BORDER); 
					QRheader.setHorizontalAlignment(Element.ALIGN_LEFT);

					QrTable.addCell(QRheader);
					chartTable.addCell(BAFheader);
					
					//chart table end



					//class teacher comment table start
					PdfPTable classTeacherCommentTable = new PdfPTable(2);  
					classTeacherCommentTable.setWidthPercentage(100); 
					classTeacherCommentTable.setWidths(new int[]{100,100}); 
					classTeacherCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					String CTcommentLabel = "CLASS TEACHER'S COMMENT \n\n";
					Paragraph CTcommentLb = new Paragraph(CTcommentLabel,boldFont);
					
					BarWeight barWeight = new BarWeight();
					if(barWeightDAO.getBarWeight(school.getUuid(), uuid, examConfig.getYear()) !=null){
                		barWeight = barWeightDAO.getBarWeight(school.getUuid(), uuid, examConfig.getYear()); 
                	}
					
					// 
					//if current term is 1, get deviation for term 3 ,last year
					double lastTermMean = 0;
					Deviation means = new Deviation();
					String lastyr = "";
					
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						//get current year
						String thisyear = "";
						int lastyear = 0;
						
						if(examConfig !=null){
							thisyear = examConfig.getYear();
							lastyear = Integer.parseInt(thisyear) - 1;
						}
						
						lastyr = Integer.toString(lastyear); 
						
					}else{
						lastyr = examConfig.getYear();
					}
					
					if(deviationDAO.getDev(uuid, lastyr)!=null){
						 means =  deviationDAO.getDev(uuid, lastyr);
					}
					
					//get last term mean 
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						lastTermMean = means.getDevThree();
					}else if(StringUtils.equals(examConfig.getTerm(), "2")){
						lastTermMean = means.getDevOne();
					}else if(StringUtils.equals(examConfig.getTerm(), "3")){
						lastTermMean = means.getDevTwo();
					}
					//now we haave our last term deviation in the variable  'lastTermMean'
					
					// we get this term mean
					double thstermMean = 0;
					Deviation thisterMmeanObj = new Deviation();
					if(deviationDAO.getDev(uuid, examConfig.getYear()) !=null){
					  thisterMmeanObj = deviationDAO.getDev(uuid, examConfig.getYear());
					}
					if(StringUtils.equals(examConfig.getTerm(), "1")){
						thstermMean = thisterMmeanObj.getDevOne();
					}else if(StringUtils.equals(examConfig.getTerm(), "2")){
						thstermMean = thisterMmeanObj.getDevTwo();
					}else if(StringUtils.equals(examConfig.getTerm(), "3")){
						thstermMean = thisterMmeanObj.getDevThree();
					}
					
					//now we haave our last term deviation in the variable  'thstermMean'
					
					double deviation_from_lastTerm = 0;
					deviation_from_lastTerm = deviationFinder(thstermMean,lastTermMean);
					
					// now we have our deviation, we generate comment
					String devComment = "";
					devComment = deviationComment(deviation_from_lastTerm);
					//end , we are done!
					
					
					
					
					
					PdfPCell CTcommentCell = new PdfPCell(new Paragraph("Hi " + firstnamee +", "+ classteacherRemarks(school,uuid,barWeight) + devComment +" \n",boldFont2));
					CTcommentCell.setBackgroundColor(Colormagenta);
					CTcommentCell.setColspan(2); 
					CTcommentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell CTsignature = new PdfPCell(new Paragraph("Class teacher's Signature: _______________("+ classteachername(classroomuuid) +")\n\n",smallBold));
					CTsignature.setBackgroundColor(Colormagenta);
					CTsignature.setHorizontalAlignment(Element.ALIGN_LEFT); 

					PdfPCell CTsignaturedate2 = new PdfPCell(new Paragraph("Date : _____________________    \n\n",smallBold));
					CTsignaturedate2.setBackgroundColor(Colormagenta);
					CTsignaturedate2.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					classTeacherCommentTable.addCell(CTcommentCell);
					classTeacherCommentTable.addCell(CTsignature);
					classTeacherCommentTable.addCell(CTsignaturedate2);
					//class teacher comment table end

                  
					//principal comment table start
					PdfPTable principalCommentTable = new PdfPTable(2);  
					principalCommentTable.setWidthPercentage(100); 
					principalCommentTable.setWidths(new int[]{100,100}); 
					principalCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					String commentLabel = "PRINCIPAL'S COMMENT \n\n";
					Paragraph commentLb = new Paragraph(commentLabel,boldFont);
					
					PdfPCell commentCell = new PdfPCell(new Paragraph("Thank you " + firstnamee +" "+ comment +" "+PrincipalRemarks(mean)+"\n",boldFont2));
					commentCell.setBackgroundColor(Colormagenta);
					commentCell.setColspan(2); 
					commentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell Cell1 = new PdfPCell(new Paragraph("Principal's Signature: _______________   " + "\n\n",smallBold));
					Cell1.setBackgroundColor(Colormagenta);
					Cell1.setHorizontalAlignment(Element.ALIGN_LEFT); 

					PdfPCell Cell2 = new PdfPCell(new Paragraph("            Rubber Stamp \n\n",smallBold));
					Cell2.setBackgroundColor(Colormagenta);
					Cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					principalCommentTable.addCell(commentCell);
					principalCommentTable.addCell(Cell1);
					principalCommentTable.addCell(Cell2);
					//principal comment table end

					document.add(prefaceTable);
					document.add(containerTable);      	  
					document.add(emptyline);
					document.add(myTable); 
					document.add(emptyline);
					document.add(gradeTable);  
					document.add(emptyline);
					document.add(bottomTable); 
					
                    document.add(chartTable);
                    document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					document.add(emptyline);
					
                  //document.add(feeTable);
					
 				   //Class teacher start
 					document.add(CTcommentLb);
 					//document.add(emptyline); 
 					document.add(classTeacherCommentTable); 
 					
 					// principal start
 					document.add(commentLb);
 					//document.add(emptyline); 
 					document.add(principalCommentTable); 
 					
 					//document.add(emptyline);
 					document.add(feeTable);
 					
 					document.add(QrTable);
 					
					position++;
					number=mean;

					document.newPage();


					


				}


			}

			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}
	
	

	/**
	 * @param classroomuuid
	 * @return
	 */
	private String classteachername(String classroomuuid) {
		String classTeacherName = "";
		//classTeacherDAO
		String teacherId = "";
		if(classTeacherDAO.getClassTeacherByclassId(classroomuuid) !=null){
		    ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByclassId(classroomuuid);
		    teacherId = classTeacher.getTeacherUuid();
		    
		    if(staffDetailsDAO.getStaffDetail(teacherId) !=null){
				StaffDetails StaffDetail = staffDetailsDAO.getStaffDetail(teacherId); 
				classTeacherName = StaffDetail.getFirstName();
			}
		    
		  }
		return classTeacherName.substring(0, Math.min(classTeacherName.length(), 5)).toLowerCase();
	}


	/**
	 * @param school
	 * @param uuid
	 * @param barWeight
	 * @return
	 */
	private String classteacherRemarks(SchoolAccount school, String uuid, BarWeight barWeight) {
		String classTeacherComent = " ";
		double T1Weight = 0;
		double T2Weight = 0;
		double T3Weight = 0;
		if(barWeight !=null){
			T1Weight = barWeight.getWeightOne();
			T2Weight = barWeight.getWeightTwo();
			T3Weight = barWeight.getWeightThree();
		}
		
		if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "1")){
			
			BarWeight barweightLastYR = new BarWeight();
			int lastyear = (Integer.parseInt(examConfig.getYear())-1);
			
			if(barWeightDAO.getBarWeight(school.getUuid(), uuid, Integer.toString(lastyear)) !=null){
				barweightLastYR = barWeightDAO.getBarWeight(school.getUuid(), uuid, Integer.toString(lastyear)); 
        	}
			
			double T3lastYR = 0;
			T3lastYR = barweightLastYR.getWeightThree();
			
			if(T1Weight > T3lastYR && T3lastYR !=0){
				//you have improved
				classTeacherComent = "comparing your last term and this term performance, we noted some improvement, please keep it up.";
				
			}else if( T1Weight > T3lastYR && T3lastYR == 0){
				//stagnant
				classTeacherComent = "it seems you did'nt do last term's exams, or the system did'nt capture your results";
				
			}else if(T1Weight == T3lastYR){
				//you have dropped 
				classTeacherComent = "we have noted no improvement compared to last term, this is worrying, work harder.";
				
			}else if(T1Weight < T3lastYR){
				//you have dropped 
				classTeacherComent = "it seems you have dropped compared to last term ! Put more effort please.";
			}
			
			
		}else if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "2")){
			
			
			if(T2Weight > T1Weight && T1Weight !=0){
				//you have improved
				classTeacherComent = "comparing your last term and this term performance, we noted some improvement, please keep it up.";
			}else if(T2Weight > T1Weight && T1Weight == 0){
				//stagnant
				classTeacherComent = "it seems you did'nt do last term's exams, or the system did'nt capture your results";
				
			}else if(T2Weight == T1Weight){
				//you have dropped 
				classTeacherComent = "we have noted no improvement compared to last term, this is worrying, work harder.";
				
			}else if(T2Weight < T1Weight){
				//you have dropped 
				classTeacherComent = "it seems you have dropped compared to last term ! put more effort please.";
			}
			
		}else if(StringUtils.equalsIgnoreCase(examConfig.getTerm(), "3")){
			
			if(T3Weight > T2Weight && T2Weight !=0){
				//you have improved
				classTeacherComent = "comparing your last term and this term performance, we noted some improvement, please keep it up.";
				
			}else if(T3Weight > T2Weight && T2Weight == 0){
				//stagnant
				classTeacherComent = "it seems you did'nt do last term's exams, or the system did'nt capture your results";
				
			}else if(T3Weight == T2Weight){
				//you have dropped 
				classTeacherComent = "we have noted no improvement compared to last term, this is worrying, work harder.";
				
			}else if(T3Weight < T2Weight){
				//you have dropped 
				classTeacherComent = "it seems you have dropped compared to last term !, put more effort please.";
			}
			

			
		}
		
		return classTeacherComent;
	}
	
	/** Find deviation from last term
	 * pass thisTermMean following lastTermMean
	 * @param lastTermMean
	 * @param thisTermMean
	 * @return the deviation 
	 */
	
	private double deviationFinder(double thisTermMean, double lastTermMean){
         double deviation = 0;
		
		if(lastTermMean == 0){
			deviation = 0;
		}else{
			deviation = thisTermMean - lastTermMean;
		}
		return deviation;
	}
	
	/** put comment as per deviation
	 * @param deviation
	 * @return teacher comment
	 */
	private String deviationComment(double mean){
		String comment = "";
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.HALF_UP);
		double dev = 0;
				dev = mean;
				if(dev<0){
					double positiveDev = Math.abs(dev);
					String newDev = "-"+df.format(positiveDev);
					comment = " Your deviation is " + newDev;
					//System.out.println(comment);
					
				}else if(dev>0){
					//positive , student working hard
					comment = " Your deviation is " + df.format(dev);
					
				}else if(dev == 0){
					//stagnant
					comment = " !";
					
				}
				
		return comment;
	}


	
	

	/**
	 * 
	 * @param mean
	 * @param studentuuid
	 * @param schooluuid
	 * @return
	 */
	 
	private boolean GraphWeightGenerator(double mean,String studentuuid,String schooluuid) {
		boolean saved = false;
		
		BarWeight barWeight;
		if(barWeightDAO.getBarWeight(schooluuid, studentuuid, examConfig.getYear())==null){
			 barWeight = new BarWeight();
		}else{
			 barWeight = barWeightDAO.getBarWeight(schooluuid, studentuuid, examConfig.getYear());
		}
	
		
		double weight = 0;
		weight =  ((mean/100)*12);
		
		if(StringUtils.equals(examConfig.getTerm(), "1")){
			barWeight.setWeightOne(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;
			
		}else if (StringUtils.equals(examConfig.getTerm(), "2")){
			barWeight.setWeightTwo(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;
			
		}else if (StringUtils.equals(examConfig.getTerm(), "3")){
			barWeight.setWeightThree(weight);
			barWeight.setSchoolAccountUuid(schooluuid);
			barWeight.setStudentUuid(studentuuid);
			barWeight.setTerm(examConfig.getTerm());
			barWeight.setYear(examConfig.getYear());
			barWeightDAO.put(barWeight,schooluuid,studentuuid,examConfig.getYear());
			saved = true;
		}
		
		return saved;
		
	}

	/**
	 * @param subjectid
	 * @param classroomid
	 * @return
	 */
	private String findSubTecher(String subjectid, String classroomid) {
		String teachername = "";
		String teacheruuid = "";
		if(teacherSubClassDAO.getSubject(subjectid, classroomid) !=null){
			TeacherSubClass teachersub = teacherSubClassDAO.getSubject(subjectid, classroomid);
			teacheruuid = teachersub.getTeacherUuid();
			if(staffDetailsDAO.getStaffDetail(teacheruuid) !=null){
				StaffDetails StaffDetail = staffDetailsDAO.getStaffDetail(teacheruuid); 
				teachername = StaffDetail.getFirstName();
			}	
		}
		if(StringUtils.isBlank(teachername)){
			teachername = "";
		}
		
		return teachername.substring(0, Math.min(teachername.length(), 5)).toLowerCase();
	}

	/**
	 * @param score
	 * @return
	 */
	private String PrincipalRemarks(double score) {
		String remarks = "";
		double mean = score;

		if(mean >= gradingSystem.getGradeAplain()){
			remarks = "Execellent work,let sky be your steping stone.";
		}else if(mean >= gradingSystem.getGradeAminus()){
			remarks = "Good work, sky is not the limit.";
		}else if(mean >= gradingSystem.getGradeBplus()){
			remarks = "Good work, a little more effort please.";
		}else if(mean >= gradingSystem.getGradeBplain()){
			remarks = "Good work but, this is not your best.";
		}else if(mean >= gradingSystem.getGradeBminus()){
			remarks = "An average student, you have the potential.";
		}else if(mean >= gradingSystem.getGradeCplus()){
			remarks = "Below average, this is not your best.";
		}else if(mean >= gradingSystem.getGradeCplain()){
			remarks = "Below average, you can do beter than this.";
		}else if(mean >= gradingSystem.getGradeCminus()){
			remarks = "Below average, you can do beter.";
		}else if(mean >= gradingSystem.getGradeDplus()){
			remarks = "Below average, put more effort.";
		}else if(mean >= gradingSystem.getGradeDplain()){
			remarks = "Far below average, please you deserve better.";
		}else if(mean >= gradingSystem.getGradeDminus()){
			remarks = "Far much below average but you deserve beter than this.";
		}else{
			remarks = "This is extremly poor but still you can do beter.";
		}

		if(mean ==0){
			remarks = " ";
		}

		return remarks;
	}

	private String computeRemarks(double score) {
		String remarks = "";
		double mean = score;

		if(mean >= gradingSystem.getGradeAplain()){
			remarks = "Execellent, keep it up.";
		}else if(mean >= gradingSystem.getGradeAminus()){
			remarks = "Good work, aim higher.";
		}else if(mean >= gradingSystem.getGradeBplus()){
			remarks = "Nice job, aim higher.";
		}else if(mean >= gradingSystem.getGradeBplain()){
			remarks = "Well done, aim higher.";
		}else if(mean >= gradingSystem.getGradeBminus()){
			remarks = "Well done, you deserve better.";
		}else if(mean >= gradingSystem.getGradeCplus()){
			remarks = "Fair, you can do better.";
		}else if(mean >= gradingSystem.getGradeCplain()){
			remarks = "Fair, put more effort.";
		}else if(mean >= gradingSystem.getGradeCminus()){
			remarks = "Fair, you can improve.";
		}else if(mean >= gradingSystem.getGradeDplus()){
			remarks = "Fair, pull up your socks.";
		}else if(mean >= gradingSystem.getGradeDplain()){
			remarks = "You can do better than this.";
		}else if(mean >= gradingSystem.getGradeDminus()){
			remarks = "You can do better than this.";
		}else{
			remarks = "You can do better than this.";
		}

		if(mean ==0){
			remarks = " ";
		}

		return remarks;
	}

	/**
	 * @param engscore2
	 * @return
	 */
	private String computeGrade(double score) {
		double mean = score;
		if(mean >= gradingSystem.getGradeAplain()){
			grade = "A";
		}else if(mean >= gradingSystem.getGradeAminus()){
			grade = "A-";
		}else if(mean >= gradingSystem.getGradeBplus()){
			grade = "B+";
		}else if(mean >= gradingSystem.getGradeBplain()){
			grade = "B";
		}else if(mean >= gradingSystem.getGradeBminus()){
			grade = "B-";
		}else if(mean >= gradingSystem.getGradeCplus()){
			grade = "C+";
		}else if(mean >= gradingSystem.getGradeCplain()){
			grade = "C";
		}else if(mean >= gradingSystem.getGradeCminus()){
			grade = "C-";
		}else if(mean >= gradingSystem.getGradeDplus()){
			grade = "D+";
		}else if(mean >= gradingSystem.getGradeDplain()){
			grade = "D";
		}else if(mean >= gradingSystem.getGradeDminus()){
			grade = "D-";
		}else{
			grade = "E";
		}

		if(mean ==0){
			grade = " ";
		}

		return grade;
	}
	
	
	/**
	 * @param score
	 * @return
	 */
	public String pointsFinder(double score){
		String points = "";
		// points 1=E, 2=D-, 3=D, 4=D+, 5=C-, 6=C, 7=c+
		//8=B-, 9=B, 10=B+,11=A-,12=A
		if(score >= gradingSystem.getGradeAplain()){
			points = "12";
		}else if(score >= gradingSystem.getGradeAminus()){
			points = "11";
		}else if(score >= gradingSystem.getGradeBplus()){
			points = "10";
		}else if(score >= gradingSystem.getGradeBplain()){
			points = "9";
		}else if(score >= gradingSystem.getGradeBminus()){
			points = "8";
		}else if(score >= gradingSystem.getGradeCplus()){
			points = "7";
		}else if(score >= gradingSystem.getGradeCplain()){
			points = "6";
		}else if(score >= gradingSystem.getGradeCminus()){
			points = "5";
		}else if(score >= gradingSystem.getGradeDplus()){
			points = "4";
		}else if(score >= gradingSystem.getGradeDplain()){
			points = "3";
		}else if(score >= gradingSystem.getGradeDminus()){
			points = "2";
		}else if(score >= gradingSystem.getGradeE()){
			points = "1";
		}

		if(score ==0){
			points = " ";
		}
		
		return points;
	}




	/**
	 * @param realPath
	 * @return
	 */
	private Element createImage(String realPath) {
		Image imgLogo = null;

		try {
			imgLogo = Image.getInstance(realPath);
			imgLogo.scaleToFit(200, 200);
			imgLogo.setAlignment(Element.ALIGN_CENTER);

		} catch (BadElementException e) {
			logger.error("BadElementException Exception while creating an image");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (MalformedURLException e) {
			logger.error("MalformedURLException for the path");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (IOException e) {
			logger.error("IOException while creating an image");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return imgLogo;
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
	private static final long serialVersionUID = 3513371438433721109L;


}
