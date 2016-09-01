/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.TermFee;

/**
 * Persistence description for {@link TermFee}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolTermFeeDAO {
	
	
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public boolean putTermFee(TermFee termFee);
	
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public boolean updateTermFee(TermFee termFee);
	/**
	 * 
	 * @param term
	 * @param year
	 * @return
	 */
	public List<TermFee> getTermFeeList(String term,String year,String accountUuid);
	/**
	 * 
	 * @return
	 */
	public List<TermFee> getTermFeeList(String accountUuid);

}
