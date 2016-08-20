/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.subject;

import java.util.List;

import ke.co.fastech.primaryschool.bean.student.StudentSubject;

/**
 * Persistence description for {@link StudentSubject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentSubjectDAO {
	
	/**
	 * 
	 * @param studentUuid
	 * @param subjectUuid
	 * @return
	 */
	public StudentSubject getStudentSubject(String studentUuid,String subjectUuid);
	/**
	 * 
	 * @param studentUuid
	 * @return
	 */
	public List<StudentSubject> getStudentSubjectList(String studentUuid);
	/**
	 * 
	 * @param studentSubject
	 * @return
	 */
	public boolean putStudentSubject(StudentSubject studentSubject);
	
	/**
	 * 
	 * @param studentSubject
	 * @return
	 */
	public boolean deleteStudentSubject(String studentUuid,String subjectUuid);
	
	

}
