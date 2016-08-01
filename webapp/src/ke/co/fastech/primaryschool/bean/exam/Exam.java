/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * This class keep the details of a particular exam(i.e exam out of)
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Exam extends StorableBean{
	
	private String examName;
    private int outOf;

	/**
	 * 
	 */
	public Exam() {
		super();
		examName = "";
		outOf = 0;
	}
	
	/**
	 * @return the examName
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * @param examName the examName to set
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}

	/**
	 * @return the outOf
	 */
	public int getOutOf() {
		return outOf;
	}

	/**
	 * @param outOf the outOf to set
	 */
	public void setOutOf(int outOf) {
		this.outOf = outOf;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Exam [ getUuid() = ");
		builder.append(getUuid());
		builder.append(",examName=");
		builder.append(examName);
		builder.append(",outOf=");
		builder.append(outOf);
		builder.append("]");
		return builder.toString(); 
		}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6687131956169870457L;

}
