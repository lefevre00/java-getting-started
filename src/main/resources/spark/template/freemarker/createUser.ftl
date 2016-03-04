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
      <div class="form-container">
	      <form method="post" role="form">
	        <div class="form-group">
		        <label for="email">Email</label>
		        <#if email??>
  		          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr" value="${email}"/>
		        <#else>
		          <input type="email" class="form-control" name="email" placeholder="adresse@email.fr"/>
                </#if>
	        </div>
	        <div class="form-group">
		        <label for="pwd">Mot de passe</label>
		        <input type="password" class="form-control" name="pwd" id="pwd" placeholder="********"/>
	        </div>
	        <div class="form-group">
		        <label for="pwd">Num&eacute;ro de place (si attribu&eacute;)</label>
		        <input type="text" class="form-control" name="placeNumber" id="placeNumber" placeholder="110" size="3" maxlength="3"/>
	        </div>
	        <div class="form-group">
				<input type="submit" class="btn btn-success" value="Valider"/>
	        </div>
	      </form>
      </div>
    </div>
  </div>   
  
</body>
</html>
