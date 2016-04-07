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
		
			<h1>Réservations</h1>
			
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div class="col-sm-8" style="text-align:left;"><h4>Voici les places que vous avez réservées :</h4></div>
				<div class="col-sm-4" style="text-align:right;"><a href="/protected/search" class="btn btn-primary btn-lg">Réserver une place</a></div>
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
								<#if placenumber == "">
					  	    		<td>Le ${place.occupationDate}</td>
						  	     	<td>(${place.occupiedBy})</td>
						  	     	<td>place n° ${place.placeNumber}</td>
						  	    <#else>
						  	    	<td>Le ${place.occupationDate}</td>
						  	     	<td>place n° ${place.placeNumber}</td>
					  	    	</#if>
							</tr>
						</#list>
					</table>
				</#if>
			</div>
		</div>
	</section>

</body>
</html>
