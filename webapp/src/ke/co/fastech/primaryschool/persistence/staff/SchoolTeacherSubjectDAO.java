/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.util.List;

import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;

/**
 * Persistence description for {@link TeacherSubject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolTeacherSubjectDAO {
	
	/**
	 * 
	 * @param teacherUuid
	 * @param subjectUuid
	 * @param streamUuid
	 * @return
	 */
	public TeacherSubject getTeacherSubject(String teacherUuid,String subjectUuid,String streamUuid);
	/**
	 * 
	 * @param teacherUuid
	 * @return
	 */
	public List<TeacherSubject> getTeacherSubjectList(String teacherUuid);
	/**
	 * 
	 * @param teacherSubject
	 * @return
	 */
	public boolean putTeacherSubject(TeacherSubject teacherSubject);
	
	/**
	 * 
	 * @param teacherSubject
	 * @return
	 */
	public boolean deleteTeacherSubject(String teacherUuid,String subjectUuid,String streamUuid);
	/**
	 * 
	 * @param teacherUuid
	 * @return
	 */
	public boolean deleteTeacherSubject(String teacherUuid);
	/**
	 * 
	 * @return
	 */
	public List<TeacherSubject> getAllTeacherSubjectList();

}
