/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school;

import ke.co.fastech.primaryschool.bean.StorableBean;

/** 
 * a class in a school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Classroom extends StorableBean{
	
	private String className;

	/**
	 * 
	 */
	public Classroom() {
		super();
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
		builder.append("Classroom");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", className = ");
		builder.append(className);
		builder.append("]");
		return builder.toString(); 
		}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5240507982925693871L;
}
