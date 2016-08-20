/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import ke.co.fastech.primaryschool.bean.staff.StaffCategory;

/**
 * Persistence description for {@link StaffCategory}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStaffCategoryDAO {
	/**
	 * 
	 * @param staffUuid
	 * @return
	 */
	public StaffCategory getStaffCategory(String staffUuid);
	/**
	 * 
	 * @param staffUuid
	 * @param categoryUuid
	 * @return
	 */
	public StaffCategory getStaffCategory(String staffUuid,String categoryUuid);
	/**
	 * 
	 * @param staffCategory
	 * @return
	 */
	public boolean putStaffCategory(StaffCategory staffCategory);
	/**
	 * 
	 * @param staffCategory
	 * @return
	 */
	public boolean updateStaffCategory(StaffCategory staffCategory);
	/**
	 * 
	 * @param staffCategory
	 * @return
	 */
	public boolean deleteStaffCategory(String staffUuid);
	

}
