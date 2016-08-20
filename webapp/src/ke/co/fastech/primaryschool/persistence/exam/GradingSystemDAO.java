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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.exam.GradingSystem;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link GradingSystem}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class GradingSystemDAO extends GenericDAO implements SchoolGradingSystemDAO {
   
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static GradingSystemDAO gradingSystemDAO;
	/**
	 * 
	 * @return {@link GradingSystemDAO} Instance
	 */
	public static GradingSystemDAO getInstance(){
		if(gradingSystemDAO == null){
			gradingSystemDAO = new GradingSystemDAO();
		}
		return gradingSystemDAO;
	}
	
	/**
	 * 
	 */
	public GradingSystemDAO() {
		super();
	}
	
	/**
	 * Invoke the super {@link GenericDAO} constructor  
	 * 
	 * @param databaseName The database name
	 * @param Host the database host
	 * @param databaseUsername the database user
	 * @param databasePassword the database password
	 * @param databasePort database port
	 */
	 
	public GradingSystemDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolGradingSystemDAO#getGradingSystem(java.lang.String)
	 */
	@Override
	public GradingSystem getGradingSystem(String accountUuid) {
		GradingSystem gradingSystem = null;
		ResultSet rset = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GradingSystem"
						+ " WHERE accountUuid = ?;");       

				){

			pstmt.setString(1, accountUuid); 
			rset = pstmt.executeQuery();
			while(rset.next()){

				gradingSystem  = beanProcessor.toBean(rset,GradingSystem.class);
			}

		}catch(SQLException e){
			logger.error("SQL Exception when getting GradingSystem with accountUuid " + accountUuid);
			logger.error(ExceptionUtils.getStackTrace(e));

		}

		return gradingSystem; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolGradingSystemDAO#putGradingSystem(ke.co.fastech.primaryschool.bean.exam.GradingSystem)
	 */
	@Override
	public boolean putGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GradingSystem" 
			        		+"(Uuid,accountUuid,GradeAplain,GradeAminus,GradeBplus,GradeBplain,"
			        		+ "GradeBminus,GradeCplus,GradeCplain,GradeCminus,GradeDplus,GradeDplain,"
			        		+ "GradeDminus,GradeE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
     		){
			   
			    pstmt.setString(1, gradingSystem.getUuid());
			    pstmt.setString(2, gradingSystem.getAccountUuid());
			    pstmt.setInt(3, gradingSystem.getGradeAplain());
	            pstmt.setInt(4, gradingSystem.getGradeAminus());
	            pstmt.setInt(5, gradingSystem.getGradeBplus());
	            pstmt.setInt(6, gradingSystem.getGradeBplain());
	            pstmt.setInt(7, gradingSystem.getGradeBminus());
	            pstmt.setInt(8, gradingSystem.getGradeCplus());
	            pstmt.setInt(9, gradingSystem.getGradeCplain());
	            pstmt.setInt(10, gradingSystem.getGradeCminus());
	            pstmt.setInt(11, gradingSystem.getGradeDplus());
	            pstmt.setInt(12, gradingSystem.getGradeDplain());
	            pstmt.setInt(13, gradingSystem.getGradeDminus());
	            pstmt.setInt(14, gradingSystem.getGradeE());
	           
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put GradingSystem: "+gradingSystem);
            logger.error(ExceptionUtils.getStackTrace(e)); 
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
		 }
		
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.exam.SchoolGradingSystemDAO#updateGradingSystem(ke.co.fastech.primaryschool.bean.exam.GradingSystem)
	 */
	@Override
	public boolean updateGradingSystem(GradingSystem gradingSystem) {
		boolean success = true;
        try (  Connection conn = dbutils.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement("UPDATE GradingSystem SET GradeAplain =?,"
        			+ "GradeAminus =?,GradeBplus =?,GradeBplain=?,GradeBminus =?,GradeCplus =?,"
        			+ "GradeCplain =?,GradeCminus =?,GradeDplus =?,GradeDplain =?,GradeDminus =?,"
        			+ "GradeE =?  WHERE Uuid =?;");
        	) { 
        	   
			    pstmt.setInt(1, gradingSystem.getGradeAplain());
	            pstmt.setInt(2, gradingSystem.getGradeAminus());
	            pstmt.setInt(3, gradingSystem.getGradeBplus());
	            pstmt.setInt(4, gradingSystem.getGradeBplain());
	            pstmt.setInt(5, gradingSystem.getGradeBminus());
	            pstmt.setInt(6, gradingSystem.getGradeCplus());
	            pstmt.setInt(7, gradingSystem.getGradeCplain());
	            pstmt.setInt(8, gradingSystem.getGradeCminus());
	            pstmt.setInt(9, gradingSystem.getGradeDplus());
	            pstmt.setInt(10, gradingSystem.getGradeDplain());
	            pstmt.setInt(11, gradingSystem.getGradeDminus());
	            pstmt.setInt(12, gradingSystem.getGradeE());	            
			    pstmt.setString(13, gradingSystem.getUuid());
                pstmt.executeUpdate(); 

        } catch (SQLException e) {
            logger.error("SQL Exception when updating GradingSystem" + gradingSystem);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
            success = false;
        } 
        
        return success;
	}

}
