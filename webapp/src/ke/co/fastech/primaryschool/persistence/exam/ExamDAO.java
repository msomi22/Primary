/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import ke.co.fastech.primaryschool.bean.exam.Exam;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Exam}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamDAO extends GenericDAO implements SchoolExamDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static ExamDAO examDAO;
	/**
	 * 
	 * @return {@link ExamDAO} Instance
	 */
	public static ExamDAO getInstance(){
		if(examDAO == null){
			examDAO = new ExamDAO();
		}
		return examDAO;
	}
	
	/**
	 * 
	 */
	public ExamDAO() {
		super();
	}
	
	/**
	 * Invoke the super {@link GenericDAO} constructor  
	 * 
	 * @param databaseName The database name
	 * @param Host the database host
	 * @param databaseUsername the database user
	 * @param databasePassword the database password
	 * @param databasePort database port
	 */
	public ExamDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamDAO#getExam(java.lang.String)
	 */
	@Override
	public Exam getExam(String uuid) {
		Exam exam = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Exam"
						+ " WHERE uuid = ?;");       
				){

			pstmt.setString(1, uuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				exam  = beanProcessor.toBean(rset,Exam.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Exam with id: " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return exam; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamDAO#getExamByExamname(java.lang.String)
	 */
	@Override
	public Exam getExamByExamname(String examName) {
		Exam exam = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Exam"
						+ " WHERE examName = ?;");       
				){

			pstmt.setString(1, examName); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				exam  = beanProcessor.toBean(rset,Exam.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Exam with examName: " + examName);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 

		}

		return exam; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamDAO#putExam(ke.co.fastech.primaryschool.bean.exam.Exam)
	 */
	@Override
	public boolean putExam(Exam exam) {

		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Exam" 
			        		+"(Uuid,ExamName,OutOf) VALUES (?,?,?);");
		             ){
			   
	            pstmt.setString(1, exam.getUuid());
	            pstmt.setString(2, exam.getExamName());	  
	            pstmt.setInt(3, exam.getOutOf());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Exam "+exam);
         logger.error(ExceptionUtils.getStackTrace(e)); 
         System.out.println(ExceptionUtils.getStackTrace(e));
         success = false;
		 }
		
		return success;
	
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamDAO#updateExam(ke.co.fastech.primaryschool.bean.exam.Exam)
	 */
	@Override
	public boolean updateExam(Exam exam) {
		boolean success = true;
		  try (  Connection conn = dbutils.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement("UPDATE Exam SET OutOf = ?, ExamName = ?"
			        + "WHERE Uuid = ?;");
		           ) {           			 	            
		        pstmt.setInt(1, exam.getOutOf());
		        pstmt.setString(2, exam.getExamName());	
		        pstmt.setString(3, exam.getUuid());       
		        pstmt.executeUpdate();

		} catch (SQLException e) {
		logger.error("SQL Exception when updating update Exam  " + exam);
		logger.error(ExceptionUtils.getStackTrace(e));
		System.out.println(ExceptionUtils.getStackTrace(e));
		success = false;
		} 

		return success;
		
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamDAO#getExamList()
	 */
	@Override
	public List<Exam> getExamList() {
		 List<Exam> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Exam;");   
			) {
			 try(ResultSet rset = pstmt.executeQuery();){
				 list = beanProcessor.toBeanList(rset, Exam.class);
			}
	        

	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Exam List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}








