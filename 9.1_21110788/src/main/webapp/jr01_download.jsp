<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>

<h1>Downloads class="styled-table"</h1>

<h2>${description}</h2>
    
<table class="styled-table">
<tr>
    <th>Song title</th>
    <th>Audio Format</th>
</tr>

<tr>
    <td>Filter</td>
    <td><a href="/9.1_21110788/musicStore/sound/${productCode}/filter.mp3">MP3</a></td>
</tr>
<tr>
    <td>So Long Lazy Ray</td>
    <td><a href="/9.1_21110788/musicStore/sound/${productCode}/so_long.mp3">MP3</a></td>
</tr>
</table>

<p><a href="?action=viewAlbums">View list of albums</a></p>

<p><a href="?action=viewCookies">View all cookies</a></p>

</body>
</html>