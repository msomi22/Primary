/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.house;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.student.StudentHouse;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentHouse}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentHouseDAO extends GenericDAO  implements SchoolStudentHouseDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static StudentHouseDAO studentHouseDAO;

	/**
	 * 
	 * @return
	 */
	public StudentHouseDAO getInstance(){
		if(studentHouseDAO == null){
			studentHouseDAO = new StudentHouseDAO();
		}
		return studentHouseDAO;
	}
	/**
	 * 
	 */
	public StudentHouseDAO() {
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
	public StudentHouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#getStudentHouse(java.lang.String)
	 */
	@Override
	public StudentHouse getStudentHouse(String studentuuid) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse WHERE StudentUuid =?;");
				){
			pstmt.setString(1, studentuuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentHouse  = beanProcessor.toBean(rset,StudentHouse.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get house for student " + studentuuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentHouse;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#getStudentHouse(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentHouse getStudentHouse(String studentUuid, String houseUuid) {
		StudentHouse studentHouse = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentHouse"
						+ " WHERE studentUuid =? AND houseUuid =?;");
				){
			pstmt.setString(1, studentUuid); 
			pstmt.setString(2, houseUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentHouse  = beanProcessor.toBean(rset,StudentHouse.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get house " +houseUuid +  " for studentUuid "+studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentHouse;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#putStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)
	 */
	@Override
	public boolean putStudentHouse(StudentHouse studentHouse) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentHouse" 
						+"(Uuid,StudentUuid,HouseUuid) VALUES (?,?,?);");
				){

			pstmt.setString(1, studentHouse.getUuid());
			pstmt.setString(2, studentHouse.getStudentUuid());
			pstmt.setString(3, studentHouse.getHouseUuid());	       
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put house " + studentHouse);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#updateStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)
	 */
	@Override
	public boolean updateStudentHouse(String studentUuid,String houseUuid) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentHouse SET HouseUuid = ? "
						+ "WHERE StudentUuid = ?;");
				) {           			 	            
			pstmt.setString(1, houseUuid);
			pstmt.setString(2, studentUuid);	           
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating upate house " + houseUuid + " for studentUuid " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#deleteStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)
	 */
	@Override
	public boolean deleteStudentHouse(String studentUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentHouse"
						+ " WHERE StudentUuid =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting house for studentUuid " +studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.house.SchoolStudentHouseDAO#getStudentHouseList()
	 */
	@Override
	public List<StudentHouse> getStudentHouseList() {
		List<StudentHouse> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentHouse;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, StudentHouse.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Student in all domitories/House");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}

}
