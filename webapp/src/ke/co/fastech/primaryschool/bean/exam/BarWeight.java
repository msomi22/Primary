/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

/**
 * A weight to be used in drawing a bar graph
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class BarWeight {
	
	private String studentUuid;
	private String term;
	private String year;
	private double weight;
	
	/**
	 * 
	 */
	public BarWeight() {
		super();
		studentUuid = "";
		term = "";
		year = "";
		weight = 0;
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
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}


	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("BarWeight [ ");
		builder.append(", studentUuid=");
		builder.append(studentUuid);
		builder.append(", term=");
		builder.append(term);
		builder.append(", year=");
		builder.append(year);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString(); 
		}

}
