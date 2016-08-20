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

import ke.co.fastech.primaryschool.bean.exam.GradingSystem;

/**
 * Unit test for {@link GradingSystem} DAO
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestGradingSystemDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	

	   final String UUID = "1EABC062-DC76-42FC-A817-52D89C8CDAE9",
			       UUID_NEW = "2F0BD8E1-32F9-4A75-A77B-C3D991A09DC4";
	
	   final int GRADE_A = 83,
			     GRADE_A_NEW = 2,
			     GRADE_A_UPDATE = 3;
	
	   final int GRADE_A_MINUS = 71,
			     GRADE_A_MINUS_NEW = 2,
			     GRADE_A_MINUS_UPDATE = 3;
	
	   final int GRADE_B_PLUS = 67,
			     GRADE_B_PLUS_NEW = 2,
			     GRADE_B_PLUS_UPDATE = 3;
	
	   final int GRADE_B_PLAIN = 61,
			     GRADE_B_PLAIN_NEW = 2,
			     GRADE_B_PLAIN_UPDATE = 3;
	
	   final int GRADE_B_MINUS = 55,
			     GRADE_B_MINUS_NEW = 2,
			     GRADE_B_MINUS_UPDATE = 3;
	
	   final int GRADE_C_PLUS = 47,
			     GRADE_C_PLUS_NEW = 2,
			     GRADE_C_PLUS_UPDATE = 3;
	
	   final int GRADE_C_PLAIN = 42,
			     GRADE_C_PLAIN_NEW = 2,
			     GRADE_C_PLAIN_UPDATE = 3;
	
	   final int GRADE_C_MINUS = 38,
			     GRADE_C_MINUS_NEW = 2,
			     GRADE_C_MINUS_UPDATE = 3;
	
	   final int GRADE_D_PLUS = 35,
			     GRADE_D_PLUS_NEW = 2,
			     GRADE_D_PLUS_UPDATE = 3;
	
	   final int GRADE_D_PLAIN = 30,
			     GRADE_D_PLAIN_NEW = 2,
			     GRADE_D_PLAIN_UPDATE = 3;
	
	   final int GRADE_D_MINUS = 25,
			     GRADE_D_MINUS_NEW = 2,
			     GRADE_D_MINUS_UPDATE = 3;
	
	   final int GRADE_E = 0,
			     GRADE_E_NEW = 2,
			     GRADE_E_UPDATE = 3;
	
	private GradingSystemDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO#getGradingSystem(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem gradingSystem = new GradingSystem();
		gradingSystem = store.getGradingSystem(UUID);
		assertEquals(gradingSystem.getGradeAplain(),GRADE_A);
		assertEquals(gradingSystem.getGradeAminus(),GRADE_A_MINUS);
		assertEquals(gradingSystem.getGradeBplus(),GRADE_B_PLUS);
		assertEquals(gradingSystem.getGradeBplain(),GRADE_B_PLAIN);
		assertEquals(gradingSystem.getGradeBminus(),GRADE_B_MINUS);
		assertEquals(gradingSystem.getGradeCplus(),GRADE_C_PLUS);
		assertEquals(gradingSystem.getGradeCplain(),GRADE_C_PLAIN);
		assertEquals(gradingSystem.getGradeCminus(),GRADE_C_MINUS);
		assertEquals(gradingSystem.getGradeDplus(),GRADE_D_PLUS);
		assertEquals(gradingSystem.getGradeDplain(),GRADE_D_PLAIN);
		assertEquals(gradingSystem.getGradeDminus(),GRADE_D_MINUS);
		assertEquals(gradingSystem.getGradeE(),GRADE_E);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO#putGradingSystem(ke.co.fastech.primaryschool.bean.exam.GradingSystem)}.
	 */
	@Ignore
	@Test
	public final void testPutGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem g = new GradingSystem();
		g.setUuid(UUID_NEW);
		g.setGradeAplain(GRADE_A_NEW);
		g.setGradeAminus(GRADE_A_MINUS_NEW);
		g.setGradeBplus(GRADE_B_PLUS_NEW);
		g.setGradeBplain(GRADE_B_PLAIN_NEW);
		g.setGradeBminus(GRADE_B_MINUS_NEW);
		g.setGradeCplus(GRADE_C_PLUS_NEW);
		g.setGradeCplain(GRADE_C_PLAIN_NEW);
		g.setGradeCminus(GRADE_C_MINUS_NEW);
		g.setGradeDplus(GRADE_D_PLUS_NEW);
		g.setGradeDplain(GRADE_D_PLAIN_NEW);
		g.setGradeDminus(GRADE_D_MINUS_NEW);
		g.setGradeE(GRADE_E_NEW);
		assertTrue(store.putGradingSystem(g)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO#updateGradingSystem(ke.co.fastech.primaryschool.bean.exam.GradingSystem)}.
	 */
	@Ignore
	@Test
	public final void testUpdateGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem g = new GradingSystem();
		g.setUuid(UUID_NEW);
		g.setGradeAplain(GRADE_A_UPDATE);
		g.setGradeAminus(GRADE_A_MINUS_UPDATE);
		g.setGradeBplus(GRADE_B_PLUS_UPDATE);
		g.setGradeBplain(GRADE_B_PLAIN_UPDATE);
		g.setGradeBminus(GRADE_B_MINUS_UPDATE);
		g.setGradeCplus(GRADE_C_PLUS_UPDATE);
		g.setGradeCplain(GRADE_C_PLAIN_UPDATE);
		g.setGradeCminus(GRADE_C_MINUS_UPDATE);
		g.setGradeDplus(GRADE_D_PLUS_UPDATE);
		g.setGradeDplain(GRADE_D_PLAIN_UPDATE);
		g.setGradeDminus(GRADE_D_MINUS_UPDATE);
		g.setGradeE(GRADE_E_UPDATE);
		assertTrue(store.updateGradingSystem(g));  
	}

}
