/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.RevertedMoney;

/**
 * Persistence description for {@link RevertedMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentRevertedMoneyDAO {
	
	/**
	 * 
	 * @param revertedMoney
	 * @return
	 */
	public boolean putRevertedMoney(RevertedMoney revertedMoney);
	/**
	 * 
	 * @param term
	 * @param year
	 * @return
	 */
	public List<RevertedMoney> getAllRevertedMoneyByTermYear(String studentUuid);
	

}
