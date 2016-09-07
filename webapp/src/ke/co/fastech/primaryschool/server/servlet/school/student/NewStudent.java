/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import ke.co.fastech.primaryschool.bean.school.SystemConfig;
import ke.co.fastech.primaryschool.bean.student.Student;
import ke.co.fastech.primaryschool.bean.student.StudentSubject;
import ke.co.fastech.primaryschool.bean.student.Subject;
import ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO;
import ke.co.fastech.primaryschool.persistence.student.StudentDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.StudentSubjectDAO;
import ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO;
import ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants;
import ke.co.fastech.primaryschool.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class NewStudent extends HttpServlet{

	final String ERROR_ADMNO_EXIST = "Admission number already exist in the system.";
	final String STUDENT_UPDATE_ERROR = "An error occured while adding student.";
	final String STUDENT_UPDATE_SUCCESS = "Student added successfully.";
	final String ERROR_EMPTY_ADM_NO = "Admission number can't be empty.";
	final String ERROR_EMPTY_MIDDLE_NAME = "Middlename can't be empty.";
	final String ERROR_EMPTY_FIRST_NAME = "Firstname can't be empty.";
	final String ERROR_EMPTY_LASTNAME = "Lastname can't be empty.";

	final String ERROR_EMPTY_STREAM = "Please select a class.";
	final String ERROR_EMPTY_GENDER = "Please select a gender.";
	final String ERROR_EMPTY_CAT = "Please select student category.";
	final String ERROR_EMPTY_LEVEL = "Please select student level.";

	private static StudentDAO studentDAO;
	private static SubjectDAO subjectDAO;
	private static SystemConfigDAO systemConfigDAO;
	private static StudentSubjectDAO studentSubjectDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentDAO = StudentDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		systemConfigDAO = SystemConfigDAO.getInstance();
		studentSubjectDAO = StudentSubjectDAO.getInstance();
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String admnumber = StringUtils.trimToEmpty(request.getParameter("admissionNumber"));
		String streamuuid = StringUtils.trimToEmpty(request.getParameter("classroomUuid"));
		String firstname = StringUtils.trimToEmpty(request.getParameter("firstname"));
		String middlename = StringUtils.trimToEmpty(request.getParameter("middlename"));
		String lastname = StringUtils.trimToEmpty(request.getParameter("lastname"));
		String gender = StringUtils.trimToEmpty(request.getParameter("gender"));

		String dobDay = StringUtils.trimToEmpty(request.getParameter("dobDay"));
		String dobMonth = StringUtils.trimToEmpty(request.getParameter("dobMonth"));
		String dobYear = StringUtils.trimToEmpty(request.getParameter("dobYear"));

		String category = StringUtils.trimToEmpty(request.getParameter("category"));
		String level = StringUtils.trimToEmpty(request.getParameter("level"));

		String schoolUuid = StringUtils.trimToEmpty(request.getParameter("schooluuid"));

		if(StringUtils.isBlank(admnumber)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_ADM_NO); 

		}else if(StringUtils.isBlank(streamuuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_STREAM); 

		}else if(StringUtils.isBlank(firstname)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_FIRST_NAME); 

		}else if(StringUtils.isBlank(middlename)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_MIDDLE_NAME); 

		}/*else if(StringUtils.isBlank(lastname)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_LASTNAME); 

		}*/else if(StringUtils.isBlank(gender)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_GENDER); 

		}else if(StringUtils.isBlank(category)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_CAT); 

		}else if(StringUtils.isBlank(level)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_GENDER); 

		}else if(StringUtils.isBlank(schoolUuid)){
			session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, ERROR_EMPTY_LEVEL); 

		}else{

			String bcertNo = " ";
			String country = "Kenya";
			String county = " ";
			String ward = " ";

			SystemConfig systemConfig = new SystemConfig();
			systemConfig = systemConfigDAO.getSystemConfig(schoolUuid);  

			Student student = new Student();
			student.setAccountUuid(schoolUuid);  
			student.setStatusUuid(ExamConstants.STATUS_ACTIVE); 
			student.setStreamUuid(streamuuid); 
			student.setAdmmissinNo(admnumber); 
			student.setFirstname(StringUtils.capitalize(firstname.toLowerCase()));
			student.setMiddlename(StringUtils.capitalize(middlename.toLowerCase()));
			student.setLastname(StringUtils.capitalize(lastname.toLowerCase())); 
			student.setGender(gender.toUpperCase()); 
			student.setDateofbirth(dobDay+"/"+dobMonth+"/"+dobYear); 
			student.setBirthcertificateNo(bcertNo);
			student.setCountry(country);
			student.setCounty(county);
			student.setWard(ward); 
			student.setRegTerm(Integer.parseInt(systemConfig.getTerm()));  
			student.setRegYear(Integer.parseInt(systemConfig.getYear()));  
			student.setFinalYear(Integer.parseInt((systemConfig.getYear()+3)));  
			student.setFinalTerm(3); 
			student.setStudentType(category);
			student.setStudentLevel(level); 

			if(studentDAO.putStudent(student)){ 
				

				if(studentDAO.getStudentByADMNO(admnumber,schoolUuid) == null){ 

					if(studentDAO.putStudent(student)){
						//assign subject
						List<Subject> subjectList = new ArrayList<>();
						subjectList = subjectDAO.getSubjectList();
						for(Subject sub : subjectList){
							StudentSubject sb = new StudentSubject(); 
							sb.setSubjectUuid(sub.getUuid()); 
							sb.setStudentUuid(student.getUuid());
							if(studentSubjectDAO.getStudentSubject(student.getUuid(), sub.getUuid()) == null){
								studentSubjectDAO.putStudentSubject(sb); 
							}
						}
						//subjects assigned

					}

				}
				
				
				session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, STUDENT_UPDATE_SUCCESS); 
			}else{
				session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, STUDENT_UPDATE_ERROR); 
			}

		}
		response.sendRedirect("newStudent.jsp");  
		return;
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873623657889649695L;


}
