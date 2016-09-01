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

import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/** 
 *  
 * Persistence abstraction for {@link SystemConfig}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SystemConfigDAO extends GenericDAO implements SchoolSystemConfigDAO {

	private static SystemConfigDAO systemConfigDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static SystemConfigDAO getInstance(){

		if(systemConfigDAO == null){
			systemConfigDAO = new SystemConfigDAO();		
		}
		return systemConfigDAO;
	}

	/**
	 * 
	 */
	public SystemConfigDAO() { 
		super();
	}

	/**
	 * 
	 */
	public SystemConfigDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolSystemConfigDAO#getSystemConfig(java.lang.String)
	 */
	@Override
	public SystemConfig getSystemConfig(String accountUuid) {
		SystemConfig systemConfig = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM SystemConfig WHERE accountUuid = ?;");       

				){

			pstmt.setString(1, accountUuid);
			rset = pstmt.executeQuery();
			while(rset.next()){

				systemConfig  = beanProcessor.toBean(rset,SystemConfig.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting SystemConfig with accountUuid " + accountUuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return systemConfig; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolSystemConfigDAO#putSystemConfig(ke.co.fastech.primaryschool.bean.school.SystemConfig)
	 */
	@Override
	public boolean putSystemConfig(SystemConfig systemConfig) {
		boolean success = true;
		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SystemConfig" 
						+"(Uuid,accountUuid,term,year,smsSend,examcode,openningDate,closingDate) VALUES (?,?,?,?,?,?,?,?);");
				){

			pstmt.setString(1, systemConfig.getUuid());
			pstmt.setString(2, systemConfig.getAccountUuid());
			pstmt.setString(3, systemConfig.getTerm());
			pstmt.setString(4, systemConfig.getYear());
			pstmt.setString(5, systemConfig.getSmsSend());
			pstmt.setString(6, systemConfig.getExamcode());
			pstmt.setString(7, systemConfig.getOpenningDate());
			pstmt.setString(8, systemConfig.getClosingDate());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put SystemConfig " + systemConfig);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}


		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolSystemConfigDAO#updateSystemConfig(ke.co.fastech.primaryschool.bean.school.SystemConfig)
	 */
	@Override
	public boolean updateSystemConfig(SystemConfig systemConfig) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE SystemConfig SET Term =?,Year =?,SmsSend =?,"
						+ "examcode =?,OpenningDate =?,ClosingDate =? WHERE accountUuid = ?;");
				) {           			 	            

			pstmt.setString(1, systemConfig.getTerm());
			pstmt.setString(2, systemConfig.getYear());
			pstmt.setString(3, systemConfig.getSmsSend());
			pstmt.setString(4, systemConfig.getExamcode());
			pstmt.setString(5, systemConfig.getOpenningDate());
			pstmt.setString(6, systemConfig.getClosingDate());
			pstmt.setString(7, systemConfig.getAccountUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating SystemConfig " + systemConfig);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolSystemConfigDAO#getSystemConfig()
	 */
	@Override
	public List<SystemConfig> getSystemConfigList(String accountUuid) {
	     List<SystemConfig> list = new ArrayList<>();
			try(
					Connection conn = dbutils.getConnection();
					PreparedStatement psmt= conn.prepareStatement("SELECT * FROM SystemConfig WHERE accountUuid =?;");
					) {
				   psmt.setString(1,accountUuid);
				  try(ResultSet rset = psmt.executeQuery();){
						
					  list = beanProcessor.toBeanList(rset, SystemConfig.class);
					}
			} catch (SQLException e) {
				logger.error("SQLException when trying to get SystemConfig List");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            System.out.println(ExceptionUtils.getStackTrace(e)); 
		    }
			return list;
		}

}
