/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.money.pocket.Deposit;
import ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney;
import ke.co.fastech.primaryschool.bean.money.pocket.Withdraw;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link PocketMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class PocketMoneyDAO  extends GenericDAO  implements SchoolPocketMoneyDAO {

	private static PocketMoneyDAO pocketMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return 
	 * 
	 */
	public static PocketMoneyDAO getInstance() {
		if(pocketMoneyDAO == null){
			pocketMoneyDAO = new PocketMoneyDAO();		
			}
		return pocketMoneyDAO;
	}
	
	public PocketMoneyDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public PocketMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolPocketMoneyDAO#getPocketMoney(java.lang.String)
	 */
	@Override
	public PocketMoney getPocketMoney(String studentUuid) {
		PocketMoney pMoney = null;
		ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PocketMoney WHERE studentUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, studentUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 pMoney  = beanProcessor.toBean(rset,PocketMoney.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception while getting PocketMoney with studentUuid " + studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e)); 
        }
    
		return pMoney;
	}
	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolPocketMoneyDAO#studentExist(java.lang.String)
	 */
	@Override
	public boolean studentExist(String studentUuid) {
		boolean exixt = false;
		String studentid = "";
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT StudentUuid FROM PocketMoney "
	        			+ "WHERE StudentUuid = ?;");
      		){
			 
	            pstmt.setString(1, studentUuid);
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						studentid = rset.getString("studentUuid");	
						exixt = (StringUtils.equals(studentid, studentUuid)) ? true : false;		
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get student PocketMoney for studentUuid " + studentUuid);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             exixt = false;
		 }
		
		return exixt;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolPocketMoneyDAO#hasBalance(java.lang.String, int)
	 */
	@Override
	public boolean hasBalance(String studentUuid, int balance) {
		boolean hasBalance = false;
		double bal = 0;
		try(    Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT balance FROM PocketMoney "
	        			+ "WHERE StudentUuid = ?;");
      		){
			 
	            pstmt.setString(1,studentUuid);
	            try(
						ResultSet rset = pstmt.executeQuery();
						
						) {
					
					if(rset.next()) {
						bal = rset.getDouble("balance");	
						hasBalance = (bal >= balance) ? true : false;	
					} 
				}
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to get balance for student " + studentUuid );
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             hasBalance = false;
		 }
		
		return hasBalance;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolPocketMoneyDAO#deposit(ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney, java.lang.String, int)
	 */
	@Override
	public boolean deposit(PocketMoney pocketMoney, String studentUuid, int balance) {
		boolean success = true;
		if(studentExist(studentUuid)) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET balance = (SELECT balance FROM PocketMoney WHERE StudentUuid=? "
							+ ") + ? " +				
							"WHERE Uuid = (SELECT Uuid FROM PocketMoney WHERE StudentUuid=? );");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(Uuid,"
							+ "studentUuid,term,year,amountDeposited,dateDeposited) VALUES(?,?,?,?,?,?);");
					) {
				
					pstmt.setString(1, studentUuid);									
					pstmt.setDouble(2, balance);
					pstmt.setString(3,studentUuid);
					pstmt.executeUpdate();
					
					if(pocketMoney instanceof Deposit){
						pstmt2.setString(1, pocketMoney.getUuid());	
						pstmt2.setString(2, studentUuid);
						pstmt2.setString(3, pocketMoney.getTerm());
						pstmt2.setString(4, pocketMoney.getYear());
						pstmt2.setDouble(5, balance);
						pstmt2.setTimestamp(6, new Timestamp(pocketMoney.getDate().getTime()));
						pstmt2.executeUpdate();
						
						
					}
				
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of studentUuid '" + studentUuid +
						"' of amount " + balance +"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				 System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
			
			
		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(Uuid,"
							+ "studentUuid,balance) VALUES(?,?,?);");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Deposit(Uuid,"
							+ "studentUuid,term,year,amountDeposited,dateDeposited) VALUES(?,?,?,?,?,?);");
					) {
					pstmt.setString(1, pocketMoney.getUuid());
					pstmt.setString(2, studentUuid);									
					pstmt.setDouble(3, balance);
					pstmt.executeUpdate();	
					
					if(pocketMoney instanceof Deposit){
						pstmt2.setString(1, pocketMoney.getUuid());									
						pstmt2.setString(2, studentUuid);
						pstmt2.setString(3, pocketMoney.getTerm());
						pstmt2.setString(4, pocketMoney.getYear());
						pstmt2.setDouble(5, balance);
						pstmt2.setTimestamp(6, new Timestamp(pocketMoney.getDate().getTime()));
						pstmt2.executeUpdate();
						
					}
						
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of student '" + studentUuid +
						"' of amount " + balance + "'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				 System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
						
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolPocketMoneyDAO#withdraw(ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney, java.lang.String, int)
	 */
	@Override
	public boolean withdraw(PocketMoney pocketMoney, String studentUuid, int balance) {
		boolean success = true;
		if(hasBalance(studentUuid,0)) {
			try( 
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("UPDATE PocketMoney " +
							"SET balance = (SELECT balance FROM PocketMoney WHERE StudentUuid=? "
							+ ") - ? " +				
							"WHERE Uuid = (SELECT Uuid FROM PocketMoney WHERE StudentUuid=? );");
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(Uuid,"
							+ "studentUuid,term,year,amountWithdrawn,dateWithdrawn) VALUES(?,?,?,?,?,?);");
					) {
				
					pstmt.setString(1, studentUuid);									
					pstmt.setDouble(2, balance);
					pstmt.setString(3, studentUuid);
					pstmt.executeUpdate();
					
					if(pocketMoney instanceof Withdraw){
						pstmt2.setString(1, pocketMoney.getUuid());									
						pstmt2.setString(2, studentUuid);
						pstmt2.setString(3, pocketMoney.getTerm());
						pstmt2.setString(4, pocketMoney.getYear());
						pstmt2.setDouble(5, balance);
						pstmt2.setTimestamp(6, new Timestamp(pocketMoney.getDate().getTime()));
						pstmt2.executeUpdate();
						
						
					}
				
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by updating the balance of student '" + studentUuid +
			    "' of amount " + balance +"'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
			
			
		} else { 
			try(	
					Connection conn = dbutils.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PocketMoney(Uuid,"
							+ "Studentuuid,balance) VALUES(?,?,?);");	
					
					PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO Withdraw(Uuid,"
							+ "studentUuid,term,year,amountWithdrawn,dateWithdrawn) VALUES(?,?,?,?,?,?);");
					) {
					pstmt.setString(1, pocketMoney.getUuid());
					pstmt.setString(2, pocketMoney.getStudentUuid());									
					pstmt.setDouble(3, balance);
					pstmt.executeUpdate();	
					
					if(pocketMoney instanceof Withdraw){
						pstmt2.setString(1, pocketMoney.getUuid());									
						pstmt2.setString(2, studentUuid);
						pstmt2.setString(3, pocketMoney.getTerm());
						pstmt2.setString(4, pocketMoney.getYear());
						pstmt2.setDouble(5, balance);
						pstmt2.setTimestamp(6, new Timestamp(pocketMoney.getDate().getTime()));
						pstmt2.executeUpdate();
					}	
						
										
			} catch(SQLException e) {
				logger.error("SQLException while adding by creating the balance of student '" + studentUuid +
				"' of amount " + balance + "'.");
				logger.error(ExceptionUtils.getStackTrace(e));
				System.out.println(ExceptionUtils.getStackTrace(e));
				success = false;				
			} 
		}
						
		
		return success;
	}

	

}
