<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Producto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleeditarProducto.css">
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

        <div class="modulo"></div>
        <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div> 
        <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
    </div>

    <div class="contenido"> 
        <div class="anuncios">Anuncios</div>
        <h2>Editar Producto</h2>

        <div class="formulario">
            <!-- Mostrar errores, si existen -->
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <!-- El formulario envía los datos al servlet -->
            <form action="${pageContext.request.contextPath}/ProductosServlet" method="POST">
                <input type="hidden" name="action" value="editar">
                <input type="hidden" name="id" value="${producto.id}">

                <label>Categoria:</label>
                <input type="text" name="categoria" required value="${producto.categoria}">

                <label>Nombre del producto:</label>
                <input type="text" name="nombre" required value="${producto.nombre}">

                <label>Cantidad:</label>
                <input type="number" name="cantidad" required value="${producto.cantidad}">

                <label>Precio:</label>
                <input type="number" name="precio" required value="${producto.precio}">

                <label>Descripcion:</label>
                <input type="text" name="descripcion" required value="${producto.descripcion}">

                <label>ID Proveedor:</label>
                <input type="text" name="idProveedor" required value="${producto.idProveedor}">

                <!-- Botones de Gestión -->
                <div class="btn-container">
                    <button type="submit" class="btn Guardar">Guardar</button>
                    <button type="reset" class="btn Limpiar">Limpiar</button>
                    <button type="button" onclick="window.location.href='productos.jsp'" class="btn Regresar">Regresar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
