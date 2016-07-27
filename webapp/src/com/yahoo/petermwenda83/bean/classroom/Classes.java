/**
 * 
 */
package com.yahoo.petermwenda83.bean.classroom;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class Classes extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355498812294520397L;
	private String className = "";

	/**
	 * 
	 */
	public Classes() {
		className = "";
	}
   

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Classes");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",className=");
		builder.append(className);
		return builder.toString(); 
		}
}
