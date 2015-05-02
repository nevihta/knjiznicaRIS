<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Knjižnica</title>
<%@ include file="head.jsp"%>
<script type="text/javascript">
	function prikaz() {

		document.getElementById('obrazec').style.display = 'block';
		document.getElementById('dodaj').style.display = 'none';

	}
	function uredi(id) {
		document.getElementById("f").action = '${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=urediVrstoGradiva';

		var x = document.getElementsByTagName("p");
	
		prikaz();
		var n1 = id*2;
		document.getElementById('naziv').value = x[n1].innerHTML;
		var n2 = id*2+1
		document.getElementById('idVrstaGradiva').value = x[n2].innerHTML;
	}

	
</script>
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
				<h1 class="templatemo-header">Pregled vrst gradiv</h1>
				<br/>
				<table >
					<tr>
						<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiVse">Gradivo</a> | </td>
						<td><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=pridobiVse">Avtorji</a> | </td>
						<td><a href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a> | </td> 
						<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=pridobiVse">Založbe</a></td>
					</tr>
				</table>
				<br/>
				
				<input id="dodaj" type="button" value="Dodaj" onClick="prikaz()" class="button" />
				<br/>
				<div id="obrazec" style="display: none;">
					<form id="f"
						action="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=dodaj"
						method="post">
						<input id="naziv" type="text" name="naziv" class="textbox" />  <input
							type="submit" value="Potrdi" name="submit" class="button" /> <input
							id="idVrstaGradiva" type="hidden" name="idVrstaGradiva" class="textbox" />
					</form>
				</div>



				<br />


				<table>
					<tr>

						<td><a
							href="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=pridobiVse">Vrste gradiv</a>
						</td>
					</tr>
				</table>
				<br>

				<table id="izpisVrst">
					<tr>
						<th>Naziv</th>
						

					</tr>

					<c:forEach var="vrsta" items="${vrsteGradiva}" varStatus="loop">
						<tr>
						
							<td><p><c:out value="${vrsta.naziv}" /></p></td>							
							<td><p hidden><c:out value="${vrsta.id }" /></p></td>

							<td><button value="${loop.index}"
									onClick="uredi(this.value)" class="button">Uredi</button></td>
									
							<td><form
									action="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=izbrisi&idVrstaGradiva=<c:out value='${vrsta.id}' />"
									method="post">
									<input type="submit" value="Izbrisi" name="submit"
										class="button" />
								</form></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>