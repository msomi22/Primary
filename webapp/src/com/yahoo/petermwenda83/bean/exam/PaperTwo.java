/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

/**
 * @author peter
 *
 */
public class PaperTwo extends Perfomance{

	/**
	 * 
	 */
	public PaperTwo() {
		super();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PapreTwo [=,");
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
		builder.append(", getPaperTwo()=");
		builder.append(getPaperTwo());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append("]");
		return builder.toString();
	}
}
