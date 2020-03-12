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
	<h3>List count: ${listCount}</h3>
	<h3>Turm - ба́шня</h3>
	
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
	            			<input type="hidden" value="${list.ID}" name="id">
	            			<input type="submit" value="submit">
	            		</form>
	            	</td>
	            </tr>
	        </c:forEach>
		</tbody>
    </table>
</body>
</html>