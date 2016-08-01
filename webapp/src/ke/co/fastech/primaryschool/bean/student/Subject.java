/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.student;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a subject that a student can take
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Subject extends StorableBean{
	
	 private String subjectCode;
	 private String subjectName;
	
	/**
	 * 
	 */
	public Subject() {
		super();
		subjectCode = "";
		subjectName = "";
	}
	
	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("subject");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", subjectCode = ");
		builder.append(subjectCode);
		builder.append(", subjectName = ");
		builder.append(subjectName);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	private static final long serialVersionUID = 659465004997971654L;
}
