/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.parent;

import java.util.List;

import ke.co.fastech.primaryschool.bean.student.StudentParent;

/**
 * Persistence description for {@link StudentParent}
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentParentDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public StudentParent getStudentParent(String studentUuid);
	/**
	 * 
	 * @param parentPhone
	 * @return
	 */
	public StudentParent getStudentParentByPhone(String parentPhone);
	/**
	 * 
	 * @param parentEmail
	 * @return
	 */
	public StudentParent getStudentParentByEmail(String parentEmail);
	/**
	 * 
	 * @param studentParent
	 * @return
	 */
	public boolean putStudentParent(StudentParent studentParent);
	/**
	 * 
	 * @param studentParent
	 * @return
	 */
	public boolean updateStudentParent(StudentParent studentParent);
	/**
	 * 
	 * @param studentParent
	 * @return
	 */
	public boolean deleteStudentParent(String studentUuid);
	/**
	 * 
	 * @return
	 */
	public List<StudentParent> getStudentParentList();

}
