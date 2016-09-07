
<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>


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

           
      if (session == null) {
            response.sendRedirect("../index.jsp");
           }

       if (StringUtils.isEmpty(schoolUsername)) {
          response.sendRedirect("../index.jsp");
        }

      session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
      response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../logout");


        CacheManager mgr = CacheManager.getInstance();
        Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);

        Account school = new Account();
        Element element;
         if ((element = accountsCache.get(schoolUsername)) != null) {
            school = (Account) element.getObjectValue();
         }
         
         schoolId = school.getUuid();

        StreamDAO streamDAO = StreamDAO.getInstance();
        List<Stream> streamList = new ArrayList<Stream>(); 
        streamList = streamDAO.getStreamList(schoolId); 


    Calendar calendar = Calendar.getInstance();
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR)-18;
    final int YEAR_COUNT = YEAR + 10;

    final int YEAR2 = calendar.get(Calendar.YEAR)-10;
    final int YEAR_COUNT2 = YEAR2 + 10;

       

%> 

<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">

    <li> <b> WELCOME TO   <b> <%=school.getSchoolName()%> </li> 
     <li> <a href="schoolIndex.jsp">Back</a>  <span class="divider">/</span> </li>
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




        <p>Fields marked with a * are compulsory.</p>
                    <form  class="form-horizontal"   action="newStudent" method="POST" >
                    <fieldset>



                                   <div class="control-group">
                                        <label class="control-label" for="admissionNumber">Admission Number*:</label>
                                        <div class="controls">
                                         <input class="input-xlarge focused" id="receiver" type="text" name="admissionNumber" 
                                            value="" >

                                        </div>
                                    </div>  

                                     <div class="control-group">
                                        <label class="control-label" for="Classroom">Classroom*:</label>
                                         <div class="controls">
                                            <select name="classroomUuid" >

                                                <option value="">Please select one</option> 
                                                 <%
                                                    int count = 1;
                                                    if (streamList != null) {
                                                        for (Stream cl : streamList) {
                                                %>
                                                <option value="<%=cl.getUuid()%>"><%=cl.getStreamName()%></option>
                                                <%
                                                            count++;
                                                        }
                                                    }
                                                %>
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 



                                     <div class="control-group">
                                        <label class="control-label" for="firstname">Firstname*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="firstname" 
                                             value="" style="text-transform: capitalize;" >                                    
                                        </div>
                                    </div> 


                                    <div class="control-group">
                                        <label class="control-label" for="middlename">Middlename*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="middlename"
                                              value="" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 


                                     <div class="control-group">
                                        <label class="control-label" for="lastname">Lastname:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                                              value="" style="text-transform: capitalize;" >
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
                                     

                                    <div class="control-group">
                                        <label class="control-label" for="dob">DOB (DD-MM-YYYY)*:</label>
                                        <div class="controls">
                                                  <select name="dobDay" id="input" style="max-width:8%;">
                                                        <%
                                                            for (int j = 1; j < DAYS_IN_MONTH; j++) {
                                                                if (j == DAY_OF_MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                   <select name="dobMonth" id="input" style="max-width:8%;" >
                                                        <%
                                                            for (int j = 1; j < 13; j++) {
                                                                if (j == MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                    <select name="dobYear" id="input" style="max-width:8%;">
                                                        <%
                                                            for (int j = YEAR; j < YEAR_COUNT; j++) {
                                                                if (j == YEAR) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                        </div>
                                        </div>



                                    <div class="control-group">
                                        <label class="control-label" for="Category">Category*:</label>
                                         <div class="controls">
                                            <select name="category" >
                                              <option value="">Please select one</option> 
                                              <option value="Boarding">Boarding</option> 
                                              <option value="Day">Day</option> 
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 


                                    <div class="control-group">
                                        <label class="control-label" for="Level">Level*:</label>
                                         <div class="controls">
                                            <select name="level" >
                                              <option value="">Please select one</option> 
                                              <option value="UPPER">Upper</option> 
                                              <option value="LOWER">Lower</option> 
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 

                                    

                                    
                                    <div class="form-actions">
                                        <input type="hidden" name="schooluuid" value="<%=schoolId%>">
                                        <button type="submit" class="btn btn-primary">Register</button>
                                    </div> 

              </fieldset>
              </form>
       

        


      
    </div><!--/span-->

</div><!--/row-->


<jsp:include page="footer.jsp" />
