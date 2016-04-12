<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">

    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:80px;">
			<h2>Erreur !</h2>
			<br><br>
			<img src="/images/error_image.png"/>
			<br><br>
			${message}
		</div>
	</section>

</body>
</html>
