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
				<a class="navbar-brand" href="/protected/adminPage">
					<img src="/images/logo.png" class="hidden-xs" alt="EcoParking">
				</a>
				<#if mail??>
					<p class="navbar-text">
						Accès administrateur
					</p>
				</#if>				
			</div>			
			
			<!-- =============================== Menus ================================-->
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/user/logout">DÉCONNEXION</a></li>
				</ul>				
			</div>			
		</div>
	</nav>

	<div class="alertSpace">
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
	</div>