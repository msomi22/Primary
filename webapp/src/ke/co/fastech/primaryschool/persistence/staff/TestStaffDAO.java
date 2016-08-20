/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.Staff;

/**
 * @author peter
 *
 */
public class TestStaffDAO {

	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;

	private StaffDAO store;
	
	private String UUID = "83CD26E1-3E64-4969-9B98-9069ECDB757D", UUID_NEW = "0CE28A71-0B5F-4144-BA09-73EB1C101AFE";
	private String ACCOUNT_UUID = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4";
	private String STATUS_UUID  = "B2733AB2-9CDB-4361-89C5-86B175A2E828";
	private String EMPLOYEE_NUMBER = "100",EMPLOYEE_NUMBER_NEW ="105";
	private String NAME  = "Peter Mwenda",NAME_NEW ="Kairu Newton";
	private String PHONE  = "718953974";
	private String EMAIL = "petermwenda83@yahoo.com",EMAIL_NEW = "kairu@gmail.com";
	private String GENDER = "M";
	private String DOB = "1990";
	private String COUNTRY = "Kenya";
	private String COUNTY = "Tharaka Nithi";
	private String WARD = "Muiru";
	private Date REGDATE = new Date(new Long(12334456)); 

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.StaffDAO#getStaff(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStaffString() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff staff = new Staff();
		staff = store.getStaff(UUID);
		assertEquals(staff.getUuid(),UUID);
		assertEquals(staff.getAccountUuid(),ACCOUNT_UUID);
		assertEquals(staff.getStatusUuid(),STATUS_UUID);
		assertEquals(staff.getEmployeeNo(),EMPLOYEE_NUMBER);
		assertEquals(staff.getName(),NAME);
		assertEquals(staff.getPhone(),PHONE);
		assertEquals(staff.getEmail(),EMAIL);
		assertEquals(staff.getGender(),GENDER);
		assertEquals(staff.getDob(),DOB);
		assertEquals(staff.getCountry(),COUNTRY);
		assertEquals(staff.getCounty(),COUNTY);
		assertEquals(staff.getWard(),WARD);
		//assertEquals(staff.getRegDate(),REGDATE); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.StaffDAO#getStaff(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStaffStringString() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff staff = new Staff();
		staff = store.getStaff(UUID, EMPLOYEE_NUMBER); 
		assertEquals(staff.getUuid(),UUID);
		assertEquals(staff.getAccountUuid(),ACCOUNT_UUID);
		assertEquals(staff.getStatusUuid(),STATUS_UUID);
		assertEquals(staff.getEmployeeNo(),EMPLOYEE_NUMBER);
		assertEquals(staff.getName(),NAME);
		assertEquals(staff.getPhone(),PHONE);
		assertEquals(staff.getEmail(),EMAIL);
		assertEquals(staff.getGender(),GENDER);
		assertEquals(staff.getDob(),DOB);
		assertEquals(staff.getCountry(),COUNTRY);
		assertEquals(staff.getCounty(),COUNTY);
		assertEquals(staff.getWard(),WARD);
		//assertEquals(staff.getRegDate(),REGDATE); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.StaffDAO#putStaff(ke.co.fastech.primaryschool.bean.staff.Staff)}.
	 */
	@Ignore
	@Test
	public final void testPutStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff staff = new Staff();
		staff = store.getStaff(UUID, EMPLOYEE_NUMBER); 
		staff.setUuid(UUID_NEW);
		staff.setAccountUuid(ACCOUNT_UUID);
		staff.setStatusUuid(STATUS_UUID);
		staff.setEmployeeNo(EMPLOYEE_NUMBER_NEW);
		staff.setName(NAME_NEW);
		staff.setEmail(EMAIL_NEW); 
		staff.setPhone(PHONE);
		staff.setGender(GENDER);
		staff.setDob(DOB);
		staff.setCountry(COUNTRY);
		staff.setCounty(COUNTY);
		staff.setWard(WARD);
		staff.setRegDate(REGDATE);
		assertTrue(store.putStaff(staff));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.StaffDAO#updateStaff(ke.co.fastech.primaryschool.bean.staff.Staff)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff staff = new Staff();
		staff = store.getStaff(UUID, EMPLOYEE_NUMBER); 
		staff.setUuid(UUID_NEW);
		staff.setAccountUuid(ACCOUNT_UUID);
		staff.setStatusUuid(STATUS_UUID);
		staff.setEmployeeNo(EMPLOYEE_NUMBER_NEW);
		staff.setName("Obed Muthomi");
		staff.setEmail(EMAIL_NEW); 
		staff.setPhone(PHONE);
		staff.setGender(GENDER);
		staff.setDob(DOB);
		staff.setCountry(COUNTRY);
		staff.setCounty(COUNTY);
		staff.setWard(WARD);
		staff.setRegDate(REGDATE);
		assertTrue(store.updateStaff(staff)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.StaffDAO#getStaffList()}.
	 */
	@Ignore
	@Test
	public final void testGetStaffList() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 List<Staff> list = store.getStaffList();
		 for(Staff sff : list){
			 System.out.println(sff);
		 } 
	}

}
