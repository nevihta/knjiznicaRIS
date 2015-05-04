<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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


				<h4>Izposoje:</h4>
				
				<form action="${pageContext.request.contextPath}/StoritevServlet?metoda=<c:out value="${metoda}" />" method="post">
					
					<c:forEach var="izposoja" items="${seznamIzposoj}">
						
						<input type="checkbox" name="gradivaSelect" value="<c:out value="${izposoja.storitev.id}" />"><c:out value="${izposoja.storitev.datumIzposoje}" /><c:out value="${izposoja.gradivo.naslov}" /><br>
							
					</c:forEach>
					
					<!-- Treba je ob gradivu obklukat še datume -->		
					
					 <input	type="submit" value="Potrdi" name="submit" class="button" />							
				</form>
				
				
				


			</div>
		</div>
</body>
</html>