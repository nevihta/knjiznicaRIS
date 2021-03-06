<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Izposoja</title>
<%@ include file="head.jsp"%>
<script src="${pageContext.request.contextPath}/js/izposoja.js"></script>
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
				<h1 class="templatemo-header">Izposoja</h1>
				<c:if test="${sessionScope.Prijava==true}">
				<h4>Gradivo za izposojo:</h4>

				<form id="forma"
					action="${pageContext.request.contextPath}/StoritevServlet?metoda=izposoja"
					method="post">
					<input type="hidden" value='<c:out value="${idOsebe}" />'
						name="idOsebe" />
					
					<c:if test="${niIzbrano==true}">
						<p>Opozorilo: izberite gradivo za izposojo!</p>
					</c:if>	
					<c:if test="${niProsto==true}">
						<p>Opozorilo: Gradivo <c:out value="${niProstoID}" /> ni mogoce izposoditi!</p>
					</c:if>	
					<div id="gradivaDIV">
						<a href="javascript:dodajSeznam()" class="button">Dodaj prosto gradivo iz seznama</a> 
						<a href="javascript:dodajNovo()"class="button">Vpiši številko gradiva</a>
						<br /> <br /> 
						<select
							name="gradivaSelect">
							<option value="-1">-----</option>
							<c:forEach var="gradivo" items="${prostaGradiva}">
								<option value='<c:out value="${gradivo.id}" />'>
								<c:out	value="${gradivo.naslov}" /></option>
							</c:forEach>

					</select>
					</div>
					<br /> <br /> <input type="button" value="Prekliči"
						onClick="history.go(-1);return true;" class="button" /> <input
						type="submit" value="Potrdi" name="submit" class="button" />
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