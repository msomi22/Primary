/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money.pocket;

import java.util.Date;

/** used while withdrawing pocket money
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Withdraw extends PocketMoney{
	
	/**
	 * 
	 */
	public Withdraw() {
		super();
	}
	
	/**
	 * @return the amountWithdrawn
	 */
	public int getAmountWithdrawn() {
		return getBalance();
	}

	/**
	 * @param amountWithdrawn the amountWithdrawn to set
	 */
	public void setAmountWithdrawn(int amountWithdrawn) {
		setBalance(amountWithdrawn);
	}

	/**
	 * @return the dateWithdrawn
	 */
	public Date getDateWithdrawn() {
		return getDate();
	}

	/**
	 * @param dateWithdrawn the dateWithdrawn to set
	 */
	public void setDateWithdrawn(Date dateWithdrawn) {
		setDate(dateWithdrawn);
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Deposit");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(getStudentUuid());
		builder.append(", term = ");
		builder.append(getTerm());
		builder.append(", year = ");
		builder.append(getYear());
		builder.append(", amountWithdrawn = ");
		builder.append(getBalance());
		builder.append(", dateWithdrawn = ");
		builder.append(getDate());
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3049160749554101808L;
}
