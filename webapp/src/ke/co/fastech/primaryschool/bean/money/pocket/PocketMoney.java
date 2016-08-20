/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money.pocket;

import java.util.Date;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 *Pocket money balance
 *
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class PocketMoney extends StorableBean{
	
	private String studentUuid;
	private String term;
	private String year;
	private int balance;
	private Date date;

	/**
	 * 
	 */
	public PocketMoney() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		balance = 0;
		date = new Date();
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
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}


	/**
	 * @param balance the balance to set
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}


	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PocketMoney");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", balance = ");
		builder.append(balance);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6822719543763494816L;
}
