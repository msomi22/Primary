/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.library.StudentBook;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link StudentBook}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentBookDAO extends GenericDAO implements SchoolStudentBookDAO {

	private static StudentBookDAO studentBookDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static StudentBookDAO getInstance(){

		if(studentBookDAO == null){
			studentBookDAO = new StudentBookDAO();		
		}
		return studentBookDAO;
	}

	/**
	 * 
	 */
	public StudentBookDAO() { 
		super();
	}

	/**
	 * 
	 */
	public StudentBookDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolStudentBookDAO#getStudentBook(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentBook getStudentBook(String studentUuid, String bookUuid) {
		StudentBook studentBook = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM StudentBook WHERE studentUuid =? AND bookUuid =?;");       

				){
			pstmt.setString(1, studentUuid);
			pstmt.setString(2, bookUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				studentBook  = beanProcessor.toBean(rset,StudentBook.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting studentBook");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return studentBook; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolStudentBookDAO#studentBoolList(java.lang.String)
	 */
	@Override
	public List<StudentBook> studentBoolList(String studentUuid,String borrowStatus) {
		List<StudentBook> bookList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentBook WHERE studentUuid = ?;");
				) {
			psmt.setString(1, studentUuid);
			try(ResultSet rset = psmt.executeQuery();){

				bookList = beanProcessor.toBeanList(rset, StudentBook.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a StudentBook List for"+studentUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return bookList;

	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolStudentBookDAO#borrowBook(ke.co.fastech.primaryschool.bean.library.StudentBook)
	 */
	@Override
	public boolean borrowBook(StudentBook studentbook) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO StudentBook" 
						+"(Uuid,StudentUuid,BookUuid,BorrowStatus,BorrowDate,ReturnDate) VALUES (?,?,?,?,?,?);");
				){

			pstmt.setString(1, studentbook.getUuid());
			pstmt.setString(2, studentbook.getStudentUuid());
			pstmt.setString(3, studentbook.getBookUuid());
			pstmt.setString(4, studentbook.getBorrowStatus());
			pstmt.setTimestamp(5, new Timestamp(studentbook.getBorrowDate().getTime()));
			pstmt.setString(6, studentbook.getReturnDate());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put studentBook "+studentbook);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolStudentBookDAO#returnBook(ke.co.fastech.primaryschool.bean.library.StudentBook)
	 */
	@Override
	public boolean returnBook(StudentBook studentbook) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE StudentBook SET BorrowStatus =?,"
						+ "ReturnDate =?  WHERE BookUuid = ? AND StudentUuid =? ;");
				) {           			 	            


			pstmt.setString(1, studentbook.getBorrowStatus());
			pstmt.setString(2, studentbook.getReturnDate());
			pstmt.setString(3, studentbook.getBookUuid());
			pstmt.setString(4, studentbook.getStudentUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating studentBook " + studentbook);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolStudentBookDAO#getAllBorrowedBooks(java.lang.String)
	 */
	@Override
	public List<StudentBook> getAllBorrowedBooks(String borrowStatus) {
		List<StudentBook> studentBookList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM StudentBook WHERE "
						+ "borrowStatus = ?;");
				) {
			psmt.setString(1, borrowStatus);
			try(ResultSet rset = psmt.executeQuery();){

				studentBookList = beanProcessor.toBeanList(rset, StudentBook.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student Book List with borrowStatus"+borrowStatus);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return studentBookList;

	}

}
