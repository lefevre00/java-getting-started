<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="/css/login.css">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">
	
	<!-- home section -->
	<section id="home">
		
		<div id="wrapper">
		
			<#if error??>
				<div class="alert alert-danger">
					<strong>Erreur : </strong>${error}
				</div>
			</#if>

			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1>Authentification</h1>
					<span>Veuillez renseigner les champs ci-dessous.</span>
				</div>

				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@email.fr"  value="abdel.tamditi@amdm.fr" required/>
	                </#if>
					<div class="email-icon emailX1"></div>

					<input class="input password" name="pwd" id="pwd" type="password" placeholder="mot de passe" value="at" required/>
					<div class="pass-icon passX1"></div>
					
					<div id="dvValue"></div>
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Se connecter" class="login" id="log"/>
					<a href="/user/new" class="register">S'inscrire</a>
					<br><br><br>
					<a href="/user/forget">Mot de passe oublié ?</a>
				</div>

			</form>

		</div>
	</section>

	<!-- cryptage md5 -->
	<#include "md5.ftl">
</body>
</html>
