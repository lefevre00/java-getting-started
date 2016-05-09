<!DOCTYPE html>
<html lang="fr">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content=""> 
	<#include "header.ftl">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">
	
    <section class="content-section">
		<div class="container containerAttr">
		
			<h1 class="titre">Mes réservations</h1>

			<!--============================= boutons de réservation ================================-->
			<div class="row" style="margin:0px auto;max-width:700px;">
				Nous sommes ${dateDuJour}<br/>
				<div class="col-sm-6" style="padding-top:20px;">
					<#if showToday??>
						<a href="/protected/search?day=${showToday}" class="btn btn-primary btn-lg">${libelleShowToday}</a>
					<#else>
						<a href="/protected/search" class="btn btn-primary btn-lg disabled">${libelleShowToday}</a>						
					</#if>				
				</div>
				<div class="col-sm-6" style="padding-top:20px;">
					<#if showTomorrow??>
						<a href="/protected/search?day=${showTomorrow}" class="btn btn-primary btn-lg">${libelleShowTomorrow}</a>
					<#else>
						<a href="/protected/search" class="btn btn-primary btn-lg disabled">${libelleShowTomorrow}</a>
					</#if>				
				</div>
			</div>

			<#if !showToday?? || !showTomorrow??>
				<div class="row">
					<br/>
					<#if canShare??>
						Vous ne pouvez pas effectuer de réservation car votre place est disponible ou inoccupée les deux prochains jours. 
					<#else>
						Vous avez déjà réservé des places pour les deux prochains jours. 
					</#if>
				</div>
			</#if>


			<!--============================= Message réservation  ================================-->
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
			
				<h4>
					<#if places??>
						Voici les places que vous avez réservées. 
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
					  	    			Le ${theDate?string["dd/MM/yyyy"]}
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

	
	
	<script type="text/javascript">
		$(function() {
			$('a[data-confirm]').click(function(ev) {
				var href = $(this).attr('href');
				
				if (!$('#dataConfirmModal').length) {
					$('body').append('<div id="dataConfirmModal" class="modal padding120" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content modalColor"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel"><strong>Confirmation d\'annulation</strong></h3></div><div class="modal-body"></div><div class="modal-footer footerColor"><button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Non</button><a class="btn btn-primary" id="dataConfirmOK">Oui</a></div></div></div></div>');
				}
				$('#dataConfirmModal').find('.modal-body').html($(this).attr('data-confirm'));
				$('#dataConfirmOK').attr('href', href);
				$('#dataConfirmModal').modal({show:true});
				
				return false;
			});
		});		
	</script>
</body>
</html>
