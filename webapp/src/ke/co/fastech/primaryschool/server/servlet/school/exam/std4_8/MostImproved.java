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
import ke.co.fastech.primaryschool.bean.exam.MeanScore;
import ke.co.fastech.primaryschool.bean.school.Classroom;
import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO;
import ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO;
import ke.co.fastech.primaryschool.persistence.school.ClassroomDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ComputationEngine;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class MostImproved extends HttpServlet{


	private Font boldCourierText10 = new Font(Font.FontFamily.COURIER, 10,Font.BOLD);
	private Font boldCourierText14 = new Font(Font.FontFamily.COURIER, 14,Font.BOLD);
	private Font boldNewRoman7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
	private Font boldNewRoman8 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
	private Font normalNewRoman7 = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
	private BaseColor baseColor = new BaseColor(255,255,255);//while

	private Document document;
	private PdfWriter writer;
	private String PDF_SUBTITLE ="";
	
	private String USER= "";
	private String path ="";

	private Cache accountCache;
	private Logger logger;

	private String schoolname = "";
	private String title = "";

	private static SystemConfigDAO systemConfigDAO;
	private static PerformanceDAO performanceDAO;
	private static MeanScoreDAO meanScoreDAO;
	private static StudentDAO studentDAO;
	private static ClassroomDAO classroomDAO;
	private static StreamDAO streamDAO;
	private ComputationEngine computationEngine;

	private HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	private HashMap<String, String> studNameHash = new HashMap<String, String>();
	private HashMap<String, String> stuStreamHash = new HashMap<String, String>();
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
		performanceDAO = PerformanceDAO.getInstance();
		classroomDAO = ClassroomDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		meanScoreDAO = MeanScoreDAO.getInstance();
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

		String classmuuid =request.getParameter("classmuuid");
		String accountuuid = request.getParameter("accountuuid");

		Account school = new Account();

		net.sf.ehcache.Element element;
		if ((element = accountCache.get(accountuuid)) != null) {
			school = (Account) element.getObjectValue();
		}

		SystemConfig systemConfig = systemConfigDAO.getSystemConfig(school.getUuid()); 

		Classroom classroom = classroomDAO.getClassroomByUuid(classmuuid);
		
		List<Stream> streamlist = new ArrayList<>();
		streamlist = streamDAO.getStreamList(school.getUuid()); 
		for(Stream stream : streamlist){
			streamHash.put(stream.getUuid(), stream.getStreamName()); 
		}
	
		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getSchoolAddres()+"\n" 
				+ ""+school.getSchoolHomeTown()+" - Kenya\n" 
				+ "" + school.getSchoolPhone()+"\n"
				+ "" + school.getSchoolEmail()+"\n"
				+ "" + school.getSchoolMotto()+"\n";
		
		String examType = systemConfig.getExamcode();
		
		String fileName = new StringBuffer(StringUtils.trimToEmpty("PerformanceList")) 
				.append("_")
				.append(classroom.getClassName().replaceAll(" ", "_"))
				.append(".pdf")
				.toString();
		response.setHeader("Content-Disposition", "inline; filename=\""+fileName);

		if(StringUtils.equals(examType, "OPENER")){
			title = "_____________________________________ \n"
					+ " Opener Exam Most Improved & Top ten for : " + classroom.getClassName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + "  "+"\n"; 

		}if(StringUtils.equals(examType, "MIDTERM")){
			title = "_____________________________________ \n"
					+ " Mid-Term Exam Most Improved & Top ten for : " + classroom.getClassName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + " "+"\n";

		}if(StringUtils.equals(examType, "ENDTERM")){
			title = "_____________________________________ \n"
					+ " End of Term Exam Most Improved & Top ten for : " + classroom.getClassName() + " \nTERM : " + systemConfig.getTerm() + " YEAR : " + systemConfig.getYear() + " "+"\n";

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
			stuStreamHash.put(stu.getUuid(),stu.getStreamUuid()); 
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedmiddle); 
		}
		
		List<ExamResult> classdistinctlist = performanceDAO.getStudentDistinctByClassId(school.getUuid(),classmuuid, systemConfig.getTerm(), systemConfig.getYear());
		
		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());

			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);
			populatePDFDocument(school,classmuuid,classdistinctlist,systemConfig,path,examType);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}


	private void populatePDFDocument(Account school, String classuuid,List<ExamResult> classdistinctlist,
			SystemConfig systemConfig, String realPath,String examType) {

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
			
			PdfPCell stdHeader = new PdfPCell(new Paragraph("STD",boldNewRoman7));
			stdHeader.setBackgroundColor(baseColor);
			stdHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell nameHeader = new PdfPCell(new Paragraph("NAME",boldNewRoman7));
			nameHeader.setBackgroundColor(baseColor);
			nameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell meanHeader = new PdfPCell(new Paragraph("MEAN",boldNewRoman7));
			meanHeader.setBackgroundColor(baseColor);
			meanHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell devHeader = new PdfPCell(new Paragraph("DEV",boldNewRoman7));
			devHeader.setBackgroundColor(baseColor);
			devHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell classPosHeader = new PdfPCell(new Paragraph("POS",boldNewRoman7));
			classPosHeader.setBackgroundColor(baseColor);
			classPosHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			//most improved
			
			PdfPCell MIcountHeader = new PdfPCell(new Paragraph("No",boldNewRoman7));
			MIcountHeader.setBackgroundColor(baseColor);
			MIcountHeader.setHorizontalAlignment(Element.ALIGN_LEFT);	

			PdfPCell MIadmnoHeader = new PdfPCell(new Paragraph("AdmNo",boldNewRoman7));
			MIadmnoHeader.setBackgroundColor(baseColor);
			MIadmnoHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell MIstdHeader = new PdfPCell(new Paragraph("STD",boldNewRoman7));
			MIstdHeader.setBackgroundColor(baseColor);
			MIstdHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell MInameHeader = new PdfPCell(new Paragraph("NAME",boldNewRoman7));
			MInameHeader.setBackgroundColor(baseColor);
			MInameHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell MIdevHeader = new PdfPCell(new Paragraph("DEV",boldNewRoman7));
			MIdevHeader.setBackgroundColor(baseColor);
			MIdevHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable subjectScoreTable = new PdfPTable(7); 
			PdfPTable mostImprovedTable = new PdfPTable(5); 

			subjectScoreTable.addCell(countHeader);
			subjectScoreTable.addCell(admnoHeader);
			subjectScoreTable.addCell(stdHeader);
			subjectScoreTable.addCell(nameHeader);
			subjectScoreTable.addCell(meanHeader);
			subjectScoreTable.addCell(devHeader);
			subjectScoreTable.addCell(classPosHeader);
			subjectScoreTable.setWidthPercentage(100); 
			subjectScoreTable.setWidths(new int[]{10,15,20,25,20,20,20});   
			subjectScoreTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			
			mostImprovedTable.addCell(MIcountHeader);
			mostImprovedTable.addCell(MIadmnoHeader);
			mostImprovedTable.addCell(MIstdHeader);
			mostImprovedTable.addCell(MInameHeader);
			mostImprovedTable.addCell(MIdevHeader);
			mostImprovedTable.setWidthPercentage(100); 
			mostImprovedTable.setWidths(new int[]{10,15,20,25,20});   
			mostImprovedTable.setHorizontalAlignment(Element.ALIGN_LEFT);


			String subject = "";

			Map<String,Double> examScoremap = new LinkedHashMap<String,Double>();
			Map<String,Double> mostImprovedMap = new LinkedHashMap<String,Double>();

			double classOTotal = 0,classMTotal = 0,classETotal = 0;
			double classOscore = 0,classMscore = 0,classEscore = 0;

			for(ExamResult studentDis : classdistinctlist){
				List<ExamResult> classperformancelist = performanceDAO.getStudentPerformanceByClassId(studentDis.getStudentUuid(),
						classuuid, systemConfig.getTerm(), systemConfig.getYear());

				for(ExamResult classscores : classperformancelist){

					subject = classscores.getSubjectUuid();

					if(StringUtils.equalsIgnoreCase(examType, "OPENER")){
						classOscore = classscores.getOpenner();
						classOTotal += computationEngine.computeOpener(subject,classOscore);
						examScoremap.put(studentDis.getStudentUuid(), classOTotal);
						mostImprovedMap.put(studentDis.getStudentUuid(), classOTotal);

					}if(StringUtils.equalsIgnoreCase(examType, "MIDTERM")){
						classMscore = classscores.getMidterm();
						classMTotal += computationEngine.computeMidterm(subject,classMscore); 
						examScoremap.put(studentDis.getStudentUuid(), classMTotal);
						mostImprovedMap.put(studentDis.getStudentUuid(), classMTotal);

					}if(StringUtils.equalsIgnoreCase(examType, "ENDTERM")){
						classEscore = classscores.getEndterm();
						classETotal += computationEngine.computeEndterm(subject,classEscore); 
						examScoremap.put(studentDis.getStudentUuid(), classETotal);
						mostImprovedMap.put(studentDis.getStudentUuid(), classETotal);
					}

				}

				classOTotal = 0;classMTotal  = 0;classETotal = 0;
				classOscore  = 0;classMscore  = 0;classEscore = 0;
				
			}
			
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
			
			
			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);	

			int finalcount = 1;
			//int studentcount = 0;
			String finalstudentuuid = "";
			double finaltotalmark  = 0;
			String thefinalposition = "";
			double finalmean = 0;
			double finalnumber = 0;
			int finalposition = 1;
			
			for(Object o : examList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				finalstudentuuid = item[0];
				finaltotalmark = Double.parseDouble(item[1]);
				finalmean = finaltotalmark/5; 
				

				if(finalmean==finalnumber){
					thefinalposition = (" " +(finalcount-finalposition++)+ " " );//+ streamStudentTotal
		
				}else{
					finalposition=1;
					thefinalposition = (" " +finalcount+ "  " );//+ streamStudentTotal
					
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
				
				double deviation = 0;
				deviation = deviationFinder(thistermmean,lasttermmean); 

				DecimalFormat rf = new DecimalFormat("0"); 
				rf.setRoundingMode(RoundingMode.HALF_UP);
				
				//if(deviation !=0){
				subjectScoreTable.addCell(new Paragraph(finalcount+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(studentAdmNoHash.get(finalstudentuuid)+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(studNameHash.get(finalstudentuuid)+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(streamHash.get(stuStreamHash.get(finalstudentuuid))+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(df.format(finalmean)+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(deviation+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(thefinalposition+" ",normalNewRoman7));
			   //  }
			
			    if(finalcount > 9){
			    	break;
			    }
				
				finalcount++;
				//studentcount++;
				finalnumber=finalmean;

			}
			

			@SuppressWarnings("unchecked")
			ArrayList<?> mostImprovedList = new ArrayList(mostImprovedMap.entrySet());
			Collections.sort(mostImprovedList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});


			
			Map<String,Double> ImprovedMap = new LinkedHashMap<String,Double>();
			//loop to find the student
			if(mostImprovedList!=null){
			for(Object o2 : mostImprovedList){

				String items = String.valueOf(o2);
				String [] item = items.split("=");
				String studentuuid = item[0];
				
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
				if(meanScoreDAO.getMeanScore(studentuuid,Integer.toString(lastterm),Integer.toString(lastyear)) !=null){
					lasttermscore = meanScoreDAO.getMeanScore(studentuuid,Integer.toString(lastterm),Integer.toString(lastyear));
				}
				if(lasttermscore !=null){
					lasttermmean = lasttermscore.getMeanScore();
				}
				//this term mean
				MeanScore thistermscore = new MeanScore();
				if(meanScoreDAO.getMeanScore(studentuuid,systemConfig.getTerm(),systemConfig.getYear()) !=null){
					thistermscore = meanScoreDAO.getMeanScore(studentuuid,systemConfig.getTerm(),systemConfig.getYear());
				}
				if(lasttermscore !=null){
					thistermmean = thistermscore.getMeanScore();
				}
				
				double deviation = 0;
				deviation = deviationFinder(thistermmean,lasttermmean); 
				ImprovedMap.put(studentuuid, deviation);
				
			 }
			}
			
			//sort improved
			@SuppressWarnings("unchecked")
			ArrayList<?> improvedList = new ArrayList(ImprovedMap.entrySet());
			Collections.sort(improvedList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});
			
			
			int devcount = 1;
			if(improvedList !=null){
			for(Object o23 : improvedList){
				String items = String.valueOf(o23);
				String [] item = items.split("=");
				String studeuuid = item[0];
				String deviation = item[1];
				double devtn = 0;
				
				devtn = Double.parseDouble(deviation);
				
				if(devtn != 0){
				mostImprovedTable.addCell(new Paragraph(devcount+" ",normalNewRoman7));
				mostImprovedTable.addCell(new Paragraph(studentAdmNoHash.get(studeuuid)+" ",normalNewRoman7));
				mostImprovedTable.addCell(new Paragraph(studNameHash.get(studeuuid)+" ",normalNewRoman7));
				subjectScoreTable.addCell(new Paragraph(streamHash.get(stuStreamHash.get(studeuuid))+" ",normalNewRoman7));
				mostImprovedTable.addCell(new Paragraph(df.format(deviation)+" ",normalNewRoman7));
				}
				
				
				if(devcount ==5 ){
					break;
				}
				
				devcount++;
			 }
			}
			
			
			
			Paragraph emptyline = new Paragraph(("                              "));
			document.add(emptyline);
			document.add(emptyline);
			document.add(subjectScoreTable);
			document.add(emptyline);
			document.add(mostImprovedTable);
			

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

	private double deviationFinder(double thisTermMean, double lastTermMean){
		DecimalFormat df = new DecimalFormat("0.00"); 
		df.setRoundingMode(RoundingMode.DOWN);	

		double deviation = 0;
		if(lastTermMean == 0){
			deviation = 0;
		}else{
			deviation = thisTermMean - lastTermMean;
		}
		if(deviation != 0){
			deviation = Double.parseDouble(df.format(deviation));
		}else{
			deviation = 0;
		}

		return deviation;
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
