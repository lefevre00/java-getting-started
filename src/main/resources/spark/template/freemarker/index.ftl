<!DOCTYPE html>
<html lang="fr">
<head>
  	<#include "header.ftl">

	<link href='https://fonts.googleapis.com/css?family=Shadows+Into+Light' rel='stylesheet' type='text/css'>
	
	<style>
		.ShadowsIntoLight {
			font-family: 'Shadows Into Light', cursive;
		}
	</style>	
	
</head>

<body>
  
	<!-- preloader section -->
	<section class="preloader">
		<div class="sk-spinner sk-spinner-pulse"></div>
	</section>

	<!-- navigation section -->
	<#include "nav.ftl">

	<!-- home section -->
	<section id="home" class="parallax-section">
		<div class="container">
			<div class="row">
				<div class="wow bounceIn col-md-12 col-sm-12">
				
					<#if mail??>
						<img src="/images/logo_accueil.png"/>
						<br clear>
						<br clear>
						<span style="text-align:center">
							<h1 style="color:#f69b0c;">Pour information</h1> 
							<p style="color:#fff;">
								<#if placesToday lte 1>
									Il y a ${placesToday} place libre pour aujourd'hui
								<#else>
									Il y a ${placesToday} places libres pour aujourd'hui
								</#if>
								<#if placesDemain gt 0 >
									<br>et ${placesDemain} pour demain.
								</#if>
							</p>
						</span>
					<#else>
						<img src="/images/logo_accueil.png"/>
						<br clear="both"/>
						<span class="ShadowsIntoLight" style="font-size: 28px;">Site de partage des places de parking</span>
						<br clear="both"/>
						<br clear="both"/>
						<a href="/user/login" class="btn btn-default">SE CONNECTER</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="/user/new" class="btn btn-default">S'INSCRIRE</a>
					</#if>
					
				</div>
			</div>
		</div>		
	</section>

	<#include "footer.ftl">
	
	<!-- Cacher le menu sur le mobile après un clique ------>
	<script>
	$(function(){ 
		var navMain = $("#navbar");
		navMain.on("click", "a", null, function () {
			navMain.collapse('hide');
		});
	});	
	</script>	
	
</body>
</html>
