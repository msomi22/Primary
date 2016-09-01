/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.money.OtherMoney;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link OtherMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class OtherMoneyDAO extends GenericDAO implements SchoolOtherMoneyDAO {

	private static OtherMoneyDAO otherMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static OtherMoneyDAO getInstance() {
		if(otherMoneyDAO == null){
			otherMoneyDAO = new OtherMoneyDAO();		
		}
		return otherMoneyDAO;
	}

	public OtherMoneyDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public OtherMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#getOtherMoney(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public OtherMoney getOtherMoney(String description, String term, String year,String accountUuid) {
		OtherMoney otherMoney = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherMoney WHERE description = ? AND term =? AND year =? AND accountUuid =?;");       

				){

			pstmt.setString(1, description);
			pstmt.setString(2, term);
			pstmt.setString(3, year);
			pstmt.setString(4, accountUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				otherMoney  = beanProcessor.toBean(rset,OtherMoney.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting Other Money with description " + description + " for term " + term + " year " + year);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return otherMoney; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#putOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)
	 */
	@Override
	public boolean putOtherMoney(OtherMoney otherMoney) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO OtherMoney" 
						+"(Uuid,accountUuid,description,term,year,amount) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, otherMoney.getUuid());
			pstmt.setString(2, otherMoney.getAccountUuid());
			pstmt.setString(3, otherMoney.getDescription());
			pstmt.setString(4, otherMoney.getTerm());
			pstmt.setString(5, otherMoney.getYear());
			pstmt.setInt(6, otherMoney.getAmount());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put other Money " + otherMoney);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#updateOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)
	 */
	@Override
	public boolean updateOtherMoney(OtherMoney otherMoney) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE OtherMoney SET Amount = ?,Description =?"
						+ "WHERE Term = ? AND Year = ? AND Uuid = ?;");
				) {           			 	            

			pstmt.setInt(1, otherMoney.getAmount());
			pstmt.setString(2, otherMoney.getDescription());
			pstmt.setString(3, otherMoney.getTerm());
			pstmt.setString(4, otherMoney.getYear());
			pstmt.setString(5, otherMoney.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating OtherMoney " + otherMoney);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#deleteOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)
	 */
	@Override
	public boolean deleteOtherMoney(OtherMoney otherMoney) {
		return false;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#getOtherMoneyList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OtherMoney> getOtherMoneyList(String term, String year,String accountUuid) {
		List<OtherMoney> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherMoney WHERE Term = ? AND Year = ? AND accountUuid =?;");
				) {      
			pstmt.setString(1, term); 
			pstmt.setString(2, year); 
			pstmt.setString(3, accountUuid); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, OtherMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting Other Money  List"); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolOtherMoneyDAO#getOtherMoneyList()
	 */
	@Override
	public List<OtherMoney> getOtherMoneyList(String accountUuid) {
		List<OtherMoney> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OtherMoney WHERE accountUuid =?;");
				) {      
			pstmt.setString(1, accountUuid); 
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, OtherMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting Other Money  List"); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}

}
