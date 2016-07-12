<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="/css/login.css">
	<link rel="stylesheet" href="/css/mediaqueries.css" type="text/css" />	
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
					
			        <input class="input password" name="mobile" type="text" placeholder="Numéro du mobile"  value="" />	     
					<div class="phone-icon phoneEdit"></div>
					
					<#if user.placeNumber??>
						<input class="input place" name="placeNumber" type="text" placeholder="N° place attribuée" value="${user.placeNumber}" required/>
					<#else>
						<input class="input place" name="placeNumber" type="text" placeholder="N° place attribuée" value="" required/>
					</#if>
					<div class="place-icon placeEdit"></div>
					
				</div>
				
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
					<a href="/protected/setting" class="register BtnRetour">Retour</a>
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