/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;


/**
 * @author peter
 *
 */
public class StudentExcelUtil {




	String inspectResultFile(File file,String schooluuid,StudentDAO studentDAO, RoomDAO roomDAO) throws IOException {

		String feedback = UploadExcel.UPLOAD_SUCCESS;
		System.out.println("file = "+ file +" schooluuid = "+schooluuid);

		FileInputStream myInput =  new FileInputStream(file);
		System.out.println("myInput = "+ myInput);

		XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		int totalRow = mySheet.getLastRowNum();

		try{

			String admissionNumber = "";
			String firstname = "";
			String middlename = "";
			String classroom = "";
			String gender = "";
			String kcpe = "";

			int count = 1;
			int totalColumn = 0;
			for(int i=0; i<=totalRow; i++){
				XSSFRow row = mySheet.getRow(i);
				if(row !=null){
					totalColumn = row.getLastCellNum();
				}

				for(int j=0; j<totalColumn; j++){
					if(i==0){
						XSSFCell cell1_score = row.getCell((short)1);

						classroom = cell1_score+""; 
						System.out.println("classroom = "+ classroom);

						if(totalColumn >5){
							return ("Invalid Number of comlumns on line \"" + count);
						}

					}else if(i >1){

						admissionNumber = row.getCell(0)+"";
						firstname =  row.getCell(1)+"";
						middlename =  row.getCell(1)+"";
						gender =  row.getCell(1)+"";
						kcpe =  row.getCell(1)+"";
						System.out.println("admissionNumber = "+ admissionNumber);
						System.out.println("firstname = "+ firstname);
						System.out.println("middlename = "+ middlename);
						System.out.println("gender = "+ gender);
						System.out.println("kcpe = "+ kcpe);

					}//end if

					return "my string" + j;
				}
				
				count++;
			}
		}


		catch (Exception e){

		}finally {
			myInput.close();

		}



		return feedback;
	}


	/**
	 * @param uploadedFile
	 * @param school
	 * @param roomDAO
	 * @param primaryDAO
	 * @param studentDAO
	 * @param studentSubjectDAO
	 * @param subjectDAO
	 * @param examConfigDAO
	 * @throws IOException
	 */
	public void saveResults(File uploadedFile,SchoolAccount school, RoomDAO roomDAO, PrimaryDAO primaryDAO,
			StudentDAO studentDAO, StudentSubjectDAO studentSubjectDAO, SubjectDAO subjectDAO,ExamConfigDAO examConfigDAO) throws IOException{

		if(uploadedFile !=null){
			
			
			
		}
	}
	
}
