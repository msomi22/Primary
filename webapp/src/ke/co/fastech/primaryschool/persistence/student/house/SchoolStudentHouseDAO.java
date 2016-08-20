/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.house;

import java.util.List;

import ke.co.fastech.primaryschool.bean.student.StudentHouse;

/**
 * Persistence description for {@link StudentHouse}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentHouseDAO {
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public StudentHouse getStudentHouse(String studentUuid);
	/**
	 * 
	 * @param studentUuid
	 * @param houseUuid
	 * @return
	 */
	public StudentHouse getStudentHouse(String studentUuid,String houseUuid);
	/**
	 * 
	 * @param studentHouse
	 * @return
	 */
	public boolean putStudentHouse(StudentHouse studentHouse);
	/**
	 * 
	 * @param studentHouse
	 * @return
	 */
	public boolean updateStudentHouse(String studentUuid,String houseUuid);
	/**
	 * 
	 * @param studentHouse
	 * @return
	 */
	public boolean deleteStudentHouse(String studentUuid);
	/**
	 * 
	 * @return
	 */
	public List<StudentHouse> getStudentHouseList();

}
