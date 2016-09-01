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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import ke.co.fastech.primaryschool.bean.exam.EndTermExam;
import ke.co.fastech.primaryschool.bean.exam.MidTermExam;
import ke.co.fastech.primaryschool.bean.exam.OpenerExam;
import ke.co.fastech.primaryschool.bean.exam.Performance;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Performance}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamEngineDAO extends GenericDAO implements SchoolExamEngineDAO {
	
	public static ExamEngineDAO examEngineDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * @return {@link ExamEngineDAO} Instance 
	 */
	public static ExamEngineDAO getInstance(){
		if(examEngineDAO == null){
			examEngineDAO = new ExamEngineDAO();
		}
		return examEngineDAO;
	}

	/**
	 * 
	 */
	public ExamEngineDAO() {
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
	public ExamEngineDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamEngineDAO#recordExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public boolean recordExist(String studentUuid, String subjectUuid, String streamUuid, String classUuid, String term,
			String year) {

		boolean studentexist = false;

		String studentid = "";
		String subjectid = "";
		String streamid = "";
		String classid = "";
		String termid = "";
		String yearid = "";
		
		ResultSet rset = null;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT studentUuid,subjectUuid,streamUuid,classUuid,term,year FROM Performance "
	        			+ "WHERE studentUuid = ? AND subjectUuid = ? AND streamUuid = ?  AND classUuid = ? AND term = ? AND year = ?;");
      		){

	            pstmt.setString(1, studentUuid);
	            pstmt.setString(2, subjectUuid);
	            pstmt.setString(3, streamUuid);
	            pstmt.setString(4, classUuid);
	            pstmt.setString(5, term);
	            pstmt.setString(6, year);
	            rset = pstmt.executeQuery();
	            
	            if(rset.next()){
	            	studentid = rset.getString("studentUuid");
	            	subjectid = rset.getString("subjectUuid");
	            	streamid = rset.getString("streamUuid");
	            	classid = rset.getString("classUuid");
	            	termid  = rset.getString("term");
	            	yearid  = rset.getString("year");
					
					studentexist = (studentid != studentUuid &&
							        subjectid != subjectUuid && 
							        streamid != streamUuid && 
							        classid != classUuid && 
							        termid != term && 
							        yearid != year) ? true : false;
					
					
				}
			
			
		  }
		     catch(SQLException e){
			 logger.error("SQL Exception while getting Performance with  studentUuid" + studentUuid + " and subjectUuid" +
		     subjectUuid + " and streamUuid " + streamUuid + " and classUuid " + classUuid + " and term " + term + " and year" + year);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
            
		 }
		

		return studentexist;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamEngineDAO#putExam(ke.co.fastech.primaryschool.bean.exam.Performance, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public boolean putExam(Performance performance, String studentUuid, String subjectUuid, String streamUuid,
			String classUuid, String term, String year) {

		boolean success = true;
		if(!recordExist(studentUuid,subjectUuid,streamUuid,classUuid,term,year)) {
		 try( Connection conn = dbutils.getConnection();
				
				PreparedStatement pstmtopener = conn.prepareStatement("INSERT INTO Performance"
						+"(AccountUuid,studentUuid,subjectUuid,streamUuid,classUuid,term,year,openner) VALUES (?,?,?,?,?,?,?,?);");
				
				PreparedStatement pstmtmidterm = conn.prepareStatement("INSERT INTO Performance"
						+"(AccountUuid,studentUuid,subjectUuid,streamUuid,classUuid,term,year,midterm) VALUES (?,?,?,?,?,?,?,?);");
				
				PreparedStatement pstmtendterm = conn.prepareStatement("INSERT INTO Performance"
						+"(AccountUuid,studentUuid,subjectUuid,streamUuid,classUuid,term,year,endterm) VALUES (?,?,?,?,?,?,?,?);");
				
				){

			if(performance instanceof OpenerExam) {
				pstmtopener.setString(1, performance.getAccountUuid());
				pstmtopener.setString(2, studentUuid);
				pstmtopener.setString(3, subjectUuid);
				pstmtopener.setString(4, streamUuid);
				pstmtopener.setString(5, classUuid);
				pstmtopener.setString(6, term);
				pstmtopener.setString(7, year);
				pstmtopener.setDouble(8, performance.getScore());
				pstmtopener.executeUpdate();
			}
			else if(performance instanceof MidTermExam) {
				pstmtmidterm.setString(1, performance.getAccountUuid());
				pstmtmidterm.setString(2, studentUuid);
				pstmtmidterm.setString(3, subjectUuid);
				pstmtmidterm.setString(4, streamUuid);
				pstmtmidterm.setString(5, classUuid);
				pstmtmidterm.setString(6, term);
				pstmtmidterm.setString(7, year);
				pstmtmidterm.setDouble(8, performance.getScore());
				pstmtmidterm.executeUpdate();
			}
			else if(performance instanceof EndTermExam) {
				pstmtendterm.setString(1, studentUuid);
				pstmtendterm.setString(1, performance.getAccountUuid());
				pstmtendterm.setString(2, studentUuid);
				pstmtendterm.setString(3, subjectUuid);
				pstmtendterm.setString(4, streamUuid);
				pstmtendterm.setString(5, classUuid);
				pstmtendterm.setString(6, term);
				pstmtendterm.setString(7, year);
				pstmtendterm.setDouble(8, performance.getScore());
				pstmtendterm.executeUpdate();
			}
			
		

		}catch(SQLException e){
			logger.error("SQL Exception while trying to insert score " + performance.getScore() + "studentUuid" + studentUuid + " and subjectUuid" +
				     subjectUuid + " and streamUuid " + streamUuid + " and classUuid " + classUuid + " and term " + term + " and year" + year);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	
	
		
		
		} else { 
			
			      try(
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmtopener = conn.prepareStatement("UPDATE performance SET openner =?" 
							+"WHERE studentUuid =? AND subjectUuid =? AND streamUuid = ? AND classUuid = ? "
							+ "AND term = ? AND year = ?;");	
			    		  
			    	PreparedStatement pstmtmidterm = conn.prepareStatement("UPDATE performance SET midterm =?" 
							+"WHERE studentUuid =? AND subjectUuid =? AND streamUuid = ? AND classUuid = ? "
							+ "AND term = ? AND year = ?;");	
			    		  
			        PreparedStatement pstmtendterm = conn.prepareStatement("UPDATE performance SET endterm =?" 
							+"WHERE studentUuid =? AND subjectUuid =? AND streamUuid = ? AND classUuid = ? "
							+ "AND term = ? AND year = ?;");	
			    	
					) {
				if(performance instanceof OpenerExam) {
					
					pstmtopener.setDouble(1, performance.getScore());
					pstmtopener.setString(2, studentUuid);
					pstmtopener.setString(3, subjectUuid);
					pstmtopener.setString(4, streamUuid);
					pstmtopener.setString(5, classUuid);
					pstmtopener.setString(6, term);
					pstmtopener.setString(7, year);
					pstmtopener.executeUpdate();
				}	
				else if(performance instanceof MidTermExam) {
					
					pstmtmidterm.setDouble(1, performance.getScore());
					pstmtmidterm.setString(2, studentUuid);
					pstmtmidterm.setString(3, subjectUuid);
					pstmtmidterm.setString(4, streamUuid);
					pstmtmidterm.setString(5, classUuid);
					pstmtmidterm.setString(6, term);
					pstmtmidterm.setString(7, year);
					pstmtmidterm.executeUpdate();
			   }	
               else if(performance instanceof EndTermExam) {
					
            	   pstmtendterm.setDouble(1, performance.getScore()); 
            	   pstmtendterm.setString(2, studentUuid);
            	   pstmtendterm.setString(3, subjectUuid);
            	   pstmtendterm.setString(4, streamUuid);
            	   pstmtendterm.setString(5, classUuid);
            	   pstmtendterm.setString(6, term);
            	   pstmtendterm.setString(7, year);
            	   pstmtendterm.executeUpdate();
			   }	
               		
			} catch(SQLException e) {
				logger.error("SQL Exception while trying to update score " + performance.getScore() + "studentUuid" + studentUuid + " and subjectUuid" +
					     subjectUuid + " and streamUuid " + streamUuid + " and classUuid " + classUuid + " and term " + term + " and year" + year);
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
		return success;
	
	}

	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolExamEngineDAO#deletePerformance(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean deletePerformance(String studentUuid,String term, String year) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM performance WHERE studentUuid = ? AND term = ? AND year = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
        	 pstmt.setString(2, term);
        	 pstmt.setString(3, year);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting " + studentUuid + " for term " + term +" and year " + year);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

}
