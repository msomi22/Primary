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

import ke.co.fastech.primaryschool.bean.exam.BarWeight;
import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link BarWeight}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class BarWeightDAO extends GenericDAO implements SchoolBarWeightDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static BarWeightDAO barWeightDAO;
	/**
	 * 
	 * @return {@link BarWeightDAO} Instance
	 */
	public static BarWeightDAO getInstance(){
		if(barWeightDAO == null){
				barWeightDAO = new BarWeightDAO();
		}
		return barWeightDAO;
	}
	
	/**
	 * 
	 */
	public BarWeightDAO() {
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
	public BarWeightDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolBarWeightDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BarWeight getBarWeight(String studentUuid, String term, String year) {
		BarWeight barWeight = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
						+ " WHERE studentUuid =? AND term =? AND year =?;");       

				){
			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, term); 
			pstmt.setString(3, year); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				barWeight  = beanProcessor.toBean(rset,BarWeight.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting BarWeight for studentUuid " + studentUuid + " and term " + term + " year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));

		}
     
		return barWeight; 
	}
	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolBarWeightDAO#getBarWeight(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BarWeight> getBarWeightList(String studentuuid, String year) {
		List<BarWeight> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight WHERE studentuuid = ? AND year =?;");    		   
     	   ) {
         	   pstmt.setString(1, studentuuid);
         	   pstmt.setString(2, year);
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, BarWeight.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting BarWeight List for studentuuid " + studentuuid + " and year" + year); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolBarWeightDAO#existWeight(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean existWeight(String studentuuid, String term, String year) {
	      boolean studentexist = false;
		  	
			String the_studentUuid = "";
			String the_year = "";
			String the_term = "";
			
			ResultSet rset = null;
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BarWeight"
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
				 logger.error("SQL Exception when getting BarWeight for studentuuid " + studentuuid + " term " + term + " and year " + year);
				 logger.error(ExceptionUtils.getStackTrace(e)); 
	             System.out.println(ExceptionUtils.getStackTrace(e));

			}
			return studentexist; 
		}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolBarWeightDAO#putWeight(ke.co.fastech.primaryschool.bean.exam.BarWeight)
	 */
	@Override
	public boolean putWeight(double weight,String studentuuid,String term,String year) {
		boolean success = true;
		if(!existWeight(studentuuid,term,year)){
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BarWeight" 
			        		+"(studentuuid,term,year,weight) VALUES (?,?,?,?);");
      		){
			    pstmt.setString(1, studentuuid);
	            pstmt.setString(2, term);
	            pstmt.setString(3, year);
	            pstmt.setDouble(4, weight);
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception when trying to put weight " + weight + " for studentuuid " + 
		      studentuuid + ", term " + term + " and year " + year);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		}else{

			 try (  Connection conn = dbutils.getConnection();
	        	PreparedStatement pstmt = conn.prepareStatement("UPDATE BarWeight SET weight = ?"
	        			+ " WHERE studentuuid =? AND term = ? AND year = ?;");
	        	) { 
				    pstmt.setDouble(1, weight);
		            pstmt.setString(2, studentuuid);
		            pstmt.setString(3, term);
				    pstmt.setString(4, year);
	                pstmt.executeUpdate(); 

	        } catch (SQLException e) {
	            logger.error("SQL Exception when trying to update weight " + weight + " for studentuuid " + studentuuid +
	            		", term " + term + " and year " + year);
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e));
	            success = false;
	        } 
			
			
		}
		
		return success;
	}


}
