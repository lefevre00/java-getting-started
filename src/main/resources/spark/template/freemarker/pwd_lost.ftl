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
					<h1>Récupération du mot de passe</h1>
					<span class="txt">Nous allons réinitialiser votre mot de passe. Vous recevrez par mail un lien afin de redéfinir votre mot de passe.</span>
				</div>

				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@email.fr" required/>
	                </#if>				
					<div class="email-icon emailX3"></div>
				</div>

				<div class="footer">
					<input type="submit" name="submit" value="Réinitialiser" class="valider" />
				</div>

			</form>

		</div>

	</section>
  
</body>
</html>
