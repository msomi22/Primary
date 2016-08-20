/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.money.pocket;

import java.util.Date;

/**
 * used while deposit pocket money
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Deposit extends PocketMoney{

	/**
	 * 
	 */
	public Deposit() {
		super();
	}
	

	/**
	 * @return the amountDeposited
	 */
	public int getAmountDeposited() {
		return getBalance();
	}

	/**
	 * @param amountDeposited the amountDeposited to set
	 */
	public void setAmountDeposited(int amountDeposited) {
		setBalance(amountDeposited);
	}

	/**
	 * @return the dateDeposited
	 */
	public Date getDateDeposited() {
		return getDate();
	}

	/**
	 * @param dateDeposited the dateDeposited to set
	 */
	public void setDateDeposited(Date dateDeposited) {
		setDate(dateDeposited);
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
		builder.append(", amountDeposited = ");
		builder.append(getBalance());
		builder.append(", dateDeposited = ");
		builder.append(getDate());
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554853914339672815L;
}
