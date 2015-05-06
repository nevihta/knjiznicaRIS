<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div
	class="col-lg-6 col-md-6 col-sm-6 col-xs-12 black-bg left-container">
	<!-- 	<h1 class="logo-left hidden-xs margin-bottom-60">Knjižnica</h1> -->

	<div class="tm-left-inner-container">
		<br/>
		<br/>
		<img class="slika" src="${pageContext.request.contextPath}/images/book_stack.png"/>
		<br/>
		<br/>
		<br/>
		<ul class="nav nav-stacked templatemo-nav">
				
			<li><a  href="${pageContext.request.contextPath}/OsebaServlet?metoda=domov"   <c:if test= "${meni eq 'domov'}"> class="active" </c:if>>
				<i	class="fa fa-home fa-medium"> </i>Domov</a></li>
			<li><a href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiVse&filter=vse"  <c:if test= "${meni eq 'osebe'}"> class="active" </c:if>>
				<i class="fa fa-shopping-cart fa-medium"></i>Uporabniki</a></li>
			<li><a href="${pageContext.request.contextPath}/GradivoServlet?metoda=pridobiVse"  <c:if test= "${meni eq 'gradivo'}"> class="active" </c:if>><i
					class="fa fa-shopping-cart fa-medium"></i>Gradivo</a></li>
			<li><a href="${pageContext.request.contextPath}/StoritevServlet?metoda=pridobiVseAktualneIzposoje"  <c:if test= "${meni eq 'izposoja'}"> class="active" </c:if>><i
					class="fa fa-shopping-cart fa-medium"></i>Izposoja</a></li>
			<li><a href="${pageContext.request.contextPath}/CrnaListaServlet?metoda=pridobiVse&filter=akt"  <c:if test= "${meni eq 'lista'}"> class="active" </c:if>><i
					class="fa fa-shopping-cart fa-medium"></i>Črna lista</a></li>

		</ul>
		<div id="meni_prijava">
			<c:if test="${Prijava==true}" >
				<a class="odjava" href="${pageContext.request.contextPath}/OsebaServlet?metoda=odjava">  Odjava</a> 
			</c:if>
			<c:if test="${(sessionScope.Prijava==false)||(sessionScope.Prijava==null)}">
				<a class="odjava" href="${pageContext.request.contextPath}/OsebaServlet?metoda=pridobiPrijavo">  Prijava</a> 
			</c:if> 
		</div>
		
	</div>
</div>