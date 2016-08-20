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

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.exam.Exam;

/**
 * Unit test for {@link Exam} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestExamDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String UUID = "DB31AB32-A6E2-45EB-98D3-625FC7D91B47";
	final String EXAM_NAME = "OPPENER";
	final int OUT_OF = 100;
	
	private ExamDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamDAO#getExam(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam = store.getExam(UUID);
		assertEquals(exam.getUuid(),UUID);
		assertEquals(exam.getExamName(),EXAM_NAME);
		assertEquals(exam.getOutOf(),OUT_OF);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamDAO#getExamByExamname(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetExamByExamname() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam = store.getExamByExamname(EXAM_NAME);
		assertEquals(exam.getUuid(),UUID);
		assertEquals(exam.getExamName(),EXAM_NAME);
		assertEquals(exam.getOutOf(),OUT_OF);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamDAO#putExam(ke.co.fastech.primaryschool.bean.exam.Exam)}.
	 */
	@Ignore
	@Test
	public final void testPutExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam.setUuid(UUID); 
		exam.setExamName(EXAM_NAME);
		exam.setOutOf(OUT_OF);
		assertTrue(store.putExam(exam));   
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamDAO#updateExam(ke.co.fastech.primaryschool.bean.exam.Exam)}.
	 */
	@Ignore
	@Test
	public final void testUpdateExam() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Exam exam = new Exam();
		exam.setOutOf(OUT_OF);
		exam.setUuid(UUID);
		exam.setExamName(EXAM_NAME);
		assertTrue(store.updateExam(exam));    
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamDAO#getExamList()}.
	 */
	@Ignore
	@Test
	public final void testGetExamList() {
		store = new ExamDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Exam> list = store.getExamList();
		for (Exam cl : list) {
			System.out.println(cl); 
		}
	}

}
