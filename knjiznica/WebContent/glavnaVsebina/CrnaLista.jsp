<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Izposoje osebe</title>
<%@ include file="head.jsp"%>
<script type="text/javascript">
	
	function Poslji() {
			document.getElementById("pret").submit();
		}
</script>
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

				<form  method="get" action="${pageContext.request.contextPath}/CrnaListaServlet">
				<table>
				<tr>
				<td><input type="hidden" name="metoda" value="pridobiVse"/><input type="hidden" name="filter" value="akt"/><input type="submit" value="Aktualni zapisi" class="button"> </td>
				<td><input type="button" value="Pretekli zapisi" class="button" onclick="Poslji()"> </td>
				
				</tr>	
				</table>
				</form>

				<div id="pretekle" style="display: none;">
					<form id="pret" method="get" action="${pageContext.request.contextPath}/CrnaListaServlet">
					<input type="hidden" name="metoda" value="pridobiVse"/>
					<input type="hidden" name="filter" value="pret"/>
					<input type="submit"/>
					</form>
					<br/>
				</div>	

				<table class="razmaki">
				<tr>
					<th>Št. zapisa</th>
					<th>Oseba</th>
					<th>Datum zapisa</th>
					<c:if test="${pretekle}">
						<th>Datum odstranitve</th>
					</c:if>
					<th>Razlog</th>
				</tr>
					<c:forEach var="zapis" items="${crnaLista}">
						<tr>

							<td><c:out value="${zapis.id}" /></td>
							<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${zapis.oseba.id}"/>"><c:out value="${zapis.oseba.ime}" /><c:out value="${zapis.oseba.priimek}" /></a></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${zapis.datumZapisa}" />
							<c:if test="${pretekle}">
								<td><fmt:formatDate pattern="dd. MM. yyyy" value="${zapis.datumIzbrisa}" /></td>
							</c:if>
							<td><c:out value="${zapis.razlog}" /></td>
							<c:if test="${!pretekle}">
							<td><a class="paddingLevoMoreBit"
								href="${pageContext.request.contextPath}/CrnaListaServlet?metoda=odstrani&idZapisa=<c:out value='${zapis.id}' />">Odstrani</a></td>
							</c:if>
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