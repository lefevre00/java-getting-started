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
			<h1><i class="fa fa-thumbs-o-up"></i> ${title}</h1>
			<div class="row">
				<div class="alert alert-success" role="alert">
					Un mail vient de vous être envoyé à l'adresse fournie.
					<br/>
					${message}
				</div>
			</div>
		</div>
	</section>

	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">	
	
</body>
</html>
