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
import ke.co.fastech.primaryschool.bean.exam.ExamResult;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link ExamResult} 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class PerformanceDAO extends GenericDAO implements SchoolPerformanceDAO {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static PerformanceDAO performanceDAO;
	
	/**
	 * @return {@link PerformanceDAO} Instance
	 */
	public static PerformanceDAO getInstance(){
		if(performanceDAO == null){
			performanceDAO = new PerformanceDAO();
		}
		return performanceDAO;
	}

	/**
	 * 
	 */
	public PerformanceDAO() {
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
	public PerformanceDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getStudentPerformanceByStreamId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public List<ExamResult> getStudentPerformanceByStreamId(String studentUuid,String streamUuid,
			String term, String year) {
		List<ExamResult> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Performance WHERE studentUuid = ? AND streamUuid = ? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, studentUuid);      
         	   pstmt.setString(2, streamUuid);  
         	   pstmt.setString(3, term); 
       	       pstmt.setString(4, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, ExamResult.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting ExamResult List for student" + studentUuid + " of streamUuid" + streamUuid +
            		"and term " + term + " and year " + year); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getStudentPerformanceByClassId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public List<ExamResult> getStudentPerformanceByClassId(String studentUuid, String classUuid,
			String term, String year) {
		List<ExamResult> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Performance WHERE studentUuid = ? AND classUuid = ? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, studentUuid);      
         	   pstmt.setString(2, classUuid);  
         	   pstmt.setString(3, term); 
       	       pstmt.setString(4, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, ExamResult.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting ExamResult List for student" + studentUuid + " of classUuid" + classUuid +
            		"and term " + term + " and year " + year); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getStudentDistinctByStreamId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public List<ExamResult> getStudentDistinctByStreamId(String accountUuid,String streamUuid,String term, String year) {
		List<ExamResult> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT studentUuid FROM  Performance WHERE "
     	         		+ " accountUuid = ? AND streamUuid = ? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountUuid); 
         	   pstmt.setString(2, streamUuid); 
         	   pstmt.setString(3, term); 
       	       pstmt.setString(4, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, ExamResult.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Student Distinct List for streamUuid" + streamUuid +
            		"and term " + term + " and year " + year); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getStudentDistinctByClassId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExamResult> getStudentDistinctByClassId(String accountUuid,String classUuid,String term, String year) {
		List<ExamResult> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT studentUuid FROM  Performance WHERE "
     	         		+ "accountUuid = ? AND classUuid = ? AND term = ? AND year = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountUuid);  
         	   pstmt.setString(2, classUuid);  
         	   pstmt.setString(3, term); 
       	       pstmt.setString(4, year); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, ExamResult.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Student Distinct List for classUuid" + classUuid +
            		"and term " + term + " and year " + year); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getSubjectCountPerStream(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getSubjectCountPerStream(String accountUuid,String subjectUuid, String streamUuid, String term, String year) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM performance WHERE accountUuid =? AND subjectuuid =? AND streamuuid =? AND term =? AND year =?;");    		   
 	    ) {
        	   
        	
        	pstmt.setString(1, accountUuid);
        	pstmt.setString(2, subjectUuid);
        	pstmt.setString(3, streamUuid);
        	pstmt.setString(4, term);
        	pstmt.setString(5, year);
        	rset = pstmt.executeQuery();
        
          	  while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
   	       	
        } catch (SQLException e) {
            logger.error("SQLException when getting count from performance:");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolPerformanceDAO#getSubjectCountPerClass(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getSubjectCountPerClass(String accountUuid,String subjectUuid, String classUuid, String term, String year) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM performance WHERE accountUuid =? AND subjectuuid =? AND classUuid =? AND term =? AND year =?;");    		   
 	    ) {
        	   
        	
        	pstmt.setString(1, accountUuid);
        	pstmt.setString(2, subjectUuid);
        	pstmt.setString(3, classUuid);
        	pstmt.setString(4, term);
        	pstmt.setString(5, year);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }

 	       
        } catch (SQLException e) {
            logger.error("SQLException when getting count from performance:");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}


}
