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
					<table>
						<tr>
							<td>
								<form action="${pageContext.request.contextPath}/OsebaServlet" method="get">
									<input type="hidden" name="metoda" value="pridobiVse"> <input
										type="hidden" name="filter" value="clan"> <input
										type="submit" value="Izpisi člane" class="button" />
								</form>
							</td>
							<td>
								<form action="${pageContext.request.contextPath}/OsebaServlet" method="get">
									<input type="hidden" name="metoda" value="pridobiVse"> <input
										type="hidden" name="filter" value="knjiznicar"> <input
										type="submit" value="Izpisi knjiznicarje" class="button" />
								</form>
							</td>
							<td>
								<form action="${pageContext.request.contextPath}/OsebaServlet"
									method="get">
									<input type="hidden" name="metoda" value="pridobiVse"> <input
										type="hidden" name="filter" value="vse"> <input
										type="submit" value="Izpisi vse" class="button" />
								</form>
							</td>
					</table>
					<br>
	
					<form action="${pageContext.request.contextPath}/OsebaServlet" method="get">
						<select name="idOsebe" size="10" class="textbox">
							<c:forEach items="${requestScope.osebe}" var="oseba">
								<option value="${oseba.id}">${oseba.ime}
									${oseba.priimek}</option>
							</c:forEach>
						</select> 
						<br><br>
						<input type="hidden" name="metoda" value="pridobiOsebo"> <input
							type="hidden" name="urejanjeOs" value="da"> <input
							type="submit" value="Uredi" class="button" />
					</form>
				</div>
			</div>
		</div>
	</body>
</html>