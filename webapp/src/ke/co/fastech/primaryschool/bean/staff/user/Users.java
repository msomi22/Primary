/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff.user;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a user who can login to the system
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Users extends StorableBean{
	
	 private String staffUuid;
	 private String username;
	 private String password;

	/**
	 * 
	 */
	public Users() {
		super();
		staffUuid = "";
		username = "";
		password = "";
	}
	
	/**
	 * @return the staffUuid
	 */
	public String getStaffUuid() {
		return staffUuid;
	}

	/**
	 * @param staffUuid the staffUuid to set
	 */
	public void setStaffUuid(String staffUuid) {
		this.staffUuid = staffUuid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Users");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", staffUuid = ");
		builder.append(staffUuid);
		builder.append(", username = ");
		builder.append(username);
		builder.append(", password = ");
		builder.append(password);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	private static final long serialVersionUID = -2692295076630154305L;
}
