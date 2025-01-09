<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 데이터 처리 애너테이션</title>
</head>
<body>
	<h1>요청 데이터 처리 애너테이션</h1>
	<a href="register/hong">register/hong</a>
	<br>
	<a href="register/hong/100">register/hong/100</a>
	<br>
	<form action="/register01" method="post">
		userId: <input type="text" name="userId" value="hong"><br>
		password: <input type="text" name="password" value="1234"><br>
		coin: <input type="text" name="coin" value="100"><br> <input
			type="submit" value="register01">
	</form>
	<form action="/register02" method="post">
		userId: <input type="text" name="userId" value="hong"><br>
		password: <input type="text" name="password" value="1234"><br>
		coin: <input type="text" name="coin" value="100"><br> <input
			type="submit" value="register02">
	</form>
</body>
</html>