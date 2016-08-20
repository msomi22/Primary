/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a comment in the report card
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Comment extends StorableBean{
	
	private String accountUuid;
	private String headTeacherCom;
	private String gradeAplaincom;
	private String gradeAminuscom;
	private String gradeBpluscom;
	private String gradeBplaincom;
	private String gradeBminuscom;
	private String gradeCpluscom;
	private String gradeCplaincom;
	private String gradeCminuscom;
	private String gradeDpluscom;
	private String gradeDplaincom;
	private String gradeDminuscom;
	private String gradeEcom;

	/**
	 * 
	 */
	public Comment() {
		super();
		accountUuid = "";
		headTeacherCom = "";
		gradeAplaincom = "";
		gradeAminuscom = "";
		gradeBpluscom = "";
		gradeBplaincom = "";
		gradeBminuscom = "";
		gradeCpluscom = "";
		gradeCplaincom = "";
		gradeCminuscom = "";
		gradeDpluscom = "";
		gradeDplaincom = "";
		gradeDminuscom = "";
		gradeEcom = "";
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
	 * @return the headTeacherCom
	 */
	public String getHeadTeacherCom() {
		return headTeacherCom;
	}

	/**
	 * @param headTeacherCom the headTeacherCom to set
	 */
	public void setHeadTeacherCom(String headTeacherCom) {
		this.headTeacherCom = headTeacherCom;
	}

	/**
	 * @return the gradeAplaincom
	 */
	public String getGradeAplaincom() {
		return gradeAplaincom;
	}

	/**
	 * @param gradeAplaincom the gradeAplaincom to set
	 */
	public void setGradeAplaincom(String gradeAplaincom) {
		this.gradeAplaincom = gradeAplaincom;
	}

	/**
	 * @return the gradeAminuscom
	 */
	public String getGradeAminuscom() {
		return gradeAminuscom;
	}

	/**
	 * @param gradeAminuscom the gradeAminuscom to set
	 */
	public void setGradeAminuscom(String gradeAminuscom) {
		this.gradeAminuscom = gradeAminuscom;
	}

	/**
	 * @return the gradeBpluscom
	 */
	public String getGradeBpluscom() {
		return gradeBpluscom;
	}

	/**
	 * @param gradeBpluscom the gradeBpluscom to set
	 */
	public void setGradeBpluscom(String gradeBpluscom) {
		this.gradeBpluscom = gradeBpluscom;
	}

	/**
	 * @return the gradeBplaincom
	 */
	public String getGradeBplaincom() {
		return gradeBplaincom;
	}

	/**
	 * @param gradeBplaincom the gradeBplaincom to set
	 */
	public void setGradeBplaincom(String gradeBplaincom) {
		this.gradeBplaincom = gradeBplaincom;
	}

	/**
	 * @return the gradeBminuscom
	 */
	public String getGradeBminuscom() {
		return gradeBminuscom;
	}

	/**
	 * @param gradeBminuscom the gradeBminuscom to set
	 */
	public void setGradeBminuscom(String gradeBminuscom) {
		this.gradeBminuscom = gradeBminuscom;
	}

	/**
	 * @return the gradeCpluscom
	 */
	public String getGradeCpluscom() {
		return gradeCpluscom;
	}

	/**
	 * @param gradeCpluscom the gradeCpluscom to set
	 */
	public void setGradeCpluscom(String gradeCpluscom) {
		this.gradeCpluscom = gradeCpluscom;
	}

	/**
	 * @return the gradeCplaincom
	 */
	public String getGradeCplaincom() {
		return gradeCplaincom;
	}

	/**
	 * @param gradeCplaincom the gradeCplaincom to set
	 */
	public void setGradeCplaincom(String gradeCplaincom) {
		this.gradeCplaincom = gradeCplaincom;
	}

	/**
	 * @return the gradeCminuscom
	 */
	public String getGradeCminuscom() {
		return gradeCminuscom;
	}

	/**
	 * @param gradeCminuscom the gradeCminuscom to set
	 */
	public void setGradeCminuscom(String gradeCminuscom) {
		this.gradeCminuscom = gradeCminuscom;
	}

	/**
	 * @return the gradeDpluscom
	 */
	public String getGradeDpluscom() {
		return gradeDpluscom;
	}

	/**
	 * @param gradeDpluscom the gradeDpluscom to set
	 */
	public void setGradeDpluscom(String gradeDpluscom) {
		this.gradeDpluscom = gradeDpluscom;
	}

	/**
	 * @return the gradeDplaincom
	 */
	public String getGradeDplaincom() {
		return gradeDplaincom;
	}

	/**
	 * @param gradeDplaincom the gradeDplaincom to set
	 */
	public void setGradeDplaincom(String gradeDplaincom) {
		this.gradeDplaincom = gradeDplaincom;
	}

	/**
	 * @return the gradeDminuscom
	 */
	public String getGradeDminuscom() {
		return gradeDminuscom;
	}

	/**
	 * @param gradeDminuscom the gradeDminuscom to set
	 */
	public void setGradeDminuscom(String gradeDminuscom) {
		this.gradeDminuscom = gradeDminuscom;
	}

	/**
	 * @return the gradeEcom
	 */
	public String getGradeEcom() {
		return gradeEcom;
	}

	/**
	 * @param gradeEcom the gradeEcom to set
	 */
	public void setGradeEcom(String gradeEcom) {
		this.gradeEcom = gradeEcom;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Comment");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", headTeacherCom = ");
		builder.append(headTeacherCom);
		builder.append(", gradeAplaincom = ");
		builder.append(gradeAplaincom);
		builder.append(", gradeAminuscom = ");
		builder.append(gradeAminuscom);
		builder.append(", gradeBpluscom = ");
		builder.append(gradeBpluscom);
		builder.append(", gradeBplaincom = ");
		builder.append(gradeBplaincom);
		builder.append(", gradeBminuscom = ");
		builder.append(gradeBminuscom);
		builder.append(", gradeCpluscom = ");
		builder.append(gradeCpluscom);
		builder.append(", gradeCplaincom = ");
		builder.append(gradeCplaincom);
		builder.append(", gradeCminuscom = ");
		builder.append(gradeCminuscom);
		builder.append(", gradeDpluscom = ");
		builder.append(gradeDpluscom);
		builder.append(", gradeDplaincom = ");
		builder.append(gradeDplaincom);
		builder.append(", gradeDminuscom = ");
		builder.append(gradeDminuscom);
		builder.append(", gradeEcom = ");
		builder.append(gradeEcom);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4124852255930083812L;
}
