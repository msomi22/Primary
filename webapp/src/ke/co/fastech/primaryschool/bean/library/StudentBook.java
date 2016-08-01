/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.library;

import java.util.Date;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * keep the record of the book borrowed and by who
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentBook extends StorableBean{
	
	private String studentUuid;
	private String bookUuid;
	private String borrowStatus;
	private Date borrowDate;
	private String term;
	private String year;
	private Date returnDate;

	/**
	 * 
	 */
	public StudentBook() {
		studentUuid = "";
		bookUuid = "";
		borrowStatus = "";
		borrowDate = new Date();
		term = "";
		year = "";
		returnDate = new Date();
	}
	
	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}

	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}

	/**
	 * @return the bookUuid
	 */
	public String getBookUuid() {
		return bookUuid;
	}

	/**
	 * @param bookUuid the bookUuid to set
	 */
	public void setBookUuid(String bookUuid) {
		this.bookUuid = bookUuid;
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
	 * @return the borrowDate
	 */
	public Date getBorrowDate() {
		return borrowDate;
	}

	/**
	 * @param borrowDate the borrowDate to set
	 */
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the returnDate
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentBook");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", bookUuid = ");
		builder.append(bookUuid);
		builder.append(", borrowStatus = ");
		builder.append(borrowStatus);
		builder.append(", borrowDate = ");
		builder.append(borrowDate);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", returnDate = ");
		builder.append(returnDate);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6651069613706580249L;
}
