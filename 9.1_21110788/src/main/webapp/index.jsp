<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Murach's Java Servlets and JSP</title>
<link rel="stylesheet" href="styles/main.css" type="text/css" />
</head>
<body>
	<h1>List of albums</h1>
	<c:if test="${cookie.firstNameCookie.value != null}">
		<h2>
			Welcome back,
			<c:out value='${cookie.firstNameCookie.value}' />
		</h2>
	</c:if>

	<c:if test="${cookie.userEmail.value!=null}">
		<h2>User Email: ${cookie.userEmail.value}</h2>
		<br>
	</c:if>


	<p>
		<a href="download?action=checkUser&amp;productCode=8601"> 86 (the
			band) - True Life Songs and Pictures ></a><br> <a
			href="download?action=checkUser&amp;productCode=pf01"> Paddlefoot
			The First CD ></a><br> <a
			href="download?action=checkUser&amp;productCode=pf02"> Paddlefoot
			The Second CD </a><br> <a
			href="download?action=checkUser&amp;productCode=jr01"> Joe Rut
			Genuine Wood Grained Finish </a>
	</p>
</body>
</html>