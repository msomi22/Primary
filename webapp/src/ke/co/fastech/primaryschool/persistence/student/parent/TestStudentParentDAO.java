/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.parent;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.student.StudentParent;

/**
 * Test Unit for {@link StudentParent} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentParentDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21",STUDENT_UUID_NEW = "7DBC3E02-DB92-4A34-A506-D2ED184F02A9";
	private final String PARENT_NAME  = "Peter Mwenda",PARENT_NAME_NEW = "Obed Muthomi";
	private final String PARENT_PHONE  = "718953974", PARENT_PHONE2 = "721585118",PARENT_PHONE_NEW = "718953974,721585118";
	private final String PARENT_EMAIL  = "mwendapeter72@gmail.com ", PARENT_EMAIL_NEW = "obedmuthomi66@yahoo.com";
	
	private StudentParentDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#getStudentParent(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentParent() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent p = new StudentParent();
		p = store.getStudentParent(STUDENT_UUID);
		String [] phone = p.getParentPhone().split(","); 
		assertEquals(phone[0],PARENT_PHONE);
		assertEquals(phone[1],PARENT_PHONE2);
		assertEquals(p.getStudentUuid(),STUDENT_UUID);
		assertEquals(p.getParentName(),PARENT_NAME);
		assertEquals(p.getParentEmail(),PARENT_EMAIL);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#getStudentParentByPhone(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentParentByPhone() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent p = new StudentParent();
		String [] phone = p.getParentPhone().split(","); 
		p = store.getStudentParentByPhone(PARENT_PHONE_NEW); 
		assertEquals(phone[0],PARENT_PHONE);
		assertEquals(phone[1],PARENT_PHONE2);
		assertEquals(p.getStudentUuid(),STUDENT_UUID);
		assertEquals(p.getParentName(),PARENT_NAME);
		assertEquals(p.getParentEmail(),PARENT_EMAIL);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#getStudentParentByEmail(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentParentByEmail() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent p = new StudentParent();
		p = store.getStudentParentByEmail(PARENT_EMAIL);
		String [] phone = p.getParentPhone().split(","); 
		assertEquals(phone[0],PARENT_PHONE);
		assertEquals(phone[1],PARENT_PHONE2);
		assertEquals(p.getStudentUuid(),STUDENT_UUID);
		assertEquals(p.getParentName(),PARENT_NAME);
		assertEquals(p.getParentEmail(),PARENT_EMAIL);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#putStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentParent() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent p = new StudentParent();
		p.setStudentUuid(STUDENT_UUID_NEW);
		p.setParentName(PARENT_NAME_NEW);
		p.setParentEmail(PARENT_EMAIL_NEW);
		p.setParentPhone(PARENT_PHONE);
		assertTrue(store.putStudentParent(p)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#updateStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudentParent() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent p = new StudentParent();
		p.setStudentUuid(STUDENT_UUID_NEW);
		p.setParentName(PARENT_NAME_NEW);
		p.setParentEmail(PARENT_EMAIL_NEW);
		p.setParentPhone(PARENT_PHONE2);
		assertTrue(store.updateStudentParent(p));  
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#deleteStudentParent(ke.co.fastech.primaryschool.bean.student.StudentParent)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentParent() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteStudentParent(STUDENT_UUID_NEW)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.parent.StudentParentDAO#getStudentParentList()}.
	 */
	@Ignore
	@Test
	public final void testGetStudentParentList() {
		store = new StudentParentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentParent> list = store.getStudentParentList();
		for(StudentParent p : list){
			System.out.println(p);
		}
	}

}
