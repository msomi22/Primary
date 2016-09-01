/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student.subject;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.student.Subject;

/**
 * Test Unit for {@link Subject} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestSubjectDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String SUBJECT_UUID = "F294FD7C-6321-417C-A56F-CFE8AC8D7950";
	final String SUBJECT_CODE = "ENG";
	final String SUBJECT_NAME = "English";
	
	private SubjectDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO#getSubjectByUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetSubjectByUuid() {
		store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Subject sub = new Subject();
		sub = store.getSubjectByUuid(SUBJECT_UUID);
		assertEquals(sub.getUuid(),SUBJECT_UUID);
		assertEquals(sub.getSubjectCode(),SUBJECT_CODE);
		assertEquals(sub.getSubjectName(),SUBJECT_NAME);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO#getSubject(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetSubject() {
		store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Subject sub = new Subject();
		sub = store.getSubject(SUBJECT_CODE);
		assertEquals(sub.getUuid(),SUBJECT_UUID);
		assertEquals(sub.getSubjectCode(),SUBJECT_CODE);
		assertEquals(sub.getSubjectName(),SUBJECT_NAME);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO#getSubjectList()}.
	 */
	//@Ignore
	@Test
	public final void testGetSubjectList() {
		store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Subject> sublist = store.getSubjectList();
		for(Subject sub : sublist){
			System.out.println(sub);
		}
	}

}
