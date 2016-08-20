/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import ke.co.fastech.primaryschool.bean.staff.Staff;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Staff}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffDAO extends GenericDAO  implements SchoolStaffDAO {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static StaffDAO staffDAO;

	/**
	 * 
	 * @return
	 */
	public static StaffDAO getInstance(){
		if(staffDAO == null){
			staffDAO = new StaffDAO();
		}
		return staffDAO;
	}
	/**
	 * 
	 */
	public StaffDAO() {
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
	public StaffDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String)
	 */
	@Override
	public Staff getStaff(String uuid) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE Uuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, uuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with uuid " + uuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return staff; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolStaffDAO#getStaff(java.lang.String, java.lang.String)
	 */
	@Override
	public Staff getStaff(String uuid, String employeeNo) {
		Staff staff = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Staff WHERE uuid = ? AND employeeNo = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, uuid);
     	     pstmt.setString(2, employeeNo);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	    	 staff  = beanProcessor.toBean(rset,Staff.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Staff with Uuid " + uuid + " and employeeNo " + employeeNo);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return staff; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolStaffDAO#putStaff(ke.co.fastech.primaryschool.bean.staff.Staff)
	 */
	@Override
	public boolean putStaff(Staff staff) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Staff" 
			        		+"(uuid,accountUuid,statusUuid,employeeNo,name,phone,email,gender,dob,country,county,ward,regDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
  		){
	            pstmt.setString(1, staff.getUuid());
	            pstmt.setString(2, staff.getAccountUuid());
	            pstmt.setString(3, staff.getStatusUuid());
	            pstmt.setString(4, staff.getEmployeeNo());
	            pstmt.setString(5, staff.getName());
	            pstmt.setString(6, staff.getPhone());
	            pstmt.setString(7, staff.getEmail());
	            pstmt.setString(8, staff.getGender());
	            pstmt.setString(9, staff.getDob());
	            pstmt.setString(10, staff.getCountry());
	            pstmt.setString(11, staff.getCounty());
	            pstmt.setString(12, staff.getWard());
	            pstmt.setTimestamp(13, new Timestamp(staff.getRegDate().getTime()));  
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Staff " + staff);
          logger.error(ExceptionUtils.getStackTrace(e)); 
          System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }	
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolStaffDAO#updateStaff(ke.co.fastech.primaryschool.bean.staff.Staff)
	 */
	@Override
	public boolean updateStaff(Staff staff) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Staff SET StatusUuid =?,EmployeeNo =?,Name =?,Phone =?,Email =?,"
			+ "Gender = ?,Dob = ?,Country = ?,County = ?, Ward = ? WHERE Uuid =? ;");
    		){			   
	            
			   
	            pstmt.setString(1, staff.getStatusUuid());
	            pstmt.setString(2, staff.getEmployeeNo());
	            pstmt.setString(3, staff.getName());
	            pstmt.setString(4, staff.getPhone());
	            pstmt.setString(5, staff.getEmail());
	            pstmt.setString(6, staff.getGender());
	            pstmt.setString(7, staff.getDob());
	            pstmt.setString(8, staff.getCountry());
	            pstmt.setString(9, staff.getCounty());
	            pstmt.setString(10, staff.getWard());
	            pstmt.setString(11, staff.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to update Staff: "+staff);
          logger.error(ExceptionUtils.getStackTrace(e)); 
          System.out.println(ExceptionUtils.getStackTrace(e));
          success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolStaffDAO#getStaffList()
	 */
	@Override
	public List<Staff> getStaffList() {
		 List<Staff> list = null;
		 try(   
	  		Connection conn = dbutils.getConnection();
	  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Staff;");   
			) {
			 try(ResultSet rset = pstmt.executeQuery();){
					
				 list = beanProcessor.toBeanList(rset, Staff.class);
				}
	        
	  } catch(SQLException e){
	  	 logger.error("SQL Exception when getting Staff List");
	     logger.error(ExceptionUtils.getStackTrace(e));
	     System.out.println(ExceptionUtils.getStackTrace(e)); 
	  }
		return list;
	}

}
