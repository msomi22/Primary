

<%@page import="ke.co.fastech.primaryschool.pagination.StudentPaginator"%>
<%@page import="ke.co.fastech.primaryschool.pagination.StudentPage"%>

<%@page import="ke.co.fastech.primaryschool.persistence.student.StudentDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.student.Student"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.server.servlet.util.PropertiesConfig"%>

<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="ke.co.fastech.primaryschool.server.servlet.school.exam.ExamConstants"%>

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

<%@page import="org.joda.time.MutableDateTime"%>

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
     SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();


     List<Student> studentList = new ArrayList<Student>();  
     studentList = studentDAO.getStudentsListByLimit(0 , 15,schoolId);  

    
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");


     int ussdCount = 0;
     StudentPaginator paginator = new StudentPaginator(schoolId);
     StudentPage studentpage;

     studentpage = (StudentPage) session.getAttribute("currentPage");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (studentpage == null
                || !StringUtils.endsWith(referrer, "schoolIndex.jsp")
                || StringUtils.equalsIgnoreCase(pageParam, "first")) {
              studentpage = paginator.getFirstPage();

            //We are to give the last page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
             studentpage = paginator.getLastPage();

            // We are to give the previous page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
            studentpage = paginator.getPrevPage(studentpage);

            // We are to give the next page 
        } else if (StringUtils.equalsIgnoreCase(pageParam, "next"))  {
           studentpage = paginator.getNextPage(studentpage);
        }

        session.setAttribute("currentPage", studentpage);
        studentList = studentpage.getContents();
        ussdCount = (studentpage.getPageNum() - 1) * studentpage.getPagesize() + 1;


        //get student stream
        Map<String,String> streamMap = new HashMap<String,String>(); 
        List<Stream> streamList = new ArrayList<Stream>();  
        streamList = streamDAO.getStreamList(schoolId); 
        for(Stream strm : streamList){
            streamMap.put(strm.getUuid(),strm.getStreamName()); 
             }


        SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);


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
    </ul>
</div>

<div class="row-fluid sortable">    

    <div class="box span12">
        <div class="box-content">

                             <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS,null);
                                  } 


                                 %>


                <div id="search_box">
                <form action="#" method="get">
                 <input type="text" placeholder="Search By AdmNo" name="q" size="10" id="searchfield" title="searchfield" onkeyup="searchstudents(this.value)" />
                </form>
                </div>















        <p> Day student count: <%=studentDAO.getStudentCountByCategory(ExamConstants.STUDENT_CAT_DAY, ExamConstants.STATUS_ACTIVE, schoolId)%>   |
            Boarding student count: <%=studentDAO.getStudentCountByCategory(ExamConstants.STUDENT_CAT_BOARDER, ExamConstants.STATUS_ACTIVE, schoolId)%>  |
            Lower student count: <%=studentDAO.getStudentCountByLevel(ExamConstants.STUDENT_LEVEL_LOWWR, ExamConstants.STATUS_ACTIVE, schoolId)%>   |
            Upper student count: <%=studentDAO.getStudentCountByLevel(ExamConstants.STUDENT_LEVEL_UPPER, ExamConstants.STATUS_ACTIVE, schoolId)%> |
            TOTAL students: <%=studentDAO.getStudentCount(ExamConstants.STATUS_ACTIVE,schoolId)%> </p> 

            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Adm No</th>
                        <th>Firstname</th>  
                        <th>Middlename</th>  
                        <th>Lastname</th>                 
                        <th>Gender</th>
                        <th>DOB</th>
                        <th>Bcert</th>
                        <th>Category</th> 
                        <th>Level</th> 
                        <th>Class</th> 
                        <th>Action</th> 
                    </tr>
                </thead>   
                <tbody class='tablebody'>
                    <%

                    String firstnameLowecase = "";
                    String middlenameLowecase ="";
                    String lastnameLowecase ="";

                    if(studentList !=null){
                    for(Student student : studentList){
                       
                        firstnameLowecase = StringUtils.capitalize(student.getFirstname().toLowerCase());
                        middlenameLowecase = StringUtils.capitalize(student.getMiddlename().toLowerCase());
                        lastnameLowecase = StringUtils.capitalize(student.getLastname().toLowerCase());
                         
                        %>

                         <tr class="tabledit">
                         <td width="3%"><%=ussdCount%></td>
                         <td class="center"><%=student.getAdmmissinNo()%></td> 
                         <td class="center"><%=firstnameLowecase%></td>
                         <td class="center"><%=middlenameLowecase%></td>
                         <td class="center"><%=lastnameLowecase%></td>
                         <td class="center"><%=student.getGender()%></td>
                         <td class="center"><%=student.getDateofbirth()%></td>
                         <td class="center"><%=student.getBirthcertificateNo()%></td>
                         <td class="center"><%=student.getStudentType()%></td>
                         <td class="center"><%=student.getStudentLevel()%></td> 
                         <td class="center"><%=streamMap.get(student.getStreamUuid())%></td> 
                         <td class="center">
                                <form name="Update" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admissionNumber" value="<%=student.getAdmmissinNo()%>">
                                <input type="hidden" name="firstname" value="<%=firstnameLowecase%>">
                                <input type="hidden" name="middlename" value="<%=middlenameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastnameLowecase%>">
                                <input type="hidden" name="studentUuid" value="<%=student.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="Update" id="Update" value="Update"/> 
                                </form>                          
                          </td>      
                         </tr>

                        <%
                          ussdCount++;
                       }
                   }
                            
                    %>
                </tbody>
            </table>  

             <div id="pagination">
                <form name="pageForm" method="post" action="schoolIndex.jsp">                                
                    <%                                            
                        if (!studentpage.isFirstPage()) {
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="First" />
                        <input class="toolbarBtn" type="submit" name="page" value="Previous" />
                    <%
                        }
                    %>
                    <span class="pageInfo">Page 
                        <span class="pagePosition currentPage"><%= studentpage.getPageNum()%></span> of 
                        <span class="pagePosition"><%= studentpage.getTotalPage()%></span>
                    </span>   
                    <%
                        if (!studentpage.isLastPage()) {                        
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="Next">  
                        <input class="toolbarBtn" type="submit" name="page" value="Last">
                    <%
                       }
                    %>                                
                </form>
            </div>

      
    </div><!--/span-->

</div><!--/row-->


<jsp:include page="footer.jsp" />
