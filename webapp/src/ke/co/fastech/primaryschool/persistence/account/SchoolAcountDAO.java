/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.account;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.account.Account;

/**
 * Persistence description for {@link Account}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolAcountDAO {
	/**
	 * 
	 * @param accountUuid
	 * @return school account object for the given accountUuid
	 */
	public Account getAccount(String accountUuid);
	/**
	 * 
	 * @param account
	 * @return whether account was inserted into database successfully
	 */
	public boolean put(Account account);
	/**
	 * 
	 * @param account
	 * @return whether account was updated successfully
	 */
	public boolean update(Account account);
	/**
	 * 
	 */
	public List<Account> getAllAccounts(); 
}
