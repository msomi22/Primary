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

import ke.co.fastech.primaryschool.bean.exam.EndTermExam;
import ke.co.fastech.primaryschool.bean.exam.MidTermExam;
import ke.co.fastech.primaryschool.bean.exam.OpenerExam;
import ke.co.fastech.primaryschool.bean.exam.Performance;

/**
 * Unit test for {@link Performance} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestExamEngineDAO {
	

	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String STUDENT_UUID = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1",STUDENT_UUID_NEW = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21"; 
	final String SUBJECT_UUID = "F294FD7C-6321-417C-A56F-CFE8AC8D7950",SUBJECT_UUID_NEW = "F294FD7C-6321-417C-A56F-CFE8AC8D7950";
	final String STREAM_UUID = "2B0F8F79-DEF2-419D-9A76-17450B5CF768";//2B0F8F79-DEF2-419D-9A76-17450B5CF768
	final String CLASS_UUID = "6537D3DF-313D-4F8D-AB7D-D2216B6407D0"; //6537D3DF-313D-4F8D-AB7D-D2216B6407D0
	final String TERM = "1";//1
	final String YEAR = "2016";//2016
	
	final double SCORE = 91,SCORE1 = 92,SCORE2 =93;
	
	private ExamEngineDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO#recordExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testRecordExist() {
		store = new ExamEngineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.recordExist(STUDENT_UUID_NEW, SUBJECT_UUID_NEW, STREAM_UUID, CLASS_UUID, TERM, YEAR));

	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO#putExam(ke.co.fastech.primaryschool.bean.exam.Performance, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testPutExam() {
		store = new ExamEngineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		// Opener Exam
		OpenerExam openner = new OpenerExam();
		openner.setScore(SCORE); 
		assertTrue(store.putExam(openner, STUDENT_UUID, SUBJECT_UUID, STREAM_UUID, CLASS_UUID, TERM, YEAR));
		
		// Mid Term Exam
		MidTermExam midterm = new MidTermExam();
		midterm.setScore(SCORE1);
		assertTrue(store.putExam(midterm, STUDENT_UUID, SUBJECT_UUID, STREAM_UUID, CLASS_UUID, TERM, YEAR));
		
		// End Term Exam
		EndTermExam endterm = new EndTermExam();
		endterm.setScore(SCORE2);
		assertTrue(store.putExam(endterm, STUDENT_UUID, SUBJECT_UUID, STREAM_UUID, CLASS_UUID, TERM, YEAR));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.ExamEngineDAO#deletePerformance(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testDeletePerformance() {
		store = new ExamEngineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deletePerformance(STUDENT_UUID, TERM, YEAR)); 
	}

}
