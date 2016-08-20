/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff;

import java.util.Date;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 *  a staff in a school 
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Staff extends StorableBean{
	
	   private String accountUuid;
	   private String statusUuid;
	   private String employeeNo;
	   private String name;
	   private String phone;
	   private String email;
	   private String gender;
	   private String dob;
	   private String country;
	   private String county;
	   private String ward;
	   private Date regDate;

	/**
	 * 
	 */
	public Staff() {
		super();
		accountUuid = "";
		statusUuid = "";
		employeeNo = "";
		name = "";
		phone = "";
		email = "";
		gender = "";
		dob = "";
		country = "";
		county = "";
		ward = "";
		regDate = new Date();
		
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
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
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
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Staff");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", statusUuid = ");
		builder.append(statusUuid);
		builder.append(", employeeNo = ");
		builder.append(employeeNo);
		builder.append(", phone = ");
		builder.append(phone);
		builder.append(", email = ");
		builder.append(email);
		builder.append(", gender = ");
		builder.append(gender);
		builder.append(", dob = ");
		builder.append(dob);
		builder.append(", country = ");
		builder.append(country);
		builder.append(", county = ");
		builder.append(county);
		builder.append(", ward = ");
		builder.append(ward);
		builder.append(", regDate = ");
		builder.append(regDate);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	   private static final long serialVersionUID = 2335868823265535867L;
}
