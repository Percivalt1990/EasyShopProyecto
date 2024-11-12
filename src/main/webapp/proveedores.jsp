<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Proveedores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleproveedores.css">
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

        <div class="modulo"></div>     
        <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
        <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
    </div>    
    
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h1>Proveedores</h1>

        <h2>Gestión de proveedores</h2>

        <div class="contenedor">
            <div class="celda-busqueda">
                <form action="${pageContext.request.contextPath}/ProveedoresServlet" method="GET">
                    <input type="text" name="search" class="search-input" placeholder="Buscar..." value="${param.search}">
                    <button type="submit" class="btn buscar">Buscar</button>
                </form>
            </div>
            <div class="btn-contenedor">
                <button onclick="window.location.href='registroProveedores.jsp'" class="btn crear">Nuevo</button>
            </div>
            <table id="tablaProveedores">
                <thead>
                    <tr> 
                        <th>ID</th> 
                        <th>Proveedor</th> 
                        <th>Número telefónico</th> 
                        <th>Dirección</th> 
                        <th>Email</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody id="productTableBody">
                    <!-- Recorre la lista de proveedores -->
                    <c:forEach var="proveedor" items="${proveedoresFiltrados}">
                        <tr>
                            <td>${proveedor.id}</td>
                            <td>${proveedor.nombre}</td>
                            <td>${proveedor.numeroTelefonico}</td>
                            <td>${proveedor.direccion}</td>
                            <td>${proveedor.email}</td>
                            <td>
                                <!-- Formulario para eliminar el proveedor -->
                                <form action="${pageContext.request.contextPath}/ProveedoresServlet" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="eliminar">
                                    <input type="hidden" name="id" value="${proveedor.id}">
                                    <button type="submit" class="btn Borrar">Borrar</button>
                                </form>
                                <!-- Formulario para editar el proveedor -->
                                <form action="${pageContext.request.contextPath}/ProveedoresServlet" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="cargar">
                                    <input type="hidden" name="id" value="${proveedor.id}">
                                    <button type="submit" class="btn Modificar">Editar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
