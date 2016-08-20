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

import ke.co.fastech.primaryschool.bean.money.StudentOtherMoney;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentOtherMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentOtherMoneyDAO extends GenericDAO implements SchoolStudentOtherMoneyDAO {

	private static StudentOtherMoneyDAO studentOtherMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return 
	 * 
	 */
	public static StudentOtherMoneyDAO getInstance() {
		if(studentOtherMoneyDAO == null){
			studentOtherMoneyDAO = new StudentOtherMoneyDAO();		
		}
		return studentOtherMoneyDAO;
	}

	public StudentOtherMoneyDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentOtherMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentOtherMoneyDAO#getStudentOtherMoney(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentOtherMoney getStudentOtherMoney(String studentUuid, String otherMoneyUuid) {
		StudentOtherMoney studentOtherMoney = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentOtherMoney WHERE studentUuid =? AND otherMoneyUuid =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, otherMoneyUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				studentOtherMoney  = beanProcessor.toBean(rset,StudentOtherMoney.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting StudentOtherMoney " +otherMoneyUuid+ " for studentUuid: " + studentUuid );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentOtherMoney; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentOtherMoneyDAO#putStudentOtherMoney(ke.co.fastech.primaryschool.bean.money.StudentOtherMoney)
	 */
	@Override
	public boolean putStudentOtherMoney(StudentOtherMoney studentOtherMoney) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentOtherMoney" 
						+"(Uuid,studentUuid,otherMoneyUuid,term,year) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, studentOtherMoney.getUuid());
			pstmt.setString(2, studentOtherMoney.getStudentUuid());
			pstmt.setString(3, studentOtherMoney.getOtherMoneyUuid());
			pstmt.setString(4, studentOtherMoney.getTerm());
			pstmt.setString(5, studentOtherMoney.getYear());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentOtherMoney " + studentOtherMoney);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}



	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentOtherMoneyDAO#deleteStudentOtherMoney(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteStudentOtherMoney(String studentUuid, String otherMoneyUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentOtherMoney"
						+ " WHERE studentUuid =? AND otherMoneyUuid =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, otherMoneyUuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting StudentOtherMoney " +otherMoneyUuid+ " for " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentOtherMoneyDAO#getStudentOtherMoneyList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentOtherMoney> getStudentOtherMoneyList(String studentUuid, String term, String year) {
		List<StudentOtherMoney> List = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentOtherMoney WHERE "
						+ "studentUuid = ? AND term =? AND year =?;");
				) {
			psmt.setString(1, studentUuid);
			psmt.setString(2, term);
			psmt.setString(3, year);
			try(ResultSet rset = psmt.executeQuery();){

				List = beanProcessor.toBeanList(rset, StudentOtherMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get StudentOtherMoney List for studentUuid " +studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return List;
	}




}
