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
		
			<h1>Trouver une place de parking</h1>

			<div class="row " style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<h4>Places disponibles le ${dateRecherche}</h4>
			</div>
			
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div>
					<div class="col-sm-1">
						<#if previous??>
							<a href="/protected/search?day=${previous}" alt="jour précédent" title="jour précédent"><span class="glyphicon glyphicon-menu-left">&nbsp;</span></a>
						</#if>
					</div>
					<div class="col-sm-10">
						<#if places??>
							<#list places as place>
								<span class="col-sm-1"> <a class="label label-success" href="/protected/book/${dateBook}/${place.placeNumber}">${place.placeNumber}</a></span>
							</#list>
						<#else>
							Aucune place disponible à cette date.
						</#if>
					</div>
					<div class="col-sm-1">
						<a href="/protected/search?day=${next}" alt="jour suivant" title="jour suivant"><span class="glyphicon glyphicon-menu-right">&nbsp;</span></a>
					</div>
				</div>
			</div>
		</div>
	
	</section>

</body>
</html>
