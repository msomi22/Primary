/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.exam;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.exam.BarWeight;

/**
 * Unit test for {@link BarWeight} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestBarWeightDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21",STUDENT_UUID_NEW = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1";
	final String TERM = "2";
	final String YEAR = "2016";
	final double WEIGHT = 7.35;
	
	private BarWeightDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.BarWeightDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBarWeight() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		BarWeight barWeight = store.getBarWeight(STUDENT_UUID, TERM, YEAR);
		assertEquals(barWeight.getStudentUuid(),STUDENT_UUID);
		assertEquals(barWeight.getTerm(),TERM);
		assertEquals(barWeight.getYear(),YEAR);
		assertEquals(barWeight.getWeight(),WEIGHT,0);
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.BarWeightDAO#existWeight(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testExistWeight() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.existWeight(STUDENT_UUID, TERM, YEAR));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.BarWeightDAO#putWeight(double, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testPutWeight() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.putWeight(WEIGHT, STUDENT_UUID_NEW, TERM, YEAR));
	}

}
