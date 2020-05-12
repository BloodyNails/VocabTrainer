<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Main Menu</title>
</head>
<body>
	<header>
		<div class="container">
			<h1>VocabTrainer</h1>
			<p>written in Java</p>
			<p>source available <a href="https://github.com/BloodyNails/VocabTrainer">here</a></p>
		</div>
	</header>

	<section>
		<div class="container">
			<nav>
				<ul>
					<li>
						<form action="Lists" method="GET">
							<input class="big-btn" type="submit" value="LISTS">
						</form>
					</li>
					<li>
						<form action="Training" method="GET">
							<input class="big-btn" type="submit" value="TRAINING">
						</form>
					</li>
				</ul>
			</nav>

		</div>
	</section>

</body>
</html>