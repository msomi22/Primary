/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result.sms;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yahoo.petermwenda83.bean.schoolaccount.SmsSend;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.sms.send.AfricasTalkingGateway;

/**
 * @author peter
 *
 */
public class SendResultSMS {
	

	/**
	 * 
	 */
	public SendResultSMS() {
		
	}

	/**
	 * @param smsSendDAO
	 * @param studentDAO
	 * @param parentsDAO
	 * @param studentUuid
	 * @param schooluuid
	 * @param engscorestr
	 * @param kswscorestr
	 * @param matscorestr
	 * @param physcorestr
	 * @param bioscorestr
	 * @param chemscorestr
	 * @param bsscorestr
	 * @param comscorestr
	 * @param hscscorestr
	 * @param agriscorestr
	 * @param geoscorestr
	 * @param crescorestr
	 * @param histscorestr
	 * @param Studentmean
	 */
	public void sendToParents(SmsSendDAO smsSendDAO, StudentDAO studentDAO, ParentsDAO parentsDAO, String studentUuid,String schooluuid,
			String engscorestr, String kswscorestr, String matscorestr,String physcorestr,
			String bioscorestr, String chemscorestr, String bsscorestr, String comscorestr,
			String hscscorestr, String agriscorestr, String geoscorestr, String crescorestr, String histscorestr,
			double Studentmean) {
		    Student student = new Student();
		    
		    String studentname = "";
		    String message = "";
		    DecimalFormat rf = new DecimalFormat("0.0"); 
			rf.setRoundingMode(RoundingMode.HALF_UP);

		    
		    
		    StudentParent studentParent = new StudentParent();
		    if(studentDAO.getStudentByuuid(schooluuid, studentUuid) !=null){
	     	student = studentDAO.getStudentByuuid(schooluuid, studentUuid);
		    }
		    if(parentsDAO.getParent(studentUuid) !=null){
	     	studentParent = parentsDAO.getParent(studentUuid);
		    }
		    
		    String formatedFirstname = StringUtils.capitalize(student.getFirstname().toLowerCase());
		    String formatedLastname = StringUtils.capitalize(student.getLastname().toLowerCase());
		    String formatedsurname = StringUtils.capitalize(student.getSurname().toLowerCase());
		    
		    formatedFirstname = formatedFirstname.substring(0, Math.min(formatedFirstname.length(), 10));
			formatedLastname = formatedLastname.substring(0, Math.min(formatedLastname.length(), 10));
			formatedsurname = formatedsurname.substring(0, Math.min(formatedsurname.length(), 10));
		     
		    studentname = formatedFirstname + " " + formatedLastname + " " + formatedsurname;
		    
		
		    message = studentname +",ENG "+engscorestr+",KIS "+kswscorestr+",MAT "+matscorestr+
		    		",PHY "+physcorestr+",BIO "+bioscorestr+",CHE "+chemscorestr+",B/S "+bsscorestr+
		    		",CMP "+comscorestr+",HSC "+hscscorestr+",AGR "+agriscorestr+",GEO "+geoscorestr+
		    		",CRE "+crescorestr+",HST "+histscorestr + ",MEAN "+ rf.format(Studentmean);
		    
		  // System.out.println(message);
		    
		    String phone = "";
			String formatedphone = "";
			String realphone = "";
			
			
		    if(parentsDAO.getParent(studentUuid) !=null){
				studentParent = parentsDAO.getParent(studentUuid);
				//parentname = StringUtils.capitalize(studentParent.getFathername().toLowerCase());
				phone = studentParent.getFatherphone();
				formatedphone = phone.replaceFirst("^0+(?!$)", "");
				realphone = "+254"+formatedphone;
			}
		    
		  
			AfricasTalking africasTalking = new AfricasTalking();
			String username = africasTalking.getUsername();
			String apiKey   = africasTalking.getApiKey();
						
			africasTalking.setMessage(message); 
			africasTalking.setRecipients(realphone); 
			// Create a new instance of our awesome gateway class
			AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
			
			//save to database
			String thestatus ="";
			String thenumber ="";
			String themessage ="";
			String thecost ="";

			SmsSend smsSend = new SmsSend();
			smsSend.setStatus("failed");
			smsSend.setPhoneNo(realphone);
			smsSend.setMessageId(message.replaceAll("[\r\n]+", " "));
			smsSend.setCost("1");
			smsSendDAO.putSmsSend(smsSend);
			System.out.println("smsSend = "+smsSend);


			try {
				JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
				for( int i = 0; i < results.length(); ++i ) {
					JSONObject result = results.getJSONObject(i);

					thestatus = result.getString("status");
					thenumber = result.getString("number");
					themessage = message;
					thecost = result.getString("cost");

					SmsSend smsSend2 = smsSendDAO.getSmsSend(smsSend.getUuid());
					smsSend2.setStatus(thestatus);
					smsSend2.setPhoneNo(thenumber);
					smsSend2.setMessageId(themessage.replaceAll("[\r\n]+", " "));
					smsSend2.setCost(thecost);
					smsSendDAO.updateSmsSend(smsSend2); 
					//System.out.println("smsSend2 = "+smsSend2);

				} 

			}

			catch (Exception e) {
				e.printStackTrace(); 
			}
		
		
		
	}
	


}
