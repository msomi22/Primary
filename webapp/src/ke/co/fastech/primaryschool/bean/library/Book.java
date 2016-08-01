/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.library;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * A book in a school that a student can borrow
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Book extends StorableBean{
	
	private String ISBN;
	private String author;
	private String publisher;
	private String title;
	private String bookStatus;
	private String borrowStatus;
	private int bookcost;

	/**
	 * 
	 */
	public Book() {
		ISBN = "";
		author = "";
		publisher = "";
		title = "";
		bookStatus = "";
		borrowStatus = "";
		bookcost = 0;
	}
	
	/**
	 * @return the iSBN
	 */
	public String getISBN() {
		return ISBN;
	}

	/**
	 * @param iSBN the iSBN to set
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the bookStatus
	 */
	public String getBookStatus() {
		return bookStatus;
	}

	/**
	 * @param bookStatus the bookStatus to set
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	/**
	 * @return the borrowStatus
	 */
	public String getBorrowStatus() {
		return borrowStatus;
	}

	/**
	 * @param borrowStatus the borrowStatus to set
	 */
	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	/**
	 * @return the bookcost
	 */
	public int getBookcost() {
		return bookcost;
	}

	/**
	 * @param bookcost the bookcost to set
	 */
	public void setBookcost(int bookcost) {
		this.bookcost = bookcost;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Book");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", ISBN = ");
		builder.append(ISBN);
		builder.append(", author = ");
		builder.append(author);
		builder.append(", publisher = ");
		builder.append(publisher);
		builder.append(", title = ");
		builder.append(title);
		builder.append(", bookStatus = ");
		builder.append(bookStatus);
		builder.append(", borrowStatus = ");
		builder.append(borrowStatus);
		builder.append(", bookcost = ");
		builder.append(bookcost);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2663074959829291886L;
}
