/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.util.List;

import ke.co.fastech.primaryschool.bean.staff.ClassTeacher;

/**
 * Persistence description for {@link ClassTeacher}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolTeacherClassDAO {
	
	/**
	 * 
	 * @param teacherUuid
	 * @return
	 */
	public ClassTeacher getTeacherClass(String teacherUuid);
	
	/**
	 * 
	 * @param streamUuid
	 * @return
	 */
	public ClassTeacher getTeacherClassByClassid(String streamUuid);
	/**
	 * 
	 * @param teacherUuid
	 * @param streamUuid
	 * @return
	 */
	public ClassTeacher getTeacherClass(String teacherUuid,String streamUuid);
	/**
	 * 
	 * @param classTeacher
	 * @return
	 */
	public boolean putClassTeacher(ClassTeacher classTeacher);
	
	/**
	 * 
	 * @param classTeacher
	 * @return
	 */
	public boolean deleteClassTeacher(String teacherUuid);
	/**
	 * 
	 * @return
	 */
	public List<ClassTeacher> getClassTeacherList(String accountUuid); 

}
