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
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<#if mail??>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true">ACTIONS <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<#if canShare??>
								<li><a href="/protected/share">PARTAGE</a></li>
							</#if>
							<li><a href="/protected/booked">RESERVATION</a></li>
							<li><a href="/protected/setting">PARAMETRES</a></li>
						</ul>
					</li>
				</#if>
				<li>
					<a href="/#aide" class="hidden-xs">AIDE</a>
					<a href="/#aide" class="visible-xs" data-toggle="collapse" data-target=".navbar-collapse">AIDE</a>
				</li>
				<li>
					<a href="/#contact" class="hidden-xs">CONTACT</a>
					<a href="/#contact" class="visible-xs" data-toggle="collapse" data-target=".navbar-collapse">CONTACT</a>
				</li>
				<#if mail??>
					<li><a href="/user/logout">DECONNEXION</a></li>
				</#if>
			</ul>
		</div>
	</div>
</section>

	<#if error??>
		<div class="alert alert-danger fade in">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Erreur : </strong>${error}
		</div>
	</#if> 
	
	<#if info??>
		<div class="alert alert-info fade in">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Information : </strong>${info}
		</div>
	</#if>
	
	<#if success??>
		<div class="alert alert-success fade in">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Succès : </strong>${success}
		</div>
	</#if>
