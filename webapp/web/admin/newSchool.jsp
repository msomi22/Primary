
<%@page import="ke.co.fastech.primaryschool.server.session.AdminSessionConstants"%>


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

        <form  class="form-horizontal"   action="addSchool" method="POST" >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="schoolname">SchoolName*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="schoolname" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("schoolname")) %>"  >

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
                        <label class="control-label" for="phone">Phone*:</label>
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
                        <label class="control-label" for="dayBoarding">Day/Boarding status*:</label>
                         <div class="controls">
                            <select name="dayBoarding" >
                                <option value="">Please select one</option> 
                                <option value="NO">Day/Boarding Only</option>
                                <option value="YES">Both Day and Boarding</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="address">Address*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="address"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("address")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="hometown">Town*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="hometown"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("hometown")) %>"  >
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="motto">Motto*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="motto"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("motto")) %>"  >
                        </div>
                    </div> 
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>

              </fieldset>
              </form>

        



       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

