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
    
	<div class="container">
	  	<div class="jumbotron">
			<form method="post" role="form">
				<input type="hidden" name="token" value="${token}">
				<div class="form-group">Nous allons redéfinir le mot de passe pour votre compte. Veuillez compléter les champs ci-dessous.</div>	
			    <div class="form-group">
				    <label for="email">Email</label>
				    <#if email??>
						<input type="email" class="form-control" name="email" placeholder="adresse@email.fr" value="${email}" required/>
				    <#else>
				        <input type="email" class="form-control" name="email" placeholder="adresse@email.fr" required/>
		            </#if>
				</div>			
			    <div class="form-group">
				    <label for="mdp">Nouveau mot de passe</label>
			        <input type="password" class="form-control" name="mdp" placeholder="motdepasse" required/>
				</div>			
				<div class="form-group">
					<input type="submit" class="btn btn-success" value="Valider"/>
				</div>
			</form>
	    </div>
	</div>
  
</body>
</html>
