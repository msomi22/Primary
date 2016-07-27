/**
 * 
 */
package com.yahoo.petermwenda83.bean.book;

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class StudentBook extends StorableBean{
	
	
	String studentUuid;
	String bookUuid;
	String borrowStatus;
	Date borrowDate;
    String returnDate;

	/**
	 * 
	 */
	public StudentBook() {
		studentUuid = "";
		bookUuid = "";
		borrowStatus = "";
		borrowDate = new Date();
		returnDate = "";
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
	 * @return the returnDate
	 */
	public String getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentBook");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",studentUuid=");
		builder.append(studentUuid);
		builder.append(",bookUuid=");
		builder.append(bookUuid);
		builder.append(",borrowStatus=");
		builder.append(borrowStatus);
		builder.append(",borrowDate=");
		builder.append(borrowDate);
		builder.append(",returnDate=");
		builder.append(returnDate);
		builder.append("]");
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1660873636733409249L;

}
