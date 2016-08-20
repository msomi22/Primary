/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.Category;

/**
 * @author peter
 *
 */
public class TestCategoryDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private CategoryDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.CategoryDAO#getCategoryList()}.
	 */
	@Ignore
	@Test
	public final void testGetCategoryList() {
		store = new CategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Category> list = store.getCategoryList();
		for(Category c : list){
			System.out.println(c);
		}
	}

}
