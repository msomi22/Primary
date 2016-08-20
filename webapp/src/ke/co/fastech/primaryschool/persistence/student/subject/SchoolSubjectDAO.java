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

import ke.co.fastech.primaryschool.bean.student.Subject;

/**
 * Persistence description for {@link Subject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolSubjectDAO {
	/**
	 * 
	 * @param subjectCode
	 * @return
	 */
	public Subject getSubject(String subjectCode);
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Subject getSubjectByUuid(String uuid);
	/**
	 * 
	 * @return
	 */
	public List<Subject> getSubjectList();

}
