<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<header>
		<div class="container">
			<h1>VocabTrainer -> Training -> Vocabulary -> Create Round</h1>
		</div>
	</header>

	<section>
		<div class="container">
			<nav>
				<ul>
					<li>
						<form action="/VocabTrainer/Training/Vocabulary" method="GET">
							<input class="big-btn back-btn" type="submit" value="BACK">
						</form>
					</li>
				</ul>
			</nav>
		</div>
	</section>


	<section>
		<div class="container">
			<p>the lists you select must not have more than two different languages</p>
			<p>the prompted language must be contained in your selected lists' languages</p>
			<form id="newRound" action="/VocabTrainer/Training/Vocabulary/create" method="POST">
				<table>
					<thead>
						<tr>
							<th>ID</th>
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
								<td><input type="checkbox" name="checkbox-${list.ID}">
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<label>prompted language: </label> 
				<select name="promptedLang" form="newRound">
					<c:forEach var="lang" items="${langs}">
						<option value="${lang.toString()}">${lang.toString()}</option>
					</c:forEach>
				</select>
				<input class="big-btn" type="submit" value="CREATE NEW ROUND">
			</form>
		</div>
	</section>
	
</body>
</html>