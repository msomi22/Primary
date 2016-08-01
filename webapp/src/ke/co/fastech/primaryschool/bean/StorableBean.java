/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class represents an object in the School System architecture that can be
 * stored in the RDBMS as well as cached.
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StorableBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6756966885024490524L;
	private int id;
	private String uuid;
	/**
	 * 
	 */
	public StorableBean() {
		id = 0;
		uuid = UUID.randomUUID().toString();
	} 
	/**
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	 /**
	  * 
	  * @return the uuid
	  */
	public String getUuid() {
		return uuid;
	}
	/**
	 * 
	 * @param uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

}
