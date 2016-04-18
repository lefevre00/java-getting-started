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
			<#if info??>
				<div class="alert alert-info">
					<strong>Information : </strong>${info}
				</div>
			</#if>
	
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
  
  <#include "footer.ftl">
	
	
</body>
</html>
