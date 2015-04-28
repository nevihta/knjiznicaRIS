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
		document.getElementById("f").action = '${pageContext.request.contextPath}/PodrocjeServlet?metoda=urediPodrocje';

		var x = document.getElementsByTagName("p");
	
		prikaz();
		var n1 = id*2;
		document.getElementById('naziv').value = x[n1].innerHTML;
		var n2 = id*2+1
		document.getElementById('idPodrocje').value = x[n2].innerHTML;
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
				<h1 class="templatemo-header">Pregled področij</h1>
				<input id="dodaj" type="button" value="Dodaj" onClick="prikaz()" class="button" />
				</br>
				<div id="obrazec" style="display: none;">
					<form id="f"
						action="${pageContext.request.contextPath}/PodrocjeServlet?metoda=dodaj"
						method="post">
						<input id="naziv" type="text" name="naziv" class="textbox" />  <input
							type="submit" value="Potrdi" name="submit" class="button" /> <input
							id="idPodrocje" type="hidden" name="idPodrocje" class="textbox" />
					</form>
				</div>



				<br />


				<table>
					<tr>

						<td><a
							href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a>
						</td>
					</tr>
				</table>
				<br>

				<table id="izpisPodrocij">
					<tr>
						<th>Naziv</th>
						

					</tr>

					<c:forEach var="podrocje" items="${podrocja}" varStatus="loop">
						<tr>
						
							<td><p><c:out value="${podrocje.naziv}" /></p></td>							
							<td><p hidden><c:out value="${podrocje.id }" /></p></td>

							<td><button value="${loop.index}"
									onClick="uredi(this.value)" class="button">Uredi</button></td>
									
							<td><form
									action="${pageContext.request.contextPath}/PodrocjeServlet?metoda=izbrisi&idPodrocje=<c:out value='${podrocje.id}' />"
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