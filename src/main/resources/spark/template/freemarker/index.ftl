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
				
					<#if logged??>
						<img src="/images/logo_accueil.png"/>
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


	<!-- Section aide -->
	<section id="aide" class="parallax-section">
		<div class="container">
			<div class="row">
				<div class="col-md-offset-2 col-md-8 col-sm-12 text-center">
					<h1 class="heading">COMMENT ÇA MARCHE ?</h1>
					<hr>
				</div>
				<div class="col-md-6 col-sm-6">
					<h4>Vous êtes titulaire d'une place</h4>
					<h5>Faites-en profiter les autres en votre absence</h5>
					<div style="text-align:justify;">
					Quam ob rem ut ii qui superiores sunt submittere se debent in amicitia, sic quodam modo inferiores extollere. Sunt enim quidam qui molestas amicitias faciunt, cum ipsi se contemni putant; quod non fere contingit nisi iis qui etiam contemnendos se arbitrantur; qui hac opinione non modo verbis sed etiam opere levandi sunt.<br>

					Nec piget dicere avide magis hanc insulam populum Romanum invasisse quam iuste. Ptolomaeo enim rege foederato nobis et socio ob aerarii nostri angustias iusso sine ulla culpa proscribi ideoque hausto veneno voluntaria morte deleto et tributaria facta est et velut hostiles eius exuviae classi inpositae in urbem advectae sunt per Catonem, nunc repetetur ordo gestorum.<br>

					Cum saepe multa, tum memini domi in hemicyclio sedentem, ut solebat, cum et ego essem una et pauci admodum familiares, in eum sermonem illum incidere qui tum forte multis erat in ore. Meministi enim profecto, Attice, et eo magis, quod P. Sulpicio utebare multum, cum is tribunus plebis capitali odio a Q. Pompeio, qui tum erat consul, dissideret, quocum coniunctissime et amantissime vixerat, quanta esset hominum vel admiratio vel querella.<br>

					Ut enim benefici liberalesque sumus, non ut exigamus gratiam (neque enim beneficium faeneramur sed natura propensi ad liberalitatem sumus), sic amicitiam non spe mercedis adducti sed quod omnis eius fructus in ipso amore inest, expetendam putamus.			
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<h4>Vous êtes à la recherche d'une place</h4>
					<h5>Des places sont peut-être libres !</h5>
					<div style="text-align:justify;">
					Sed laeditur hic coetuum magnificus splendor levitate paucorum incondita, ubi nati sunt non reputantium, sed tamquam indulta licentia vitiis ad errores lapsorum ac lasciviam. ut enim Simonides lyricus docet, beate perfecta ratione vieturo ante alia patriam esse convenit gloriosam.<br>

					Quam ob rem ut ii qui superiores sunt submittere se debent in amicitia, sic quodam modo inferiores extollere. Sunt enim quidam qui molestas amicitias faciunt, cum ipsi se contemni putant; quod non fere contingit nisi iis qui etiam contemnendos se arbitrantur; qui hac opinione non modo verbis sed etiam opere levandi sunt.<br>

					Cum saepe multa, tum memini domi in hemicyclio sedentem, ut solebat, cum et ego essem una et pauci admodum familiares, in eum sermonem illum incidere qui tum forte multis erat in ore. Meministi enim profecto, Attice, et eo magis, quod P. Sulpicio utebare multum, cum is tribunus plebis capitali odio a Q. Pompeio, qui tum erat consul, dissideret, quocum coniunctissime et amantissime vixerat, quanta esset hominum vel admiratio vel querella.<br>

					Ut enim benefici liberalesque sumus, non ut exigamus gratiam (neque enim beneficium faeneramur sed natura propensi ad liberalitatem sumus), sic amicitiam non spe mercedis adducti sed quod omnis eius fructus in ipso amore inest, expetendam putamus.			
					</div>
				</div>

			</div>
		</div>
	</section>		




	<!-- Section contact -->
	<section id="contact" class="parallax-section">
		<div class="container">
			<div class="row">
				<div class="col-md-offset-1 col-md-10 col-sm-12 text-center">
					<h1 class="heading">NOUS CONTACTER</h1>
					<hr>
				</div>
				<div class="col-md-offset-1 col-md-10 col-sm-12 wow fadeIn" data-wow-delay="0.9s">
					<form action="#" method="post">
						<div class="col-md-6 col-sm-6">
							<input name="nom" type="text" class="form-control" id="nom" placeholder="Nom">
					  	</div>
						<div class="col-md-6 col-sm-6">
							<input name="email" type="email" class="form-control" id="email" placeholder="Email">
					  	</div>
						<div class="col-md-12 col-sm-12">
							<textarea name="message" rows="8" class="form-control" id="message" placeholder="Message"></textarea>
						</div>
						<div class="col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6">
							<input name="submit" type="submit" class="form-control" id="submit" value="ENVOYER">
						</div>
					</form>
				</div>
				<div class="col-md-2 col-sm-1"></div>
			</div>
		</div>
	</section>




	<!-- footer section -->
	<section id="footer">
		<div class="container">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<p>CoParking | Copyright © 2016</p>
				</div>
			</div>
		</div>
	</section>

	
	
	<!-- JAVASCRIPT JS FILES -->	
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.parallax.js"></script>
	<script src="js/smoothscroll.js"></script>
	<script src="js/nivo-lightbox.min.js"></script>
	<script src="js/wow.min.js"></script>
	<script src="js/custom.js"></script>

</body>
</html>
