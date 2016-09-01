
<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

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
   <li> <a href="schoolIndex.jsp">Back</a>  <span class="divider">/</span> </li>
    </ul>
</div>


<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-content">
        <form  class="form-horizontal" action="updateStudent" method="POST" >
                 <fieldset>
                    
                                     
                             <div class="control-group">
                                <label class="control-label" for="AdmissionNumber">Admission Number:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="admissionNumber"
                                      value="<%=request.getParameter("admissionNumber")%>" >  
                                </div>
                             </div> 

                              
                             <div class="control-group">
                                <label class="control-label" for="firstname">Firstname:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="firstname"
                                      value="<%=request.getParameter("firstname") %>"  style="text-transform: capitalize;">
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="middlename">Middlename:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="middlename"
                                      value="<%=request.getParameter("middlename") %>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="lastname">Lastname:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                                      value="<%=request.getParameter("lastname")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             
                            <div class="form-actions">
                                <input type="hidden" name="studentUuid" value="<%=request.getParameter("studentUuid")%>">
                                <input type="hidden" name="schoolUuid" value="<%=schoolId%>">
                                <button type="submit" class="btn btn-primary">Update</button>
                            </div> 

              </fieldset>
              </form>



    </div>

</div>


<jsp:include page="footer.jsp" />
