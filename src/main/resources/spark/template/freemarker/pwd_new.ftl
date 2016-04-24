<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="/css/login.css">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">
    
	<section id="home">
	
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
					<div class="email-icon emailX2"></div>
					
					<input class="input password" name="pwd1" id="pwd" type="password" placeholder="nouveau mot de passe" required/>
					<div class="pass-icon passX2"></div>
					<!--
					<input class="input password" name="pwd2" id="pwd2" type="password"  placeholder="re-saisir le mot de passe" required/>
					<div class="pass-icon passX3"></div>
					-->
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
				</div>
			
			</form>

		</div>
		
	</section>
  
	<!-- cryptage md5 -->
	<#include "md5.ftl">
</body>
</html>
