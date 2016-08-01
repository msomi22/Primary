/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * What is common to any exam that a student takes
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamCommon extends Performance{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3752806809956555105L;

	/**
	 * 
	 */
	public ExamCommon() {
		super();
	}
   
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExamCommon [");
		builder.append("getStudentUuid()=");
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid()=");
		builder.append(getSubjectUuid());
		builder.append(", getStreamUuid()=");
		builder.append(getStreamUuid());
		builder.append(", getClassUuid()="); 
		builder.append(getClassUuid());
		builder.append("]");
		return builder.toString();
	}
}
