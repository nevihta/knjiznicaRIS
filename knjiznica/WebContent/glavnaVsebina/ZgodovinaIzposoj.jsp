<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<c:if test="${zgo eq 'oseba'}"> osebe <c:out value="${o.ime}"/> <c:out value="${o.priimek}"/></c:if>
					<c:if test="${zgo eq 'gradivo'}"> gradiva <c:out value="${g.naslov}"/>	</c:if>
					</h1>
					
				<br/>
				<c:if test="${zgo eq 'oseba'}">
					<table>
						<tr><th>Št. gradiva</th><th>Naslov</th><th>Od</th><th>Do</th></tr>
					<c:forEach var="izposoja" items="${zgodovinaO}">        
						<tr><td><c:out value="${izposoja.gradivo.id+1000}"/></td>
						<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&idGradiva=<c:out value="${izposoja.gradivo.id}"/>"><c:out value="${izposoja.gradivo.naslov}"/></a></td>
						<td><c:out value="${izposoja.storitev.datumIzposoje}"/></td>
						<td><c:out value="${izposoja.storitev.datumVrnitve}"/></td>
					</c:forEach>
					</table>
				</c:if>
					
				<c:if test="${zgo eq 'gradivo'}">
					<table>
						<tr><th>Članska št.</th><th>Oseba</th><th>Od</th><th>Do</th></tr>
					<c:forEach var="izposoja" items="${zgodovinaG}">        
						<tr><td><c:out value="${izposoja.gradivo.id+1000}"/></td>
						<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${izposoja.oseba.id}"/>"><c:out value="${izposoja.oseba.ime}"/> <c:out value="${izposoja.oseba.priimek}"/></a></td>
						<td><c:out value="${izposoja.storitev.datumIzposoje}"/></td>
						<td><c:out value="${izposoja.storitev.datumVrnitve}"/></td>
					</c:forEach>
					</table>
				</c:if>	
					
				</div>
			</div>	
		</div>
	</body>	
</html>	