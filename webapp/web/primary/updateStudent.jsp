
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
                    
                                     
                             <div class="control-group">
                                <label class="control-label" for="AdmissionNumber">Admission Number:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="admissionNumber"
                                      value="<%=request.getParameter("admissionNumber")%>" >  
                                </div>
                             </div> 

                              <div class="control-group">
                                <label class="control-label" for="finalYear">Final Year:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalYear"
                                      value="<%=request.getParameter("finalYear")%>"  >
                                </div>
                             </div> 

                              <div class="control-group">
                                <label class="control-label" for="finalTerm">Final Term:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalTerm"
                                      value="<%=request.getParameter("finalTerm")%>"  >
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
                                <label class="control-label" for="dob">Date of Birth:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                                      value="<%=request.getParameter("dob")%>"  >
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="bcertNo">Birth Cert No:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="bcertNo"
                                      value="<%=request.getParameter("bcertNo")%>"  >
                                </div>
                             </div> 
                            
                             <div class="control-group">
                                <label class="control-label" for="County">County:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="county"
                                      value="<%=request.getParameter("county")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 


                             <div class="control-group">
                                <label class="control-label" for="ward">Ward:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="ward"
                                      value="<%=request.getParameter("ward")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 



                                    
                                    
                            <div class="form-actions">
                                <input type="hidden" name="studentUuid" value="<%=request.getParameter("studentUuid")%>">
                                <input type="hidden" name="accountuuid" value="<%=accountuuid%>">
                                <button type="submit" class="btn btn-primary">Update</button>
                            </div> 

              </fieldset>
              </form>
       











    </div>

</div>


<jsp:include page="footer.jsp" />
