/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

/**
 * @author peter
 *
 */
public class PaperThree extends Perfomance{

	/**
	 * 
	 */
	public PaperThree() {
		super();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaperThree [=,");
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
		builder.append(", getPaperThree()=");
		builder.append(getPaperThree());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append("]");
		return builder.toString();
	}
}
