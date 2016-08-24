/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.staff.TeacherSubject;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link TeacherSubject}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherSubjectDAO extends GenericDAO  implements SchoolTeacherSubjectDAO {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static TeacherSubjectDAO teacherSubjectDAO;

	/**
	 * 
	 * @return
	 */
	public static TeacherSubjectDAO getInstance(){
		if(teacherSubjectDAO == null){
			teacherSubjectDAO = new TeacherSubjectDAO();
		}
		return teacherSubjectDAO;
	}
	/**
	 * 
	 */
	public TeacherSubjectDAO() {
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
	public TeacherSubjectDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}
	
	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#getTeacherSubject(java.lang.String, java.lang.String)
	 */
	@Override
	public TeacherSubject getTeacherSubject(String subjectUuid, String streamUuid) {
		TeacherSubject teacherSubject = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE subjectUuid = ? AND streamUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, subjectUuid);
     	     pstmt.setString(2, streamUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teacherSubject  = beanProcessor.toBean(rset,TeacherSubject.class);
	   }
     	
     	
     	
     }catch(SQLException e){ 
     	  logger.error("SQL Exception when getting Teacher with " +  
     			  "  subjectUuid " + subjectUuid + " and streamUuid" + streamUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teacherSubject; 
	}
	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#getTeacherSubject(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TeacherSubject getTeacherSubject(String teacherUuid, String subjectUuid, String streamUuid) {
		TeacherSubject teacherSubject = null;
        ResultSet rset = null;
     try(
     		      Connection conn = dbutils.getConnection();
        	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE teacherUuid = ? AND subjectUuid = ? AND streamUuid = ?;");       
     		
     		){
     	
     	     pstmt.setString(1, teacherUuid);
     	     pstmt.setString(2, subjectUuid);
     	     pstmt.setString(3, streamUuid);
	         rset = pstmt.executeQuery();
	        while(rset.next()){
	
	        	teacherSubject  = beanProcessor.toBean(rset,TeacherSubject.class);
	   }
     	
     	
     	
     }catch(SQLException e){ 
     	  logger.error("SQL Exception when getting Teacher's Subject(s) for teacherUuid " + teacherUuid + 
     			  " and subjectUuid " + subjectUuid + " and streamUuid" + streamUuid);
          logger.error(ExceptionUtils.getStackTrace(e));
          System.out.println(ExceptionUtils.getStackTrace(e));
     }
     
		return teacherSubject; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#getTeacherSubjectList(java.lang.String)
	 */
	@Override
	public List<TeacherSubject> getTeacherSubjectList(String teacherUuid) {
		List<TeacherSubject> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject WHERE teacherUuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, teacherUuid);      
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, TeacherSubject.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Teacher's subjects List for teacherUuid " + teacherUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#putTeacherSubjec(ke.co.fastech.primaryschool.bean.staff.TeacherSubject)
	 */
	@Override
	public boolean putTeacherSubject(TeacherSubject teacherSubject) {
		boolean success = true; 
		  
		 try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TeacherSubject" 
			        		+"(Uuid,teacherUuid,subjectUuid,streamUuid) VALUES (?,?,?,?);");
   		){
	            pstmt.setString(1, teacherSubject.getUuid());
	            pstmt.setString(2, teacherSubject.getTeacherUuid());
	            pstmt.setString(3, teacherSubject.getSubjectUuid());
	            pstmt.setString(4, teacherSubject.getStreamUuid());          
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Teacher's Subjects " + teacherSubject);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }	
		
		return success;
	}

	

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#deleteTeacherSubjec(ke.co.fastech.primaryschool.bean.staff.TeacherSubject)
	 */
	@Override
	public boolean deleteTeacherSubject(String teacherUuid,String subjectUuid,String streamUuid) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TeacherSubject WHERE teacherUuid = ? AND subjectUuid =? AND streamUuid =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, teacherUuid);
        	 pstmt.setString(2, subjectUuid);
        	 pstmt.setString(3, streamUuid);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting subjectUuid " + subjectUuid +  "and  streamUuid " + streamUuid + ""
        	 		+ " for staff " + teacherUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}
	
	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#deleteTeacherSubjec(java.lang.String)
	 */
	@Override
	public boolean deleteTeacherSubject(String teacherUuid) {
		boolean success = true; 
        try(
        	Connection conn = dbutils.getConnection();
           	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TeacherSubject WHERE teacherUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, teacherUuid);
	         pstmt.executeUpdate();
	     
        }catch(SQLException e){
        	 logger.error("SQL Exception when deletting Teacher's Subjects for teacher" + teacherUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             success = false;
             
        }
        
		return success; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.SchoolTeacherSubjectDAO#getAllTeacherSubjectList()
	 */
	@Override
	public List<TeacherSubject> getAllTeacherSubjectList() {
		List<TeacherSubject>  list = null;
		
		 try(   
      		Connection conn = dbutils.getConnection();
      		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM TeacherSubject;");   
      		ResultSet rset = pstmt.executeQuery();
  		) {
      	
          list = beanProcessor.toBeanList(rset, TeacherSubject.class);

      } catch(SQLException e){
      	  logger.error("SQL Exception when getting Teachers Subjects List");
          logger.error(ExceptionUtils.getStackTrace(e));
      }
		return list;
	}
	

}
