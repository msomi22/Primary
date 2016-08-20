/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * Opener exam 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class OpenerExam extends Performance{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2610932939736726094L;


	/**
	 * 
	 */
	public OpenerExam() {
		super();
	}
	
	/**
	 * @return the score
	 */
	public double getOpenner() {
		return getScore();
	}

	/**
	 * @param score the score to set
	 */
	public void setOpenner(double score) {
		setScore(score); 
	}
  

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpenerExam [=,");
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
		builder.append(", Openner =");
		builder.append(getScore());
		builder.append("]");
		return builder.toString();
	}
}
