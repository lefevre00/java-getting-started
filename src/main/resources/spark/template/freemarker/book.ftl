<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">

    <section class="content-section">
		<div class="container containerAttr">
		
			<h1 class="titre">Confirmation de r&eacute;servation</h1>
					<#assign theDate = '${dateRecherche}'?date("yyyy-MM-dd")>
					${theDate?string["dd/MM/yyyy"]}	
			<div class="row " style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<h4>La place numéro ${numeroPlace} vous est réservée pour le ${theDate?string["dd/MM/yyyy"]}.</h4>
			</div>
			
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div>
					<center>
						${message}
						<br/>
						<a href="/protected/booked" class="btn btn-info btn-lg">Retour</a>
					  	     		
					</center>
				</div>
			</div>
			
		</div>
	</section>
	
	<!--================================== javascripts files section  ===============================-->
	<#include "commonjs.ftl">	
	
</body>
</html>
