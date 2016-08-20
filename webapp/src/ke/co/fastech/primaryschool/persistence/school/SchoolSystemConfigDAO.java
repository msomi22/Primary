/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.SystemConfig;

/**
 * Persistence description for {@link SystemConfig}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolSystemConfigDAO {
	/**
	 * 
	 * @param accountUuid
	 * @return
	 */
	public SystemConfig getSystemConfig(String accountUuid);
	/**
	 * 
	 * @param systemConfig
	 * @return
	 */
	public boolean putSystemConfig(SystemConfig systemConfig);
	/**
	 * 
	 * @param systemConfig
	 * @return
	 */
	public boolean updateSystemConfig(SystemConfig systemConfig);
	/**
	 * 
	 * @return
	 */
	public List<SystemConfig> getSystemConfig();

}
