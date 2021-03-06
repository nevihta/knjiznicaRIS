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
		document.getElementById("f").action = '${pageContext.request.contextPath}/AvtorServlet?metoda=urediAvtor';

		var x = document.getElementsByTagName("p");
		document.getElementById("demo").innerHTML = x[id].innerHTML;
		prikaz();
		var n1 = id * 3;
		document.getElementById('ime').value = x[n1].innerHTML;
		var n2 = id * 3 + 1;
		document.getElementById('priimek').value = x[n2].innerHTML;
		var n3 = id * 3 + 2;
		document.getElementById('idAvtor').value = x[n3].innerHTML;
	}

	
</script>
</head>
<body>
	<div class="templatemo-container">
		<%@ include file="meni.jsp"%>

		<div
			class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
			<h1 class="logo-right hidden-xs margin-bottom-60">
				<b>Knjižnica</b> <a id="demo"></a>
			</h1>
			<div class="tm-right-inner-container">
				<h1 class="templatemo-header"></h1>
				<br/>
			<c:if test="${sessionScope.Prijava==true}">
				<table >
					<tr>
						<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiVse">Gradiva</a> | </td>
						<td><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=pridobiVse"><b>Avtorji</b></a> | </td>
						<td><a href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a> | </td> 
						<td><a href="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=pridobiVse">Vrste gradiva</a> | </td> 
						<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=pridobiVse">Založbe</a></td>
					</tr>
				</table>
				<br/>
				
				<input id="dodaj" type="button" value="Dodaj" onClick="prikaz()" class="button" />
				<br/>
				<div id="obrazec" style="display: none;">
					<form id="f"
						action="${pageContext.request.contextPath}/AvtorServlet?metoda=dodaj"
						method="post">
						<input id="ime" type="text" name="ime" class="textbox" /> <input
							id="priimek" type="text" name="priimek" class="textbox" /> <input
							type="submit" value="Potrdi" name="submit" class="button" /> <input
							id="idAvtor" type="hidden" name="idAvtor" class="textbox" />
					</form>
				</div>

				<br />
				<c:if test="${neizbrisan==true}">
						<p>Opozorilo: avtorja ni mogoče izbrisat, saj je že vezan na gradivo.</p>
					</c:if>
				<table class="razmaki">
					<tr>
						<th>Ime</th>
						<th>Priimek</th>
					</tr>

					<c:forEach var="avtor" items="${avtorji}" varStatus="loop">
						<tr>
						
							<td><p><c:out value="${avtor.ime}" /></p></td>
							<td><p><c:out value="${avtor.priimek }" /></p></td>

							<td><p hidden="true"><c:out value="${avtor.id }" /></p></td>
							<td id="brezPresledka"><a onclick="uredi(<c:out value='${loop.index}'/>)">Uredi</a> |</td>
							<td ><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=izbrisi&idAvtor=<c:out value='${avtor.id}' />">Izbriši</a></td>

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