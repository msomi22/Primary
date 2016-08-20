/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.persistence.school;

import java.util.List;

import ke.co.fastech.primaryschool.bean.school.Comment;

/**
 * Persistence description for {@link Comment}
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolCommentDAO {
   
	/**
	 * 
	 * @param accountUuid
	 * @return
	 */
	 
	public Comment getComment(String accountUuid);
	/**
	 * 
	 * @param comment
	 * @return
	 */
	public boolean putComment(Comment comment);
	/**
	 * 
	 * @param comment
	 * @return
	 */
	public boolean updateComment(Comment comment);
	/**
	 * 
	 * @return
	 */
	public List<Comment> getCommentList();
}
