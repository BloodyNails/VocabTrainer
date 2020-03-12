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
	
	<table>
		<thead>
			<tr>
				<th>wordID</th>
				<th>wordLang1</th>
				<th>wordLang2</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="word" items="${words}">
	            <tr>
	            	<td>${word.ID}</td>
	            	<td>${word.wordLang1}</td>
	            	<td>${word.wordLang2}</td>
	            	<td>x</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
    
</body>
</html>