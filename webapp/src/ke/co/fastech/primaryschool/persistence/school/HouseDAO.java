/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.school.House;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link House}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class HouseDAO extends GenericDAO implements SchoolHouseDAO {

	private static HouseDAO houseDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static HouseDAO getInstance(){

		if(houseDAO == null){
			houseDAO = new HouseDAO();		
		}
		return houseDAO;
	}

	/**
	 * 
	 */
	public HouseDAO() { 
		super();
	}

	/**
	 * 
	 */
	public HouseDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolHouseDAO#getHouse(java.lang.String)
	 */
	@Override
	public House getHouse(String uuid) {
		House house = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM House WHERE Uuid = ?;");       

				){

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				house  = beanProcessor.toBean(rset,House.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting House with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return house; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolHouseDAO#getHouseByName(java.lang.String)
	 */
	@Override
	public House getHouseByName(String houseName,String accountUuid) {
		House house = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM House WHERE houseName = ? AND accountUuid =?;");       

				){

			pstmt.setString(1, houseName);
			pstmt.setString(2, accountUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				house  = beanProcessor.toBean(rset,House.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting House with houseName " + houseName);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return house; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolHouseDAO#putHouse(ke.co.fastech.primaryschool.bean.school.House)
	 */
	@Override
	public boolean putHouse(House house) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO House" 
						+"(Uuid,AccountUuid,HouseName) VALUES (?,?,?);");
				){

			pstmt.setString(1, house.getUuid());
			pstmt.setString(2, house.getAccountUuid());
			pstmt.setString(3, house.getHouseName());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put house "+ house);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolHouseDAO#updateHouse(ke.co.fastech.primaryschool.bean.school.House)
	 */
	@Override
	public boolean updateHouse(House house) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE House SET HouseName = ?"
						+ "WHERE Uuid = ? AND AccountUuid = ?;");
				) {           			 	            
			pstmt.setString(1, house.getHouseName());
			pstmt.setString(2, house.getUuid());
			pstmt.setString(3, house.getAccountUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating House " + house);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolHouseDAO#getHouseList()
	 */
	@Override
	public List<House> getHouseList(String accountUuid) {
	     List<House> list = new ArrayList<>();
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM House WHERE accountUuid =?;");
					) {
				   psmt.setString(1,accountUuid);
				  try(ResultSet rset = psmt.executeQuery();){
						
					  list = beanProcessor.toBeanList(rset, House.class);
					}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get House List");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			return list;
		}

}
