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

import ke.co.fastech.primaryschool.bean.student.Subject;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/** 
 * Persistence abstraction for {@link Subject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SubjectDAO extends GenericDAO implements SchoolSubjectDAO {

	private static SubjectDAO subjectDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * 
	 * @return
	 */
	public static SubjectDAO getInstance(){
		if(subjectDAO == null){
			subjectDAO = new SubjectDAO();
		}
		return subjectDAO;
	}
	/**
	 * 
	 */
	public SubjectDAO() {
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
	public SubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolSubjectDAO#getSubjectByUuid(java.lang.String)
	 */
	@Override
	public Subject getSubjectByUuid(String uuid) {
		Subject Subject = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE Uuid = ?;");       

				){

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				Subject  = beanProcessor.toBean(rset,Subject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting subject with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return Subject; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolSubjectDAO#getSubject(java.lang.String)
	 */
	@Override
	public Subject getSubject(String subjectCode) {
		Subject Subject = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Subject WHERE subjectCode = ?;");       

				){

			pstmt.setString(1, subjectCode);
			rset = pstmt.executeQuery();
			while(rset.next()){
				Subject  = beanProcessor.toBean(rset,Subject.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting subject with subjectCode " + subjectCode);
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return Subject; 
	}



	/**
	 * @see ke.co.fastech.primaryschool.persistence.student.subject.SchoolSubjectDAO#getSubjectList()
	 */
	@Override
	public List<Subject> getSubjectList() {
		List<Subject>  list = null;		
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Subject;");          		
				) {			     
			try( ResultSet rset = pstmt.executeQuery();){

				list = beanProcessor.toBeanList(rset, Subject.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Subject");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
