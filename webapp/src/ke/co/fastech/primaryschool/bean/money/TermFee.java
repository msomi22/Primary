/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * Term fee (amount) per year
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TermFee extends StorableBean{
	
	private String accountUuid;
	private String term;
	private String year;
	private String studentLevel;
	private int amount;
	

	/**
	 * 
	 */
	public TermFee() {
		super();
		accountUuid = "";
		term = "";
		year = "";
		studentLevel ="";
		amount = 0;
	}
	

	/**
	 * @return the accountUuid
	 */
	public String getAccountUuid() {
		return accountUuid;
	}


	/**
	 * @param accountUuid the accountUuid to set
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
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
	 * @return the studentLevel
	 */
	public String getStudentLevel() {
		return studentLevel;
	}


	/**
	 * @param studentLevel the studentLevel to set
	 */
	public void setStudentLevel(String studentLevel) {
		this.studentLevel = studentLevel;
	}


	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TermFee");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", studentLevel = ");
		builder.append(studentLevel);
		builder.append(", amount = ");
		builder.append(amount);
		builder.append("]");
		return builder.toString(); 
		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3170653580557474285L;
}
