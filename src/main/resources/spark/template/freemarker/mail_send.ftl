<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>

  <div class="container">
    <div class="jumbotron">
    	<h2><i class="fa fa-thumbs-o-up"></i> ${title}</h2>
    	<br/>

		<div class="alert alert-success" role="alert">
			Un mail vient de vous être envoyé à l'adresse fournie.
			<br/>
			${message}
		</div>
	</div>
  </div>

</body>
</html>
