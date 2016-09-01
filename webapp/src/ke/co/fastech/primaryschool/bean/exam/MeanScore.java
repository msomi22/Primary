/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * The students mean score for a particular exam
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class MeanScore{
	
	private String studentUuid;
	private String term;
	private String year;
	private double meanScore;
	private String streamPosition;
	private String classPosition;
	
	/**
	 * 
	 */
	public MeanScore() {
		studentUuid = "";
		term = "";
		year = "";
		meanScore = 0;
		streamPosition = "";
		classPosition = "";
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
	 * @return the streamPosition
	 */
	public String getStreamPosition() {
		return streamPosition;
	}


	/**
	 * @param streamPosition the streamPosition to set
	 */
	public void setStreamPosition(String streamPosition) {
		this.streamPosition = streamPosition;
	}


	/**
	 * @return the classPosition
	 */
	public String getClassPosition() {
		return classPosition;
	}


	/**
	 * @param classPosition the classPosition to set
	 */
	public void setClassPosition(String classPosition) {
		this.classPosition = classPosition;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("MeanScore [ ");
		builder.append(" studentUuid=");
		builder.append(studentUuid);
		builder.append(", term=");
		builder.append(term);
		builder.append(", year=");
		builder.append(year);
		builder.append(", meanScore=");
		builder.append(meanScore);
		builder.append(", streamPosition=");
		builder.append(streamPosition);
		builder.append(", classPosition=");
		builder.append(classPosition);
		builder.append("]");
		return builder.toString();    
		}
	
}
