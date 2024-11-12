<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleindex.css">
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
        <a href="${pageContext.request.contextPath}/balance.jsp" class="enlace">Balance</a>
        <a href="${pageContext.request.contextPath}/facturas.jsp" class="enlace">Facturas</a>

        <div class="modulo">   
            <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
            <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
        </div>  
    </div>

    <div class="contenido-principal">
        <div class="anuncios">
            <img src="${pageContex.request.contextPath}/img/promo-header.jpg" alt="">
        </div>
        <h2>Bienvenido</h2>
        <h2>Accesos Rápidos</h2>
        
        <div class="tablero">
            <div class="seccion">
                <h3>Ingresar Cliente</h3>
                <p>Acceso rápido de ingreso de clientes de tu tienda.</p>
                <a href="${pageContext.request.contextPath}/clientes.jsp" class="button">Ver más</a>
            </div>

            <div class="seccion">
                <h3>Nueva Venta</h3>
                <p>Acceso para venta rápida.</p>
                <a href="${pageContext.request.contextPath}/nuevaVenta.jsp" class="button">Ver más</a>
            </div>

            <!-- aquí se pueden añadir más tarjetas como sea necesario -->
        </div>
    </div>
</body>
</html>


