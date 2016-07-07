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
	
	<!--============================================ login form ========================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">			
		<div id="wrapper">

			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1>Authentification</h1>
					<span>Veuillez renseigner les champs ci-dessous.</span>
				</div>

				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@email.fr"  value="admin.ecoparking@amdm.fr" required/>
	                </#if>
					<div class="email-icon emailLogin"></div>

					<input class="input password" name="pwd" id="pwd" type="password" placeholder="mot de passe" value="adminecop" required/>
					<div class="pass-icon passLogin"></div>
					
					<div id="dvValue"></div>
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Se connecter" class="login" id="log"/>
					<a href="/user/new" class="register">S'inscrire</a>
					<br><br><br>
					<a href="/user/forget" class="forget">Mot de passe oublié ?</a>
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
