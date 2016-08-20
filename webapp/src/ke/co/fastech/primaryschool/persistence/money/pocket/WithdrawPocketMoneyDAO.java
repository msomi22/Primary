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
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.money.pocket.Withdraw;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Withdraw}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class WithdrawPocketMoneyDAO extends GenericDAO  implements SchoolWithdrawPocketMoneyDAO {

	private static WithdrawPocketMoneyDAO withdrawPocketMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static WithdrawPocketMoneyDAO getInstance() {
		if(withdrawPocketMoneyDAO == null){
			withdrawPocketMoneyDAO = new WithdrawPocketMoneyDAO();		
			}
		return withdrawPocketMoneyDAO;
	}
	
	public WithdrawPocketMoneyDAO() {
		super();
		
	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public WithdrawPocketMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolWithdrawPocketMoneyDAO#getDeposit(java.lang.String)
	 */
	@Override
	public List<Withdraw> getWithdraw(String studentUuid) {
		List<Withdraw> withdrawList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Withdraw WHERE "
						+ "studentUuid = ?;");
				) {
			psmt.setString(1, studentUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				withdrawList = beanProcessor.toBeanList(rset, Withdraw.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Withdraw List for student " + studentUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return withdrawList;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.pocket.SchoolWithdrawPocketMoneyDAO#getDeposit(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Withdraw> getWithdraw(String studentUuid, String term, String year) {
		List<Withdraw> withdrawList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Withdraw WHERE "
						+ "studentUuid = ? AND term = ? AND year =?;");
				) {
			psmt.setString(1, studentUuid);
			psmt.setString(2, term);
			psmt.setString(3, year);
			try(ResultSet rset = psmt.executeQuery();){
			
				withdrawList = beanProcessor.toBeanList(rset, Withdraw.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Withdraw List for student " + studentUuid + " and term " + term + " and year" + year);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return withdrawList;
	}

	

}
