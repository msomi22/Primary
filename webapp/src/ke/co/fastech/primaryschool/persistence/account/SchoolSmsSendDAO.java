/**
 * 
 */
package ke.co.fastech.primaryschool.persistence.account;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.account.SmsSend;

/**
 * @author peter
 *  Persistence description for {@link SmsSend} DAO
 *  
 ** @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
public interface SchoolSmsSendDAO {
	/**
	 * 
	 * @param smssend  SmsSend object  
	 * @return {@link SmsSend} object
	 */
	public SmsSend putSmsSend(SmsSend smssend);
	/**
	 *  
	 * @param status sms status
	 * @return whether {@link SmsSend} was deleted successfully
	 */
	public boolean deleteSmsSend(String status);
	/**
	 * 
	 * @param status sms status
	 * @return {@link SmsSend} List
	 */
	public List<SmsSend> getsmssendList(String status); 
} 
