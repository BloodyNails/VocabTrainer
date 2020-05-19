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
<title>VocabTrainer - Vocabulary</title>
</head>
<body>

	<header>
		<div class="container">
			<h4>VocabTrainer -> Training -> Vocabulary</h4>
			<p>Round count: ${roundCount}</p>
		</div>
	</header>

	<section>
		<div class="container-wide">
			<nav>
				<ul>
					<li>
						<form action="/VocabTrainer/Training" method="GET">
							<input class="big-btn back-btn" type="submit" value="BACK">
						</form>
					</li>
					<li>
						<form action="/VocabTrainer/Training/Vocabulary/create"
							method="GET">
							<input class="big-btn" type="submit" value="CREATE NEW ROUND">
						</form>
					</li>
				</ul>
			</nav>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>completed</th>
						<th>lists</th>
						<th>cycle count</th>
						<th>first language</th>
						<th>second language</th>
						<th>prompted language</th>
						<th>time in s</th>
						<th>correct answers</th>
						<th>incorrect answers</th>
						<th>incorrect / correct ratio</th>
						<th></th>
						<th></th>
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
							<td>${round.getCycleIDs().size()}</td>
							<td>${round.languages.getLang1().toString()}</td>
							<td>${round.languages.getLang2().toString()}</td>
							<td>${round.promptedLang.toString()}</td>
							<td>${round.time}</td>
							<td>${round.trueCount}</td>
							<td>${round.falseCount}</td>
							<td>${round.tfRatio}</td>
							<td>
								<c:choose>
									<c:when test="${round.getCycleIDs().size() > 0}">
										<form class="form"
											action="/VocabTrainer/Training/Vocabulary/continue" method="POST">
											<input type="hidden" value="${round.ID}" name="roundID">
											<input class="small-btn" type="submit" value="CONTINUE">
										</form>
									</c:when>
									<c:otherwise>
										<form class="form"
											action="/VocabTrainer/Training/Vocabulary/continue" method="POST">
											<input type="hidden" value="${round.ID}" name="roundID">
											<input class="small-btn" type="submit" value="START">
										</form>
									</c:otherwise>
								</c:choose></td>
							<td>
								<form class="form"
									action="/VocabTrainer/Training/Vocabulary/delete" method="POST">
									<input type="hidden" value="${round.ID}" name="roundID">
									<input class="small-btn" type="submit" value="DELETE">
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>
	
	<script type="text/javascript">
		/* tell java the browser was closed */
	</script>

</body>
</html>