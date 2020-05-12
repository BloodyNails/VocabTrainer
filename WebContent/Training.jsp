<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value="/style-main.css"/>"/>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
	<title>VocabTrainer - Training</title>
</head>
<body>
	<header>
		<div class="container">
			<h1>VocabTrainer -> Training</h1>	
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
					<li>
						<form action="Training/Vocabulary" style="" method="GET">
							<input class="big-btn" type="submit" value="VOCABULARY">
						</form>
					</li>
					<li> 
						<input class="big-btn" type="submit" value="more to come">
					</li>
				</ul>
			</nav>
		</div>
	</section>

</body>
</html>