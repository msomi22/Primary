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

import ke.co.fastech.primaryschool.bean.exam.BarWeight;
import ke.co.fastech.primaryschool.bean.exam.ExamResult;
import ke.co.fastech.primaryschool.bean.exam.GradingSystem;
import ke.co.fastech.primaryschool.bean.exam.MeanScore;
import ke.co.fastech.primaryschool.bean.school.Classroom;
import ke.co.fastech.primaryschool.bean.school.Comment;
import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.staff.ClassTeacher;
import ke.co.fastech.primaryschool.bean.staff.Staff;
import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.bean.student.Subject;
import ke.co.fastech.primaryschool.persistence.exam.BarWeightDAO;
import ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO;
import ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO;
import ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO;
import ke.co.fastech.primaryschool.persistence.school.ClassroomDAO;
import ke.co.fastech.primaryschool.persistence.school.CommentDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.staff.StaffDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;
import ke.co.fastech.primaryschool.server.cache.CacheVariables;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ComputationEngine;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


/**
 * @author peter
 *
 */
public class ReportCard extends HttpServlet{

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
	private Comment comment;

	private String USER= "";
	private String path ="";

	private Cache accountCache;
	private Logger logger;

	private String schoolname = "";
	private String title = "";

	private static SystemConfigDAO systemConfigDAO;
	private static GradingSystemDAO gradingSystemDAO;
	private static TeacherSubjectDAO teacherSubjectDAO;
	private static PerformanceDAO performanceDAO;
	private static CommentDAO commentDAO;
	private static TeacherClassDAO teacherClassDAO;
	private static MeanScoreDAO meanScoreDAO;
	private static BarWeightDAO barWeightDAO;
	private static StudentDAO studentDAO;
	private static StaffDAO staffDAO;
	private static ClassroomDAO classroomDAO;
	private static StreamDAO streamDAO;
	private static SubjectDAO subjectDAO;
	private ComputationEngine computationEngine;

	private HashMap<String, String> studentAdmNoHash = new HashMap<String, String>();
	private HashMap<String, String> studNameHash = new HashMap<String, String>();
	private HashMap<String, String> firstnameHash = new HashMap<String, String>();
	private HashMap<String, String> streamHash = new HashMap<String, String>();
	HashMap<String, Integer> admYearMap = new HashMap<String, Integer>(); 
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
		commentDAO = CommentDAO.getInstance();
		teacherClassDAO = TeacherClassDAO.getInstance();
		meanScoreDAO = MeanScoreDAO.getInstance();
		barWeightDAO = BarWeightDAO.getInstance();
		classroomDAO = ClassroomDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		teacherSubjectDAO = TeacherSubjectDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		USER = System.getProperty("user.name");
		path = "/home/"+USER+"/school/logo/logo.png";
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/pdf");

		String accountuuid = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4";
		String streamuuid = "2B0F8F79-DEF2-419D-9A76-17450B5CF768";

		/*accountuuid =request.getParameter("accountuuid");
		 streamuuid = request.getParameter("streamuuid");*/

		Account school = new Account();

		net.sf.ehcache.Element element;
		if ((element = accountCache.get(accountuuid)) != null) {
			school = (Account) element.getObjectValue();
		}

		schoolname = school.getSchoolName().toUpperCase()+"\n";
		PDF_SUBTITLE =  "P.O BOX "+school.getSchoolAddres()+"\n" 
				+ ""+school.getSchoolHomeTown()+" - Kenya\n" 
				+ "" + school.getSchoolPhone()+"\n"
				+ "" + school.getSchoolEmail()+"\n"
				+ "" + school.getSchoolMotto()+"\n";

		title = "_____________________________________ \n"
				+ " End of Term Report Card "+"\n";

		SystemConfig systemConfig = systemConfigDAO.getSystemConfig(school.getUuid()); 
		Stream stream = streamDAO.getStreamById(streamuuid); 

		systemConfig.getTerm();
		systemConfig.getYear();
		if(commentDAO.getComment(school.getUuid()) !=null){
			comment = commentDAO.getComment(school.getUuid());
		}

		streamHash.put(streamuuid, stream.getStreamName());
		String classuuid = "";
		gradingSystem = gradingSystemDAO.getGradingSystem(school.getUuid()); 

		List<Classroom>  list = classroomDAO.getClassroomList();
		for(Classroom room : list){
			if(StringUtils.contains(stream.getStreamName(), room.getClassName())){
				classuuid = room.getUuid();
			}
		}

		List<Student> studentList  = new ArrayList<Student>(); 
		studentList = studentDAO.getStudentsList();
		for(Student stu : studentList){
			studentAdmNoHash.put(stu.getUuid(),stu.getAdmmissinNo()); 

			String formatedFirstname = StringUtils.capitalize(stu.getFirstname().toLowerCase());
			String formatedmiddle = StringUtils.capitalize(stu.getMiddlename().toLowerCase());
			String formatedlastname = StringUtils.capitalize(stu.getLastname().toLowerCase());

			formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedmiddle = formatedmiddle.substring(0, Math.min(formatedmiddle.length(), 10));
			formatedlastname = formatedlastname.substring(0, Math.min(formatedlastname.length(), 10));
			admYearMap.put(stu.getUuid(), stu.getRegYear()); 
			studNameHash.put(stu.getUuid(),formatedFirstname + " " + formatedmiddle + " " + formatedlastname +"\n"); 
			firstnameHash.put(stu.getUuid(), formatedFirstname);
		}


		List<ExamResult> classdistinctlist = performanceDAO.getStudentDistinctByClassId(classuuid, systemConfig.getTerm(), systemConfig.getYear());
		List<ExamResult> streamdistinctlist = performanceDAO.getStudentDistinctByStreamId(streamuuid, systemConfig.getTerm(), systemConfig.getYear());

		document = new Document(PageSize.A4, 46, 46, 64, 64);

		try {
			writer = PdfWriter.getInstance(document, response.getOutputStream());

			PdfUtil event = new PdfUtil();
			writer.setBoxSize("art", new Rectangle(46, 64, 559, 788));
			writer.setPageEvent(event);
			populatePDFDocument(school,classuuid,streamuuid,classdistinctlist,streamdistinctlist,systemConfig,path);

		} catch (DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}



