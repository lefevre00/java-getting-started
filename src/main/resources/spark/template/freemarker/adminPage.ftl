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
                        <div class="panel-heading ">- Liste utilisateurs - </div>
                        <div class="panel-body">
                        	Accéder à la liste de tous les utilisateurs de l'application.
                        	Modifier des données / Libérer des places.       
                            <!--Récupérer la liste des utilisateurs au format CSV <br>en cliquant <a href="" class="linkClass">ici</a--> <br/>
                            <hr>
                            <small>Visualisez la liste des utilisateurs de l'application.</small>
                            <br/><br/>
                            <a href="${routesDirectory}protected/usersList" class="btn btn-primary tailleText">Visualiser</a>	
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