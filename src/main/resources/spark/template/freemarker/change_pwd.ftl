<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="/css/login.css">
	<link rel="stylesheet" href="/css/mediaqueries.css" type="text/css" />	
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	
    
	<!--======================================== Form =====================================-->
	<section id="conteneur" class="jumbotron" style="background-color:transparent !important;">	
		<div id="wrapper">

			<form method="post" role="form" name="login-form" class="login-form">
				<div class="header">
					<h1>Modifier<br/> votre mot de passe</h1>
					<span>Veuillez compléter les champs ci-dessous.</span>
				</div>

				<div class="content">
					<input class="input" name="pwd" id="pwd" type="password" placeholder="Mot de passe actuel" required/>
					<div class="pass-icon pwdActual"></div>					
					
					<input class="input password" name="newPwd" id="newPwd" type="password" placeholder="Nouveau mot de passe" required/>
					<div class="pass-icon-new pwdNew"></div>
					
					<input class="input password" name="confirmPwd" id="confirmPwd" type="password"  placeholder="Confirmer nouveau mot de passe" required/>
					<div class="pass-icon-new confirmPwd"></div>
				</div>

				<div class="footer">
					<input type="submit" name="submit" value="Valider" class="valider" />
				</div>
			</form>

		</div>
	</section>


	<!--================================== javascripts files section  ===============================-->
	<#include "commonjs.ftl">	
	
	<!-- cryptage md5 -->
	<#include "md5.ftl">
</body>
</html>