/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.bean.student.StudentSubject;
import ke.co.fastech.primaryschool.bean.student.Subject;
import ke.co.fastech.primaryschool.persistence.school.StreamDAO;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;


/**
 * @author peter
 *
 */
public class StudentExcelUtil {
	
	private String[] genderArray;
	private List<String> genderList;
	private String[] categoryArray;
	private List<String> categoryList;
	private String[] levelArray;
	private List<String> levelList;
	
	
	/**
	 * 
	 */
	protected StudentExcelUtil() {
		genderArray = new String[] {"M", "F", "m", "f"};
		genderList = Arrays.asList(genderArray);
		
		categoryArray = new String[] {"Day", "Boarding"};
		categoryList = Arrays.asList(categoryArray);
		
		levelArray = new String[] {"UPPER", "LOWER"};
		levelList = Arrays.asList(levelArray);
	} 

	
	/**
	 * 
	 * @param uploadedFile
	 * @param schooluuid
	 * @param studentDAO
	 * @param streamDAO
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	 
	String processUploadedFiles(File uploadedFile, String schooluuid, StudentDAO studentDAO, StreamDAO streamDAO) throws IOException, InvalidFormatException {
		String feedback = StudentPerclass.UPLOAD_SUCCESS;
		
		if(uploadedFile !=null){
			FileInputStream myInput =  new FileInputStream(uploadedFile);
			
			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();
			
			try{
				
				String stream = "";
				String admno = "";
				String firstname = "";
				String middlename = "";
				String gender = "";
				String stydentType = "";
				String stydentLevel = "";

				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
					  totalColumn = row.getLastCellNum();
					}
					
					for(int j=0; j<totalColumn; j++){
		    			if(i==0){
		    				XSSFCell classroomCell = row.getCell((short)0);
		    				stream = classroomCell+""; 
		              
		    				if(totalColumn >7){
		    					return ("Invalid Number of comlumns on line \"" + count);
		    				}
		    				
		    			}else if(i > 1){
		    				
		    				admno = row.getCell(0)+"";
		    				firstname =  row.getCell(1)+"";
		    				middlename =  row.getCell(2)+"";
		    				//lastname =  row.getCell(3)+"";
		    				gender =  row.getCell(4)+"";
		    				stydentType =  row.getCell(5)+"";
		    				stydentLevel = row.getCell(6)+"";
		    
						if (StringUtils.isBlank(admno) || StringUtils.equalsIgnoreCase(admno, "null")) {
							return ("Invalid/blank admno " + admno + " on line " + count);
						 }
						
						if (StringUtils.isBlank(firstname) || StringUtils.equalsIgnoreCase(firstname, "null")) {
							return ("Invalid/blank firstname " + firstname + " on line " + count);
						 }
						
						if (StringUtils.isBlank(middlename) || StringUtils.equalsIgnoreCase(middlename, "null")) {
							return ("Invalid/blank middlename " + middlename + " on line " + count);
						 }
						
						if (StringUtils.isBlank(gender) || StringUtils.equalsIgnoreCase(gender, "null")) {
							return ("Invalid/blank gender " + gender + " on line " + count);
						 }
						
						if(!genderList.contains(gender)) {
							return ("Invalid gender " + gender + " on line " + count);
						}  
						
						if(!categoryList.contains(stydentType)) {
							return ("Invalid Category " + stydentType + " on line " + count);
						}
						
						if(!levelList.contains(stydentLevel)) {
							return ("Invalid Level " + stydentLevel + " on line " + count);
						}
						
						
						if(studentDAO.getStudentByADMNO(admno.replace(".0", ""),schooluuid) != null){
							return ("Pupil with admission number " + admno.replace(".0", "") + " on line " + count + " already exist.");
						}
						
						if(streamDAO.getStream(stream,schooluuid) ==null){
							return ("Class " + stream + " not found.");
						}
						
			
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
	 * 
	 * @param systemConfigDAO 
	 * @param subjectDAO 
	 * @param studentSubjectDAO 
	 * @param studentDAO 
	 * @param streamDAO 
	 * @param school 
	 * @param uploadedFile 
	 * @param uploadedFile
	 * @param school
	 * @param streamDAO
	 * @param studentDAO
	 * @param studentSubjectDAO
	 * @param subjectDAO
	 * @param systemConfigDAO
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	 
	public void saveResults(File uploadedFile, Account school, StreamDAO streamDAO, StudentDAO studentDAO, StudentSubjectDAO studentSubjectDAO, SubjectDAO subjectDAO, SystemConfigDAO systemConfigDAO) throws IOException, InvalidFormatException{

		if(uploadedFile !=null){
			
        FileInputStream myInput =  new FileInputStream(uploadedFile); 
			
			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();
			
			SystemConfig systemConfig = new SystemConfig();
			systemConfig = systemConfigDAO.getSystemConfig(school.getUuid());  
			try{
				
				String steamname = "";
				String streamuuid = "";
				String admno = "";
				String firstname = "";
				String middlename = "";
				String lastname = "";
				String gender = "";
				String category = "";
				String level = "";
				
				Calendar calendar = Calendar.getInstance();
				final int YEAR = calendar.get(Calendar.YEAR);

				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
					  totalColumn = row.getLastCellNum();
					}
					
					for(int j=0; j<totalColumn; j++){
		    			if(i==0){
		    				XSSFCell classroomCell = row.getCell((short)0);
		    				steamname = classroomCell+""; 
		    				Stream stream = new Stream();
		    				if(streamDAO.getStream(steamname,school.getUuid()) !=null){
		    					stream = streamDAO.getStream(steamname,school.getUuid());
		    					streamuuid = stream.getUuid();
		    				}
		    				
		    			}else if(i > 1){

		    				admno = row.getCell(0)+"";
		    				firstname =  row.getCell(1)+"";
		    				middlename =  row.getCell(2)+"";
		    				lastname =  row.getCell(3)+"";
		    				gender =  row.getCell(4)+"";
		    				category =  row.getCell(5)+"";
		    				level = row.getCell(6)+"";
		    				
		    			}//end if
		    			
					}
					
					//save details
					if(!StringUtils.isBlank(admno) &&
							!StringUtils.isBlank(firstname)
							&& !StringUtils.isBlank(middlename)
							&& !StringUtils.isBlank(gender)
							&& !StringUtils.isBlank(category)
							&& !StringUtils.isBlank(level)){
						
						if (StringUtils.isBlank(lastname) || StringUtils.equalsIgnoreCase(lastname, "null")) {
							lastname = " ";
						}
						
						String bcertNo = " ";
						String country = "Kenya";
						String county = " ";
						String ward = " ";
					
						admno = admno.replace(".0", "");
	    				
						Student student = new Student();
	    				student.setAccountUuid(school.getUuid());  
	    				student.setStatusUuid(ExamConstants.STATUS_ACTIVE); 
	    				student.setStreamUuid(streamuuid); 
	    				student.setAdmmissinNo(admno); 
	    				student.setFirstname(StringUtils.capitalize(firstname.toLowerCase()));
	    				student.setMiddlename(StringUtils.capitalize(middlename.toLowerCase()));
	    				student.setLastname(StringUtils.capitalize(lastname.toLowerCase())); 
	    				student.setGender(gender.toUpperCase()); 
	    				student.setDateofbirth("1"+"/"+"12"+"/"+YEAR); 
	    				student.setBirthcertificateNo(bcertNo);
	    				student.setCountry(country);
	    				student.setCounty(county);
	    				student.setWard(ward); 
	    				student.setRegTerm(Integer.parseInt(systemConfig.getTerm()));  
	    				student.setRegYear(Integer.parseInt(systemConfig.getYear()));  
	    				student.setFinalYear(YEAR+3); 
	    				student.setFinalTerm(3); 
	    				student.setStudentType(category);
	    				student.setStudentLevel(level); 
	    				

	    				if(studentDAO.getStudentByADMNO(admno,school.getUuid()) == null){ 
	    				  
	    				   if(studentDAO.putStudent(student)){
	   	    			   //assign subject
		    				List<Subject> subjectList = new ArrayList<>();
		    				subjectList = subjectDAO.getSubjectList();
		    				for(Subject sub : subjectList){
		    					StudentSubject sb = new StudentSubject(); 
		    					sb.setSubjectUuid(sub.getUuid()); 
		    					sb.setStudentUuid(student.getUuid());
		    					if(studentSubjectDAO.getStudentSubject(student.getUuid(), sub.getUuid()) == null){
		    					   studentSubjectDAO.putStudentSubject(sb); 
		    					}
		    				}
	                       //subjects assigned
							
	    				   }
	    				   
	    				}
	    			
					}
    		
				}
				
				
			}
			
		
			catch (Exception e){

			}finally {
				myInput.close();

			}
		}
		
	}
	
}
