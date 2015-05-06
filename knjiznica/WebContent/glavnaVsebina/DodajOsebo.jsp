<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Uporabnik</title>
		<%@ include file="head.jsp"%>
	
		<script type="text/javascript">
			function yesnoCheck() {
				if (document.getElementById('uporabnik').checked) {
		
					document.getElementById('knjiznicarDA').style.display = 'none';
				} else if (document.getElementById('knjiznicar').checked) {
					document.getElementById('knjiznicarDA').style.display = 'block';
		
				}
			}
		</script>
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp" %>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header">Uporabnik</h1>
					<c:if test="${sessionScope.Prijava==true}">
					<form id="forma"
						action="${pageContext.request.contextPath}/OsebaServlet?metoda=<c:out value="${metoda}" />"
						method="post">
												
						<input type="hidden" name="idOsebe" class="textbox" value='<c:out value="${uporabnik.id}" />' />
	
						<h4>Ime:</h4>
						<input type="text" name="ime" class="textbox" value='<c:out value="${uporabnik.ime}" />' required/>
	
						<h4>Priimek:</h4>
						<input type="text" name="priimek" class="textbox" value='<c:out value="${uporabnik.priimek}" />' required/>
						
						<c:if test="${uporabnik.ime==null}">
							<h4>Tip uporabnika:</h4>
							<p>
								Knjižničar <input id="knjiznicar" value="knjižničar" type="radio"
									name="tip" class="radio" onclick="javascript:yesnoCheck();" required/>
									
								Član<input id="uporabnik" value="član" type="radio" name="tip"
									class="radio" onclick="javascript:yesnoCheck();" />
							</p>	
		
							<div id="knjiznicarDA" style="display: none">
								<h3>Račun: </h3>
								<h4>Uporabniško ime:</h4>
								<input type="text" name="upIme" class="textbox" />
		
								<h4>Geslo:</h4>
								<input type="password" name="geslo" class="textbox" />
							</div>
						</c:if>
						
						<br />
						<h3>Naslov: </h3>
						<h4>Ulica:</h4>
						<input type="text" name="ulica" class="textbox" value='<c:out value="${naslov.ulica}" />' required/>
	
						<h4>Hišna številka:</h4>
						<input type="text" name="hisna" class="textbox" value='<c:out value="${naslov.hisnaSt}" />' required/>
	
						<h4>Mesto:</h4>
						<input type="text" name="mesto" class="textbox" value='<c:out value="${naslov.mesto}" />' required/>
	
						<h4>Poštna številka:</h4>
						<input type="number" name="posta" class="textbox" value='<c:out value="${naslov.postnaSt}" />' required/>
	
						<h4>Država:</h4>
						<input type="text" name="drzava" class="textbox" value='<c:out value="${naslov.drzava}" />' required/> 
						<input type="hidden" name=idNaslov value='<c:out value="${naslov.id}" />' />
						
						<h3>Kontakt: </h3>
						<h4>Email:</h4>
						<input type="email" name="email" class="textbox" value='<c:out value="${uporabnik.email}" />' required/>
	
						<h4>Telefon:</h4>
						<input type="text" name="tel" class="textbox" value='<c:out value="${uporabnik.telefon}" />' required/>

						<br /> 
						<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/> 
						<input type="submit" value="Potrdi" name="submit" class="button" />

					</form>
					
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