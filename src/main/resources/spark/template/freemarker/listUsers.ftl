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
	<#include "nav.ftl">

		
    <section class="content-section">
		<div class="container containerAttr">

			<h1 class="titre">Statistiques</h1>
	


			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">					
				
					<!--=============================== liste des users============================-->
					<#if listUsers??>
						<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:30px;">
							<table class="table table-bordered table-striped table-condensed padding20"  id="table">
								<tr style="background-color: #f5f5f5; color: #317bba;">
									<th style="text-align:center;">user</th> 
									<th style="text-align:center;">Num. de place</th> 
									<th style="text-align:center;">Etat compte</th> 
								</tr>
								
								<#list listUsers as user>
									<tr>
										<td>${user.emailAMDM?substring(0, user.emailAMDM?index_of("@"))?cap_first?replace(".", " ")}</td>
						  	    		<td><#if user.placeNumber??>${user.placeNumber}<#else>-</#if></td>
						  	    		<td><#if user.tokenMail??>Attente activation<#else>Activé</#if></td>
									</tr>
								</#list>
							</table>
							<button id="export" data-export="export" class="btn btn-primary">Exporter csv</button>
						</div>
					</#if>
					<!--======================================= Fin liste =====================================-->
			</div>	

		</div>
	</section>

	<!--==================================== javascripts files section  ==================================-->
	<#include "commonjs.ftl">
	<script src="/js/confirm.js"></script>
	<link href="/css/datetimepicker.css" rel="stylesheet">
  	<script src="/js/moment-with-locales.js"></script>
  	<script src="/js/datetimepicker.js"></script>	
    <script src="/js/jquery.tabletoCSV.js"></script>
        <script>
        $(function(){
            $("#export").click(function(){
                $("#table").tableToCSV();
            });
        });
    </script>
</body>
</html>
