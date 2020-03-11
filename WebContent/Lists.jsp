<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet"> 
	<link rel="stylesheet" type="text/css" href="style-main.css"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Lists</title>
</head>
<body>
	<h3>List count: ${listCount}</h3>
	
	<ol>
        <c:forEach var="list" items="${lists}">
            <li>${list.description}</li>
        </c:forEach>
    </ol>
</body>
</html>