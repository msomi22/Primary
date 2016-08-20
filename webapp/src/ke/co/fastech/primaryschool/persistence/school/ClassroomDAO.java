/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.school.Classroom;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Classroom}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ClassroomDAO extends GenericDAO implements SchoolClassroomDAO {

	private static ClassroomDAO classroomDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static ClassroomDAO getInstance(){

		if(classroomDAO == null){
			classroomDAO = new ClassroomDAO();		
		}
		return classroomDAO;
	}

	/**
	 * 
	 */
	public ClassroomDAO() { 
		super();
	}

	/**
	 * 
	 */
	public ClassroomDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolClassroomDAO#getClassroomByUuid(java.lang.String)
	 */
	@Override
	public Classroom getClassroomByUuid(String uuid) {
		Classroom classroom = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Classroom WHERE Uuid = ?;");       

				){

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classroom  = beanProcessor.toBean(rset,Classroom.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Classroom with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return classroom; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolClassroomDAO#getClassroom(java.lang.String)
	 */
	@Override
	public Classroom getClassroom(String className) {
		Classroom classroom = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Classroom WHERE className = ?;");       

				){

			pstmt.setString(1, className);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classroom  = beanProcessor.toBean(rset,Classroom.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Classroom with className " + className);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return classroom; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolClassroomDAO#putClassroom(ke.co.fastech.primaryschool.bean.school.Classroom)
	 */
	@Override
	public boolean putClassroom(Classroom classroom) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Classroom" 
						+"(Uuid, className) VALUES (?,?);");
				){

			pstmt.setString(1, classroom.getUuid());
			pstmt.setString(2, classroom.getClassName());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put classroom "+ classroom);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolClassroomDAO#updateClassroom(ke.co.fastech.primaryschool.bean.school.Classroom)
	 */
	@Override
	public boolean updateClassroom(Classroom classroom) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Classes SET ClassName = ?"
						+ "WHERE Uuid = ?;");
				) {           			 	            
			pstmt.setString(1, classroom.getClassName());
			pstmt.setString(2, classroom.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating Classroom " + classroom);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolClassroomDAO#getClassroomList()
	 */
	@Override
	public List<Classroom> getClassroomList() {
		List<Classroom>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Classroom ;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, Classroom.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Classroom");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
