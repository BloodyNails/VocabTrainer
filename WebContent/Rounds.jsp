<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Training</title>
</head>
<body>
	<h1>VocabTrainer -> Training -> Vocabulary</h1>
	<form action="/VocabTrainer/Training" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>
	
	<br>
	
	<c:choose>
		<c:when test="${roundCount < 1}">
			create new round
		</c:when>
		<c:otherwise>
			roundlist
		</c:otherwise>
	</c:choose>
	
</body>
</html>