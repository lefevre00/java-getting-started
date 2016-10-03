<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="${ressourcesDirectory}css/login.css">
	<link rel="stylesheet" href="${ressourcesDirectory}css/mediaqueries.css" type="text/css" />	
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	 
  
	<!--======================================== User creation form ====================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">	
		
		<div id="wrapper">

			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1><#if tok??>Activation de compte<#else>Création de compte</#if></h1>
					<span><#if tok??>Veuillez renseigner le mot de passe de votre compte<#else>Veuillez compléter les champs ci-dessous.</#if></span>
				</div>
			
				<div class="content">
					<#if tok??>
						<input type="hidden" name="tok" value="${tok}"/>
					</#if>
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@amdm.fr" value="${email}" required <#if tok??>disabled</#if>/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@amdm.fr" required/>
	                </#if>
					<div class="email-icon emailRegister"></div>

					<input class="input password" name="pwd" id="pwd" type="password" placeholder="mot de passe" required/>
					<div class="pass-icon passRegister"></div>
					
					<input class="input place" name="placeNumber" id="placeNumber" type="text"  placeholder="Numéro de place (si attribuée)" size="3" maxlength="3" <#if placeNumber??>value="${placeNumber}"</#if><#if tok??>disabled</#if>/>
					<div class="place-icon"></div>	
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
				</div>
			
			</form>

		</div>
			
	</section>

	<!--================================== javascripts files section  ===============================-->
	<#include "commonjs.ftl">	
	
	<!-- cryptage md5 -->
	<#include "md5.ftl">
</body>
</html>
