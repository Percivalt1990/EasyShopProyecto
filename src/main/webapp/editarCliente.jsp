<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Cliente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleeditarCliente.css">
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
        <h2>Actualizar Cliente</h2>
        <div class="formulario">
            <!-- Mostrar errores, si existen -->
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <!-- El formulario envía los datos al servlet -->
            <form action="${pageContext.request.contextPath}/ClientesServlet" method="POST">
                <h2>Editar cliente</h2>
                <input type="hidden" name="action" value="editar">
                <input type="hidden" name="id" value="${cliente.id}">

                <label>Nombre del cliente:</label>
                <input type="text" name="nombre" required value="${cliente.nombre}">

                <label>N° de Documento:</label>
                <input type="text" name="documento" required value="${cliente.documento}">

                <label>Tipo de Documento:</label>
                <input list="tipo_documento" id="tipoDocumento" name="tipoDocumento" required value="${cliente.tipoDocumento}">
                <datalist id="tipo_documento">
                    <option value="Cedula de Ciudadania">
                    <option value="Cedula de Extranjeria">
                </datalist>

                <label>Teléfono:</label>
                <input type="text" name="telefono" required value="${cliente.telefono}">

                <label>Dirección:</label>
                <input type="text" name="direccion" required value="${cliente.direccion}">

                <label>Email:</label>
                <input type="email" name="email" required value="${cliente.email}">

                <!-- Botones de Gestión -->
                <div class="btn-container">
                    <button type="submit" class="btn Guardar">Guardar</button>
                    <button type="reset" class="btn Limpiar">Limpiar</button>
                    <button type="button" onclick="window.location.href='clientes.jsp'" class="btn Regresar">Regresar</button>  
                </div>        
            </form>
        </div>
    </div>
</body>
</html>
