<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<meta http-equiv="refresh" content="5; URL=${routesDirectory}">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#if admin??>
		<#include "navAdmin.ftl">
	<#else>
		<#include "nav.ftl">
	</#if>
  
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
			<h2>Vous êtes déconnecté.</h2>
			<br><br>
			<img src="${ressourcesDirectory}images/deconnexion.png"/>
			<br/><br/><br/>
			Dans 5 secondes, vous serez rediriger vers la page d'accueil.<br/> 
			Merci pour votre visite.
		</div>
	</section>
  
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">	
	
</body>
</html>
