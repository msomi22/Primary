/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.exam;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * The grading criteria used in exam 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class GradingSystem extends StorableBean{
	
	private String accountUuid;
	private int GradeAplain;
	private int GradeAminus;
	private int GradeBplus;
	private int GradeBplain;
	private int GradeBminus;
	private int GradeCplus;
	private int GradeCplain;
	private int GradeCminus;
	private int GradeDplus;
	private int GradeDplain;
	private int GradeDminus;
	private int GradeE;

	/**
	 * 
	 */
	public GradingSystem() {
		super();
		accountUuid = "";
		GradeAplain = 0;
		GradeAminus = 0;
		GradeBplus = 0;
		GradeBminus = 0;
		GradeCplus = 0;
		GradeCplain = 0;
		GradeCminus = 0;
		GradeDplus = 0;
		GradeDplain = 0;
		GradeDminus = 0;
		GradeE = 0;
	}
	

	/**
	 * @return the accountUuid
	 */
	public String getAccountUuid() {
		return accountUuid;
	}


	/**
	 * @param accountUuid the accountUuid to set
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}


	/**
	 * @return the gradeAplain
	 */
	public int getGradeAplain() {
		return GradeAplain;
	}


	/**
	 * @param gradeAplain the gradeAplain to set
	 */
	public void setGradeAplain(int gradeAplain) {
		GradeAplain = gradeAplain;
	}


	/**
	 * @return the gradeAminus
	 */
	public int getGradeAminus() {
		return GradeAminus;
	}


	/**
	 * @param gradeAminus the gradeAminus to set
	 */
	public void setGradeAminus(int gradeAminus) {
		GradeAminus = gradeAminus;
	}


	/**
	 * @return the gradeBplus
	 */
	public int getGradeBplus() {
		return GradeBplus;
	}


	/**
	 * @param gradeBplus the gradeBplus to set
	 */
	public void setGradeBplus(int gradeBplus) {
		GradeBplus = gradeBplus;
	}


	/**
	 * @return the gradeBplain
	 */
	public int getGradeBplain() {
		return GradeBplain;
	}


	/**
	 * @param gradeBplain the gradeBplain to set
	 */
	public void setGradeBplain(int gradeBplain) {
		GradeBplain = gradeBplain;
	}


	/**
	 * @return the gradeBminus
	 */
	public int getGradeBminus() {
		return GradeBminus;
	}


	/**
	 * @param gradeBminus the gradeBminus to set
	 */
	public void setGradeBminus(int gradeBminus) {
		GradeBminus = gradeBminus;
	}


	/**
	 * @return the gradeCplus
	 */
	public int getGradeCplus() {
		return GradeCplus;
	}


	/**
	 * @param gradeCplus the gradeCplus to set
	 */
	public void setGradeCplus(int gradeCplus) {
		GradeCplus = gradeCplus;
	}


	/**
	 * @return the gradeCplain
	 */
	public int getGradeCplain() {
		return GradeCplain;
	}


	/**
	 * @param gradeCplain the gradeCplain to set
	 */
	public void setGradeCplain(int gradeCplain) {
		GradeCplain = gradeCplain;
	}


	/**
	 * @return the gradeCminus
	 */
	public int getGradeCminus() {
		return GradeCminus;
	}


	/**
	 * @param gradeCminus the gradeCminus to set
	 */
	public void setGradeCminus(int gradeCminus) {
		GradeCminus = gradeCminus;
	}


	/**
	 * @return the gradeDplus
	 */
	public int getGradeDplus() {
		return GradeDplus;
	}


	/**
	 * @param gradeDplus the gradeDplus to set
	 */
	public void setGradeDplus(int gradeDplus) {
		GradeDplus = gradeDplus;
	}


	/**
	 * @return the gradeDplain
	 */
	public int getGradeDplain() {
		return GradeDplain;
	}


	/**
	 * @param gradeDplain the gradeDplain to set
	 */
	public void setGradeDplain(int gradeDplain) {
		GradeDplain = gradeDplain;
	}


	/**
	 * @return the gradeDminus
	 */
	public int getGradeDminus() {
		return GradeDminus;
	}


	/**
	 * @param gradeDminus the gradeDminus to set
	 */
	public void setGradeDminus(int gradeDminus) {
		GradeDminus = gradeDminus;
	}


	/**
	 * @return the gradeE
	 */
	public int getGradeE() {
		return GradeE;
	}


	/**
	 * @param gradeE the gradeE to set
	 */
	public void setGradeE(int gradeE) {
		GradeE = gradeE;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("GradingSysem [ getUuid() =");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(",GradeAplain=");
		builder.append(GradeAplain);
		builder.append(", GradeAminus =");
		builder.append(GradeAminus);
		builder.append(", GradeBplus =");
		builder.append(GradeBplus);
		builder.append(", GradeBminus =");
		builder.append(GradeBminus);
		builder.append(", GradeCplus =");
		builder.append(GradeCplus);
		builder.append(", GradeCplain =");
		builder.append(GradeCplain);
		builder.append(", GradeCminus =");
		builder.append(GradeCminus);
		builder.append(", GradeDplus =");
		builder.append(GradeDplus);
		builder.append(", GradeDplain =");
		builder.append(GradeDplain);
		builder.append(", GradeDminus =");
		builder.append(GradeDminus);
		builder.append(", GradeE =");
		builder.append(GradeE);
		builder.append("]");
		return builder.toString();   
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2952936958302446375L;

}
