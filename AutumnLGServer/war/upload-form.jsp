<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
    	<style>
    		form {
    			border: 1px solid #ccc;
				padding: 0.5em 0.7em;
				display: inline-block;
				background-color: #fdfdfd;
    		}
    		code {
    			border: 1px solid #ddd;
				background-color: #fdfdfd;
				padding: 0.2em;
				line-height: 2.1em;
				white-space: nowrap;
    		}
    		.error {
    			display: inline-block;
    			padding: 0.3em;
    			color: #a00;
    			background-color: #fdd; 
    			border: 1px solid #c00;
    		}
    	</style>
    </head>
    <body>
    	<a href="/">&laquo; Back to files list</a>
    	
    	<p>When you hit upload the browser will start POSTing to:<br>
    	<code>${uploadUrl}</code></p>
    	
    	<form action="${uploadUrl}" method="post" enctype="multipart/form-data">
    		<p> id: <input type="text" name="id"> </p>
    		<p> name: <input type="text" name="name"> </p>
      		<p> price: <input type="text" name="price"> </p>
    		<p><input type="file" name="files" multiple></p>
    		<p><input type="submit" value="Upload"></p>
    	</form>
    	
    	<c:if test="${not empty error}">
    		<p class="error">${error}</p>
    	</c:if>
    </body>
</html>
