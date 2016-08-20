/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school.account;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * A school account
 *
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Account extends StorableBean{
	
	private String schoolName;
	private String schoolEmail;
	private String schoolPhone;
	private String schoolAddres;
	private String schoolHomeTown;
	private String schoolCounty;
	private String schoolMotto;
	private String dayBoarding;

	/**
	 * 
	 */
	public Account() {
		super();
		schoolName = "";
		schoolEmail = "";
		schoolPhone = "";
		schoolAddres = "";
		schoolHomeTown = "";
		schoolCounty = "";
		schoolMotto = "";
		dayBoarding = "";
	}
	
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the schoolEmail
	 */
	public String getSchoolEmail() {
		return schoolEmail;
	}

	/**
	 * @param schoolEmail the schoolEmail to set
	 */
	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}

	/**
	 * @return the schoolPhone
	 */
	public String getSchoolPhone() {
		return schoolPhone;
	}

	/**
	 * @param schoolPhone the schoolPhone to set
	 */
	public void setSchoolPhone(String schoolPhone) {
		this.schoolPhone = schoolPhone;
	}

	/**
	 * @return the schoolAddres
	 */
	public String getSchoolAddres() {
		return schoolAddres;
	}

	/**
	 * @param schoolAddres the schoolAddres to set
	 */
	public void setSchoolAddres(String schoolAddres) {
		this.schoolAddres = schoolAddres;
	}

	/**
	 * @return the schoolHomeTown
	 */
	public String getSchoolHomeTown() {
		return schoolHomeTown;
	}

	/**
	 * @param schoolHomeTown the schoolHomeTown to set
	 */
	public void setSchoolHomeTown(String schoolHomeTown) {
		this.schoolHomeTown = schoolHomeTown;
	}

	/**
	 * @return the schoolCounty
	 */
	public String getSchoolCounty() {
		return schoolCounty;
	}

	/**
	 * @param schoolCounty the schoolCounty to set
	 */
	public void setSchoolCounty(String schoolCounty) {
		this.schoolCounty = schoolCounty;
	}

	/**
	 * @return the schoolMotto
	 */
	public String getSchoolMotto() {
		return schoolMotto;
	}

	/**
	 * @param schoolMotto the schoolMotto to set
	 */
	public void setSchoolMotto(String schoolMotto) {
		this.schoolMotto = schoolMotto;
	}
	
	

	/**
	 * @return the dayBoarding
	 */
	public String getDayBoarding() {
		return dayBoarding;
	}

	/**
	 * @param dayBoarding the dayBoarding to set
	 */
	public void setDayBoarding(String dayBoarding) {
		this.dayBoarding = dayBoarding;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SchoolAccount");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",schoolName=");
		builder.append(schoolName);
		builder.append(",schoolEmail=");
		builder.append(schoolEmail);
		builder.append(", schoolPhone=");
		builder.append(schoolPhone);
		builder.append(",schoolAddres=");
		builder.append(schoolAddres);
		builder.append(",schoolHomeTown=");
		builder.append(schoolHomeTown);
		builder.append(",schoolCounty=");
		builder.append(schoolCounty);
		builder.append(",schoolMotto=");
		builder.append(schoolMotto);
		builder.append(",dayBoarding=");
		builder.append(dayBoarding);
		builder.append("]");
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4525478845270295686L;

}
