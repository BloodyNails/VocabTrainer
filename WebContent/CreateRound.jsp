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
	<h1>VocabTrainer -> Training -> Vocabulary -> Create Round</h1>

	<form action="/VocabTrainer/Training/Vocabulary" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>

	<br>

	<p>you must only select lists with the same languages</p>
	<p>you also must only select a prompted language which is included
		in the languages of the selected lists</p>

	<div class="input-div">
		<form id="newRound" action="/VocabTrainer/Training/Vocabulary/create"
			method="POST">
			<table>
				<thead>
					<tr>
						<th class="small-col">ID</th>
						<th>description</th>
						<th>first language</th>
						<th>second language</th>
						<th class="small-col">select</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${lists}" varStatus="loop">
						<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
							<td>${list.ID}</td>
							<td>${list.description}</td>
							<td>${list.lang1.toString()}</td>
							<td>${list.lang2.toString()}</td>
							<td >
								<input type="checkbox" name="checkbox-${list.ID}">
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<input type="submit">
		</form>

		<label>prompted language (can only be): </label> <select
			name="promptedLang" form="newRound">
			<c:forEach var="lang" items="${langs}">
				<option value="${lang.toString()}">${lang.toString()}</option>
			</c:forEach>
		</select>
	</div>

</body>
</html>