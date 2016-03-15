<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>

  <div class="container">
    <div class="jumbotron">
    	<h2>Validation de l'adresse email</h2>
    	<br/>

		<#if ok??>
    		<div class="alert alert-success" role="alert">${message}</div>
    		<p><a class="btn btn-primary btn-lg" href="/user/login" role="button">Se connecter</a></p>
		<#else>
    	    <div class="alert alert-warning" role="alert">${message}</div>
    	</#if>
	</div>
  </div>

</body>
</html>
