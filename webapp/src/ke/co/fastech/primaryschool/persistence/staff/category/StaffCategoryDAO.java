/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import ke.co.fastech.primaryschool.bean.staff.StaffCategory;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StaffCategory}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffCategoryDAO extends GenericDAO  implements SchoolStaffCategoryDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static StaffCategoryDAO staffCategoryDAO;

	/**
	 * 
	 * @return
	 */
	public static StaffCategoryDAO getInstance(){
		if(staffCategoryDAO == null){
			staffCategoryDAO = new StaffCategoryDAO();
		}
		return staffCategoryDAO;
	}
	/**
	 * 
	 */
	public StaffCategoryDAO() {
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
	public StaffCategoryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolStaffCategoryDAO#getStaffCategory(java.lang.String)
	 */
	@Override
	public StaffCategory getStaffCategory(String staffUuid) {
		StaffCategory StaffCategory = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StaffCategory WHERE staffUuid = ?;");       
     		
     		){
     	     pstmt.setString(1, staffUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	StaffCategory  = beanProcessor.toBean(rset,StaffCategory.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting StaffCategory with staffUuid " + staffUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffCategory; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolStaffCategoryDAO#getStaffCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public StaffCategory getStaffCategory(String staffUuid, String  categoryUuid) {
		StaffCategory StaffCategory = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StaffCategory WHERE staffUuid = ? AND categoryUuid =?;");       
     		
     		){
     	     pstmt.setString(1, staffUuid);
     	     pstmt.setString(2, categoryUuid); 
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	StaffCategory  = beanProcessor.toBean(rset,StaffCategory.class);
	   }
     	
     	
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting StaffCategory with staffUuid " + staffUuid + " and categoryUuid " + categoryUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return StaffCategory; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolStaffCategoryDAO#putStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)
	 */
	@Override
	public boolean putStaffCategory(StaffCategory staffCategory) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StaffCategory" 
			        		+"(Uuid,staffUuid,categoryUuid) VALUES (?,?,?);");
 		){
	            pstmt.setString(1, staffCategory.getUuid());
	            pstmt.setString(2, staffCategory.getStaffUuid());
	            pstmt.setString(3, staffCategory.getCategoryUuid());	                      
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put staffCategory " + staffCategory);
	         logger.error(ExceptionUtils.getStackTrace(e)); 
	         System.out.println(ExceptionUtils.getStackTrace(e));
	         success = false;
		 }	
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolStaffCategoryDAO#updateStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)
	 */
	@Override
	public boolean updateStaffCategory(StaffCategory staffCategory) {
		boolean success = true;		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE StaffCategory SET categoryUuid = ? WHERE staffUuid = ?;");
	) {           			 	            
	            pstmt.setString(1, staffCategory.getCategoryUuid());
	            pstmt.setString(2, staffCategory.getStaffUuid());	     	           
	            pstmt.executeUpdate();

	} catch (SQLException e) {
	  logger.error("SQL Exception when updating StaffCategory " + staffCategory);
	  logger.error(ExceptionUtils.getStackTrace(e));
	  System.out.println(ExceptionUtils.getStackTrace(e));
	  success = false;
	} 
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolStaffCategoryDAO#deleteStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)
	 */
	@Override
	public boolean deleteStaffCategory(String staffUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StaffCategory"
						+ " WHERE staffUuid =?;");       

				){

			pstmt.setString(1, staffUuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Staff Category for staffUuid " + staffUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

}
