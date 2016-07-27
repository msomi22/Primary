

/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.persistence.exam;

import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public interface SchoolPerfomanceDAO {
	
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	 
	public List<Perfomance> getPerformance(String schoolAccountUuid,String classRoomUuid,String studentUuid,String Term,String Year); 
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @param studentUuid
	 * @param Term
	 * @param Year
	 * @return
	 */
	public List<Perfomance> getPerformanceGeneral(String schoolAccountUuid,String ClassesUuid,String studentUuid,String Term,String Year); 

	   /**
	    * 
	    * @param perfomance
	    * @return
	    */
	public boolean deletePerfomance(Perfomance perfomance);
	   /**
	    * 
	    * @param schoolAccountUuid
	    * @return
	    */
	public List<Perfomance> getPerfomanceList(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<Perfomance> getPerfomanceListDistinct(String schoolAccountUuid,String classRoomUuid,String Term,String Year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param classRoomUuid
	 * @return
	 */
	public List<Perfomance> getPerfomanceListDistinctGeneral(String schoolAccountUuid,String ClassesUuid,String Term,String Year);
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param ClassesUuid
	 * @return
	 */
	public List<Perfomance> getClassPerfomanceListGeneral(String schoolAccountUuid,String ClassesUuid,String Term,String Year);
	
	
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param ClassesUuid
	 * @return
	 */
	public List<Perfomance> getAllclassesPerformance(String schoolAccountUuid,String Term,String Year);
	
	
}
