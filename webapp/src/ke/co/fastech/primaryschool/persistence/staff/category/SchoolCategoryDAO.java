/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import java.util.List;

import ke.co.fastech.primaryschool.bean.staff.Category;

/**
 * Persistence description for {@link Category}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolCategoryDAO {
	/**
	 * 
	 * @return {@link Category} List
	 */
	 
	public List<Category> getCategoryList();
	
	

}
