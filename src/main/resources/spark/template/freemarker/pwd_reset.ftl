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
					<h1>Réinitialisation du compte</h1>
					<span>Vous recevrez par mail un lien afin de red&eacute;finir votre mot de passe.</span>
				</div>

				<div class="content">
			        <#if email??>
						<input class="input" name="email" type="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          	<input class="input" name="email" type="email" placeholder="adresse@email.fr"  value="damien.urvoix@amdm.fr" required/>
	                </#if>
					<div class="email-icon emailX1"></div>
				</div>
				<div class="footer">
					<input type="submit" name="submit" value="Réinitialiser" class="valider" />
				</div>

			</form>
			
		</div>
		
	</section>
	  
</body>
</html>
