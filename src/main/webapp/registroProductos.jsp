<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Productos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleregistroProductos.css">
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
        <h2>Registrar Producto</h2>
        <div class="formulario">
            <form action="${pageContext.request.contextPath}/ProductosServlet" method="POST">
                <h2>Nuevo Producto</h2>

                <input type="hidden" name="action" value="crear">

                <label>Categoría:</label>
                <input type="text" name="categoria" required>

                <label>Nombre del producto:</label>
                <input type="text" name="nombre" required>

                <label>Cantidad:</label>
                <input type="number" name="cantidad" required>

                <label>Precio:</label>
                <input type="number" name="precio" required>

                <label>Descripción:</label>
                <input type="text" name="descripcion" required>

                <label>ID Proveedor:</label>
                <input type="text" name="idProveedor" required>

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
