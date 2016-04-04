<!DOCTYPE html>
<html lang="fr">
<head>
	<#include "header.ftl">
	<link rel="stylesheet" href="/stylesheets/datepicker.css">
	<script type="text/javascript" src="/js/bootstrap-datepicker.js"></script>
</head>

<body>
  
	<!-- navigation section -->
	<#include "nav.ftl">


    <section class="content-section">
		<div class="container containerAttr">

			<h1>Proposer une place de parking</h1>

			<div class="row" style="margin:0px auto;max-width:700px; padding-top:20px;">					
				<form method="post" role="form">
					<div class="well">
						Je lib&egrave;re la place n&deg;<strong>${placeNumber}</strong> pour la (ou les) journ&eacute;e(s) du <br><br/>
						<div class="text-center"> 
									 
							<div class="form-group col-md-2">
								<div class='input-group date' id='datetimepicker6'>
									<input type='text' class="form-control" name="dateDebut"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>

							<div class="form-group col-md-2">
								<div class='input-group date' id='datetimepicker7'>
									<input type='text' class="form-control"  name="dateFin"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						<div/>

						<br clear="both"/>
						<input type="submit" class="btn btn-primary btn-lg" value="Valider"/>
					</div>
				</form>
			</div>	

		</div>

	</section>

</body>
</html>

<script type="text/javascript">
    $( document ).ready(function() {
        $('#datetimepicker6').datetimepicker({
        	locale: 'FR',
        	daysOfWeekDisabled: [0, 6],
               format: 'DD/MM/YYYY'
        });
        $('#datetimepicker7').datetimepicker({
        	daysOfWeekDisabled: [0, 6],
            useCurrent: true,
            format: 'DD/MM/YYYY',
            locale: 'FR' //Important! See issue #1075
        });
        $("#datetimepicker6").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });
    });
</script>

