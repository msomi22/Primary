/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.exam;

import org.apache.commons.lang3.StringUtils;

/**
 * @author peter
 *
 */
public class ComputationEngine {

	/**
	 * @param subject
	 * @param openerScore
	 * @return
	 */
	public double computeOpener(String subject, double openerScore) {
		double engTotal=0,kisTotal=0,mathTotal=0,sciTotal=0,creTotal=0,sstTotal= 0; 
		double sscreScore = 0,totalMark =0 ;
		//opener logic
		if(StringUtils.equals(subject, ExamConstants.SUB_ENG)) {
			if(openerScore > ExamConstants.SUB_MAX){
				openerScore = 0;
			}
			engTotal = openerScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_KIS)) {
			if(openerScore > ExamConstants.SUB_MAX){
				openerScore = 0;
			}
			kisTotal = openerScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_MATH)) {
			if(openerScore > ExamConstants.SUB_MAX){
				openerScore = 0;
			}
			mathTotal = openerScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_SCI)) {
			if(openerScore > ExamConstants.SUB_MAX){
				openerScore = 0;
			}
			sciTotal = openerScore;//out of 100
			
		}
		if(true){
		if(StringUtils.equals(subject, ExamConstants.SUB_CRE)) {
			if(openerScore > ExamConstants.SUB_MAX_CRE){
				openerScore = 0;
			}
			creTotal = openerScore;//out of 30
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_SST)) {
			if(openerScore > ExamConstants.SUB_MAX_SS){
				openerScore = 0;
			}
			sstTotal = openerScore;//out of 60
			
		 }
		sscreScore = (((creTotal + sstTotal)/ExamConstants.SS_CRE_MAX)*100);
		}
		totalMark = (engTotal + kisTotal + mathTotal + sciTotal + sscreScore);
		return totalMark;
	}

	
	/**
	 * @param subject
	 * @param midtermScore
	 * @return
	 */
	public double computeMidterm(String subject, double midtermScore) {
		double engTotal=0,kisTotal=0,mathTotal=0,sciTotal=0,creTotal=0,sstTotal= 0; 
		double sscreScore = 0,totalMark =0 ;
		//mid term logic
		if(StringUtils.equals(subject, ExamConstants.SUB_ENG)) {
			if(midtermScore > ExamConstants.SUB_MAX){
				midtermScore = 0;
			}
			engTotal = midtermScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_KIS)) {
			if(midtermScore > ExamConstants.SUB_MAX){
				midtermScore = 0;
			}
			kisTotal = midtermScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_MATH)) {
			if(midtermScore > ExamConstants.SUB_MAX){
				midtermScore = 0;
			}
			mathTotal = midtermScore;//out of 100
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_SCI)) {
			if(midtermScore > ExamConstants.SUB_MAX){
				midtermScore = 0;
			}
			sciTotal = midtermScore;//out of 100
			
		}
		if(true){
		if(StringUtils.equals(subject, ExamConstants.SUB_CRE)) {
			if(midtermScore > ExamConstants.SUB_MAX_CRE){
				midtermScore = 0;
			}
			creTotal = midtermScore;//out of 30
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_SST)) {
			if(midtermScore > ExamConstants.SUB_MAX_SS){
				midtermScore = 0;
			}
			sstTotal = midtermScore;//out of 60
		}
		sscreScore = (((creTotal + sstTotal)/ExamConstants.SS_CRE_MAX)*100);
		}
		totalMark = (engTotal + kisTotal + mathTotal + sciTotal + sscreScore);
		return totalMark;
	}
	
	

	/**
	 * @param subject
	 * @param endtermScore
	 * @return
	 */
	public double computeEndterm(String subject, double endtermScore) {
		double engTotal=0,kisTotal=0,mathTotal=0,sciTotal=0,creTotal=0,sstTotal= 0; 
		double sscreScore = 0,totalMark =0 ;
		//end term logic
		if(StringUtils.equals(subject, ExamConstants.SUB_ENG)) {
			if(endtermScore > ExamConstants.SUB_MAX){
				endtermScore = 0;
			}
			engTotal = endtermScore;//out of 100
			
		}else if(StringUtils.equals(subject, ExamConstants.SUB_KIS)) {
			if(endtermScore > ExamConstants.SUB_MAX){
				endtermScore = 0;
			}
			kisTotal = endtermScore;//out of 100
			
		}else if(StringUtils.equals(subject, ExamConstants.SUB_MATH)) {
			if(endtermScore > ExamConstants.SUB_MAX){
				endtermScore = 0;
			}
			mathTotal = endtermScore;//out of 100
			
		}else if(StringUtils.equals(subject, ExamConstants.SUB_SCI)) {
			if(endtermScore > ExamConstants.SUB_MAX){
				endtermScore = 0;
			}
			sciTotal = endtermScore;//out of 100
			
		}
		if(true){
		if(StringUtils.equals(subject, ExamConstants.SUB_CRE)) {
			if(endtermScore > ExamConstants.SUB_MAX_CRE){
				endtermScore = 0;
			}
			creTotal = endtermScore;//out of 30
			
		}if(StringUtils.equals(subject, ExamConstants.SUB_SST)) {
			//System.out.println("{ " + endtermScore + " }");
			if(endtermScore > ExamConstants.SUB_MAX_SS){
				endtermScore = 0;
			}
             sstTotal = endtermScore;//out of 60
		}
		sscreScore = (((creTotal + sstTotal)/ExamConstants.SS_CRE_MAX)*100);
		}
		totalMark = (engTotal + kisTotal + mathTotal + sciTotal + sscreScore);
		return totalMark;
	}

	
	
	
}
