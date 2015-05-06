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
				<h1 class="templatemo-header">Vnos na črno listo</h1>
			<c:if test="${sessionScope.Prijava==true}">
				<form
					action="${pageContext.request.contextPath}/CrnaListaServlet?metoda=dodajOSnaCL"
					method="post">
					<input type="hidden" value="${idOsebe}" name="idOsebe" /> Razlog: <br />
					<br />
					<textarea rows="4" cols="50" name="razlog"></textarea>
					<br /> <br /> 
					<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/>
					<input type="submit" value="Potrdi" class="button" />
				</form>

			</c:if>
			
			<c:if test="${sessionScope.Prijava!=true}">
				<p>Za ogled te strani morate biti prijavljeni!</p>
				<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiPrijavo">  Prijava</a>
			</c:if>
		</div>

		</div>
	</div>
</body>
</html>