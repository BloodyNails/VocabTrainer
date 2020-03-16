<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Detail View</title>
</head>
<body>
	<h1>Detail View of ${list.description}</h1>
	
	<form action="/VocabTrainer/Lists" method="GET">
		<label>BACK: </label><input type="submit" value="BACK">
	</form>
	
	<br>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>${list.lang1}</th>
				<th>${list.lang2}</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="word" items="${words}">
	            <tr>
	            	<td>${word.ID}</td>
	            	<td>${word.wordLang1}</td>
	            	<td>${word.wordLang2}</td>
	            	<td>
	            		<form action="" method="POST">
	            			<input type="hidden" value="${word.ID}" name="wordID">
	            			<input type="submit" value="delete">
	            		</form>
					</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
    
   	<form action="" method="POST">
   		<label>input ${list.lang1}:</label>
		<input type="text" value="" name="wordLang1">
		<br>
		<label>input ${list.lang2}:</label>
		<input type="text" value="" name="wordLang2">
		<br>
		<input type="submit" value="add">
	</form>
</body>
</html>