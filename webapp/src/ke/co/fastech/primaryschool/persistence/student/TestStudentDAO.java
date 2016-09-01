/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.student;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.student.Student;


/**
 * Test Unit for {@link Student} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestStudentDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private StudentDAO store;
	
	private final String ACCOUNT_UUID = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4";
	
	private final String STUDENT_UUID = "CA69EB58-7DCA-4E9E-80B1-AC47B63A8C21",
			             STUDENT_UUID_NEW ="D1A13DE9-36CA-4AF2-9912-02CFEB25D7B7";
	
	private final String STATUS_UUID = "B2733AB2-9CDB-4361-89C5-86B175A2E828";
	private final String STREAM_UUID = "2B0F8F79-DEF2-419D-9A76-17450B5CF768";
	
	private final String ADMISSION_NUMBER = "100",ADMISSION_NUMBER_NEW = "2000";
	private final String FIRSTNAME = "Peter", FIRSTNAME_NEW ="Dynah";
	private final String MIDDLENAME = "Mwenda", MIDDLENAME_NEW ="Mwende";
	private final String LASTNAME = "Njeru", LASTNAME_NEW ="Mwangi";
	private final String GENDER = "M";
	private final String DATEOFBIRTH = "19/90/31";
	private final String BIRTHCERTIFICATE = "L177728";
	private final String COUNTRY = "Kenyan";
	private final String COUNTY = "Tharaka Nithi";
	private final String WARD = "Mugwe";
	private final int REG_TERM = 1;
	private final int REG_YEAR = 2016;
	private final int FINAL_TERM = 3;
	private final int FINAL_YEAR = 2019;
	private final String STUDENT_TYPE  = "Day",STUDENT_TYPE_NEW ="Boarding";
	private final String STUDENT_LEVEL  = "UPPER",STUDENT_LEVEL_NEW ="LOWER";
	private final Date ADMISSION_DATE = new Date(new Long("1419410347000") );
	

	
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentByUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentByUuid() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Student s = new Student();
		s = store.getStudentByUuid(STUDENT_UUID);
		assertEquals(s.getUuid(),STUDENT_UUID); 
		assertEquals(s.getStatusUuid(),STATUS_UUID);
		assertEquals(s.getStreamUuid(),STREAM_UUID);
		assertEquals(s.getAdmmissinNo(),ADMISSION_NUMBER);
		assertEquals(s.getFirstname(),FIRSTNAME);
		assertEquals(s.getMiddlename(),MIDDLENAME);
		assertEquals(s.getLastname(),LASTNAME);
		assertEquals(s.getDateofbirth(),DATEOFBIRTH);
		assertEquals(s.getBirthcertificateNo(),BIRTHCERTIFICATE);
		assertEquals(s.getGender(),GENDER);
		assertEquals(s.getCountry(),COUNTRY);
		assertEquals(s.getCounty(),COUNTY);
		assertEquals(s.getWard(),WARD);
		assertEquals(s.getRegTerm(),REG_TERM);
		assertEquals(s.getRegYear(),REG_YEAR);
		assertEquals(s.getFinalTerm(),FINAL_TERM);
		assertEquals(s.getFinalYear(),FINAL_YEAR);
		assertEquals(s.getStudentType(),STUDENT_TYPE);
		assertEquals(s.getStudentLevel(),STUDENT_LEVEL);
		//assertEquals(s.getAdmissiondate(),ADMISSION_DATE);
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentByADMNO(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentByADMNO() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Student s = new Student();
		s = store.getStudentByADMNO(ADMISSION_NUMBER,ACCOUNT_UUID); 
		assertEquals(s.getUuid(),STUDENT_UUID); 
		assertEquals(s.getStatusUuid(),STATUS_UUID);
		assertEquals(s.getStreamUuid(),STREAM_UUID);
		assertEquals(s.getAdmmissinNo(),ADMISSION_NUMBER);
		assertEquals(s.getFirstname(),FIRSTNAME);
		assertEquals(s.getMiddlename(),MIDDLENAME);
		assertEquals(s.getLastname(),LASTNAME);
		assertEquals(s.getDateofbirth(),DATEOFBIRTH);
		assertEquals(s.getBirthcertificateNo(),BIRTHCERTIFICATE);
		assertEquals(s.getGender(),GENDER);
		assertEquals(s.getCountry(),COUNTRY);
		assertEquals(s.getCounty(),COUNTY);
		assertEquals(s.getWard(),WARD);
		assertEquals(s.getRegTerm(),REG_TERM);
		assertEquals(s.getRegYear(),REG_YEAR);
		assertEquals(s.getFinalTerm(),FINAL_TERM);
		assertEquals(s.getFinalYear(),FINAL_YEAR);
		assertEquals(s.getStudentType(),STUDENT_TYPE);
		assertEquals(s.getStudentLevel(),STUDENT_LEVEL);
		//assertEquals(s.getAdmissiondate(),ADMISSION_DATE);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#putStudent(ke.co.fastech.primaryschool.bean.student.Student)}.
	 */
	@Ignore
	@Test
	public final void testPutStudent() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Student s = new Student();
		s.setUuid(STUDENT_UUID_NEW);
		s.setAccountUuid(ACCOUNT_UUID); 
		s.setStatusUuid(STATUS_UUID);
		s.setStreamUuid(STREAM_UUID);
		s.setAdmmissinNo(ADMISSION_NUMBER_NEW);
		s.setFirstname(FIRSTNAME_NEW);
		s.setMiddlename(MIDDLENAME_NEW);
		s.setLastname(LASTNAME_NEW);
		s.setDateofbirth(DATEOFBIRTH);
		s.setBirthcertificateNo(BIRTHCERTIFICATE);
		s.setGender(GENDER);
		s.setCountry(COUNTRY);
		s.setCounty(COUNTY);
		s.setWard(WARD);
		s.setRegTerm(REG_TERM);
		s.setRegYear(REG_YEAR);
		s.setFinalTerm(FINAL_TERM);
		s.setFinalYear(FINAL_YEAR);
		s.setStudentType(STUDENT_TYPE);
		s.setStudentLevel(STUDENT_LEVEL);
		s.setAdmissiondate(ADMISSION_DATE);
		assertTrue(store.putStudent(s));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#updateStudent(ke.co.fastech.primaryschool.bean.student.Student)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudent() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Student s = new Student();
		s.setUuid(STUDENT_UUID_NEW);
		s.setStatusUuid(STATUS_UUID);
		s.setStreamUuid(STREAM_UUID);
		s.setAdmmissinNo("3000");
		s.setFirstname("Updated");
		s.setMiddlename("Updated");
		s.setLastname("Updated");
		s.setDateofbirth(DATEOFBIRTH);
		s.setBirthcertificateNo(BIRTHCERTIFICATE);
		s.setGender(GENDER);
		s.setCountry(COUNTRY);
		s.setCounty(COUNTY);
		s.setWard(WARD);
		s.setRegTerm(REG_TERM);
		s.setRegYear(REG_YEAR);
		s.setFinalTerm(FINAL_TERM);
		s.setFinalYear(FINAL_YEAR);
		s.setStudentType(STUDENT_TYPE_NEW);
		s.setStudentLevel(STUDENT_LEVEL_NEW);
		s.setAdmissiondate(ADMISSION_DATE);
		assertTrue(store.updateStudent(s)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentListByAdmNo(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentListByAdmNo() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentListByAdmNo("10",ACCOUNT_UUID);
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentListByStreamUuid(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentListByStreamUuid() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentListByStreamUuid("2B0F8F79-DEF2-419D-9A76-17450B5CF768",ACCOUNT_UUID);
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentsListByLimit(int, int)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentsListByLimit() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentsListByLimit(0, 15,ACCOUNT_UUID);
		for (Student l : list) {
			System.out.println(l);	
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.student.StudentDAO#getStudentsList()}.
	 */
	@Ignore
	@Test
	public final void testGetStudentsList() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getStudentsList(ACCOUNT_UUID);
		for (Student l : list) {
			System.out.println(l);	
		}
	}

}
