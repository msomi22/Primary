/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ke.co.fastech.primaryschool.bean.staff.user.Users;

/**
 * Test Unit for {@link Users} DAO 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestUserDAO {
	
	final String databaseName = "primarydb";
	final String Host = "localhost";
	final String databaseUsername = "prim";
	final String databasePassword = "priM123PriM";
	final int databasePort = 5432;
	
	 final String UUID = "87E4C0A3-E6EA-4C27-8A70-F88AEC3AC357";
	 final String STAFF_UUID = "83CD26E1-3E64-4969-9B98-9069ECDB757D";
	 private final String USERNAME = "peter",USERNAME_NEW ="username";
	 private final String PASSWORD = "1234",PASSWORD_NEW ="password";
	
	private UserDAO store;

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#getUser(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetUser() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Users user = new Users();
		user = store.getUser(STAFF_UUID);
		assertEquals(user.getStaffUuid(),STAFF_UUID);
		assertEquals(user.getUsername(),USERNAME);
		assertEquals(user.getPassword(),PASSWORD);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#getUserByUsername(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetUserByUsername() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Users user = new Users();
		user = store.getUserByUsername(USERNAME);
		assertEquals(user.getStaffUuid(),STAFF_UUID);
		assertEquals(user.getUsername(),USERNAME);
		assertEquals(user.getPassword(),PASSWORD);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#getUserPassword(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetUserPassword() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Users user = new Users();
		user = store.getUserPassword(USERNAME, PASSWORD);
		assertEquals(user.getStaffUuid(),STAFF_UUID);
		assertEquals(user.getUsername(),USERNAME);
		assertEquals(user.getPassword(),PASSWORD);
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#putUser(ke.co.fastech.primaryschool.bean.staff.user.Users)}.
	 */
	@Ignore
	@Test
	public final void testPutUser() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Users user = new Users();
		user.setUuid(UUID);
		user.setStaffUuid(STAFF_UUID);
		user.setUsername(USERNAME_NEW);
		user.setPassword(PASSWORD_NEW);
		assertTrue(store.putUser(user));
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#updateUser(ke.co.fastech.primaryschool.bean.staff.user.Users)}.
	 */
	@Ignore
	@Test
	public final void testUpdateUser() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Users user = new Users();
		user.setUuid(UUID);
		user.setStaffUuid(STAFF_UUID);
		user.setUsername("updated");
		user.setPassword("updated");
		assertTrue(store.updateUser(user)); 
	}

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.persistence.user.UserDAO#getUserList()}.
	 */
	@Ignore
	@Test
	public final void testGetUserList() {
		store = new UserDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Users> userlis = store.getUserList();
		for(Users usr : userlis){
			System.out.println(usr); 
		}
	}

}
