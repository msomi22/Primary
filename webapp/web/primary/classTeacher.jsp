<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.StaffDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Staff"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.TeacherClassDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.ClassTeacher"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>


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


         StaffDAO staffDAO = StaffDAO.getInstance();
         TeacherClassDAO teacherClassDAO = TeacherClassDAO.getInstance();

         Map<String,String> staffNameMap = new HashMap<String,String>(); 
         List<Staff> staffList = new ArrayList<Staff>(); 
         staffList = staffDAO.getStaffList(schoolId); 
         for(Staff staff : staffList){
             staffNameMap.put(staff.getUuid(),staff.getName()); 
           }

         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);

         List<ClassTeacher> classteacherList = new ArrayList<ClassTeacher>(); 
         classteacherList = teacherClassDAO.getClassTeacherList(schoolId); 
           
          Map<String,String> streamMap = new HashMap<String,String>(); 
          StreamDAO streamDAO = StreamDAO.getInstance();
          List<Stream> streamList = new ArrayList<Stream>();  
          streamList = streamDAO.getStreamList(schoolId); 
          for(Stream stm : streamList) {
               streamMap.put(stm.getUuid(),stm.getStreamName());
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
     <li> <a href="academic.jsp">Back</a>  <span class="divider">/</span> </li>
     <li> <a href="newClassTeacher.jsp">New Class Teacher</a>  <span class="divider">/</span> </li>
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
                        <th>Teacher </th>
                        <th>Class </th>
                        <th>Class List</th>
                        <th>Remove</th>
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                        if(classteacherList !=null){
                       for(ClassTeacher clsst : classteacherList) { 

                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + staffNameMap.get(clsst.getTeacherUuid())  + "</td>"); 
                             out.println("<td width=\"5%\" class=\"center\">" + streamMap.get(clsst.getStreamUuid()) + "</td>");  
                            
                                  %>
                                <td class="center" width="5%">
                                <form name="claslist" method="POST" action=""> 
                                <input type="hidden" name="streamuuid" value="<%=clsst.getStreamUuid()%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Class List" /> 
                                </form>                          
                                </td> 

                                <td class="center" width="5%">
                                <form name="remove" method="POST" action="deleteClassTeacher"> 
                                <input type="hidden" name="teacheruuid" value="<%=clsst.getTeacherUuid()%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Remove" />  
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


        




    </div>

</div>


<jsp:include page="footer.jsp" />
