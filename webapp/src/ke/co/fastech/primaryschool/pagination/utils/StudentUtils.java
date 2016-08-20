/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.pagination.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.persistence.GenericDAO;





/**
 * @author peter
 *
 */
public class StudentUtils extends GenericDAO {
	
	  private static StudentUtils studentUtils;
	  private final Logger logger = Logger.getLogger(this.getClass());

	  public static StudentUtils getInstance() {
	        if (studentUtils == null) {
	        	studentUtils = new StudentUtils();
	        }

	        return studentUtils;
	    }
	   
	/**
	 * 
	 */
	public StudentUtils() {
		 super();
	}
	
	public StudentUtils(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
        super(databaseName, Host, databaseUsername, databasePassword, databasePort);
    }
	
	
	/**
	 * @param SchoolAccountUuid
	 * @return
	 */
	public int getStudents() {
        int count = 0;
        
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student;");    		   
 	    ) {
        	       
 	       	ResultSet rset = pstmt.executeQuery();
 	       	
 	       	while(rset.next()){
 	       		count = count + 1;
 	            //System.out.println(count);
 	       	}

 	       
        } catch (SQLException e) {
            logger.error("SQLException when getting count from student:");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        
        return count;
    }
    
	
	 /**
	 * @param accountuuid
	 * @return
	 */
	public int getIncomingCount() {
	        int count=0;

	        try ( Connection conn = dbutils.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM Student;");
	        		){           
	          
	                try(ResultSet rset = pstmt.executeQuery();){
	            
	            rset.next();
	            count = count + rset.getInt(1);
	            //System.out.println(count);
	                }

	        } catch (SQLException e) {
	            logger.error("SQLException while getting all incoming count from student");
	            logger.error(ExceptionUtils.getStackTrace(e));

	        } 
	        return count;
	    }
	
	
	
	
	
	
	

}
