

<%@page import="ke.co.fastech.primaryschool.pagination.StudentPaginator"%>
<%@page import="ke.co.fastech.primaryschool.pagination.StudentPage"%>

<%@page import="ke.co.fastech.primaryschool.persistence.student.StudentDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.student.Student"%>

<%@page import="ke.co.fastech.primaryschool.server.servlet.util.PropertiesConfig"%>

<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

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

<%@page import="org.joda.time.MutableDateTime"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%


   
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList<Student>();  
     studentList = studentDAO.getStudentsListByLimit(0 , 15);  

    
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");


     int ussdCount = 0;
     StudentPaginator paginator = new StudentPaginator();
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



        String schoolId = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_ACCOUNTUUID); 

        CacheManager mgr = CacheManager.getInstance();
        Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID);

        Account school = new Account();
        Element element;
         if ((element = accountsCache.get(schoolId)) != null) {
            school = (Account) element.getObjectValue();
         }


        final String ACTIVE_STATUS = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
        final String DAY_STATE = "Day";
        final String BOARDER_STATE = "Boarder";

       /* int dayStudentCount = 0;
        int boardingStudentCount = 0;

        dayStudentCount = studentDAO.getStudentCount(ACTIVE_STATUS,DAY_STATE);
        boardingStudentCount = studentDAO.getStudentCount(ACTIVE_STATUS,BOARDER_STATE); */

       

%> 

<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">

    <li> <b> WELCOME TO   <b> <%=school.getSchoolName()%> </li> 
    </ul>
</div>

<div class="row-fluid sortable">    

    <div class="box span12">
        <div class="box-content">

        <p> Day student count: **   |  Boarding student count: **  |  Lower student count: **  | Upper student count:  ** </p> 

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
                    </tr>
                </thead>   
                <tbody >
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
