/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * school configuration properties 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SystemConfig extends StorableBean{
	
	private String accountUuid;
	private String term;
	private String year;
	private String smsSend;
	private String examcode;
	private String openningDate;
	private String closingDate;

	/**
	 * 
	 */
	public SystemConfig() {
		super();
		accountUuid = "";
		term = "";
		year = "";
		smsSend = "";
		examcode = "";
		openningDate = "";
		closingDate = "";
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
	 * @return the smsSend
	 */
	public String getSmsSend() {
		return smsSend;
	}



	/**
	 * @param smsSend the smsSend to set
	 */
	public void setSmsSend(String smsSend) {
		this.smsSend = smsSend;
	}

	/**
	 * @return the examcode
	 */
	public String getExamcode() {
		return examcode;
	}



	/**
	 * @param examcode the examcode to set
	 */
	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}



	/**
	 * @return the openningDate
	 */
	public String getOpenningDate() {
		return openningDate;
	}



	/**
	 * @param openningDate the openningDate to set
	 */
	public void setOpenningDate(String openningDate) {
		this.openningDate = openningDate;
	}



	/**
	 * @return the closingDate
	 */
	public String getClosingDate() {
		return closingDate;
	}



	/**
	 * @param closingDate the closingDate to set
	 */
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SystemConfig");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", smsSend = ");
		builder.append(smsSend);
		builder.append(", examcode = ");
		builder.append(examcode);
		builder.append(", openningDate = ");
		builder.append(openningDate);
		builder.append(", closingDate = ");
		builder.append(closingDate);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6415846862169021303L;
}
