/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.student.StudentSubject;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentSubject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentSubjectDAO extends GenericDAO implements SchoolStudentSubjectDAO {

	private static StudentSubjectDAO studentSubjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * 
	 * @return
	 */
	public static StudentSubjectDAO getInstance(){
		if(studentSubjectDAO == null){
			studentSubjectDAO = new StudentSubjectDAO();
		}
		return studentSubjectDAO;
	}
	/**
	 * 
	 */
	public StudentSubjectDAO() {
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
	public StudentSubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolStudentSubjectDAO#getStudentSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentSubject getStudentSubject(String studentUuid, String subjectUuid) {
		StudentSubject studentsub = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentSubject WHERE StudentUuid = ? AND SubjectUuid = ?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, subjectUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				studentsub  = beanProcessor.toBean(rset,StudentSubject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Subject " + subjectUuid + " for student " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return studentsub; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolStudentSubjectDAO#getStudentSubjectList(java.lang.String)
	 */
	@Override
	public List<StudentSubject> getStudentSubjectList(String studentUuid) {
		List<StudentSubject>  subjectlist = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentSubject WHERE studentuuid = ?;");
				) {
			psmt.setString(1, studentUuid);
			try(ResultSet rset = psmt.executeQuery();){

				subjectlist = beanProcessor.toBeanList(rset, StudentSubject.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get student subjects List for  " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return subjectlist;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolStudentSubjectDAO#putStudentSubject(ke.co.fastech.primaryschool.bean.student.StudentSubject)
	 */
	@Override
	public boolean putStudentSubject(StudentSubject studentSubject) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentSubject" 
						+"(Uuid,StudentUuid,SubjectUuid) VALUES (?,?,?);");
				){

			pstmt.setString(1, studentSubject.getUuid());
			pstmt.setString(2, studentSubject.getStudentUuid());
			pstmt.setString(3, studentSubject.getSubjectUuid());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put studentSubject " + studentSubject);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolStudentSubjectDAO#deleteStudentSubject(ke.co.fastech.primaryschool.bean.student.StudentSubject)
	 */
	@Override
	public boolean deleteStudentSubject(String studentUuid,String subjectUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM StudentSubject"
						+ " WHERE studentUuid = ? AND subjectUuid =?;");       

				){

			pstmt.setString(1, studentUuid);
			pstmt.setString(2, subjectUuid); 
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Subject " + subjectUuid + " for studentUuid " + studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}
	

}
