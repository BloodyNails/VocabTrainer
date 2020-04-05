<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/style-main.css"/>" />
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>VocabTrainer - Training</title>
</head>
<body>
	<h1>VocabTrainer -> Training -> Vocabulary -> Game</h1>

	<form action="/VocabTrainer/Training/Vocabulary" method="POST">
		<input type="checkbox" name="stop" checked="checked" value="stop" style="visibility: hidden;">
		<input class="reg-btn" type="submit" value="STOP">
	</form>
	
	<h3>PLAYING</h3>

</body>
</html>