/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

/**
 * @author peter
 *
 */
public class Common extends Perfomance{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494866295837176808L;

	/**
	 * 
	 */
	public Common() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Common [= ,");
		builder.append("getSchoolAccountUuid()=");
		builder.append(getSchoolAccountUuid());
		builder.append(", getClassRoomUuid()=");
		builder.append(getClassRoomUuid());
		builder.append(", getTeacherUuid()=");
		builder.append(getTeacherUuid());
		builder.append(", getStudentUuid()="); 
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid()=");
		builder.append(getSubjectUuid());
		builder.append("]");
		return builder.toString();
	}
}
