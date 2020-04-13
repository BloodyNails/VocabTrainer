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
	<h1>VocabTrainer -> Training -> Vocabulary</h1>

	<form action="/VocabTrainer/Training" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>

	<c:choose>
		<c:when test="${!emptyLists}">
			<h3>list count: ${listCount}</h3>
			<h3>select the lists which will be prompted:</h3>

			<form class="form" action="" method="POST">
				<table>
					<thead>
						<tr>
							<th class="small-col">ID</th>
							<th>description</th>
							<th>first language</th>
							<th>second language</th>
							<th>select</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${lists}" varStatus="loop">
							<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
								<td>${list.ID}</td>
								<td>${list.description}</td>
								<td>${list.lang1.toString()}</td>
								<td>${list.lang2.toString()}</td>
								<td><input type="checkbox" name="select" value="${list.ID}"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<input type="submit" name="submit">
			</form>

		</c:when>
		<c:otherwise>
			<h3>no lists found</h3>
		</c:otherwise>
	</c:choose>


</body>
</html>