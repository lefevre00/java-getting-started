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
		
			<h1 class="titre">Confirmation de réservation</h1>

			<div class="row " style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<#if place??>
					<h4>La place n° <strong>${place.placeNumber}</strong> est disponible pour le ${dateRecherche}</h4>
					Souhaitez-vous valider la réservation ?
					<br clear="both"/>
					<br clear="both"/>
					<a href="/protected/book/${dateBook}/${place.placeNumber}" class="btn btn-primary btn-lg">Valider</a>
				<#else>
					<#if message??>
						<h4>${message} pour le ${dateRecherche}</h4>
					<#else>
						<h4>Aucune place n'est disponible pour le ${dateRecherche}</h4>
					</#if>
				</#if>
			</div>
			
		</div>
	
	</section>

</body>
</html>
