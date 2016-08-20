/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.StudentOtherMoney;

/**
 * Persistence description for {@link StudentOtherMoney}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentOtherMoneyDAO {
	/**
	 * 
	 * @param studentUuid
	 * @param otherMoneyUuid
	 * @return
	 */
	 
	public StudentOtherMoney getStudentOtherMoney(String studentUuid,String otherMoneyUuid);
	
	/**
	 * 
	 * @param studentOtherMoney
	 * @return
	 */
	public boolean putStudentOtherMoney(StudentOtherMoney studentOtherMoney);
	
	
	/**
	 * 
	 * @param studentOtherMoney
	 * @return
	 */
	public boolean deleteStudentOtherMoney(String studentUuid,String otherMoneyUuid);
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public List<StudentOtherMoney> getStudentOtherMoneyList(String studentUuid,String term,String year);
	
	
	

}
