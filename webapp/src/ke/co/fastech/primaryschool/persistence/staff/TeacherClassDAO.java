/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.staff.ClassTeacher;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/** 
 * Persistence abstraction for {@link ClassTeacher}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherClassDAO extends GenericDAO  implements SchoolTeacherClassDAO {

	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static TeacherClassDAO teacherClassDAO;

	/**
	 * 
	 * @return
	 */
	public static TeacherClassDAO getInstance(){
		if(teacherClassDAO == null){
			teacherClassDAO = new TeacherClassDAO();
		}
		return teacherClassDAO;
	}
	/**
	 * 
	 */
	public TeacherClassDAO() {
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
	public TeacherClassDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#getTeacherClass(java.lang.String)
	 */
	@Override
	public ClassTeacher getTeacherClass(String teacherUuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE teacherUuid = ?;");       

				){
			pstmt.setString(1, teacherUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with teacherUuid " + teacherUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}
	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#getTeacherClassByClassid(java.lang.String)
	 */
	@Override
	public ClassTeacher getTeacherClassByClassid(String streamUuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE streamUuid = ?;");       

				){
			pstmt.setString(1, streamUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with streamUuid " + streamUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}
	


	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#getTeacherClass(java.lang.String, java.lang.String)
	 */
	@Override
	public ClassTeacher getTeacherClass(String teacherUuid, String streamUuid) {
		ClassTeacher classTeacher =null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ClassTeacher WHERE teacherUuid = ? AND streamUuid = ?;");       

				){
			pstmt.setString(1, teacherUuid);
			pstmt.setString(2, streamUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				classTeacher  = beanProcessor.toBean(rset,ClassTeacher.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting classTeacher with teacherUuid " + teacherUuid + " and streamUuid " + streamUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

		return classTeacher; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#putClassTeacher(ke.co.fastech.primaryschool.bean.staff.ClassTeacher)
	 */
	@Override
	public boolean putClassTeacher(ClassTeacher classTeacher) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ClassTeacher" 
						+"(Uuid,accountUuid,TeacherUuid,StreamUuid) VALUES (?,?,?,?);");
				){
			pstmt.setString(1, classTeacher.getUuid());
			pstmt.setString(2, classTeacher.getAccountUuid());
			pstmt.setString(3, classTeacher.getTeacherUuid());
			pstmt.setString(4, classTeacher.getStreamUuid());	                      
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put ClassTeacher " + classTeacher);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}

	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#deleteClassTeacher(ke.co.fastech.primaryschool.bean.staff.ClassTeacher)
	 */
	@Override
	public boolean deleteClassTeacher(String teacherUuid) {
		boolean success = true; 
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ClassTeacher"
						+ " WHERE teacherUuid =?;");       

				){

			pstmt.setString(1, teacherUuid);
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception when deletting Teacher with teacherUuid " + teacherUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;

		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherClassDAO#getClassTeacherList()
	 */
	@Override
	public List<ClassTeacher> getClassTeacherList(String accountUuid) {

	     List<ClassTeacher> list = new ArrayList<>();
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM ClassTeacher WHERE accountUuid =?;");
					) {
				   psmt.setString(1,accountUuid);
				  try(ResultSet rset = psmt.executeQuery();){
						
					  list = beanProcessor.toBeanList(rset, ClassTeacher.class);
					}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get ClassTeacher List");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			
		return list;
	}
	

}
