/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.ClassTeacher;

/**
 * @author peter
 *
 */
public class TestTeacherClassDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private TeacherClassDAO store;
	
	 private String UUID ="DE8F8DFE-BFF1-4A33-BBC0-8F834ED148F6",UUID_NEW ="0201DDCE-94FC-41CE-8EC8-71DF29C1BD02";
	 private String TEACHER_UUID ="83CD26E1-3E64-4969-9B98-9069ECDB757D",TEACHER_UUID_NEW ="4BB37A08-D180-47DF-8401-B3162F84E23F";
	 private String STREAM_UUID ="2B0F8F79-DEF2-419D-9A76-17450B5CF768",STREAM_UUID_NEW = "57348359-C425-4320-9BCE-AD95D4E9A228";

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO#getTeacherClass(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetTeacherClass() {
		store = new TeacherClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassTeacher classTeacher = new ClassTeacher();
		classTeacher = store.getTeacherClass(TEACHER_UUID);
		assertEquals(classTeacher.getUuid(),UUID);
		assertEquals(classTeacher.getTeacherUuid(),TEACHER_UUID);
		assertEquals(classTeacher.getStreamUuid(),STREAM_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO#getTeacherClass(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testgetTeacherClass() {
		store = new TeacherClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassTeacher classTeacher = new ClassTeacher();
		classTeacher = store.getTeacherClass(TEACHER_UUID, STREAM_UUID);
		assertEquals(classTeacher.getUuid(),UUID);
		assertEquals(classTeacher.getTeacherUuid(),TEACHER_UUID);
		assertEquals(classTeacher.getStreamUuid(),STREAM_UUID);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO#putClassTeacher(ke.co.fastech.primaryschool.bean.staff.ClassTeacher)}.
	 */
	@Ignore
	@Test
	public final void testPutClassTeacher() {
		store = new TeacherClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassTeacher classTeacher = new ClassTeacher();
		classTeacher.setUuid(UUID_NEW);
		classTeacher.setTeacherUuid(TEACHER_UUID_NEW);
		classTeacher.setStreamUuid(STREAM_UUID_NEW); 
		assertTrue(store.putClassTeacher(classTeacher));
	}

	
	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO#deleteClassTeacher(ke.co.fastech.primaryschool.bean.staff.ClassTeacher)}.
	 */
	@Ignore
	@Test
	public final void testDeleteClassTeacher() {
		store = new TeacherClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteClassTeacher(TEACHER_UUID));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO#getClassTeacherList()}.
	 */
	@Ignore
	@Test
	public final void testGetClassTeacherList() {
		store = new TeacherClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ClassTeacher> list = store.getClassTeacherList();
		for(ClassTeacher ct : list){
			System.out.println(ct);
		}
	}

}
