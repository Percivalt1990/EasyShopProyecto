<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Balance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylebalance.css">
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
        <a href="${pageContext.request.contextPath}/balance.jsp" class="enlace">Balance</a>
        <a href="${pageContext.request.contextPath}/facturas.jsp" class="enlace">Facturas</a>
    
        <div class="modulo">    
            <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y Condiciones</a></div> 
            <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
        </div> 
    </div>
    
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Balance</h2>
        <div class="ventanas">
            <!-- Sección de ingresos -->
            <div class="seccion">
                <h3>Vista general de ingresos del día</h3>
                <div class="vista-ingresos">
                    <div class="titulo">Ingresos</div>
                    <div class="valor" id="vista-ingresos">
                        <div class="valor" id="vista-ingresos">
                            <!-- Mostrar los ingresos traídos del servlet -->
                            ${totalIngresos != null ? String.format("$%,d", totalIngresos) : "$0"}
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Seccion de egresos -->
            <div class="seccion">
                <h3>Vista general de egresos del día</h3>
                <div class="vista-egresos">
                    <div class="titulo">Egresos</div>
                    <div class="valor" id="vista-egresos">
                        <!-- Mostrar los egresos traidos del servlet -->
                        ${totalEgresos != null ? String.format("$%,d", totalEgresos) : "$0"}
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>