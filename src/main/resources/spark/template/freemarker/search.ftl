<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	

	<!--================================================================================================-->	
    <section class="content-section">
		<div class="container containerAttr">
		
			<h1 class="titre">Confirmation de r&eacute;servation</h1>

			<div class="row " style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<#if place??>
					<h4>La place n&deg; <strong>${place.placeNumber}</strong> est disponible pour le ${dateRecherche}</h4>
					Souhaitez-vous valider la r&eacute;servation ?
					<br clear="both"/>
					<br clear="both"/>
					
					<a href="/protected/booked" class="btn btn-info btn-lg"> Retour</a>
					&nbsp;
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
	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	
</body>
</html>
