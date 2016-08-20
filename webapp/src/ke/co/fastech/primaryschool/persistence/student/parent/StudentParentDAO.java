/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.student.StudentParent;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentParent}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentParentDAO extends GenericDAO implements SchoolStudentParentDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static StudentParentDAO studentParentDAO;

	/**
	 * 
	 * @return
	 */
	public StudentParentDAO getInstance(){
		if(studentParentDAO == null){
			studentParentDAO = new StudentParentDAO();
		}
		return studentParentDAO;
	}
	/**
	 * 
	 */
	public StudentParentDAO() {
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
	public StudentParentDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#getStudentParent(java.lang.String)
	 */
	@Override
	public StudentParent getStudentParent(String studentUuid) {
		StudentParent studentParent = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM studentParent WHERE studentUuid =?;");
				){
			pstmt.setString(1, studentUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentParent  = beanProcessor.toBean(rset,StudentParent.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get studentParent for studentuuid " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentParent;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#getStudentParentByPhone(java.lang.String)
	 */
	@Override
	public StudentParent getStudentParentByPhone(String parentPhone) {
		StudentParent studentParent = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM studentParent"
						+ " WHERE parentPhone =?;");
				){
			pstmt.setString(1, parentPhone); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentParent  = beanProcessor.toBean(rset,StudentParent.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get studentParent with Phone " + parentPhone);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentParent;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#getStudentParentByEmail(java.lang.String)
	 */
	@Override
	public StudentParent getStudentParentByEmail(String parentEmail) {
		StudentParent studentParent = null;
		ResultSet rset = null;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM studentParent"
						+ " WHERE parentEmail =?;");
				){
			pstmt.setString(1, parentEmail); 
			rset = pstmt.executeQuery();
			while(rset.next()){
				studentParent  = beanProcessor.toBean(rset,StudentParent.class);
			}


		}catch(SQLException e){
			logger.error("SQL Exception trying to get Parent with Email " + parentEmail);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));

		}
		return studentParent;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#putStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)
	 */
	@Override
	public boolean putStudentParent(StudentParent studentParent) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentParent" 
						+"(Uuid,studentUuid,parentName,parentPhone,parentEmail) VALUES "
						+ "(?,?,?,?,?);");
				){

			pstmt.setString(1, studentParent.getUuid());
			pstmt.setString(2, studentParent.getStudentUuid());	            
			pstmt.setString(3, studentParent.getParentName());	       
			pstmt.setString(4, studentParent.getParentPhone());
			pstmt.setString(5, studentParent.getParentEmail());	       
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Parent " + studentParent);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#updateStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)
	 */
	@Override
	public boolean updateStudentParent(StudentParent studentParent) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentParent SET ParentName = ?,ParentPhone = ?,ParentEmail = ?"
						+ "WHERE StudentUuid = ?;");
				) {      			 	                           
			pstmt.setString(1, studentParent.getParentName());	       
			pstmt.setString(2, studentParent.getParentPhone());
			pstmt.setString(3, studentParent.getParentEmail());	 
			pstmt.setString(4, studentParent.getStudentUuid());	      
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating update Parent " + studentParent);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#deleteStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)
	 */
	@Override
	public boolean deleteStudentParent(String studentUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentParent WHERE StudentUuid =?;");       
				){

			pstmt.setString(1, studentUuid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.error("SQL Exception when deletting Parent fot studentUuid " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.parent.SchoolStudentParentDAO#getStudentParentList()
	 */
	@Override
	public List<StudentParent> getStudentParentList() {
		List<StudentParent> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM StudentParent;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, StudentParent.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting Parent List");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}

}
