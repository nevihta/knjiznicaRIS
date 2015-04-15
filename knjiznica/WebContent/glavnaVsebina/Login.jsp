<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Prijava</title>
	</head>
	<body>
		<form
			action="${pageContext.request.contextPath}/OsebaServlet?metoda=prijava"
			method="post">
			<table>
				<tr>
					<td>Ime:</td>
					<td><input type="text" name="ime" /></td>
				</tr>
				<tr>
					<td>Geslo:</td>
					<td><input type="password" name="geslo" /></td>
				</tr>
			</table>
			<input type="submit" value="Potrdi" name="submit" />
		</form>
		<c:if test="${sessionScope.Prijava==false}">
			<c:out value="Vnesli ste napacno ime ali geslo" />
		</c:if>
	</body>
</html>