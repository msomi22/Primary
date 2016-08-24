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

import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;

public class PerClassExcelUtil {

	private String[] examcodeArray;
	private List<String> examcodeList;

	String classesuuid = "";

	/**
	 * 
	 */
	protected PerClassExcelUtil() {
		examcodeArray = new String[] {"OPENER", "MIDTERM", "ENDTERM"};
		examcodeList = Arrays.asList(examcodeArray);
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

				String classroom = "";
				String exam = "";

				String filename = uploadedFile.getName().replaceAll("_", " "); 

				String [] parts = filename.split("\\.");
				classroom = parts[0]; 
				exam = parts[1];

				if(streamDAO.getStream(classroom) ==null){
					return ("Class code \"" + classroom + "\" not found! ");
				}

				String code = StringUtils.lowerCase(StringUtils.trimToEmpty(exam) );
				if(!examcodeList.contains(code)) {
					return ("Invalid exam code \"" + code.toUpperCase()+"\"");
				}

				
				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);

					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i==0){

							XSSFCell eng_cell = row.getCell((short)2);
							XSSFCell comp_cell = row.getCell((short)3);
							XSSFCell kisw_cell = row.getCell((short)4);
							XSSFCell insha_cell = row.getCell((short)5);
							
							XSSFCell math_scell = row.getCell((short)6);
							XSSFCell sci_cell = row.getCell((short)7);

							XSSFCell ss_cell = row.getCell((short)8);
							XSSFCell cre_cell = row.getCell((short)9);
						
							if(totalColumn > 10){
								return ("Invalid Number of comlumns on line \"" + count);
							}



						}else if(i >0){

							


						}//end if


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
	 
	public void saveResults(File uploadedFile, Account school, ExamEngineDAO examEngineDAO, StudentDAO studentDAO, StreamDAO streamDAO, SubjectDAO subjectDAO, SystemConfigDAO systemConfigDAO) throws IOException{

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
				SystemConfig  examConfig = systemConfigDAO.getSystemConfig(school.getUuid());
				String [] parts = filename.split("\\.");
				classroom = parts[0]; 
				exam = parts[1];

			}


			catch (Exception e){

			}finally {
				myInput.close();

			}
		}
	}
}
