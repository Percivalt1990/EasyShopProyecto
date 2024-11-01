<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Restablecer Contraseña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleforgotpassword.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="formulario">
        <h2>Restablecer Contraseña</h2>

        <!-- Mostrar el mensaje de error si existe -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                <p style="color: orangered;">${errorMessage}</p>
            </div>
        </c:if>

        <!-- Formulario de restablecimiento de contraseña -->
        <form action="${pageContext.request.contextPath}/ResetPasswordServlet" method="POST">
            <label for="numeroDocumento">Numero de Documento</label>
            <input type="text" id="numeroDocumento" name="numeroDocumento" placeholder="Numero de Documento" required>

            <label for="newPassword">Nueva Contraseña</label>
            <input type="password" id="newPassword" name="newPassword" placeholder="Nueva Contraseña" required>

            <label for="confirmPassword">Confirmar Nueva Contraseña</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirmar Nueva Contraseña" required>
            
            <br/>
            <div name="reset">
                <button class="boton" id="boton" type="submit">Restablecer Contraseña</button>
            </div>
        </form>

    </div>    
</body>
</html>
