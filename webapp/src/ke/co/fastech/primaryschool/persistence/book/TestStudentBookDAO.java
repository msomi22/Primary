/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.book;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.library.StudentBook;

/**
 * @author peter
 *
 */
public class TestStudentBookDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StudentBookDAO store;
	
	private String UUID = "20DBB269-DCE8-4068-AD42-619F60FEE929", UUID_NEW = "E0C13BE0-CD15-4FF5-9308-D125F5DB7ACE";
	private String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21", STUDENT_UUID_NEW = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1";
	private String BOOK_UUID = "C17DD410-386D-4C60-8AE3-C80E64D98597";//, BOOK_UUID_NEW = "";
	private String BORROWSTATUS = "Returned" , BORROWSTATUS_NEW = "Borrowed"; 
	private String TERM = "1";
	private String YEAR = "2016";
	private String RETURNDATE = "Wed, 03/2016";
	
	private Date BORROWDATE = new Date(new Long(123456));

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.StudentBookDAO#getStudentBook(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentBook() {
		store = new StudentBookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentBook studentBook = new StudentBook();
		studentBook = store.getStudentBook(STUDENT_UUID, BOOK_UUID);
		assertEquals(studentBook.getUuid(),UUID);
		assertEquals(studentBook.getStudentUuid(),STUDENT_UUID);
		assertEquals(studentBook.getBookUuid(),BOOK_UUID);
		assertEquals(studentBook.getBorrowStatus(),BORROWSTATUS);
		assertEquals(studentBook.getTerm(),TERM);
		assertEquals(studentBook.getYear(),YEAR);
		assertEquals(studentBook.getReturnDate(),RETURNDATE);
		//assertEquals(studentBook.getBorrowDate(),BORROWDATE);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.StudentBookDAO#studentBoolList(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testStudentBoolList() {
		store = new StudentBookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentBook> bookList = store.studentBoolList(STUDENT_UUID, BOOK_UUID);
		for(StudentBook sb : bookList){
			System.out.println(sb);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.StudentBookDAO#borrowBook(ke.co.fastech.primaryschool.bean.library.StudentBook)}.
	 */
	@Ignore
	@Test
	public final void testBorrowBook() {
		store = new StudentBookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentBook studentBook = new StudentBook();
		studentBook.setUuid(UUID_NEW);
		studentBook.setBookUuid(BOOK_UUID); 
		studentBook.setStudentUuid(STUDENT_UUID_NEW);
		studentBook.setBorrowStatus(BORROWSTATUS_NEW);
		studentBook.setTerm(TERM);
		studentBook.setYear(YEAR);
		studentBook.setBorrowDate(BORROWDATE);
		studentBook.setReturnDate(RETURNDATE); 
		assertTrue(store.borrowBook(studentBook)); 
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.StudentBookDAO#returnBook(ke.co.fastech.primaryschool.bean.library.StudentBook)}.
	 */
	@Ignore
	@Test
	public final void testReturnBook() {
		store = new StudentBookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentBook studentBook = new StudentBook();
		studentBook.setBookUuid(BOOK_UUID); 
		studentBook.setStudentUuid(STUDENT_UUID_NEW);
		studentBook.setBorrowStatus(BORROWSTATUS);
		studentBook.setBorrowDate(BORROWDATE);
		studentBook.setReturnDate(RETURNDATE); 
		assertTrue(store.returnBook(studentBook)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.StudentBookDAO#getAllBorrowedBooks(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetAllBorrowedBooks() {
		store = new StudentBookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentBook> studentBookList = store.getAllBorrowedBooks(BORROWSTATUS);
		for(StudentBook sb : studentBookList){
			System.out.println(sb);
		}
	}

}
