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
				<h1 class="templatemo-header">Registracija</h1>
				</br>
				<form
					action="${pageContext.request.contextPath}/OsebaServlet?metoda=registracija"
					method="post">
					<table>
						<tr class="spaceUnder">
							<td><h4>Ime:</h4></td>
							<td><input type="text" name="ime" class="textbox" /></td>
						</tr>
						<tr>
							<td><h4>Priimek:</h4></td>
							<td><input type="text" name="priimek" class="textbox" /></td>
						</tr>
						<tr class="spaceUnder">
							<td><h4>Tip uporabnika:</h4></td>
						</tr>
						<tr class="spaceUnder"><td><input type="radio" name="tipOsebe" class="radio" /></td>
							<td>Član
							</td>
						</tr>
						<tr class="spaceUnder"><td><input type="radio" name="tipOsebe" class="radio" /></td>
							<td>Knjiižničar</td>

						</tr>
						<tr class="spaceUnder">
							<td><h4>Email:</h4></td>
							<td><input type="text" name="email" class="textbox" /></td>
						</tr>
						<tr class="spaceUnder">
							<td><h4>Telefon:</h4></td>
							<td><input type="text" name="telefon" class="textbox" /></td>
						</tr>
						<tr class="spaceUnder">
							<td><h4>Geslo:</h4></td>
							<td><input type="password" name="geslo" class="textbox" /></td>
						</tr>
						<tr class="spaceUnder">
							<td><input type="submit" value="Potrdi" name="submit"
								class="button" /></td>

						</tr>
					</table>

				</form>



			</div>
		</div>
	</div>

</body>
</html>