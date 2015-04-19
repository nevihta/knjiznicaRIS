<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Knjižnica</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/templatemo_style.css"
	rel="stylesheet" type="text/css">



</head>
<body>

	<div class="templatemo-container">

		<jsp:include page="meni.jsp" />


		<div
			class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
			<h1 class="logo-right hidden-xs margin-bottom-60">
				<b>Knjižnica</b>
			</h1>
			<div class="tm-right-inner-container">
				<h1 class="templatemo-header">Pregled oseb</h1>
				<table>
					<tr>
						<td>
							<form action="${pageContext.request.contextPath}/OsebaServlet"
								method="get">
								<input type="hidden" name="metoda" value="pridobiVse"> <input
									type="hidden" name="filter" value="clan"> <input
									type="submit" value="Izpisi člane" class="button" />

							</form>
						</td>
						<td>
							<form action="${pageContext.request.contextPath}/OsebaServlet"
								method="get">
								<input type="hidden" name="metoda" value="pridobiVse"> <input
									type="hidden" name="filter" value="knjiznicar"> <input
									type="submit" value="Izpisi knjiznicarje" class="button" />

							</form>
						</td>
						<td>
							<form action="${pageContext.request.contextPath}/OsebaServlet"
								method="get">
								<input type="hidden" name="metoda" value="pridobiVse"> <input
									type="hidden" name="filter" value="vse"> <input
									type="submit" value="Izpisi vse" class="button" />

							</form>
						</td>
				</table>
				<br>

				<form action="${pageContext.request.contextPath}/OsebaServlet"
					method="get">
					<select name="idOsebe" size="10" class="textbox">
						<c:forEach items="${requestScope.osebe}" var="oseba">
							<option value="${oseba.id}">${oseba.ime}
								${oseba.priimek}</option>
						</c:forEach>
					</select> 
					<br><br>
					<input type="hidden" name="metoda" value="pridobiOsebo"> <input
						type="hidden" name="urejanjeOs" value="da"> <input
						type="submit" value="Najdi" class="button" />
				</form>






			</div>
		</div>
	</div>

</body>
</html>