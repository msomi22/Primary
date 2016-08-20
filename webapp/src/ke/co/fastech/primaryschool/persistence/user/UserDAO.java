/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.staff.user.Users;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Users}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class UserDAO extends GenericDAO implements SchoolUserDAO {

	private static UserDAO userDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * 
	 * @return
	 */
	public static UserDAO getInstance(){
		if(userDAO == null){
			userDAO = new UserDAO();
		}
		return userDAO;
	}
	/**
	 * 
	 */
	public UserDAO() {
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
	public UserDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#getUser(java.lang.String)
	 */
	@Override
	public Users getUser(String staffUuid) {
		Users user = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE staffUuid = ?;");       

				){

			pstmt.setString(1, staffUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				user  = beanProcessor.toBean(rset,Users.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting User with staffUuid " + staffUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return user; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#getUserByUsername(java.lang.String)
	 */
	@Override
	public Users getUserByUsername(String username) {
		Users user = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ?;");       

				){

			pstmt.setString(1, username);
			rset = pstmt.executeQuery();
			while(rset.next()){
				user  = beanProcessor.toBean(rset,Users.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting User with username " + username);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return user; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#getUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Users getUserPassword(String username, String password) {
		Users user = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?;");       

				){

			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			while(rset.next()){
				user  = beanProcessor.toBean(rset,Users.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting User with username " + username);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return user; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#putUser(ke.co.fastech.primaryschool.bean.staff.user.Users)
	 */
	@Override
	public boolean putUser(Users users) {
		boolean success = true; 

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Users" 
						+"(Uuid,staffUuid,username,password) VALUES (?,?,?,?);");
				){
			pstmt.setString(1, users.getUuid());
			pstmt.setString(2, users.getStaffUuid());
			pstmt.setString(3, users.getUsername());
			pstmt.setString(4, users.getPassword());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put user " + users);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}	

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#updateUser(ke.co.fastech.primaryschool.bean.staff.user.Users)
	 */
	@Override
	public boolean updateUser(Users users) {
		boolean success = true; 
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Users SET Username = ?,Password = ?"
						+ "WHERE StaffUuid = ?;");
				){

			pstmt.setString(1, users.getUsername());
			pstmt.setString(2, users.getPassword());
			pstmt.setString(3, users.getStaffUuid());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to update User "+users);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.user.SchoolUserDAO#getUserList()
	 */
	@Override
	public List<Users> getUserList() {
		List<Users>  list = null;		
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Users;");          		
				) {			     
			try( ResultSet rset = pstmt.executeQuery();){ 
				list = beanProcessor.toBeanList(rset, Users.class);
			}

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Subject");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
