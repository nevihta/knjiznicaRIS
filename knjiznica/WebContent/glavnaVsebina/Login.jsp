<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Prijava</title>
		<%@ include file="head.jsp"%>
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp"%>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header">Prijava</h1>
					<br />
					
					<form action="${pageContext.request.contextPath}/OsebaServlet?metoda=prijava" method="post">
						<table>
							<tr class="spaceUnder">
								<td><h4>Ime:</h4></td>
								<td><input type="text" name="ime" class="textbox" required></td>
							</tr>
							<tr class="spaceUnder">
								<td><h4>Geslo:</h4></td> 
								<td><input type="password" name="geslo" class="textbox" required></td>
							</tr>
							<tr class="spaceUnder">
								<td><input type="submit" value="Potrdi" name="submit" class="button"/></td>
							</tr>
						</table>
					</form>
					
					<c:if test="${sessionScope.Prijava==false}">
						<c:out value="Vnesli ste napacno ime ali geslo" />
					</c:if>
				</div>
			</div>
		</div>
	</body>
</html>