<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.persistence.exam.GradingSystemDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.exam.GradingSystem"%>

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


         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);

         GradingSystemDAO gradingSystemDAO = GradingSystemDAO.getInstance(); 
         GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(schoolId);





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
     <li> <a href="newStaff.jsp">New Staff</a>  <span class="divider">/</span> </li>
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">


                                 <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(SessionConstants.SCHOOL_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.SCHOOL_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.SCHOOL_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.SCHOOL_SUCCESS,null);
                                  } 


                                 %>


       <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                       
                        <th>Term</th>
                        <th>Year</th> 
                        <th>SMS send</th>
                        <th>Examcode</th>
                        <th>Next Term Openning Date</th>
                        <th>Closing Date</th>
                        <th>Action</th>
                       
                    </tr>
                </thead>   
                <tbody>
                    
                    <tr>
                         <td class="center"><%=systemConfig.getTerm() %></td>
                         <td class="center"><%=systemConfig.getYear() %></td>
                         <td class="center"><%=systemConfig.getSmsSend() %></td>
                         <td class="center"><%=systemConfig.getExamcode() %></td> 
                         <td class="center"><%=systemConfig.getOpenningDate() %></td>   
                         <td class="center"><%=systemConfig.getClosingDate()%></td>  
                         <td class="center">
                                <form name="edit" method="POST" action="updateSystemConfig.jsp" > 
                                <input type="hidden" name="schoolUuid" value="<%=schoolId%>">
                                <input type="hidden" name="term" value="<%=systemConfig.getTerm()%>">
                                <input type="hidden" name="year" value="<%=systemConfig.getYear()%>">
                                <input type="hidden" name="smssend" value="<%=systemConfig.getSmsSend()%>">
                                <input type="hidden" name="examcode" value="<%=systemConfig.getExamcode()%>">
                                <input type="hidden" name="openningdate" value="<%=systemConfig.getOpenningDate()%>">
                                <input type="hidden" name="closingdate" value="<%=systemConfig.getClosingDate()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  
                        
                    </tr>

                </tbody>
            </table> 




             <h3><i class="icon-edit"></i> Grading Scale:</h3>  
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                       
                        <th>A</th>
                        <th>A-</th> 
                        <th>B+</th>                
                        <th>B</th>
                        <th>B-</th>
                        <th>C+</th>
                        <th>C</th>
                        <th>C-</th>
                        <th>D+</th>
                        <th>D</th>
                        <th>D-</th>
                        <th>E</th>
                        <th>Action</th>
                    
                    </tr>
                </thead>   
                <tbody>
                    
                    <tr>
                         <td class="center"> 100 - <%=gradingSystem.getGradeAplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeAplain()-1%> - <%=gradingSystem.getGradeAminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeAminus()-1%> - <%=gradingSystem.getGradeBplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeBplus()-1%> - <%=gradingSystem.getGradeBplain()%> </td>  
                         <td class="center"> <%=gradingSystem.getGradeBplain()-1%> - <%=gradingSystem.getGradeBminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeBminus()-1%> - <%=gradingSystem.getGradeCplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeCplus()-1%> - <%=gradingSystem.getGradeCplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeCplain()-1%> - <%=gradingSystem.getGradeCminus()%> </td>  
                         <td class="center"> <%=gradingSystem.getGradeCminus()-1%> - <%=gradingSystem.getGradeDplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDplus()-1%> - <%=gradingSystem.getGradeDplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDplain()-1%> - <%=gradingSystem.getGradeDminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDminus()-1%> - <%=gradingSystem.getGradeE()%> </td>  
                         <td class="center">
                                <form name="edit" method="POST" action="updateGradingScale.jsp" > 
                                <input type="hidden" name="schoolUuid" value="<%=gradingSystem.getAccountUuid()%>">
                                <input type="hidden" name="A" value="<%=gradingSystem.getGradeAplain()%>">
                                <input type="hidden" name="Am" value="<%=gradingSystem.getGradeAminus()%>">
                                <input type="hidden" name="Bp" value="<%=gradingSystem.getGradeBplus()%>">
                                <input type="hidden" name="B" value="<%=gradingSystem.getGradeBplain()%>">
                                <input type="hidden" name="Bm" value="<%=gradingSystem.getGradeBminus()%>">
                                <input type="hidden" name="Cp" value="<%=gradingSystem.getGradeCplus()%>">
                                <input type="hidden" name="C" value="<%=gradingSystem.getGradeCplain()%>">
                                <input type="hidden" name="Cm" value="<%=gradingSystem.getGradeCminus()%>">
                                <input type="hidden" name="Dp" value="<%=gradingSystem.getGradeDplus()%>">
                                <input type="hidden" name="D" value="<%=gradingSystem.getGradeDplain()%>">
                                <input type="hidden" name="Dm" value="<%=gradingSystem.getGradeDminus()%>">
                                <input type="hidden" name="E" value="<%=gradingSystem.getGradeE()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  

                    </tr>

                </tbody>
            </table>  

        




    </div>

</div>


<jsp:include page="footer.jsp" />
