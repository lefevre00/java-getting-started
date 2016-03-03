<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  <#include "nav.ftl">

  <div class="container">
  	
    <div class="row"><center>Places disponibles le ${dateRecherche}</center></div>
    <div>
      <div class="col-sm-1"></div>
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