		/*
		OutputStream out = response.getOutputStream();
		//response.setContentType("application/json;charset=UTF-8");
		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();

		out.write(gson.toJson(populatePDFDocument(school,classuuid,streamuuid,classdistinctlist,streamdistinctlist,systemConfig,path)).getBytes());
		out.flush();
		out.close();*/

	}


	private void populatePDFDocument(Account school, String classuuid, String streamuuid,List<ExamResult> classdistinctlist,
			List<ExamResult> streamdistinctlist, SystemConfig systemConfig, String realPath) {

		try {

			document.open();

			PdfPTable prefaceTable = new PdfPTable(2);  
			prefaceTable.setWidthPercentage(100); 
			prefaceTable.setWidths(new int[]{70,130}); 

			Paragraph content = new Paragraph();
			content.add(new Paragraph((schoolname +"") , boldCourierText14));
			content.add(new Paragraph((PDF_SUBTITLE +"") , boldCourierText10));
			content.add(new Paragraph((title) , boldNewRoman8));

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

			double total = 0;
			String subject = "";
			double OMEtotal,openerScore,midtermScore,endtermScore = 0;

			Map<String,Double> openermap = new LinkedHashMap<String,Double>();
			Map<String,Double> midtermmap = new LinkedHashMap<String,Double>();
			Map<String,Double> endtermmap = new LinkedHashMap<String,Double>();
			Map<String,String> endTermclassPosmmap = new LinkedHashMap<String,String>();
			Map<String,Integer> endTermclassPosmmap2 = new LinkedHashMap<String,Integer>();
			int classStudentCount = 0;
			int classStudentTotal = 0; 

			double openerTotal = 0;
			double midtermTotal = 0;
			double endtermTotal = 0;

			for(ExamResult studentDis : classdistinctlist){
				List<ExamResult> classperformancelist = performanceDAO.getStudentPerformanceByClassId(studentDis.getStudentUuid(),
						classuuid, systemConfig.getTerm(), systemConfig.getYear());

				for(ExamResult classscores : classperformancelist){

					openerScore = classscores.getOpenner();
					midtermScore = classscores.getMidterm();
					endtermScore = classscores.getEndterm();

					OMEtotal = openerScore + midtermScore + endtermScore;
					subject = classscores.getSubjectUuid();

					openerTotal += computationEngine.computeOpener(subject,openerScore);
					openermap.put(studentDis.getStudentUuid(), openerTotal);

					midtermTotal += computationEngine.computeMidterm(subject,midtermScore); 
					midtermmap.put(studentDis.getStudentUuid(), midtermTotal);

					endtermTotal += computationEngine.computeEndterm(subject,endtermScore); 
					endtermmap.put(studentDis.getStudentUuid(), endtermTotal);

				}

				openerTotal = 0;
				midtermTotal = 0;
				endtermTotal = 0;


				classStudentCount++;
			}


			classStudentTotal = classStudentCount;

			/** Class result Sorting begins
			 * Opener start ***********************************************************************************************************************************
			 *                                                                   PETER MWENDA 
			 * */

			Map<String,String> openerCPOSmap = new LinkedHashMap<String,String>(); // C class
			Map<String,String> midterCPOSmmap = new LinkedHashMap<String,String>();


			@SuppressWarnings("unchecked")
			ArrayList<?> openerList = new ArrayList(openermap.entrySet());
			Collections.sort(openerList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int OCSCount = 1;// OPENER CLASS STUDENT COUNT MCSC
			String Ostudentuuid = "";
			String Ototalmarks  = "";
			String Opos = "";
			int Oposition = 1;
			double Omean = 0;
			double Onumber = 0.0;
			for(Object o : openerList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				Ostudentuuid = item[0];
				Ototalmarks = item[1];

				Omean = Double.parseDouble(Ototalmarks)/5;
				if(Omean==Onumber){
					Opos = (" " +(OCSCount-Oposition++)+ " / " + classStudentTotal);
				}else{
					Oposition=1;
					Opos = (" " +OCSCount+ " / " + classStudentTotal);
				}
				openerCPOSmap.put(Ostudentuuid, Opos);

				OCSCount++;
				Onumber=Omean;



			}
			/** opener End ***********************************************************************************************************************************************/


			/**     PETER MWENDA */

			/** Mid term start **************************************************************************************************************************************/
			@SuppressWarnings("unchecked")
			ArrayList<?> midtermList = new ArrayList(midtermmap.entrySet());
			Collections.sort(midtermList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int MCSCount = 1;// MIDTERM CLASS STUDENT COUNT MCSC
			String Mstudentuuid = "";
			String Mtotalmarks  = "";
			String Mpos = "";
			double Mmean = 0;
			double Mnumber = 0;
			int Mposition = 1;
			for(Object o : midtermList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				Mstudentuuid = item[0];
				Mtotalmarks = item[1];

				Mmean = Double.parseDouble(Mtotalmarks)/5;
				if(Mmean==Mnumber){
					Mpos = (" " +(MCSCount-Mposition++)+ " / " + classStudentTotal);
				}else{
					Mposition=1;
					Mpos = (" " +MCSCount+ " / " + classStudentTotal);
				}
				midterCPOSmmap.put(Mstudentuuid, Mpos);

				MCSCount++;
				Mnumber=Mmean;

			}
			/** Mid term End ****************************************************************************************************************************************/

			/**     PETER MWENDA */


			/** End term start ***********************************************************************************************************************************/
			@SuppressWarnings("unchecked")
			ArrayList<?> endtermList = new ArrayList(endtermmap.entrySet());
			Collections.sort(endtermList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int ETCSCount = 1;//END TERM  CLASS STUDENT COUNT ETSC
			String Estudentuuid = "";// E = END TERM
			String Etotalmarks  = "";
			String Epos = "";
			double Emean = 0;
			double Enumber = 0;
			int Eposition = 1;
			int classPosition1 =  0;
			for(Object o : endtermList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				Estudentuuid = item[0];
				Etotalmarks = item[1];

				Emean = Double.parseDouble(Etotalmarks)/5;
				if(Emean==Enumber){
					Epos = (" " +(ETCSCount-Eposition++)+ " / " + classStudentTotal);
					classPosition1 = (ETCSCount-Eposition++);
				}else{
					Eposition=1;
					Epos = (" " +ETCSCount+ " / " + classStudentTotal);
					classPosition1 = ETCSCount;
					endTermclassPosmmap.put(Estudentuuid, Epos); 
					endTermclassPosmmap2.put(Estudentuuid, classPosition1);
				}

				//save mean and positions
				double weight = 0;
				weight = (Emean/100)*12; 
				meanScoreDAO.putMeanScore(Emean, 0, classPosition1, Estudentuuid, systemConfig.getTerm(), systemConfig.getYear());
				barWeightDAO.putWeight(weight, Estudentuuid, systemConfig.getTerm(), systemConfig.getYear());


				ETCSCount++;
				Enumber=Emean;

			}


			/** End term End 
			 * Class sorting ends
			 * ************************************************************************************************************************************************
			 *                                                          PETER MWENDA 
			 * */
			/** end for(ExamResult stuscore : classdistinctlist){ */




			/**  STREAM PERFORMANCE BEGINS




			/** *************************************************************************************************************************************************                    
			 * Stream performance 
			 * 
			 * Here we print the report card
			 */




			DecimalFormat df = new DecimalFormat("0.00"); 
			df.setRoundingMode(RoundingMode.DOWN);	


			Map<String,Double> streamopenermap = new LinkedHashMap<String,Double>();
			Map<String,Double> streammidtermmap = new LinkedHashMap<String,Double>();
			Map<String,Double> streamendtermmap = new LinkedHashMap<String,Double>();

			Map<String,String> SOPmap = new LinkedHashMap<String,String>();// Stream Opener Position SOP
			Map<String,String> SMPmap = new LinkedHashMap<String,String>();// Stream Mid-Term Position SMP

			Map<String,String> SOTmap = new LinkedHashMap<String,String>();// Stream Opener Total SOT
			Map<String,String> SMTmap = new LinkedHashMap<String,String>();// Stream Mid-Term Total SMT

			int streamStudentCount = 0;
			int streamStudentTotal = 0; 
			double SopenerScore = 0;
			double SopenerTotal = 0,SmidtermScore = 0;
			double SmidtermTotal = 0,SendtermScore = 0;
			double SendtermTotal = 0;
			String Ssubject = "";


			for(ExamResult studentDis : streamdistinctlist){
				List<ExamResult> streamperformancelist = performanceDAO.getStudentPerformanceByStreamId(studentDis.getStudentUuid(),
						streamuuid, systemConfig.getTerm(), systemConfig.getYear());


				for(ExamResult streamscores : streamperformancelist){

					SopenerScore = streamscores.getOpenner();
					SmidtermScore = streamscores.getMidterm();
					SendtermScore = streamscores.getEndterm();

					Ssubject = streamscores.getSubjectUuid();

					SopenerTotal += computationEngine.computeOpener(Ssubject,SopenerScore); 
					streamopenermap.put(studentDis.getStudentUuid(), SopenerTotal);

					SmidtermTotal += computationEngine.computeMidterm(Ssubject,SmidtermScore); 
					streammidtermmap.put(studentDis.getStudentUuid(), SmidtermTotal);

					SendtermTotal += computationEngine.computeEndterm(Ssubject,SendtermScore); 
					streamendtermmap.put(studentDis.getStudentUuid(), SendtermTotal);

				}

				SopenerTotal = 0;
				SmidtermTotal = 0;
				SendtermTotal = 0;

				streamStudentCount ++;



			}
			/** end for(ExamResult studentDis : streamdistinctlist){ */


			streamStudentTotal = streamStudentCount;





			/** Stream result Sorting begins
			 * Opener start ***********************************************************************************************************************************
			 *                                                                   PETER MWENDA 
			 * */
			@SuppressWarnings("unchecked")
			ArrayList<?> openerStreamList = new ArrayList(streamopenermap.entrySet());
			Collections.sort(openerStreamList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int OSSCount = 1;// OPENER STREAM STUDENT COUNT MCSC
			String OSstudentuuid = "";
			double OStotalmarks  = 0;
			String OSpos = "";
			int OSposition = 1;
			double OSmean = 0;
			double OSnumber = 0.0;
			for(Object o : openerStreamList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				OSstudentuuid = item[0];
				OStotalmarks = Double.parseDouble(item[1]);

				OSmean = OStotalmarks/5;
				if(OSmean==OSnumber){
					OSpos = (" " +(OSSCount-OSposition++)+ " / " + streamStudentTotal);
				}else{
					OSposition=1;
					OSpos = (" " +OSSCount+ " / " + streamStudentTotal);
				}

				SOPmap.put(OSstudentuuid, OSpos);
				SOTmap.put(OSstudentuuid, " " + df.format(OStotalmarks) + " / 500 ");

				OSSCount++;
				OSnumber=OSmean;

			}
			/** opener End ***********************************************************************************************************************************************/


			/**     PETER MWENDA */

			/** Mid term start **************************************************************************************************************************************/
			@SuppressWarnings("unchecked")
			ArrayList<?> midtermStreamList = new ArrayList(streammidtermmap.entrySet());
			Collections.sort(midtermStreamList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});

			int MSSCount = 1;// MIDTERM STREAM STUDENT COUNT MCSC
			String MSstudentuuid = "";
			double MStotalmarks  = 0;
			String MSpos = "";
			double MSmean = 0;
			double MSnumber = 0;
			int MSposition = 1;
			for(Object o : midtermStreamList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				MSstudentuuid = item[0];
				MStotalmarks = Double.parseDouble(item[1]);

				MSmean = MStotalmarks/5;
				if(MSmean==MSnumber){
					MSpos = (" " +(MSSCount-MSposition++)+ " / " + streamStudentTotal);
				}else{
					MSposition=1;
					MSpos = (" " +MSSCount+ " / " + streamStudentTotal);
				}

				SMPmap.put(MSstudentuuid, MSpos);
				SMTmap.put(MSstudentuuid, " " + df.format(MStotalmarks) + " / 500");

				MSSCount++;
				MSnumber=MSmean;

			}
			/** Mid term End ****************************************************************************************************************************************/

			/**     PETER MWENDA */


			/** End term start ***********************************************************************************************************************************/
			@SuppressWarnings("unchecked")
			ArrayList<?> endtermStreamList = new ArrayList(streamendtermmap.entrySet());
			Collections.sort(endtermStreamList,new Comparator(){
				public int compare(Object object1,Object object2){
					Map.Entry entry1 = (Map.Entry)object1;
					Map.Entry entry2 = (Map.Entry)object2;
					Double first = (Double)entry1.getValue();
					Double second = (Double)entry2.getValue();
					return second.compareTo(first);
				}
			});




			int ETSSCount = 1;//END TERM  STREAM STUDENT COUNT ETSC
			String ESstudentuuid = "";// E = END TERM
			double EStotalmarks  = 0;
			String ESpos = "";
			double ESmean = 0;
			double ESnumber = 0;
			int ESposition = 1;
			int streamPosition = 0;
			int classPosition = 0;
			for(Object o : endtermStreamList){
				String items = String.valueOf(o);
				String [] item = items.split("=");
				ESstudentuuid = item[0];
				EStotalmarks = Double.parseDouble(item[1]);

				ESmean = EStotalmarks/5; 
				if(ESmean==ESnumber){
					ESpos = (" " +(ETSSCount-ESposition++)+ " / " + streamStudentTotal);
					streamPosition = (ETSSCount-ESposition++);
				}else{
					ESposition=1;
					ESpos = (" " +ETSSCount+ " / " + streamStudentTotal);
					streamPosition = ETSSCount;
				}

				//save mean and positions
				double weight = 0;
				weight = (ESmean/100)*12; 
				if(endTermclassPosmmap2.get(ESstudentuuid)!=null){
					classPosition = endTermclassPosmmap2.get(ESstudentuuid); 
				}
				meanScoreDAO.putMeanScore(ESmean, streamPosition, classPosition, ESstudentuuid, systemConfig.getTerm(), systemConfig.getYear());
				barWeightDAO.putWeight(weight, ESstudentuuid, systemConfig.getTerm(), systemConfig.getYear());

				String classteachercomment = classteacherComment(ESstudentuuid,systemConfig,streamuuid); 
				//class teacher comment table start
				PdfPTable classTeacherCommentTable = new PdfPTable(2);  
				classTeacherCommentTable.setWidthPercentage(100); 
				classTeacherCommentTable.setWidths(new int[]{100,100}); 
				classTeacherCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				String CTcommentLabel = "CLASS TEACHER'S COMMENT \n\n"; 
				Paragraph CTcommentLb = new Paragraph(CTcommentLabel,normalNewRoman7); 
				PdfPCell CTcommentCell = new PdfPCell(new Paragraph("Hi " + firstnameHash.get(ESstudentuuid) +", "+ classteachercomment + " \n",normalNewRoman7));
				CTcommentCell.setBackgroundColor(baseColor);
				CTcommentCell.setColspan(2); 
				CTcommentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell CTsignature = new PdfPCell(new Paragraph("Class teacher's Signature: _______________\n\n",normalNewRoman7));
				CTsignature.setBackgroundColor(baseColor);
				CTsignature.setHorizontalAlignment(Element.ALIGN_LEFT); 

				PdfPCell CTsignaturedate2 = new PdfPCell(new Paragraph("Date : _____________________    \n\n",normalNewRoman7));
				CTsignaturedate2.setBackgroundColor(baseColor);
				CTsignaturedate2.setHorizontalAlignment(Element.ALIGN_LEFT);

				classTeacherCommentTable.addCell(CTcommentCell);
				classTeacherCommentTable.addCell(CTsignature);
				classTeacherCommentTable.addCell(CTsignaturedate2);

				//principal comment table start
				PdfPTable principalCommentTable = new PdfPTable(2);  
				principalCommentTable.setWidthPercentage(100); 
				principalCommentTable.setWidths(new int[]{100,100}); 
				principalCommentTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				String commentLabel = "HEADTEACHER'S COMMENT \n\n";
				Paragraph commentLb = new Paragraph(commentLabel,normalNewRoman7);


				PdfPCell commentCell = new PdfPCell(new Paragraph("Thank you " + firstnameHash.get(ESstudentuuid) +" "+ comment.getHeadTeacherCom() +" "+PrincipalRemarks(ESmean) +"\n",normalNewRoman7));
				commentCell.setBackgroundColor(baseColor);
				commentCell.setColspan(2); 
				commentCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell Cell1 = new PdfPCell(new Paragraph("Headteacher Signature: _______________   " + "\n\n",normalNewRoman7));
				Cell1.setBackgroundColor(baseColor);
				Cell1.setHorizontalAlignment(Element.ALIGN_LEFT); 

				PdfPCell Cell2 = new PdfPCell(new Paragraph("            Rubber Stamp \n\n",normalNewRoman7));
				Cell2.setBackgroundColor(baseColor);
				Cell2.setHorizontalAlignment(Element.ALIGN_LEFT);

				principalCommentTable.addCell(commentCell);
				principalCommentTable.addCell(Cell1);
				principalCommentTable.addCell(Cell2);
				//principal comment table end

				PdfPTable feeTable = new PdfPTable(2);  
				feeTable.setWidthPercentage(100); 
				feeTable.setWidths(new int[]{100,100}); 
				feeTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell feeCell = new PdfPCell(new Paragraph("Closing Fee Balance  " + "XXX" +" \n\nNext Term Fee = "  + "XXX" ,normalNewRoman7));
				feeCell.setBackgroundColor(baseColor);
				feeCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell DateCell = new PdfPCell(new Paragraph(("Closing date : " + systemConfig.getClosingDate() +" \n\nNext Term Opening date :" + systemConfig.getOpenningDate())+"\n",normalNewRoman7));
				DateCell.setBackgroundColor(baseColor);
				DateCell.setHorizontalAlignment(Element.ALIGN_LEFT);

				feeTable.addCell(feeCell);
				feeTable.addCell(DateCell);



				PdfPCell contheader = new PdfPCell(new Paragraph(("TERM " +systemConfig.getTerm() + ": YEAR " + systemConfig.getYear() +"\n\n" +("STD : " + streamHash.get(streamuuid) +"\n")) +"",normalNewRoman7));
				contheader.setBackgroundColor(baseColor);
				contheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell bodyheader = new PdfPCell(new Paragraph(("ADMISSION NUMBER : " + studentAdmNoHash.get(ESstudentuuid) +"\n\n" + ( "STUDENT NAME : " + studNameHash.get(ESstudentuuid) +"\n")),normalNewRoman7));
				bodyheader.setBackgroundColor(baseColor);
				bodyheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPTable headerTable = new PdfPTable(2);  
				headerTable.setWidthPercentage(100); 
				headerTable.setWidths(new int[]{100,100}); 
				headerTable.setHorizontalAlignment(Element.ALIGN_LEFT);


				headerTable.addCell(bodyheader);
				headerTable.addCell(contheader);



				PdfPCell countHeader = new PdfPCell(new Paragraph("*",boldNewRoman7));
				countHeader.setBackgroundColor(baseColor);
				countHeader.setHorizontalAlignment(Element.ALIGN_LEFT);	

				PdfPCell subjectHeader = new PdfPCell(new Paragraph("SUBJECT",boldNewRoman7));
				subjectHeader.setBackgroundColor(baseColor);
				subjectHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				//opener
				PdfPCell openerHeader = new PdfPCell(new Paragraph("OPENER / 100",boldNewRoman7));
				openerHeader.setBackgroundColor(baseColor);
				openerHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell openerGradeHeader = new PdfPCell(new Paragraph("GRADE",boldNewRoman7));
				openerGradeHeader.setBackgroundColor(baseColor);
				openerGradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				//mid term
				PdfPCell midtermHeader = new PdfPCell(new Paragraph("MID-TERM / 100",boldNewRoman7));
				midtermHeader.setBackgroundColor(baseColor);
				midtermHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell midtermGradeHeader = new PdfPCell(new Paragraph("GRADE",boldNewRoman7));
				midtermGradeHeader.setBackgroundColor(baseColor);
				midtermGradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				//end term
				PdfPCell endtermHeader = new PdfPCell(new Paragraph("END-TERM / 100",boldNewRoman7));
				endtermHeader.setBackgroundColor(baseColor);
				endtermHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell endtermGradeHeader = new PdfPCell(new Paragraph("GRADE",boldNewRoman7));
				endtermGradeHeader.setBackgroundColor(baseColor);
				endtermGradeHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell subTeacherHeader = new PdfPCell(new Paragraph("TEACHER",boldNewRoman7));
				subTeacherHeader.setBackgroundColor(baseColor);
				subTeacherHeader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPTable subjectScoreTable = new PdfPTable(9);  
				subjectScoreTable.addCell(countHeader);
				subjectScoreTable.addCell(subjectHeader);
				subjectScoreTable.addCell(openerHeader);
				subjectScoreTable.addCell(openerGradeHeader);
				subjectScoreTable.addCell(midtermHeader);
				subjectScoreTable.addCell(midtermGradeHeader);
				subjectScoreTable.addCell(endtermHeader);
				subjectScoreTable.addCell(endtermGradeHeader);
				subjectScoreTable.addCell(subTeacherHeader);
				subjectScoreTable.setWidthPercentage(100); 
				subjectScoreTable.setWidths(new int[]{10,26,40,18,40,18,40,18,24});   
				subjectScoreTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell openerheader = new PdfPCell(new Paragraph("OPENER EXAM ANALYSIS \n\n"
						+ "Stream Pos:  " + SOPmap.get(ESstudentuuid) + " \t\t Class Pos:  " + openerCPOSmap.get(ESstudentuuid) + " \n" 
						+ "Total Marks:  " + SOTmap.get(ESstudentuuid) + "  \n" , normalNewRoman7));
				openerheader.setBackgroundColor(baseColor);
				openerheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell midTermheader = new PdfPCell(new Paragraph("MID-TERM EXAM ANALYSIS \n\n"
						+ "Stream Pos:  " + SMPmap.get(ESstudentuuid) + " \t\t Class Pos:  " + midterCPOSmmap.get(ESstudentuuid) + " \n" 
						+ "Total Marks:  " + SMTmap.get(ESstudentuuid) + "  \n" , normalNewRoman7));
				midTermheader.setBackgroundColor(baseColor);
				midTermheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell endTermheader = new PdfPCell(new Paragraph("END-TERM EXAM ANALYSIS \n\n"
						+ "Stream Pos:  " + ESpos + " \t\t Class Pos:  " + endTermclassPosmmap.get(ESstudentuuid) + " \n" 
						+ "Total Marks:  " + df.format(EStotalmarks) + " / 500    \n" , normalNewRoman7));
				endTermheader.setBackgroundColor(baseColor);
				endTermheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPTable resultTable = new PdfPTable(3);   
				resultTable.setWidthPercentage(100); 
				resultTable.setWidths(new int[]{100,100,100}); 
				resultTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				resultTable.addCell(openerheader);
				resultTable.addCell(midTermheader); 
				resultTable.addCell(endTermheader); 


				int engOpenerscore = 0,kisOpenerscore = 0,mathOpenerscore = 0,sciOpenerscore = 0;
				double sstOpenerscore = 0,creOpenerscore = 0;

				int engMidtermscore = 0,kisMidtermscore = 0,mathMidtermscore = 0,sciMidtermscore = 0;
				double sstMidtermscore = 0,creMidtermscore = 0;

				int engEndtermscore = 0,kisEndtermscore = 0,mathEndtermscore = 0,sciEndtermscore = 0;
				double sstEndtermscore = 0,creEndtermscore = 0;

				List<ExamResult> streamperformancelist = performanceDAO.getStudentPerformanceByStreamId(ESstudentuuid,
						streamuuid, systemConfig.getTerm(), systemConfig.getYear());

				for(ExamResult score : streamperformancelist){

					if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_ENG)) {
						engOpenerscore = (int)score.getOpenner();
						engMidtermscore = (int)score.getMidterm();
						engEndtermscore = (int)score.getEndterm();
					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_KIS)) {
						kisOpenerscore = (int)score.getOpenner();
						kisMidtermscore = (int)score.getMidterm();
						kisEndtermscore = (int)score.getEndterm();
					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_MATH)) {
						mathOpenerscore = (int)score.getOpenner();
						mathMidtermscore = (int)score.getMidterm();
						mathEndtermscore = (int)score.getEndterm();
					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_SCI)) {
						sciOpenerscore = (int)score.getOpenner();
						sciMidtermscore = (int)score.getMidterm();
						sciEndtermscore = (int)score.getEndterm();
					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_CRE)) {
						creOpenerscore = (int)score.getOpenner();
						creMidtermscore = (int)score.getMidterm();
						creEndtermscore = (int)score.getEndterm();
					}if(StringUtils.equals(score.getSubjectUuid(), ExamConstants.SUB_SST)) {
						sstOpenerscore = score.getOpenner();
						sstMidtermscore = score.getMidterm();
						sstEndtermscore = score.getEndterm();
					}
				}


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


				PdfPCell gradeA = new PdfPCell(new Paragraph(maxscore+"-"+(gradingSystem.getGradeAplain()),boldNewRoman7));
				gradeA.setBackgroundColor(baseColor);
				gradeA.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeAminus = new PdfPCell(new Paragraph(aplain+"-"+(gradingSystem.getGradeAminus()),boldNewRoman7));
				gradeAminus.setBackgroundColor(baseColor);
				gradeAminus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeBplus = new PdfPCell(new Paragraph(aminus+"-"+(gradingSystem.getGradeBplus()),boldNewRoman7));
				gradeBplus.setBackgroundColor(baseColor);
				gradeBplus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeBplain = new PdfPCell(new Paragraph(bplus+"-"+(gradingSystem.getGradeBplain()),boldNewRoman7));
				gradeBplain.setBackgroundColor(baseColor);
				gradeBplain.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeBminus = new PdfPCell(new Paragraph(bplain+"-"+(gradingSystem.getGradeBminus()),boldNewRoman7));
				gradeBminus.setBackgroundColor(baseColor);
				gradeBminus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeCplus = new PdfPCell(new Paragraph(bminus+"-"+(gradingSystem.getGradeCplus()),boldNewRoman7));
				gradeCplus.setBackgroundColor(baseColor);
				gradeCplus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeCplain = new PdfPCell(new Paragraph(cplus+"-"+(gradingSystem.getGradeCplain()),boldNewRoman7));
				gradeCplain.setBackgroundColor(baseColor);
				gradeCplain.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeCminus = new PdfPCell(new Paragraph(cplain+"-"+(gradingSystem.getGradeCminus()),boldNewRoman7));
				gradeCminus.setBackgroundColor(baseColor);
				gradeCminus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeDplus = new PdfPCell(new Paragraph(cminus+"-"+(gradingSystem.getGradeDplus()),boldNewRoman7));
				gradeDplus.setBackgroundColor(baseColor);
				gradeDplus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeDplain = new PdfPCell(new Paragraph(dplus+"-"+(gradingSystem.getGradeDplain()),boldNewRoman7));
				gradeDplain.setBackgroundColor(baseColor);
				gradeDplain.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeDminus = new PdfPCell(new Paragraph(dplain+"-"+(gradingSystem.getGradeDminus()),boldNewRoman7));
				gradeDminus.setBackgroundColor(baseColor);
				gradeDminus.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell gradeEE = new PdfPCell(new Paragraph(dminus+"-"+gradeE,boldNewRoman7));
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

				gradeTable.addCell(new Paragraph("A",normalNewRoman7));
				gradeTable.addCell(new Paragraph("A-",normalNewRoman7));
				gradeTable.addCell(new Paragraph("B+",normalNewRoman7));
				gradeTable.addCell(new Paragraph("B",normalNewRoman7));
				gradeTable.addCell(new Paragraph("B-",normalNewRoman7));
				gradeTable.addCell(new Paragraph("C+",normalNewRoman7));
				gradeTable.addCell(new Paragraph("C",normalNewRoman7));
				gradeTable.addCell(new Paragraph("C-",normalNewRoman7));
				gradeTable.addCell(new Paragraph("D+",normalNewRoman7));
				gradeTable.addCell(new Paragraph("D",normalNewRoman7));
				gradeTable.addCell(new Paragraph("D-",normalNewRoman7));
				gradeTable.addCell(new Paragraph("E",normalNewRoman7));



				List<Subject> subList = subjectDAO.getSubjectList();
				DecimalFormat rf = new DecimalFormat("0"); 
				rf.setRoundingMode(RoundingMode.HALF_UP);

				int count = 1;
				for(Subject sub : subList){
					subjectScoreTable.addCell(new Paragraph(" "+count,normalNewRoman7)); 

					if(StringUtils.equals(sub.getUuid(), ExamConstants.SUB_ENG)){
						subjectScoreTable.addCell(new Paragraph(" "+sub.getSubjectName(),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+engOpenerscore + " - " + computeRemarks(engOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(engOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+engMidtermscore + " - " + computeRemarks(engMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(engMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+engEndtermscore + " - " + computeRemarks(engEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(engEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" " + findSubTecher(ExamConstants.SUB_ENG,streamuuid),normalNewRoman7)); 

					}if(StringUtils.equals(sub.getUuid(), ExamConstants.SUB_KIS)){
						subjectScoreTable.addCell(new Paragraph(" "+sub.getSubjectName(),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+kisOpenerscore+ " - " + computeRemarks(kisOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(kisOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+kisMidtermscore + " - " + computeRemarks(kisMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(kisMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+kisEndtermscore + " - " + computeRemarks(kisEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(kisEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" " + findSubTecher(ExamConstants.SUB_KIS,streamuuid),normalNewRoman7));

					}if(StringUtils.equals(sub.getUuid(), ExamConstants.SUB_MATH)){
						subjectScoreTable.addCell(new Paragraph(" "+sub.getSubjectName(),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+mathOpenerscore+ " - " + computeRemarks(mathOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(mathOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+mathMidtermscore + " - " + computeRemarks(mathMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(mathMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+mathEndtermscore + " - " + computeRemarks(mathEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(mathEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" " + findSubTecher(ExamConstants.SUB_MATH,streamuuid),normalNewRoman7));

					}if(StringUtils.equals(sub.getUuid(), ExamConstants.SUB_SCI)){
						subjectScoreTable.addCell(new Paragraph(" "+sub.getSubjectName(),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+sciOpenerscore+ " - " + computeRemarks(sciOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(sciOpenerscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+sciMidtermscore + " - " + computeRemarks(sciMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(sciMidtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+sciEndtermscore + " - " + computeRemarks(sciEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" "+computeGrade(sciEndtermscore),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" " + findSubTecher(ExamConstants.SUB_SCI,streamuuid),normalNewRoman7));

					}if(StringUtils.equals(sub.getUuid(), ExamConstants.SUB_SST) || StringUtils.equals(sub.getUuid(), ExamConstants.SUB_CRE)){
						subjectScoreTable.addCell(new Paragraph(" SS-CRE",normalNewRoman7));

						String openerSscre = rf.format(((sstOpenerscore + creOpenerscore)/90)*100);
						String midtermSscre = rf.format(((sstMidtermscore + creMidtermscore)/90)*100);
						String endtermSscre = rf.format(((sstEndtermscore + creEndtermscore)/90)*100);

						subjectScoreTable.addCell(new Paragraph(" SS = " + rf.format(sstOpenerscore) + "\n CRE = " + rf.format(creOpenerscore) + "\n TOTAL = " + openerSscre + " - " + computeRemarks(Double.parseDouble(openerSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" \n\n" + computeGrade(Double.parseDouble(openerSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" SS = " + rf.format(sstMidtermscore) + "\n CRE = " + rf.format(creMidtermscore) + "\n TOTAL = " + midtermSscre + " - " + computeRemarks(Double.parseDouble(midtermSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" \n\n" + computeGrade(Double.parseDouble(midtermSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" SS = " + rf.format(sstEndtermscore) + "\n CRE = " + rf.format(creEndtermscore) + "\n TOTAL = " + endtermSscre + " - " + computeRemarks(Double.parseDouble(endtermSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" \n\n" + computeGrade(Double.parseDouble(endtermSscre)),normalNewRoman7));
						subjectScoreTable.addCell(new Paragraph(" " + findSubTecher(ExamConstants.SUB_SST,streamuuid) + "\n" + findSubTecher(ExamConstants.SUB_CRE,streamuuid),normalNewRoman7));

					}if(count >= 5){
						break;
					}

					count++;
				}

				engOpenerscore  = 0;kisOpenerscore  = 0;mathOpenerscore  = 0;sciOpenerscore = 0;
				sstOpenerscore  = 0;creOpenerscore = 0;

				engMidtermscore  = 0;kisMidtermscore  = 0;mathMidtermscore = 0;sciMidtermscore = 0;
				sstMidtermscore  = 0;creMidtermscore = 0;

				engEndtermscore  = 0;kisEndtermscore  = 0;mathEndtermscore  = 0;sciEndtermscore = 0;
				sstEndtermscore  = 0;creEndtermscore = 0;

				//TODO year performance
				
				String t1streamposStr = "",t1classposStr = "";
				String t1meanStr = "0";
				
				String t2streamposStr = "",t2classposStr = "";
				String t2meanStr = "0";
				
				String t3streamposStr = "",t3classposStr = "";
				String t3meanStr = "0";
				
				if(meanScoreDAO.getMeanScore(ESstudentuuid,"1",systemConfig.getYear()) !=null){
					MeanScore termOnemeanscore = meanScoreDAO.getMeanScore(ESstudentuuid,"1",systemConfig.getYear()); 
					if(termOnemeanscore.getStreamPosition() != 0){
						t1streamposStr = Integer.toString(termOnemeanscore.getStreamPosition());
					}else{
						t1streamposStr = "";
					}
					
					if(termOnemeanscore.getClassPosition() != 0){
						t1classposStr = Integer.toString(termOnemeanscore.getClassPosition());
					}else{
						t1classposStr = "";
					}
					
					if(termOnemeanscore.getMeanScore() != 0){
						t1meanStr = df.format(termOnemeanscore.getMeanScore());
					}else{
						t1meanStr = "";
					}
					
				}

				if(meanScoreDAO.getMeanScore(ESstudentuuid,"2",systemConfig.getYear()) !=null){
					MeanScore termTwomeanscore = meanScoreDAO.getMeanScore(ESstudentuuid,"2",systemConfig.getYear()); 
					if(termTwomeanscore.getStreamPosition() != 0){
						t2streamposStr = Integer.toString(termTwomeanscore.getStreamPosition());
					}else{
						t2streamposStr = "";
					}
					
					if(termTwomeanscore.getClassPosition() != 0){
						t2classposStr = Integer.toString(termTwomeanscore.getClassPosition());
					}else{
						t2classposStr = "";
					}
					
					if(termTwomeanscore.getMeanScore() != 0){
						t2meanStr = df.format(termTwomeanscore.getMeanScore());
					}else{
						t2meanStr = "";
					}
				}

				if(meanScoreDAO.getMeanScore(ESstudentuuid,"3",systemConfig.getYear()) !=null){
					MeanScore termThreemeanscore = meanScoreDAO.getMeanScore(ESstudentuuid,"3",systemConfig.getYear()); 
					if(termThreemeanscore.getStreamPosition() != 0){
						t3streamposStr = Integer.toString(termThreemeanscore.getStreamPosition());
					}else{
						t3streamposStr = "";
					}
					
					if(termThreemeanscore.getClassPosition() != 0){
						t3classposStr = Integer.toString(termThreemeanscore.getClassPosition());
					}else{
						t3classposStr = "";
					}
					
					if(termThreemeanscore.getMeanScore() != 0){
						t3meanStr = df.format(termThreemeanscore.getMeanScore());
					}else{
						t3meanStr = "";
					}
				}
				
				PdfPCell termOneheader = new PdfPCell(new Paragraph("TERM 1 ANALYSIS \n\n"
						+ "Stream Pos:  " + t1streamposStr + " \t\t\t\t\t Class Pos:  " + t1classposStr + " \n" 
						+ "Mean:  " + t1meanStr + " Grade \t\t\t\t\t " + computeGrade(Double.parseDouble(t1meanStr))+" \n" , normalNewRoman7));
				termOneheader.setBackgroundColor(baseColor);
				termOneheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell termTwoheader = new PdfPCell(new Paragraph("TERM 2 ANALYSIS \n\n"
						+ "Stream Pos:  " + t2streamposStr + " \t\t\t\t\t Class Pos:  " + t2classposStr + " \n" 
						+ "Mean:  " + t2meanStr + " Grade \t\t\t\t\t " + computeGrade(Double.parseDouble(t2meanStr))+"  \n" , normalNewRoman7));
				termTwoheader.setBackgroundColor(baseColor);
				termTwoheader.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell termThreeheader = new PdfPCell(new Paragraph("TERM 3 ANALYSIS \n\n"
						+ "Stream Pos:  " + t3streamposStr + " \t\t\t\t\t Class Pos:  " + t3classposStr + " \n" 
						+ "Mean:  " + t3meanStr + " Grade \t\t\t\t\t " + computeGrade(Double.parseDouble(t3meanStr))+" \n" , normalNewRoman7));
				termThreeheader.setBackgroundColor(baseColor);
				termThreeheader.setHorizontalAlignment(Element.ALIGN_LEFT);
				

				PdfPTable yearPerformanceTable = new PdfPTable(3);   
				yearPerformanceTable.setWidthPercentage(100); 
				yearPerformanceTable.setWidths(new int[]{100,100,100}); 
				yearPerformanceTable.setHorizontalAlignment(Element.ALIGN_LEFT);

				yearPerformanceTable.addCell(termOneheader);
				yearPerformanceTable.addCell(termTwoheader); 
				yearPerformanceTable.addCell(termThreeheader); 
				
				
				//end year performance


				PdfPCell BAFheader = new PdfPCell();
				int regYear = admYearMap.get(ESstudentuuid); 
				int std4 = regYear;
				int std5 = std4 + 1;
				int std6 = std5 + 1;
				int std7 = std6 + 1;
				int std8 = std7 + 1; 

				double w4 =0,w5 =0,w6 =0,w7 =0,w8 =0;

				List<BarWeight> barWeight4 = new ArrayList<>();
				if(barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std4)) !=null){
					barWeight4 = barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std4));
					for(BarWeight b4 : barWeight4){
						w4 += b4.getWeight();  
					}
					w4 = w4/3;
				}
				List<BarWeight> barWeight5 = new ArrayList<>();
				if(barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std5)) !=null){
					barWeight5 = barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std5));
					for(BarWeight b5 : barWeight5){
						w5 += b5.getWeight();  
					}
					w5 = w5/3;
				}
				List<BarWeight> barWeight6 = new ArrayList<>();
				if(barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std6)) !=null){
					barWeight6 = barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std6));
					for(BarWeight b6 : barWeight6){
						w6 += b6.getWeight();  
					}
					w6 = w6/3;
				}
				List<BarWeight> barWeight7 = new ArrayList<>();
				if(barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std7)) !=null){
					barWeight7 = barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std7));
					for(BarWeight b7 : barWeight7){
						w7 += b7.getWeight();  
					}
					w7 = w7/3;
				}
				List<BarWeight>  barWeight8 =new ArrayList<>();
				if(barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std8)) !=null){
					barWeight8 = barWeightDAO.getBarWeightList(ESstudentuuid,Integer.toString(std8));
					for(BarWeight b8 : barWeight8){
						w8 += b8.getWeight();  
					}
					w8 = w8/3;
				}


				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				dataset.setValue(w4, "Pnts", " YEAR " + std4);
				dataset.setValue(w5, "Pnts", " YEAR " + std5);
				dataset.setValue(w6, "Pnts", " YEAR " + std6);
				dataset.setValue(w7, "Pnts", " YEAR " + std7);
				dataset.setValue(w8, "Pnts", " YEAR " + std8);  

				//CONTROL
				w4 =0;w5 =0;w6 =0;w7 =0;w8 =0;
				dataset.setValue(12, "Control ", "Control ");

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

				//chart table
				PdfPTable chartTable = new PdfPTable(1);  
				chartTable.setWidthPercentage(100); 
				chartTable.setWidths(new int[]{100}); 
				chartTable.setHorizontalAlignment(Element.ALIGN_LEFT);
				chartTable.addCell(BAFheader);





				Paragraph emptyline = new Paragraph(("                              "));

				document.add(prefaceTable);
				document.add(emptyline); 
				document.add(headerTable);
				document.add(emptyline); 
				document.add(subjectScoreTable);

				ETSSCount++;
				ESnumber=ESmean;
               
				document.add(emptyline); 
				document.add(resultTable); 
				document.add(emptyline); 
				document.add(yearPerformanceTable); 
				document.add(emptyline); 
				document.add(gradeTable); 
				document.add(emptyline); 

				document.add(chartTable);

				document.add(emptyline);
				document.add(emptyline);
				document.add(emptyline);
				document.add(emptyline);
				document.add(emptyline);

				document.add(CTcommentLb);
				document.add(classTeacherCommentTable); 
				document.add(emptyline);
				document.add(commentLb);
				document.add(principalCommentTable);
				document.add(feeTable); 
				document.newPage();

			}


			/** End term End 
			 * Class sorting ends
			 * ************************************************************************************************************************************************
			 *                                                          PETER MWENDA 
			 * */


			document.close();
		}
		catch(DocumentException e) {
			logger.error("DocumentException while writing into the document");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}

	/**
	 * @param studentuuid
	 * @param systemConfig
	 * @param streamuuid
	 * @return class teacher comment
	 */
	private String classteacherComment(String studentuuid, SystemConfig systemConfig, String streamuuid) {
		String coment = "";
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

		if(lasttermmean < thistermmean && lasttermmean != 0){//improved,ameliorated
			coment = "You have improved with " + deviationFinder(thistermmean,lasttermmean)+". This term comment : " + thisTermComment(thistermmean); 

		}else if(lasttermmean < thistermmean && lasttermmean == 0){//no last term exam
			coment = "Your last term exam was not recorded " +". This term comment : " + thisTermComment(thistermmean);

		}else if(lasttermmean == thistermmean){//equals
			coment = "Wake up! No improvement " + ". This term comment " + thisTermComment(thistermmean);

		}else if(lasttermmean < thistermmean){//deteriorated,nosedive 
			coment = "You are nosediving, you have deteriorated with " + deviationFinder(thistermmean,lasttermmean)+". This term comment " + thisTermComment(thistermmean);

		}
		//find class teacher  
		String teacherID = "";
		String teachername = "";
		ClassTeacher classTeacher = new ClassTeacher();
		if(teacherClassDAO.getTeacherClassByClassid(streamuuid) !=null){
			classTeacher = teacherClassDAO.getTeacherClassByClassid(streamuuid);
			teacherID = classTeacher.getTeacherUuid();
		}
		if(staffDAO.getStaff(teacherID) !=null){
			Staff staff = staffDAO.getStaff(teacherID); 
			teachername = staff.getName(); 
			teachername.substring(0, Math.min(teachername.length(), 10)).toLowerCase();
		}

		return coment + " ( Teacher : " + teachername + " ) "; 
	}

	private String thisTermComment(double thistermmean) {
		String thistermComment = "";

		DecimalFormat rf = new DecimalFormat("0"); 
		rf.setRoundingMode(RoundingMode.HALF_UP);

		double mean = Double.parseDouble(rf.format(thistermmean));

		if(mean >= gradingSystem.getGradeAplain()){
			thistermComment = "Execellent";
		}else if(mean >= gradingSystem.getGradeAminus()){
			thistermComment = "Execellent";
		}else if(mean >= gradingSystem.getGradeBplus()){
			thistermComment = "Good work";
		}else if(mean >= gradingSystem.getGradeBplain()){
			thistermComment = "Good work";
		}else if(mean >= gradingSystem.getGradeBminus()){
			thistermComment = "Good work";
		}else if(mean >= gradingSystem.getGradeCplus()){
			thistermComment = "Average";
		}else if(mean >= gradingSystem.getGradeCplain()){
			thistermComment = "Average";
		}else if(mean >= gradingSystem.getGradeCminus()){
			thistermComment = "Average";
		}else if(mean >= gradingSystem.getGradeDplus()){
			thistermComment = "Below average";
		}else if(mean >= gradingSystem.getGradeDplain()){
			thistermComment = "Below average";
		}else if(mean >= gradingSystem.getGradeDminus()){
			thistermComment = "Below average";
		}else{
			thistermComment = "Below average";
		}

		if(mean ==0){
			thistermComment = " ";
		}

		return thistermComment;
	}

	/**
	 * @param subjectid
	 * @param classroomid
	 * @return
	 */
	private String findSubTecher(String subjectid, String streamuuid) {
		String teachername = "";
		String teacheruuid = "";
		if(teacherSubjectDAO.getTeacherSubject(subjectid, streamuuid) !=null){
			TeacherSubject teacherSubject = teacherSubjectDAO.getTeacherSubject(subjectid, streamuuid);
			teacheruuid = teacherSubject.getTeacherUuid();
			if(staffDAO.getStaff(teacheruuid) !=null){
				Staff staff = staffDAO.getStaff(teacheruuid); 
				teachername = staff.getName(); 
			}	
		}
		if(StringUtils.isBlank(teachername)){
			teachername = "";
		}

		return teachername.substring(0, Math.min(teachername.length(), 9)).toLowerCase();
	}


	/**
	 * @param score
	 * @return
	 */
	private String computeRemarks(double score) {
		String remarks = "";
		DecimalFormat rf = new DecimalFormat("0"); 
		rf.setRoundingMode(RoundingMode.HALF_UP);
		double mean = Double.parseDouble(rf.format(score));
		if(mean >= gradingSystem.getGradeAplain()){
			remarks = "Execellent";
		}else if(mean >= gradingSystem.getGradeAminus()){
			remarks = "Good work";
		}else if(mean >= gradingSystem.getGradeBplus()){
			remarks = "Nice job";
		}else if(mean >= gradingSystem.getGradeBplain()){
			remarks = "Well done";
		}else if(mean >= gradingSystem.getGradeBminus()){
			remarks = "Well done";
		}else if(mean >= gradingSystem.getGradeCplus()){
			remarks = "Fair";
		}else if(mean >= gradingSystem.getGradeCplain()){
			remarks = "Fair";
		}else if(mean >= gradingSystem.getGradeCminus()){
			remarks = "Fair";
		}else if(mean >= gradingSystem.getGradeDplus()){
			remarks = "Improve";
		}else if(mean >= gradingSystem.getGradeDplain()){
			remarks = "Improve";
		}else if(mean >= gradingSystem.getGradeDminus()){
			remarks = "Improve";
		}else{
			remarks = "Improve";
		}

		if(mean ==0){
			remarks = " ";
		}

		return remarks;
	}

	/**
	 * @param score
	 * @return
	 */
	private String computeGrade(double score) {
		String grade = "";
		DecimalFormat rf = new DecimalFormat("0"); 
		rf.setRoundingMode(RoundingMode.HALF_UP);
		double mean = Double.parseDouble(rf.format(score));
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
			remarks = "Average, you have the potential.";
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
	private static final long serialVersionUID = -1430883323448135623L;

}
