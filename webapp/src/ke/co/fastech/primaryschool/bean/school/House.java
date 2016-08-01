/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a hpuse in the school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class House extends StorableBean{
	
	private String houseName;

	/**
	 * 
	 */
	public House() {
		super();
		houseName = "";
	}
	
	/**
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}

	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("House");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", houseName = ");
		builder.append(houseName);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4540038359429057822L;
}
