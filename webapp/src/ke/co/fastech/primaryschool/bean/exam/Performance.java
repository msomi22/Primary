/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * performance analyzing logic 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Performance extends StorableBean{
	
	private String studentUuid;
	private String subjectUuid;
	private String streamUuid;
	private String classUuid;
	private String term;
	private String year;
	private double openner;
	private double midterm;
	private double endterm;

	/**
	 * 
	 */
	public Performance() {
		  super();
		  studentUuid = "";
		  subjectUuid = "";
		  streamUuid = "";
		  classUuid = "";
		  term = "";
		  year = "";
		  openner = 0;
		  midterm = 0;
		  endterm = 0;
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

	/**
	 * @return the streamUuid
	 */
	public String getStreamUuid() {
		return streamUuid;
	}

	/**
	 * @param streamUuid the streamUuid to set
	 */
	public void setStreamUuid(String streamUuid) {
		this.streamUuid = streamUuid;
	}

	/**
	 * @return the classUuid
	 */
	public String getClassUuid() {
		return classUuid;
	}

	/**
	 * @param classUuid the classUuid to set
	 */
	public void setClassUuid(String classUuid) {
		this.classUuid = classUuid;
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
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Performance [");
		builder.append("studentUuid =");
		builder.append(studentUuid); 
		builder.append(", subjectUuid =");
		builder.append(subjectUuid); 
		builder.append(", streamUuid =");
		builder.append(streamUuid);
		builder.append(", classUuid =");
		builder.append(classUuid);
		builder.append(", term =");
		builder.append(term);
		builder.append(", year =");
		builder.append(year);
		builder.append(", openner =");
		builder.append(openner);
		builder.append(", midterm =");
		builder.append(midterm);
		builder.append(", endterm =");
		builder.append(endterm);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1425917000683716473L;
}
