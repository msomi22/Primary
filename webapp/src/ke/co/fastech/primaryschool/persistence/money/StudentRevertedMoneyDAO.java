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

import ke.co.fastech.primaryschool.bean.money.RevertedMoney;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link RevertedMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentRevertedMoneyDAO extends GenericDAO implements SchoolStudentRevertedMoneyDAO {

	private static StudentRevertedMoneyDAO studentRevertedMoneyDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * @return 
	 * 
	 */
	public static StudentRevertedMoneyDAO getInstance() {
		if(studentRevertedMoneyDAO == null){
			studentRevertedMoneyDAO = new StudentRevertedMoneyDAO();		
		}
		return studentRevertedMoneyDAO;
	}

	public StudentRevertedMoneyDAO() {
		super();

	}


	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public StudentRevertedMoneyDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort){
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);

	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentRevertedMoneyDAO#putRevertedMoney(ke.co.fastech.primaryschool.bean.money.RevertedMoney)
	 */
	@Override
	public boolean putRevertedMoney(RevertedMoney revertedMoney) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RevertedMoney" 
						+"(Uuid,studentUuid,otherMoneyUuid,term,year) VALUES (?,?,?,?,?);");
				){

			pstmt.setString(1, revertedMoney.getUuid());
			pstmt.setString(2, revertedMoney.getStudentUuid());
			pstmt.setString(3, revertedMoney.getOtherMoneyUuid());
			pstmt.setString(4, revertedMoney.getTerm());
			pstmt.setString(5, revertedMoney.getYear());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put RevertedMoney: "+revertedMoney);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}


		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.money.SchoolStudentRevertedMoneyDAO#getAllRevertedMoneyByTermYear(java.lang.String)
	 */
	@Override
	public List<RevertedMoney> getAllRevertedMoneyByTermYear(String studentUuid) {
		List<RevertedMoney> list = null;
		try (
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM RevertedMoney WHERE studentUuid = ?;");
				) {
			pstmt.setString(1, studentUuid);      
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, RevertedMoney.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when getting RevertedMoney  List"); 
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return list;
	}



}
