<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="${ressourcesDirectory}css/login.css">
	<link rel="stylesheet" href="${ressourcesDirectory}css/mediaqueries.css" type="text/css" />	
</head> 

<body>     

	<!--======================================== Section navigation ====================================-->
	<#if admin??>
		<#include "navAdmin.ftl">
	<#else>
		<#include "nav.ftl">
	</#if>

	<!--======================================== login =====================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">	

		<div id="editWrapper">	

			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1>Données utilisateur</h1>
					<span>Veuillez renseigner/modifier les champs ci-dessous.</span>
				</div>

				<div class="content">
				
					<#if user.id??>
						<input type="hidden" name="idUser" value="${user.id}">
			    	<#else>
			    		<input type="hidden" name="idUser" value="">
			    	</#if>
			    	
			        <input class="input" name="email" type="email" placeholder="Adresse email"  value="${user.emailAMDM}" required/>	     
					<div class="email-icon emailEdit"></div>
					
			        <!--input class="input password" name="mobile" type="text" placeholder="Numéro du mobile"  value="" />	     
					<div class="phone-icon phoneEdit"></div-->
					
					<#if user.placeNumber??>
						<input class="input place" name="placeNumber" type="text" placeholder="N° place attribuée" value="${user.placeNumber}" <#if !admin??>readonly</#if> />
					<#else>
						<input class="input place" name="placeNumber" type="text" placeholder="N° place attribuée" value="" <#if !admin??>readonly</#if> />
					</#if>
					<div class="place-icon placeEdit"></div>
					envoi mail information : <input type="checkbox" "checked" id="mailInformation" name="mailInformation"/>
					
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
					<#if admin??>
						<a href="${routesDirectory}protected/usersList" class="register BtnRetour">Retour</a>
					<#else>
						<a href="${routesDirectory}protected/setting" class="register BtnRetour">Retour</a>
					</#if>
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