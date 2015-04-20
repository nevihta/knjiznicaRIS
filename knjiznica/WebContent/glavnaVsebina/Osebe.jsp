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
							<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=vse"> Vsi</a>
							</td>
						</tr>
					</table>
					<br>
					
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
						
							</tr>
						</c:forEach>
					</table>

				</div>
			</div>
		</div>
	</body>
</html>