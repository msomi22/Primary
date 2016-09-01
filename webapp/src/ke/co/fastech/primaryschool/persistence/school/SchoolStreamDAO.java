/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.Stream;

/**
 * Persistence description for {@link Stream}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStreamDAO {
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Stream getStreamById(String uuid);
	/**
	 * 
	 * @param streamName
	 * @return
	 */
	public Stream getStream(String streamName,String accountUuid);
	/**
	 * 
	 * @param stream
	 * @return
	 */
	public boolean putStream(Stream stream);
	/**
	 * 
	 * @param stream
	 * @return
	 */
	public boolean updateStream(Stream stream);
	/**
	 * 
	 * @return
	 */
	public List<Stream> getStreamList(String accountUuid);

}
