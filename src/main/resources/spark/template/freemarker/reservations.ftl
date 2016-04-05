<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">
	
    <section class="content-section">
		<div class="container containerAttr">
		
			<h1>Réservations</h1>
			
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div class="col-sm-9" style="text-align:left;"><h4>Voici les places que vous avez réservées :</h4></div>
				<div class="col-sm-3"><a href="/protected/search" class="btn btn-primary btn-lg">Réserver une place</a></div>
			</div>
			<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<#if places??>
					<table class="table table-bordered table-striped table-condensed padding20">
						<tr style="background-color: #337ab7; color: white;">
							<th style="text-align:center;">Date</th> 
							<th style="text-align:center;">N° place</th>
						</tr>
						<#list places as place>
							<tr> 
								<td>${place.occupationDate}</td> 
								<td>${place.placeNumber}</td> 
							</tr>
						</#list>
					</table>
				</#if>
			</div>
		</div>
	</section>

</body>
</html>
