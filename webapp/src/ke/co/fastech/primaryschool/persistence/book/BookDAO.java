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
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.library.Book;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Book}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class BookDAO extends GenericDAO implements SchoolBookDAO {

	private static BookDAO bookDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static BookDAO getInstance(){

		if(bookDAO == null){
			bookDAO = new BookDAO();		
		}
		return bookDAO;
	}

	/**
	 * 
	 */
	public BookDAO() { 
		super();
	}

	/**
	 * 
	 */
	public BookDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#getBookByISBN(java.lang.String)
	 */
	@Override
	public Book getBookByISBN(String isbn) {
		Book book = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE ISBN = ?;");       
        		
        		){
 
        	 pstmt.setString(1, isbn);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 book  = beanProcessor.toBean(rset,Book.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Book with ISBN " + isbn);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return book; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#getBookByUuid(java.lang.String)
	 */
	@Override
	public Book getBookByUuid(String uuid) {
		Book book = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 book  = beanProcessor.toBean(rset,Book.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Book with Uuid " + uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return book; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#getBookByBookStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public Book getBookByBookStatus(String isbn, String bookStatus) {
		Book book = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE ISBN = ? AND BookStatus = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, isbn);
        	 pstmt.setString(2, bookStatus);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 book  = beanProcessor.toBean(rset,Book.class);
	   }
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Book " + isbn + "  with status " + bookStatus);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return book; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#getBookByBorrowStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public Book getBookByBorrowStatus(String isbn, String borrowStatus) {
		Book book = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Book WHERE ISBN = ? AND BorrowStatus = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, isbn);
        	 pstmt.setString(2, borrowStatus);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 book  = beanProcessor.toBean(rset,Book.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Book " + isbn + " with BorrowStatus " + borrowStatus);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return book; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#putBook(ke.co.fastech.primaryschool.bean.library.Book)
	 */
	@Override
	public boolean putBook(Book book) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Book" 
			        		+"(Uuid,ISBN,Author,Publisher,Title,BookStatus,BorrowStatus) VALUES (?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, book.getUuid());
	            pstmt.setString(2, book.getISBN());
	            pstmt.setString(3, book.getAuthor());
	            pstmt.setString(4, book.getPublisher());
	            pstmt.setString(5, book.getTitle());
	            pstmt.setString(6, book.getBookStatus());
	            pstmt.setString(7, book.getBorrowStatus());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put book " + book);
       logger.error(ExceptionUtils.getStackTrace(e)); 
       System.out.println(ExceptionUtils.getStackTrace(e));
       success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#updateBook(ke.co.fastech.primaryschool.bean.library.Book)
	 */
	@Override
	public boolean updateBook(Book book) {
		boolean success = true;
		
		  try (  Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("UPDATE Book SET ISBN =?, Author = ? ,Publisher =?,"
			        + "Title =? ,BookStatus =? ,BorrowStatus =? WHERE Uuid = ?;");
	               ) {           			 	            
			 
			    pstmt.setString(1, book.getISBN());
	            pstmt.setString(2, book.getAuthor());
	            pstmt.setString(3, book.getPublisher());
	            pstmt.setString(4, book.getTitle());
	            pstmt.setString(5, book.getBookStatus());
	            pstmt.setString(6, book.getBorrowStatus());
	            pstmt.setString(7, book.getUuid());
	            pstmt.executeUpdate();

		  } catch (SQLException e) {
		    logger.error("SQL Exception when updating book " + book);
		    logger.error(ExceptionUtils.getStackTrace(e));
		    System.out.println(ExceptionUtils.getStackTrace(e));
		    success = false;
		 } 
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#deleteBook(java.lang.String)
	 */
	@Override
	public boolean deleteBook(String uuid) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Book"
	         	      		+ " WHERE uuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1,uuid);
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting book with uuid " + uuid);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.book.SchoolBookDAO#getboolList()
	 */
	@Override
	public List<Book> getboolList() {
		List<Book> bookList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Book;");
				) {
			try(ResultSet rset = psmt.executeQuery();){
				bookList = beanProcessor.toBeanList(rset, Book.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a book List");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return bookList;
	}

}
