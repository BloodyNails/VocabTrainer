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
<title>VocabTrainer - Lists</title>
</head>
<body>

	<header>
		<div class="container">
			<h1>VocabTrainer -> Lists</h1>
			<p>List count: ${listCount}</p>
		</div>
	</header>


	<section>
		<div class="container">
			<nav>
				<ul>
					<li>
						<form action="/VocabTrainer" method="GET">
							<input class="big-btn back" type="submit" value="BACK">
						</form>
					</li>
				</ul>
			</nav>
			
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>description</th>
						<th>language1</th>
						<th>language2</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="list" items="${lists}" varStatus="loop">
						<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
							<td>${list.ID}</td>
							<td>${list.description}</td>
							<td>${list.lang1.toString()}</td>
							<td>${list.lang2.toString()}</td>
							<td>
								<form action="Lists/View" method="GET">
									<input type="hidden" value="${list.ID}" name="listID">
									<input class="small-btn" type="submit" value="DETAILS">
								</form>
							</td>
							<td>
								<form action="" method="POST">
									<input type="hidden" value="${list.ID}" name="listID">
									<input class="small-btn" type="submit" value="DELETE">
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>


	<section>
		<div class="container">
			<div class="input-div">
				<form action="" method="POST" id="newlist">
					<label>description: </label> 
					<input type="text" value="" name="description" autofocus="autofocus"> 
					<select name="lang1" form="newlist">
						<c:forEach var="lang" items="${langs}">
							<option value="${lang.toString()}">${lang.toString()}</option>
						</c:forEach>
					</select> 
					<select name="lang2" form="newlist">
						<c:forEach var="lang" items="${langs}">
							<option value="${lang.toString()}">${lang.toString()}</option>
						</c:forEach>
					</select> <input class="big-btn" type="submit" value="SUBMIT">
				</form>


			</div>
		</div>
	</section>



</body>
</html>