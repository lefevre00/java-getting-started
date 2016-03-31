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
			<strong>Erreur : </strong>${error}
		</div>
	</#if>  
	<#if info??>
		<div class="alert alert-info">
			<strong>Information : </strong>${info}
		</div>
	</#if>  
	  
	<div class="jumbotron">
		<div class="container">
			<div class="form-container">
				<form method="post" role="form">
					<div class="form-group">
						<label for="pwd">Num&eacute;ro de place (si attribu&eacute;)</label>
						<input type="text" class="form-control" name="placeNumber" id="placeNumber" placeholder="110" size="3" maxlength="3"
							<#if placeNumber??>
								value="${placeNumber}"
							</#if>
>						
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-success" value="Valider"/>
					</div>
				</form>
			</div>
		</div>
	</div> 
  
</body>
</html>
