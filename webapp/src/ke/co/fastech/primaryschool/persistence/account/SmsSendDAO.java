/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import ke.co.fastech.primaryschool.bean.school.account.SmsSend;
import ke.co.fastech.primaryschool.persistence.GenericDAO;

/**
 * @author peter
 *
 */
public class SmsSendDAO extends GenericDAO implements SchoolSmsSendDAO {

	public static SmsSendDAO smsSendDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static SmsSendDAO  getInstance(){
		if(smsSendDAO == null){
			smsSendDAO = new SmsSendDAO();
		}
		return smsSendDAO;
	}
	/**
	 * 
	 */
	public SmsSendDAO() {
		super();
	}
	
	/**
	 * @param databaseName
	 * @param Host
	 * @param databaseUsername
	 * @param databasePassword
	 * @param databasePort
	 */
	public SmsSendDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


	
	/* (non-Javadoc)
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolSmsSendDAO#putSmsSend(ke.co.fastech.primaryschool.bean.school.account.SmsSend)
	 */
	@Override
	public SmsSend putSmsSend(SmsSend smssend) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolSmsSendDAO#deleteSmsSend(java.lang.String)
	 */
	@Override
	public boolean deleteSmsSend(String status) {
		boolean success = false;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("DELETE FROM SmsSend WHERE "
						+ "status = ?;");
				) {
			psmt.setString(1, status);
			psmt.executeQuery(); 
			
		} catch (SQLException e) {
			logger.error("SQLException when trying to delete smssend  with status " + status);
			logger.error(ExceptionUtils.getStackTrace(e));
			//System.out.println(ExceptionUtils.getStackTrace(e)); 
			success = false;
		}

		return success;

	}

	/**
	 * @see ke.co.fastech.primaryschool.persistence.account.SchoolSmsSendDAO#getsmssendList(java.lang.String)
	 */
	@Override
	public List<SmsSend> getsmssendList(String status) {
		List<SmsSend> smssendList = null;
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM SmsSend WHERE "
						+ "status = ?;");
				) {
			psmt.setString(1, status);
			try(ResultSet rset = psmt.executeQuery();){

				smssendList = beanProcessor.toBeanList(rset, SmsSend.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get smssend List with status " + status);
			logger.error(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getStackTrace(e)); 
		}

		return smssendList;

	}

}
