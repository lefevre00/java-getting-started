<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>
  
    <div class="container">
	  <div class="jumbotron">
	      <h2>Veuillez vous authentifier</h2>
	      <form method="post" role="form">
			      
		  <#if error??>
			  <div class="alert alert-danger">
			  	<strong>Erreur : </strong>${error}
			  </div>
		  </#if>
		  
	        <div class="form-group">
	        	<div class="input-group">
		        	<div class="input-group-addon"><i class="fa fa-envelope-o"></i></div>
			        <#if email??>
	  		          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr" value="${email}" required/>
			        <#else>
			          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr" required/>
	                </#if>
                </div>
	        </div>
	        <div class="form-group">
	        	<div class="input-group">
	        		<div class="input-group-addon"><i class="fa fa-key"></i></div>
			        <input type="password" class="form-control" name="pwd" id="pwd" placeholder="motdepasse" required/>
			    </div>
	        </div>
	        <div class="form-group">
				<a class="btn btn-primary pull-right" href="/user/new">Cr&eacute;er un compte</a>
				<input type="submit" class="btn btn-success" value="Se connecter"/>
				<a href="/user/forget">Mot de passe perdu ?</a>
	        </div>
	      </form>
      </div>
    </div>
  
</body>
</html>
