<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Facturas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylefacturas.css">
</head>
<body>
    <!-- barra lateral -->
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
    
        <div class="modulo"></div>     
        <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
        <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
    </div>
    <!-- contenido principal --> 
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Facturas</h2>
        <h2>Control de Facturas</h2>
        <div class="Tablafacturas">
            <div class="celda-busqueda">
                <form action="${pageContext.request.contextPath}/FacturasServlet" method="GET">
                    <input type="text" name="search" class="search-input" placeholder="Buscar..." value="${param.search}">
                    <button type="submit" class="btn Buscar">Buscar</button>
                </form>
            </div>
            <div class="contenedor">
                <table id="tablafacturas">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>IdCliente</th>
                            <th>IdUsuario</th>
                            <th>Forma de pago</th>
                            <th>Fecha</th>
                            <th>Total</th>
                            <th>Factura</th>
                        </tr>
                    </thead>
                   <tbody id="datosTabla">
                        <c:forEach var="factura" items="${facturas}">
                            <tr>
                                <td>${factura.id}</td>
                                <td>${factura.clienteId}</td>
                                <td>${factura.usuarioId}</td>
                                <td>${factura.formaPago}</td>
                                <td>${factura.fecha}</td>
                                <td>${factura.total}</td>
                                <td><a href="${factura.pdfUrl}" target="_blank">Pdf</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table> 
            </div>
        </div>
    </div>
</body>
</html>

