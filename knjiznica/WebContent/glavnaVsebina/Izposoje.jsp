<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Knjižnica</title>
		<%@ include file="head.jsp"%>
		<script type="text/javascript">
		function Vrni() {
			document.getElementById('obrazecV').style.display = 'block';
			document.getElementById('obrazecP').style.display = 'none';
		}
		function Podaljsaj() {
			document.getElementById('obrazecP').style.display = 'block';
			document.getElementById('obrazecV').style.display = 'none';
		}
		</script>
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp" %>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header"></h1>			
					<br/>	
				<form action="${pageContext.request.contextPath}/StoritevServlet" method="get">
				<table>
				<tr><td><input type="hidden" name="metoda" value="vsiClani"/><input type="submit" value="Izposodi" class="button"> </td>
				<td><input id="podaljsaj" type="button" value="Podaljšaj" onClick="Podaljsaj()" class="button" />
				<td><input id="vrni" type="button" value="Vrni" onClick="Vrni()" class="button" />
				</table>	
				</form>
				<br/>				
				
				<div id="obrazecP" style="display: none;">
					<form method="get" action="${pageContext.request.contextPath}/StoritevServlet">
					<input type="hidden" name="metoda" value="nastaviPodaljsanje"/>
		 			<table>
		 			<tr><td>Podaljšaj gradivo osebe: </td><td>
		 			<select name="idOsebe">
						<option value="-1"> ----- </option>
						<c:forEach var="o" items="${osebe}" >
								<option value='<c:out value="${o.id}" />'><c:out value="${o.ime}" /><c:out value="${o.priimek}" /></option>
						</c:forEach>
					</select>
		 		
		 			</td></tr>
		 			<tr><td><input type="submit" value="Podaljšaj" name="submit" class="button" />	</td></tr>
					</table>
					</form>
				<br/>
				</div>
				
				<div id="obrazecV" style="display: none;">
					<form method="get" action="${pageContext.request.contextPath}/StoritevServlet">
					<input type="hidden" name="metoda" value="nastaviVracilo"/>
		 			<table>
		 			<tr><td>Vrni gradivo osebe: </td><td>
		 			<select name="idOsebe">
						<option value="-1"> ----- </option>
						<c:forEach var="o" items="${osebe}" >
								<option value='<c:out value="${o.id}" />'><c:out value="${o.ime}" /><c:out value="${o.priimek}" /></option>
						</c:forEach>
					</select>
		 		
		 			</td></tr>
		 			<tr><td><input type="submit" value="Vrni" name="submit" class="button" />	</td></tr>
					</table>
					</form>
				<br/>
				</div>
				
				
				<table>
				<tr>
				<th>Zap št</th>
				<th>Gradivo</th>
				<th>Oseba</th>
				<th>Datum izposoje</th>
				<th>Rok vrnitve</th>
						
				</tr>
				<c:forEach var="i" items="${seznamIzposoj}">
				<tr>
					<td><c:out value="${i.storitev.id}"/></td>
					<td><c:out value="${i.gradivo.naslov}"/></td>
					<td><c:out value="${i.oseba.ime}"/> <c:out value="${i.oseba.priimek}"/></td>
					<td><c:out value="${i.storitev.datumIzposoje}"/></td>
					<td><c:out value="${i.storitev.rokVrnitve}"/></td>
				</tr>
				</c:forEach>
						
				</table>
				
				</div>
			</div>
		</div>
	</body>
</html>
			