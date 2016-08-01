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
 * used while deposit pocket money
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Deposit extends StorableBean{
	
	private String studentUuid;
	private String term;
	private String year;
	private int amountDeposited;
	private Date dateDeposited;

	/**
	 * 
	 */
	public Deposit() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		amountDeposited = 0;
		dateDeposited = new Date();
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
	 * @return the amountDeposited
	 */
	public int getAmountDeposited() {
		return amountDeposited;
	}

	/**
	 * @param amountDeposited the amountDeposited to set
	 */
	public void setAmountDeposited(int amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	/**
	 * @return the dateDeposited
	 */
	public Date getDateDeposited() {
		return dateDeposited;
	}

	/**
	 * @param dateDeposited the dateDeposited to set
	 */
	public void setDateDeposited(Date dateDeposited) {
		this.dateDeposited = dateDeposited;
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
		builder.append(", amountDeposited = ");
		builder.append(amountDeposited);
		builder.append(", dateDeposited = ");
		builder.append(dateDeposited);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554853914339672815L;
}
