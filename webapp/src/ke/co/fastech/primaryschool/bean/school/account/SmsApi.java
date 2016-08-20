/**
 * 
 */
package ke.co.fastech.primaryschool.bean.school.account;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * @author peter
 *
 */
public class SmsApi extends StorableBean{

	private String accountUuid;
	private String apiKey;
	private String apiPassword;
	/**
	 * 
	 */
	public SmsApi() {
		super();
		accountUuid = "";
		apiKey = "";
		apiPassword = "";	
	}
	
	
	/**
	 * @return the accountUuid
	 */
	public String getAccountUuid() {
		return accountUuid;
	}


	/**
	 * @param accountUuid the accountUuid to set
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}


	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}


	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	/**
	 * @return the apiPassword
	 */
	public String getApiPassword() {
		return apiPassword;
	}


	/**
	 * @param apiPassword the apiPassword to set
	 */
	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
	}


	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SmsApi");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", accountUuid = ");
		builder.append(accountUuid);
		builder.append(", apiKey = ");
		builder.append(apiKey);
		builder.append(", apiPassword = ");
		builder.append(apiPassword);
		builder.append("]");
		return builder.toString(); 
		}

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1957745159294998494L;
}
