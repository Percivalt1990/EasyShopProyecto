<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Página de Ingreso</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylelogin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="formulario">
        <h2>LOGIN</h2>
        
        <!-- Mostrar el mensaje de error si existe -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                <p style="color: orangered;">${errorMessage}</p>
            </div>
        </c:if>
        
        <!-- Formulario de login -->
        <form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
            <label for="numeroDocumento">Numero de Documento</label>
            <input type="text" id="numeroDocumento" name="numeroDocumento" placeholder="Numero de Documento" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <br/>
            <div name="enlace-contraseña"><a href="${pageContext.request.contextPath}/ForgotPassword.jsp" target="_blank">Forgot your password?</a></div> 
            <br>
            <div name="ingresar">
                <button class="boton" id="boton" type="submit">Login</button>
            </div>
        </form>

    </div>    
        
    <div class="text" id="texto" name="texto">
        <p>New Here? <a href="${pageContext.request.contextPath}/registro.jsp" target="_blank">Sign in</a></p>
    </div>
</body>
</html>
