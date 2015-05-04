<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Gradivo</title>
<%@ include file="head.jsp"%>
<script type="text/javascript">
	function prikaz() {

		document.getElementById('obrazec').style.display = 'block';
		document.getElementById('dodaj').style.display = 'none';

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
		<table >
			<tr>
				<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiVse"><b>Gradiva</b></a> | </td>
				<td><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=pridobiVse">Avtorji</a> | </td>
				<td><a href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a> | </td> 
				<td><a href="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=pridobiVse">Vrste gradiva</a> | </td> 
				<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=pridobiVse">Založbe</a></td>
			</tr>
		</table>
		<br/>
		<form action="${pageContext.request.contextPath}/GradivoServlet" method="get">
		<table>
		<tr>
		<td><input type="hidden" name="metoda" value="dodajGr"/><input type="submit" value="Dodaj gradivo" class="button"> </td>
		<td><input id="dodaj" type="button" value="Filtriraj" onClick="prikaz()" class="button" />
		</td></tr>
		
		</table>
		
		
		</form>
		
		<br/>
		<div id="obrazec" style="display: none;">
					
			<form method="get" action="${pageContext.request.contextPath}/GradivoServlet?metoda=prikaziFiltrirane">
			<table>
				<tr><td>Filtriraj glede na: </td></tr>
				<tr><td><h3></h3></td></tr>
				<tr><td>Vrsto gradiva:</td>
				<td><select name="vrstaFilter">
					<option value="-1">----</option>
			 		<c:forEach var="vrsta" items="${vrsteGradiva}">
						<option value='<c:out value="${vrsta.id}" />'><c:out value="${vrsta.naziv}" /></option>
					</c:forEach>
				</select></td></tr>
				<tr><td>Avtorja:</td><td>
				<input type="text" name="imeAvtorjaFilter" placeholder="ime">
				<input type="text" name="priimekAvtorjaFilter" placeholder="priimek">
				</td></tr>
				<tr><td>Jezik: </td>
				<td><select name="jezikFilter">
					<option value="brez">----</option>
			 		<c:forEach var="jez" items="${jeziki}">
						<option value='<c:out value="${jez}" />'><c:out value="${jez}" /></option>
					</c:forEach>
				</select></td></tr>
				<tr><td>Leto izida: </td>
				<td><input type="number" name="letoFilter"></td></tr>
				<tr><td>Področje:</td>
				<td><select name="podrocjeFilter">
						<option value="-1">----</option>
						<c:forEach var="podr" items="${podrocja}" >
							<option value='<c:out value="${podr.id}" />'><c:out value="${podr.naziv}" /></option>
						</c:forEach>
				</select> </td></tr>
				<tr><td>Zalozbo:</td>
				<td><select name="zalozbaFilter">
					<option value="-1">----</option>
					<c:forEach var="zal" items="${zalozbe}" >
							<option value='<c:out value="${zal.id}" />'><c:out value="${zal.naziv}" /></option>
					</c:forEach>
				</select></td></tr>
				<tr><td></td>
				<td><input 	type="submit" value="Prikaži rezultate" name="submit" class="button" />	</td></tr>
			</table>
			</form>
			<br/>
		</div>	
		
		
		<table id="izpisGradiva">
			<tr>
				<th>Št. gradiva</th>
				<th>Naslov</th>
				<th>Avtorji</th>	
				<th>Vrsta </th>	
				<th>Področje</th>
				<th>Založba</th>
			</tr>
						
			<c:forEach var="gradivo" items="${gradiva}">
			<tr>
							
				<td><c:out value="${1000+gradivo.id}" /></td>
				<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&idGradiva=<c:out value="${gradivo.id}" />"><c:out value="${gradivo.naslov}" /></a></td>
				<td><c:forEach var="avtor" items="${gradivo.avtorji}">
				<c:out value="${avtor.ime}"/> <c:out value="${avtor.priimek}"/> <br/>
				</c:forEach></td>
				<td><c:out value="${gradivo.vrsta }" /></td>
				<td><c:out value="${gradivo.podrocje}"/></td>
				<td><c:out value="${gradivo.zalozba}"/></td>
			</tr>
			</c:forEach>
		</table>
		</div>
	</div>
</div>
</body>
</html>