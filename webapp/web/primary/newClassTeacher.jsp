<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

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

        StreamDAO streamDAO = StreamDAO.getInstance();

         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);

         List<Stream> streamList = new ArrayList<Stream>();  
         streamList = streamDAO.getStreamList(schoolId); 


         
         
         
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
     <li> <a href="classTeacher.jsp">Back</a>  <span class="divider">/</span> </li> 
    </ul>
</div>

<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-content">
                               
            
                  <form  class="form-horizontal" action="newClassTeacher" method="POST" >
                    <fieldset>


                     <div class="control-group">
                        <label class="control-label" for="name">Employee Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value=""  >                                    
                        </div>
                    </div> 


                   <div class="control-group" id="divid">
                        <label class="control-label" for="streamuuid">Class*</label>
                        <div class="controls">
                            <select name="streamuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                     for(Stream stm : streamList) {
                                %>
                                <option value="<%=stm.getUuid()%>"><%=stm.getStreamName()%></option>
                                <%
                                            count++;
                                        }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                   

                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
