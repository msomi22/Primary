/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * a class teacher in the school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ClassTeacher extends StorableBean{
	
	 private String accountUuid;
	 private String teacherUuid;
	 private String streamUuid;

	/**
	 * 
	 */
	public ClassTeacher() {
		super();
		accountUuid = "";
		teacherUuid = "";
		streamUuid = "";
	}
	
	/**
	 * @return the accountUuid
	 */
	public String getAccountUuid() {
		return accountUuid;
	}

	/**
	 * @param accountUuid the accountUuid to set
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
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
		builder.append("ClassTeacher");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", teacherUuid = ");
		builder.append(teacherUuid);
		builder.append(", streamUuid = ");
		builder.append(streamUuid);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	 private static final long serialVersionUID = -2182024978505280821L;
}
