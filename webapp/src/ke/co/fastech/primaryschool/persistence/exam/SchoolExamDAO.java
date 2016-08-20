/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.exam;

import java.util.List;

import ke.co.fastech.primaryschool.bean.exam.Exam;

/**
 * Persistence description for {@link Exam}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolExamDAO {
	/**
	 * 
	 * @param uuid The ID
	 * @return {@link Exam} Object
	 */
	public Exam getExam(String uuid);
	/**
	 * 
	 * @param examName The exam name
	 * @return {@link Exam} Object
	 */
	public Exam getExamByExamname(String examName);
	/**
	 * 
	 * @param exam {@link Exam} Object
	 * @return Whether {@link Exam} was inserted successfully 
	 */
	public boolean putExam(Exam exam);
	/**
	 * 
	 * @param exam {@link Exam} Object
	 * @return  Whether {@link Exam} was updated successfully 
	 */
	public boolean updateExam(Exam exam);
	/**
	 * 
	 * @return {@link Exam} List
	 */
	public List<Exam> getExamList();

}
