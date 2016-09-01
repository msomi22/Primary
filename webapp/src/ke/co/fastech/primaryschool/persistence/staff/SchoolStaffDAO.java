/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.util.List;

import ke.co.fastech.primaryschool.bean.staff.Staff;

/**
 * Persistence description for {@link Staff}
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStaffDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Staff getStaff(String uuid);
	/**
	 * 
	 * @param phone
	 * @return
	 */
	
	public Staff getStaff(String accountUuid,String employeeNo);
	/**
	 * 
	 * @param phone
	 * @return
	 */
	
	public boolean putStaff(Staff staff);
	/**
	 * 
	 * @param staff
	 * @return
	 */
	public boolean updateStaff(Staff staff);
	/**
	 * 
	 * @return
	 */
	public List<Staff> getStaffList(String accountUuid);

}
