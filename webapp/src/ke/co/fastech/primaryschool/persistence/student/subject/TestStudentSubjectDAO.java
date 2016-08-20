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

import ke.co.fastech.primaryschool.bean.student.StudentSubject;

/**
 * Test Unit for {@link StudentSubject} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentSubjectDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String UUID = "803AA359-0337-48BB-BDA8-6B81B8EBF1C0",UUID_NEW ="34AD3056-633D-4B17-821D-E4F9CE1F29C4";
	final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21";
	final String SUBJECT_UUID = "F294FD7C-6321-417C-A56F-CFE8AC8D7950";
	
	private StudentSubjectDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO#getStudentSubject(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentSubject() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSubject ssub = new StudentSubject();
		ssub = store.getStudentSubject(STUDENT_UUID,SUBJECT_UUID);
		assertEquals(ssub.getUuid(),UUID);
		assertEquals(ssub.getStudentUuid(),STUDENT_UUID);
		assertEquals(ssub.getSubjectUuid(),SUBJECT_UUID);
	}

	

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO#getStudentSubjectList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentSubjectList() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentSubject>  subjectlist = store.getStudentSubjectList(STUDENT_UUID);
		for(StudentSubject ss : subjectlist){
			System.out.println(ss);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO#putStudentSubject(ke.co.fastech.primaryschool.bean.student.StudentSubject)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentSubject() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSubject ssub = new StudentSubject();
		ssub.setUuid(UUID_NEW);
		ssub.setStudentUuid(STUDENT_UUID);
		ssub.setSubjectUuid(SUBJECT_UUID); 
		assertTrue(store.putStudentSubject(ssub));
	}


	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO#deleteStudentSubject(ke.co.fastech.primaryschool.bean.student.StudentSubject)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentSubject() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteStudentSubject(STUDENT_UUID, SUBJECT_UUID));
	}

}
