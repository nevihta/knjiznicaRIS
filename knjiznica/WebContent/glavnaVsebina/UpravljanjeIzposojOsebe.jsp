<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Upravljanje izposoj osebe</title>
<%@ include file="head.jsp"%>
<script src="${pageContext.request.contextPath}/js/dodajanjeGradiva.js"></script>

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
				<h1 class="templatemo-header">Upravljanje izposoj osebe</h1>
				<c:if test="${sessionScope.Prijava==true}">

				<h4>
				<c:if test="${metoda eq 'vrni' }">Vrni:</c:if>
				<c:if test="${metoda eq 'podaljsaj' }">Podaljšaj:</c:if>
				</h4>
				
				<c:if test="${seznamIzposoj.size()==0}">
					<br/>
					Vsa gradiva izbranega uporabnika so že bila podaljšana.
				</c:if>
				
				<c:if test="${seznamIzposoj.size()!=0}">

				
				<form action="${pageContext.request.contextPath}/StoritevServlet?metoda=<c:out value="${metoda}" />&idOsebe=<c:out value="${idO}"/>" method="post">
					<table>
						<tr>
						<th>x</th>
						<th>Št. gradiva</th>
						<th>Naslov </th>
						<th>Rok vrnitve</th>
						</tr>
						<c:forEach var="izposoja" items="${seznamIzposoj}">
							<tr>
							<td><input type="checkbox" name="gradivaSelect" value="<c:out value='${izposoja.storitev.id}'/><c:if test="${metoda eq 'podaljsaj'}">*<c:out value='${izposoja.storitev.rokVrnitve}'/></c:if>"></td>
							<td><c:out value="${izposoja.gradivo.id + 1000 }"/></td>
							<td><c:out value="${izposoja.gradivo.naslov}"/></td>
							<td><fmt:formatDate pattern="dd. MM. yyyy" value="${izposoja.storitev.rokVrnitve}" /></td>
							</tr>
						</c:forEach>
					</table>
					
					<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/>
					<input	type="submit" value="Potrdi" name="submit" class="button" />						
				</form>
				
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