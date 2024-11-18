<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleusuario.css">
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
    <!-- Contenido principal-->
    <div class="contenido">
        <div class="anuncios">Anuncios</div>
        <h2>Usuarios</h2>
        <h2>Gestión de Usuarios</h2>
        
        <!-- Formulario de busqueda -->
        <form action="${pageContext.request.contextPath}/UsuariosServlet" method="GET">
            <input type="hidden" name="action" value="buscar">
            <input type="text" name="search" class="search-input" placeholder="Buscar..." value="${param.search}">
            <button type="submit" class="btn Buscar">Buscar</button>
        </form>
            
        <div class="btn-container">
            <!-- Boton para crear un nuevo usuario -->
            <button onclick="window.location.href='registro.jsp'" class="btn Crear">Nuevo</button>
        </div> 

        <!-- Tabla de usuarios -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre de Usuario</th>
                    <th>Tipo de Documento</th>
                    <th>N° de Documento</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Contraseña</th>
                    <th>Confirmación</th>
                    <th>Permisos</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody id="tablagestiondeusuario">
                <c:forEach var="usuario" items="${usuariosFiltrados}">
                    <tr>
                        <td>${usuario.id}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.tipoDocumento}</td>
                        <td>${usuario.numeroDocumento}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.telefono}</td>
                        <td>${usuario.password}</td>
                        <td>${usuario.confirmacion}</td>
                        <td>${usuario.permisos}</td>
                        <td>
                            <!-- Formulario para eliminar el usuario -->
                            <form action="${pageContext.request.contextPath}/UsuariosServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="eliminar">
                                <input type="hidden" name="id" value="${usuario.id}">
                                <button type="submit" class="btn Borrar">Borrar</button>
                            </form>
                            <!-- Formulario para editar el usuario -->
                            <form action="${pageContext.request.contextPath}/UsuariosServlet" method="GET" style="display:inline;">
                                <input type="hidden" name="action" value="cargar">
                                <input type="hidden" name="id" value="${usuario.id}">
                                <button type="submit" class="btn Modificar">Editar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
