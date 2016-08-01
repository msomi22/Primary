/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * The students mean score for a particular exam
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class MeanScore extends StorableBean{
	
	private String studentUuid;
	private String term;
	private String year;
	private double meanScore;
	
	/**
	 * 
	 */
	public MeanScore() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		meanScore = 0;
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
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}


	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}


	/**
	 * @return the meanScore
	 */
	public double getMeanScore() {
		return meanScore;
	}


	/**
	 * @param meanScore the meanScore to set
	 */
	public void setMeanScore(double meanScore) {
		this.meanScore = meanScore;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("MeanScore [ getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid=");
		builder.append(studentUuid);
		builder.append(", term=");
		builder.append(term);
		builder.append(", year=");
		builder.append(year);
		builder.append(", meanScore=");
		builder.append(meanScore);
		builder.append("]");
		return builder.toString(); 
		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5244715530419961210L;
}
