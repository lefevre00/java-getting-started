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
  
	<!--======================================== User creation form ====================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">	
		
		<div id="wrapper">

			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1>Création de compte</h1>
					<span>Veuillez compléter les champs ci-dessous.</span>
				</div>
			
				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@amdm.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@amdm.fr" required/>
	                </#if>
					<div class="email-icon emailRegister"></div>

					<input class="input password" name="pwd" id="pwd" type="password" placeholder="mot de passe" required/>
					<div class="pass-icon passRegister"></div>
					
					<input class="input place" name="placeNumber" id="placeNumber" type="text"  placeholder="Numéro de place (si attribuée)" size="3" maxlength="3"/>
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
