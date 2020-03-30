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
	<h1>VocabTrainer -> Lists -> ${list.description}</h1>
	<h3>Word count: ${wordCount}</h3>
	
	<form action="/VocabTrainer/Lists" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>
	
	<br>
	
	<table>
		<thead>
			<tr>
				<th class="small-col">ID</th>
				<th>${list.lang1.toString()}</th>
				<th>${list.lang2.toString()}</th>
				<th class="small-col">delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="word" items="${words}" varStatus="loop">
	            <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
	            	<td>${word.ID}</td>
	            	<td>${word.wordLang1}</td>
	            	<td>${word.wordLang2}</td>
	            	<td>
	            		<form class="form" action="" method="POST">
	            			<input type="hidden" value="${word.ID}" name="wordID">
	            			<input class="table-btn, delete-input" type="submit" value="delete">
	            		</form>
					</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
    
    <br>
    
    <div class="input-div">
    	<form action="" method="POST">
	   		<label>input ${list.lang1.toString()}:</label>
			<input type="text" value="" name="wordLang1" autofocus="autofocus">
			<br>
			<label>input ${list.lang2.toString()}:</label>
			<input type="text" value="" name="wordLang2">
			<br>
			<input class="form-btn" type="submit" value="add">
		</form>
    </div>
   	
</body>
</html>