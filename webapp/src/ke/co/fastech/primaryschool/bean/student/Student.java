/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.student;

import java.util.Date;
import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * a student in a school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Student extends StorableBean implements Comparable<Student>{
	
	private String accountUuid;
	private String statusUuid;
	private String streamUuid;
	private String admmissinNo;
	private String firstname;
	private String middlename;
	private String lastname;
	private String gender;
	private String dateofbirth;
	private String birthcertificateNo;
	private String country;
	private String county;
	private String ward;
	private int regTerm;
	private int regYear;
	private int finalTerm;
	private int finalYear;
	private String studentType;
	private String studentLevel;
	private Date admissiondate;

	/**
	 * 
	 */
	public Student() {
		super();
		accountUuid = "";
		statusUuid = "";
		streamUuid = "";
		admmissinNo = "";
		firstname = "";
		middlename = "";
		lastname = "";
		gender ="";
		dateofbirth = "";
		birthcertificateNo = "";
		country ="";
		county = "";
		ward = "";
		regTerm = 0;
		regYear = 0;
		finalTerm = 0;
		finalYear = 0;
		studentType = "";
		studentLevel = "";
		admissiondate = new Date();
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
	 * @return the statusUuid
	 */
	public String getStatusUuid() {
		return statusUuid;
	}

	/**
	 * @param statusUuid the statusUuid to set
	 */
	public void setStatusUuid(String statusUuid) {
		this.statusUuid = statusUuid;
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
	 * @return the admmissinNo
	 */
	public String getAdmmissinNo() {
		return admmissinNo.substring(0, Math.min(admmissinNo.length(), 4));
	}

	/**
	 * @param admmissinNo the admmissinNo to set
	 */
	public void setAdmmissinNo(String admmissinNo) {
		this.admmissinNo = admmissinNo;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname.substring(0, Math.min(firstname.length(), 10));
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename.substring(0, Math.min(middlename.length(), 10));
	}

	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname.substring(0, Math.min(lastname.length(), 10));
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateofbirth
	 */
	public String getDateofbirth() {
		return dateofbirth;
	}

	/**
	 * @param dateofbirth the dateofbirth to set
	 */
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	/**
	 * @return the birthcertificateNo
	 */
	public String getBirthcertificateNo() {
		return birthcertificateNo;
	}

	/**
	 * @param birthcertificateNo the birthcertificateNo to set
	 */
	public void setBirthcertificateNo(String birthcertificateNo) {
		this.birthcertificateNo = birthcertificateNo;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the ward
	 */
	public String getWard() {
		return ward;
	}

	/**
	 * @param ward the ward to set
	 */
	public void setWard(String ward) {
		this.ward = ward;
	}

	/**
	 * @return the regTerm
	 */
	public int getRegTerm() {
		return regTerm;
	}

	/**
	 * @param regTerm the regTerm to set
	 */
	public void setRegTerm(int regTerm) {
		this.regTerm = regTerm;
	}

	/**
	 * @return the regYear
	 */
	public int getRegYear() {
		return regYear;
	}

	/**
	 * @param regYear the regYear to set
	 */
	public void setRegYear(int regYear) {
		this.regYear = regYear;
	}

	/**
	 * @return the finalTerm
	 */
	public int getFinalTerm() {
		return finalTerm;
	}

	/**
	 * @param finalTerm the finalTerm to set
	 */
	public void setFinalTerm(int finalTerm) {
		this.finalTerm = finalTerm;
	}

	/**
	 * @return the finalYear
	 */
	public int getFinalYear() {
		return finalYear;
	}

	/**
	 * @param finalYear the finalYear to set
	 */
	public void setFinalYear(int finalYear) {
		this.finalYear = finalYear;
	}

	/**
	 * @return the studentLevel
	 */
	public String getStudentLevel() {
		return studentLevel;
	}

	/**
	 * @param studentLevel the studentLevel to set
	 */
	public void setStudentLevel(String studentLevel) {
		this.studentLevel = studentLevel;
	}

	/**
	 * @return the studentType
	 */
	public String getStudentType() {
		return studentType;
	}

	/**
	 * @param studentType the studentType to set
	 */
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	/**
	 * @return the admissiondate
	 */
	public Date getAdmissiondate() {
		return admissiondate;
	}

	/**
	 * @param admissiondate the admissiondate to set
	 */
	public void setAdmissiondate(Date admissiondate) {
		this.admissiondate = admissiondate;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student [getUuid() =");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(",statusUuid =");
		builder.append(statusUuid);		
		builder.append(",streamUuid =");
		builder.append(streamUuid);		
		builder.append(",admmissinNo =");
		builder.append(admmissinNo);	
		builder.append(",firstname =");
		builder.append(firstname);	
		builder.append(",middlename =");
		builder.append(middlename);
		builder.append(",lastname =");
		builder.append(lastname);
		builder.append(",gender =");
		builder.append(gender);
		builder.append(",dateofbirth =");
		builder.append(dateofbirth);
		builder.append(",birthcertificateNo =");
		builder.append(birthcertificateNo);  
		builder.append(",country =");
		builder.append(country);
		builder.append(",county =");
		builder.append(county);
		builder.append(",ward =");
		builder.append(ward);
		builder.append(",regTerm =");
		builder.append(regTerm);
		builder.append(",regYear =");
		builder.append(regYear);
		builder.append(",finalTerm =");
		builder.append(finalTerm);
		builder.append(",finalYear =");
		builder.append(finalYear);
		builder.append(",studentType =");
		builder.append(studentType);
		builder.append(",studentLevel =");
		builder.append(studentLevel);
		builder.append(",admissiondate =");
		builder.append(admissiondate);
		builder.append("]");
		return builder.toString(); 
		}

	@Override
	public int compareTo(Student ss) {
		return getAdmmissinNo().compareTo(((Student) ss).getAdmmissinNo());
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 5713239963783994513L;
}
