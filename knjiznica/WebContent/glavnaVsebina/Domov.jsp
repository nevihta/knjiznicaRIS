<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
<title>Knjižnica</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<div class="templatemo-container">
		<%@ include file="meni.jsp"%>

		<div
			class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">

			<div class="tm-right-inner-container onLoadFade poravnano">
				<h1 class="templatemo-header"></h1>
				<img class="onLoadFade poravnano"
					src="${pageContext.request.contextPath}/images/napis" /> <img
					class="slikaPolica"
					src="${pageContext.request.contextPath}/images/polica.jpg" />
				<article>Aplikacija je namenjena knijžničarjem. Omogoča
					dodajanje članov in knjižničarjev knjižnice, urejanje podatkov o
					njih ter odstranjevanje izpisanih članov. Projekt prav tako omogoča
					dodajanje in brisanje gradiv ter urejanje njihovih in
					vodenje informacij o izposojah za člane, ter vodenje črne liste članov.</article>
					<br/>
					<br/>
					<br/>
					<p style="font-size:15px">Avtorji: <i><b>Anja Špindler, Monika Malok, Klemen Kunstek</b></i></p>


			</div>
		</div>
	</div>
</body>
</html>