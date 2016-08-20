/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * Mid term exam 
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class MidTermExam extends Performance{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081729122514469402L;

	/**
	 * 
	 */
	public MidTermExam() {
		super();
	}
	
	/**
	 * @return the score
	 */
	public double getMidTerm() {
		return getScore();
	}

	/**
	 * @param score the score to set
	 */
	public void setMidTerm(double score) {
		setScore(score);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MidTermExam [=,");
		builder.append("getStudentUuid()=");
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid()=");
		builder.append(getSubjectUuid());
		builder.append(", getStreamUuid()=");
		builder.append(getStreamUuid());
		builder.append(", getClassUuid()="); 
		builder.append(getClassUuid());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append(", Midterm =");
		builder.append(getScore());
		builder.append("]");
		return builder.toString();
	}
}
