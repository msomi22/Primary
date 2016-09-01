/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.money;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.money.OtherMoney;

/**
 * Unit test for {@link OtherMoney} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestOtherMoneyDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private final String ACCOUNT_UUID = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4";
	
	private final String UUID = "CAA3E91A-E324-4A18-AEE1-88BF14DDB606", UUID_NEW ="HKJ3E96O-E324-4A18-ADD1-66BF14DDB404";
	private final String DESCRIPTION = "Kneck Exam",DESCRIPTION_NEW ="New Money";
	private final String TERM = "1";
	private final String YEAR = "2016";
	private final int AMOUNT = 2500,AMOUNT_NEW = 3400;
	
	
	private OtherMoneyDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#getOtherMoney(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetOtherMoney() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		OtherMoney otherMoney = new OtherMoney();
		otherMoney = store.getOtherMoney(DESCRIPTION, TERM, YEAR,ACCOUNT_UUID);
		assertEquals(otherMoney.getUuid(),UUID);
		assertEquals(otherMoney.getDescription(),DESCRIPTION); 
		assertEquals(otherMoney.getTerm(),TERM);
		assertEquals(otherMoney.getYear(),YEAR);
		assertEquals(otherMoney.getAmount(),AMOUNT);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#putOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)}.
	 */
	@Ignore
	@Test
	public final void testPutOtherMoney() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		OtherMoney otherMoney = new OtherMoney();
		otherMoney.setUuid(UUID_NEW);
		otherMoney.setDescription(DESCRIPTION_NEW);
		otherMoney.setTerm(TERM);
		otherMoney.setYear(YEAR);
		otherMoney.setAmount(AMOUNT_NEW);
		assertTrue(store.putOtherMoney(otherMoney));
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#updateOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)}.
	 */
	@Ignore
	@Test
	public final void testUpdateOtherMoney() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		OtherMoney otherMoney = new OtherMoney();
		otherMoney.setUuid(UUID_NEW);
		otherMoney.setDescription("UPDATE");
		otherMoney.setTerm(TERM);
		otherMoney.setYear(YEAR);
		otherMoney.setAmount(55555); 
		assertTrue(store.updateOtherMoney(otherMoney));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#deleteOtherMoney(ke.co.fastech.primaryschool.bean.money.OtherMoney)}.
	 */
	@Ignore
	@Test
	public final void testDeleteOtherMoney() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#getOtherMoneyList(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetOtherMoneyList() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<OtherMoney> omoneyList = store.getOtherMoneyList(TERM, YEAR,ACCOUNT_UUID);
		for(OtherMoney om : omoneyList){
			System.out.println(om); 
		}
	}
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.OtherMoneyDAO#getOtherMoneyList()}.
	 */
	@Ignore
	@Test
	public final void testGetOtherMone() {
		store = new OtherMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<OtherMoney> omoneyList = store.getOtherMoneyList(ACCOUNT_UUID);
		for(OtherMoney om : omoneyList){
			System.out.println(om); 
		}
	}

}
