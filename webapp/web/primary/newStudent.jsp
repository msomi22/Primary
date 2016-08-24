
<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

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

        String schoolId = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_ACCOUNTUUID); 

        CacheManager mgr = CacheManager.getInstance();
        Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_UUID);

        Account school = new Account();
        Element element;
         if ((element = accountsCache.get(schoolId)) != null) {
            school = (Account) element.getObjectValue();
         }


        String accountuuid = school.getUuid();

       

%> 

<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">

    <li> <b> WELCOME TO   <b> <%=school.getSchoolName()%> </li> 
    </ul>
</div>

<div class="row-fluid sortable">    

    <div class="box span12">
        <div class="box-content">




        <p>Fields marked with a * are compulsory.</p>
                    <form  class="form-horizontal"   action="" method="POST" >
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
                                                    if (classList != null) {
                                                        for (ClassRoom cl : classList) {
                                                %>
                                                <option value="<%=cl.getUuid()%>"><%=cl.getRoomName()%></option>
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
                                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>" style="text-transform: capitalize;" >                                    
                                        </div>
                                    </div> 


                                    <div class="control-group">
                                        <label class="control-label" for="middlename">Middlename*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="middlename"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("middlename")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 


                                     <div class="control-group">
                                        <label class="control-label" for="lastname">Lastname:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("lastname")) %>" style="text-transform: capitalize;" >
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
                                        <label class="control-label" for="bcertNo">Birth Cert:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="bcertNo"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("bcertNo")) %>"  >
                                        </div>
                                    </div> 

                                     <div class="control-group">
                                        <label class="control-label" for="County">County:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="Ward">Ward:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="ward"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("ward")) %>" style="text-transform: capitalize;" >
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
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                        <button type="submit" class="btn btn-primary">Register</button>
                                    </div> 

              </fieldset>
              </form>
       

        


      
    </div><!--/span-->

</div><!--/row-->


<jsp:include page="footer.jsp" />
