<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Knjižnica</title>
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
		<h1 class="templatemo-header"></h1>			
		<br/>			
		<table >
			<tr>
				<td><a href="${pageContext.request.contextPath}/AvtorServlet?metoda=pridobiVse">Avtorji</a>|</td>
				<td><a href="${pageContext.request.contextPath}/PodrocjeServlet?metoda=pridobiVse">Področja</a> |</td> 
				<td><a href="${pageContext.request.contextPath}/VrstaGradivaServlet?metoda=pridobiVse">Vrste gradiva</a> |</td> 
				<td><a href="${pageContext.request.contextPath}/ZalozbaServlet?metoda=pridobiVse">Založbe</a></td>
			</tr>
		</table>
		</div>
	</div>
</div>
</body>
</html>