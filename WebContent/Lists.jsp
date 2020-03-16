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
	<h1>VocabTrainer - Lists</h1>
	<h3>List count: ${listCount}</h3>
	
	<form action="/VocabTrainer" method="GET">
		<label>BACK: </label><input type="submit" value="BACK">
	</form>
	
	<br>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>description</th>
				<th>first language</th>
				<th>second language</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lists}">
	            <tr>
	            	<td>${list.ID}</td>
	            	<td>${list.description}</td>
	            	<td>${list.lang1}</td>
	            	<td>${list.lang2}</td>
	            	<td>
	            		<form action="Lists/View" method="GET">
	            			<input type="hidden" value="${list.ID}" name="listID">
	            			<input type="submit" value="details">
	            		</form>
	            	</td>
	            	<td>
	            		<form action="" method="POST">
	            			<input type="hidden" value="${list.ID}" name="listID">
	            			<input type="submit" value="delete">
	            		</form>
	            	</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
    
    <form action="" method="POST">
    	<label>description: </label><input type="text" value="" name="description"><br>
    	<label>first language: </label><input type="text" value="" name="lang1"><br>
    	<label>second language: </label><input type="text" value="" name="lang2"><br>
    	<label>submit</label><input type="submit" value="SUBMIT">
    </form>
    
</body>
</html>