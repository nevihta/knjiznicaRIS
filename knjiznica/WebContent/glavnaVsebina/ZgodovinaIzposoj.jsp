<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Pregled uporabnika</title>
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
					<h1 class="templatemo-header">Pregled zgodovine 
					<c:if test="${zgo eq 'oseba'}"> osebe <a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${o.id}"/>"><c:out value="${o.ime}"/> <c:out value="${o.priimek}"/></a></c:if>
					<c:if test="${zgo eq 'gradivo'}"> gradiva <a  href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&idGradiva=<c:out value="${g.id}"/>"><c:out value="${g.naslov}"/>	</a></c:if>
					</h1>
				<c:if test="${sessionScope.Prijava==true}">	
					<br/>
					<c:if test="${zgo eq 'oseba'}">
						<table class="razmaki">
							<tr><th>Št. gradiva</th><th>Naslov</th><th>Od</th><th>Do</th></tr>
						<c:forEach var="izposoja" items="${zgodovinaO}">        
							<tr><td><c:out value="${izposoja.gradivo.id+1000}"/></td>
							<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&idGradiva=<c:out value="${izposoja.gradivo.id}"/>"><c:out value="${izposoja.gradivo.naslov}"/></a></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${izposoja.storitev.datumIzposoje}" /></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${izposoja.storitev.datumVrnitve}" /></td>
							</tr>
						</c:forEach>
						</table>
					</c:if>
						
					<c:if test="${zgo eq 'gradivo'}">
					<table class="razmaki">
							<tr><th>Članska št.</th><th>Oseba</th><th>Od</th><th>Do</th></tr>
						<c:forEach var="izposoja" items="${zgodovinaG}">        
							<tr><td><c:out value="${izposoja.oseba.id+1000}"/></td>
							<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${izposoja.oseba.id}"/>"><c:out value="${izposoja.oseba.ime}"/> <c:out value="${izposoja.oseba.priimek}"/></a></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${izposoja.storitev.datumIzposoje}" /></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${izposoja.storitev.datumVrnitve}" /></td>
							</tr>
						</c:forEach>
						</table>
					</c:if>	
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