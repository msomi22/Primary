/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.money;

import java.util.List;

import ke.co.fastech.primaryschool.bean.money.StudentFee;

/**
 * Persistence description for {@link StudentFee}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentFeeDAO {
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public StudentFee getStudentFee(String studentUuid,String term,String year);
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @param transactionID
	 * @return
	 */
	public StudentFee getStudentFee(String studentUuid,String term,String year,String transactionID);
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return
	 */
	public List<StudentFee> getStudentFeeList(String studentUuid,String term,String year);
	
	/**
	 * 
	 * @param studentFee
	 * @return
	 */
	public boolean putStudentFee(StudentFee studentFee);
	/**
	 * 
	 * @param studentFee
	 * @return
	 */
	public boolean updateStudentFee(StudentFee studentFee);
	/**
	 * 
	 * @param term
	 * @param year
	 * @return
	 */
	public List<StudentFee> getAllStudentFeeByTermYear(String term,String year);
	

}
