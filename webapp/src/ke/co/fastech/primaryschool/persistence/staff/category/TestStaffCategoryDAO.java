/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.StaffCategory;

/**
 * @author peter
 *
 */
public class TestStaffCategoryDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StaffCategoryDAO store;
	
	 private String UUID = "2DAF4B9F-B603-4EAD-A068-3FDB3CCEC009",UUID_NEW ="0DD70045-183C-44E5-98C0-8052C54ACFC9";
	 private String STAFF_UUID = "83CD26E1-3E64-4969-9B98-9069ECDB757D";
	 private String CATEGORY_UUID = "5136671D-4217-4084-BA80-7D0D2155A43F", CATEGORY_UUID_NEW = "0A3ABF26-CA6A-45E8-97B0-DF6426C52140";

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO#getStaffCategory(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStaffCategoryString() {
		store = new StaffCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffCategory staffCategory = new StaffCategory();
		staffCategory = store.getStaffCategory(STAFF_UUID);
		assertEquals(staffCategory.getUuid(),UUID);
		assertEquals(staffCategory.getStaffUuid(),STAFF_UUID);
		assertEquals(staffCategory.getCategoryUuid(),CATEGORY_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO#getStaffCategory(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStaffCategoryStringString() {
		store = new StaffCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffCategory staffCategory = new StaffCategory();
		staffCategory = store.getStaffCategory(STAFF_UUID, CATEGORY_UUID);
		assertEquals(staffCategory.getUuid(),UUID);
		assertEquals(staffCategory.getStaffUuid(),STAFF_UUID);
		assertEquals(staffCategory.getCategoryUuid(),CATEGORY_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO#putStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)}.
	 */
	@Ignore
	@Test
	public final void testPutStaffCategory() {
		store = new StaffCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffCategory staffCategory = new StaffCategory();
		staffCategory.setUuid(UUID_NEW);
		staffCategory.setStaffUuid(STAFF_UUID);
		staffCategory.setCategoryUuid(CATEGORY_UUID_NEW); 
		assertTrue(store.putStaffCategory(staffCategory)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO#updateStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStaffCategory() {
		store = new StaffCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffCategory staffCategory = new StaffCategory();
		staffCategory.setUuid(UUID_NEW);
		staffCategory.setStaffUuid(STAFF_UUID);
		staffCategory.setCategoryUuid(CATEGORY_UUID); 
		assertTrue(store.updateStaffCategory(staffCategory));  
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO#deleteStaffCategory(ke.co.fastech.primaryschool.bean.staff.StaffCategory)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStaffCategory() {
		store = new StaffCategoryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteStaffCategory(STAFF_UUID));
	}

}
