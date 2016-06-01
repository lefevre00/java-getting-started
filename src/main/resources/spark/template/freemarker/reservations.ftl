<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	
	<style>
		.tailleText{
			font-size :14px;
			padding : 7px 12px 7px 12px;
		}
	</style>
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	
	
	
	<!--================================================================================================-->	
    <section class="content-section">
		<div class="container containerAttr">
		
			<h1 class="titre">Réservations</h1>

			<!--============================= boutons de réservation ================================-->
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div class="panel panel-default" >
					<div class="panel-heading">Nous sommes ${dateDuJour}
						<#if showToday?? || showTomorrow??>
							, réserver votre place pour 
						</#if>
					</div>
					<div class="panel-body">
						<div class="col-sm-6" style="margin-bottom:10px;">
							<#if showToday??>
								<a href="/protected/search?day=${showToday}" class="btn btn-primary btn-lg tailleText">${libelleShowToday}</a>
							<#else>
								<a href="/protected/search" class="btn btn-primary btn-lg disabled tailleText">${libelleShowToday}</a>						
							</#if>
						</div>
						<div class="col-sm-6" style="margin-bottom:10px;">
							<#if showTomorrow??>
								<a href="/protected/search?day=${showTomorrow}" class="btn btn-primary btn-lg tailleText">${libelleShowTomorrow}</a>
							<#else>
								<a href="/protected/search" class="btn btn-primary btn-lg disabled tailleText">${libelleShowTomorrow}</a>
							</#if>
						</div>
					</div>
				</div>
			</div>

			<#if !showToday?? || !showTomorrow??>
				<#if canShare??>
					<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
						<div class="panel panel-default" >
							<div class="panel-heading"><span class="glyphicon glyphicon-info-sign fa-2x text-primary"></span></div>
							<div class="panel-body">
								Vous ne pouvez pas effectuer de réservation car votre place est disponible ou inoccupée les deux prochains jours.
							</div>
						</div>
					</div>
				</#if>
			</#if>


			<!--============================= Message réservation  ================================-->
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
			
				<h4>
					<#if places??>
						Voici les places que vous avez réservées :
					<#else>
						Aucune réservation enregistrée.
					</#if>
				</h4>
								
				<#if places??>
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-condensed padding20">
							<tr style="background-color: #337ab7; color: white;">
								<th style="text-align:center;">Date</th> 
								<th style="text-align:center;">N° place</th>
								<th style="text-align:center;">Annuler</th>
							</tr>
							<#list places as place>
								<tr> 
					  	    		<td>
					  	    			<#assign theDate = '${place.occupationDate}'?date("yyyy-MM-dd")>
					  	    			${theDate?string["EEEE dd/MM/yyyy"]}
					  	    		</td>
						  	     	<td>${place.placeNumber}</td>
						  	     	<td>
						  	     		<a href="?release=${place.occupationDate}" data-confirm='Annuler la réservation du <strong> ${theDate?string["dd/MM/yyyy"]} </strong> ?' ><img src="/images/cancel.png"/></a>							  	     		
						  	     	</td>
								</tr>
							</#list>
						</table>
					</div>
				</#if>
			</div>			

		</div>
	</section>

	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	<script src="/js/confirm.js"></script>
</body>
</html>
