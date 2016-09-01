/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
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
	public Student getStudentByUuid(String Uuid) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE Uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with studentUuid " + Uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return student; 
	}
	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentBystatus(java.lang.String, java.lang.String)
	 */
	@Override
	public Student getStudentBystatus(String admmissinNo, String statusUuid,String accountUuid) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo = ? AND statusUuid = ? AND accountUuid =?;");       
     		
     		){
     	
     	 pstmt.setString(1, admmissinNo);
     	 pstmt.setString(2, statusUuid);
     	 pstmt.setString(3, accountUuid);
	     rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with admmissinNo " + admmissinNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return student; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentBystream(java.lang.String, java.lang.String)
	 */
	@Override
	public Student getStudentBystream(String admmissinNo, String streamUuid,String accountUuid) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo = ? AND streamUuid = ? AND accountUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, admmissinNo);
     	 pstmt.setString(2, streamUuid);
     	 pstmt.setString(3, accountUuid);
	     rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with admmissinNo " + admmissinNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return student; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentByADMNO(java.lang.String)
	 */
	@Override
	public Student getStudentByADMNO(String admmissinNo,String accountUuid) {
		Student student = null;
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo = ? AND accountUuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, admmissinNo);
     	 pstmt.setString(2, accountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Student with admmissinNo " + admmissinNo);
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
			        		+"(uuid,accountUuid,statusUuid,streamUuid,admmissinNo,firstname,middlename,lastname,gender,"
			        		+ "dateofbirth,birthcertificateNo,country,county,ward,regTerm,"
			        		+ "regYear,finalTerm,finalYear,studentType,studentLevel,admissiondate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
   		){
	            pstmt.setString(1, student.getUuid());
	            pstmt.setString(2, student.getAccountUuid());
	            pstmt.setString(3, student.getStatusUuid());
	            pstmt.setString(4, student.getStreamUuid());
	            pstmt.setString(5, student.getAdmmissinNo());  
	            pstmt.setString(6, student.getFirstname());
	            pstmt.setString(7, student.getMiddlename());
	            pstmt.setString(8, student.getLastname());
	            pstmt.setString(9, student.getGender());
	            pstmt.setString(10, student.getDateofbirth());
	            pstmt.setString(11, student.getBirthcertificateNo());
	            pstmt.setString(12, student.getCountry());
	            pstmt.setString(13, student.getCounty());
	            pstmt.setString(14, student.getWard());
	            pstmt.setInt(15, student.getRegTerm());
	            pstmt.setInt(16, student.getRegYear());
	            pstmt.setInt(17, student.getFinalTerm());
	            pstmt.setInt(18, student.getFinalYear());
	            pstmt.setString(19, student.getStudentType()); 
	            pstmt.setString(20, student.getStudentLevel()); 
	            pstmt.setTimestamp(21, new Timestamp(student.getAdmissiondate().getTime()));
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
			        + "County =?,Ward =?,RegTerm =?,RegYear =?,FinalTerm =?,FinalYear =?,StudentType =?, StudentLevel =? WHERE Uuid = ?;");
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
	            pstmt.setString(17, student.getStudentType()); 
	            pstmt.setString(18, student.getStudentLevel()); 
	            pstmt.setString(19, student.getUuid());
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
	public List<Student> getStudentListByAdmNo(String admmissinNo,String accountUuid) {
		List<Student> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	       PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE admmissinNo ILIKE ? AND accountUuid = ?  LIMIT ? OFFSET ?;");    		   
     	   ) {        
         	   pstmt.setString(1, "%"+admmissinNo+"%"); 
         	   pstmt.setString(2,accountUuid);
         	   pstmt.setInt(3, 15);
         	   pstmt.setInt(4, 0);
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
	public List<Student> getStudentListByStreamUuid(String streamUuid,String accountUuid) {
		List<Student> list = null;

		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Student WHERE streamUuid =? AND accountUuid =? ORDER BY admmissinNo ASC;");   
			) {
			 pstmt.setString(1,streamUuid);
			 pstmt.setString(2,accountUuid);
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
	public List<Student> getStudentsListByLimit(int startIndex, int endIndex,String accountUuid) {
		List<Student> studentList =null;
		
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student  WHERE accountUuid =? ORDER BY admmissinNo DESC LIMIT ? OFFSET ?;");
				) {
			psmt.setString(1, accountUuid); 
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);
			
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
	public List<Student> getStudentsList(String accountUuid) {
	     List<Student> studentList = new ArrayList<>();
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student WHERE accountUuid =? ORDER BY admmissinNo ASC;");
					) {
				   psmt.setString(1,accountUuid);
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

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentCountPerClass(java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCountPerClass(String statusUuid, String streamUuid,String accountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE statusUuid =? AND streamUuid =? AND accountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, statusUuid);
        	pstmt.setString(2, streamUuid);
        	pstmt.setString(3, accountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for stream" + streamUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCount(String statusUuid, String accountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE statusUuid =? AND accountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, statusUuid);
        	pstmt.setString(2, accountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for accountUuid" + accountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentCountByCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCountByCategory(String studentType, String statusUuid, String accountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE studentType =? AND statusUuid =? AND accountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, studentType);
        	pstmt.setString(2, statusUuid);
        	pstmt.setString(3, accountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for category" + studentType);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.SchoolStudentDAO#getStudentCountByLevel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCountByLevel(String studentLevel, String statusUuid, String accountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE studentLevel =? AND statusUuid =? AND accountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, studentLevel);
        	pstmt.setString(2, statusUuid);
        	pstmt.setString(3, accountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for Level" + studentLevel);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	

}
