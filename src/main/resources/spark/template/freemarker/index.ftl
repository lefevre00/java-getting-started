<!DOCTYPE html>
<html lang="fr">
<head>
  	<#include "header.ftl">
	<link rel="stylesheet" href="/css/animate.min.css">	
</head>

<body>
  
	<!-- preloader section -->
	<section class="preloader">
		<div class="sk-spinner sk-spinner-pulse"></div>
	</section>

	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">

	<!--======================================== Section d'accueil =====================================-->
	<section id="home" class="jumbotron parallax-section" style="background-color:transparent !important;">	
		<div class="container">
			<div class="row">
				<div class="wow bounceIn col-md-12 col-sm-12">
				
					<#if mail??>
						<img src="/images/logo_accueil.png" class="responsive"/>
						<br clear>
						<br clear>
						<span style="text-align:center">
							<h1 style="color:#f69b0c;">Pour information</h1> 
							<span style="color:#fff;">
								<#if placesToday lte 1>
									Il y a ${placesToday} place libre pour ${labelFirstDay}
								<#else>
									Il y a ${placesToday} places libres pour ${labelFirstDay}
								</#if>
								<#if placesDemain gt 0 >
									<br>et ${placesDemain} pour ${labelSecondDay}.
								</#if>
							</span>
						</span>
					<#else>
						<img src="/images/logo_accueil.png" class="responsive"/>
						<br clear="both"/>
						<h2 class="txtAccueil">Site de partage des places de parking</h2>
						<br clear="both"/>
						<br clear="both"/>
						<div class="col-md-3"></div>					
						<div class="col-md-3">
							<a href="/user/login" class="btn btn-default">SE CONNECTER</a>
						</div>						
						<div class="col-md-3">
							<a href="/user/new" class="btn btn-default">S'INSCRIRE</a>
						</div>	
						<div class="col-md-3"></div>						
					</#if>
					
				</div>
			</div>
		</div>		
	</section>

	
	<!--======================================== Section aide ====================================-->
	<br/><br/>
	<section id="aide" class="parallax-section">
		<div class="container">
			<div class="row">	
				<div class="col-md-offset-1 col-md-10 col-sm-12 text-center">
					<h1>Pourquoi partager ?</h1>
					<hr>
					<div style="text-align:justify;">
					Depuis quelques années, l'économie de partage a pu se répandre rapidement car elle apporte une réponse à un besoin de la société, pour plein de raisons. Elle est devenue le visage de la consommation de demain par la réutilisation ou la mutualisation de toute sorte d'objets, de service etc : C'est un échange <strong>gagnant-gagnant</strong> !<br><br>
					L'optimisation des places de parking par le partage suit cette mouvance de la nouvelle consommation. Elle est fortement lié à la volonté de celles et ceux qui sont prêts à jouer le jeu en mettant à disposition leurs places lorqu'elles sont inoccupées, <strong>rendant ainsi un service à un collègue !</strong> <br><br>
					</div>
					
					<h1 class="heading">COMMENT ÇA MARCHE ?</h1>
					<hr>
					<div style="text-align:justify;">
					Vous devez commencer par vous inscrire en créant votre compte (utilisation de l'adresse email de la Mutuelle des Motards). Un email vous sera envoyé pour l'activation du compte. <br><br>
					Si vous êtes <strong>titulaire d'une place de parking</strong>, il vous suffit alors de vous connecter et de libérer votre place pour le(s) jour(s) de votre absence.<br> 
					Pour les <strong>demandeurs</strong>, une fois connecté, vous êtes redirigé automatiquement vers la page de réservation qui vous propose d'effectuer une réservation pour 2 jours, si des places sont disponibles. En l'absence de places de parking, les deux boutons sont désactivés !<br><br>
					<strong>Alors chers/chères collègues, mutualisons nos places de parking.</strong><br><br> 
					</div>
					<h3>Vous n'avez pas encore créé votre compte</h3>
					Rendez-vous <a href="/user/new" style="color:black">ICI</a>.<br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<h3>Vous êtes titulaire d'une place</h3>
					<hr>
					<h4><i>Pour libérer votre place en votre absence</i></h4>
					<div style="text-align:justify;">
						<ol type="1">
							<li>Cliquez dans le menu <strong><a href="/protected/share" style="color:black">ACTIONS&nbsp;>>&nbsp;PARTAGE</a></strong> en haut de la page. Deux possibilités s'offrent à vous :</li>
							<li>Une libération rapide : permet de libérer la place pour les deux jours successifs</li>  
							<li>Une libération par période : dans le calendrier, renseignez le jour ou la période de votre absence, pour partager votre place.</li>
						</ol>
					</div>
					<hr>
					<h4><i>Vous avez déjà partagé votre place, mais vous en avez besoin.</i></h4>
					<div style="text-align:justify;">
					  Cliquez dans le menu <strong><a href="/protected/share" style="color:black">ACTIONS >> PARTAGE</a></strong>.<br/>
						<ol type="1">
						<li> Si ce jour-là votre place n'est pas occupée, à l'aide de la croix, vous pouvez annuler votre libération.</li>
						<li>Si la place est occupée par quelqu'un, pas de soucis, vous pouvez réservé une place libre en cliquant sur le menu <strong><a href="/protected/booked" style="color:black">ACTIONS >> RESERVATION</a></strong>.</li>
						</ol>
					</div>
				</div>
				<div class="col-md-6 col-sm-6">
					<h3>Vous êtes à la recherche d'une place</h3>
					<hr>
					<h4>Des places sont peut-être libres !</h4>
					<div style="text-align:justify;">
						<ul>
							<li>Cliquez sur le menu <strong><a href="/protected/booked" style="color:black">ACTIONS >> RESERVATION</a></strong>, sélectionnez la date souhaitée.</li>
							<li>Si une place est disponible à cette date, notez votre numéro de place et validez. </li>
						</ul>
					</div>
				</div>

			</div>
		</div>
	</section>		
	
	
	<!--======================================== Section contact ====================================-->	
	<br/><br/><br/><br/><br/><br/>
	<section id="contact" class="parallax-section">
		<div class="container">
			<div class="row">	
				<div class="col-md-offset-1 col-md-10 col-sm-12 text-center">
					<h1 class="heading">NOUS CONTACTER</h1>
					<hr>
				</div>
				<div class="col-md-offset-1 col-md-10 col-sm-12 wow fadeIn" data-wow-delay="0.9s">
					<form action="/user/contact" method="post">
						<div class="col-md-6 col-sm-6">
							<input name="nom" type="text" class="form-control" id="nom" placeholder="Nom" required>
					  	</div>
						<div class="col-md-6 col-sm-6">
							<input name="mail" type="email" class="form-control" id="mail" placeholder="Email" required>
					  	</div>
						<div class="col-md-12 col-sm-12">
							<textarea name="message" rows="8" class="form-control" id="message" placeholder="Message" required></textarea>
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
	
	
	<!--======================================== footer section  ====================================-->
	<section id="footer">
		<div class="container">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<p>EcoParking | Copyright © 2016</p>
				</div>
			</div>
		</div>
	</section>		
	
	
	
	
	<!--================================== javascripts files section  ===============================-->
	<#include "commonjs.ftl">
	
	<!-- seulement sur la page d'accueil --------------->
	<script src="/js/wow.min.js"></script>	
	<script src="/js/custom.js"></script> 	
	
</body>
</html>
