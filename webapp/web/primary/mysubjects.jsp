<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

<%@page import="ke.co.fastech.primaryschool.persistence.student.subject.SubjectDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.student.Subject"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.TeacherSubjectDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.TeacherSubject"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.StaffDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Staff"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

        String schoolUsername = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY);
        String schoolId = "";


        CacheManager mgr = CacheManager.getInstance();
        Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

        Account school = new Account();
        Element element;
         if ((element = accountsCache.get(schoolUsername)) != null) {
            school = (Account) element.getObjectValue();
         }
         
         schoolId = school.getUuid();

         StreamDAO streamDAO = StreamDAO.getInstance();
         SubjectDAO subjectDAO = SubjectDAO.getInstance();
         TeacherSubjectDAO teacherSubjectDAO = TeacherSubjectDAO.getInstance(); 

         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);

         Map<String,String> streamMap = new HashMap<String,String>(); 
         Map<String,String> subjectMap = new HashMap<String,String>(); 
         List<Stream> streamList = new ArrayList<Stream>();  
         streamList = streamDAO.getStreamList(schoolId); 

         List<Subject> subjectList = new ArrayList<Subject>();  
         subjectList = subjectDAO.getSubjectList();  
         for(Subject sub : subjectList){
            subjectMap.put(sub.getUuid(),sub.getSubjectName());
           }

           for(Stream strm : streamList){
            streamMap.put(strm.getUuid(),strm.getStreamName()); 
           }
        
         String teacherUuid = request.getParameter("teacherUuid");

          List<TeacherSubject> teacherSubjectList = new ArrayList<TeacherSubject>();  
          teacherSubjectList = teacherSubjectDAO.getTeacherSubjectList(teacherUuid);   

           StaffDAO staffDAO = StaffDAO.getInstance();
           Map<String,String> staffNameMap = new HashMap<String,String>(); 
           List<Staff> staffList = new ArrayList<Staff>(); 
           staffList = staffDAO.getStaffList(schoolId); 
           for(Staff staff : staffList){
             staffNameMap.put(staff.getUuid(),staff.getName()); 
           }


         
         
         
        if (session == null) {
            response.sendRedirect("../index.jsp");
           }

       if (StringUtils.isEmpty(schoolUsername)) {
          response.sendRedirect("../index.jsp");
        }

      session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
      response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../logout");


       

%> 


<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">

   <li> <b> WELCOME TO   <b> <%=school.getSchoolName()%>  : TERM  : <%=systemConfig.getTerm()%>  YEAR :  <%=systemConfig.getYear()%> </li> <br>
     <li> <a href="staff.jsp">Back</a>  <span class="divider">/</span> </li> 
    </ul>
</div>

<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-content">

                   <h3> <%=staffNameMap.get(teacherUuid)%>  </h3>
          
            
                  <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>ClassRoom</th>
                        <th>Subject </th>
                    </tr>
                </thead>   
                <tbody>
          
                    <%              
                             
                       int count = 1;
                       for(TeacherSubject ts : teacherSubjectList) {
                             out.println("<tr>");
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td class=\"center\">" + streamMap.get(ts.getStreamUuid()) + "</td>"); 
                             out.println("<td class=\"center\">" + subjectMap.get(ts.getSubjectUuid()) + "</td>");  
                             %>
                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action="deleteTeacherSubject"> 
                                <input type="hidden" name="streamuuid" value="<%=ts.getStreamUuid()%>">
                                <input type="hidden" name="subjectuuid" value="<%=ts.getSubjectUuid()%>">
                                <input type="hidden" name="teacheruuid" value="<%=teacherUuid%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Delete" />  
                                </form>                          
                                </td>  
                                <%
                               
                        count++;
                      } 
                    %>
                    
                    </tbody>
            </table> 
            
            </div>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
