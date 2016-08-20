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

import java.util.List;

import ke.co.fastech.primaryschool.bean.staff.user.Users;

/**
 * Persistence description for {@link Users}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolUserDAO {
	/**
	 * 
	 * @param staffUuid The usre ID
	 * @return {@link Users} Object
	 */
	public Users getUser(String staffUuid);
	/**
	 * 
	 * @param username The username
	 * @return {@link Users} Object
	 */
	public Users getUserByUsername(String username);
	
	/**
	 * 
	 * @param username The username
	 * @param password The user password
	 * @return {@link Users} Object
	 */
	public Users getUserPassword(String username,String password);
	/**
	 * 
	 * @param users {@link Users} object
	 * @return
	 */
	public boolean putUser(Users users);
	/**
	 * 
	 * @param users
	 * @return
	 */
	public boolean updateUser(Users users);
	/**
	 * 
	 * @return {@link Users} List
	 */
	public List<Users> getUserList();

}
