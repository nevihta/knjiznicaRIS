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
				<h1 class="templatemo-header">Črna lista</h1>
			<c:if test="${sessionScope.Prijava==true}">

				<table>
				<tr>
					<th>Št. zapisa</th>
					<th>Oseba</th>
					<th>Datum zapisa</th>
					<th>Razlog</th>
				</tr>
					<c:forEach var="zapis" items="${crnaLista}">
						<tr>

							<td><c:out value="${zapis.id}" /></td>
							<td><c:out value="${zapis.oseba.ime}" /><c:out value="${zapis.oseba.priimek}" /></td>
							<td><c:out value="${zapis.datumZapisa}" /></td>
							<td><c:out value="${zapis.razlog}" /></td>
							<td><a class="paddingLevoMoreBit"
								href="${pageContext.request.contextPath}/CrnaListaServlet?metoda=odstrani&idZapisa=<c:out value='${zapis.id}' />">Izbriši</a></td>
						</tr>
					</c:forEach>
				</table>
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