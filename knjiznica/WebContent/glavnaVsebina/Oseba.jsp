<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Pregled uporabnika</title>
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
					<h1 class="templatemo-header"><c:out value="${uporabnik.ime} " /> <c:out value="${uporabnik.priimek}" /></h1>
					<c:if test="${sessionScope.Prijava==true}">
					<p>
						<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiOsebo&urejanjeOs=true&idOsebe=<c:out value='${uporabnik.id}' />">Uredi </a> 
						| 
						<a href="${pageContext.request.contextPath}/OsebaServlet?metoda=izbrisiOsebo&idOsebe=<c:out value='${uporabnik.id}' />">Izbriši</a>
						| <a href="${pageContext.request.contextPath}/StoritevServlet?metoda=pridobiVseAktualneIzposoje&filter=i&id=<c:out value='${uporabnik.id}' />">Poglej aktualne izposoje</a>
						| <a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiZgO&idOsebe=<c:out value='${uporabnik.id}' />">Poglej zgodovino izposoj</a>
						<c:if test="${!cl}">
						| <a href="${pageContext.request.contextPath}/CrnaListaServlet?metoda=dodajOsnaCL&idOsebe=<c:out value='${uporabnik.id}' />">Dodaj na črno listo</a>
						</c:if>
						<c:if test="${uporabnik.id == sessionScope.ID}">
						| <a href="${pageContext.request.contextPath}/OsebaServlet?metoda=urediKnjiznicar">Uredi uporabniško ime in geslo</a>
						
						</c:if>
					</p>								
					<input type="hidden" name="idOsebe" class="textbox" value='<c:out value="${uporabnik.id}" />' />
					<c:if test="${neizbrisan==true}">
						<p>Opozorilo: osebe ni mogoče izbrisati, saj je že uporabila storitve knjižnice.</p>
					</c:if>
					<table>
						<tr>
							<td><c:out value="${uporabnik.tipOsebe}" /></td>
							<td><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=urediTip&idOsebe=<c:out value='${uporabnik.id}' />&tip=<c:out value='${uporabnik.tipOsebe}' />">Spremeni tip</a></td>
						</tr>
						<tr>
							<td></td>
							<td><h4></h4></td>
						</tr>
						<tr>
							<td>Članska št:</td>
							<td><c:out value="${1000 + uporabnik.id}" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><h3>Kontakt</h3></td>
							<td> </td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><c:out value="${uporabnik.email}" /></td>
						</tr>
						<tr>
							<td>Telefon:</td>
							<td><c:out value="${uporabnik.telefon}" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><h3>Naslov</h3></td>
							<td></td>
						</tr>
						<tr>
							<td>Ulica:</td>
							<td><c:out value="${naslov.ulica}" /></td>
						</tr>
						<tr>
							<td>Hišna številka:</td>
							<td><c:out value="${naslov.hisnaSt}" /></td>
						</tr>
						<tr>
							<td>Mesto:</td>
							<td><c:out value="${naslov.mesto}" /></td>
						</tr>
						<tr>
							<td>Poštna številka:</td>
							<td><c:out value="${naslov.postnaSt}" /></td>
						</tr>
						<tr>
							<td>Država:</td>
							<td><c:out value="${naslov.drzava}" /></td>
						</tr>
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