<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  <#include "nav.ftl">

  <div class="container">

    <div class="text-center">
    	<h2>Trouver une place de parking</h2>
	</div>
	<br/>
  	
    <div class="row"><center>Places disponibles le ${dateRecherche}</center></div>
    <div>
      <div class="col-sm-1">
      	<#if yesteday??>
      		<a href="/protected/search${yesteday}" alt="jour précédent" title="jour précédent"><span class="glyphicon glyphicon-menu-left">&nbsp;</span></a>
      	</#if>
      </div>
      <div class="col-sm-10">
      	<#list places as place>
      	  <#if place.free>
      	    <span class="col-sm-1"> <a class="label label-success" href="/protected/book/${place.placeNumber}">${place.placeNumber}</a></span>
      	  <#else>
            <span class="col-sm-1"><span class="label label-default">${place.placeNumber}</span></span>
      	  </#if>
        </#list>
        
        
      </div>
      <div class="col-sm-1"><a href="/protected/search${nextDay}" alt="jour suivant" title="jour suivant"><span class="glyphicon glyphicon-menu-right">&nbsp;</span></a></div>
    </div>
  </div>

</body>
</html>
