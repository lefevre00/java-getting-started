<!DOCTYPE html>
<html lang="fr">
<head>
  	<#include "header.ftl">

	<link href='https://fonts.googleapis.com/css?family=Poiret+One|Yanone+Kaffeesatz|Lobster|Ubuntu+Condensed|Pacifico|Shadows+Into+Light|Comfortaa|Architects+Daughter|BenchNine|Tangerine|Playball|Great+Vibes|Bad+Script|Marck+Script|Niconne|Damion|Michroma|Allerta+Stencil|Marcellus+SC|Coda|Quantico|Alex+Brush|Julius+Sans+One|Syncopate|Teko|Fredericka+the+Great|PT+Mono|Ubuntu+Mono|Advent+Pro|Gruppo|Italianno|Mountains+of+Christmas|Federo|Henny+Penny|Dynalight|Caveat|Sarina|Montserrat+Subrayada|Krona+One|Ruthie|Open+Sans+Condensed:300|Khand|Wire+One|Oswald|Roboto+Condensed|Montserrat' rel='stylesheet' type='text/css'>
	
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
						<span><h1 style="color:#f69b0c;">Pour information :</h1> <p style="color:#fff;">Il y a ${placesToday} place(s) libre(s) pour aujourd'hui,<br>
						et ${placesDemain} pour demain.</p></span>
						
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
	
</body>
</html>
