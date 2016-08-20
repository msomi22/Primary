/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import ke.co.fastech.primaryschool.bean.exam.Performance;

/**
 * Persistence description for {@link Performance}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolExamEngineDAO {
	
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link Performance} was deleted successfully
	 */
	 
	public boolean deletePerformance(String studentUuid,String term,String year);

	/**
	 * 
	 * @param studentUuid The student ID
	 * @param subjectUuid The subject ID
	 * @param streamUuid The stream ID
	 * @param classUuid The class ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link Performance} exist in the RDMS
	 */
	public boolean recordExist(String studentUuid,String subjectUuid,String streamUuid,String classUuid,String term,String year);
    /**
     *  
     * @param performance The {@link Performance} Object
     * @param studentUuid The student ID
     * @param subjectUuid The subject ID
     * @param streamUuid The stream ID
     * @param classUuid The class ID
     * @param term The term
     * @param year The year
     * @return Whether {@link Performance} was inserted successfully 
     */
	public boolean putExam(Performance performance,String studentUuid,String subjectUuid,String streamUuid,String classUuid,String term,String year);
}
