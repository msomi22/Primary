/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.exam.std4_8;

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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

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

import ke.co.fastech.primaryschool.bean.exam.ExamResult;
import ke.co.fastech.primaryschool.bean.exam.GradingSystem;
import ke.co.fastech.primaryschool.bean.exam.MeanScore;
import ke.co.fastech.primaryschool.bean.school.Classroom;
import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.persistence.exam.BarWeightDAO;
import ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO;
import ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO;
import ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO;
import ke.co.fastech.primaryschool.persistence.school.ClassroomDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ComputationEngine;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author peter
 *
 */
public class StreamPerformanceList  extends HttpServlet{


	private Font boldCourierText10 = new Font(Font.FontFamily.COURIER, 10,Font.BOLD);
	private Font boldCourierText14 = new Font(Font.FontFamily.COURIER, 14,Font.BOLD);
	private Font boldNewRoman7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
	private Font boldNewRoman8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
	private Font normalNewRoman7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private BaseColor baseColor = new BaseColor(255,255,255);//while

	private Document document;
	private PdfWriter writer;
	private String PDF_SUBTITLE ="";
	private GradingSystem gradingSystem;

	private String USER= "";
	private String path ="";

	private Cache accountCache;
	private Logger logger;

	private String schoolname = "";
	private String title = "";

	private static SystemConfigDAO systemConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static PerformanceDAO performanceDAO;
	private static MeanScoreDAO meanScoreDAO;
	private static BarWeightDAO barWeightDAO;
	private static StudentDAO studentDAO;
	private static ClassroomDAO classroomDAO;
	private static StreamDAO streamDAO;
	private ComputationEngine computationEngine;

