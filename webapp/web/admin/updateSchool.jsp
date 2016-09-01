
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

        <form class="form-horizontal" action="updateSchool" method="POST"  >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="schoolname">Schoolname</label>
                        <div class="controls">
                            <input class="input-xlarge"   name="schoolname" type="text" value="<%=request.getParameter("schoolname")%>">
                        </div>
                    </div>
                        
                    <div class="control-group">
                        <label class="control-label" for="username">Username</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="username" type="text" value="<%=request.getParameter("username")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="email" type="text" value="<%=request.getParameter("email")%>">
                        </div>
                    </div>

                    
                    <div class="control-group">
                        <label class="control-label" for="phone">Phone</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="phone" type="text" value="<%=request.getParameter("phone")%>">
                        </div>
                    </div>

                    


                    <div class="control-group">
                        <label class="control-label" for="address">Postal Address</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="address" type="text" value="<%=request.getParameter("address")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="hometown">Home Town*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="hometown" type="text" value="<%=request.getParameter("hometown")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="county">County*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="county" type="text" value="<%=request.getParameter("county")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="motto">Motto*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="motto" type="text" value="<%=request.getParameter("motto")%>">
                        </div>
                    </div>

                   
                    <div class="form-actions">
                        <input type="hidden" name="accountuuid" value="<%=request.getParameter("accountuuid")%>">
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>

                </fieldset>
            </form>

        


       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

