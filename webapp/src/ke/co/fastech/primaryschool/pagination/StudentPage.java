/**
 * Primary School Management system
 *  This software is a product of FasTech Solutions Ltd
 *  You can no copy,distribute,sell or use this software.
 *  please read the license
 */
package ke.co.fastech.primaryschool.pagination;

import java.util.ArrayList;
import java.util.List;
import ke.co.fastech.primaryschool.bean.student.Student;

/**
 * For student pagination 
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentPage {
	
	private int pageNum;
	private int totalPage;
	private int pagesize;
    private List<Student> contents;

	/**
	 * 
	 */
	public StudentPage() {
		pageNum = 1;
		totalPage = 1;
		pagesize = 1;
		contents = new ArrayList<>();
	}
   
	
	public StudentPage(final int pageNum, final int totalPage, final int pagesize,
			final List<Student> contents) {
		this.pageNum = pageNum;
		this.totalPage = totalPage;
		this.pagesize = pagesize;
		this.contents = contents;
	}
	
	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the pagesize
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * @return the contents
	 */
	public List<Student> getContents() {
		return new ArrayList<>(contents);
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<Student> contents) {
		this.contents = contents;
	}
	 
	

    /**
     *
     * @return		<code>true</code> if this is the first page; <code>false</code>
     * for otherwise
     */
	
	public boolean isFirstPage() {
	        return pageNum == 1;
	    }

	    /**
	     *
	     * @return		<code>true</code> if this is the last page; <code>false</code>
	     * for otherwise
	     */
	    public boolean isLastPage() {
	        return pageNum == totalPage;
	    }

}
