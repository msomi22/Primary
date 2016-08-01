/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * Other money assigned to a student 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentOtherMoney extends StorableBean{
	
	private String studentUuid;
	private String otherMoneyUuid;
	private String term;
	private String year;

	/**
	 * 
	 */
	public StudentOtherMoney() {
		super();
		studentUuid = "";
		otherMoneyUuid = "";
		term = "";
		year = "";
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
	 * @return the otherMoneyUuid
	 */
	public String getOtherMoneyUuid() {
		return otherMoneyUuid;
	}

	/**
	 * @param otherMoneyUuid the otherMoneyUuid to set
	 */
	public void setOtherMoneyUuid(String otherMoneyUuid) {
		this.otherMoneyUuid = otherMoneyUuid;
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

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentFee");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", otherMoneyUuid = ");
		builder.append(otherMoneyUuid);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9056322765647726673L;
}
