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
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.school.Stream;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Stream}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StreamDAO extends GenericDAO implements SchoolStreamDAO {

	private static StreamDAO streamDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();

	public static StreamDAO getInstance(){

		if(streamDAO == null){
			streamDAO = new StreamDAO();		
		}
		return streamDAO;
	}

	/**
	 * 
	 */
	public StreamDAO() { 
		super();
	}

	/**
	 * 
	 */
	public StreamDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolStreamDAO#getStreamById(java.lang.String)
	 */
	@Override
	public Stream getStreamById(String uuid) {
		Stream stream = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE Uuid = ?;");       

				){

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();
			while(rset.next()){
				stream  = beanProcessor.toBean(rset,Stream.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Stream with uuid " + uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return stream; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolStreamDAO#getStream(java.lang.String)
	 */
	@Override
	public Stream getStream(String streamName) {
		Stream stream = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Stream WHERE streamName = ?;");       

				){

			pstmt.setString(1, streamName);
			rset = pstmt.executeQuery();
			while(rset.next()){
				stream  = beanProcessor.toBean(rset,Stream.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting Stream with streamName " + streamName);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return stream; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolStreamDAO#putStream(ke.co.fastech.primaryschool.bean.school.Stream)
	 */
	@Override
	public boolean putStream(Stream stream) {
		boolean success = true;

		try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Stream" 
						+"(Uuid, StreamName) VALUES (?,?);");
				){

			pstmt.setString(1, stream.getUuid());
			pstmt.setString(2, stream.getStreamName());
			pstmt.executeUpdate();

		}catch(SQLException e){
			logger.error("SQL Exception trying to put Stream " + stream);
			logger.error(ExceptionUtils.getStackTrace(e)); 
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		}

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolStreamDAO#updateStream(ke.co.fastech.primaryschool.bean.school.Stream)
	 */
	@Override
	public boolean updateStream(Stream stream) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Stream SET StreamName = ?"
						+ "WHERE Uuid = ?;");
				) {           			 	            
			pstmt.setString(1, stream.getStreamName());
			pstmt.setString(2, stream.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating Stream " + stream);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolStreamDAO#getStreamList()
	 */
	@Override
	public List<Stream> getStreamList() {
		List<Stream>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Stream ;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, Stream.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Stream");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
