/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import java.util.List;

import ke.co.fastech.primaryschool.bean.exam.ExamResult;

/**
 * Persistence description for {@link ExamResult}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolPerformanceDAO {
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param subjectUuid The subject ID
	 * @param streamUuid The stream ID
	 * @param term The term
	 * @param year The year
	 * @return A List of type {@link ExamResult}
	 */
	 
	public List<ExamResult> getStudentPerformanceByStreamId(String studentUuid,String subjectUuid,String streamUuid,String term,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param subjectUuid The subject ID
	 * @param classUuid The class ID
	 * @param term The term
	 * @param year The Year
	 * @return A List of type {@link ExamResult}
	 */
	 
	 
	public List<ExamResult> getStudentPerformanceByClassId(String studentUuid,String subjectUuid,String classUuid,String term,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param subjectUuid The subject ID
	 * @param streamUuid The stream ID
	 * @param term The term
	 * @param year The year
	 * @return A List of type {@link ExamResult}
	 */
    public List<ExamResult> getStudentDistinctByStreamId(String streamUuid,String term,String year);
    /**
     * 
     * @param studentUuid The student ID
     * @param subjectUuid The subject ID
     * @param classUuid The class ID
     * @param term The term 
     * @param year The year
     * @return A List of type {@link ExamResult}
     */
     
	public List<ExamResult> getStudentDistinctByClassId(String classUuid,String term,String year);
	/**
	 * 
	 * @param streamUuid The stream ID
	 * @param term The term
	 * @param year The year
	 * @return A List of type {@link ExamResult}
	 */
    public List<ExamResult> getStreamPerformance(String streamUuid,String term,String year);
    /**
     * 
     * @param classUuid The class ID
     * @param term The term
     * @param year The year
     * @return A List of type {@link ExamResult}
     */
	public List<ExamResult> getClassPerformance(String classUuid,String term,String year);

}
