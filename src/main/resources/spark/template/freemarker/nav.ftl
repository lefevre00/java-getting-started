<!-- navigation section -->
<section class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon icon-bar"></span>
				<span class="icon icon-bar"></span>
				<span class="icon icon-bar"></span>
			</button>
			<#if logged??>
			<a href="/protected/" class="navbar-brand" >
			<#else>
			<a href="/" class="navbar-brand" >
			</#if>
				<img src="/images/logo.png" class="hidden-xs" alt="CoParking">
			</a>			
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<#if logged??>
				<li class="dropdown dropdown-submenu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">ACTIONS</a>
					<ul class="dropdown-menu">
						<#if shared??><li><a href="/protected/share">Partage</a></li></#if>
						<li><a href="/protected/booked">Réservation</a></li>
						<li><a href="/protected/setting">Paramètres</a></li>
					</ul>
				</li>
				<#else>
				<li><a href="/">ACCUEIL</a></li>
				</#if>
				<li><a href="/#aide">AIDE</a></li>
				<li><a href="/#contact">CONTACT</a></li>
				<#if logged??>
				<li><a href="/user/logout">DECONNEXION</a></li>
				</#if>
			</ul>
		</div>
	</div>
</section>
