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
        <form class="form-horizontal" action="" method="POST"  >
                <fieldset>

                        
                    <div class="control-group" id="divid">
                        <label class="control-label" for="name">Position</label>
                        <div class="controls">
                            <select name="position" >
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
                        <label class="control-label" for="employeeNo">Employee Number</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="employeeNo" type="text" value="<%=request.getParameter("employeeNo")%>" >
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="username">Username</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="username" type="text" value="<%=request.getParameter("username")%>" >
                        </div>
                    </div>
                    
                    <div class="control-group">
                        <label class="control-label" for="staffname">Staff name</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="staffname" type="text" value="<%=request.getParameter("staffname")%>" style="text-transform: capitalize;">
                        </div>
                    </div>

            
                     <div class="control-group">
                        <label class="control-label" for="gender">Gender:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="phone">Phone*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="phone" type="text" value="<%=request.getParameter("phone")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email *:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="email"
                              value="<%=request.getParameter("email")%>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="dob">DOB</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="dob" type="text" value="<%=request.getParameter("dob")%>">
                        </div>
                    </div>

                     

                    <div class="control-group">
                        <label class="control-label" for="country">Country:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="country"
                              value="<%=request.getParameter("country")%>" >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="county">County:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                              value="<%=request.getParameter("county")%>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="ward">Ward:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="ward"
                              value="<%=request.getParameter("ward")%>" >
                        </div>
                    </div>  


                  
                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             
            
          


    </div>

</div>


<jsp:include page="footer.jsp" />
