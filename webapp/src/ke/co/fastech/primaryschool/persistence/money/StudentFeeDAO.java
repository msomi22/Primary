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
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.money.StudentFee;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentFee}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentFeeDAO extends GenericDAO implements SchoolStudentFeeDAO {

	private static StudentFeeDAO studentFeeDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return examDAO
	 * 
	 */
	public static StudentFeeDAO getInstance() {
		if(studentFeeDAO == null){
			studentFeeDAO = new StudentFeeDAO();		
		}
		return studentFeeDAO;
	}

	public StudentFeeDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentFeeDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#getStudentFee(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentFee getStudentFee(String studentUuid, String term, String year) {
		StudentFee studentFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE studentUuid =? AND Term =? AND Year =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, term);
			pstmt.setString(3, year);
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentFee  = beanProcessor.toBean(rset,StudentFee.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting studentFee for student: " + studentUuid );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentFee; 
	}
	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#getStudentFee(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StudentFee getStudentFee(String studentUuid, String term, String year, String transactionID) {
		StudentFee studentFee = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentFee WHERE studentUuid =? AND Term =? AND Year =? AND transactionID =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, term);
			pstmt.setString(3, year);
			pstmt.setString(4, transactionID);
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentFee  = beanProcessor.toBean(rset,StudentFee.class);
			}
		}catch(SQLException e){
			logger.error("SQL Exception when getting studentFee for student: " + studentUuid );
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentFee; 
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#getStudentFeeList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentFee> getStudentFeeList(String studentUuid, String term, String year) {
		List<StudentFee> studentFeeList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentFee WHERE "
						+ "studentUuid = ? AND Term =? AND Year =?;");
				) {
			psmt.setString(1, studentUuid);
			psmt.setString(2, term);
			psmt.setString(3, year);
			try(ResultSet rset = psmt.executeQuery();){

				studentFeeList = beanProcessor.toBeanList(rset, StudentFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get StudentFee List for student " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return studentFeeList;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#putStudentFee(ke.co.fastech.primaryschool.bean.money.StudentFee, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean putStudentFee(StudentFee studentFee) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentFee" 
						+"(Uuid,studentUuid,term,year,studentType,amountPaid,transactionID,datePaid) VALUES (?,?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, studentFee.getUuid());
			pstmt.setString(2, studentFee.getStudentUuid());
			pstmt.setString(3, studentFee.getTerm());
			pstmt.setString(4, studentFee.getYear());
			pstmt.setString(5, studentFee.getStudentType()); 
			pstmt.setDouble(6, studentFee.getAmountPaid());
			pstmt.setString(7, studentFee.getTransactionID());
			pstmt.setTimestamp(8, new Timestamp(studentFee.getDatePaid().getTime()));
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put StudentFee "+studentFee);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#updateStudentFee(ke.co.fastech.primaryschool.bean.money.StudentFee)
	 */
	@Override
	public boolean updateStudentFee(StudentFee studentFee) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentFee SET Term = ?,Year = ?,StudentType = ?,"
						+ "AmountPaid =?,TransactionID =? WHERE Uuid = ? AND StudentUuid =?;");
				) {           			 	            

			pstmt.setString(1, studentFee.getTerm());
			pstmt.setString(2, studentFee.getYear());
			pstmt.setString(3, studentFee.getStudentType()); 
			pstmt.setDouble(4, studentFee.getAmountPaid());
			pstmt.setString(5, studentFee.getTransactionID());
			pstmt.setString(6, studentFee.getUuid());
			pstmt.setString(7, studentFee.getStudentUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating StudentFee " + studentFee);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentFeeDAO#getAllStudentFeeByTermYear(java.lang.String, java.lang.String)
	 */
	@Override
	public List<StudentFee> getAllStudentFeeByTermYear(String term, String year) {
		List<StudentFee> studentFeeList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentFee WHERE Term =? AND Year =?;");
				) {

			psmt.setString(1, term);
			psmt.setString(2, year);
			try(ResultSet rset = psmt.executeQuery();){

				studentFeeList = beanProcessor.toBeanList(rset, StudentFee.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get StudentFee List ");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return studentFeeList;
	}

	

}
