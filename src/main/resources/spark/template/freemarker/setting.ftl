<!DOCTYPE html>
<html lang="fr">
<head>

	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />

	<#include "header.ftl">
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">	

	<!--=============================================== form ===========================================-->
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
		
			<h1>Paramètres du compte</h1>
			
            <div class="row">
                <div class="col-md-offset-1 col-md-5 col-sm-5"> 
                    <div class="panel panel-default" >
                        <div class="panel-heading ">- Informations personnelles - </div>
                        <div class="panel-body">
                            Email : ${user.emailAMDM}<br/>
                            Numéro de place attribuée : <#if user.placeNumber??>${user.placeNumber}<else>-</#if>
                            <br>
                            <hr>
                            <small>Vous avez la possibilité de modifier votre adresse email, numéro de téléphone.</small>
                            <br/><br/>
                            <!--a href="${routesDirectory}protected/userEdit?email=${user.emailAMDM}" class="btn btn-primary tailleText">Modifier</a-->
                            <a href="#" class="btn btn-primary tailleText  disabled">Modifier</a> 		
                        </div>
                    </div>
                </div>
                
                <div class="col-md-5 col-sm-5">
                    <div class="panel panel-default" >
                        <div class="panel-heading">- Historiques d'utilisations - </div>
                        <div class="panel-body">
                            historiques de réservation<br>
                            historiques de partage.
                            <br>
                            <hr>
                            <small>Vous avez la possibilité de consulter l'historique de votre activité de partage et/ou de réservation.</small>
                            <br><br>
                            <!--a href="${routesDirectory}protected/history" class="btn btn-primary tailleText">Visualiser</a-->
                            <a href="${routesDirectory}protected/statistics?var=p&email=${user.emailAMDM}&place=<#if user.placeNumber??>${user.placeNumber}</#if>" class="btn btn-primary tailleText">Visualiser</a>                         
                        </div>
                    </div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-offset-1 col-md-5 col-sm-5"> 
                    <div class="panel panel-default" >
                        <div class="panel-heading ">- Mot de passe -</div>
                        <div class="panel-body">
                           <small>Choisissez un mot de passe assez long en mélangeant caractères, chiffres et caractères spéciaux. </small>
                            <br><br>
                            <a href="${routesDirectory}protected/change_pwd" class="btn btn-primary tailleText">Changer</a>                            
                        </div>
                    </div>
                </div>
                
                <div class="col-md-5 col-sm-5">
                    <div class="panel panel-default" >
                        <div class="panel-heading">- Suppression du compte -</div>
                        <div class="panel-body">
                            <small>Vous avez la possibilité de supprimer votre compte.</small>
                            <br><br><br>
                            <a href="${routesDirectory}protected/unregister" class="btn btn-primary tailleText" data-confirm='Etes-vous sûr de vouloir supprimer votre compte ?' >Supprimer</a>
                        </div>
                    </div> 
                </div>
            </div>

		</div>
	</section>
	
	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">		
	<script src="${ressourcesDirectory}js/confirm.js"></script>
</body>
</html>
