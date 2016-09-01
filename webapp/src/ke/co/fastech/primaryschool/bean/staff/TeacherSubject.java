/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * Teacher subject assignment logic 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherSubject extends StorableBean{
	
	 private String teacherUuid;
	 private String subjectUuid;
	 private String streamUuid;

	/**
	 * 
	 */
	public TeacherSubject() {
		super();
		teacherUuid = "";
		subjectUuid = "";
		streamUuid = "";
	}
	
	/**
	 * @return the teacherUuid
	 */
	public String getTeacherUuid() {
		return teacherUuid;
	}

	/**
	 * @param teacherUuid the teacherUuid to set
	 */
	public void setTeacherUuid(String teacherUuid) {
		this.teacherUuid = teacherUuid;
	}

	/**
	 * @return the subjectUuid
	 */
	public String getSubjectUuid() {
		return subjectUuid;
	}

	/**
	 * @param subjectUuid the subjectUuid to set
	 */
	public void setSubjectUuid(String subjectUuid) {
		this.subjectUuid = subjectUuid;
	}

	/**
	 * @return the streamUuid
	 */
	public String getStreamUuid() {
		return streamUuid;
	}

	/**
	 * @param streamUuid the streamUuid to set
	 */
	public void setStreamUuid(String streamUuid) {
		this.streamUuid = streamUuid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TeacherSubject");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", teacherUuid = ");
		builder.append(teacherUuid);
		builder.append(", subjectUuid = ");
		builder.append(subjectUuid);
		builder.append(", streamUuid = ");
		builder.append(streamUuid);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	private static final long serialVersionUID = -8391257608149917413L;

}
