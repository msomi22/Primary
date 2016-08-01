/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Student}
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentDAO extends GenericDAO implements SchoolStudentDAO {
	
	private static StudentDAO studentDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentDAO getInstance(){
		
		if(studentDAO == null){
			studentDAO = new StudentDAO();		
		}
		return studentDAO;
	}
	
	/**
	 * 
	 */
	public StudentDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public StudentDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentByUuid(java.lang.String)
	 */
	@Override
	public Student getStudentByUuid(String studentUuid) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE studentUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, studentUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with studentUuid: " + studentUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return student; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentByADMNO(java.lang.String)
	 */
	@Override
	public Student getStudentByADMNO(String admmissinNo) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, admmissinNo);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with admmissinNo: " + admmissinNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return student; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#putStudent(ke.co.fastech.primaryschool.bean.student.Student)
	 */
	@Override
	public boolean putStudent(Student student) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student" 
			        		+"(uuid,statusUuid,streamUuid,admmissinNo,firstname,middlename,lastname,gender"
			        		+ "dateofbirth,birthcertificateNo,country,county,ward,regTerm"
			        		+ "regYear,finalTerm,finalYear,admissiondate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?.?,?);");
   		){
	            pstmt.setString(1, student.getUuid());
	            pstmt.setString(2, student.getStatusUuid());
	            pstmt.setString(3, student.getStreamUuid());
	            pstmt.setString(4, student.getAdmmissinNo());  
	            pstmt.setString(5, student.getFirstname());
	            pstmt.setString(6, student.getMiddlename());
	            pstmt.setString(7, student.getLastname());
	            pstmt.setString(8, student.getGender());
	            pstmt.setString(9, student.getDateofbirth());
	            pstmt.setString(10, student.getBirthcertificateNo());
	            pstmt.setString(11, student.getCountry());
	            pstmt.setString(12, student.getCounty());
	            pstmt.setString(13, student.getWard());
	            pstmt.setInt(14, student.getRegTerm());
	            pstmt.setInt(15, student.getRegYear());
	            pstmt.setInt(16, student.getFinalTerm());
	            pstmt.setInt(17, student.getFinalYear());
	            pstmt.setTimestamp(18, new Timestamp(student.getAdmissiondate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Student: " + student);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#updateStudent(ke.co.fastech.primaryschool.bean.student.Student)
	 */
	@Override
	public boolean updateStudent(Student student) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Student SET StatusUuid =?, StreamUuid =?," 
			        +"AdmmissinNo =?,Firstname =?,Middlename =?,Lastname =?,"
			        + "Gender =?,Dateofbirth =?,BirthcertificateNo=?,Country =?,"
			        + "County =?,Ward =?,RegTerm =?,RegYear =?,FinalTerm =?,FinalYear =? WHERE Uuid = ?;");
		){
			  
			 
	            pstmt.setString(1, student.getStatusUuid());
	            pstmt.setString(2, student.getStreamUuid());
	            pstmt.setString(3, student.getAdmmissinNo());  
	            pstmt.setString(4, student.getFirstname());
	            pstmt.setString(5, student.getMiddlename());
	            pstmt.setString(6, student.getLastname());
	            pstmt.setString(7, student.getGender());
	            pstmt.setString(8, student.getDateofbirth());
	            pstmt.setString(9, student.getBirthcertificateNo());
	            pstmt.setString(10, student.getCountry());
	            pstmt.setString(11, student.getCounty());
	            pstmt.setString(12, student.getWard());
	            pstmt.setInt(13, student.getRegTerm());
	            pstmt.setInt(14, student.getRegYear());
	            pstmt.setInt(15, student.getFinalTerm());
	            pstmt.setInt(16, student.getFinalYear());
	            pstmt.setString(17, student.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update Student: "+student);
             logger.error(ExceptionUtils.getStackTrace(e));  
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentListByAdmNo(java.lang.String)
	 */
	@Override
	public List<Student> getStudentListByAdmNo(String admmissinNo) {
		List<Student> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	       PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo ILIKE ? LIMIT ? OFFSET ?;");    		   
     	   ) {        
         	   pstmt.setString(1, "%"+admmissinNo+"%"); 
         	   pstmt.setInt(2, 15);
         	   pstmt.setInt(3, 0);
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Student.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Student with  admmissinNo" + admmissinNo);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentListByStreamUuid(java.lang.String)
	 */
	@Override
	public List<Student> getStudentListByStreamUuid(String streamUuid) {
		List<Student> list = null;

		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Student WHERE streamUuid =? ORDER BY admmissinNo ASC;");   
			) {
			 pstmt.setString(1,streamUuid);
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, Student.class);
				}
	        

	  } catch(SQLException e){
	   	 logger.error("SQL Exception when getting all Student for class " + streamUuid);
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }

		
		return list;
		}
	

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	@Override
	public List<Student> getStudentsListByLimit(int startIndex, int endIndex) {
		List<Student> studentList =null;
		
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student ORDER BY admmissinNo DESC LIMIT ? OFFSET ?;");
				) {
			psmt.setInt(1, endIndex - startIndex);
			psmt.setInt(2, startIndex);
			
			try(ResultSet rset = psmt.executeQuery();){
				studentList = beanProcessor.toBeanList(rset, Student.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student List with an index and offset.");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return studentList;		
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentsList()
	 */
	@Override
	public List<Student> getStudentsList() {
	     List<Student> studentList = new ArrayList<>();
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student ORDER BY admmissinNo ASC;");
					) {
				
				try(ResultSet rset = psmt.executeQuery();){
				
					studentList = beanProcessor.toBeanList(rset, Student.class);
				}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get Student List");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			return studentList;
		}

}
