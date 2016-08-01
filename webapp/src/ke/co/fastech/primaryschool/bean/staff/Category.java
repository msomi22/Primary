/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.staff;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a category for which a staff must fall
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Category extends StorableBean{
	
	private String categoryName;

	/**
	 * 
	 */
	public Category() {
		super();
		categoryName = "";
	}
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Category");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", categoryName = ");
		builder.append(categoryName);
		builder.append("]");
		return builder.toString(); 
		}
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3436729852917925973L;
}
