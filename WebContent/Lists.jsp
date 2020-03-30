<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Lists</title>
</head>
<body>
	<h1>VocabTrainer -> Lists</h1>
	<h3>List count: ${listCount}</h3>
	
	<form action="/VocabTrainer" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>
	
	<br>
	
	<table>
		<thead>
			<tr>
				<th class="small-col">ID</th>
				<th>description</th>
				<th>first language</th>
				<th>second language</th>
				<th class="small-col">details</th>
				<th class="small-col">delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lists}" varStatus="loop">
	            <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
	            	<td>${list.ID}</td>
	            	<td>${list.description}</td>
	            	<td>${list.lang1.toString()}</td>
	            	<td>${list.lang2.toString()}</td>
	            	<td>
	            		<form class="form" action="Lists/View" method="GET">
	            			<input type="hidden" value="${list.ID}" name="listID">
	            			<input class="table-btn" type="submit" value="details">
	            		</form>
	            	</td>
	            	<td>
	            		<form class="form" action="" method="POST">
	            			<input type="hidden" value="${list.ID}" name="listID">
	            			<input class="table-btn, delete-input" type="submit" value="delete">
	            		</form>
	            	</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
    
    <br>
    <div class="input-div">
	    <form action="" method="POST" id="newlist">
	    	<label>description: </label><input type="text" value="" name="description" autofocus="autofocus"><br>
	    		<!-- 
			    	<label>first language: </label><input type="text" value="" name="lang1"><br>
			    	<label>second language: </label><input type="text" value="" name="lang2"><br>
		    	 -->
	    	<input class="form-btn" type="submit" value="SUBMIT">
    	</form>
    	
	    	<select name="lang1" form="newlist">
	    		<option value="German">German</option>
	    		<option value="English">English</option>
	    		<option value="Russian">Russian</option>
	    	</select>
	    
	    <select name="lang2" form="newlist">
	    		<option value="German">German</option>
	    		<option value="English">English</option>
	    		<option value="Russian">Russian</option>
	    	</select>
    </div>
    
    
</body>
</html>