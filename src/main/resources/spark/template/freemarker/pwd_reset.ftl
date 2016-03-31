<!DOCTYPE html>
<html lang="fr">
<head>
  <#include "header.ftl">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">
  
  	<#if error??>
	  	<div class="alert alert-danger">
	  		<strong>Erreur : </strong>${error}.
	  	</div>
  	</#if>
    
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<form method="post" role="form">
					<div class="form-group">Nous allons r&eacute;initialiser votre compte. Vous recevrez par mail un lien afin de red&eacute;finir votre mot de passe.</div>	
					<div class="form-group">
						<label for="email">Email</label>
						<#if email??>
							<input type="email" class="form-control" name="email" placeholder="adresse@email.fr" value="${email}"/>
						<#else>
							<input type="email" class="form-control" name="email" placeholder="adresse@email.fr"/>
						</#if>
					</div>			
					<div class="form-group">
						<input type="submit" class="btn btn-success" value="R&eacute;initialiser"/>
					</div>
				</form>
			</div>
		</div>
	</div>
  
</body>
</html>
