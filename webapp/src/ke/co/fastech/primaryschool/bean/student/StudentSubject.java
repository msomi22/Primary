/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.student;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a subject that a student takes
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentSubject extends StorableBean{
	
	private String studentUuid;
	private String subjectUuid;

	/**
	 * 
	 */
	public StudentSubject() {
		super();
		studentUuid = "";
		subjectUuid = "";
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

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentSubject");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", subjectUuid = ");
		builder.append(subjectUuid);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4198594328897548141L;
}
