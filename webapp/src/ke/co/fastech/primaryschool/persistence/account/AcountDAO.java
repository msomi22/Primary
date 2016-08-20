/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.school.account.Account;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Account}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class AcountDAO extends GenericDAO implements SchoolAcountDAO {

	private static AcountDAO acountDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static AcountDAO getInstance(){
		
		if(acountDAO == null){
			acountDAO = new AcountDAO();		
		}
		return acountDAO;
	}
	
	/**
	 * 
	 */
	public AcountDAO() { 
		super();
	}
	
	
	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public AcountDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolAcountDAO#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String accountUuid) {
		Account account = new Account();
        ResultSet rset = null;
     try(
     		 Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE Uuid = ?;");       
     		
     		){
     	
     	 pstmt.setString(1, accountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 account  = beanProcessor.toBean(rset,Account.class);
	   }
     	
     }catch(SQLException e){
     	  logger.error("SQL Exception when getting Account with accountUuid: " + accountUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
		return account; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolAcountDAO#put(ke.co.fastech.primaryschool.bean.school.account.Account)
	 */
	@Override
	public boolean put(Account account) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Account" 
			        		+"(uuid,schoolName,schoolEmail,schoolPhone,schoolAddres,schoolHomeTown,schoolCounty,schoolMotto,DayBoarding) VALUES (?,?,?,?,?,?,?,?,?);");
    		){
	            pstmt.setString(1, account.getUuid());
	            pstmt.setString(2, account.getSchoolName());
	            pstmt.setString(3, account.getSchoolEmail());
	            pstmt.setString(4, account.getSchoolPhone());
	            pstmt.setString(5, account.getSchoolAddres());
	            pstmt.setString(6, account.getSchoolHomeTown());
	            pstmt.setString(7, account.getSchoolCounty());
	            pstmt.setString(8, account.getSchoolMotto());
	            pstmt.setString(9, account.getDayBoarding());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put Account: " + account);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolAcountDAO#update(ke.co.fastech.primaryschool.bean.school.account.Account)
	 */
	@Override
	public boolean update(Account account) {
		boolean success = true; 
		 try(   Connection conn = dbutils.getConnection();
	      PreparedStatement pstmt = conn.prepareStatement("UPDATE Account SET SchoolName =?,SchoolEmail =?,SchoolPhone =?,"
			+ "SchoolAddres =?,SchoolHomeTown =?,SchoolCounty =?,SchoolMotto =?,Boarding =? WHERE Uuid = ? ;");
      		){
			 
	            pstmt.setString(1, account.getSchoolName());
	            pstmt.setString(2, account.getSchoolEmail());
	            pstmt.setString(3, account.getSchoolPhone());
	            pstmt.setString(4, account.getSchoolAddres());
	            pstmt.setString(5, account.getSchoolHomeTown());
	            pstmt.setString(6, account.getSchoolCounty());
	            pstmt.setString(7, account.getSchoolMotto());
	            pstmt.setString(8, account.getDayBoarding());
	            pstmt.setString(9, account.getUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to update Account: "+account);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolAcountDAO#getAllAccounts()
	 */
	@Override
	public List<Account> getAllAccounts() {
		List<Account> list = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Account;");
				) {
			try(ResultSet rset = psmt.executeQuery();){
				list = beanProcessor.toBeanList(rset, Account.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get Account List");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return list;
	}

}
