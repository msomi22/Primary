
<%@page import="ke.co.fastech.primaryschool.server.session.AdminSessionConstants"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.category.CategoryDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Category"%>

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
  
         CategoryDAO categoryDAO = CategoryDAO.getInstance();
         List<Category> catList = new ArrayList<Category>(); 
         catList = categoryDAO.getCategoryList();

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
          <p> <a href="adminIndex.jsp">Back</a> </p> 
        </div>
       
        <div class="box-content">

        <%
            HashMap<String,String> paramHash = (HashMap<String,String>) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }


        %>
          
            
                  <form  class="form-horizontal"   action="addHeadTeacher" method="POST" >
                    <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="SchoolAccount">Account:</label>
                         <div class="controls">
                            <select name="accountUuid" >
                                <option value="">Please select one</option> 
                               <%
                                    int acount = 1;
                                    if (schoolList != null) {
                                        for (Account ac : schoolList) {
                                %>
                                <option value="<%= ac.getUuid()%>"><%=ac.getSchoolName()%></option>
                                <%
                                            acount++;
                                        }
                                    }
                                    %>
                                
                            </select>                           
                          
                        </div>
                    </div> 

                   <div class="control-group" id="divid">
                        <label class="control-label" for="Category">Category*</label>
                        <div class="controls">
                            <select name="categoryuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (catList != null) {
                                        for (Category cat : catList) {
                                             if(StringUtils.equals(cat.getCategoryName(), "Principal")){   
                                %>
                                <option value="<%=cat.getUuid()%>"><%=cat.getCategoryName()%></option>
                                <%
                                            count++;
                                            }
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="employeeNo">Employee Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 



                    <div class="control-group">
                        <label class="control-label" for="username">Username*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="username" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("username")) %>"  >

                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="password">Password*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="password" name="password" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("password")) %>"  >

                        </div>
                    </div>  

                   


                     <div class="control-group">
                        <label class="control-label" for="staffname">Staff Name*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="staffname" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("staffname")) %>" style="text-transform: capitalize;"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="phone">Phone No*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="email"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("email")) %>"  >
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="gender">Gender*:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 



                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div> 

              </fieldset>
              </form>
       

        

       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

