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
					<div class="email-icon emailX2"></div>

					<input class="input password" name="pwd" id="pwd" type="password" placeholder="mot de passe" required/>
					<div class="pass-icon passX2"></div>
					
					<input class="input place" name="placeNumber" id="placeNumber" type="text"  placeholder="Numéro de place (si attribuée)" size="3" maxlength="3"/>
					<div class="place-icon"></div>	
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
