<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Gradivo</title>
		<%@ include file="head.jsp"%>
		<script src="${pageContext.request.contextPath}/js/dodajanjeGradiva.js"></script>
	</head>
	<body>
		<div class="templatemo-container">
			<%@ include file="meni.jsp" %>
			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
				<h1 class="logo-right hidden-xs margin-bottom-60">
					<b>Knjižnica</b>
				</h1>
				
				<div class="tm-right-inner-container">
					<h1 class="templatemo-header">Gradivo</h1>
					<form id="forma"
						action="${pageContext.request.contextPath}/GradivoServlet?metoda=<c:out value="${metoda}" />"
						method="post">
												
						<input type="hidden" name="idGradiva" class="textbox" value='<c:out value="${gradivo.id}" />' />
						<c:if test="${niAvtorjev==true}">
							<p>Opozorilo: izberite avtorja!</p>
						</c:if>	
						<c:if test="${niOstalo==true}">
							<p>Opozorilo: Izberite področje, vrsto gradiva in založbo!</p>
						</c:if>
						<h4>Naslov:</h4>
						<input type="text" name="naslov" class="textbox" value='<c:out value="${gradivo.naslov}" />' required/>
	
						<h4>Original naslov:</h4>
						<input type="text" name="originalNaslov" class="textbox" value='<c:out value="${gradivo.originalNaslov}" />' required/>

						<h4>Avtor:</h4>
						<div id="avtorjiDIV">
							<a href="javascript:dodajSeznam()" class="button">Dodaj avtorja iz seznama</a>
							<a href="javascript:dodajNovo()" class="button">Dodaj novega avtorja</a><br /><br />
							
							<select name="avtorjiSelect">
								<option value="-1"> ----- </option>
								<c:forEach var="avt" items="${vsiAvtorji}" >
									<option value='<c:out value="${avt.id}" />' ${avt.id == avtorji[0].id ? 'selected' : ''}><c:out value="${avt.ime}" /> <c:out value="${avt.priimek}" /></option>
								</c:forEach>
							</select>
							<br />
							<c:forEach items="${avtorji}" var="avtor" varStatus="i">
								<c:if test="${i.index >= 1}">
									<div id="${i.index }urejanje">
										<select name="avtorjiSelect">
											<option value="-1"> ----- </option>
											<c:forEach var="avt" items="${vsiAvtorji}" >
												<option value='<c:out value="${avt.id}" />' ${avt.id == avtor.id ? 'selected' : ''}><c:out value="${avt.ime}" /> <c:out value="${avt.priimek}" /></option>
											</c:forEach>
										</select>
										<a href="javascript:izbrisi('${i.index }urejanje')">Izbriši</a><br /><br />
									</div>
						       	</c:if>
						    </c:forEach>
						</div>

						<h4>Leto izida:</h4>
						<input type="number" name="leto" class="textbox" value='<c:out value="${gradivo.letoIzida}" />' required/>
	
						<h4>ISBN:</h4>
						<input type="text" name="isbn" class="textbox" value='<c:out value="${gradivo.ISBN}" />' required/>
	
						<h4>Opis:</h4>
						<textarea rows="10" cols="50" maxlength="200" name="opis" required><c:out value="${gradivo.opis}" /></textarea>
	
						<h4>Jezik:</h4>
						<select name="jezik">
						  	<c:forEach var="jez" items="${jeziki}" >
								<option value='<c:out value="${jez}" />'><c:out value="${jez}" /></option>
							</c:forEach>
						</select> 
	
						<h4>Področje:</h4>
						<a href="javascript:unhide('podrocjeS', 'podrocjeI')" class="button">Izberi iz seznama</a>
    					<a href="javascript:unhide('podrocjeI', 'podrocjeS')" class="button">Dodaj novo</a><br /> <br /> 
						<div id="podrocjeI"  class="hidden">
							<input type="text" name="podrocjeInput" class="textbox" /> 
						</div>
						<div id="podrocjeS" >
							<select name="podrocjeSelect">
								<option value="nic"> ----- </option>
							  	<c:forEach var="podr" items="${podrocja}" >
									<option value='<c:out value="${podr.id}" />' ${podr.id == podrocje.id ? 'selected' : ''}><c:out value="${podr.naziv}" /></option>
								</c:forEach>
							</select> 
						</div>
						
						<h4>Vrsta gradiva: </h4>
						<a href="javascript:unhide('vrstaS', 'vrstaI')" class="button">Izberi iz seznama</a>
    					<a href="javascript:unhide('vrstaI', 'vrstaS')" class="button">Dodaj novo</a><br /> <br /> 
						<div id="vrstaI"  class="hidden">
							<input type="text" name="vrstaInput" class="textbox" />
						</div>
						<div id="vrstaS" >
							<select name="vrstaSelect">
								<option value="nic"> ----- </option>
							  	<c:forEach var="vrst" items="${vrsteGradiva}" >
									<option value='<c:out value="${vrst.id}" />' ${vrst.id == vrsta.id ? 'selected' : ''}><c:out value="${vrst.naziv}" /></option>
								</c:forEach>
							</select>
						</div>
						
						<h4>Založba:</h4>
						<a href="javascript:unhide('zalozbaS', 'zalozbaI')" class="button">Izberi iz seznama</a>
    					<a href="javascript:unhide('zalozbaI', 'zalozbaS')" class="button">Dodaj novo</a><br /> <br /> 
						<div id="zalozbaI"  class="hidden">
							<input type="text" name="zalozbaInput" class="textbox" />
						</div>
						<div id="zalozbaS" >
							<select name="zalozbaSelect">
								<option value="nic"> ----- </option>
							  	<c:forEach var="zal" items="${zalozbe}" >
									<option value='<c:out value="${zal.id}" />' ${zal.id == zalozba.id ? 'selected' : ''}><c:out value="${zal.naziv}" /></option>
								</c:forEach>
							</select>
						</div>
						
						<br /> <br /> 
						<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/> 
						<input type="submit" value="Potrdi" name="submit" class="button" />

					</form>
				</div>
			</div>
		</div>
	</body>
</html>