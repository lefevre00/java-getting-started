<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">
	
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:80px;">
			<h2>Oups !</h2>
			<br>
			<img src="/images/error_image.png"/>
			<br><br>
			${message}
		</div>
	</section>

	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	
</body>
</html>
