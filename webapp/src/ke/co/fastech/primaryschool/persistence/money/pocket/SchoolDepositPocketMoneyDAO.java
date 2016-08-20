/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.pocket.Deposit;

/**
 * Persistence description for {@link Deposit}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolDepositPocketMoneyDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<Deposit > getDeposit(String studentUuid);
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public List<Deposit > getDeposit(String studentUuid,String term,String year);
	

}
