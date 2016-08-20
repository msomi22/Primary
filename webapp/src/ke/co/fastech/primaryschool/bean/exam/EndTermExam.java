/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * End term exam 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class EndTermExam extends Performance{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987861612292813030L;

	/**
	 * 
	 */
	public EndTermExam() {
		super();
	}
	
	/**
	 * @return the score
	 */
	public double getEndTerm() {
		return getScore();
	}

	/**
	 * @param score the score to set
	 */
	public void setEndTerm(double score) {
		setScore(score);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EndTermExam [=,");
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
		builder.append(", Endterm =");
		builder.append(getScore());
		builder.append("]");
		return builder.toString();
	}
}
