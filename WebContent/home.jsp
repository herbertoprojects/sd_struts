<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>IVotas - Home</title>
        
        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="assets/css/form-elements.css">
        <link rel="stylesheet" href="assets/css/style.css">
		<link href='http://fonts.googleapis.com/css?family=Inconsolata:400,700' rel='stylesheet' type='text/css'>
				    	

        <link rel="shortcut icon" href="assets/ico/favicon.png">
        
        <!-- Alert -->
        <link rel="stylesheet" href="./assets/alerts/style.css">
        <script type="text/javascript" src="./assets/alerts/script.js"></script>
        
    </head>
    
    <body>
    
    	<div class="top-content">
        	<div class="container">

                <div class="row">
                    <div class="col-sm-8 col-sm-offset-2 text">
                        <h1>IVotas 2.0</h1>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-4 col-sm-offset-4 show-forms">
                    	<span class="show-register-form active">Register</span>
                    	<span class="show-forms-divider">/</span>
                    	<span class="show-login-form">Login</span>
                    </div>
                </div>

                <div class="row register-form">
                    <div class="col-sm-4 col-sm-offset-4">
						<form role="form" action="register" method="post" class="r-form">
	                    	<div class="form-group">
	                    		<label class="sr-only" for="r-form-first-name">Personal ID</label>
	                        	<input type="text" name="ncc" placeholder="Personal ID" class="r-form-first-name form-control" id="ncc">
	                        </div>
	                        <div class="form-group">
	                        	<label class="sr-only" for="r-form-last-name">Password</label>
	                        	<input type="password" name="password" placeholder="Password" class="r-form-last-name form-control" id="password">
	                        </div>
				            <button type="submit" class="btn">Sign me up!</button>
						</form>
                    </div>
                </div>

                <div class="row login-form">
                    <div class="col-sm-4 col-sm-offset-4">
						<form role="form" action="login" method="post" class="l-form">
	                    	<div class="form-group">
	                    		<label class="sr-only" for="l-form-username">Personal ID</label>
	                        	<input type="text" name="ncc" placeholder="Personal ID" class="l-form-username form-control" id="ncc">
	                        </div>
	                        <div class="form-group">
	                        	<label class="sr-only" for="l-form-password">Password</label>
	                        	<input type="password" name="password" placeholder="Password" class="l-form-password form-control" id="password">
	                        </div>
				            <button type="submit" class="btn">Sign in!</button>
				    	</form>
				    	
						<style>
					    	.btn-social{position:relative;padding-left:44px;text-align:left;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.btn-social :first-child{position:absolute;left:0;top:0;bottom:0;width:32px;line-height:34px;font-size:1.6em;text-align:center;border-right:1px solid rgba(0,0,0,0.2)}
							.btn-social.btn-lg{padding-left:61px}.btn-social.btn-lg :first-child{line-height:45px;width:45px;font-size:1.8em}
							.btn-social.btn-sm{padding-left:38px}.btn-social.btn-sm :first-child{line-height:28px;width:28px;font-size:1.4em}
							.btn-social.btn-xs{padding-left:30px}.btn-social.btn-xs :first-child{line-height:20px;width:20px;font-size:1.2em}
							.btn-social-icon{position:relative;padding-left:44px;text-align:left;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;height:34px;width:34px;padding-left:0;padding-right:0}.btn-social-icon :first-child{position:absolute;left:0;top:0;bottom:0;width:32px;line-height:34px;font-size:1.6em;text-align:center;border-right:1px solid rgba(0,0,0,0.2)}
							.btn-social-icon.btn-lg{padding-left:61px}.btn-social-icon.btn-lg :first-child{line-height:45px;width:45px;font-size:1.8em}
							.btn-social-icon.btn-sm{padding-left:38px}.btn-social-icon.btn-sm :first-child{line-height:28px;width:28px;font-size:1.4em}
							.btn-social-icon.btn-xs{padding-left:30px}.btn-social-icon.btn-xs :first-child{line-height:20px;width:20px;font-size:1.2em}
							.btn-social-icon :first-child{border:none;text-align:center;width:100% !important}
							.btn-social-icon.btn-lg{height:45px;width:45px;padding-left:0;padding-right:0}
							.btn-social-icon.btn-sm{height:30px;width:30px;padding-left:0;padding-right:0}
							.btn-social-icon.btn-xs{height:22px;width:22px;padding-left:0;padding-right:0}
							.btn-facebook{color:#fff;background-color:#3b5998;border-color:rgba(0,0,0,0.2)}.btn-facebook:hover,.btn-facebook:focus,.btn-facebook:active,.btn-facebook.active,.open .dropdown-toggle.btn-facebook{color:#fff;background-color:#30487b;border-color:rgba(0,0,0,0.2)}
							.btn-facebook:active,.btn-facebook.active,.open .dropdown-toggle.btn-facebook{background-image:none}
							.btn-facebook.disabled,.btn-facebook[disabled],fieldset[disabled] .btn-facebook,.btn-facebook.disabled:hover,.btn-facebook[disabled]:hover,fieldset[disabled] .btn-facebook:hover,.btn-facebook.disabled:focus,.btn-facebook[disabled]:focus,fieldset[disabled] .btn-facebook:focus,.btn-facebook.disabled:active,.btn-facebook[disabled]:active,fieldset[disabled] .btn-facebook:active,.btn-facebook.disabled.active,.btn-facebook[disabled].active,fieldset[disabled] .btn-facebook.active{background-color:#3b5998;border-color:rgba(0,0,0,0.2)}
				    	</style>
				    	
				    	<div class="social-login">
                        	<div>
                        		<a class="btn btn-block btn-social btn-facebook" href="https://www.facebook.com/v2.8/dialog/oauth?client_id=343334306050475&redirect_uri=http://localhost:8080/iBei/callback&response_type=code&scope=publish_actions">
                        		<i class="fa fa-facebook"></i> Sign in with Facebook
  								</a>
                        	</div>
                        </div>
                    </div>
                </div>

        	</div>
        </div>

        <!-- Javascript -->
        <script src="assets/js/jquery-1.11.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/scripts.js"></script> 

    </body>
    
    </body>