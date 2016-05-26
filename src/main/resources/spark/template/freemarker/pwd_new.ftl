<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">	
	<link rel="stylesheet" href="/css/login.css">
	<link rel="stylesheet" href="/css/mediaqueries.css" type="text/css" />		
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	
    
	<!--=============================================== form ===========================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">	
		<div id="wrapper">

			<form method="post" role="form" name="login-form" class="login-form">
				<input type="hidden" name="token" value="${token}">
				<div class="header">
					<h1>Ré-initialisation du mot de passe</h1>
					<span>Veuillez compléter les champs ci-dessous.</span>
				</div>

				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@email.fr" required/>
	                </#if>
					<div class="email-icon emailPwdNew"></div>
					
					<input class="input password" name="pwd" id="pwd" type="password" placeholder="nouveau mot de passe" required/>
					<div class="pass-icon passPwdNew"></div>
					<!--
					<input class="input password" name="pwd2" id="pwd2" type="password"  placeholder="re-saisir le mot de passe" required/>
					<div class="pass-icon passPwdNew2"></div>
					-->
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
				</div>
			
			</form>

		</div>		
	</section>
  
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">	  
  
	<!-- cryptage md5 -->
	<#include "md5.ftl">
</body>
</html>
