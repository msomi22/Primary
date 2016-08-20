/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.book;

import java.util.List;

import ke.co.fastech.primaryschool.bean.library.StudentBook;

/**
 * Persistence description for {@link StudentBook}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentBookDAO {
	
	/**
	 * 
	 * @param studentUuid
	 * @param bookUuid
	 * @return
	 */
	public StudentBook getStudentBook(String studentUuid,String bookUuid);
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<StudentBook> studentBoolList(String studentUuid,String borrowStatus);
	/**
	 * 
	 * @param studentbook
	 * @return
	 */
	public boolean borrowBook(StudentBook studentbook);
	/**
	 * 
	 * @param studentbook
	 * @return
	 */
	public boolean returnBook(StudentBook studentbook);
	
	/**
	 * 
	 * @param borrowStatus
	 * @return
	 */
	public List<StudentBook> getAllBorrowedBooks(String borrowStatus); 
	

}
