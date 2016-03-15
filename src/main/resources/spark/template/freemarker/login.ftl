<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <#include "header.ftl">
</head>

<body>
   
  <#if error??>
	  <div class="alert alert-danger">
	  	<strong>Erreur : </strong>${error}
	  </div>
  </#if>
  
  <div class="jumbotron">
    <div class="container">
      <div class="row">
	      <form method="post" role="form">
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
				<input type="submit" class="btn btn-success" value="Se connecter"/>
				<a href="/user/forget">Mot de passe perdu ?</a>
				<a class="btn btn-primary pull-right" href="/user/new">Cr&eacute;er un compte</a>
	        </div>
	      </form>
      </div>
    </div>
  </div>
  
</body>
</html>
