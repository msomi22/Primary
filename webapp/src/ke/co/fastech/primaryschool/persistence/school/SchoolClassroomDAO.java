/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.Classroom;

/**
 * Persistence description for {@link Classroom}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolClassroomDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Classroom getClassroomByUuid(String uuid );
	/**
	 * 
	 * @param className
	 * @return
	 */
	public Classroom getClassroom(String className);
	/**
	 * 
	 * @param classroom
	 * @return
	 */
	public boolean putClassroom(Classroom classroom);
	/**
	 * 
	 * @param classroom
	 * @return
	 */
	public boolean updateClassroom(Classroom classroom);
	/**
	 * 
	 * @return
	 */
	public List<Classroom> getClassroomList();

}
