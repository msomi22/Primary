/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.book;

import java.util.List;

import ke.co.fastech.primaryschool.bean.library.Book;

/**
 * Persistence description for {@link Book}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolBookDAO {
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	public Book getBookByISBN(String isbn);
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Book getBookByUuid(String uuid);
	/**
	 * 
	 * @param uuid
	 * @param bookStatus
	 * @return
	 */
	public Book getBookByBookStatus(String isbn,String bookStatus);
	/**
	 * 
	 * @param uuid
	 * @param borrowStatus
	 * @return
	 */
	public Book getBookByBorrowStatus(String isbn,String borrowStatus);
	/**
	 * 
	 * @param book
	 * @return
	 */
	public boolean putBook(Book book);
	/**
	 * 
	 * @param book
	 * @return
	 */
	public boolean updateBook(Book book);
	/**
	 * 
	 * @param book
	 * @return
	 */
	public boolean deleteBook(String uuid);
	/**
	 * 
	 * @return
	 */
	public List<Book> getboolList();

}
