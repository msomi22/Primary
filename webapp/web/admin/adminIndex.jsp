
<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>


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



<body>

 <jsp:include page="header.jsp" />

<div class="row-fluid sortable">    
    <div class="box span12">
    <div class="box-header well" data-original-title>
          <p> <a href="">New School</a> </p> 
        </div>
       
        <div class="box-content">

        
                <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>School Name</th>
                        <th>UserName</th> 
                        <th>Principal</th> 
                        <th>Students</th>                
                        <th>Mobile</th>
                        <th>Email</th>
                        <th>Postal address</th>
                        <th>Home Town</th>
                        <th>Status</th>
                        <th>Actions</th>

                    </tr>
                </thead>   
                <tbody>
                    <%                                                          
                      

                    %>
                    <tr>
                        



                    </tr>

                    
                </tbody>
            </table>  



       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

