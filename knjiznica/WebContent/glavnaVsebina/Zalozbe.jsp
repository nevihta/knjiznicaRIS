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
		document.getElementById("f").action = '${pageContext.request.contextPath}/ZalozbaServlet?metoda=urediZalozbo';

		var x = document.getElementsByTagName("p");
	
		prikaz();
		var n1 = id*2;
		document.getElementById('naziv').value = x[n1].innerHTML;
		var n2 = id*2+1
		document.getElementById('idZalozba').value = x[n2].innerHTML;
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
				<h1 class="templatemo-header"></h1>
				<br/>
				<table >
					<tr>
						<td><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiVse">Gradiva</a> | </td>
						<td><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=pridobiVse">Avtorji</a> | </td>
						<td><a href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a> | </td> 
						<td><a href="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=pridobiVse">Vrste gradiva</a> | </td> 
						<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=pridobiVse"><b>Založbe</b></a></td>
					</tr>
				</table>
				<br/>
				<input id="dodaj" type="button" value="Dodaj" onClick="prikaz()" class="button" />
				<br/>
				<div id="obrazec" style="display: none;">
					<form id="f"
						action="${pageContext.request.contextPath}/ZalozbaServlet?metoda=dodaj"
						method="post">
						<input id="naziv" type="text" name="naziv" class="textbox" />  <input
							type="submit" value="Potrdi" name="submit" class="button" /> <input
							id="idZalozba" type="hidden" name="idZalozba" class="textbox" />
					</form>
				</div>

				<br />
				<table id="izpisZaložb">
					<tr>
						<th>Naziv založbe</th>
						

					</tr>

					<c:forEach var="zalozba" items="${zalozbe}" varStatus="loop">
						<tr>
						
							<td><p><c:out value="${zalozba.naziv}" /></p></td>							
							<td><p hidden="true"><c:out value="${zalozba.id }" /></p></td>
							<td><a onclick="uredi(<c:out value='${loop.index}'/>)">Uredi</a> |</td>
							<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=izbrisi&idZalozba=<c:out value='${zalozba.id}' />">Izbriši</a>
							
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>