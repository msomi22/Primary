<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.StaffDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Staff"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.category.CategoryDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Category"%>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.category.StaffCategoryDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.StaffCategory"%>

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


         StaffDAO staffDAO = StaffDAO.getInstance();
         StaffCategoryDAO staffCategoryDAO = StaffCategoryDAO.getInstance();

         List<Staff> staffList = new ArrayList<Staff>(); 
         staffList = staffDAO.getStaffList(schoolId); 

         SystemConfigDAO systemConfigDAO = SystemConfigDAO.getInstance();
         SystemConfig systemConfig = systemConfigDAO.getSystemConfig(schoolId);

         Map<String,String> catMap = new HashMap<String,String>(); 
         CategoryDAO categoryDAO = CategoryDAO.getInstance();

         List<Category> catList = new ArrayList<Category>(); 
         catList = categoryDAO.getCategoryList();
         for(Category cat : catList){
               catMap.put(cat.getUuid(),cat.getCategoryName());
            }



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
     <li> <a href="newStaff.jsp">New Staff</a>  <span class="divider">/</span> </li>
     <li> <a href="newStaffSubject.jsp">Assign Subjects</a>  <span class="divider">/</span> </li>
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">

                                 <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateErr = (String) session.getAttribute(SessionConstants.STAFF_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STAFF_SUCCESS);                     

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_SUCCESS,null);
                                  } 


                                 %>


        <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Category</th>
                        <th>EmpNo </th>
                        <th>Staff Name</th>
                        <th>Phone</th>
                        <th>Email </th>
                        <th>Gender </th>
                        <th>Subjects </th>
                        <th>Update </th>
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                        if(staffList !=null){
                       for(Staff s : staffList) { 

                             StaffCategory staffCategory = staffCategoryDAO.getStaffCategory(s.getUuid()); 

                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + catMap.get(staffCategory.getCategoryUuid())  + "</td>"); 
                             out.println("<td width=\"5%\" class=\"center\">" + s.getEmployeeNo() + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + s.getName() + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + s.getPhone() + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + s.getEmail() + "</td>"); 
                             out.println("<td width=\"5%\" class=\"center\">" + s.getGender() + "</td>"); 
                                  %>
                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action="mysubjects.jsp"> 
                                <input type="hidden" name="teacherUuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Subjects" /> 
                                </form>                          
                                </td>          

                                <td class="center" width="5%">
                                <form name="update" method="POST" action="updateStaff.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input type="hidden" name="employeeNo" value="<%=s.getEmployeeNo()%>">
                                <input type="hidden" name="staffname" value="<%=s.getName()%>">
                                <input type="hidden" name="phone" value="<%=s.getPhone()%>">
                                <input type="hidden" name="email" value="<%=s.getEmail()%>">
                                <input type="hidden" name="gender" value="<%=s.getGender()%>">
                                <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                                </form>                          
                                </td>   

                             <%

                           count++;
                          } 
                     }
                    %>
                    
                    </tbody>
            </table> 
            
            </div>


        




    </div>

</div>


<jsp:include page="footer.jsp" />
