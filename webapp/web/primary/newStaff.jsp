<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.category.CategoryDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Category"%>

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

         CategoryDAO categoryDAO = CategoryDAO.getInstance();
         List<Category> catList = new ArrayList<Category>(); 
         catList = categoryDAO.getCategoryList();

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

        <%
            HashMap<String,String> paramHash = (HashMap<String,String>) session.getAttribute(SessionConstants.STAFF_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }


        %>
          
            
                  <form  class="form-horizontal"   action="newStaff" method="POST" >
                    <fieldset>

                   <div class="control-group" id="divid">
                        <label class="control-label" for="Category">Category*</label>
                        <div class="controls">
                            <select name="categoryuuid" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (catList != null) {
                                        for (Category cat : catList) {
                                             if(!StringUtils.equals(cat.getCategoryName(), "Principal")){   
                                %>
                                <option value="<%=cat.getUuid()%>"><%=cat.getCategoryName()%></option>
                                <%
                                            count++;
                                            }
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="employeeNo">Employee Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 



                    <div class="control-group">
                        <label class="control-label" for="username">Username*:</label>
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
                        <label class="control-label" for="phone">Phone No*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">Email:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="email"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("email")) %>"  >
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



                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=schoolId%>">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