	private HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	private HashMap<String, String> studNameHash = new HashMap<String, String>();
	private HashMap<String, String> streamHash = new HashMap<String, String>();
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
		computationEngine = new ComputationEngine();
		systemConfigDAO = SystemConfigDAO.getInstance();
		gradingSystemDAO = GradingSystemDAO.getInstance();
		performanceDAO = PerformanceDAO.getInstance();
		classroomDAO = ClassroomDAO.getInstance();
		meanScoreDAO = MeanScoreDAO.getInstance();
		barWeightDAO = BarWeightDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
	}

	/** streamPerformanceList
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");

		String accountuuid =request.getParameter("accountuuid");
		String streamuuid = request.getParameter("streamuuid");

		Account school = new Account();

		net.sf.ehcache.Element element;
		if ((element = accountCache.get(accountuuid)) != null) {
			school = (Account) element.getObjectValue();
		}

		SystemConfig systemConfig = systemConfigDAO.getSystemConfig(school.getUuid()); 
		Stream stream = streamDAO.getStreamById(streamuuid); 
		
		String examType = systemConfig.getExamcode();

		streamHash.put(streamuuid, stream.getStreamName());
		String classuuid = "";
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid()); 
		
		String fileName = new StringBuffer(StringUtils.trimToEmpty("PerformanceList")) 
				.append("_")
				.append(stream.getStreamName().replaceAll(" ", "_"))
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getSchoolAddres()+"\n" 
				+ ""+school.getSchoolHomeTown()+" - Kenya\n" 
				+ "" + school.getSchoolPhone()+"\n"
				+ "" + school.getSchoolEmail()+"\n"
				+ "" + school.getSchoolMotto()+"\n";

		if(StringUtils.equals(examType, "OPENER")){
			title = "_____________________________________ \n"
					+ " Opener Exam Result for : " + stream.getStreamName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + " "+"\n"; 

		}if(StringUtils.equals(examType, "MIDTERM")){
			title = "_____________________________________ \n"
					+ " Mid-Term Exam Result for : " + stream.getStreamName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + " "+"\n";

		}if(StringUtils.equals(examType, "ENDTERM")){
			title = "_____________________________________ \n"
					+ " End of Term Exam Result for : " + stream.getStreamName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + " "+"\n";

		}



		List<Classroom>  list = classroomDAO.getClassroomList();
		for(Classroom room : list){
			if(StringUtils.contains(stream.getStreamName(), room.getClassName())){
				classuuid = room.getUuid();
			}
		}

		List<Student> studentList  = new ArrayList<Student>(); 
		studentList = studentDAO.getStudentsList(accountuuid);
		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmmissinNo().substring(0, Math.min(stu.getAdmmissinNo().length(), 4))); 

			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedmiddle = StringUtils.capitalize(stu.getMiddlename().toLowerCase());
			String formatedlastname = StringUtils.capitalize(stu.getLastname().toLowerCase());

			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 7));
			formatedmiddle = formatedmiddle.substring(0, Math.min(formatedmiddle.length(), 7));
			formatedlastname = formatedlastname.substring(0, Math.min(formatedlastname.length(), 7));

			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedmiddle); 
		}


		List<ExamResult> classdistinctlist = performanceDAO.getStudentDistinctByClassId(school.getUuid(),classuuid, systemConfig.getTerm(), systemConfig.getYear());
		List<ExamResult> streamdistinctlist = performanceDAO.getStudentDistinctByStreamId(school.getUuid(),streamuuid, systemConfig.getTerm(), systemConfig.getYear());

		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());

			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);
			populatePDFDocument(school,classuuid,streamuuid,classdistinctlist,streamdistinctlist,systemConfig,path,examType);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}


	private void populatePDFDocument(Account school, String classuuid, String streamuuid,List<ExamResult> classdistinctlist,
			List<ExamResult> streamdistinctlist, SystemConfig systemConfig, String realPath,String examType) {

		try {

			document.open();

			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , boldCourierText14));//
			content.add(new Paragraph((PDF_SUBTITLE +"") , boldNewRoman8));
			content.add(new Paragraph((title +" \n") , boldCourierText10));

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


			PdfPCell countHeader = new PdfPCell(new Paragraph("No",boldNewRoman7));
			countHeader.setBackgroundColor(baseColor);
			countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);	

			PdfPCell admnoHeader = new PdfPCell(new Paragraph("AdmNo",boldNewRoman7));
			admnoHeader.setBackgroundColor(baseColor);
			admnoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell nameHeader = new PdfPCell(new Paragraph("NAME",boldNewRoman7));
			nameHeader.setBackgroundColor(baseColor);
			nameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell engHeader = new PdfPCell(new Paragraph("ENG",boldNewRoman7));
			engHeader.setBackgroundColor(baseColor);
			engHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell kiswHeader = new PdfPCell(new Paragraph("KISW",boldNewRoman7));
			kiswHeader.setBackgroundColor(baseColor);
			kiswHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell mathHeader = new PdfPCell(new Paragraph("MATH",boldNewRoman7));
			mathHeader.setBackgroundColor(baseColor);
			mathHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell sciHeader = new PdfPCell(new Paragraph("SCI",boldNewRoman7));
			sciHeader.setBackgroundColor(baseColor);
			sciHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell ssHeader = new PdfPCell(new Paragraph("SS",boldNewRoman7));
			ssHeader.setBackgroundColor(baseColor);
			ssHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell creHeader = new PdfPCell(new Paragraph("CRE",boldNewRoman7));
			creHeader.setBackgroundColor(baseColor);
			creHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell sscreHeader = new PdfPCell(new Paragraph("SS-CRE",boldNewRoman7));
			sscreHeader.setBackgroundColor(baseColor);
			sscreHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell totalHeader = new PdfPCell(new Paragraph("TOTAL",boldNewRoman7));
			totalHeader.setBackgroundColor(baseColor);
			totalHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanHeader = new PdfPCell(new Paragraph("MEAN",boldNewRoman7));
			meanHeader.setBackgroundColor(baseColor);
			meanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell gradeHeader = new PdfPCell(new Paragraph("GRADE",boldNewRoman7));
			gradeHeader.setBackgroundColor(baseColor);
			gradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell devHeader = new PdfPCell(new Paragraph("DEV",boldNewRoman7));
			devHeader.setBackgroundColor(baseColor);
			devHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell streamPosHeader = new PdfPCell(new Paragraph("S-POS",boldNewRoman7));
			streamPosHeader.setBackgroundColor(baseColor);
			streamPosHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell classPosHeader = new PdfPCell(new Paragraph("C-POS",boldNewRoman7));
			classPosHeader.setBackgroundColor(baseColor);
			classPosHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable subjectScoreTable = new PdfPTable(16); 
			PdfPTable subAnalysisTable = new PdfPTable(10); 

			subjectScoreTable.addCell(countHeader);
			subjectScoreTable.addCell(admnoHeader);
			subjectScoreTable.addCell(nameHeader);
			subjectScoreTable.addCell(engHeader);
			subjectScoreTable.addCell(kiswHeader);
			subjectScoreTable.addCell(mathHeader);
			subjectScoreTable.addCell(sciHeader);
			subjectScoreTable.addCell(ssHeader);
			subjectScoreTable.addCell(creHeader);
			subjectScoreTable.addCell(sscreHeader);
			subjectScoreTable.addCell(totalHeader);
			subjectScoreTable.addCell(meanHeader);
			subjectScoreTable.addCell(gradeHeader);
			subjectScoreTable.addCell(devHeader);
			subjectScoreTable.addCell(streamPosHeader);
			subjectScoreTable.addCell(classPosHeader);
			subjectScoreTable.setWidthPercentage(100); 
			subjectScoreTable.setWidths(new int[]{10,15,35,12,12,13,12,12,12,18,20,15,15,15,15,15});   
			subjectScoreTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			subAnalysisTable.addCell(new Paragraph("ENTRY",boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph(" ",boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("ENG :\nEntry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_ENG,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("KISW :\nEntry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_KIS,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));

			subAnalysisTable.addCell(new Paragraph("MATH : Entry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_MATH,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("SCI :\nEntry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SCI,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("SS :\nEntry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SST,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));

			subAnalysisTable.addCell(new Paragraph("CRE :\nEntry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_CRE,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("SS-CRE : Entry " + performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SST,streamuuid,systemConfig.getTerm(),systemConfig.getYear()),boldNewRoman7));
			subAnalysisTable.addCell(new Paragraph("TOTAL",boldNewRoman7));

			subAnalysisTable.setWidthPercentage(100); 
			subAnalysisTable.setWidths(new int[]{15,20,23,23,20,20,20,25,20,25});    
			subAnalysisTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			String subject = "";

			Map<String,Double> examScoremap = new LinkedHashMap<String,Double>();

			double classOTotal = 0,classMTotal = 0,classETotal = 0;
			double classOscore = 0,classMscore = 0,classEscore = 0;

			int classposition = 0;
			String classStudentTotal = "";
			for(ExamResult studentDis : classdistinctlist){
				List<ExamResult> classperformancelist = performanceDAO.getStudentPerformanceByClassId(studentDis.getStudentUuid(),
						classuuid, systemConfig.getTerm(), systemConfig.getYear());

				for(ExamResult classscores : classperformancelist){

					subject = classscores.getSubjectUuid();

					if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
						classOscore = classscores.getOpenner();
						classOTotal += computationEngine.computeOpener(subject,classOscore);
						examScoremap.put(studentDis.getStudentUuid(), classOTotal);

					}if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
						classMscore = classscores.getMidterm();
						classMTotal += computationEngine.computeMidterm(subject,classMscore); 
						examScoremap.put(studentDis.getStudentUuid(), classMTotal);

					}if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
						classEscore = classscores.getEndterm();
						classETotal += computationEngine.computeEndterm(subject,classEscore); 
						examScoremap.put(studentDis.getStudentUuid(), classETotal);
					}

				}

				classOTotal = 0;classMTotal  = 0;classETotal = 0;
				classOscore  = 0;classMscore  = 0;classEscore = 0;
				classposition++;
			}
			classStudentTotal = Integer.toString(classposition); 


			Map<String,String> classPositionmap = new LinkedHashMap<String,String>(); // C class
			//Map<String,Integer> classPositionmap2 = new LinkedHashMap<String,Integer>(); // C class

			@SuppressWarnings("unchecked")
			ArrayList<?> examList = new ArrayList(examScoremap.entrySet());
			Collections.sort(examList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int examcount = 1;
			String studentuuid = "";
			String totalmark  = "";
			String theposition = "";
			int ositionInt = 1;
			//int mypositionInt = 0;
			double mean = 0;
			double number = 0.0;
			//int classPosition1 = 0;
			for(Object o : examList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				studentuuid = item[0];
				totalmark = item[1];

				mean = Double.parseDouble(totalmark)/5;
				if(mean==number){
					theposition = (" " +(examcount-ositionInt++)+ " " );//+ classStudentTotal
					//mypositionInt = (examcount-ositionInt++);
				}else{
					ositionInt=1;
					theposition = (" " +examcount+ " " );//+ classStudentTotal
					//mypositionInt = examcount;
				}
				classPositionmap.put(studentuuid, theposition);
				//classPositionmap2.put(studentuuid, mypositionInt);

				//save mean and positions
				double weight = 0;
				weight = (mean/100)*12; 
				if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
					meanScoreDAO.putMeanScore(mean,"0", theposition, studentuuid, systemConfig.getTerm(), systemConfig.getYear());
					barWeightDAO.putWeight(weight, studentuuid, systemConfig.getTerm(), systemConfig.getYear());
				}


				examcount++;
				number=mean;
			}




			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);	


			Map<String,Double> streamScoremap = new LinkedHashMap<String,Double>();

			double streamOT=0,streamMT=0,streamET=0;//total
			double streamOS=0,streamMS=0,streamES=0;//score

			int streamStudentcount = 0;
			String streamStudentTotal = "";
			for(ExamResult studentDis : streamdistinctlist){
				List<ExamResult> streamperformancelist = performanceDAO.getStudentPerformanceByStreamId(studentDis.getStudentUuid(),
						streamuuid, systemConfig.getTerm(), systemConfig.getYear());


				for(ExamResult streamscores : streamperformancelist){

					subject = streamscores.getSubjectUuid();

					if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
						streamOS = streamscores.getOpenner();
						streamOT += computationEngine.computeOpener(subject,streamOS);
						streamScoremap.put(studentDis.getStudentUuid(), streamOT);

					}

					if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
						streamMS = streamscores.getMidterm();
						streamMT += computationEngine.computeMidterm(subject,streamMS); 
						streamScoremap.put(studentDis.getStudentUuid(), streamMT);

					}

					if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
						streamES = streamscores.getEndterm();
						streamET += computationEngine.computeEndterm(subject,streamES); 
						streamScoremap.put(studentDis.getStudentUuid(), streamET);
					}

				}

				streamOT=0;streamMT=0;streamET=0;//total
				streamOS=0;streamMS=0;streamES=0;//score
				streamStudentcount++;
			}
			streamStudentTotal = Integer.toString(streamStudentcount);


			@SuppressWarnings("unchecked")
			ArrayList<?> streamList = new ArrayList(streamScoremap.entrySet());
			Collections.sort(streamList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});





			//subject totals 
			double engtotal = 0,engmean = 0;
			double kistotal = 0,kismean = 0;
			double mathtotal = 0,mathmean = 0;
			double scitotal = 0,scimean = 0;
			double sstotal = 0,ssmean = 0;
			double cretotal = 0,cremean = 0;
			double sscretotal = 0,sscremean = 0;
			//subject totals 



			int finalcount = 1;
			int studentcount = 0;
			String finalstudentuuid = "";
			double finaltotalmark  = 0;
			String thefinalposition = "";
			double finalmean = 0;
			double finalnumber = 0;
			int finalposition = 1;
			double streamTotal = 0;
			//int streamPosition =0;
			String classPosition = "";
			for(Object o : streamList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				finalstudentuuid = item[0];
				finaltotalmark = Double.parseDouble(item[1]);
				finalmean = finaltotalmark/5; 
				streamTotal += finalmean;


				if(finalmean==finalnumber){
					thefinalposition = (" " +(finalcount-finalposition++)+ " " );//+ streamStudentTotal
					//streamPosition = (finalcount-finalposition++);
				}else{
					finalposition=1;
					thefinalposition = (" " +finalcount+ "  " );//+ streamStudentTotal
					//streamPosition = finalcount;
				}

				//save mean and positions
				double weight = 0;
				weight = (finalmean/100)*12; 
				if(classPositionmap.get(finalstudentuuid) !=null){
					classPosition = classPositionmap.get(finalstudentuuid); 
				}
				if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
					meanScoreDAO.putMeanScore(finalmean, thefinalposition + "/ " + streamStudentTotal, classPosition+"/ " + classStudentTotal, finalstudentuuid, systemConfig.getTerm(), systemConfig.getYear());
					barWeightDAO.putWeight(weight, finalstudentuuid, systemConfig.getTerm(), systemConfig.getYear());
				}

				//find deviation
				int thisterm =0,lastterm =0;
				int thisyear = 0,lastyear=0;
				double thistermmean = 0,lasttermmean = 0; 
				thisterm = Integer.parseInt(systemConfig.getTerm());
				thisyear = Integer.parseInt(systemConfig.getYear()); 
				if(thisterm == 1){
					lastterm = 3;
					lastyear = thisyear - 1;
				}if(thisterm == 2){
					lastterm = 1;
					lastyear = thisyear;
				}if(thisterm == 3){
					lastterm = 2;
					lastyear = thisyear;
				}

				//get last term mean
				MeanScore lasttermscore = new MeanScore();
				if(meanScoreDAO.getMeanScore(finalstudentuuid,Integer.toString(lastterm),Integer.toString(lastyear)) !=null){
					lasttermscore = meanScoreDAO.getMeanScore(finalstudentuuid,Integer.toString(lastterm),Integer.toString(lastyear));
				}
				if(lasttermscore !=null){
					lasttermmean = lasttermscore.getMeanScore();
				}
				//this term mean
				MeanScore thistermscore = new MeanScore();
				if(meanScoreDAO.getMeanScore(finalstudentuuid,systemConfig.getTerm(),systemConfig.getYear()) !=null){
					thistermscore = meanScoreDAO.getMeanScore(finalstudentuuid,systemConfig.getTerm(),systemConfig.getYear());
				}
				if(lasttermscore !=null){
					thistermmean = thistermscore.getMeanScore();
				}

				int engfinalscore = 0,kisfinalscore = 0,mathfinalscore = 0,scifinalscore = 0;
				double sstfinalscore = 0,crefinalscore = 0;

				List<ExamResult> streamperformancelist = performanceDAO.getStudentPerformanceByStreamId(finalstudentuuid,
						streamuuid, systemConfig.getTerm(), systemConfig.getYear());

				for(ExamResult score : streamperformancelist){

					if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_ENG)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							engfinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							engfinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							engfinalscore = (int)score.getEndterm();
						}

					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_KIS)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							kisfinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							kisfinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							kisfinalscore = (int)score.getEndterm();
						}

					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_MATH)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							mathfinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							mathfinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							mathfinalscore = (int)score.getEndterm();
						}

					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_SCI)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							scifinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							scifinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							scifinalscore = (int)score.getEndterm();
						}

					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_CRE)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							crefinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							crefinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							crefinalscore = (int)score.getEndterm();
						}

					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_SST)) {
						if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
							sstfinalscore  = (int)score.getOpenner();
						}else if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
							sstfinalscore = (int)score.getMidterm();
						}else if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
							sstfinalscore = (int)score.getEndterm();
						}

					}

				}

				DecimalFormat rf = new DecimalFormat("0"); 
				rf.setRoundingMode(RoundingMode.HALF_UP);

				double sscre = 0;String sscreStr = "";
				sscre = ((sstfinalscore + crefinalscore)/90)*100;
				sscreStr = rf.format(sscre);

				subjectScoreTable.addCell(new Paragraph(" "+finalcount+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(studentAdmNoHash.get(finalstudentuuid),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(studNameHash.get(finalstudentuuid),normalNewRoman7));				   
				subjectScoreTable.addCell(new Paragraph(" "+engfinalscore,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+kisfinalscore,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+mathfinalscore,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+scifinalscore,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+rf.format(sstfinalscore),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+rf.format(crefinalscore),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+sscreStr,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+df.format(finaltotalmark),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+df.format(finalmean),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+computeGrade(finalmean),normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+deviationFinder(thistermmean,lasttermmean),normalNewRoman7)); 
				subjectScoreTable.addCell(new Paragraph(" "+thefinalposition,normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(" "+classPositionmap.get(finalstudentuuid),normalNewRoman7));		

				//subject totals
				engtotal += engfinalscore; 
				kistotal += kisfinalscore;
				mathtotal += mathfinalscore;
				scitotal += scifinalscore;
				sstotal += (sstfinalscore/60)*100; 
				cretotal += (crefinalscore/30)*100; 
				sscretotal += sscre; 

				engfinalscore = 0;kisfinalscore= 0;mathfinalscore= 0;scifinalscore = 0;
				sstfinalscore = 0;crefinalscore = 0;


				finalcount++;
				studentcount++;
				finalnumber=finalmean;

			}

			
			double thestreamMean= (streamTotal/studentcount); 
			engmean = engtotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_ENG,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			kismean =  kistotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_KIS,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			mathmean  =  mathtotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_MATH,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			scimean  =  scitotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SCI,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			ssmean  =  sstotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SST,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			cremean  =  cretotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_CRE,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			sscremean  =  sscretotal/performanceDAO.getSubjectCountPerStream(school.getUuid(),ExamConstants.SUB_SST,streamuuid,systemConfig.getTerm(),systemConfig.getYear());
			
				for(int i = 0; i < 3;i++){

						if(i == 0){
							subAnalysisTable.addCell(new Paragraph(studentcount+" ",normalNewRoman7)); 
							subAnalysisTable.addCell(new Paragraph("TOTAL ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(engtotal)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(kistotal)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(df.format(mathtotal)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(scitotal)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(sstotal)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(df.format(cretotal)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(sscretotal)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(streamTotal)+" ",normalNewRoman7));

						}if(i == 1){
							subAnalysisTable.addCell(new Paragraph(studentcount+" ",normalNewRoman7)); 
							subAnalysisTable.addCell(new Paragraph("MEAN ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(engmean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(kismean)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(df.format(mathmean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(scimean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(ssmean)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(df.format(cremean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(sscremean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(df.format(thestreamMean)+" ",normalNewRoman7));
						}
						if(i == 2){
							subAnalysisTable.addCell(new Paragraph(" ",normalNewRoman7)); 
							subAnalysisTable.addCell(new Paragraph("GRADE ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(engmean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(kismean)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(computeGrade(mathmean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(scimean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(ssmean)+" ",normalNewRoman7));

							subAnalysisTable.addCell(new Paragraph(computeGrade(cremean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(sscremean)+" ",normalNewRoman7));
							subAnalysisTable.addCell(new Paragraph(computeGrade(thestreamMean)+" ",normalNewRoman7));
						}


					}
			engtotal = 0;kistotal= 0;mathtotal= 0;scitotal= 0;sstotal= 0;cretotal= 0;sscretotal= 0;

			Paragraph emptyline = new Paragraph(("                              "));
			document.add(emptyline);
			document.add(emptyline);
			document.add(subjectScoreTable);
			document.add(emptyline);
			document.add(subAnalysisTable);

			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}


	/** Find deviation from last term
	 * pass thisTermMean following lastTermMean
	 * @param lastTermMean
	 * @param thisTermMean
	 * @return the deviation 
	 */

	private String deviationFinder(double thisTermMean, double lastTermMean){
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.DOWN);	
		String deviationStr = "";
		double deviation = 0;
		if(lastTermMean == 0){
			deviation = 0;
		}else{
			deviation = thisTermMean - lastTermMean;
		}
		if(deviation != 0){
			deviationStr = df.format(deviation);
		}else{
			deviationStr = "";
		}

		return deviationStr;
	}



	/**
	 * @param score
	 * @return
	 */
	private String computeGrade(double score) {
		String grade = "";
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
		}else if(mean >= gradingSystem.getGradeE()){
			grade = "E";
		}

		if(mean ==0){
			grade = " ";
		}

		return grade;
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
			imgLogo.setAlignment(Element.ALIGN_LEFT);

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
	private static final long serialVersionUID = -7670858158714121735L;

}
