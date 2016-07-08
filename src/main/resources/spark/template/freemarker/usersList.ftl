<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	
    <link href="/css/bootstrap-table.css" rel="stylesheet">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap-table.js"></script>
    <style>
		.table th {
		   text-align: center;
		} 
    </style>	
</head> 
<body>     

	<!--======================================== Section navigation ====================================-->
	<#include "navAdmin.ftl">


	<!--================================================================================================-->
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
			<h1>Liste des utilisateurs</h1>
			
			<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:30px;">
				<#if (usersList?size > 7)>
					<table class="table table-striped table-condensed padding20"
						   id="table" 
						   data-toggle="table"
						   data-height="387" 
						   data-pagination="true"
						   data-page-size="7"								   							   
						   data-pagination-pre-text="< "
						   data-pagination-next-text=" >">							
								
						<thead style="background-color: #f5f5f5; color: #317bba;">
							<tr>
								<th style="text-align:center;">Utilisateur</th> 
								<th style="text-align:center;">N° place</th> 
								<!--th style="text-align:center;">Nb connexion</th> 
								<th style="text-align:center;">Nb partage</th> 
								<th style="text-align:center;">Nb réservation </th--> 
								<th style="text-align:center;">Etat compte</th> 
								<th style="text-align:center;">Editer</th> 
							</tr>
						</thead>
				<#else>
					<table class="table table-bordered table-striped table-condensed padding20">
						<tr style="background-color: #f5f5f5; color: #317bba;">
							<th style="text-align:center;">Utilisateur</th> 
							<th style="text-align:center;">N° place</th> 
							<!--th style="text-align:center;">Nb connexion</th> 
							<th style="text-align:center;">Nb partage</th> 
							<th style="text-align:center;">Nb réservation </th--> 
							<th style="text-align:center;">Etat compte</th> 
							<th style="text-align:center;">Editer</th> 							
						</tr>
				</#if>			

				<#list usersList as user>
					<tr> 
		  	    		<td>
		  	    			${user.emailAMDM?substring(0, user.emailAMDM?index_of("@"))?upper_case?replace(".", " ")}
		  	    		</td>
		  	    		<td>
		  	    			<#if user.placeNumber??>
		  	    				${user.placeNumber}
		  	    			<#else>
		  	    				-
		  	    			</#if>
		  	    		</td>		
		  	    		<td><#if user.tokenMail??>Attente activation<#else>Activé</#if></td>				  	    		
			  	     	<td><a href="/protected/userEdit/${user.emailAMDM}"><img src="/images/edit.png"/></a></td>
					</tr>
				</#list>				
				 
				</table>				
				
			</div>				
			
			

		</div>
	</section>


<!--==================================== javascripts files section  ==================================-->
<#include "commonjs.ftl">	
	

</body> 
</html> 