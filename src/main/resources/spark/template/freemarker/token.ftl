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
				
			<h1>${title}</h1>

			<div class="row" style="padding-top:20px;">
				<#if ok??>
					<div class="alert alert-success" role="alert">${message}</div>
					<p><a class="btn btn-primary btn-lg" href="${urlDest}" role="button">${libelleBtn}</a></p>
				<#else>
					<div class="alert alert-warning" role="alert">${message}</div>
				</#if>
			</div>
		</div>
	</section>

		
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">	
	
</body>
</html>
