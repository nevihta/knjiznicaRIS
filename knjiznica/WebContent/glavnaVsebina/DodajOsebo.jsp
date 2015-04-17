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

<script type="text/javascript">
	function yesnoCheck() {
		if (document.getElementById('uporabnik').checked) {
			document.getElementById('uporabnikDA').style.display = 'block';
			document.getElementById('knjiznicarDA').style.display = 'none';
		} else if (document.getElementById('knjiznicar').checked) {
			document.getElementById('knjiznicarDA').style.display = 'block';
			document.getElementById('uporabnikDA').style.display = 'none';
		}
	}
</script>

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
					action="${pageContext.request.contextPath}/OsebaServlet?metoda=dodaj"
					method="post">


					<h4>Ime:</h4>
					<input type="text" name="ime" class="textbox" />

					<h4>Priimek:</h4>
					<input type="text" name="priimek" class="textbox" />

					<h4>Tip uporabnika:</h4>
					<p>
						Knjiižničar <input id="knjiznicar" type="radio" name="tip"
							class="radio" onclick="javascript:yesnoCheck();" />
					</p>

					<p>
						Uporabnik<input id="uporabnik" type="radio" name="tipOsebe"
							class="radio" onclick="javascript:yesnoCheck();" />
					</p>


					<div id="knjiznicarDA" style="display: none">
						<h4>Uporabniško ime:</h4>
						<input type="text" name="upIme" class="textbox" />

						<h4>Geslo:</h4>
						<input type="password" name="geslo" class="textbox" />
					</div>

					<div id="uporabnikDA" style="display: none">
					
						<h4>Ulica:</h4>
						<input type="text" name="ulica" class="textbox" />

						<h4>Hisna:</h4>
						<input type="text" name="hisna" class="textbox" />

						<h4>Mesto:</h4>
						<input type="text" name="mesto" class="textbox" />

						<h4>Pošta:</h4>
						<input type="text" name="posta" class="textbox" />

						<h4>Država:</h4>
						<input type="text" name="drzava" class="textbox" />

						<h4>Email:</h4>
						<input type="text" name="email" class="textbox" />

						<h4>Telefon:</h4>
						<input type="text" name="tel" class="textbox" />

					</div>


					</br> <input type="submit" value="Potrdi" name="submit" class="button" />



				</form>



			</div>
		</div>
	</div>

</body>
</html>