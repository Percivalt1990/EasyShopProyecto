<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Formato de registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleregistro.css">
    <!-- estilos adicionales-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="container">
        <div class="header-form">
            <h1>REGISTRO</h1>            
        </div>

        <!-- Mostrar mensajes de exito de registro o error -->
        <c:if test="${not empty mensajeExito}">
            <div class="success-message">
                <p style="color: green;">${mensajeExito}</p>
            </div>
        </c:if>
        <c:if test="${not empty mensajeError}">
            <div class="error-message">
                <p style="color: red;">${mensajeError}</p>
            </div>
        </c:if>

        <div class="form-data">
            <form action="${pageContext.request.contextPath}/UsuariosServlet" method="POST">
                <input type="hidden" name="action" value="crear">

                <div class="form-item-group">
                    <input type="text" id="nombre" name="nombre" placeholder="Nombre y Apellido" required>
                </div>  
                <div class="form-item-group">
                    <input list="tipo_documento" id="tipoDocumento" name="tipoDocumento" placeholder="Tipo de Documento" required>
                    <datalist id="tipo_documento">
                        <option value="Cedula de Ciudadania">
                        <option value="Cedula de Extranjeria">
                    </datalist>
                </div>
                <div class="form-item">
                    <input type="tel" id="numeroDocumento" name="numeroDocumento" placeholder="Número de documento" required>
                </div>
                <div class="form-item">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="form-item">
                    <input type="tel" id="telefono" name="telefono" placeholder="Número de Teléfono" required>
                </div>
                <div class="form-item">
                    <input type="password" id="password" name="password" placeholder="Contraseña" required>
                </div>
                <div class="form-item">
                    <input type="password" id="confirmacion" name="confirmacion" placeholder="Confirmar Contraseña" required>
                </div>
                <div class="checkbox">
                    <input type="checkbox" id="permisos" name="permisos" value="true">
                    <label for="permisos">Rol de Administrador</label>
                </div>      
                <button type="submit" id="button">Registrar</button>
            </form>
        </div>    
    </div>
</body>
</html>
