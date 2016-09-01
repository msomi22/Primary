<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
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

         StreamDAO streamDAO = StreamDAO.getInstance();

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
    <li> <a href="registration.jsp">Back</a>  <span class="divider">/</span> </li>
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">

                               <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS,null);
                                  } 


                                 %>

        <h3>Promote per class</h3>

        <form  class="form-horizontal" action="promotePerStream" method="POST" >
                    <fieldset>


                     <div class="control-group" id="divid">
                        <label class="control-label" for="oldStreamuuid">Promote From*</label>
                        <div class="controls">
                            <select name="oldStreamuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count1 = 1;
                                     for(Stream stm : streamList) {
                                %>
                                <option value="<%=stm.getUuid()%>"><%=stm.getStreamName()%></option>
                                <%
                                            count1++;
                                        }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 


                   <div class="control-group" id="divid">
                        <label class="control-label" for="newStreamuuid">Promote To*</label>
                        <div class="controls">
                            <select name="newStreamuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count2 = 1;
                                     for(Stream stm : streamList) {
                                %>
                                <option value="<%=stm.getUuid()%>"><%=stm.getStreamName()%></option>
                                <%
                                            count2++;
                                        }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                   

                    <div class="form-actions">
                        <input type="hidden" name="schoolid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div> 

              </fieldset>
              </form>



              <h3>Promote per student</h3>

              <form  class="form-horizontal" action="promotePerStudent" method="POST" >
                    <fieldset>


                     <div class="control-group">
                        <label class="control-label" for="admissionNo">Admission Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="admissionNo" 
                             value=""  >                                    
                        </div>
                    </div> 


                   <div class="control-group" id="divid">
                        <label class="control-label" for="newStreamuuid">Class*</label>
                        <div class="controls">
                            <select name="newStreamuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count3 = 1;
                                     for(Stream stm : streamList) {
                                %>
                                <option value="<%=stm.getUuid()%>"><%=stm.getStreamName()%></option>
                                <%
                                            count3++;
                                        }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                   

                    <div class="form-actions">
                        <input type="hidden" name="schoolid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div> 

              </fieldset>
              </form>
        

    </div>

</div>


<jsp:include page="footer.jsp" />
