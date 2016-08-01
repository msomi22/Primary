/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.student;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a parent for a particular student
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentParent extends StorableBean{
	
	private String studentUuid;
	private String parentName;
	private String parentPhone;
	private String parentEmail;

	/**
	 * 
	 */
	public StudentParent() {
		super();
		studentUuid = "";
		parentName = "";
		parentPhone = "";
		parentEmail = "";
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
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the parentPhone
	 */
	public String getParentPhone() {
		return parentPhone;
	}

	/**
	 * @param parentPhone the parentPhone to set
	 */
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	/**
	 * @return the parentEmail
	 */
	public String getParentEmail() {
		return parentEmail;
	}

	/**
	 * @param parentEmail the parentEmail to set
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentParent");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", parentName = ");
		builder.append(parentName);
		builder.append(", parentPhone = ");
		builder.append(parentPhone);
		builder.append(", parentEmail = ");
		builder.append(parentEmail);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501017524827030275L;
}
