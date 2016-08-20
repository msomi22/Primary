<!DOCTYPE html>

<%@page import="ke.co.fastech.primaryschool.server.servlet.util.PropertiesConfig"%>
<%@page import="ke.co.fastech.primaryschool.server.session.SessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

                    

<html lang="en">
    <head>
        
        <meta charset="utf-8">
        <title>FastPro School Management System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content=".">
        <meta name="author" content="Peter Mwenda" >
        
         <script src="../js/jquery/jquery-1.8.2.min.js"></script>  
         <script src="../js/jquery/jquery-1.7.2.min.js"></script>
         <script src="../js/searchstudent.js"></script>
         
        <link href="../css/bootstrap/bootstrap-cerulean.css" rel="stylesheet">
        <style type="text/css">
            body {
                padding-bottom: 40px;
            }
            .sidebar-nav {
                padding: 9px 0;
            }
        </style>
        <!-- jQuery --> 
       
  
      
        
        <link href="../css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
        <link href="../css/fastech/charisma-app.css" rel="stylesheet">
        <link href="../css/jquery/jquery-ui-1.8.21.custom.css" rel="stylesheet">
        <link href='../css/fastech/fullcalendar.css' rel='stylesheet'>
        <link href='../css/fastech/fullcalendar.print.css' rel='stylesheet'  media='print'>
        <link href='../css/fastech/chosen.css' rel='stylesheet'>
        <link href='../css/fastech/uniform.default.css' rel='stylesheet'>
        <link href='../css/fastech/colorbox.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.cleditor.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.noty.css' rel='stylesheet'>
        <link href='../css/fastech/noty_theme_default.css' rel='stylesheet'>
        <link href='../css/fastech/elfinder.min.css' rel='stylesheet'>
        <link href='../css/fastech/elfinder.theme.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.iphone.toggle.css' rel='stylesheet'>
        <link href='../css/fastech/opa-icons.css' rel='stylesheet'>
        <link href='../css/fastech/uploadify.css' rel='stylesheet'>
        <link href='../css/fastech/template.css' rel='stylesheet'>
        <link href='../css/fastech/checkpass.css' rel='stylesheet'>
        <link href='../css/fastech/styles.css' rel='stylesheet'>
        <link href='../css/fastech/chatStyle.css' rel='stylesheet'>

         <link rel="stylesheet" href="../css/reset.css" type="text/css" media="all">
        <link rel="stylesheet" href="../css/site.css" type="text/css" media="all">


        
        
        <!-- The fav icon -->
        <link rel="shortcut icon" href="img/favicon.ico">
        
      

    </head>


         <%
            String schoolId = (String) session.getAttribute(SessionConstants.ACCOUNT_SIGN_IN_ACCOUNTUUID); 
            String staffId = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_ID);
            String staffPosition = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_POSITION);
            String username = (String) session.getAttribute(SessionConstants.STAFF_SIGN_IN_USERNAME);

         %>




    <body>

        
    <div class="navbar">
          <div class="navbar-inner">
          <div class="container-fluid">
          <div class="btn-group pull-right theme-container" >
          </div>
         

          <!-- user dropdown starts -->
          <div class="btn-group pull-right" >
          <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
          <i class="icon-user"></i><span class="hidden-phone"> <%=username%> </span>
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
          <!--<li><a href="#">Profile</a></li>-->
          <li class="divider"></li>
          <li><a href="../logout">Logout</a></li>
          <li ><a href="" target="_blank">Help</a></li>
          </ul>
          </div>

          <!-- user dropdown ends -->
          <div class="top-nav nav-collapse">
          </div><!--/.nav-collapse -->
          </div>

      <!-- top menu -->
      <div class="topmenu">


          <a href="">REGISTRATION</a>
          <a href="">ACADEMIC</a>
          <a href="">FINANCE</a>
          <a href="">STAFF</a>
          <a href="">CONTROL PANEL</a>
          
  
          
      </div>

      </div>
      </div>
                    
 



 </div>

</div>
</div>
<!-- topbar ends -->
<div class="container-fluid">
<div class="row-fluid">
<!-- left menu starts -->
<div class="span2 main-menu-span">
<div class="well nav-collapse sidebar-nav">
<ul class="nav nav-tabs nav-stacked main-menu">

<!--menu to change depending on page requested-->
<li class="nav-header hidden-tablet">MORE</li>
<li><a href="schoolIndex.jsp" class="ajax-link" id ="btn-dangers1"href=""><i class="icon-envelope"></i><span class="hidden-tablet">HOME</span></a></li>
<li><a href="" class="ajax-link" id ="btn-dangers1"href=""><i class="icon-envelope"></i><span class="hidden-tablet">LIBRARY</span></a></li>


</ul>
</div><!--/.well -->
</div><!--/span-->
<!-- left menu ends -->


<noscript>
    <div class="alert alert-block span10">
    <h4 class="alert-heading">Warning!</h4>
    <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
    </div>
</noscript>
<div id="content" class="span10">
<!-- content starts -->

                   
              
            