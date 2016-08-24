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
          
            
                  <form  class="form-horizontal"   action="" method="POST" >
                    <fieldset>

                   <div class="control-group" id="divid">
                        <label class="control-label" for="name">Position*</label>
                        <div class="controls">
                            <select name="Position" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (positionList != null) {
                                        for (Position p : positionList) {
                                %>
                                <option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">Employee Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 



                    <div class="control-group">
                        <label class="control-label" for="name">Username*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="username" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("username")) %>"  >

                        </div>
                    </div>  

                   


                     <div class="control-group">
                        <label class="control-label" for="staffname">Staff Name*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="staffname" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("staffname")) %>" style="text-transform: capitalize;"  >                                    
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="gender">Gender*:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="phone">Phone No*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email *:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="email"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("email")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="dob">DOB:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("dob")) %>"  >
                        </div>
                    </div>  


                    <div class="control-group">
                        <label class="control-label" for="country">Country:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="country"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("country")) %>" style="text-transform: capitalize;" >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="county">County:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>" style="text-transform: capitalize;" >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="ward">Ward:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="ward"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("ward")) %>" style="text-transform: capitalize;" >
                        </div>
                    </div>  

                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
