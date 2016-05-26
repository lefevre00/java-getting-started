	<!-- navigation section -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			
			<!-- =============================== Logo ================================-->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">
					<img src="/images/logo.png" class="hidden-xs" alt="EcoParking">
				</a>
				<#if mail??>
					<p class="navbar-text">
						${mail?substring(0, mail?index_of("@"))?cap_first?replace(".", " ")}
						<#if placeNumber??>
							(place : ${placeNumber})
						</#if>
					</p>
				</#if>				
			</div>			
			
			<!-- =============================== Menus ================================-->
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<#if mail??>				
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ACTIONS <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<#if canShare??>
									<li><a href="/protected/share">PARTAGE</a></li>
								</#if>
								<li><a href="/protected/booked">RÉSERVATIONS</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="/protected/setting">PARAMÈTRES</a></li>
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
						<li><a href="/user/logout">DÉCONNEXION</a></li>
					</#if>					
				</ul>
			</div>			
		</div>
	</nav>

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
