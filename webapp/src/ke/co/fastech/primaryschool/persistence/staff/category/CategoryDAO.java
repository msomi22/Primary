/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.fastech.primaryschool.persistence.staff.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.staff.Category;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/** 
 * Persistence abstraction for {@link Category}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class CategoryDAO extends GenericDAO  implements SchoolCategoryDAO {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	private static CategoryDAO categoryDAO;

	/**
	 * 
	 * @return
	 */
	public static CategoryDAO getInstance(){
		if(categoryDAO == null){
			categoryDAO = new CategoryDAO();
		}
		return categoryDAO;
	}
	/**
	 * 
	 */
	public CategoryDAO() {
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
	public CategoryDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	/**
	 * @see ke.co.fastech.primaryschool.persistence.staff.category.SchoolCategoryDAO#getCategoryList()
	 */
	@Override
	public List<Category> getCategoryList() {
		List<Category> list = null;
		try(   
				Connection conn = dbutils.getConnection();
				PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Category;");   
				ResultSet rset = pstmt.executeQuery();
				) {

			  list = beanProcessor.toBeanList(rset, Category.class);

		} catch(SQLException e){
			logger.error("SQL Exception when getting Category List");
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}
		return list;
	}

}
