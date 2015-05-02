<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
	<head>
		<!-- http://www.templatemo.com/preview/templatemo_419_black_white -->
		<title>Gradivo</title>
		<%@ include file="head.jsp"%>
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
	
						<h4>Naslov:</h4>
						<input type="text" name="naslov" class="textbox" value='<c:out value="${gradivo.naslov}" />' required/>
	
						<h4>Original naslov:</h4>
						<input type="text" name="originalNaslov" class="textbox" value='<c:out value="${gradivo.originalNaslov}" />' required/>

						<h4>Avtor:</h4>
						<input type="text" name="avtorjiImeInput" class="textbox"  /> 
						<input type="text" name="avtorjiPriimekInput" class="textbox"  /> 
						<select name="avtorjiSelect">
							<c:forEach var="avt" items="${vsiAvtorji}" >
								<option value='<c:out value="${avt.id}" />'><c:out value="${avt.ime}" /><c:out value="${avt.priimek}" /></option>
							</c:forEach>
						</select>
						
						<h4>Leto izida:</h4>
						<input type="number" name="leto" class="textbox" value='<c:out value="${gradivo.letoIzida}" />' required/>
	
						<h4>ISBN:</h4>
						<input type="text" name="isbn" class="textbox" value='<c:out value="${gradivo.ISBN}" />' required/>
	
						<h4>Opis:</h4>
						<input type="text" name="opis" class="textbox" value='<c:out value="${gradivo.opis}" />' required/>
	
						<h4>Jezik:</h4>
						<input type="text" name="jezik" class="textbox" value='<c:out value="${gradivo.jezik}" />' required/>
	
						<h4>Področje:</h4>
						<input type="text" name="podrocjeInput" class="textbox" /> 
						<select name="podrocjeSelect">
						  	<c:forEach var="podr" items="${podrocja}" >
								<option value='<c:out value="${podr.id}" />'><c:out value="${podr.naziv}" /></option>
							</c:forEach>
						</select> 

						<h4>Vrsta gradiva: </h4>
						<input type="text" name="vrstaInput" class="textbox" />
						<select name="vrstaSelect">
						  	<c:forEach var="vrst" items="${vrsteGradiva}" >
								<option value='<c:out value="${vrst.id}" />'><c:out value="${vrst.naziv}" /></option>
							</c:forEach>
						</select>
						
						<h4>Založba:</h4>
						<input type="text" name="zalozbaInput" class="textbox" />
						<select name="zalozbaSelect">
						  	<c:forEach var="zal" items="${zalozbe}" >
								<option value='<c:out value="${zal.id}" />'><c:out value="${zal.naziv}" /></option>
							</c:forEach>
						</select>
						
						<br /> 
						<input type="button" value="Prekliči" onClick="history.go(-1);return true;" class="button"/> 
						<input type="submit" value="Potrdi" name="submit" class="button" />

					</form>
				</div>
			</div>
		</div>
	</body>
</html>