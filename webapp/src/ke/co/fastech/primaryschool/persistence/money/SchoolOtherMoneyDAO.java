/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.OtherMoney;

/**
 * Persistence description for {@link OtherMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolOtherMoneyDAO {
	/**
	 * 
	 * @param uuid
	 * @param term
	 * @param year
	 * @return
	 */
	public OtherMoney getOtherMoney(String description,String term,String year,String accountUuid);
	/**
	 * 
	 * @param otherMoney
	 * @return
	 */
	public boolean putOtherMoney(OtherMoney otherMoney);
	/**
	 * 
	 * @param otherMoney
	 * @return
	 */
	public boolean updateOtherMoney(OtherMoney otherMoney);
	/**
	 * 
	 * @param otherMoney
	 * @return
	 */
	public boolean deleteOtherMoney(OtherMoney otherMoney);
	/**
	 * 
	 * @param term
	 * @param year
	 * @return
	 */
	public List<OtherMoney> getOtherMoneyList(String term,String year,String accountUuid);
	/*
	 * 
	 */
	public List<OtherMoney> getOtherMoneyList(String accountUuid);
	
	

}
