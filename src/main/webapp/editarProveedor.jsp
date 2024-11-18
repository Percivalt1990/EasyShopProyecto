<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Proveedor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleeditarProveedor.css">
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

        <div class="modulo"></div>     
        <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
        <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
    </div>    
    <!-- contenido principal --> 
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Editar Proveedor</h2>

        <div class="formulario">
            <!-- Mostrar errores, si existen -->
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <!-- Elemento formulario que envia los datos al servlet mediante el metodo post -->
            <form action="${pageContext.request.contextPath}/ProveedoresServlet" method="POST">
                <input type="hidden" name="action" value="editar">
                <input type="hidden" name="id" value="${proveedor.id}"> 
                
                <label>Proveedor:</label>
                <input type="text" name="nombre" required value="${proveedor.nombre}">
                
                <label>N° Telefonico:</label>
                <input type="text" name="telefono" required value="${proveedor.numeroTelefonico}">
                
                <label>Direccion:</label>
                <input type="text" name="direccion" required value="${proveedor.direccion}">
                
                <label>Email:</label>
                <input type="email" name="email" required value="${proveedor.email}">
                
                <!-- Botones  -->
                <div class="btn-container">
                    <button type="submit" class="btn Guardar">Guardar</button>
                    <button type="reset" class="btn Limpiar">Limpiar</button>
                    <button onclick="window.location.href='proveedores.jsp'" type="button" class="btn Regresar">Regresar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
