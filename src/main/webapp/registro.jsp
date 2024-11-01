<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formato de registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleregistro.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
    <div class="container">
        <div class="header-form">
            <h1>REGISTRO</h1>            
        </div>
        <div class="form-data">
            <form action="${pageContext.request.contextPath}/RegistroServlet" method="POST">
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
                    <input type="tel" id="numeroDocumento" name="numeroDocumento" placeholder="Numero de documento" required>
                </div>
                <div class="form-item">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="form-item">
                    <input type="tel" id="telefono" name="telefono" placeholder="Numero de Telefono" required>
                </div>
                <div class="form-item">
                    <input type="password" id="password" name="password" placeholder="Contraseña" required>
                </div>
                <div class="form-item">
                    <input type="password" id="confirmacion" name="confirmacion" placeholder="Confirmar Contraseña" required>
                </div>
                <div class="checkbox">
                    <input type="checkbox" id="permisos" name="permisos">
                    <label for="permisos">Rol de Administrador</label>
                </div>      
                <button type="submit" id="button">Registrar</button>
            </form>
        </div>    
    </div>
</body>
</html>





