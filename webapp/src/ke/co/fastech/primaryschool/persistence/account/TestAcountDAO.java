/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.account;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.school.account.Account;

/**
 * Test Unit for {@link Account} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestAcountDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private final String UUID = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4",
			             UUID_NEW= "395135BB-60EC-4DC8-BE65-7537FC9E2036";  
	

	
	private final String SCHOOL_NAME = "Kathituni Boarding Primary School",
			             SCHOOL_NAME_NEW = "NEW SCHOOL ACOUNT",
			             SCHOOL_NAME_UPDATE = "UPDATED SCHOOL ACCOUNT";
	
	private final String SCHOOL_EMAIL = "kathitunib@gmail.com",
			             SCHOOL_EMAIL_NEW = "NEW@GMAIL.COM",
			             SCHOOL_EMAIL_UPDATE = "UPDATE@GMAIL.COM";
	
	private final String SCHOOL_ADDRESS = "356-60400",
			             SCHOOL_ADDRESS_NEW = "NEW1234",
			             SCHOOL_ADDRESS_UPDATE = "UPDATED1234";
	
	private final String SCHOOL_PHONE = "718953974",
			             SCHOOL_PHONE_NEW = "009999999",
			             SCHOOL_PHONE_UPDATE = "00888888";
	
	private final String SCHOOL_MOTTO = "Hard Work Pays",
                		 SCHOOL_MOTTO_NEW = "NEW MOTTO",
                		 SCHOOL_MOTTO_UPDATE = "UPDATED MOTTO";
	
	private final String SCHOOL_HOME_TOWN = "Chuka",
			             SCHOOL_HOME_TOWN_NEW = "NEW",
			             SCHOOL_HOME_TOWN_UPDATE = "UPDATED";
	
	private final String SCHOOL_COUNTY = "Tharaka Nithi",
			             SCHOOL_COUNTY_NEW = "NEW",
			             SCHOOL_COUNTY_UPDATE = "UPDATED";
	
	private AcountDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.AcountDAO#getAccount(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetAccount() {
		store = new AcountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account account = new Account();
		account = store.getAccount(UUID);
		assertEquals(account.getUuid(),UUID);
		assertEquals(account.getSchoolName(),SCHOOL_NAME);
		assertEquals(account.getSchoolEmail(),SCHOOL_EMAIL);
		assertEquals(account.getSchoolAddres(),SCHOOL_ADDRESS);
		assertEquals(account.getSchoolPhone(),SCHOOL_PHONE);
		assertEquals(account.getSchoolMotto(),SCHOOL_MOTTO);
		assertEquals(account.getSchoolHomeTown(),SCHOOL_HOME_TOWN);
		assertEquals(account.getSchoolCounty(),SCHOOL_COUNTY);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.AcountDAO#put(ke.co.fastech.primaryschool.bean.school.account.Account)}.
	 */
	@Ignore
	@Test
	public final void testPut() {
		store = new AcountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account account = new Account();
		account.setUuid(UUID_NEW);
		account.setSchoolName(SCHOOL_NAME_NEW);
		account.setSchoolEmail(SCHOOL_EMAIL_NEW);
		account.setSchoolAddres(SCHOOL_ADDRESS_NEW);
		account.setSchoolPhone(SCHOOL_PHONE_NEW);
		account.setSchoolMotto(SCHOOL_MOTTO_NEW);
		account.setSchoolHomeTown(SCHOOL_HOME_TOWN_NEW);
		account.setSchoolCounty(SCHOOL_COUNTY_NEW); 
		assertTrue(store.put(account));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.account.AcountDAO#update(ke.co.fastech.primaryschool.bean.school.account.Account)}.
	 */
	@Ignore
	@Test
	public final void testUpdate() {
		store = new AcountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account account = new Account();
		account.setUuid(UUID_NEW);
		account.setSchoolName(SCHOOL_NAME_UPDATE);
		account.setSchoolEmail(SCHOOL_EMAIL_UPDATE);
		account.setSchoolAddres(SCHOOL_ADDRESS_UPDATE);
		account.setSchoolPhone(SCHOOL_PHONE_UPDATE);
		account.setSchoolMotto(SCHOOL_MOTTO_UPDATE);
		account.setSchoolHomeTown(SCHOOL_HOME_TOWN_UPDATE);
		account.setSchoolCounty(SCHOOL_COUNTY_UPDATE); 
		assertTrue(store.update(account));
	}
	
	//@Ignore
	@Test
	public final void testgetAllAccounts() {
		store = new AcountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Account> list = store.getAllAccounts();
		for(Account acc : list){
			System.out.println(acc);
		}
	}

}
