/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * Exam Result object 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamResult extends Performance{
	
	private double openner;
	private double midterm;
	private double endterm;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3752806809956555105L;

	/**
	 * 
	 */
	public ExamResult() {
		super();
		openner = 0;
		midterm = 0;
		endterm = 0;
	}
	
	
   
	/**
	 * @return the openner
	 */
	public double getOpenner() {
		return openner;
	}



	/**
	 * @param openner the openner to set
	 */
	public void setOpenner(double openner) {
		this.openner = openner;
	}



	/**
	 * @return the midterm
	 */
	public double getMidterm() {
		return midterm;
	}



	/**
	 * @param midterm the midterm to set
	 */
	public void setMidterm(double midterm) {
		this.midterm = midterm;
	}



	/**
	 * @return the endterm
	 */
	public double getEndterm() {
		return endterm;
	}



	/**
	 * @param endterm the endterm to set
	 */
	public void setEndterm(double endterm) {
		this.endterm = endterm;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExamResult [");
		builder.append("getStudentUuid() = ");
		builder.append(getStudentUuid());
		builder.append(", getSubjectUuid() = ");
		builder.append(getSubjectUuid());
		builder.append(", getStreamUuid() = ");
		builder.append(getStreamUuid());
		builder.append(", getClassUuid() = "); 
		builder.append(getClassUuid());
		builder.append(", getTerm() = ");
		builder.append(getTerm());
		builder.append(", getYear() = ");
		builder.append(getYear());
		builder.append(", openner = ");
		builder.append(openner);
		builder.append(", midterm = ");
		builder.append(midterm);
		builder.append(", endterm = ");
		builder.append(endterm);
		builder.append("]");
		return builder.toString();
	}
}
