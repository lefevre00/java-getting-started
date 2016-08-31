<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
    <script src="${ressourcesDirectory}js/jquery.min.js"></script>
</head>

<body>

	<!--======================================== Section navigation ====================================-->
	<#include "navAdmin.ftl">


	<!--================================================================================================-->
    <section class="content-section">
		<div class="container containerAttr" style="padding-bottom:50px;">
			<h1>Libération de places</h1>
			Email de l'utilisateur concerné : <strong>${email}</strong> (N° place : ${place})
			
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
						
						<button type="button" class="btn btn-default active" style="margin-bottom:20px;" onclick="history.go(-1);">Annuler</button>
						&nbsp;&nbsp;
						<button type="submit" class="btn btn-primary" style="margin-bottom:20px;">Valider</button>
						
					</div>
			
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
	<link href="${ressourcesDirectory}css/datetimepicker.css" rel="stylesheet">
  	<script src="${ressourcesDirectory}js/moment-with-locales.js"></script>
  	<script src="${ressourcesDirectory}js/datetimepicker.js"></script>	

</body> 
</html> 
