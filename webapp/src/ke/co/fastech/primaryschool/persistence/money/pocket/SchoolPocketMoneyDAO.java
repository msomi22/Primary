/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money.pocket;

import ke.co.fastech.primaryschool.bean.money.pocket.PocketMoney;

/**
 * Persistence description for {@link PocketMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolPocketMoneyDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public PocketMoney getPocketMoney(String studentUuid);
	
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public boolean studentExist(String studentUuid);
	/**
	 * 
	 * @param studentUuid
	 * @param balance
	 * @return
	 */
	public boolean hasBalance(String studentUuid,int balance);
	/**
	 * 
	 * @param pocketMoney
	 * @param balance
	 * @return
	 */
	public boolean deposit(PocketMoney pocketMoney,String studentUuid,int balance);
	/**
	 * 
	 * @param pocketMoney
	 * @param balance
	 * @return
	 */
	public boolean withdraw(PocketMoney pocketMoney,String studentUuid,int balance);

}
