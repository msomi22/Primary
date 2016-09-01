<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>


<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

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
   <li> <a href="staff.jsp">Back</a>  <span class="divider">/</span> </li> 
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">
        <form class="form-horizontal" action="updateStaff" method="POST"  >
                <fieldset>


                    <div class="control-group">
                        <label class="control-label" for="employeeNo">Employee Number*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="employeeNo" type="text" value="<%=request.getParameter("employeeNo")%>" >
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="staffname">Staff name*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="staffname" type="text" value="<%=request.getParameter("staffname")%>" >
                        </div>
                    </div>



                     <div class="control-group">
                        <label class="control-label" for="phone">Phone*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="phone" type="text" value="<%=request.getParameter("phone")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email :</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="email"
                              value="<%=request.getParameter("email")%>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="gender">Gender*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="gender"
                              value="<%=request.getParameter("gender")%>"  >
                        </div>
                    </div> 


                     
                    <div class="form-actions">
                        <input type="hidden" name="staffuuid" value="<%=request.getParameter("staffuuid")%>">
                         <input type="hidden" name="schooluuid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             
            
          


    </div>

</div>


<jsp:include page="footer.jsp" />
