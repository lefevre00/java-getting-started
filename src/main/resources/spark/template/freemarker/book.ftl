<!DOCTYPE html>
<html lang="fr">
<head>
  <#include "header.ftl">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">

	<div class="container">
		<div class="text-center">
	    	<h2>Réserver une place de parking</h2>
		</div>
		<br/>
	  	
	    <div class="row">
	    	<center>
	    		Vous avez demandez une place pour le ${dateRecherche}.
	    		<br/>
	    		${message}
	    	</center>
	    </div>
	</div>

</body>
</html>
