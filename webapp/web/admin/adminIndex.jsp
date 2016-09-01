
<%@page import="ke.co.fastech.primaryschool.server.session.AdminSessionConstants"%>

<%@page import="ke.co.fastech.primaryschool.persistence.account.AcountDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

     AcountDAO acountDAO = AcountDAO.getInstance();
     List<Account> schoolList = new ArrayList<Account>(); 
     schoolList = acountDAO.getAllAccounts(); 

     if (session == null) {
        response.sendRedirect("index.jsp");
    }

    String username = (String) session.getAttribute(AdminSessionConstants.ADMIN_SESSION_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("index.jsp");
    }

     session.setMaxInactiveInterval(AdminSessionConstants.SESSION_TIMEOUT);
     response.setHeader("Refresh", AdminSessionConstants.SESSION_TIMEOUT + "; url=logout");
      






%>

<body>

 <jsp:include page="header.jsp" />

<div class="row-fluid sortable">    
    <div class="box span12">
    <div class="box-header well" data-original-title>
          <p> <a href="newSchool.jsp">New School</a> </p> 
        </div>
       
        <div class="box-content">

                                  <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR);
                                     updateSuccess = (String) session.getAttribute(AdminSessionConstants.ACCOUNT_ADD_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.ACCOUNT_ADD_SUCCESS,null);
                                  } 


                                 %>

        
               <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Susername</th>
                        <th>schoolName</th> 
                        <th>Email</th> 
                        <th>Phone</th>                
                        <th>Addres</th>
                        <th>Home Town</th>
                        <th>County</th>
                        <th>Motto</th>
                        <th>dayBoarding</th>
                        <th>Actions</th>

                    </tr>
                </thead>   
                <tbody>
                    <%                                                          
                      int count = 1;
                         for (Account acc : schoolList) {
                          
                              %>
                    <tr>
                        <td width="3%"><%=count%></td>
                         <td class="center"><%=acc.getUsername()%></td> 
                         <td class="center"><%=acc.getSchoolName()%></td>
                         <td class="center"><%=acc.getSchoolEmail()%></td>
                         <td class="center"><%=acc.getSchoolPhone()%></td>
                         <td class="center"><%=acc.getSchoolAddres()%></td>
                         <td class="center"><%=acc.getSchoolHomeTown()%></td>  
                         <td class="center"><%=acc.getSchoolCounty()%></td>
                         <td class="center"><%=acc.getSchoolMotto()%></td>  
                         <td class="center"><%=acc.getDayBoarding()%></td>  
                         <td class="center">
                                <form name="edit" method="POST" action="updateSchool.jsp"> 
                                <input type="hidden" name="username" value="<%=acc.getUsername()%>">
                                <input type="hidden" name="schoolname" value="<%=acc.getSchoolName()%>">
                                <input type="hidden" name="email" value="<%=acc.getSchoolEmail()%>">
                                <input type="hidden" name="phone" value="<%=acc.getSchoolPhone()%>">
                                <input type="hidden" name="address" value="<%=acc.getSchoolAddres()%>">
                                <input type="hidden" name="hometown" value="<%=acc.getSchoolHomeTown()%>">
                                <input type="hidden" name="county" value="<%=acc.getSchoolCounty()%>">
                                <input type="hidden" name="motto" value="<%=acc.getSchoolMotto()%>">
                                <input type="hidden" name="dayboarding" value="<%=acc.getDayBoarding()%>">
                                <input type="hidden" name="accountuuid" value="<%=acc.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                           
                        </td>   
                    </tr>

                    <%     
                           count++;
                            } 
                    %>
                </tbody>
            </table>  



       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

