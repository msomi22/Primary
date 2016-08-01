/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money.pocket;

import java.util.Date;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** used while withdrawing pocket money
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Withdraw extends StorableBean{
	
	private String studentUuid;
	private String term;
	private String year;
	private int amountWithdrawn;
	private Date dateWithdrawn;

	/**
	 * 
	 */
	public Withdraw() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		amountWithdrawn = 0;
		dateWithdrawn = new Date();
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
	 * @return the amountWithdrawn
	 */
	public int getAmountWithdrawn() {
		return amountWithdrawn;
	}

	/**
	 * @param amountWithdrawn the amountWithdrawn to set
	 */
	public void setAmountWithdrawn(int amountWithdrawn) {
		this.amountWithdrawn = amountWithdrawn;
	}

	/**
	 * @return the dateWithdrawn
	 */
	public Date getDateWithdrawn() {
		return dateWithdrawn;
	}

	/**
	 * @param dateWithdrawn the dateWithdrawn to set
	 */
	public void setDateWithdrawn(Date dateWithdrawn) {
		this.dateWithdrawn = dateWithdrawn;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Deposit");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", amountWithdrawn = ");
		builder.append(amountWithdrawn);
		builder.append(", dateWithdrawn = ");
		builder.append(dateWithdrawn);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3049160749554101808L;
}
