/**
 * 
 */
package com.yahoo.petermwenda83.server.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author peter
 *
 */
 
public class SessionStatistics implements Serializable {
	
	//They are used to keep userId against online status
    private Map<String, String> userStatus;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6516924608718437954L;
   
	 public SessionStatistics() {
		 userStatus = new HashMap<>();
	 }

	/**
	 * @return the userStatus
	 */
	public Map<String, String> getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(Map<String, String> userStatus) {
		this.userStatus = userStatus;
	}
	
	//anonymous class
	class onlineUser{
		private String userid;
		private String onlinestatus;
		public onlineUser(){
			userid = "";
			onlinestatus = "";
		}
		/**
		 * @return the userid
		 */
		public String getUserid() {
			return userid;
		}
		/**
		 * @param userid the userid to set
		 */
		public void setUserid(String userid) {
			this.userid = userid;
		}
		/**
		 * @return the onlinestatus
		 */
		public String getOnlinestatus() {
			return onlinestatus;
		}
		/**
		 * @param onlinestatus the onlinestatus to set
		 */
		public void setOnlinestatus(String onlinestatus) {
			this.onlinestatus = onlinestatus;
		}
		
	}
	 
}

 