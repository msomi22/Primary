/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.bean.school;

import ke.co.fastech.primaryschool.bean.StorableBean;

/**
 * a stream in a school
 * 
 *@author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Stream extends StorableBean{
	
	private String streamName;

	/**
	 * 
	 */
	public Stream() {
		super();
		streamName = "";
	}
	
	/**
	 * @return the streamName
	 */
	public String getStreamName() {
		return streamName;
	}

	/**
	 * @param streamName the streamName to set
	 */
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Stream");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", streamName = ");
		builder.append(streamName);
		builder.append("]");
		return builder.toString(); 
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2921030451158646017L;
}
