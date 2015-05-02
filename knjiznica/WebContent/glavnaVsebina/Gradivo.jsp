<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Pregled gradiva</title>
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
					<h1 class="templatemo-header"><c:out value="${gradivo.naslov}" /> </h1>
					<p>
						<a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&urejanjeGr=true&idGradiva=<c:out value='${gradivo.id}' />">Uredi </a> 
						| 
						<a href="${pageContext.request.contextPath}/GradivoServlet?metoda=izbrisi&idGradiva=<c:out value='${gradivo.id}' />">Izbriši</a>
						<c:if test="${uporabnik.id == sessionScope.ID}">
						| <a href="${pageContext.request.contextPath}/GlavnaVsebina/ZgodovinaIzposoj.jsp">Poglej zgodovino izposoj</a>
						</c:if>
					</p>								
					<input type="hidden" name="idGradiva" class="textbox" value='<c:out value="${gradivo.id}" />' />
					<table>
						<tr>
							<td>Naslov v originalu: </td>
							<td><c:out value='${gradivo.originalNaslov}'/></td>
						</tr>
						<tr>
							<td>Vrsta gradiva: </td>
							<td><c:out value='${vrsta.naziv}'/></td>
						</tr>
						<tr>
							<td>Št. gradiva:</td>
							<td><c:out value="${1000 + gradivo.id}" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><h3>Avtorji</h3></td>
							<td> </td>
						</tr>
						<c:forEach var="a" items="${avtorji}">
							<tr><td colspan="2"><c:out value="${a.ime}"/> <c:out value="${a.priimek}"></c:out></td></tr>
						</c:forEach>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><h3>Ostali podatki</h3></td>
							<td></td>
						</tr>
						<tr>
							<td>Jezik:</td>
							<td><c:out value="${gradivo.jezik}" /></td>
						</tr>
						<tr>
							<td>Podrocje:</td>
							<td><c:out value="${podrocje.naziv}" /></td>
						</tr>
						<tr>
							<td>Leto izida:</td>
							<td><c:out value="${gradivo.letoIzida}" /></td>
						</tr>
						<tr>
							<td>Zalozba:</td>
							<td><c:out value="${zalozba.naziv}" /></td>
						</tr>
						<tr>
							<td>ISBN:</td>
							<td><c:out value="${gradivo.ISBN}" /></td>
						</tr>
						<tr>
							<td>Opis:</td>
							<td><c:out value="${gradivo.opis}" /></td>
						</tr>
					</table>

				</div>
			</div>
		</div>
	</body>
</html>