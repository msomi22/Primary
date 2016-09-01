

<%@page import="ke.co.fastech.primaryschool.persistence.student.StudentDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.student.Student"%>

<%@page import="ke.co.fastech.primaryschool.persistence.school.StreamDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.school.Stream"%>

<%@page import="ke.co.fastech.primaryschool.bean.school.account.Account"%>

<%@page import="ke.co.fastech.primaryschool.server.cache.CacheVariables"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>


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



       
         StudentDAO studentDAO = StudentDAO.getInstance(); 
         StreamDAO streamDAO = StreamDAO.getInstance();
        

          String admmissinNo = request.getParameter("admissNo");
          out.println("admmissinNo " + admmissinNo);


         List<Student> studentList = new ArrayList<Student>();  
         studentList = studentDAO.getStudentListByAdmNo(admmissinNo,schoolId);  

        //get student stream
        Map<String,String> streamMap = new HashMap<String,String>(); 
        List<Stream> streamList = new ArrayList<Stream>();  
        streamList = streamDAO.getStreamList(schoolId); 
        for(Stream strm : streamList){
            streamMap.put(strm.getUuid(),strm.getStreamName()); 
             }

  

                    String firstnameLowecase = "";
                    String middlenameLowecase ="";
                    String lastnameLowecase ="";

                     int count =1;  
                    for(Student student : studentList){
                       
                        firstnameLowecase = StringUtils.capitalize(student.getFirstname().toLowerCase());
                        middlenameLowecase = StringUtils.capitalize(student.getMiddlename().toLowerCase());
                        lastnameLowecase = StringUtils.capitalize(student.getLastname().toLowerCase());
                         
                        %>

                         <tr class="tabledit">
                         <td width="3%"><%=count%></td>
                         <td class="center"><%=student.getAdmmissinNo()%></td> 
                         <td class="center"><%=firstnameLowecase%></td>
                         <td class="center"><%=middlenameLowecase%></td>
                         <td class="center"><%=lastnameLowecase%></td>
                         <td class="center"><%=student.getGender()%></td>
                         <td class="center"><%=student.getDateofbirth()%></td>
                         <td class="center"><%=student.getBirthcertificateNo()%></td>
                         <td class="center"><%=student.getStudentType()%></td>
                         <td class="center"><%=student.getStudentLevel()%></td> 
                         <td class="center"><%=streamMap.get(student.getStreamUuid())%></td> 
                         <td class="center">
                                <form name="Update" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admissionNumber" value="<%=student.getAdmmissinNo()%>">
                                <input type="hidden" name="firstname" value="<%=firstnameLowecase%>">
                                <input type="hidden" name="middlename" value="<%=middlenameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastnameLowecase%>">
                                <input type="hidden" name="studentUuid" value="<%=student.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="Update" id="Update" value="Update"/> 
                                </form>                          
                          </td>      
                         </tr>

                        <%
                          count++;
                       }
                

                            
                    %>
                