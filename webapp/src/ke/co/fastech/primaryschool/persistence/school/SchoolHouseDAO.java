/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.House;

/**
 * Persistence description for {@link House}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolHouseDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public House getHouse(String uuid);
	/**
	 * 
	 * @param houseName
	 * @return
	 */
	public House getHouseByName(String houseName);
	/**
	 * 
	 * @param house
	 * @return
	 */
	public boolean putHouse(House house);
	/**
	 * 
	 * @param house
	 * @return
	 */
	public boolean updateHouse(House house);
	/**
	 * 
	 * @return
	 */
	public List<House> getHouseList();

}
