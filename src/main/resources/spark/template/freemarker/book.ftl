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
			<h1>Confirmation de réservation</h1>

			<div class="row " style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<h4>La place numéro XXX vous est réservée pour le ${dateRecherche}.</h4>
			</div>						
			
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div>
					<center>
						${message}
					</center>
				</div>
			</div>
		</div>
	</section>

</body>
</html>
