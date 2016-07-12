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
		.imageLeft {
			float:left;
		}			
    </style>
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "navAdmin.ftl">

		
    <section class="content-section">
		<div class="container containerAttr" >

			<div  style="margin:0px auto;max-width:700px;" >
				<a href="#" onclick="history.go(-1);">
					<img src="/images/retour.png" title="Retour" class="imageLeft" />
				</a>
				<h1 class="titre">${title}</h1>
			</div>

			<!--=================================== liste de partage ==================================-->
			<#if listePlaces??>

				<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:30px;">
						
					<#if (listePlaces?size > 9)>
						<table class="table table-striped table-condensed padding20"
							   id="table" 
							   data-toggle="table"
							   data-height="379" 
							   data-pagination="true"
							   data-page-size="9"
							   data-pagination-pre-text="< "
							   data-pagination-next-text=" >">
										
							<thead style="background-color: #f5f5f5; color: #317bba;">
							<tr>
								<th style="text-align:center;">Date</th> 
								<th style="text-align:center;">Num. de place</th> 
								<th style="text-align:center;">Occupant</th>
							</tr>
							</thead>
					<#else>
						<table class="table table-bordered table-striped table-condensed padding20"  id="table">
							<tr style="background-color: #f5f5f5; color: #317bba;">
								<th style="text-align:center;">Date</th>
								<th style="text-align:center;">Num. de place</th>
								<th style="text-align:center;">Occupant</th>
							</tr>
					</#if>
					<#list listePlaces as place>
						<#assign show = place.usedBy>
						<#assign theDate = '${place.occupationDate}'?date("yyyy-MM-dd")>
						<tr>
							<td>${theDate?string["dd/MM/yyyy"]}</td>
					   		<td>${place.placeNumber}</td>
					   		<td>
								<#if show == "" || show == " ">
					  				-
					  			<#else>
					  				${show?substring(0, show?index_of("@"))?upper_case?replace(".", " ")}
					  			</#if>
					  		</td>
						</tr>
					</#list>
					</table>
					<button id="export" data-export="export" class="btn btn-primary">Exporter csv</button>
				</div>
						
			</#if>
			<!--======================================= Fin liste =====================================-->

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
