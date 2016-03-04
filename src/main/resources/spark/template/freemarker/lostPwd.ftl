<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  
  <#if error??>
  	<div class="bg-warning">
  	${error}
  	</1div>
  </#if>
    
  <div class="jumbotron">
    <div class="container">
      <div class="row">
		  <form method="post" role="form">
			<div class="form-group">Nous allons r&eacute;initialiser votre compte. Vous recevrez par mail un lien afin de red&eacute;finir votre mot de passe.</div>	
	        <div class="form-group">
		        <label for="email">Email</label>
		        <#if email??>
  		          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr" value="${email}"/>
		        <#else>
		          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr"/>
                </#if>
	        </div>			
			<div class="form-group">
				<input type="submit" class="btn btn-success" value="R&eacute;initialiser"/>
			</div>
		  </form>
      </div>
    </div>
  </div>
  
</body>
</html>
