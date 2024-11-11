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
    <div class="contenido">
        <h2>Actualizar Usuario</h2>
        <div class="formulario">
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/UsuariosServlet" method="POST">
                <!-- Cambia action a UsuariosServlet y asegúrate de que "editar" es el valor de action -->
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
