/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import java.util.List;

import ke.co.fastech.primaryschool.bean.exam.BarWeight;

/**
 * Persistence description for {@link BarWeight}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolBarWeightDAO {
	
	/**
	 * 
	 * @param studentuuid
	 * @param year
	 * @return
	 */
	public List<BarWeight> getBarWeightList(String studentuuid,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return {@link BarWeight} Object
	 */
	public BarWeight getBarWeight(String studentuuid,String term,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link BarWeight} object exist in the RDMS
	 */
	public boolean existWeight(String studentuuid,String term,String year);
	/**
	 * 
	 * @param weight The weight
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link BarWeight} was inserted successfully 
	 */
	 
	public boolean putWeight(double weight,String studentuuid,String term,String year);
	

}
