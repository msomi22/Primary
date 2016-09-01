
<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.SystemConfigDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.SystemConfig"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="ke.co.fastech.primaryschool.server.servlet.school.upload.StudentPerclass"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

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

        String notNull=null;

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
                 


                
                <p>Upload Excel file with format <code>admission number,first name, middle name,gender,category,level</code> : Saved as <code>filename.xlsx</code></p> 
                <p>Download this sample excel file  <a href="../resources/students.xlsx">Download</a>    </p>
                <form method="POST" action="studentPerclass" enctype="multipart/form-data">  
                <fieldset>
                         <div class="control-group" id="javascript" javaScriptCheck="<%=notNull%>">
                         
                               <input type="file" name="file" required="true" multiple accept=".csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"/>

                              <div class="form-actions">                        
                              <button type="submit" class="btn btn-primary">Upload File</button>           
                             </div>
                           </div>
                </fieldset>
                </form> 

                
                
                 <h3>
                <%
                    if(StringUtils.isNotBlank((String)session.getAttribute(StudentPerclass.UPLOAD_FEEDBACK ))) {
                    String servletResponse =(String)session.getAttribute(StudentPerclass.UPLOAD_FEEDBACK );
                        out.println(servletResponse);
                        //used by javascript 
                        if(servletResponse!=null){notNull=servletResponse.substring(0,10);
                        }                    
                        session.setAttribute(StudentPerclass.UPLOAD_FEEDBACK, null);
                    }
                %>  
                </h3>


            </div>

      

        </div>
    </div><!--/span-->

</div><!--/row-->

<!--scroll to the bottom the page if file upload is done -->
<script type="text/javascript">


$("document").ready(function() {   

        var check1 = $("#javascript").attr("javaScriptCheck");
        if(check1.length>2){
           $("html, body").animate({ scrollTop: $(document).height() });
        }   

    });
</script>


<jsp:include page="footer.jsp" />
