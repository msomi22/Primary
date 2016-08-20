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

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import ke.co.fastech.primaryschool.bean.exam.MeanScore;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link MeanScore}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class MeanScoreDAO extends GenericDAO implements SchoolMeanScoreDAO {
   
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static MeanScoreDAO meanScoreDAO;
	
	/**
	 * @return {@link MeanScoreDAO} Instance
	 */
	public MeanScoreDAO getInstance(){
		if(meanScoreDAO == null){
			meanScoreDAO = new MeanScoreDAO();
		}
		return meanScoreDAO;
	}
	/**
	 * 
	 */
	public MeanScoreDAO() {
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
	 
	public MeanScoreDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolMeanScoreDAO#getMeanScore(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MeanScore getMeanScore(String studentUuid, String term, String year) {
		MeanScore meanscore = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM MeanScore"
						+ " WHERE studentUuid =? AND term =? AND year =?;");       

				){
			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, term); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				meanscore  = beanProcessor.toBean(rset,MeanScore.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting MeanScore for studentUuid " + studentUuid + " and term " + term + " year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return meanscore; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolMeanScoreDAO#meanExist(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean meanExist(String studentuuid, String term, String year) {
	      boolean studentexist = false;
	  	
			String the_studentUuid = "";
			String the_year = "";
			String the_term = "";
			
			ResultSet rset = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM MeanScore"
							+ " WHERE studentuuid = ? AND term = ? AND year = ?;");       

					){

				pstmt.setString(1, studentuuid); 
				pstmt.setString(2, term);
				pstmt.setString(3, year); 
				rset = pstmt.executeQuery();
				 
	            if(rset.next()){
	            	the_studentUuid = rset.getString("studentuuid");
	            	the_year = rset.getString("year");
	            	the_term = rset.getString("term");
	            	
					studentexist = (the_studentUuid != studentuuid && 
									the_year != year &&
									the_term != term ) ? true : false;
					
				}

			}catch(SQLException e){
				 logger.error("SQL Exception when getting MeanScore for studentuuid " + studentuuid + " term " + term + " and year " + year);
				 logger.error(ExceptionUtils.getStackTrace(e)); 
	             System.out.println(ExceptionUtils.getStackTrace(e));

			}
			return studentexist; 
		}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolMeanScoreDAO#putMeanScore(ke.co.fastech.primaryschool.bean.exam.MeanScore, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putMeanScore(double mean,int streamPosition,int classPosition,String studentuuid, String term, String year) {
		boolean success = true;
		if(!meanExist(studentuuid,term,year)){
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO MeanScore" 
			        		+"(studentuuid,term,year,meanScore,streamPosition,classPosition) VALUES (?,?,?,?,?,?);");
      		){
			    pstmt.setString(1, studentuuid);
	            pstmt.setString(2, term);
	            pstmt.setString(3, year);
	            pstmt.setDouble(4, mean);
	            pstmt.setInt(5, streamPosition);
	            pstmt.setInt(6, classPosition);
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception when trying to put mean " + mean + " streamPosition " + 
		      streamPosition + " classPosition " +  classPosition + " for studentuuid " + 
		      studentuuid + ", term " + term + " and year " + year);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		}else{

			 try (  Connection conn = dbutils.getConnection();
	        	PreparedStatement pstmt = conn.prepareStatement("UPDATE MeanScore SET meanScore = ?,streamPosition = ?, classPosition = ?"
	        			+ " WHERE studentuuid =? AND term = ? AND year = ?;");
	        	) { 
				    pstmt.setDouble(1, mean);
				    pstmt.setInt(2, streamPosition);
				    pstmt.setInt(3, classPosition);
		            pstmt.setString(4, studentuuid);
		            pstmt.setString(5, term);
				    pstmt.setString(6, year);
	                pstmt.executeUpdate(); 

	        } catch (SQLException e) {
	            logger.error("SQL Exception when trying to update mean " + mean + " for studentuuid " + studentuuid +
	            		", term " + term + " and year " + year);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e));
	            success = false;
	        } 
			
			
		}
		
		return success;
	}

}
