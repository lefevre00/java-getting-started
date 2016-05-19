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

	<!--=============================================== form ===========================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">			
		<div id="wrapper">
	
			<form method="post" role="form" name="login-form" class="login-form">
			
				<div class="header">
					<h1>Attribution N° place</h1>
					<span>Veuillez saisir le n° de place (si attribué).</span>
				</div>
	
				<div class="content">
					<input type="text" class="form-control" name="placeNumber" id="placeNumber" placeholder="110" size="3" maxlength="3"
						<#if placeNumber??>
							value="${placeNumber}"
						</#if>
>
				</div>
				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
				</div>
			
			</form>	
	
		</div>
	</section>
	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
  
</body>
</html>
