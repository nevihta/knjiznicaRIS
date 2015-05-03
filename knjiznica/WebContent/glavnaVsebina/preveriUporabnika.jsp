<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Preveri uporabnika</title>
		<%@ include file="head.jsp"%>
		<script src="${pageContext.request.contextPath}/js/dodajanjeGradiva.js"></script>
		
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp" %>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header">Preveri uporabnika</h1>
					<form id="forma"
						action="${pageContext.request.contextPath}/StoritevServlet?metoda=nastaviIzposojo"
						method="post">
												
						<h4>Član:</h4>
						<a href="javascript:unhide('osebaS', 'osebaI')" class="button">Izberi iz seznama</a>
    					<a href="javascript:unhide('osebaI', 'osebaS')" class="button">Vnesi ID</a><br /> <br /> 
						
						<div id="osebaI"  class="hidden">
							<input type="number" name="osebaInput" class="textbox" />
						</div>
						
						<div id="osebaS" >
							<select name="osebaSelect">
								<option value="nic"> ----- </option>
							  	<c:forEach var="clan" items="${clani}" >
							  	
									<option value='<c:out value="${clan.id}" />' ${clan.id == clan.id ? 'selected' : ''}><c:out value="${clan.ime}" /> <c:out value="${clan.priimek}" /></option>
								</c:forEach>
							</select>
						</div>
						<br>
						<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/> 
						<input type="submit" value="Potrdi" name="submit" class="button" />

					</form>
				</div>
			</div>
		</div>
	</body>
</html>