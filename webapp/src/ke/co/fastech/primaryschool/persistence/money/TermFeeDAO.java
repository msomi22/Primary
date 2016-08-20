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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.money.TermFee;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link TermFee}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TermFeeDAO extends GenericDAO implements SchoolTermFeeDAO {

	private static TermFeeDAO termFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return 
	 * 
	 */
	public static TermFeeDAO getInstance() {
		if(termFeeDAO == null){
			termFeeDAO = new TermFeeDAO();		
		}
		return termFeeDAO;
	}

	public TermFeeDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public TermFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolTermFeeDAO#putTermFee(ke.co.fastech.primaryschool.bean.money.TermFee, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putTermFee(TermFee termFee) {

		boolean success = true;
		try( Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TermFee" 
						+"(Uuid,term,year,studentLevel,amount) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, termFee.getUuid());
			pstmt.setString(2, termFee.getTerm());
			pstmt.setString(3, termFee.getYear());
			pstmt.setString(4, termFee.getStudentLevel());
			pstmt.setDouble(5, termFee.getAmount());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put TermFee " + termFee);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}


		return success;
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolTermFeeDAO#updateTermFee(ke.co.fastech.primaryschool.bean.money.TermFee)
	 */
	@Override
	public boolean updateTermFee(TermFee termFee) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE termFee SET Term =?,Year =?,StudentLevel =?, Amount =?"
						+ " WHERE Uuid = ?;");
				) {           			 	            

			pstmt.setString(1, termFee.getTerm());
			pstmt.setString(2, termFee.getYear());
			pstmt.setString(3, termFee.getStudentLevel());
			pstmt.setDouble(4, termFee.getAmount());
			pstmt.setString(5, termFee.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating TermFee " + termFee);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolTermFeeDAO#getTermFeeList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TermFee> getTermFeeList(String term, String year) {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee WHERE "
						+ "term = ? AND year = ?;");
				) {
			psmt.setString(1, term);
			psmt.setString(2, year);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolTermFeeDAO#getTermFeeList()
	 */
	@Override
	public List<TermFee> getTermFeeList() {
		List<TermFee> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM TermFee ORDER BY YEAR DESC;");
				) {
			try(ResultSet rset = psmt.executeQuery();){
				List = beanProcessor.toBeanList(rset, TermFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Fee List");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}


}
