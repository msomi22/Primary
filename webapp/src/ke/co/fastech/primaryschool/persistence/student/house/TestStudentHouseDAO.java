/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.house;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.student.StudentHouse;

/**
 * Test Unit for {@link StudentHouse} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentHouseDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String UUID = "4BB97AF1-D6BB-450F-8E00-FCE2C60BE09A", UUID_NEW = "F2B42853-02A4-416C-8BE8-F6D9EA9CFCF6";
	final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21", STUDENT_UUID_NEW = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1";
	final String HOUSE_UUID = "C3E4B333-7691-44B2-BD73-C5A217E806A9",
			     HOUSE_UUID_NEW = "81CDA101-4014-4105-92D8-158EB94CD008",
			     HOUSE_UUID_UPDATE ="2CE3B480-8B94-40B3-B68B-C2C48158F62E";
	
	private StudentHouseDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#getStudentHouse(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentHouseString() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h = store.getStudentHouse(STUDENT_UUID);
		assertEquals(h.getStudentUuid(),STUDENT_UUID);
		assertEquals(h.getHouseUuid(),HOUSE_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#getStudentHouse(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentHouseStringString() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h = store.getStudentHouse(STUDENT_UUID,HOUSE_UUID);
		assertEquals(h.getStudentUuid(),STUDENT_UUID);
		assertEquals(h.getHouseUuid(),HOUSE_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#putStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h.setUuid(UUID_NEW); 
		h.setStudentUuid(STUDENT_UUID_NEW);
		h.setHouseUuid(HOUSE_UUID_NEW);
		assertTrue(store.putStudentHouse(h));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#updateStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudentHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.updateStudentHouse(STUDENT_UUID_NEW, HOUSE_UUID_UPDATE)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#deleteStudentHouse(ke.co.fastech.primaryschool.bean.student.StudentHouse)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteStudentHouse(STUDENT_UUID_NEW));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.house.StudentHouseDAO#getStudentHouseList()}.
	 */
	@Ignore
	@Test
	public final void testGetStudentHouseList() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentHouse> list = store.getStudentHouseList();
		for(StudentHouse h : list){
			System.out.println(h); 
		}
	}

}
