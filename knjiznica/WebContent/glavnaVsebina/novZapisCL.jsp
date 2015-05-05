<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Izposoje osebe</title>
<%@ include file="head.jsp"%>


</head>
<body>
	<div class="templatemo-container">
		<%@ include file="meni.jsp"%>

		<div
			class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
			<h1 class="logo-right hidden-xs margin-bottom-60">
				<b>Knjižnica</b>
			</h1>
			<div class="tm-right-inner-container">
				<h1 class="templatemo-header">Vnos na črno listo ${idOsebe}</h1>

				<form
					action="${pageContext.request.contextPath}/CrnaListaServlet?metoda=dodajOSnaCL"
					method="post">
					<input type="hidden" value="${idOsebe}" name="idOsebe" /> Razlog: <br />
					<br />
					<textarea rows="4" cols="50" name="razlog"></textarea>
					<br /> <br /> <input type="submit" value="Potrdi" class="button" />
				</form>




			</div>
		</div>
</body>
</html>