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

			<h1 class="titre">Partages</h1>
	
			<!--======================================== libération rapide =====================================-->		
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">
				<div class="panel panel-default" >
					<div class="panel-heading">Libération rapide</div>
					<div class="panel-body">
						<div class="col-sm-6" style="margin-bottom:10px;">
							<form method="post" role="form">
								<input type="hidden" name="dateDebut" value="${jourProchaineLiberation}"/>
								<input type="hidden" name="dateFin" value="${jourProchaineLiberation}"/>
								<#if canShareToday??>
									<input type="submit" class="btn btn-primary disabled" value="${libelleJourProchaineLiberation}"/>
								<#else>
									<input type="submit" class="btn btn-primary" value="${libelleJourProchaineLiberation}"/>
								</#if>
							</form>
						</div>
						<div class="col-sm-6" style="margin-bottom:10px;">
							<form method="post" role="form">
								<input type="hidden" name="dateDebut" value="${jourDeuxiemeLiberation}"/>
								<input type="hidden" name="dateFin" value="${jourDeuxiemeLiberation}"/>
								<#if canShareTomorrow??>
									<input type="submit" class="btn btn-primary disabled" value="${libelleJourDeuxiemeLiberation}"/>
								<#else>				
									<input type="submit" class="btn btn-primary" value="${libelleJourDeuxiemeLiberation}"/>
								</#if>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!--======================================== libération par dates ==================================-->
			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<form method="post" role="form">
					
					<div class="panel panel-default" >
						<div class="panel-heading">Libération par dates</div>
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
							
							<div class="col-md-6 col-sm-6" style="padding-top:15px;">
								<div>
									<span>Email de l'occupant de la place</span>
								</div>
							</div>
							<div class="col-md-6 col-sm-6" style="padding-top:10px;">
								<input type="email" class="shareInput typeahead" style="width: 180px;" name="emailOccupant" id="emailOccupant" />
							</div>
							
						</div>
						
						<input type="submit" class="btn btn-primary btn-lg" value="Valider" style="margin-bottom:20px;"/>						
					</div>					
					

					<!--=============================== liste des dates de partage ============================-->
					<#if datesPartages??>
						<div class="row table-responsive" style="margin:0px auto;max-width:700px; padding-top:30px;">
							<#if (datesPartages?size > 7)>
							<table class="table table-striped table-condensed padding20"
								   id="table" 
								   data-toggle="table"
								   data-height="330" 
								   data-pagination="true"
								   data-page-size="7"								   							   
								   data-pagination-pre-text="< "
								   data-pagination-next-text=" >">							
							
								<thead style="background-color: #f5f5f5; color: #317bba;">
									<tr>
										<th>Date</th> 
										<th>Occupée par</th>
										<th>Annuler</th>
									</tr>
								</thead>
							<#else>
							<table class="table table-bordered table-striped table-condensed padding20">
								<tr style="background-color: #f5f5f5; color: #317bba;">
									<th style="text-align:center;">Date</th> 
									<th style="text-align:center;">Occupée par</th> 
									<th style="text-align:center;">Annuler</th>
								</tr>
							</#if>
								<#list datesPartages as place>
									<#assign show = place.usedBy>
									<tr> 
						  	    		<td>
						  	    			<#assign theDate = '${place.occupationDate}'?date("yyyy-MM-dd")>
						  	    			${theDate?string["EEEE dd/MM/yyyy"]}	
						  	    		</td>
						  	    		<td>
						  	    			<#if show == "" || show == " ">
						  	    				-
						  	    			<#else>
						  	    				${show?substring(0, show?index_of("@"))?cap_first?replace(".", " ")}
						  	    			</#if>
						  	    		</td>						  	    		
							  	     	<td>
							  	     	 	<#if show == "" || show == " ">
							  	     			<a href="/protected/share?unshareDate=${place.occupationDate}" data-confirm='Annuler le partage de votre place du <strong> ${theDate?string["dd/MM/yyyy"]}	 </strong> ?' ><img src="/images/cancel.png"/></a>
											<#else>
												-
							  	     		</#if>
							  	     	</td>
									</tr>
								</#list>
							</table>
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
	               format: 'DD/MM/YYYY',
	               minDate: firstDate
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


</body>
</html>
