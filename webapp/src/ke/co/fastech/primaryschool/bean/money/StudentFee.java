/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money;

import java.util.Date;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * Fee paid by a student
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentFee extends StorableBean{
	
	private String studentUuid;
	private String term;
	private String year;
	private String studentType;
	private int amountPaid;
	private String transactionID;
	private Date datePaid;
	

	/**
	 * 
	 */
	public StudentFee() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		studentType = "";
		amountPaid = 0;
		transactionID = "";
		datePaid = new Date();
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
	 * @return the studentType
	 */
	public String getStudentType() {
		return studentType;
	}

	/**
	 * @param studentType the studentType to set
	 */
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	/**
	 * @return the amountPaid
	 */
	public int getAmountPaid() {
		return amountPaid;
	}

	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return the datePaid
	 */
	public Date getDatePaid() {
		return datePaid;
	}

	/**
	 * @param datePaid the datePaid to set
	 */
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentFee");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", studentType = ");
		builder.append(studentType);
		builder.append(", amountPaid = ");
		builder.append(amountPaid);
		builder.append(", transactionID = ");
		builder.append(transactionID);
		builder.append(", datePaid = ");
		builder.append(datePaid);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3991923520380021946L;
}
