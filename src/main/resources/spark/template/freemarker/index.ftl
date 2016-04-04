<!DOCTYPE html>
<html lang="fr">
<head>
  	<#include "header.ftl">
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
					<h2>BIENVENUE !</h2>
					<h3>Site de partage des places de parking</h3>
					<h1>destiné aux salariés de la Mutuelle Des Motards</h1>
					
					<a href="/user/login" class="btn btn-default">SE CONNECTER</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="/user/new" class="btn btn-default">S'INSCRIRE</a>
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
					<h5>Sous-titre</h5>
					<div style="text-align:justify;">
					Quam ob rem ut ii qui superiores sunt submittere se debent in amicitia, sic quodam modo inferiores extollere. Sunt enim quidam qui molestas amicitias faciunt, cum ipsi se contemni putant; quod non fere contingit nisi iis qui etiam contemnendos se arbitrantur; qui hac opinione non modo verbis sed etiam opere levandi sunt.<br>

					Nec piget dicere avide magis hanc insulam populum Romanum invasisse quam iuste. Ptolomaeo enim rege foederato nobis et socio ob aerarii nostri angustias iusso sine ulla culpa proscribi ideoque hausto veneno voluntaria morte deleto et tributaria facta est et velut hostiles eius exuviae classi inpositae in urbem advectae sunt per Catonem, nunc repetetur ordo gestorum.<br>

					Cum saepe multa, tum memini domi in hemicyclio sedentem, ut solebat, cum et ego essem una et pauci admodum familiares, in eum sermonem illum incidere qui tum forte multis erat in ore. Meministi enim profecto, Attice, et eo magis, quod P. Sulpicio utebare multum, cum is tribunus plebis capitali odio a Q. Pompeio, qui tum erat consul, dissideret, quocum coniunctissime et amantissime vixerat, quanta esset hominum vel admiratio vel querella.<br>

					Ut enim benefici liberalesque sumus, non ut exigamus gratiam (neque enim beneficium faeneramur sed natura propensi ad liberalitatem sumus), sic amicitiam non spe mercedis adducti sed quod omnis eius fructus in ipso amore inest, expetendam putamus.			
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<h4>Vous êtes à la recherche d'une place</h4>
					<h5>Sous-titre</h5>
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




	<!-- copyright section -->
	<section id="copyright">
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
