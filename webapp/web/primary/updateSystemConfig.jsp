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
   <li> <a href="controlPanel.jsp">Back</a>  <span class="divider">/</span> </li> 
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">
        <form class="form-horizontal" action="updateSystemConfig" method="POST"  >
                <fieldset>


                    <div class="control-group">
                        <label class="control-label" for="term">Term*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="term" type="text" value="<%=request.getParameter("term")%>" >
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="year">Year*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="year" type="text" value="<%=request.getParameter("year")%>" >
                        </div>
                    </div>



                     <div class="control-group">
                        <label class="control-label" for="examcode">Exam Code*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="examcode" type="text" value="<%=request.getParameter("examcode")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="smssend">SMS Send Code :</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="smssend"
                              value="<%=request.getParameter("smssend")%>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="openningdate">Openning Date*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="openningdate"
                              value="<%=request.getParameter("openningdate")%>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="closingdate">Closing Date*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="closingdate"
                              value="<%=request.getParameter("closingdate")%>"  >
                        </div>
                    </div> 


                     
                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             
            
          


    </div>

</div>


<jsp:include page="footer.jsp" />
