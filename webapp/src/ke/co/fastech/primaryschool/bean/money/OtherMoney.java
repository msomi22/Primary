/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * Other kind of money payable by a student apart from school fee
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class OtherMoney extends StorableBean{
	
	private String description;
	private String term;
	private String year;
	private int amount;

	/**
	 * 
	 */
	public OtherMoney() {
		super();
		description = "";
		term = "";
		year = "";
		amount = 0;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
		builder.append("OtherMoney");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", description = ");
		builder.append(description);
		builder.append(", term = ");
		builder.append(term);
		builder.append(", year = ");
		builder.append(year);
		builder.append(", amount = ");
		builder.append(amount);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2553588021985010341L;
}
