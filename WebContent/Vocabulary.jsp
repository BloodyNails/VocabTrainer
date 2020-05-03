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
	<h3>Round count: ${roundCount}</h3>

	<form action="/VocabTrainer/Training" method="GET">
		<input class="reg-btn" type="submit" value="BACK">
	</form>

	<br>

	<table>
		<thead>
			<tr>
				<th class="small-col">ID</th>
				<th>completed</th>
				<th>lists</th>
				<th>first language</th>
				<th>second language</th>
				<th>prompted language</th>
				<th>time in s</th>
				<th>correct answers</th>
				<th>incorrect answers</th>
				<th>incorrect / correct ratio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="round" items="${rounds}" varStatus="loop">
				<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
					<td>${round.ID}</td>
					<td>${round.completed}</td>
					<td>
						<table>
							<c:forEach var="description"
								items="${round.getListDescriptions()}">
								<tr>
									<td>${description}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td>${round.languages.getLang1().toString()}</td>
					<td>${round.languages.getLang2().toString()}</td>
					<td>${round.promptedLang.toString()}</td>
					<td>${round.time}</td>
					<td>${round.trueCount}</td>
					<td>${round.falseCount}</td>
					<td>${round.tfRatio}</td>
					<td>
						<form class="form" action="/VocabTrainer/Training/Vocabulary/continue" method="POST">
							<input type="hidden" value="${round.ID}" name="roundID">
							<input class="table-btn" type="submit" value="continue">
						</form>
					</td>
					<td>
						<form class="form" action="/VocabTrainer/Training/Vocabulary/delete" method="POST">
							<input type="hidden" value="${round.ID}" name="roundID">
							<input class="table-btn, delete-input" type="submit"
								value="delete">
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<br>

	<div class="input-div">
		<form action="/VocabTrainer/Training/Vocabulary/create" method="GET">
			<input class="form-btn" type="submit" value="CREATE NEW ROUND">
		</form>
	</div>


</body>
</html>