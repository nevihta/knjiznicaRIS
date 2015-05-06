<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Knjižnica</title>
		<%@ include file="head.jsp"%>
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp" %>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header">Pregled oseb</h1>	
					<c:if test="${sessionScope.Prijava==true}">		
					<br/>				
					<table >
						<tr>
							<td id="dodajUp">
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=dodajOs"
							> Dodaj novega uporabnika</a>	
							</td>
							<td>	
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=clan">Člani</a> | 
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=knjiznicar"> Knjižničarji </a> | 
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=zamudnik"> Zamudniki</a>|
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=vse"> Vsi</a>
							</td>
							
						</tr>
					</table>
					<br>
					<c:if test="${poslano==true}">
						<p>Email je bil poslan!</p>
					</c:if>	
					<c:if test="${poslano==false}">
						<p>Pri pošiljanju emaila je prišlo do napake!</p>
					</c:if>	
					<table id="izpisOseb">
						<tr>
							<th>Članska št.</th>
							<th>Ime in priimek</th>
							<th>Email</th>	
						</tr>
						
						<c:forEach var="oseba" items="${osebe}">
							<tr>
							
								<td><c:out value="${1000+oseba.id}" /></td>
								<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${oseba.id}" />"><c:out value="${oseba.ime}" /> <c:out value="${oseba.priimek}" /></a></td>
								<td><c:out value="${oseba.email }" /></td>
								<c:if test="${filt}">
								<td><a  href="${pageContext.request.contextPath}/CrnaListaServlet?metoda=email&idOsebe=<c:out value="${oseba.id}" />">Obvesti</a></td>
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