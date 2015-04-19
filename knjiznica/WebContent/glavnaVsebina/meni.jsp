<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div
	class="col-lg-6 col-md-6 col-sm-6 col-xs-12 black-bg left-container">
	<!-- 	<h1 class="logo-left hidden-xs margin-bottom-60">Knjižnica</h1> -->

	<div class="tm-left-inner-container">

		<ul class="nav nav-stacked templatemo-nav">
			</br>
			</br>
			<li><img class="slika"
				src="${pageContext.request.contextPath}/images/book_stack.png"></li>
			</br>
			</br>
			</br>
			<p>${pageContext.request.contextPath}</p>
			<li><a href="${pageContext.request.contextPath}/glavnaVsebina/Domov.jsp" class="active"><i
					class="fa fa-home fa-medium"></i>Domov</a></li>
			<li><a href="${pageContext.request.contextPath}/glavnaVsebina/DodajOsebo.jsp"><i
					class="fa fa-shopping-cart fa-medium"></i>Dodajanje uporabnikov</a></li>
			<li><a href="${pageContext.request.contextPath}/glavnaVsebina/Osebe.jsp"><i
					class="fa fa-shopping-cart fa-medium"></i>Pregled uporabnikov</a></li>


		</ul>
	</div>
</div>