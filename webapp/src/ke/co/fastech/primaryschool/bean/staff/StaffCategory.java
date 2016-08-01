/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * teachers categories 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffCategory extends StorableBean{
	
	 private String staffUuid;
	 private String categoryUuid;

	/**
	 * 
	 */
	public StaffCategory() {
		super();
		staffUuid = "";
		categoryUuid = "";
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
	 * @return the categoryUuid
	 */
	public String getCategoryUuid() {
		return categoryUuid;
	}

	/**
	 * @param categoryUuid the categoryUuid to set
	 */
	public void setCategoryUuid(String categoryUuid) {
		this.categoryUuid = categoryUuid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StaffCategory");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", staffUuid = ");
		builder.append(staffUuid);
		builder.append(", categoryUuid = ");
		builder.append(categoryUuid);
		builder.append("]");
		return builder.toString(); 
		}

	 /**
	 * 
	 */
	 private static final long serialVersionUID = 231902936088689005L;
}
