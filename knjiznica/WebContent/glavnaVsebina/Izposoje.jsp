<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
			document.getElementById('obrazecO').style.display = 'none';
			document.getElementById('obrazecS').style.display = 'none';


		}
		function Podaljsaj() {
			document.getElementById('obrazecP').style.display = 'block';
			document.getElementById('obrazecV').style.display = 'none';
			document.getElementById('obrazecO').style.display = 'none';
			document.getElementById('obrazecS').style.display = 'none';
		}
		function OsebaX() {
			document.getElementById('obrazecO').style.display = 'block';
			document.getElementById('obrazecV').style.display = 'none';
			document.getElementById('obrazecP').style.display = 'none';
			document.getElementById('obrazecS').style.display = 'none';
		}
		function Status() {
			document.getElementById('obrazecS').style.display = 'block';
			document.getElementById('obrazecV').style.display = 'none';
			document.getElementById('obrazecO').style.display = 'none';
			document.getElementById('obrazecP').style.display = 'none';
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
					<c:if test="${sessionScope.Prijava==true}">		
					<br/>	
				<form action="${pageContext.request.contextPath}/StoritevServlet" method="get">
				<table>
				<tr><td><input type="hidden" name="metoda" value="vsiClani"/><input type="submit" value="Izposodi" class="button"> </td>
				<td><input id="podaljsaj" type="button" value="Podaljšaj" onClick="Podaljsaj()" class="button" /></td>
				<td><input id="vrni" type="button" value="Vrni" onClick="Vrni()" class="button" /></td>
				<td><input id="osebeX" type="button" value="Filtriraj po osebi" onClick="OsebaX()" class="button" /></td>
				<td><input id="stanje" type="button" value="Filtriraj glede na status" onClick="Status()" class="button" /></td>
				
				</table>	
				</form>
				<br/>							
								
				<div id="obrazecS" style="display: none;">
					<form method="get" action="${pageContext.request.contextPath}/StoritevServlet">
					<input type="hidden" name="metoda" value="pridobiVseAktualneIzposoje"/>
					<input type="hidden" name="filter" value="s"/>
					
		 			<table>
		 			<tr><td> Filtriraj glede na status:</td></tr>
		 			<tr><td><h3></h3></td></tr>
		 			<tr><td>Preko roka </td><td><input type="radio" name="statusFilter" value="rok"></td></tr>
		 			<tr><td>Aktualne </td><td><input type="radio" name="statusFilter" value="akt"></td></tr>
		 			<tr><td>Pretekle </td><td><input type="radio" name="statusFilter" value="pret"></td></tr>
		 			<tr><td><h4></h4></td></tr>
		 			<tr><td><input type="submit" value="Prikaži rezultate" name="submit" class="button" />	</td></tr>
					</table>
					</form>
					<br/>
				</div>	
						
				<div id="obrazecO" style="display: none;">
					<form method="get" action="${pageContext.request.contextPath}/StoritevServlet">
					<input type="hidden" name="metoda" value="pridobiVseAktualneIzposoje"/>
					<input type="hidden" name="filter" value="o"/>
			 			
			 		<table>
			 			<tr><td> Filtriraj po osebi:</td></tr>
			 			<tr><td><h3></h3></td></tr>
			 			<tr><td>Ime:</td><td><input type="text" name="imeOsebeFilter" placeholder="ime"></td></tr>
			 			<tr><td>Priimek:</td><td><input type="text" name="priimekOsebeFilter" placeholder="priimek"></td></tr>
			 			<tr><td><h4></h4></td></tr>
			 			<tr><td></td><td><input type="submit" value="Prikaži rezultate" name="submit" class="button" />	</td></tr>
						</table>
					</form>
					<br/>
				</div>	
															
				<div id="obrazecP" style="display: none;">
					<form method="get" action="${pageContext.request.contextPath}/StoritevServlet">
					<input type="hidden" name="metoda" value="nastaviPodaljsanje"/>
		 			<table>
		 			<tr><td>Podaljšaj gradivo osebe: </td><td>
		 			<select name="idOsebe">
						<option value="-1"> ----- </option>
						<c:forEach var="o" items="${osebe}" >
								<option value='<c:out value="${o.id}" />'><c:out value="${o.ime}" /> <c:out value="${o.priimek}" /></option>
						</c:forEach>
					</select>
		 			</td></tr>
		 			<tr><td><h4></h4></td></tr>
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
								<option value='<c:out value="${o.id}" />'><c:out value="${o.ime}" /> <c:out value="${o.priimek}" /></option>
						</c:forEach>
					</select>
		 			</td></tr>
		 			<tr><td><h4></h4></td></tr>
		 			<tr><td><input type="submit" value="Vrni" name="submit" class="button" />	</td></tr>
					</table>
					</form>
				<br/>
				</div>

				<c:if test="${filter}"><c:if test="${seznamIzposoj.size()==0}">
				Uporabnik nima trenutno izposojenega nobenega gradiva.
				<br/>
				<br/>
				</c:if></c:if>
				<table class="razmaki">
				<tr>
				<th>Zap št</th>
				<th>Gradivo</th>
				<th>Oseba</th>
				<th>Datum izposoje</th>
				<th>Rok vrnitve</th>
				<c:if test="${pretekle}">
				<th>Datum vrnitve</th>
				</c:if>		
				</tr>
				<c:forEach var="i" items="${seznamIzposoj}">
				<tr>
					<td><c:out value="${i.storitev.id}"/></td>
					<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiGradivo&idGradiva=<c:out value="${i.gradivo.id}"/>"><c:out value="${i.gradivo.naslov}"/></a></td>
					<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&idOsebe=<c:out value="${i.oseba.id}"/>"><c:out value="${i.oseba.ime}"/> <c:out value="${i.oseba.priimek}"/></a></td>
					<td><fmt:formatDate pattern="dd. MM. yyyy" value="${i.storitev.datumIzposoje}" /></td>
					<td><fmt:formatDate pattern="dd. MM. yyyy" value="${i.storitev.rokVrnitve}" /></td>
					<c:if test="${pretekle}">
					<td><fmt:formatDate pattern="dd. MM. yyyy" value="${i.storitev.datumVrnitve}" /></td>
					</c:if>	
				</tr>
				</c:forEach>
						
				</table>
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
			