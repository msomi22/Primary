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

import ke.co.fastech.primaryschool.bean.school.Comment;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Comment}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class CommentDAO extends GenericDAO implements SchoolCommentDAO {

	private static CommentDAO commentDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static CommentDAO getInstance(){
		
		if(commentDAO == null){
			commentDAO = new CommentDAO();		
		}
		return commentDAO;
	}
	
	/**
	 * 
	 */
	public CommentDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public CommentDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolCommentDAO#getComment(java.lang.String)
	 */
	@Override
	public Comment getComment(String accountUuid) {
		Comment comment = null;
        ResultSet rset = null;
        try(
        	     Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Comment WHERE accountUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, accountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 comment  = beanProcessor.toBean(rset,Comment.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting comment with accountUuid " + accountUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return comment; 
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolCommentDAO#putComment(ke.co.fastech.primaryschool.bean.school.Comment)
	 */
	@Override
	public boolean putComment(Comment comment) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Comment" 
			        		+"(Uuid,accountUuid,headTeacherCom,gradeAplaincom,gradeAminuscom,gradeBpluscom,gradeBplaincom,"
			        		+ "gradeBminuscom,gradeCpluscom,gradeCplaincom,gradeCminuscom,gradeDpluscom,"
			        		+ "gradeDplaincom,gradeDminuscom,gradeEcom) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, comment.getUuid());
	            pstmt.setString(2, comment.getAccountUuid());
	            pstmt.setString(3, comment.getHeadTeacherCom());
	            pstmt.setString(4, comment.getGradeAplaincom());
	            pstmt.setString(5, comment.getGradeAminuscom());
	            pstmt.setString(6, comment.getGradeBpluscom());
	            pstmt.setString(7, comment.getGradeBplaincom());
	            pstmt.setString(8, comment.getGradeBminuscom());
	            pstmt.setString(9, comment.getGradeCpluscom());
	            pstmt.setString(10, comment.getGradeCplaincom());
	            pstmt.setString(11, comment.getGradeCminuscom());
	            pstmt.setString(12, comment.getGradeDpluscom());
	            pstmt.setString(13, comment.getGradeDplaincom());
	            pstmt.setString(14, comment.getGradeDminuscom());
	            pstmt.setString(15, comment.getGradeEcom()); 
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Comment " + comment);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolCommentDAO#updateComment(ke.co.fastech.primaryschool.bean.school.Comment)
	 */
	@Override
	public boolean updateComment(Comment comment) {
		boolean success = true;

		try (  Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Comment SET HeadTeacherCom = ?,GradeAplaincom =?,GradeAminuscom =?,"
						+ "GradeBpluscom =?,GradeBplaincom =?,GradeBminuscom=?, GradeCpluscom =?, GradeCplaincom =?,"
						+ "GradeCminuscom =?,GradeDpluscom =?, GradeDplaincom =?, GradeDminuscom =?, GradeEcom =? WHERE accountUuid = ?;");
				) { 
			
	            pstmt.setString(1, comment.getHeadTeacherCom());
	            pstmt.setString(2, comment.getGradeAplaincom());
	            pstmt.setString(3, comment.getGradeAminuscom());
	            pstmt.setString(4, comment.getGradeBpluscom());
	            pstmt.setString(5, comment.getGradeBplaincom());
	            pstmt.setString(6, comment.getGradeBminuscom());
	            pstmt.setString(7, comment.getGradeCpluscom());
	            pstmt.setString(8, comment.getGradeCplaincom());
	            pstmt.setString(9, comment.getGradeCminuscom());
	            pstmt.setString(10, comment.getGradeDpluscom());
	            pstmt.setString(11, comment.getGradeDplaincom());
	            pstmt.setString(12, comment.getGradeDminuscom());
	            pstmt.setString(13, comment.getGradeEcom()); 
	            pstmt.setString(14, comment.getAccountUuid());
			    pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQL Exception when updating Comment " + comment);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
			success = false;
		} 

		return success;
	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.school.SchoolCommentDAO#getCommentList()
	 */
	@Override
	public List<Comment> getCommentList() {
		List<Comment>  list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Comment ;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			list = beanProcessor.toBeanList(rset, Comment.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting all Comment");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		return list;
	}

}
