<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>IVotas - Homepage</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="./assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="./assets/css/form-elements.css">
        <link rel="stylesheet" href="./assets/css/style.css">

        <!-- Favicon and touch icons -->
        <link rel="shortcut icon" href="./assets/ico/favicon.png">
        
        <!-- Alert -->
        <link rel="stylesheet" href="./assets/alerts/style.css">
        <script type="text/javascript" src="./assets/alerts/script.js"></script>
        
</head>
<body>
	<nav class="navbar navbar-light bg-faded">
	  	<ul class="nav navbar-nav">
	  		<li class="nav-item">
	    		<a href="<url action="userhome"/>">IVotas User</a>
	    	</li>
	    		<li class="nav-item">
	    		<a href="https://www.facebook.com/v2.8/dialog/oauth?client_id=343334306050475&redirect_uri=http://localhost:8080/iBei/callback&response_type=code&scope=publish_actions">
                Associate Facebook <i class="fa fa-facebook"></i> 
 				</a>
	    	</li>
	    	
	    	
	  	</ul>
	</nav>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="./assets/toast/toast.js"></script>
</body>
</html>