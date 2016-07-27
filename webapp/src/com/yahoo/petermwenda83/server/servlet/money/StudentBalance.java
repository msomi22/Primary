/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.money;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;

/**
 * @author peter
 *
 */
public class StudentBalance {

	SimpleDateFormat yearformatter;
	ExamConfig termConfig;
	String admYear;
	List<StudentFee> studentFeeList;
	List<StudentOtherMonies> othermoneyList;
	TermFee termFee;
	String [] terms;
	String currentYear;
	String currentTerm;

	/** find the student balance between (admission year and term) - (current year and term)
	 * 
	 */
	public StudentBalance() {
		yearformatter = new SimpleDateFormat("yyyy");
		termConfig = new ExamConfig();
		admYear = "";
		studentFeeList = new ArrayList<>();
		othermoneyList = new ArrayList<>();
		termFee = new TermFee();
		terms = new String [] {"1","2","3"};
		currentYear = "";

	}

	public double findBalance(TermFeeDAO termFeeDAO, ExamConfigDAO examConfigDAO, StudentFeeDAO studentFeeDAO,
			StudentOtherMoniesDAO studentOtherMoniesDAO, Date admissiondate, String studentRegTerm, String studentuuid, String schooluuid,int finalYear) {
		
		double balance = 0;
		double amountPaid = 0;
		double otherPaid = 0;
		admYear = yearformatter.format(admissiondate);
		termConfig = examConfigDAO.getExamConfig(schooluuid);
		termFee = termFeeDAO.getFee(schooluuid,termConfig.getTerm(), termConfig.getYear());
		currentYear = termConfig.getYear();
		currentTerm = termConfig.getTerm();

		int admYr = Integer.parseInt(admYear);
		int admTm = Integer.parseInt(studentRegTerm);
		int crrntYr = Integer.parseInt(currentYear);
		//int finalYr = 0;
		//finalYr = Integer.parseInt(admYear) + 3; 
		
		if(admYr == admYr){
		     if(admTm == 2){   
		    	 terms = new String [] {"2","3"}; 
		     }else if(admTm == 3){  
		    	 terms = new String [] {"3"};
		     }else  if(admTm == 1){
		    	 terms = new String [] {"1","2","3"}; 
		     }
		}

		while(admYr <= crrntYr && crrntYr <= finalYear){ 
			//start from admission year
			String year = Integer.toString(admYr);
			String term = "";
			for(int i=0;i<terms.length;i++){
				term = terms[i];
				
				//start finding the balance here
				studentFeeList = studentFeeDAO.getStudentFeeByStudentUuidList(schooluuid, studentuuid, term, year);
				othermoneyList = studentOtherMoniesDAO.getStudentOtherList(studentuuid, term, year);
				TermFee admTermFee = new TermFee();
				admTermFee = termFeeDAO.getFee(schooluuid,term, year);
				
				amountPaid = 0;
				otherPaid = 0;
				
				for(StudentFee studentFee :studentFeeList){
					amountPaid +=studentFee.getAmountPaid();
				}

				for(StudentOtherMonies otherMoney :othermoneyList){
					otherPaid += otherMoney.getAmountPiad();
				}
				
				
                  
				balance += (admTermFee.getTermAmount() + otherPaid) - amountPaid;
				
				if(admYr == crrntYr && crrntYr <= finalYear){
					if(StringUtils.equals(term, currentTerm)){
						break;
					}
				}

			}
			
			terms = new String [] {"1","2","3"}; 
			admYr +=1;
		}

		return balance;
	}

}
