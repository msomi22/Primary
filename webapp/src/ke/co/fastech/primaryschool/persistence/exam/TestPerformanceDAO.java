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

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.exam.ExamResult;

/**
 * Unit test for {@link ExamResult} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestPerformanceDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String STUDENT_UUID = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1";
	final String SUBJECT_UUID = "F294FD7C-6321-417C-A56F-CFE8AC8D7950";
	final String STREAM_UUID = "2B0F8F79-DEF2-419D-9A76-17450B5CF768";
	final String CLASS_UUID = "6537D3DF-313D-4F8D-AB7D-D2216B6407D0";
	final String TERM = "1";
	final String YEAR = "2016";
	
	private PerformanceDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getStudentPerformanceByStreamId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentPerformanceByStreamId() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getStudentPerformanceByStreamId(STUDENT_UUID, SUBJECT_UUID, STREAM_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getStudentPerformanceByClassId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentPerformanceByClassId() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getStudentPerformanceByClassId(STUDENT_UUID, SUBJECT_UUID, CLASS_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getStudentDistinctByStreamId(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentDistinctByStreamId() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getStudentDistinctByStreamId(STREAM_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getStudentDistinctByClassId(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentDistinctByClassId() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getStudentDistinctByClassId(CLASS_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getStreamPerformance(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStreamPerformance() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getStreamPerformance(STREAM_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.PerformanceDAO#getClassPerformance(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetClassPerformance() {
		store = new PerformanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ExamResult> resultList = store.getClassPerformance(CLASS_UUID, TERM, YEAR);
		for(ExamResult result : resultList){
			System.out.println(result);
		}
	}

}
