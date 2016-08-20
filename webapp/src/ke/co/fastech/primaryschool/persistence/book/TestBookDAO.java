/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.book;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.library.Book;

/**
 * @author peter
 *
 */
public class TestBookDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private BookDAO store;
	
	private String UUID = "C17DD410-386D-4C60-8AE3-C80E64D98597",UUID_NEW = "75400C79-BACA-43B2-B8F3-82CCA4CF0484";
	private String ISBN = "15526278810298900",ISBN_NEW = "CFE7152A69E5",ISBN_UPDATE = "43C6-48C0-8813";
	private String AUTHOR = "Kithaka Wa Nberia",AUTHOR_NEW = "aaaaaaaaaa",AUTHOR_UPDATE = "bbbbbbbbbbbbbb";
	private String PUBLISHER = "abc pub",PUBLISHER_NEW = "aaaaa",PUBLISHER_UPDATE = "bbbbb";
	private String TITLE = "Kifo Kisimani",TITLE_NEW = "new title",TITLE_UPDATE = "updated";
	private String BOOKSTATUS = "Reference",BOOKSTATUS_NEW = "Shortloan";
	private String BORROWSTATUS = "Available",BORROWSTATUS_NEW = "Borrowed";
	private int BOOKCOST = 1700,BOOKCOST_NEW = 2000,BOOKCOST_UPDATE = 4000;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#getBookByISBN(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBookByISBN() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book = store.getBookByISBN(ISBN);
		assertEquals(book.getUuid(),UUID);
		assertEquals(book.getISBN(),ISBN);
		assertEquals(book.getAuthor(),AUTHOR);
		assertEquals(book.getPublisher(),PUBLISHER);
		assertEquals(book.getTitle(),TITLE);
		assertEquals(book.getBookStatus(),BOOKSTATUS);
		assertEquals(book.getBorrowStatus(),BORROWSTATUS);
		assertEquals(book.getBookcost(),BOOKCOST); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#getBookByUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBookByUuid() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book = store.getBookByUuid(UUID);
		assertEquals(book.getUuid(),UUID);
		assertEquals(book.getISBN(),ISBN);
		assertEquals(book.getAuthor(),AUTHOR);
		assertEquals(book.getPublisher(),PUBLISHER);
		assertEquals(book.getTitle(),TITLE);
		assertEquals(book.getBookStatus(),BOOKSTATUS);
		assertEquals(book.getBorrowStatus(),BORROWSTATUS);
		assertEquals(book.getBookcost(),BOOKCOST); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#getBookByBookStatus(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBookByBookStatus() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book = store.getBookByBookStatus(ISBN, BOOKSTATUS);
		assertEquals(book.getUuid(),UUID);
		assertEquals(book.getISBN(),ISBN);
		assertEquals(book.getAuthor(),AUTHOR);
		assertEquals(book.getPublisher(),PUBLISHER);
		assertEquals(book.getTitle(),TITLE);
		assertEquals(book.getBookStatus(),BOOKSTATUS);
		assertEquals(book.getBorrowStatus(),BORROWSTATUS);
		assertEquals(book.getBookcost(),BOOKCOST); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#getBookByBorrowStatus(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBookByBorrowStatus() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book = store.getBookByBorrowStatus(ISBN, BORROWSTATUS);
		assertEquals(book.getUuid(),UUID);
		assertEquals(book.getISBN(),ISBN);
		assertEquals(book.getAuthor(),AUTHOR);
		assertEquals(book.getPublisher(),PUBLISHER);
		assertEquals(book.getTitle(),TITLE);
		assertEquals(book.getBookStatus(),BOOKSTATUS);
		assertEquals(book.getBorrowStatus(),BORROWSTATUS);
		assertEquals(book.getBookcost(),BOOKCOST); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#putBook(ke.co.fastech.primaryschool.bean.library.Book)}.
	 */
	@Ignore
	@Test
	public final void testPutBook() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book.setUuid(UUID_NEW);
		book.setISBN(ISBN_NEW);
		book.setAuthor(AUTHOR_NEW);
		book.setPublisher(PUBLISHER_NEW);
		book.setTitle(TITLE_NEW);
		book.setBookStatus(BOOKSTATUS_NEW);
		book.setBorrowStatus(BORROWSTATUS_NEW);
		book.setBookcost(BOOKCOST_NEW);
		assertTrue(store.putBook(book));
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#updateBook(ke.co.fastech.primaryschool.bean.library.Book)}.
	 */
	@Ignore
	@Test
	public final void testUpdateBook() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Book book  = new Book();
		book.setUuid(UUID_NEW);
		book.setISBN(ISBN_UPDATE);
		book.setAuthor(AUTHOR_UPDATE);
		book.setPublisher(PUBLISHER_UPDATE);
		book.setTitle(TITLE_UPDATE);
		book.setBookStatus(BOOKSTATUS);
		book.setBorrowStatus(BORROWSTATUS);
		book.setBookcost(BOOKCOST_UPDATE);
		assertTrue(store.updateBook(book)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#deleteBook(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testDeleteBook() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteBook(UUID_NEW));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.book.BookDAO#getboolList()}.
	 */
	@Ignore
	@Test
	public final void testGetboolList() {
		store = new BookDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Book> bookList = store.getboolList();
			for(Book bk : bookList){
				System.out.println(bk);
			}
		}
	

}
