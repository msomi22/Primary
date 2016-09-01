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

import ke.co.fastech.primaryschool.bean.exam.MeanScore;

/**
 * Unit test for {@link MeanScore} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestMeanScoreDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21",STUDENT_UUID_NEW = "82B17C63-6BBA-4B5C-B387-43DD1E74B2B1";
	final String TERM = "1",TERM_NEW = "2";
	final String YEAR = "2016";
	final double MEAN_SCORE = 467;
	final int STREAM_POSITION = 3;
	final int CLASS_POSITION = 7;
	
	private MeanScoreDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO#getMeanScore(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetMeanScore() {
		store = new MeanScoreDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		MeanScore meanscore = new MeanScore();
		meanscore = store.getMeanScore(STUDENT_UUID, TERM, YEAR);
		assertEquals(meanscore.getStudentUuid(),STUDENT_UUID);
		assertEquals(meanscore.getTerm(),TERM);
		assertEquals(meanscore.getYear(),YEAR);
		assertEquals(meanscore.getMeanScore(),MEAN_SCORE,0);
		assertEquals(meanscore.getStreamPosition(),STREAM_POSITION);
		assertEquals(meanscore.getClassPosition(),CLASS_POSITION); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO#meanExist(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testMeanExist() {
		store = new MeanScoreDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.meanExist(STUDENT_UUID, TERM, YEAR)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.MeanScoreDAO#putMeanScore(double, int, int, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public final void testPutMeanScore() {
		store = new MeanScoreDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		//assertTrue(store.putMeanScore(MEAN_SCORE, STREAM_POSITION, CLASS_POSITION, STUDENT_UUID_NEW, TERM, YEAR)); 
	}

}
