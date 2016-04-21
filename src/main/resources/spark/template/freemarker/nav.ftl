<!-- navigation section -->
<section class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		
		<!-- =============================== Logo ================================-->
		<div class="navbar-header">
			<button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon icon-bar"></span>
				<span class="icon icon-bar"></span>
				<span class="icon icon-bar"></span>
			</button>
			<a href="/" class="navbar-brand" >
				<img src="/images/logo.png" class="hidden-xs" alt="CoParking">
			</a>
			<#if mail??>
				<p class="navbar-text">${mail}</p>
			</#if>
		</div>
		
		<!-- =============================== Menus ================================-->
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<#if mail??>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true">ACTIONS <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<#if canShare??>
								<li><a href="/protected/share">Partage</a></li>
							</#if>
							<li><a href="/protected/booked">Réservation</a></li>
							<li><a href="/protected/setting">Paramètres</a></li>
						</ul>
					</li>
				</#if>
				<li><a href="/#aide">AIDE</a></li>
				<li><a href="/#contact">CONTACT</a></li>
				<#if mail??>
					<li><a href="/user/logout">DECONNEXION</a></li>
				</#if>
			</ul>
		</div>
		
	</div>
</section>
