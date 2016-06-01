<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<style>
		.input-group {
			display: inline-flex;
		}
		.input-group-addon {
			width: auto;
			height: 34px;
			font-size: 14px;
		}
	</style>
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	

	<!--=============================================== form ===========================================-->
    <section class="content-section">
		<div class="container containerAttr">
			<h1 class="titre">Paramètres</h1>

			<div class="row">

				<!-- Modification n° place -->
				<div class="col-md-6">
					<form action="/protected/setting" method="post" role="form" name="place-form">
						<div class="panel panel-default" >
							<div class="panel-heading">Place attribuée</div>
							<div class="panel-body">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">Numéro</span>
									<input type="text" class="form-control" name="placeNumber" aria-describedby="basic-addon1" id="placeNumber" placeholder="110" size="3" maxlength="3"
									<#if placeNumber??>
										value="${placeNumber}"
									</#if>
									>
								</div>
								&nbsp;
								<input type="submit" name="submit" value="Valider" class="btn btn-primary" />
							</div>
						</div>
					</form>
				</div>
				
				<!-- Suppression de compte -->
				<div class="col-md-6">
					<div class="panel panel-default" >
						<div class="panel-heading">Suppression de compte</div>
						<div class="panel-body">
							<a href="/protected/unregister" class="btn btn-primary" 
							  data-confirm='Suppression de votre compte ?' ><img src="/images/cancel.png"/>
							  Supprimer
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	<script src="/js/confirm.js"></script>
</body>
</html>
