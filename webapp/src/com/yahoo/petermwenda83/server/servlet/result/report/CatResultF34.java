/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result.report;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.GradingSystem;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.servlet.result.PdfUtil;
import com.yahoo.petermwenda83.server.session.SessionConstants;
import com.yahoo.petermwenda83.server.session.SessionStatistics;
import com.yahoo.petermwenda83.server.util.magic.MiddleNumberFor3;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */
public class CatResultF34 extends HttpServlet{

	private Font normalText = new Font(Font.FontFamily.COURIER, 9,Font.BOLD);
	private Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	private Font normalText2 = new Font(Font.FontFamily.COURIER, 9,Font.BOLD);
	private Font normalText3 = new Font(Font.FontFamily.COURIER, 14,Font.BOLD);
	private Font boldFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.ITALIC);

	private Document document;
	private PdfWriter writer;
	private Cache schoolaccountCache, statisticsCache;

	private Logger logger;
	ExamConfig examConfig;
	GradingSystem gradingSystem;

	private String PDF_SUBTITLE ="";
	private String schoolname = "";
	private String title = "";

	private static PerfomanceDAO perfomanceDAO;
	private static StudentDAO studentDAO;
	private static RoomDAO roomDAO;
	private static ExamConfigDAO examConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static PrimaryDAO primaryDAO;
	private static StudentSubjectDAO studentSubjectDAO;


	HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	HashMap<String, String> studNameHash = new HashMap<String, String>();
	HashMap<String, String> roomHash = new HashMap<String, String>();



	double mean = 0;double score = 0;
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
	
	int engCount,kisCount,matCount = 0;
	int phyCount,bioCount,chmCount = 0;
	int bsCount,agrCount,hscCount,cmpCount = 0;
	int hstCount,creCount,geoCount = 0;
	int Mod = 20;



	String grade = "";String studeadmno = "";String studename = "";String admno = "";
	double paper1  = 0;double paper2  = 0;double paper3  = 0;
	double cat1 = 0;  double cat2  = 0;double endterm  = 0;
	double catTotals  = 0;double catmean  = 0;double examcattotal  = 0;
	double total  = 0;double pmean  = 0;
	//Languages
	final String ENG_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446";
	final String KISWA_UUID = "66027e51-b1ad-4b10-8250-63af64d23323";
	//Sciences
	final String MATH_UUID = "4f59580d-1a16-4669-9ed5-4b89615d6903";
	final String PHY_UUID = "44f23b3c-e066-4b45-931c-0e8073d3a93a";
	final String BIO_UUID = "de0c86be-9bcb-4d3b-8098-b06687536c1f";
	final String CHEM_UUID = "552c0a24-6038-440f-add5-2dadfb9a23bd";
	//Technical
	final String BS_UUID = "e1729cc2-524a-4069-b4a4-be5aec8473fe";
	final String COMP_UUID = "F1972BF2-C788-4F41-94FE-FBA1869C92BC";
	final String H_S = "C1F28FF4-1A18-4552-822A-7A4767643643";
	final String AGR_UUID = "b9bbd718-b32f-4466-ab34-42f544ff900e";
	//Humanities 
	final String GEO_UUID = "0e5dc1c6-f62f-4a36-a1ec-064173332694";
	final String CRE_UUID = "f098e943-26fd-4dc0-b6a0-2d02477004a4";
	final String HIST_UUID = "c9caf109-c27d-4062-9b9f-ac4268629e27";

	String USER= "";
	String path ="";
	String examType = "";


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
		studentDAO = StudentDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();

		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");
		String classroomuuid = "";
		String schoolusername = "";

		SchoolAccount school = new SchoolAccount();
		HttpSession session = request.getSession(false); 
		schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		String classID = "";

		classroomuuid = StringUtils.trimToEmpty(request.getParameter("classroomuuid"));
		examType = StringUtils.trimToEmpty(request.getParameter("examType"));



		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}

		ClassRoom clssrom = roomDAO.getroom(school.getUuid(), classroomuuid);

		if(StringUtils.contains(clssrom.getRoomName(), "FORM 3")){ 
			classID = "A4BFC2BD-262F-4207-99C8-057D6ADF80C7";

		}else if(StringUtils.contains(clssrom.getRoomName(), "FORM 4")){ 
			classID = "14E56350-08DA-45CC-97D9-C225AF74A7AD";

		}


		List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
		classroomList = roomDAO.getAllRooms(school.getUuid()); 
		for(ClassRoom c : classroomList){
			roomHash.put(c.getUuid() , c.getRoomName());

		}


		examConfig = examConfigDAO.getExamConfig(school.getUuid());
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid());

		SessionStatistics statistics = new SessionStatistics();
		if ((element = statisticsCache.get(schoolusername)) != null) {
			statistics = (SessionStatistics) element.getObjectValue();
		}

		List<Perfomance> perfomanceListGeneral = new ArrayList<Perfomance>(); 
		List<Perfomance> pDistinctListGeneral = new ArrayList<Perfomance>();
		perfomanceListGeneral = perfomanceDAO.getClassPerfomanceListGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());
		pDistinctListGeneral = perfomanceDAO.getPerfomanceListDistinctGeneral(school.getUuid(), classID,examConfig.getTerm(),examConfig.getYear());

		List<Perfomance> perfomanceList = new ArrayList<Perfomance>(); 
		List<Perfomance> pDistinctList = new ArrayList<Perfomance>();
		perfomanceList = perfomanceDAO.getPerfomanceList(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());
		pDistinctList = perfomanceDAO.getPerfomanceListDistinct(school.getUuid(), classroomuuid,examConfig.getTerm(),examConfig.getYear());


		List<Student> studentList = new ArrayList<Student>(); 
		studentList = studentDAO.getAllStudentList(school.getUuid());

		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmno()); 
			
			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedSurname = StringUtils.capitalize(stu.getLastname().toLowerCase());
			
			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedSurname = formatedSurname.substring(0, Math.min(formatedSurname.length(), 10));
			
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedSurname );
		}


		String fileName = new StringBuffer(StringUtils.trimToEmpty(examType+"_PerformanceList")) 
				.append("_")
				.append(roomHash.get(classroomuuid).replaceAll(" ", "_"))
				.append(".pdf")
				.toString();

		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getPostalAddress()+"\n" 
				+ ""+school.getTown()+" - Kenya\n" 
				+ "" + school.getMobile()+"\n"
				+ "" + school.getEmail()+"\n" ;

		title = "_____________________________________ \n"

                   + " Term:"+examConfig.getTerm()+",Year:"+examConfig.getYear() + " " + examType + " Performance List For: "+roomHash.get(classroomuuid)+"\n";

		document = new Document(PageSize.A4.rotate(), 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());

			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);

			populatePDFDocument(statistics, school,classroomuuid,classID,perfomanceList,pDistinctList,perfomanceListGeneral,pDistinctListGeneral,path);



		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return;
	}



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

			BaseColor baseColor = new BaseColor(255,255,255);//while

			Paragraph emptyline = new Paragraph(("                              "));
			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 
			// OURTABLES
			PdfPTable barchartTable = new PdfPTable(1);  
			PdfPTable SubjectTable = new PdfPTable(12); 

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

			document.add(prefaceTable);

			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);

			DecimalFormat df2 = new DecimalFormat("0.00"); 
			df2.setRoundingMode(RoundingMode.UP); 

			DecimalFormat halfUP = new DecimalFormat("0.00"); 
			halfUP.setRoundingMode(RoundingMode.HALF_UP);

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

			double  cat1gn  = 0, cat2gn  = 0;
			double  grandscoregn = 0;
			double languageScoregn = 0; 


			double numbergn = 0.0;

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
					cat1gn  = 0; cat2gn  = 0;

					for(Perfomance pp : listGeneral){
						//Languages
						//Include all the languages
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ENG_UUID) ){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;
									engscorehashgn.put(pD.getStudentUuid(),cat1gn);
									engscoregn = cat1gn;
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									engscorehashgn.put(pD.getStudentUuid(),cat2gn);
									engscoregn = cat2gn;

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), KISWA_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;									
									kswscoreMapgn.put(pD.getStudentUuid(),cat1gn);
									kswscoregn = cat1gn;
								}
								else {

									cat2gn = (pp.getCatTwo()/30)*100;									
									kswscoreMapgn.put(pD.getStudentUuid(),cat2gn);
									kswscoregn = cat2gn;

								}

							}

							engscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(engscoregn)))));
							kswscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscoregn)))));
							languageScoregn = (engscoregn+kswscoregn); 


						}   


						//Sciences
						//Pick best two if the student take the three
						if(true){
							double subjectBiggn = 0;
							double subjectSmallgn = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), PHY_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									physcoregn = cat1gn;
									physcoreMapgn.put(pD.getStudentUuid(),physcoregn);
								}
								else {

									cat2gn = (pp.getCatTwo()/30)*100;
									physcoregn = cat2gn;
									physcoreMapgn.put(pD.getStudentUuid(),physcoregn);

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), BIO_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;
									bioscoregn = cat1gn;
									bioscoreMapgn.put(pD.getStudentUuid(),bioscoregn);
								}
								else{
									cat2gn = (pp.getCatTwo()/30)*100;
									bioscoregn = cat2gn;
									bioscoreMapgn.put(pD.getStudentUuid(),bioscoregn);

								}
							}
							if(StringUtils.equals(pp.getSubjectUuid(), CHEM_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									chemscoregn = cat1gn;
									chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									chemscoregn = cat2gn;
									chemscorehashgn.put(pD.getStudentUuid(),chemscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), MATH_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									matscoregn = cat1gn;
									matscorehashgn.put(pD.getStudentUuid(),matscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									matscoregn = cat2gn;
									matscorehashgn.put(pD.getStudentUuid(),matscoregn);

								}

							}

							physcoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(physcoregn)))));
							bioscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscoregn)))));
							chemscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscoregn)))));
							matscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(matscoregn)))));
							MiddleNumberFor3 middle = new MiddleNumberFor3();
							subjectBiggn = Math.max( (Math.max(physcoregn, bioscoregn)), Math.max(Math.max(physcoregn, bioscoregn), chemscoregn));
							subjectSmallgn = middle.ComputeMiddle(physcoregn, bioscoregn, chemscoregn);
							scienceScoregn = (subjectBiggn+subjectSmallgn+matscoregn);

						}
						//Technical
						//Here we pick one subject, the one he/she has performed best, but this subject can be replaced by a science, if the student takes 3 sciences and he/she performed better in the science than in all  the techinicals . 
						if(true){
							double bestTechinicalgn = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), BS_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;
									bsscoregn = cat1gn;
									bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									bsscoregn = cat2gn;
									bsscoreMapgn.put(pD.getStudentUuid(),bsscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), AGR_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									agriscoregn = cat1gn;
									agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									agriscoregn = cat2gn;
									agriscorehashgn.put(pD.getStudentUuid(),agriscoregn);

								}

							}     
							if(StringUtils.equals(pp.getSubjectUuid(), H_S)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									hscscoregn = cat1gn;
									hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									hscscoregn = cat2gn;
									hscscoreMapgn.put(pD.getStudentUuid(),hscscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), COMP_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									comscoregn = cat1gn;
									comscoreMapgn.put(pD.getStudentUuid(),comscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									comscoregn = cat2gn;
									comscoreMapgn.put(pD.getStudentUuid(),comscoregn);

								}

							} 
							bsscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscoregn)))));
							agriscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscoregn)))));
							hscscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscoregn)))));
							comscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(comscoregn)))));
							bestTechinicalgn = Math.max( (Math.max(bsscoregn, agriscoregn)), Math.max(hscscoregn, comscoregn));
							techinicalScoregn = bestTechinicalgn; 

						}    

						//Humanities
						//Here we pick only one subject, the one the student has performed best . 
						if(true){  
							double bestHumanitygn = 0;     
							if(StringUtils.equals(pp.getSubjectUuid(), GEO_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	 
									geoscoregn = cat1gn;
									geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									geoscoregn = cat2gn;
									geoscoreMapgn.put(pD.getStudentUuid(),geoscoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CRE_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	
									crescoregn = cat1gn;
									crescorehashgn.put(pD.getStudentUuid(),crescoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									crescoregn = cat2gn;
									crescorehashgn.put(pD.getStudentUuid(),crescoregn);

								}

							}
							if(StringUtils.equals(pp.getSubjectUuid(), HIST_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1gn = (pp.getCatOne()/30)*100;	 
									histscoregn = cat1gn;
									histscoreMapgn.put(pD.getStudentUuid(),histscoregn);
								}
								else {
									cat2gn = (pp.getCatTwo()/30)*100;
									histscoregn = cat2gn;
									histscoreMapgn.put(pD.getStudentUuid(),histscoregn);
								}
							}

							geoscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscoregn)))));
							crescoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(crescoregn)))));
							histscoregn = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(histscoregn)))));
							bestHumanitygn = Math.max( (Math.max(geoscoregn, crescoregn)), Math.max(Math.max(geoscoregn, crescoregn), histscoregn));
							humanityScoregn = bestHumanitygn; 

						} 
					}


					grandscoregn = languageScoregn+scienceScoregn+humanityScoregn+techinicalScoregn;
					//System.out.println("student="+studNameHash.get(pD.getStudentUuid())+",languageScore="+languageScoregn+",scienceScore="+scienceScoregn+",humanityScore="+humanityScoregn+",techinicalScore="+techinicalScoregn+",grandscore="+grandscoregn);	

					languageScoregn = 0; scienceScoregn = 0; humanityScoregn = 0;techinicalScoregn = 0;  
					grandscoremapgn.put(pD.getStudentUuid(), grandscoregn); 					         
					grandscoregn = 0;

					//Finalposition = mycountgn++;
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

				int countgn = 1;
				int counttwogn = 1;
				String totalzgn = "";
				for(Object o : as){

					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];

					totalzgn = item[1];

					//double the_grandscoregn = 0;
					//the_grandscoregn = Double.parseDouble(totalzgn);
					//meangn = the_grandscoregn/7; 


					String pos = "";

					if(Double.parseDouble(totalzgn)==numbergn){
						pos = (countgn-counttwogn++) +" ";
						POSMapgn.put(uuid,pos);
					}
					else{
						counttwogn=1;
						pos = " "+countgn;
						POSMapgn.put(uuid,pos);
					}


					countgn++;
					//studentcountgn++;
					numbergn=Double.parseDouble(totalzgn);




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

			//table here

			PdfPCell countHeader = new PdfPCell(new Paragraph("No",boldFont));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			PdfPCell admNoHeader = new PdfPCell(new Paragraph("AdNo",boldFont));
			admNoHeader.setBackgroundColor(baseColor);

			PdfPCell nameHeader = new PdfPCell(new Paragraph("NAME",boldFont));
			nameHeader.setBackgroundColor(baseColor);
			nameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kcpeHeader = new PdfPCell(new Paragraph("KCPE",boldFont));
			kcpeHeader.setBackgroundColor(baseColor);
			kcpeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell engHeader = new PdfPCell(new Paragraph("ENG",boldFont));
			engHeader.setBackgroundColor(baseColor);
			engHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kisHeader = new PdfPCell(new Paragraph("KIS",boldFont));
			kisHeader.setBackgroundColor(baseColor);
			kisHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell matHeader = new PdfPCell(new Paragraph("MAT",boldFont));
			matHeader.setBackgroundColor(baseColor);
			matHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell phyHeader = new PdfPCell(new Paragraph("PHY",boldFont));
			phyHeader.setBackgroundColor(baseColor);
			phyHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell cheHeader = new PdfPCell(new Paragraph("CHE",boldFont));
			cheHeader.setBackgroundColor(baseColor);
			cheHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell bioHeader = new PdfPCell(new Paragraph("BIO",boldFont));
			bioHeader.setBackgroundColor(baseColor);
			bioHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hisHeader = new PdfPCell(new Paragraph("HIS",boldFont));
			hisHeader.setBackgroundColor(baseColor);
			hisHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell creHeader = new PdfPCell(new Paragraph("CRE",boldFont));
			creHeader.setBackgroundColor(baseColor);
			creHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell geoHeader = new PdfPCell(new Paragraph("GEO",boldFont));
			geoHeader.setBackgroundColor(baseColor);
			geoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell bsHeader = new PdfPCell(new Paragraph("B/S",boldFont));
			bsHeader.setBackgroundColor(baseColor);
			bsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell agrHeader = new PdfPCell(new Paragraph("AGR",boldFont));
			agrHeader.setBackgroundColor(baseColor);
			agrHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hscHeader = new PdfPCell(new Paragraph("HSC",boldFont));
			hscHeader.setBackgroundColor(baseColor);
			hscHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell comHeader = new PdfPCell(new Paragraph("COM",boldFont));
			comHeader.setBackgroundColor(baseColor);
			comHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell totalsHeader = new PdfPCell(new Paragraph("TOTAL",boldFont));
			totalsHeader.setBackgroundColor(baseColor);
			totalsHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanHeader = new PdfPCell(new Paragraph("MN",boldFont));
			meanHeader.setBackgroundColor(baseColor);
			meanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRD",boldFont));
			gradeHeader.setBackgroundColor(baseColor);
			gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell SPHeader = new PdfPCell(new Paragraph("Stm Ps",boldFont));
			SPHeader.setBackgroundColor(baseColor);
			SPHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell CPHeader = new PdfPCell(new Paragraph("Cls Ps",boldFont));
			CPHeader.setBackgroundColor(baseColor);
			CPHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


			PdfPTable myTable = new PdfPTable(22); 
			myTable.addCell(countHeader);
			myTable.addCell(admNoHeader);
			myTable.addCell(nameHeader);
			myTable.addCell(kcpeHeader);
			myTable.addCell(engHeader);
			myTable.addCell(kisHeader);
			myTable.addCell(matHeader);
			myTable.addCell(phyHeader);
			myTable.addCell(cheHeader);
			myTable.addCell(bioHeader);
			myTable.addCell(hisHeader);
			myTable.addCell(creHeader);
			myTable.addCell(geoHeader);
			myTable.addCell(bsHeader);
			myTable.addCell(agrHeader);
			myTable.addCell(hscHeader);
			myTable.addCell(comHeader);
			myTable.addCell(totalsHeader);
			myTable.addCell(meanHeader);
			myTable.addCell(gradeHeader);
			myTable.addCell(SPHeader);
			myTable.addCell(CPHeader);
			myTable.setWidthPercentage(100); 
			myTable.setWidths(new int[]{15,21,46,20,15,15,16,15,15,15,15,15,15,15,15,15,17,25,22,20,15,16});   
			myTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			double totalclassmark = 0;
			double classmean = 0;



			int gradeCountA = 0;
			int gradeCountAm = 0;
			int gradeCountBp = 0;
			int gradeCountB = 0;
			int gradeCountBm = 0;
			int gradeCountCP = 0;
			int gradeCountC = 0;
			int gradeCountCm = 0;
			int gradeCountDp = 0;
			int gradeCountD = 0;
			int gradeCountDm = 0;
			int gradeCountE = 0;

			String studeadmno = "";
			String studename = "";

			Map<String,Double> grandscoremap = new LinkedHashMap<String,Double>(); 
			double languageScore = 0;double scienceScore = 0;double humanityScore = 0;
			double techinicalScore = 0;double grandscore = 0;double number = 0.0;
			MiddleNumberFor3 middle = new MiddleNumberFor3();
			//System.out.println("pDistinctList = " + pDistinctList+"\n");
			if(pDistinctList !=null){
				for(Perfomance s : pDistinctList){     
					listGeneral = perfomanceDAO.getPerformance(school.getUuid(), classroomuuid, s.getStudentUuid(),examConfig.getTerm(),examConfig.getYear());

					engscore = 0;	kswscore = 0;
					matscore = 0;	physcore = 0;
					bioscore = 0;	chemscore = 0;
					bsscore = 0;comscore = 0;
					hscscore = 0;agriscore = 0;
					geoscore = 0;crescore = 0;
					histscore = 0;
					cat1  = 0; cat2  = 0;
					//System.out.println("listGeneral = " + listGeneral+"\n");
					for(Perfomance pp : listGeneral){
						
						List<StudentSubject>  subjectlist = new ArrayList<>();
						subjectlist = studentSubjectDAO.getstudentSubListBySubID(pp.getSubjectUuid());
						//Languages
						//Include all the languages
						if(true){
							if(StringUtils.equals(pp.getSubjectUuid(), ENG_UUID) ){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;
									engscore = cat1;
									engscorehash.put(s.getStudentUuid(),engscore);

								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									engscore = cat2;
									engscorehash.put(s.getStudentUuid(),engscore);


								}
								// Start student count for eng
								// TODO
								int eng = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										eng++;
										engCount += eng;
										
									}
								}
								
								// end student-subject counter
							}
							if(StringUtils.equals(pp.getSubjectUuid(), KISWA_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									kswscore = cat1;
									kswscoreMap.put(s.getStudentUuid(),kswscore);

								}
								else {

									cat2 = (pp.getCatTwo()/30)*100;	
									kswscore = cat2;
									kswscoreMap.put(s.getStudentUuid(),kswscore);


								}
								// Start student count for kis
								
								int kis = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										kis++;
										kisCount += kis;
										
									}
								}
								
								// end student-subject counter

							}

							engscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(engscore)))));
							kswscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscore)))));
							languageScore = (engscore+kswscore); 
							//System.out.println("engscoregn= " + engscore +",kswscoregn = " + kswscore);

						}       
						//Sciences
						//Pick best two if the student take the three
						if(true){
							double subjectBig = 0;
							double subjectSmall = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), PHY_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									physcore = cat1;
									physcoreMap.put(s.getStudentUuid(),physcore);
								}
								else {

									cat2 = (pp.getCatTwo()/30)*100;
									physcore = cat2;
									physcoreMap.put(s.getStudentUuid(),physcore);

								}
								// Start student count for phy
								 
								int phy = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										phy++;
										phyCount += phy;
										
									}
								}
								
								// end student-subject counter
							}
							if(StringUtils.equals(pp.getSubjectUuid(), BIO_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;
									bioscore = cat1;
									bioscoreMap.put(s.getStudentUuid(),bioscore);
								}
								else{
									cat2 = (pp.getCatTwo()/30)*100;
									bioscore = cat2;
									bioscoreMap.put(s.getStudentUuid(),bioscore);

								}
								// Start student count for bio
								
								int bio = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										bio++;
										bioCount += bio;
										
									}
								}
								
								// end student-subject counter
							}
							if(StringUtils.equals(pp.getSubjectUuid(), CHEM_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									chemscore = cat1;
									chemscorehash.put(s.getStudentUuid(),chemscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									chemscore = cat2;
									chemscorehash.put(s.getStudentUuid(),chemscore);

								}
								// Start student count for chem
								
								int chm = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										chm++;
										chmCount += chm;
										
									}
								}
								
								// end student-subject counter

							}
							if(StringUtils.equals(pp.getSubjectUuid(), MATH_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									matscore = cat1;
									matscorehash.put(s.getStudentUuid(),matscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									matscore = cat2;
									matscorehash.put(s.getStudentUuid(),matscore);

								}
								// Start student count for mat
								
								int mat = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										mat++;
										matCount += mat;
										
									}
								}
								
								// end student-subject counter

							}

							physcore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(physcore)))));
							bioscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscore)))));
							chemscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscore)))));
							matscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(matscore)))));
							//MiddleNumberFor3 middle = new MiddleNumberFor3();
							subjectBig = Math.max( (Math.max(physcore, bioscore)), Math.max(Math.max(physcore, bioscore), chemscore));
							subjectSmall = middle.ComputeMiddle(physcore, bioscore, chemscore);
							scienceScore = (subjectBig+subjectSmall+matscore);

						}
						//Technical
						//Here we pick one subject, the one he/she has performed best, but this subject can be replaced by a science, if the student takes 3 sciences and he/she performed better in the science than in all  the techinicals . 
						if(true){
							double bestTechinical = 0;
							if(StringUtils.equals(pp.getSubjectUuid(), BS_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;
									bsscore = cat1;
									bsscoreMap.put(s.getStudentUuid(),bsscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									bsscore = cat2;
									bsscoreMap.put(s.getStudentUuid(),bsscore);

								}// Start student count for bs
								
								int bs = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										bs++;
										bsCount += bs;
										
									}
								}
								
								// end student-subject counter

							}
							if(StringUtils.equals(pp.getSubjectUuid(), AGR_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									agriscore = cat1;
									agriscorehash.put(s.getStudentUuid(),agriscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									agriscore = cat2;
									agriscorehash.put(s.getStudentUuid(),agriscore);

								}
								// Start student count for agr
								
								int agr = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										agr++;
										agrCount += agr;
										
									}
								}
								
								// end student-subject counter

							}     
							if(StringUtils.equals(pp.getSubjectUuid(), H_S)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									hscscore = cat1;
									hscscoreMap.put(s.getStudentUuid(),hscscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									hscscore = cat2;
									hscscoreMap.put(s.getStudentUuid(),hscscore);

								}
								// Start student count for hsc
								
								int hsc = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										hsc++;
										hscCount += hsc;
										
									}
								}
								
								// end student-subject counter

							}
							if(StringUtils.equals(pp.getSubjectUuid(), COMP_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									comscore = cat1;
									comscoreMap.put(s.getStudentUuid(),comscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									comscore = cat2;
									comscoreMap.put(s.getStudentUuid(),comscore);

								}
								// Start student count for comp
								// TODO 
								int cmp = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										cmp++;
										cmpCount += cmp;
									}
								}
								
								// end student-subject counter

							} 
							bsscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscore)))));
							agriscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscore)))));
							hscscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscore)))));
							comscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(comscore)))));
							bestTechinical = Math.max( (Math.max(bsscore, agriscore)), Math.max(hscscore, comscore));
							techinicalScore = bestTechinical; 

						}    

						//Humanities
						//Here we pick only one subject, the one the student has performed best . 
						if(true){  
							double bestHumanity = 0;     
							if(StringUtils.equals(pp.getSubjectUuid(), GEO_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	 
									geoscore = cat1;
									geoscoreMap.put(s.getStudentUuid(),geoscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									geoscore = cat2;
									geoscoreMap.put(s.getStudentUuid(),geoscore);

								}
								// Start student count for geo
								 
								int geo = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										geo++;
										geoCount += geo;
										
									}
								}
								
								// end student-subject counter

							}
							if(StringUtils.equals(pp.getSubjectUuid(), CRE_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	
									crescore = cat1;
									crescorehash.put(s.getStudentUuid(),crescore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									crescore = cat2;
									crescorehash.put(s.getStudentUuid(),crescore);

								}// Start student count for cre
								
								int cre = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										cre++;
										creCount += cre;
										//System.out.println("debug here creCount = " + creCount);
										
									}
								}
								
								// end student-subject counter

							}
							if(StringUtils.equals(pp.getSubjectUuid(), HIST_UUID)){
								if(StringUtils.equals(examType, "C1")){
									cat1 = (pp.getCatOne()/30)*100;	 
									histscore = cat1;
									histscoreMap.put(s.getStudentUuid(),histscore);
								}
								else {
									cat2 = (pp.getCatTwo()/30)*100;
									histscore = cat2;
									histscoreMap.put(s.getStudentUuid(),histscore);
								}
								// Start student count for hist
								
								int hst = 0;
								for(StudentSubject stuSuc : subjectlist){
									if(StringUtils.equals(stuSuc.getStudentUuid(), s.getStudentUuid())){
										hst++;
										hstCount += hst;
										
									}
								}
								
								// end student-subject counter
							}

							geoscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscore)))));
							crescore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(crescore)))));
							histscore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(histscore)))));
							bestHumanity = Math.max( (Math.max(geoscore, crescore)), Math.max(Math.max(geoscore, crescore), histscore));
							humanityScore = bestHumanity; 

						} 
					}


					grandscore = languageScore+scienceScore+humanityScore+techinicalScore;
					//System.out.println("student="+studNameHash.get(s.getStudentUuid())+",languageScore="+languageScore+",scienceScore="+scienceScore+",humanityScore="+humanityScore+",techinicalScore="+techinicalScore+",grandscore="+grandscore);	

					languageScore = 0; scienceScore = 0; humanityScore = 0;techinicalScore = 0;  
					grandscoremap.put(s.getStudentUuid(), grandscore); 					         
					grandscore = 0;

					//Finalposition = mycount++;
				}

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

				//eng
				double eng_total,eng_grand_total =0;
				double kis_total,kis_grand_total =0;
				double math_total,math_grand_total =0;
				double bio_total,bio_grand_total =0;
				double chem_total,chem_grand_total =0;
				double phy_total,phy_grand_total =0;
				double bs_total,bs_grand_total =0;
				double agr_total,agr_grand_total =0;
				double comp_total,comp_grand_total =0;
				double hmsc_total,hmsc_grand_total =0;
				double cre_total,cre_grand_total =0;
				double hist_total,hist_grand_total =0;
				double geo_total,geo_grand_total =0;
				
				eng_total = 0;
				kis_total = 0;
				math_total = 0;
				bio_total = 0;
				chem_total = 0;
				phy_total = 0;
				agr_total = 0;
				bs_total = 0;
				comp_total = 0;
				hmsc_total = 0;
				hist_total = 0;
				cre_total = 0;
				geo_total = 0;

				//end
				int gA = 0;
				int gAm = 0;
				int gBp = 0; 
				int gB = 0;
				int gBm = 0;
				int gCp = 0; 
				int gC = 0;
				int gCm = 0;
				int gDp = 0; 
				int gD = 0;
				int gDm = 0;
				int gE = 0;


				int count = 1;
				int counttwo = 1;
				int studentcount = 0;
				int stCount = 1;
				for(Object o : as){
					String items = String.valueOf(o);
					String [] item = items.split("=");
					String uuid = item[0];
					totalz = item[1];
					mean = Double.parseDouble(totalz)/7;


					eng_total = 0;
					kis_total = 0;
					math_total = 0;
					bio_total = 0;
					chem_total = 0;
					phy_total = 0;
					agr_total = 0;
					bs_total = 0;
					comp_total = 0;
					hmsc_total = 0;
					hist_total = 0;
					cre_total = 0;
					geo_total = 0;

					totalclassmark +=mean;

					studeadmno = studentAdmNoHash.get(uuid);
					studename = studNameHash.get(uuid);    



					//KCSE
					double kcpe = 0;
					if(primaryDAO.getPrimary(uuid)!=null){
						StudentPrimary primary = primaryDAO.getPrimary(uuid);
						kcpe = Integer.parseInt(primary.getKcpemark()); 
					}




					if(engscorehash.get(uuid)!=null){
						engscore = engscorehash.get(uuid); 
						eng_total = engscore;
						engscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(engscore)))); 

					}else{
						engscorestr = "";
					}


					if(kswscoreMap.get(uuid)!=null){
						kswscore = kswscoreMap.get(uuid);
						kis_total = kswscore;
						kswscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(kswscore))));
					}else{
						kswscorestr = "";
					}


					if(physcoreMap.get(uuid)!=null){
						physcore = physcoreMap.get(uuid);
						phy_total = physcore;
						physcorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(physcore))));
					}else{
						physcorestr = "";
					}

					if(bioscoreMap.get(uuid)!=null){
						bioscore = bioscoreMap.get(uuid);
						bio_total = bioscore;
						bioscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(bioscore))));
					}else{
						bioscorestr = "";
					}


					if(chemscorehash.get(uuid)!=null){
						chemscore = chemscorehash.get(uuid);
						chem_total = chemscore;
						chemscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(chemscore))));
					}else{
						chemscorestr = "";
					}



					if(matscorehash.get(uuid)!=null){
						matscore = matscorehash.get(uuid);
						math_total = matscore;
						matscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(matscore))));
					}else{
						matscorestr = "";
					}

					if(histscoreMap.get(uuid)!=null){
						histscore = histscoreMap.get(uuid);
						hist_total = histscore;
						histscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(histscore))));
					}else{
						histscorestr = "";
					}


					if(crescorehash.get(uuid)!=null){
						crescore = crescorehash.get(uuid);
						cre_total = crescore;
						crescorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(crescore))));
					}else{
						crescorestr = "";
					}


					if(geoscoreMap.get(uuid)!=null){
						geoscore = geoscoreMap.get(uuid);
						geo_total = geoscore;
						geoscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(geoscore))));
					}else{
						geoscorestr = "";
					}


					if(bsscoreMap.get(uuid)!=null){
						bsscore = bsscoreMap.get(uuid);
						bs_total = bsscore;
						bsscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(bsscore))));
					}else{
						bsscorestr = "";
					}


					if(agriscorehash.get(uuid)!=null){
						agriscore = agriscorehash.get(uuid);
						agr_total = agriscore;
						agriscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(agriscore))));
					}else{
						agriscorestr = "";
					}

					if(hscscoreMap.get(uuid)!=null){
						hscscore = hscscoreMap.get(uuid);
						hmsc_total = hscscore;
						hscscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(hscscore))));
					}else{
						hscscorestr = "";
					}

					if(comscoreMap.get(uuid)!=null){
						comscore = comscoreMap.get(uuid);
						comp_total = comscore;
						comscorestr = rf2.format((double)Math.round(Double.parseDouble(rf.format(comscore))));
					}else{
						comscorestr = "";
					}   




					myTable.addCell(new Paragraph(" "+stCount+" ",smallBold));
					myTable.addCell(new Paragraph(studeadmno,smallBold));
					myTable.addCell(new Paragraph(studename,smallBold));	
					myTable.addCell(new Paragraph((int)kcpe +" ",smallBold));
					myTable.addCell(new Paragraph(engscorestr /* "         " + computeSubGrade(engscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(kswscorestr /* "   		 " + computeSubGrade(kswscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(matscorestr /* " 		 " + computeSubGrade(matscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(physcorestr /* "  		 " + computeSubGrade(physcorestr)*/,smallBold));
					myTable.addCell(new Paragraph(chemscorestr /* " 		 " + computeSubGrade(chemscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(bioscorestr /* "		 " + computeSubGrade(bioscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(histscorestr /* " 		 " + computeSubGrade(histscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(crescorestr /* " 		 " + computeSubGrade(crescorestr)*/,smallBold));				   
					myTable.addCell(new Paragraph(geoscorestr /* " 		 " + computeSubGrade(geoscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(bsscorestr /* " 		 " + computeSubGrade(bsscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(agriscorestr /* " 		 " + computeSubGrade(agriscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(hscscorestr /* " 		 " + computeSubGrade(hscscorestr)*/,smallBold));
					myTable.addCell(new Paragraph(comscorestr /* " 		 " + computeSubGrade(comscorestr)*/,smallBold));				   
					myTable.addCell(new Paragraph(halfUP.format(Double.parseDouble(totalz)),smallBold));				   
					myTable.addCell(new Paragraph(halfUP.format(mean),smallBold));
					myTable.addCell(new Paragraph(computeGrade(mean),smallBold));

					if(Double.parseDouble(totalz)==number){
						myTable.addCell(new Paragraph(" "+(count-counttwo++),smallBold));
					}
					else{
						counttwo=1;
						myTable.addCell(new Paragraph(" "+count,smallBold));
					}

					myTable.addCell(new Paragraph(POSMapgn.get(uuid),smallBold));



					/**start grade analyzer */

					if(mean >= gradingSystem.getGradeAplain()){
						gradeCountA++;// set to 0
						gA += gradeCountA;	// ince gA	= 1
						gradeCountA = 0; //reset to 0

					}else if(mean >= gradingSystem.getGradeAminus()){
						gradeCountAm++;
						gAm +=gradeCountAm;	
						gradeCountAm = 0;


					}else if(mean >= gradingSystem.getGradeBplus()){
						gradeCountBp++;
						gBp +=gradeCountBp;	
						gradeCountBp = 0;


					}else if(mean >= gradingSystem.getGradeBplain()){
						//System.out.println("B grade ="+ gradeCountB);

						gradeCountB++;
						gB +=gradeCountB;	
						gradeCountB = 0;
					}else if(mean >= gradingSystem.getGradeBminus()){
						//System.out.println("B- grade ="+ gradeCountBm);
						gradeCountBm++;
						gBm +=gradeCountBm;	
						gradeCountBm = 0;
					}else if(mean >= gradingSystem.getGradeCplus()){
						//System.out.println("C+ grade ="+ gradeCountCP);
						gradeCountCP++;
						gCp +=gradeCountCP;	
						gradeCountCP = 0;
					}else if(mean >= gradingSystem.getGradeCplain()){
						//System.out.println("C grade ="+ gradeCountC);	
						gradeCountC++;
						gC +=gradeCountC;	
						gradeCountC = 0;

					}else if(mean >= gradingSystem.getGradeCminus()){
						// System.out.println("C- grade ="+ gradeCountCm);					  
						gradeCountCm++;
						gCm +=gradeCountCm;	
						gradeCountCm = 0;
					}else if(mean >= gradingSystem.getGradeDplus()){
						//System.out.println("D+ grade ="+ gradeCountDp);	
						gradeCountDp++;
						gDp +=gradeCountDp;	
						gradeCountDp = 0;

					}else if(mean >= gradingSystem.getGradeDplain()){
						//System.out.println("D grade ="+ gradeCountD);	
						gradeCountD++;
						gD +=gradeCountD;	
						gradeCountD = 0;

					}else if(mean >= gradingSystem.getGradeDminus()){
						// System.out.println("D- grade ="+ gradeCountDm);
						gradeCountDm++;
						gDm +=gradeCountDm;	
						gradeCountDm = 0;
					}else{
						// System.out.println("E grade ="+ gradeCountE);
						gradeCountE++;
						gE +=gradeCountE;	
						gradeCountE = 0;

					}

					if(mean ==0){
						//System.out.println("E grade ="+ gradeCountE);
						gradeCountE++;
						gE +=gradeCountE;	
						gradeCountE = 0;

					}//end if

					count++;
					studentcount++;
					number=Double.parseDouble(totalz);



					//add totals
					eng_grand_total +=eng_total;
					kis_grand_total +=kis_total;
					math_grand_total +=math_total;
					bio_grand_total +=bio_total;
					phy_grand_total +=phy_total;
					chem_grand_total +=chem_total;
					agr_grand_total +=agr_total;
					bs_grand_total +=bs_total;
					comp_grand_total +=comp_total;
					hmsc_grand_total +=hmsc_total;
					hist_grand_total +=hist_total;
					cre_grand_total +=cre_total;
					geo_grand_total +=geo_total;

					stCount ++;

				}


				//TABLE START
				SubjectTable.addCell(new Paragraph("A",boldFont));
				SubjectTable.addCell(new Paragraph("A-",boldFont));
				SubjectTable.addCell(new Paragraph("B+",boldFont));

				SubjectTable.addCell(new Paragraph("B",boldFont));
				SubjectTable.addCell(new Paragraph("B-",boldFont));
				SubjectTable.addCell(new Paragraph("C+",boldFont));

				SubjectTable.addCell(new Paragraph("C",boldFont));
				SubjectTable.addCell(new Paragraph("C-",boldFont));
				SubjectTable.addCell(new Paragraph("D+",boldFont));

				SubjectTable.addCell(new Paragraph("D",boldFont));
				SubjectTable.addCell(new Paragraph("D-",boldFont));
				SubjectTable.addCell(new Paragraph("E",boldFont));


				SubjectTable.setWidthPercentage(100); 
				SubjectTable.setWidths(new int[]{20,20,20,20,20,20,20,20,20,20,20,20});   
				SubjectTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				//chart table

				barchartTable.setWidthPercentage(100); 
				barchartTable.setWidths(new int[]{100}); 
				barchartTable.setHorizontalAlignment(Element.ALIGN_LEFT);



				SubjectTable.addCell(new Paragraph(gA+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gAm+" ",smallBold));

				SubjectTable.addCell(new Paragraph(gBp+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gB+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gBm+" ",smallBold));

				SubjectTable.addCell(new Paragraph(gCp+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gC+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gCm+" ",smallBold));

				SubjectTable.addCell(new Paragraph(gDp+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gD+" ",smallBold));
				SubjectTable.addCell(new Paragraph(gDm+" ",smallBold));

				SubjectTable.addCell(new Paragraph(gE+" ",smallBold));


				//END TABLE

				//draw chart start
				PdfPCell BarChartHeader = new PdfPCell();
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();

