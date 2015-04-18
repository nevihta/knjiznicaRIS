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
<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="../css/templatemo_style.css" rel="stylesheet"
	type="text/css">



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
				</br>

				<c:forEach items="${requestScope.osebe}" var="oseba">
					<tr>
						<td>${oseba.ime}</td>

					</tr>
				</c:forEach>



				<form
					action="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=knjiznicar"
					method="get">

					<input type="submit" value="Izpisi knjiznicarje" class="button" />,

				</form>

			</div>
		</div>
	</div>

</body>
</html>