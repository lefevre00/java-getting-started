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

		
    <section class="content-section">
		<div class="container containerAttr">

			<h1 class="titre">Statistiques</h1>
	
			<!--======================================== libération par dates ==================================-->
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<form method="post" role="form">
					<div class="panel panel-default" >
						<div class="panel-heading">Choix de la période</div>
						<div class="panel-body">
							<div class="col-md-6 col-sm-6">
								<div class="input-group date" id="datepicker1" style="margin-bottom:10px;">
									<label>du&nbsp;&nbsp;</label>
									<input type="text" class="shareInput" name="dateDebut" required />
									<span class="input-group-addon">
										<i class="fa fa-calendar-o"></i>
									</span>
								</div>
							</div>
							<div class="col-md-6 col-sm-6">
								<div class="input-group date" id="datepicker2" style="margin-bottom:10px;">
									<label>au&nbsp;&nbsp;</label>
									<input type="text" class="shareInput" name="dateFin" required/>
									<span class="input-group-addon">
										<i class="fa fa-calendar-o"></i>
									</span>
								</div>
							</div>						
						</div>
						
						<input type="submit" class="btn btn-primary btn-lg" value="Valider" style="margin-bottom:20px;"/>						
					</div>					
					

					<!--=============================== Nbre de places partagé ============================-->
					<#if nbrePartage??>
					
						<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:30px;">
							<#assign dateD = '${dateDebut}'?date("yyyy-MM-dd")>
							<#assign dateF = '${dateFin}'?date("yyyy-MM-dd")>					
							Période de partage des places du <strong>${dateD?string["dd/MM/yyyy"]}</strong>  au  <strong>${dateF?string["dd/MM/yyyy"]}</strong>	<br/><br/>
							<table class="table table-bordered table-striped table-condensed padding20"  id="table">
								<tr style="background-color: #f5f5f5; color: #317bba;">
									<th style="text-align:center;">Nbre partagé</th> 
									<th style="text-align:center;">Nbre réservé</th> 
									<th style="text-align:center;">Nbre inoccupé</th>
								</tr>
								<tr>
									<td><a href="/protected/statistics?var=p&dd=${dateDebut}&df=${dateFin}" class="linkClass">${nbrePartage}</a></td>
					  	    		<td><a href="/protected/statistics?var=o&dd=${dateDebut}&df=${dateFin}" class="linkClass">${nbreOccupe}</a></td>
					  	    		<td><a href="/protected/statistics?var=i&dd=${dateDebut}&df=${dateFin}" class="linkClass">${nbreInoccupe}</a></td>
								</tr>
							</table>
							<!--button id="export" data-export="export" class="btn btn-primary">Exporter csv</button-->
							<br/>
						</div>
					</#if>
					<!--======================================= Fin liste =====================================-->
					
				</form>
			</div>	

		</div>
	</section>
	
	<script type="text/javascript">
		var today = new Date();
		var firstDate = new Date();
		if(today.getHours()>14 || today.getDay()==0){
			firstDate =  rechercherDateLejourSuivant(firstDate);
		}
	    $( document ).ready(function() {
	        $('#datepicker1').datetimepicker({
	        	locale: 'FR',
	        	daysOfWeekDisabled: [0, 6],
	               format: 'DD/MM/YYYY'
	        });
	        $('#datepicker2').datetimepicker({
	        	daysOfWeekDisabled: [0, 6],
	            useCurrent: true,
	            format: 'DD/MM/YYYY',
	            minDate: firstDate,
	            locale: 'FR' //Important!
	        });
	        $("#datepicker1").on("dp.change", function (e) {
	            $('#datepicker2').data("DateTimePicker").minDate(e.date);
	        });
	        $("#datepicker2").on("dp.change", function (e) {
	            $('#datepicker1').data("DateTimePicker").maxDate(e.date);
	        });
	    });
	    
		function rechercherDateLejourSuivant(date){
			var nbJourAAjouter = 1;
			if(date.getDay()==5){
				nbJourAAjouter = 3;
			}else if(date.getDay()==6){
				nbJourAAjouter = 2;
			}else{
				nbJourAAjouter = 1
			}
			return new Date(today.getTime() + 24 * 60 * 60 * 1000 * nbJourAAjouter);
		}
		 
		function isWeekEnd(date){
			return date.getDay()==6 || date.getDay()==0;
		}
	</script>	
	
	
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
