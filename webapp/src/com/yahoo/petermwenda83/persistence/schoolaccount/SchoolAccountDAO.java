/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import java.util.List;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;

/**
 * @author peter
 *
 */
public interface SchoolAccountDAO {
	/**
	 * @param Uuid
	 * @return
	 */
	public SchoolAccount get(String Uuid);
	
	/**
	 * 
	 * @param Username
	 * @return the SchoolName
	 */
	public SchoolAccount getSchoolByUsername(String Username);
	/**
	 * 
	 * @param school
	 * @return
	 */
	public SchoolAccount getSchool(SchoolAccount school);
	/**
	 * 
	 * @param school
	 * @return whether SchoolAccount has been added successfully
	 */
    public boolean put(SchoolAccount school);
    /**
     * 
     * @param school
     * @return whether SchoolAccount has been updated successfully
     */
    public boolean update(SchoolAccount school);
    /**
     * 
     * @param school
     * @return whether SchoolAccount has been deleted successfully
     */
    public boolean delete(SchoolAccount school);
    /**
     * 
     * @return List of all schools 
     */
    public List<SchoolAccount> getAllSchools();
    
    

}
