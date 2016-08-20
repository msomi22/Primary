/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import ke.co.fastech.primaryschool.bean.exam.GradingSystem;

/**
 * Persistence description for {@link GradingSystem}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolGradingSystemDAO {
	
	/**
	 * 
	 * @param accountUuid
	 * @return
	 */
	public GradingSystem getGradingSystem(String accountUuid);
	/**
	 * 
	 * @param gradingSystem The {@link GradingSystem} Object
	 * @return The whether the {@link GradingSystem}  was inserted successfully
	 */
	public boolean putGradingSystem(GradingSystem gradingSystem);
	/**
	 * 
	 * @param gradingSystem The {@link GradingSystem} Object
	 * @return The whether the {@link GradingSystem}  was updated successfully
	 */
	public boolean updateGradingSystem(GradingSystem gradingSystem);

}
