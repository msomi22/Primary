/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

/**
 * @author peter
 *
 */
public class EndTerm extends Perfomance{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1022193371404580518L;


	/**
	 * 
	 */
	public EndTerm() {
		super();
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("End Term [");
		builder.append(", getSchoolAccountUuid()=");
		builder.append(getSchoolAccountUuid());
		builder.append(", getClassRoomUuid()=");
		builder.append(getClassRoomUuid());
		builder.append(", getTeacherUuid()=");
		builder.append(getTeacherUuid());
		builder.append(", getStudentUuid()="); 
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid()=");
		builder.append(getSubjectUuid());
		builder.append(", getEndTerm()=");
		builder.append(getEndTerm());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append("]");
		return builder.toString();
	}

}
