/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.student;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a house to which a student can reside
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentHouse extends StorableBean{
	
	private String studentUuid;
	private String houseUuid;

	/**
	 * 
	 */
	public StudentHouse() {
		super();
		studentUuid = "";
		houseUuid = "";
	}
	
	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}

	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}

	/**
	 * @return the houseUuid
	 */
	public String getHouseUuid() {
		return houseUuid;
	}

	/**
	 * @param houseUuid the houseUuid to set
	 */
	public void setHouseUuid(String houseUuid) {
		this.houseUuid = houseUuid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentHouse,");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", houseUuid = ");
		builder.append(houseUuid);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8226779171181188359L;
}
