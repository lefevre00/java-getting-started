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
	<#include "nav.ftl">

    <div class="panel panel-default col-sm-8 col-sm-offset-2">
    	<div class="panel-heading">
			<h2>Voici les places que vous avez réservez :
				<a class="btn btn-primary btn-lg pull-right" href="/protected/search" role="button">R&eacute;server une place</a>
			</h2>
		</div>
		<div class="panel-body">
	  		<#if places??>
	  			<#list places as place>
		  	    	<div>
			  	     	<span class="col-md-6">Le ${place.occupationDate}</span>
			  	     	<span class="col-md-6">place n° ${place.placeNumber}</span>
			  	    </div
			    </#list>
	  		</#if>
        </div>
    </div>
  
</body>
</html>
