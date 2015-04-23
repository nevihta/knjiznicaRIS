<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Nova prijava</title>
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
					<h1 class="templatemo-header">Nova prijava</h1>
					<br />
					
					<form action="${pageContext.request.contextPath}/OsebaServlet?metoda=<c:out value="${metoda}" />" method="post">
						<table>
							<tr class="spaceUnder">
								<td><h4>Ime:</h4></td>
								<td><input type="text" name="ime" value='<c:out value="${ime}"/>' class="textbox" required></td>
							</tr>
							<tr class="spaceUnder">
								<td><h4>Geslo:</h4></td> 
								<td><input type="password" name="geslo" class="textbox" required></td>
							</tr>
							<c:if test="${ime!=null }">
								<tr class="spaceUnder">
									<td><h4>Novo geslo:</h4></td> 
									<td><input type="password" name="gesloN" class="textbox" required></td>
								</tr>
							</c:if>
							<tr class="spaceUnder">
								<c:if test="${metoda!= 'dodajPrijavo'}">
									<td><input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/></td>
								</c:if>
								<td><input type="submit" value="Potrdi" name="submit" class="button"/></td>
							</tr>
						</table>
						<input type="hidden" name="idOsebe" value='<c:out value="${idOsebe}"/>'/>
					</form>
					
					<c:if test="${sessionScope.Sprememba==false}">
						<c:out value="Vnesli ste napacno geslo"/>
					</c:if>
				</div>
			</div>
		</div>
	</body>
</html>