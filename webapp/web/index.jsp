<!DOCTYPE html>

<%@page import="ke.co.fastech.primaryschool.persistence.staff.category.CategoryDAO"%>
<%@page import="ke.co.fastech.primaryschool.bean.staff.Category"%>

<%@page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>

<%@page import="ke.co.fastech.primaryschool.server.servlet.util.FontImageGenerator"%>
<%@page import="ke.co.fastech.primaryschool.server.servlet.util.PropertiesConfig"%>

<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Calendar" %>

<%

   

     CategoryDAO categoryDAO = CategoryDAO.getInstance();

     List<Category> catList = new ArrayList<Category>(); 
     catList = categoryDAO.getCategoryList();

    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();   
    textEncryptor.setPassword(PropertiesConfig.getConfigValue("ENCRYPT_PASSWORD")); 
      
    String captcha = RandomStringUtils.randomAlphabetic(4); 
    String encryptedCaptcha = textEncryptor.encrypt(captcha);


%>


<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="css/style.css">
 
</head>

<body>
 <div class="alert alert-info">
                           




  <div class="container">


  <%
                                String loginErrStr = "";
                                session = request.getSession(false);

                                if(session != null) {
                                    loginErrStr = (String) session.getAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR);
                                }                        

                                if (StringUtils.isNotEmpty(loginErrStr)) {
                                    out.println("<p style='color:red;'>");                   
                                    out.println("Login error: " + loginErrStr);
                                    out.println("</p>");                                    
                                    session.setAttribute(SessionConstants.ACCOUNT_LOGIN_ERROR, null);
                                  } 


                            %>

                          
        

      <section class="register">
      <h1>Login to Your School</h1>
      <form autocomplete="off" method="POST" action="login" >
      <div class="reg_section password">
      <h3>Your Position</h3>

      <select name="staffposition" >
      
                                  <%
                                 int count = 1;
                                    if (catList != null) {
                                        for (Category cat : catList) {
                                %>
                                <option value="<%=cat.getUuid()%>"><%=cat.getCategoryName()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
      </select>
      </div>

       <div class="reg_section password">
       <h3>Staff Username </h3>
       <input autocomplete="off" type="text" name="staffusername" value="" placeholder="Staff username">
       <h3>Staff Passworld</h3>
       <input autocomplete="off" type="password" name="staffpassword"  placeholder="Staff password" />


                       <%
                        String fontImageUrl = "fontImageGenerator?text=" + URLEncoder.encode(encryptedCaptcha, "UTF-8");
                        %> 

                        <div class="field_container">
                        <div class='wrapper'>
                    
                            <div id="spam-check">                                        
                                <span id="captchaGuidelines">Type the characters you see in the image below</span><br>
                                <img id="captcha" src=<% out.println("\"" + fontImageUrl + "\"");%> width="80" height="40" /> <br>
                                <input type="text" name="captchaAnswer" id="captchaAnswer" size="5" class="input_normal"/>
                                <input type="hidden" name="captchaHidden" id="captchaHidden"
                                       value=<% out.println("\"" + URLEncoder.encode(encryptedCaptcha, "UTF-8") + "\"");%> />
                                             

                            </div>
                        </div>
                      </div>

                      <a href="">Forgot Password?</a>

       </div>
      
      <p class="submit"><input type="submit" name="submit" value="Login"></p>
      </form>
    </section>


  </div>


</body>
</html>