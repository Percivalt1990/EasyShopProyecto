<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventarios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleinventario.css">
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
        <a href="${pageContext.request.contextPath}/index.jsp" class="enlace">Inicio</a>
        <a href="${pageContext.request.contextPath}/clientes.jsp" class="enlace">Clientes</a>
        <a href="${pageContext.request.contextPath}/usuarios.jsp" class="enlace">Usuarios</a>
        <a href="${pageContext.request.contextPath}/ventas.jsp" class="enlace">Ventas</a>
        <a href="${pageContext.request.contextPath}/inventario.jsp" class="enlace">Inventarios</a>
        <a href="${pageContext.request.contextPath}/balance.jsp" class="enlace">Balance</a>
        <a href="${pageContext.request.contextPath}/facturas.jsp" class="enlace">Facturas</a>
    
        <div class="modulo"></div>     
        <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
        <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
    </div>    
    
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Inventario</h2>
        <div class="tablero">
            <div class="boton-acceso">
                <h3>Sección Productos</h3>
                <p>Gestión de productos</p>
                <a href="${pageContext.request.contextPath}/productos.jsp" class="button">Productos</a>
            </div>
            <div class="boton-acceso">
                <h3>Sección Proveedores</h3>
                <p>Gestión de proveedores</p>
                <a href="${pageContext.request.contextPath}/proveedores.jsp" class="button">Proveedores</a>
            </div>

            <!-- Aquí se puede continuar con el anexo de botones -->
        </div>
    </div>
</body>
</html>


