<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
    <link href="/css/bootstrap-table.css" rel="stylesheet">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap-table.js"></script>
</head>

<body>
  
	<!--======================================== Section navigation ====================================-->
	<#include "nav.ftl">

	<!--================================================================================================-->	
    <section class="content-section">
		<div class="container containerAttr">

			<h1 class="titre">Partages : <small>place ${placeNumber}</small></h1>
			
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
			

			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<form method="post" role="form">
					
					<div class="text-center"> 
									 
						<div class="col-md-6 col-sm-6">
							<div class="input-group date" id="datepicker1">
								<label>Je libère du&nbsp;&nbsp;</label>
								<input type="text" class="shareInput" name="dateDebut" required />
								<span class="input-group-addon">
									<i class="fa fa-calendar-o"></i>
								</span>
							</div>
						</div>

						<div class="col-md-6 col-sm-6">
							<div class="input-group date" id="datepicker2">
								<label>au&nbsp;&nbsp;</label>
								<input type="text" class="shareInput" name="dateFin" required/>
								<span class="input-group-addon">
									<i class="fa fa-calendar-o"></i>
								</span>
							</div>
						</div>
					<div/>

					<br clear="both"/>
					<br clear="both"/>
					<input type="submit" class="btn btn-primary btn-lg" value="Valider"/>

					<!--=============================== liste des dates de partage ============================-->

					<#if datesPartages??>
						<div class="row table-responsive" style="margin:0px auto;max-width:550px; padding-top:30px;">
							<#if (datesPartages?size > 7)>
							<table class="table table-striped table-condensed padding20"
								   id="table" 
								   data-toggle="table"
								   data-height="330" 
								   data-pagination="true"
								   data-page-size="7"								   							   
								   data-pagination-pre-text="< "
								   data-pagination-next-text=" >">							
							
								<thead style="background-color: #337ab7; color: white;">
									<tr>
										<th style="text-align:center;">Date de partage</th> 
										<th style="text-align:center;">Annuler partage</th>
									</tr>
								</thead>
							<#else>
							<table class="table table-bordered table-striped table-condensed padding20">
								<tr style="background-color: #337ab7; color: white;">
									<th style="text-align:center;">Date de partage</th> 
									<th style="text-align:center;">Annuler partage</th>
								</tr>
							</#if>
								<#list datesPartages as place>
									<tr> 
						  	    		<td>
						  	    			<#assign theDate = '${place.occupationDate}'?date("yyyy-MM-dd")>
						  	    			${theDate?string["EEEE dd/MM/yyyy"]}	
						  	    		</td>
							  	     	<td>
							  	     		<#assign show = place.usedBy>
							  	     	 	<#if show == "" || show == " ">
							  	     			<a href="/protected/share?unshareDate=${place.occupationDate}" data-confirm='Annuler le partage de votre place du <strong> ${theDate?string["dd/MM/yyyy"]}	 </strong> ?' ><img src="/images/cancel.png"/></a>
											<#else>
												${show}
							  	     		</#if>
							  	     	</td>
									</tr>
								</#list>
							</table>
						</div>
					</#if>

					
				</form>
			</div>	

		</div>
	</section>
	
	
	<script type="text/javascript">
		$(function() {
			$('a[data-confirm]').click(function(ev) {
				var href = $(this).attr('href');
				
				if (!$('#dataConfirmModal').length) {
					$('body').append('<div id="dataConfirmModal" class="modal padding120" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content modalColor"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel"><strong>Confirmation d\'annulation</strong></h3></div><div class="modal-body"></div><div class="modal-footer footerColor"><button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">Non</button><a class="btn btn-primary" id="dataConfirmOK">Oui</a></div></div></div></div>');
				}
				$('#dataConfirmModal').find('.modal-body').html($(this).attr('data-confirm'));
				$('#dataConfirmOK').attr('href', href);
				$('#dataConfirmModal').modal({show:true});
				
				return false;
			});
		});		
	</script>
	

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
	<link href="/css/datetimepicker.css" rel="stylesheet">
  	<script src="/js/moment-with-locales.js"></script>
  	<script src="/js/datetimepicker.js"></script>	


</body>
</html>
