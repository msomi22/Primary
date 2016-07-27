/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

/**
 * @author peter
 *
 */
public class CatTwo extends Perfomance{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8589583534878635968L;



	/**
	 * 
	 */
	public CatTwo() {
		super();
	}
	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cat Two [");
		builder.append("getSchoolAccountUuid()=");
		builder.append(getSchoolAccountUuid());
		builder.append(", getClassRoomUuid()=");
		builder.append(getClassRoomUuid());
		builder.append(", getTeacherUuid()=");//getClassesUuid()
		builder.append(getTeacherUuid());
		builder.append(", getStudentUuid()="); 
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid()=");
		builder.append(getSubjectUuid());
		builder.append(", getCatTwo()=");
		builder.append(getCatTwo());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append("]");
		return builder.toString();
	}

}
