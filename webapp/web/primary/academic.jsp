<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.ClassroomDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Classroom"%>

<%@page import="ke.co.fastech.primaryschool.persistence.student.StudentDAO"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
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

         StudentDAO studentDAO = StudentDAO.getInstance(); 
         StreamDAO streamDAO = StreamDAO.getInstance();
         ClassroomDAO classroomDAO = ClassroomDAO.getInstance();
         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);


         List<Stream> streamList = new ArrayList<Stream>();  
         List<Classroom> classroomList = new ArrayList<Classroom>();  

         streamList = streamDAO.getStreamList(schoolId); 
         classroomList = classroomDAO.getClassroomList();

         if (session == null) {
            response.sendRedirect("../index.jsp");
           }

       if (StringUtils.isEmpty(schoolUsername)) {
          response.sendRedirect("../index.jsp");
        }

      session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
      response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../logout");

      final String STATUS = "B2733AB2-9CDB-4361-89C5-86B175A2E828"; 

       

%> 


<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">

   <li> <b> WELCOME TO   <b> <%=school.getSchoolName()%>  : TERM  : <%=systemConfig.getTerm()%>  YEAR :  <%=systemConfig.getYear()%> </li> <br>
     <li> <a href="schoolIndex.jsp">Back</a>  <span class="divider">/</span> </li>
     <li> <a href="examUpload.jsp">Upload Exam</a>  <span class="divider">/</span> </li>
     <li> <a href="classTeacher.jsp">Class Teachers</a>  <span class="divider">/</span> </li>
     <li> <a href="newStream.jsp">New Class</a>  <span class="divider">/</span> </li>
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">

                          <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(SessionConstants.STAFF_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STAFF_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_SUCCESS,null);
                                  } 


                                 %>



            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Class</th>
                        <th>Students</th>
                        <th>Performance List </th>
                        <th>Report Cards </th>
                        <th>Mark Sheet </th>
                        <th>Update</th> 
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                           int count = 1;
                           if(streamList !=null){
                            for(Stream stm : streamList) {
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + stm.getStreamName()  + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + studentDAO.getStudentCountPerClass(STATUS,stm.getUuid(),schoolId)  + "</td>");   
                             
                               %> 
                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="streamPerformanceList" target="_blank"> 
                                <input type="hidden" name="streamuuid" value="<%=stm.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Performance List" /> 
                                </form>                          
                                </td>  


                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="reportCard" target="_blank"> 
                                <input type="hidden" name="streamuuid" value="<%=stm.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Report Cards" /> 
                                </form>                          
                                </td>  

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="examList" target="_blank"> 
                                <input type="hidden" name="streamuuid" value="<%=stm.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Mark Sheet" /> 
                                </form>                          
                                </td> 

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="updateStream.jsp"> 
                                <input type="hidden" name="streamname" value="<%=stm.getStreamName()%>">
                                <input type="hidden" name="streamuuid" value="<%=stm.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                                </td>  

                               <%
                                count++;
                              }
                          } 
                         %>
                    
                    </tbody>
            </table> 
            
            </div>






            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>STD</th>
                        <th>Performance List </th>
                        <th>Action </th>
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                           int count2 = 1;
                           if(classroomList !=null){
                            for(Classroom cls : classroomList) {
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count2 + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + cls.getClassName()  + "</td>"); 
                             
                               %> 
                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="classPerformanceList" target="_blank"> 
                                <input type="hidden" name="classmuuid" value="<%=cls.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Performance List" /> 
                                </form>                          
                                </td>  

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="mostImproved" target="_blank"> 
                                <input type="hidden" name="classmuuid" value="<%=cls.getUuid()%>">
                                <input type="hidden" name="accountuuid" value="<%=schoolId%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Most Improved" /> 
                                </form>                          
                                </td>  

                               <%
                                count2++;
                              }
                          } 
                         %>
                    
                    </tbody>
            </table> 
            
            </div>
        

    </div>

</div>


<jsp:include page="footer.jsp" />
