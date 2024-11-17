<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Balance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylebalance.css">
</head>
<body>
    <!-- Barra lateral -->
    <div class="sidebar">
        <div class="header">
            <div class="logo">
                <img src="${pageContext.request.contextPath}/img/tituloPOS.png" alt="Logo">
            </div>
            <h1>Menú</h1>
        </div>
        <div class="usuario">
            <img src="${pageContext.request.contextPath}/img/icon-user2.jpg" alt="">
            <div class="info-usuario">
                <span class="nombre">${sessionScope.usuario.nombre}</span>
                <span class="email">${sessionScope.usuario.email}</span>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/index.jsp" class="enlace">Inicio</a>
        <a href="${pageContext.request.contextPath}/clientes.jsp" class="enlace">Clientes</a>
        <a href="${pageContext.request.contextPath}/usuarios.jsp" class="enlace">Usuarios</a>
        <a href="${pageContext.request.contextPath}/ventas.jsp" class="enlace">Ventas</a>
        <a href="${pageContext.request.contextPath}/inventario.jsp" class="enlace">Inventarios</a>
        <a href="${pageContext.request.contextPath}/BalanceServlet" class="enlace">Balance</a> 
        <a href="${pageContext.request.contextPath}/facturas.jsp" class="enlace">Facturas</a>
        <div class="modulo">
            <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y Condiciones</a></div>
            <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
        </div>
    </div>

    <!-- Contenido principal -->
    <div class="contenido">
        <div class="anuncios">Anuncios</div>
        <h2>Balance</h2>
        <div class="ventanas">
            <div class="seccion">
                <h3 class="ingresos">Ingresos del Dia</h3>
                <div class="valor">
                    $<c:out value="${totalIngresos}" />
                </div>
            </div>
            <div class="seccion">
                <h3 class="egresos">Egresos del Dia</h3>
                <div class="valor">
                    $<c:out value="${totalEgresos}" />
                </div>
            </div>
            <div class="seccion">
                <h3 class="balance">Balance del Dia</h3>
                <div class="valor">
                    $<c:out value="${balance}" />
                </div>
            </div>
        </div>
    </div>
</body>
</html>
