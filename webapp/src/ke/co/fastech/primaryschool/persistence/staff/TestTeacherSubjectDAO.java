/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;

/**
 * @author peter
 *
 */
public class TestTeacherSubjectDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	private TeacherSubjectDAO store;
	
	 private String UUID ="304F7D11-57C8-4FBA-9CF9-2702BB5CEFF6",UUID_NEW = "A36C7364-F118-42CC-8FFC-7305369DEFBD";
	 private String TEACHER_UUID ="83CD26E1-3E64-4969-9B98-9069ECDB757D";
	 private String SUBJECT_UUID ="F294FD7C-6321-417C-A56F-CFE8AC8D7950",SUBJECT_UUID_NEW = "D3E46980-A6A0-42E7-AB85-858BD367CF98";
	 private String STREAM_UUID ="2B0F8F79-DEF2-419D-9A76-17450B5CF768",STREAM_UUID_NEW = "57348359-C425-4320-9BCE-AD95D4E9A228";

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO#getTeacherSubject(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetTeacherSubjectString() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject = store.getTeacherSubject(TEACHER_UUID, SUBJECT_UUID, STREAM_UUID);
		assertEquals(teacherSubject.getUuid(),UUID);
		assertEquals(teacherSubject.getTeacherUuid(),TEACHER_UUID); 
		assertEquals(teacherSubject.getSubjectUuid(),SUBJECT_UUID);
		assertEquals(teacherSubject.getStreamUuid(),STREAM_UUID);
	}

	

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO#getTeacherSubjectList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetTeacherSubjectList() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TeacherSubject> list = store.getTeacherSubjectList(TEACHER_UUID);
		for(TeacherSubject ts : list){
			System.out.println(ts);
		}
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO#putTeacherSubjec(ke.co.fastech.primaryschool.bean.staff.TeacherSubject)}.
	 */
	@Ignore
	@Test
	public final void testPutTeacherSubjec() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setUuid(UUID_NEW);
		teacherSubject.setTeacherUuid(TEACHER_UUID);
		teacherSubject.setStreamUuid(STREAM_UUID_NEW);
		teacherSubject.setSubjectUuid(SUBJECT_UUID_NEW); 
		assertTrue(store.putTeacherSubject(teacherSubject));
		
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO#deleteTeacherSubjec(ke.co.fastech.primaryschool.bean.staff.TeacherSubject)}.
	 */
	@Ignore
	@Test
	public final void testDeleteTeacherSubjec() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteTeacherSubject(TEACHER_UUID));
	}
	
	@Ignore
	@Test
	public final void testDeleteTeacherSubjec2() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		assertTrue(store.deleteTeacherSubject(TEACHER_UUID, SUBJECT_UUID_NEW, STREAM_UUID_NEW));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO#getAllTeacherSubjectList()}.
	 */
	@Ignore
	@Test
	public final void testGetAllTeacherSubjectList() {
		store = new TeacherSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TeacherSubject>  list = store.getAllTeacherSubjectList();
		for(TeacherSubject tsub : list){
			System.out.println(tsub);
		}
	}

}
