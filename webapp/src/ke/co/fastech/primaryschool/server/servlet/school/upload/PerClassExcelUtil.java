package ke.co.fastech.primaryschool.server.servlet.school.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ke.co.fastech.primaryschool.bean.exam.EndTermExam;
import ke.co.fastech.primaryschool.bean.exam.MidTermExam;
import ke.co.fastech.primaryschool.bean.exam.OpenerExam;
import ke.co.fastech.primaryschool.bean.school.Classroom;
import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.bean.student.Subject;
import ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO;
import ke.co.fastech.primaryschool.persistence.school.ClassroomDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;

public class PerClassExcelUtil {

	private String[] examcodeArray;
	private List<String> examcodeList;

	private String[] subjectcodeArray;
	private List<String> subjectcodeList;

	String classesuuid = "";

	/**
	 * 
	 */
	protected PerClassExcelUtil() {
		examcodeArray = new String[] {"OPENER", "MIDTERM", "ENDTERM"};
		examcodeList = Arrays.asList(examcodeArray);

		subjectcodeArray = new String[] {"COMP", "INSH"};
		subjectcodeList = Arrays.asList(subjectcodeArray);
	} 


	/**Checks that an uploaded results file is in proper order.
	 * 
	 * @param uploadedFile
	 * @param schooluuid
	 * @param streamDAO
	 * @param subjectDAO
	 * @param teacherSubjectDAO
	 * @param studentDAO
	 * @return
	 * @throws IOException
	 */
	protected String inspectResultFile(File uploadedFile, String schooluuid, StreamDAO streamDAO, SubjectDAO subjectDAO, TeacherSubjectDAO teacherSubjectDAO, StudentDAO studentDAO) throws IOException {

		String feedback = ScorePerClass.UPLOAD_SUCCESS;
		// Creating Input Stream 

		if(uploadedFile !=null){
			FileInputStream myInput =  new FileInputStream(uploadedFile); 

			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);

			DecimalFormat rf3 = new DecimalFormat("0"); 
			rf3.setRoundingMode(RoundingMode.DOWN);

			try{

				String streamname = "";
				String exam = "";

				String filename = uploadedFile.getName().replaceAll("_", " "); 

				String [] parts = filename.split("\\.");
				streamname = parts[0]; 
				exam = parts[1];
               // System.out.println("streamname " + streamname + " schooluuid" + schooluuid);
				if(streamDAO.getStream(streamname, schooluuid) == null){
					return ("Class code \"" + streamname + "\" not found! ");
				}

				String code = StringUtils.upperCase(StringUtils.trimToEmpty(exam));
				if(!examcodeList.contains(code)) {
					return ("Invalid exam code \"" + code + "\"");
				}

				String admnostr = "";

				String eng_score_str = "";
				String comp_score_str = "";
				String kisw_score_str = "";
				String insha_score_str = "";

				String math_score_str = "";
				String sci_score_str = "";

				String ss_score_str = "";
				String cre_score_str = "";

				String eng = "",comp = "",kisw = "",insha = "";
				String math = "",sci = "",ss = "",cre = "";

				String eng_code = "",eng_outof = "";
				String comp_code = "",comp_outof = "";
				String kisw_code = "",kisw_outof = "";
				String insha_code = "",insha_outof = "";
				String math_code = "",math_outof = "";
				String sci_code = "",sci_outof = "";
				String ss_code = "",ss_outof = "";
				String cre_code = "",cre_outof = "";


				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);

					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i == 0){

							XSSFCell eng_cell = row.getCell((short)2);
							XSSFCell comp_cell = row.getCell((short)3);
							XSSFCell kisw_cell = row.getCell((short)4);
							XSSFCell insha_cell = row.getCell((short)5);

							XSSFCell math_cell = row.getCell((short)6);
							XSSFCell sci_cell = row.getCell((short)7);

							XSSFCell ss_cell = row.getCell((short)8);
							XSSFCell cre_cell = row.getCell((short)9);

							eng = eng_cell + "";
							comp = comp_cell + "";
							kisw = kisw_cell + "";
							insha = insha_cell + "";
							math = math_cell + "";
							sci = sci_cell + "";
							ss = ss_cell + "";
							cre = cre_cell + "";

							String [] eng_parts = eng.split("/");   
							String [] comp_parts = comp.split("/");
							String [] kisw_parts = kisw.split("/");   
							String [] insha_parts = insha.split("/");
							String [] math_parts = math.split("/");   
							String [] sci_parts = sci.split("/");
							String [] ss_parts = ss.split("/");   
							String [] cre_parts = cre.split("/");

							eng_code = eng_parts[0];eng_outof = eng_parts[1];
							comp_code = comp_parts[0];comp_outof = comp_parts[1];
							kisw_code = kisw_parts[0];kisw_outof = kisw_parts[1];
							insha_code = insha_parts[0];insha_outof = insha_parts[1];
							math_code = math_parts[0];math_outof = math_parts[1];
							sci_code = sci_parts[0];sci_outof = sci_parts[1];
							ss_code = ss_parts[0];ss_outof = ss_parts[1];
							cre_code = cre_parts[0];cre_outof = cre_parts[1];

							if(subjectDAO.getSubject(eng_code) == null){
								return ("Invalid Subject code " + eng_code + " on line " + count);
							}if(!subjectcodeList.contains(comp_code)){
								return ("Invalid Subject code " + comp_code + " on line " + count);
							}if(subjectDAO.getSubject(kisw_code) == null){
								return ("Invalid Subject code " + kisw_code + " on line " + count);
							}if(!subjectcodeList.contains(insha_code)){
								return ("Invalid Subject code " + insha_code + " on line " + count);
							}if(subjectDAO.getSubject(math_code) == null){
								return ("Invalid Subject code " + math_code + " on line " + count);
							}if(subjectDAO.getSubject(sci_code) == null){
								return ("Invalid Subject code " + sci_code + " on line " + count);
							}if(subjectDAO.getSubject(ss_code) == null){
								return ("Invalid Subject code " + ss_code + " on line " + count);
							}if(subjectDAO.getSubject(cre_code) == null){
								return ("Invalid Subject code " + cre_code + " on line " + count);
							}

							if(totalColumn > 10){
								return ("Invalid Number of comlumns on line \"" + count);
							}

							//System.out.println("eng_code " + eng_code);

						}else if(i > 0){

							admnostr = row.getCell(1)+"";
							admnostr = (int)Double.parseDouble(admnostr) + "";  
                            
							eng_score_str =  row.getCell(2)+"";
							comp_score_str =  row.getCell(3)+"";
							kisw_score_str =  row.getCell(4)+"";
							insha_score_str =  row.getCell(5)+"";
							math_score_str =  row.getCell(6)+"";
							sci_score_str =  row.getCell(7)+"";
							ss_score_str =  row.getCell(8)+"";
							cre_score_str =  row.getCell(9)+"";



							if(StringUtils.equals(eng_score_str, "null") || !isNumeric(eng_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(comp_score_str, "null") || !isNumeric(comp_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(kisw_score_str, "null") || !isNumeric(kisw_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(insha_score_str, "null") || !isNumeric(insha_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(math_score_str, "null") || !isNumeric(math_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(sci_score_str, "null") || !isNumeric(sci_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(ss_score_str, "null") || !isNumeric(ss_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(cre_score_str, "null") || !isNumeric(cre_score_str)){
								return "Blank/Invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}


							String studentuuid = "";
							Student student = new Student();
							if(admnostr !=null){
								student = studentDAO.getStudentByADMNO(admnostr,schooluuid); 
								//System.out.println("admnostr " + admnostr + " schooluuid " + schooluuid);
								if(student !=null){
									studentuuid = student.getUuid();
								}

							}

							if(studentDAO.getStudentByUuid(studentuuid)==null) {
								return ("Pupil with admNo \"" + admnostr + "\" on line \"" + count + "\" was not found in the System");
							}

						}//end if

					}
					if(!StringUtils.isBlank(admnostr)){

						if(Double.parseDouble(eng_score_str) > Double.parseDouble(eng_outof)){ 
							return "Invalid score " + eng_score_str + " on line " + count + " . Hint : score " + eng_score_str + " is greater that ( > ) the \"out of\" mark " + eng_outof + " , this is impossible .";
						}if(Double.parseDouble(comp_score_str) > Double.parseDouble(comp_outof)){ 
							return "Invalid score " + comp_score_str + " on line " + count + " . Hint : score " + comp_score_str + " is greater that ( > ) the \"out of\" mark " + comp_outof + " , this is impossible .";
						}if(Double.parseDouble(kisw_score_str) > Double.parseDouble(kisw_outof)){ 
							return "Invalid score " + kisw_score_str + " on line " + count + " . Hint : score " + kisw_score_str + " is greater that ( > ) the \"out of\" mark " + kisw_outof + " , this is impossible .";
						}if(Double.parseDouble(insha_score_str) > Double.parseDouble(insha_outof)){ 
							return "Invalid score " + insha_score_str + " on line " + count + " . Hint : score " + insha_score_str + " is greater that ( > ) the \"out of\" mark " + insha_outof + " , this is impossible .";
						}if(Double.parseDouble(math_score_str) > Double.parseDouble(math_outof)){ 
							return "Invalid score " + math_score_str + " on line " + count + " . Hint : score " + math_score_str + " is greater that ( > ) the \"out of\" mark " + math_outof + " , this is impossible .";
						}if(Double.parseDouble(sci_score_str) > Double.parseDouble(sci_outof)){ 
							return "Invalid score " + sci_score_str + " on line " + count + " . Hint : score " + sci_score_str + " is greater that ( > ) the \"out of\" mark " + sci_outof + " , this is impossible .";
						}if(Double.parseDouble(ss_score_str) > Double.parseDouble(ss_outof)){ 
							return "Invalid score " + ss_score_str + " on line " + count + " . Hint : score " + ss_score_str + " is greater that ( > ) the \"out of\" mark " + ss_outof + " , this is impossible .";
						}if(Double.parseDouble(cre_score_str) > Double.parseDouble(cre_outof)){ 
							return "Invalid score " + cre_score_str + " on line " + count + " . Hint : score " + cre_score_str + " is greater that ( > ) the \"out of\" mark " + cre_outof + " , this is impossible .";
						}
						//System.out.println("admnostr " + admnostr + " eng_score_str " + eng_score_str + " comp_score_str " + comp_score_str);

					}


					count++;
				}
			}

			catch (Exception e){

			}finally {
				myInput.close();

			}
		}


		return feedback;
	}




	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}



	/**
	 * 
	 * @param uploadedFile
	 * @param school
	 * @param examEngineDAO
	 * @param studentDAO
	 * @param streamDAO
	 * @param subjectDAO
	 * @param systemConfigDAO
	 * @throws IOException
	 */

	public void saveResults(File uploadedFile, Account school, ExamEngineDAO examEngineDAO, StudentDAO studentDAO, StreamDAO streamDAO,ClassroomDAO classroomDAO, SubjectDAO subjectDAO, SystemConfigDAO systemConfigDAO) throws IOException{

		if(uploadedFile !=null){
			FileInputStream myInput =  new FileInputStream(uploadedFile); 

			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();

			DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

			DecimalFormat rf2 = new DecimalFormat("0"); 
			rf2.setRoundingMode(RoundingMode.UP);

			DecimalFormat rf3 = new DecimalFormat("0"); 
			rf3.setRoundingMode(RoundingMode.DOWN);

			try{

				String classroom = "";
				String exam = "";

				String filename = uploadedFile.getName().replaceAll("_", " "); 

				String [] parts = filename.split("\\.");
				classroom = parts[0]; 
				exam = parts[1];
				Stream stream = new Stream();
				if(streamDAO.getStream(classroom,school.getUuid()) !=null){
					stream = streamDAO.getStream(classroom,school.getUuid());
				}
				String classuuid = "";
				List<Classroom>  classlist = new ArrayList<>();
				classlist = classroomDAO.getClassroomList(); 
				for(Classroom clss : classlist){
					if(StringUtils.contains(stream.getStreamName(), clss.getClassName())){
						classuuid = clss.getUuid();
					}
				}

				String admnostr = "";

				String eng_score_str = "";
				String comp_score_str = "";
				String kisw_score_str = "";
				String insha_score_str = "";

				String math_score_str = "";
				String sci_score_str = "";

				String ss_score_str = "";
				String cre_score_str = "";

				String eng = "",comp = "",kisw = "",insha = "";
				String math = "",sci = "",ss = "",cre = "";

				String eng_code = "",eng_outof = "";
				String comp_outof = "";
				String kisw_code = "",kisw_outof = "";
				String insha_outof = "";
				String math_code = "",math_outof = "";
				String sci_code = "",sci_outof = "";
				String ss_code = "",ss_outof = "";
				String cre_code = "",cre_outof = "";

				SystemConfig systemConfig = new SystemConfig();
				if(systemConfigDAO.getSystemConfig(school.getUuid()) !=null){
					systemConfig = systemConfigDAO.getSystemConfig(school.getUuid()); 
				}


				String studentuuid = "";
				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);

					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i == 0){

							XSSFCell eng_cell = row.getCell((short)2);
							XSSFCell comp_cell = row.getCell((short)3);
							XSSFCell kisw_cell = row.getCell((short)4);
							XSSFCell insha_cell = row.getCell((short)5);

							XSSFCell math_cell = row.getCell((short)6);
							XSSFCell sci_cell = row.getCell((short)7);

							XSSFCell ss_cell = row.getCell((short)8);
							XSSFCell cre_cell = row.getCell((short)9);

							eng = eng_cell + "";
							comp = comp_cell + "";
							kisw = kisw_cell + "";
							insha = insha_cell + "";
							math = math_cell + "";
							sci = sci_cell + "";
							ss = ss_cell + "";
							cre = cre_cell + "";

							String [] eng_parts = eng.split("/");   
							String [] comp_parts = comp.split("/");
							String [] kisw_parts = kisw.split("/");   
							String [] insha_parts = insha.split("/");
							String [] math_parts = math.split("/");   
							String [] sci_parts = sci.split("/");
							String [] ss_parts = ss.split("/");   
							String [] cre_parts = cre.split("/");

							eng_code = eng_parts[0];eng_outof = eng_parts[1];
							comp_outof = comp_parts[1];
							kisw_code = kisw_parts[0];kisw_outof = kisw_parts[1];
							insha_outof = insha_parts[1];
							math_code = math_parts[0];math_outof = math_parts[1];
							sci_code = sci_parts[0];sci_outof = sci_parts[1];
							ss_code = ss_parts[0];ss_outof = ss_parts[1];
							cre_code = cre_parts[0];cre_outof = cre_parts[1];
							//System.out.println("eng_code " + eng_code);
						}else if(i > 0){

							admnostr = row.getCell(1)+"";
							admnostr = (int)Double.parseDouble(admnostr) + "";  

							eng_score_str =  row.getCell(2)+"";
							comp_score_str =  row.getCell(3)+"";
							kisw_score_str =  row.getCell(4)+"";
							insha_score_str =  row.getCell(5)+"";
							math_score_str =  row.getCell(6)+"";
							sci_score_str =  row.getCell(7)+"";
							ss_score_str =  row.getCell(8)+"";
							cre_score_str =  row.getCell(9)+"";


							Student student = new Student();
							if(admnostr !=null){
								student = studentDAO.getStudentByADMNO(admnostr,school.getUuid()); 
								if(student !=null){
									studentuuid = student.getUuid();
								}

							}


						}//end if

					}
					if(!StringUtils.isBlank(admnostr)){
						//save score
						List<Subject>  sublist = new ArrayList<>();
						sublist = subjectDAO.getSubjectList();

						for(Subject sub : sublist){

							if(StringUtils.equals(exam, "OPENER")) {
								OpenerExam opener = new OpenerExam();
								opener.setAccountUuid(school.getUuid()); 
								
								if(StringUtils.equals(sub.getSubjectCode(), eng_code)){ 
									double engscore = 0,engoutof = 0;
									double compscore = 0,compoutof =0;
									double engtotal = 0;
									
									engscore = Double.parseDouble(eng_score_str); 
									compscore = Double.parseDouble(comp_score_str); 
									engoutof = Double.parseDouble(eng_outof); 
									compoutof = Double.parseDouble(comp_outof); 
									if(engscore > engoutof){
										engscore = 0;
									}
									if(compscore > compoutof){
										compscore = 0;
									}
									engscore = (engscore/engoutof)*60;
									compscore = (compscore/compoutof)*40; 
									engtotal = engscore + compscore;
									opener.setScore(engtotal); 
									opener.setAccountUuid(school.getUuid()); 
									examEngineDAO.putExam(opener,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

									
								}else if(StringUtils.equals(sub.getSubjectCode(), kisw_code)){ 
									double kiswscore = 0,kiswoutof = 0;
									double inshascore = 0,inshaoutof =0;
									double kiswtotal = 0;
									
									kiswscore = Double.parseDouble(kisw_score_str); 
									inshascore = Double.parseDouble(insha_score_str); 
									kiswoutof = Double.parseDouble(kisw_outof); 
									inshaoutof = Double.parseDouble(insha_outof); 
									if(kiswscore > kiswoutof){
										kiswscore = 0;
									}
									if(inshascore > inshaoutof){
										inshascore = 0;
									}
									kiswscore = (kiswscore/kiswoutof)*60;
									inshascore = (inshascore/inshaoutof)*40; 
									kiswtotal = kiswscore + inshascore;
									opener.setScore(kiswtotal); 
									examEngineDAO.putExam(opener,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

 
								}else if(StringUtils.equals(sub.getSubjectCode(), math_code)){ 
									double mathscore = 0,mathoutof = 0;
									double mathtotal = 0;
									mathscore = Double.parseDouble(math_score_str); 
									mathoutof = Double.parseDouble(math_outof); 
									if(mathscore > mathoutof){
										mathscore = 0;
									}
									mathtotal = ((mathscore/mathoutof)*50)*2; 
									opener.setScore(mathtotal);
									examEngineDAO.putExam(opener,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), sci_code)){ 
									double sciscore = 0,scioutof = 0;
									double scitotal = 0;
									sciscore = Double.parseDouble(sci_score_str); 
									scioutof = Double.parseDouble(sci_outof); 
									if(sciscore > scioutof){
										sciscore = 0;
									}
									scitotal = ((sciscore/scioutof)*50)*2; 
									opener.setScore(scitotal);
									examEngineDAO.putExam(opener,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), ss_code)){ 
									double ssscore = 0,ssoutof = 0;
									double sstotal = 0;
									ssscore = Double.parseDouble(ss_score_str); 
									ssoutof = Double.parseDouble(ss_outof); 
									if(ssscore > ssoutof){
										ssscore = 0;
									}
									sstotal = (ssscore/ssoutof)*60; 
									opener.setScore(sstotal); 
									examEngineDAO.putExam(opener,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), cre_code)){ 
									double crescore = 0,creoutof = 0;
									double cretotal = 0;
									crescore = Double.parseDouble(cre_score_str); 
									creoutof = Double.parseDouble(cre_outof); 
									if(crescore > creoutof){
										crescore = 0;
									}
									cretotal = (crescore/creoutof)*30; 
									opener.setScore(cretotal);
									examEngineDAO.putExam(opener,studentuuid,sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

								}


							}else if(StringUtils.equals(exam, "MIDTERM")) {
								MidTermExam midterm = new MidTermExam();
								midterm.setAccountUuid(school.getUuid()); 
								
								if(StringUtils.equals(sub.getSubjectCode(), eng_code)){ 
									double engscore = 0,engoutof = 0;
									double compscore = 0,compoutof =0;
									double engtotal = 0;
									
									engscore = Double.parseDouble(eng_score_str); 
									compscore = Double.parseDouble(comp_score_str); 
									engoutof = Double.parseDouble(eng_outof); 
									compoutof = Double.parseDouble(comp_outof); 
									if(engscore > engoutof){
										engscore = 0;
									}
									if(compscore > compoutof){
										compscore = 0;
									}
									engscore = (engscore/engoutof)*60;
									compscore = (compscore/compoutof)*40; 
									engtotal = engscore + compscore;
									midterm.setScore(engtotal); 
									examEngineDAO.putExam(midterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

									
								}else if(StringUtils.equals(sub.getSubjectCode(), kisw_code)){ 
									double kiswscore = 0,kiswoutof = 0;
									double inshascore = 0,inshaoutof =0;
									double kiswtotal = 0;
									
									kiswscore = Double.parseDouble(kisw_score_str); 
									inshascore = Double.parseDouble(insha_score_str); 
									kiswoutof = Double.parseDouble(kisw_outof); 
									inshaoutof = Double.parseDouble(insha_outof); 
									if(kiswscore > kiswoutof){
										kiswscore = 0;
									}
									if(inshascore > inshaoutof){
										inshascore = 0;
									}
									kiswscore = (kiswscore/kiswoutof)*60;
									inshascore = (inshascore/inshaoutof)*40; 
									kiswtotal = kiswscore + inshascore;
									midterm.setScore(kiswtotal); 
									examEngineDAO.putExam(midterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

 
								}else if(StringUtils.equals(sub.getSubjectCode(), math_code)){ 
									double mathscore = 0,mathoutof = 0;
									double mathtotal = 0;
									mathscore = Double.parseDouble(math_score_str); 
									mathoutof = Double.parseDouble(math_outof); 
									if(mathscore > mathoutof){
										mathscore = 0;
									}
									mathtotal = ((mathscore/mathoutof)*50)*2; 
									midterm.setScore(mathtotal);
									examEngineDAO.putExam(midterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), sci_code)){ 
									double sciscore = 0,scioutof = 0;
									double scitotal = 0;
									sciscore = Double.parseDouble(sci_score_str); 
									scioutof = Double.parseDouble(sci_outof); 
									if(sciscore > scioutof){
										sciscore = 0;
									}
									scitotal = ((sciscore/scioutof)*50)*2; 
									midterm.setScore(scitotal);
									examEngineDAO.putExam(midterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), ss_code)){ 
									double ssscore = 0,ssoutof = 0;
									double sstotal = 0;
									ssscore = Double.parseDouble(ss_score_str); 
									ssoutof = Double.parseDouble(ss_outof); 
									if(ssscore > ssoutof){
										ssscore = 0;
									}
									sstotal = (ssscore/ssoutof)*60; 
									midterm.setScore(sstotal); 
									examEngineDAO.putExam(midterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), cre_code)){ 
									double crescore = 0,creoutof = 0;
									double cretotal = 0;
									crescore = Double.parseDouble(cre_score_str); 
									creoutof = Double.parseDouble(cre_outof); 
									if(crescore > creoutof){
										crescore = 0;
									}
									cretotal = (crescore/creoutof)*30; 
									midterm.setScore(cretotal);
									examEngineDAO.putExam(midterm,studentuuid,sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

								}




							}else if(StringUtils.equals(exam, "ENDTERM")) {
								EndTermExam endterm = new EndTermExam();
								endterm.setAccountUuid(school.getUuid()); 
								
								if(StringUtils.equals(sub.getSubjectCode(), eng_code)){ 
									double engscore = 0,engoutof = 0;
									double compscore = 0,compoutof =0;
									double engtotal = 0;
									
									engscore = Double.parseDouble(eng_score_str); 
									compscore = Double.parseDouble(comp_score_str); 
									engoutof = Double.parseDouble(eng_outof); 
									compoutof = Double.parseDouble(comp_outof); 
									if(engscore > engoutof){
										engscore = 0;
									}
									if(compscore > compoutof){
										compscore = 0;
									}
									engscore = (engscore/engoutof)*60;
									compscore = (compscore/compoutof)*40; 
									engtotal = engscore + compscore;
									endterm.setScore(engtotal); 
									examEngineDAO.putExam(endterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

									
								}else if(StringUtils.equals(sub.getSubjectCode(), kisw_code)){ 
									double kiswscore = 0,kiswoutof = 0;
									double inshascore = 0,inshaoutof =0;
									double kiswtotal = 0;
									
									kiswscore = Double.parseDouble(kisw_score_str); 
									inshascore = Double.parseDouble(insha_score_str); 
									kiswoutof = Double.parseDouble(kisw_outof); 
									inshaoutof = Double.parseDouble(insha_outof); 
									if(kiswscore > kiswoutof){
										kiswscore = 0;
									}
									if(inshascore > inshaoutof){
										inshascore = 0;
									}
									kiswscore = (kiswscore/kiswoutof)*60;
									inshascore = (inshascore/inshaoutof)*40; 
									kiswtotal = kiswscore + inshascore;
									endterm.setScore(kiswtotal); 
									examEngineDAO.putExam(endterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

 
								}else if(StringUtils.equals(sub.getSubjectCode(), math_code)){ 
									double mathscore = 0,mathoutof = 0;
									double mathtotal = 0;
									mathscore = Double.parseDouble(math_score_str); 
									mathoutof = Double.parseDouble(math_outof); 
									if(mathscore > mathoutof){
										mathscore = 0;
									}
									mathtotal = ((mathscore/mathoutof)*50)*2; 
									endterm.setScore(mathtotal);
									examEngineDAO.putExam(endterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), sci_code)){ 
									double sciscore = 0,scioutof = 0;
									double scitotal = 0;
									sciscore = Double.parseDouble(sci_score_str); 
									scioutof = Double.parseDouble(sci_outof); 
									if(sciscore > scioutof){
										sciscore = 0;
									}
									scitotal = ((sciscore/scioutof)*50)*2; 
									endterm.setScore(scitotal);
									examEngineDAO.putExam(endterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), ss_code)){ 
									double ssscore = 0,ssoutof = 0;
									double sstotal = 0;
									ssscore = Double.parseDouble(ss_score_str); 
									ssoutof = Double.parseDouble(ss_outof); 
									if(ssscore > ssoutof){
										ssscore = 0;
									}
									sstotal = (ssscore/ssoutof)*60; 
									endterm.setScore(sstotal); 
									examEngineDAO.putExam(endterm,studentuuid, sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());


								}else if(StringUtils.equals(sub.getSubjectCode(), cre_code)){ 
									double crescore = 0,creoutof = 0;
									double cretotal = 0;
									crescore = Double.parseDouble(cre_score_str); 
									creoutof = Double.parseDouble(cre_outof); 
									if(crescore > creoutof){
										crescore = 0;
									}
									cretotal = (crescore/creoutof)*30; 
									endterm.setScore(cretotal);
									examEngineDAO.putExam(endterm,studentuuid,sub.getUuid(),stream.getUuid(), classuuid,systemConfig.getTerm(),systemConfig.getYear());

								}



							}


						}
						//System.out.println("admnostr " + admnostr + " eng_score_str " + eng_score_str + " comp_score_str " + comp_score_str);

					}


					count++;
				}
			}

			catch (Exception e){

			}finally {
				myInput.close();

			}
		}
	}
}
