<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Usuario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleeditarUsuario.css">
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
        <!-- Mostrar el usuario logeado -->
        <div class="usuario-logeado">
            <p>Bienvenido, ${usuario != null ? usuario.nombre : ''}</p>
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
    
    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Actualizar Usuario</h2>
        <div class="formulario">
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/UsuariosServlet" method="POST">
                <input type="hidden" name="action" value="editar">
                <input type="hidden" name="id" value="${usuario != null ? usuario.id : ''}">

                <label>Nombre y Apellido:</label>
                <input type="text" name="nombre" required value="${usuario != null ? usuario.nombre : ''}">

                <label>Tipo de Documento:</label>
                <input list="tipo_documento" id="tipoDocumento" name="tipoDocumento" required value="${usuario != null ? usuario.tipoDocumento : ''}">
                <datalist id="tipo_documento">
                    <option value="Cedula de Ciudadania">
                    <option value="Cedula de Extranjeria">
                </datalist>

                <label>Número de Documento:</label>
                <input type="text" name="numeroDocumento" required value="${usuario != null ? usuario.numeroDocumento : ''}">

                <label>Email:</label>
                <input type="text" name="email" required value="${usuario != null ? usuario.email : ''}">

                <label>Teléfono:</label>
                <input type="text" name="telefono" required value="${usuario != null ? usuario.telefono : ''}">

                <label>Contraseña:</label>
                <input type="password" name="password" required value="${usuario != null ? usuario.password : ''}">

                <label>Confirmar Contraseña:</label>
                <input type="password" name="confirmacion" required value="${usuario != null ? usuario.confirmacion : ''}">

                <div class="checkbox">
                    <input type="checkbox" name="permisos" ${usuario != null && usuario.permisos ? 'checked' : ''}>
                    <label for="permisos">Rol de Administrador</label>
                </div>

                <div class="btn-container">
                    <button type="submit" class="btn Guardar">Guardar</button>
                    <button type="reset" class="btn Limpiar">Limpiar</button>
                    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/UsuariosServlet?action=listar'" class="btn Regresar">Regresar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>