/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import ke.co.fastech.primaryschool.bean.exam.MeanScore;

/**
 * Persistence description for {@link MeanScore}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolMeanScoreDAO {
	
	/**
	 * 
	 * @param studentUuid
	 * @param year
	 * @return
	 */
	public MeanScore getMeanScore(String studentUuid,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return The {@link MeanScore} Object 
	 */
	public MeanScore getMeanScore(String studentUuid,String term,String year);
	/**
	 * 
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link MeanScore} object exist 
	 */
	public boolean meanExist(String studentUuid,String term,String year); 
	/**
	 * 
	 * @param mean The mean Score
	 * @param streamPosition The stream Position
	 * @param classPosition The class Position
	 * @param studentUuid The student ID
	 * @param term The term
	 * @param year The year
	 * @return Whether {@link MeanScore} was inserted successfully 
	 */
	 
	public boolean putMeanScore(double mean,String streamPosition,String classPosition,String studentUuid,String term,String year);

}
