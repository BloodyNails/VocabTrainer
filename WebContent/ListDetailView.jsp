<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Detail View</title>
</head>
<body>

	<header>
		<div class="container">
			<h1>VocabTrainer -> Lists -> ${list.description}</h1>
			<p>Word count: ${wordCount}</p>
		</div>
	</header>


	<section>
		<div class="container">
			<nav>
				<ul>
					<li>
						<form action="/VocabTrainer/Lists" method="GET">
							<input class="big-btn back-btn" type="submit" value="BACK">
						</form>
					</li>
				</ul>
			</nav>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>${list.lang1.toString()}</th>
						<th>${list.lang2.toString()}</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="word" items="${words}" varStatus="loop">
						<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
							<td>${word.ID}</td>
							<td>${word.wordLang1}</td>
							<td>${word.wordLang2}</td>
							<td>
								<form class="form" action="" method="POST">
									<input type="hidden" value="${word.ID}" name="wordID">
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
				<form action="" method="POST">
					<label>input ${list.lang1.toString()}:</label> <input type="text" value="" name="wordLang1" autofocus="autofocus">
					<label>input ${list.lang2.toString()}:</label> <input type="text" value="" name="wordLang2"> 
					<input class="big-btn" type="submit" value="ADD">
				</form>
			</div>
		</div>
	</section>
    
    
   	
</body>
</html>