// TODO 
				
				if(engCount == 0){
					engCount = Mod;
				}if(kisCount == 0){
					kisCount = Mod;
				}if(matCount == 0){
					matCount = Mod;
				}if(phyCount == 0){
					phyCount = Mod;
				}if(chmCount == 0){
					chmCount = Mod;
				}if(bioCount == 0){
					bioCount = Mod;
				}if(hstCount == 0){
					hstCount = Mod;
				}if(creCount == 0){
					creCount = Mod;
				}if(geoCount == 0){
					geoCount = Mod;
				}if(bsCount == 0){
					bsCount = Mod;
				}if(agrCount == 0){
					agrCount = Mod;
				}if(hscCount == 0){
					hscCount = Mod;
				}if(cmpCount == 0){
					cmpCount = Mod;
				}
				
				/*System.out.println("eng ( " + eng_grand_total +"/ (" + engCount + " *100)) *12 = " + (eng_grand_total/(engCount*100))*12);
				System.out.println("kis ( " + kis_grand_total +"/ (" + kisCount + " *100)) *12 = " + (kis_grand_total/(kisCount*100))*12);
				System.out.println("mat ( " + math_grand_total +"/ (" + matCount + " *100)) *12 = " + (math_grand_total/(matCount*100))*12);
				
				System.out.println("phy ( " + phy_grand_total +"/ (" + phyCount + " *100)) *12 = " + (phy_grand_total/(phyCount*100))*12);
				System.out.println("chm ( " + chem_grand_total +"/ (" + chmCount + " *100)) *12 = " + (chem_grand_total/(chmCount*100))*12);
				System.out.println("bio ( " + bio_grand_total +"/ (" + phyCount + " *100)) *12 = " + (bio_grand_total/(phyCount*100))*12);
				
				System.out.println("hist ( " + hist_grand_total +"/ (" + hstCount + " *100)) *12 = " + (hist_grand_total/(hstCount*100))*12);
				System.out.println("cre ( " + cre_grand_total +"/ (" + creCount + " *100)) *12 = " + (cre_grand_total/(creCount*100))*12);
				System.out.println("geo ( " + geo_grand_total +"/ (" + geoCount + " *100)) *12 = " + (geo_grand_total/(geoCount*100))*12);
				
				System.out.println("bs ( " + bs_grand_total +"/ (" + bsCount + " *100)) *12 = " + (bs_grand_total/(bsCount*100))*12);
				System.out.println("agr ( " + agr_grand_total +"/ (" + agrCount + " *100)) *12 = " + (agr_grand_total/(agrCount*100))*12);
				System.out.println("hsc ( " + hmsc_grand_total +"/ (" + hscCount + " *100)) *12 = " + (hmsc_grand_total/(hscCount*100))*12);
				System.out.println("phy ( " + comp_grand_total +"/ (" + cmpCount + " *100)) *12 = " + (comp_grand_total/(cmpCount*100))*12);*/
                
				
				dataset.setValue((eng_grand_total/(engCount*100))*12, "Pnts", "ENG");
				dataset.setValue((kis_grand_total/(kisCount*100))*12, "Pnts", "KIS");
				dataset.setValue((math_grand_total/(matCount*100))*12, "Pnts", "MAT");


				dataset.setValue((phy_grand_total/(phyCount*100))*12, "Pnts", "PHY");
				dataset.setValue((chem_grand_total/(chmCount*100))*12, "Pnts", "CHE");
				dataset.setValue((bio_grand_total/(bioCount*100))*12, "Pnts", "BIO");

				dataset.setValue((hist_grand_total/(hstCount*100))*12, "Pnts", "HST");
				dataset.setValue((cre_grand_total/(creCount*100))*12, "Pnts", "CRE");
				dataset.setValue((geo_grand_total/(geoCount*100))*12, "Pnts", "GEO");

				dataset.setValue((bs_grand_total/(bsCount*100))*12, "Pnts", "BS");
				dataset.setValue((agr_grand_total/(agrCount*100))*12, "Pnts", "AGR");
				dataset.setValue((hmsc_grand_total/(hscCount*100))*12, "Pnts", "HSC");
				dataset.setValue((comp_grand_total/(cmpCount*100))*12, "Pnts", "CMP");
				
				eng_grand_total = 0;
				kis_grand_total = 0;
				math_grand_total = 0;
				phy_grand_total = 0;
				chem_grand_total = 0;
				bio_grand_total = 0;
				hist_grand_total = 0;
				cre_grand_total =0;
				geo_grand_total = 0;
				bs_grand_total = 0;
				agr_grand_total = 0;
				hmsc_grand_total = 0;
				comp_grand_total = 0;
				

				engCount = 0;kisCount = 0;matCount = 0;
				phyCount = 0;bioCount = 0;chmCount = 0;
				bsCount = 0;agrCount = 0;hscCount = 0;cmpCount = 0;
				hstCount = 0;creCount = 0;geoCount = 0;



				//CONTROL
				dataset.setValue(12, "Control ", "Control ");
				JFreeChart chart = ChartFactory.createBarChart("Subjects Performance Analysis", // chart title
						"Subject", // domain axis label (X axis)
						"Weight", //  range axis label (Y axis)
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
					chartImage.scaleToFit(1500,500); 
					BarChartHeader.addElement(new Chunk(chartImage,15,-130));// margin left  ,  margin top
					BarChartHeader.setBorder(Rectangle.NO_BORDER); 
					BarChartHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
					BarChartHeader.setHorizontalAlignment(Element.ALIGN_LEFT);


				} catch (IOException e) {
					e.printStackTrace();
				}

				barchartTable.addCell(BarChartHeader);
				//draw chart end


				//reset variables
				gA = 0;
				gAm = 0;
				gBp = 0;
				gB = 0;
				gBm = 0;
				gCp = 0;
				gC = 0;
				gCm = 0;
				gDp = 0;
				gD = 0;
				gDm = 0;
				gE = 0;

				classmean = totalclassmark/studentcount;
				//reset the subjects mean
				eng_grand_total =0;

			}
			Paragraph classname = new Paragraph();
			classname.add(new Paragraph(" (CLASS MEAN :" + halfUP.format(classmean) + ",  GRADE : "+computeGrade(classmean) +")\n",boldFont2));


			document.add(emptyline);
			document.add(classname);
			document.add(emptyline);
			document.add(myTable);  
			document.add(emptyline);
			document.newPage();
			document.add(SubjectTable); 
			document.add(emptyline);
			document.add(barchartTable);  
			// step 5
			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}


	/**
	 * @param score
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
