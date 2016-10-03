<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
</head>

<body>   

	<!--======================================== Section navigation ====================================-->
	<#include "navAdmin.ftl">


	<!--================================================================================================-->
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
			<h1>Administration</h1>
			
            <div class="row">			
                <div class="col-md-offset-1 col-md-5 col-sm-5"> 
                    <div class="panel panel-default" >
                        <div class="panel-heading ">- Gestion des utilisateurs - </div>
                        <div class="panel-body">
                        
	                        <div class="media">
							  <div class="media-left media-middle">
							    <a href="${routesDirectory}protected/usersList" class="btn btn-primary tailleText">
							    	<i class="fa fa-users" aria-hidden="true"></i>&nbsp;Visualiser
							    </a>
							  </div>
							  <div class="media-body">
							    <h4 class="media-heading">Liste des utilisateurs</h4>
							     Modifier des données / libérer des places.       
                                 <!--Récupérer la liste des utilisateurs au format CSV <br>en cliquant <a href="" class="linkClass">ici</a--> <br/>
							  </div>
							</div>
							
	                        <div class="media">
							  <div class="media-left media-middle">
							    <a href="${routesDirectory}protected/adminCreate" class="btn btn-primary tailleText">
							    	<i class="fa fa-user-plus" aria-hidden="true"></i>&nbsp;Créer
							    </a>
							  </div>
							  <div class="media-body">
							    <h4 class="media-heading">Ajouter un utilisateur</h4>
							  </div>
							</div>
                                                    	
                        </div>
                    </div>
                </div>
                <div class="col-md-5 col-sm-5">
                    <div class="panel panel-default" >
                        <div class="panel-heading">- Statistiques d'utilisation - </div>
                        <div class="panel-body">
							historiques de partage<br>
                            historiques de réservation
							<br>
                            <hr>
                            <small>Consulter les statistiques d'utilisation par période.<br/></small>
                            <br/>
                            <a href="${routesDirectory}protected/statistics" class="btn btn-primary tailleText">Visualiser</a>	                            
                        </div>
                    </div> 
                </div>
            </div>
			           
		</div>
	</section>


	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">


</body> 
</html> 