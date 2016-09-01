/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student;

import java.util.List;

import ke.co.fastech.primaryschool.bean.student.Student;

/**
 * Persistence description for {@link Student}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentDAO {
	/**
	 * 
	 * @param Uuid
	 * @return a {@link Student} object with studentUuid
	 */
	public Student getStudentByUuid(String Uuid);
	/**
	 * 
	 * @param admmissinNo
	 * @param statusUuid
	 * @param accountUuid
	 * @return
	 */
	 
	public Student getStudentBystatus(String admmissinNo,String statusUuid,String accountUuid);
	/**
	 * 
	 * @param admmissinNo
	 * @param streamUuid
	 * @param accountUuid
	 * @return
	 */
	 
	public Student getStudentBystream(String admmissinNo,String streamUuid,String accountUuid);
	/**
	 * 
	 * @param admmissinNo
	 * @return a {@link Student} object with admmissinNo
	 */
	public Student getStudentByADMNO(String admmissinNo,String accountUuid);
	/**
	 * 
	 * @param student
	 * @return whether {@link Student} object was inserted successfully 
	 */
	public boolean putStudent(Student student);
	/**
	 * 
	 * @param student
	 * @return whether {@link Student} object was updated successfully  
	 */
	public boolean updateStudent(Student student);
	/**
	 * 
	 * @param admmissinNo
	 * @return {@link List} of all {@link Student}(s) in the school with admission number like admmissinNo
	 */
	public List<Student> getStudentListByAdmNo(String admmissinNo,String accountUuid);
	/**
	 * 
	 * @param streamUuid
	 * @return {@link List} of all {@link Student}(s) in the school for a given streamUuid
	 */
	public List<Student> getStudentListByStreamUuid(String streamUuid,String accountUuid);
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Student> getStudentsListByLimit(int startIndex,int endIndex,String accountUuid);
	/**
	 * 
	 * @return {@link List} of all {@link Student}(s) in the school
	 */
	public List<Student> getStudentsList(String accountUuid);
	/**
	 * 
	 * @param statusUuid
	 * @param streamUuid
	 * @return
	 */
	public int getStudentCountPerClass(String statusUuid,String streamUuid,String accountUuid);
	/**
	 * 
	 * @param statusUuid
	 * @param accountUuid
	 * @return
	 */
	public int getStudentCount(String statusUuid,String accountUuid);
	/**
	 * 
	 * @param studentType
	 * @param statusUuid
	 * @param accountUuid
	 * @return
	 */
	public int getStudentCountByCategory(String studentType,String statusUuid,String accountUuid);
	/**
	 * 
	 * @param studentLevel
	 * @param statusUuid
	 * @param accountUuid
	 * @return
	 */
	public int getStudentCountByLevel(String studentLevel,String statusUuid,String accountUuid);
	
	
}
