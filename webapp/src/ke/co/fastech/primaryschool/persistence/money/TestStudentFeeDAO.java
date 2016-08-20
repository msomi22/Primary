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

import ke.co.fastech.primaryschool.bean.money.StudentFee;

/**
 * Unit test for {@link StudentFee} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentFeeDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private final String UUID = "7902EEDC-D5EC-44CB-ABED-516ED61F6A2A",
			             UUID_NEW = "F205A34B-C6E1-40AD-8A76-D94606FEA339";
	private final String STUDENT_UUID  = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	private final String TERM  = "1";
	private final String YEAR  = "2016";
	private final String STUDENT_TYPE  = "Day";
	private final int AMOUNT_PAID  = 3000;
	private final String TRANSACTION_ID  = "80B1-AC47B63A8C21";
	//private final Date DATE_PAID = new Date();
	
	private StudentFeeDAO  store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#getStudentFee(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFee() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sfee = new StudentFee();
		sfee = store.getStudentFee(STUDENT_UUID, TERM, YEAR);
		assertEquals(sfee.getUuid(),UUID);
		assertEquals(sfee.getStudentUuid(),STUDENT_UUID); 
		assertEquals(sfee.getTerm(),TERM);
		assertEquals(sfee.getYear(),YEAR);
		assertEquals(sfee.getStudentType(),STUDENT_TYPE);
		assertEquals(sfee.getAmountPaid(),AMOUNT_PAID);
		assertEquals(sfee.getTransactionID(),TRANSACTION_ID);
	}
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#getStudentFee(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeString() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sfee = new StudentFee();
		sfee = store.getStudentFee(STUDENT_UUID, TERM, YEAR,TRANSACTION_ID);
		assertEquals(sfee.getUuid(),UUID);
		assertEquals(sfee.getStudentUuid(),STUDENT_UUID); 
		assertEquals(sfee.getTerm(),TERM);
		assertEquals(sfee.getYear(),YEAR);
		assertEquals(sfee.getStudentType(),STUDENT_TYPE);
		assertEquals(sfee.getAmountPaid(),AMOUNT_PAID);
		assertEquals(sfee.getTransactionID(),TRANSACTION_ID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#getStudentFeeList(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeList() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentFee> studentFeeList = store.getStudentFeeList(STUDENT_UUID, TERM, YEAR);
		for(StudentFee sfee : studentFeeList){
			System.out.println(sfee);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#putStudentFee(ke.co.fastech.primaryschool.bean.money.StudentFee)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentFee() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sfee = new StudentFee();
		sfee.setUuid(UUID_NEW); 
		sfee.setStudentUuid(STUDENT_UUID);
		sfee.setStudentType(STUDENT_TYPE);
		sfee.setTransactionID(TRANSACTION_ID); 
		sfee.setTerm(TERM);
		sfee.setYear(YEAR);
		sfee.setAmountPaid(AMOUNT_PAID); 
		assertTrue(store.putStudentFee(sfee)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#updateStudentFee(ke.co.fastech.primaryschool.bean.money.StudentFee)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudentFee() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sfee = new StudentFee();
		sfee.setUuid(UUID_NEW); 
		sfee.setStudentUuid(STUDENT_UUID);
		sfee.setStudentType(STUDENT_TYPE);
		sfee.setTransactionID(TRANSACTION_ID); 
		sfee.setTerm(TERM);
		sfee.setYear(YEAR);
		sfee.setAmountPaid(12000); 
		assertTrue(store.updateStudentFee(sfee));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.money.StudentFeeDAO#getAllStudentFeeByTermYear(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetAllStudentFeeByTermYear() {
		store = new StudentFeeDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentFee> studentFeeList = store.getAllStudentFeeByTermYear(TERM, YEAR);
		for(StudentFee sfee : studentFeeList){
			System.out.println(sfee);
		}
	}

}
