<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Venta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylenuevaVenta.css">
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
            <div><a href="${pageContext.request.contextPath}/Terminos.jsp" target="_blank">Términos y condiciones</a></div>
            <div class="cerrar-sesion"><a href="${pageContext.request.contextPath}/login.jsp" name="cerrar">Cerrar sesión</a></div>
        </div>
    </div>

    <!-- Contenido principal -->
    <div class="contenido">
        <div class="anuncios">Anuncios</div>
        <h2>Nueva Venta</h2>
        <div class="contenedor">
            <!-- Sección de productos disponibles -->
            <div class="secciones productos-disponibles">
                <h2>Productos Disponibles</h2>
                <!-- Formulario de búsqueda -->
                <form action="${pageContext.request.contextPath}/NuevaVentaServlet" method="GET" style="display:inline;">
                    <input type="text" name="search" placeholder="Buscar..." class="search-box" value="${param.search}">
                    <button type="submit" class="btn Buscar">Buscar</button>
                </form>
                <!-- Lista de productos -->
                <table class="lista-productos">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listaProductos}">
                            <c:forEach var="producto" items="${listaProductos}">
                            <tr>
                                <td>${producto.id}</td>
                                <td>${producto.nombre}</td>
                                <td>$${producto.precio}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/NuevaVentaServlet" method="POST" style="display:inline;">
                                        <input type="hidden" name="idProducto" value="${producto.id}">
                                        <input type="number" name="cantidad" value="1" min="1" required>
                                        <input type="hidden" name="action" value="add"> <!-- Input para la acción -->
                                        <button type="submit" class="btn Añadir">Añadir</button>
                                    </form>
                                </td>
                            </tr>
                            </c:forEach>
                        </c:if>
                            <c:if test="${empty listaProductos}">
                                <tr>
                                    <td colspan="5">No hay productos disponibles.</td>
                                </tr>
                            </c:if>    
                    </tbody>
                </table>
            </div>

            <!-- Sección de productos seleccionados -->
            <div class="secciones productos-seleccionados">
                <h2>Productos Seleccionados</h2>
                <!-- Label e input para ingresar el ID del cliente -->
                <div class="cliente-id">
                    <label for="idCliente">ID Cliente:</label>
                    <form action="${pageContext.request.contextPath}/NuevaVentaServlet" method="GET" style="display:inline;">
                        <input type="text" id="idCliente" name="idCliente" placeholder="Ingrese ID Cliente" required>
                        <button type="submit" class="btn Buscar">Buscar Cliente</button>
                    </form>
                </div>
                
                <div class="productos-seleccionados"> 
                    <!-- Tabla de productos seleccionados -->
                    <table class="carritoVentas">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Subtotal</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0"/>
                            <c:if test="${not empty carritoVentas}">
                            <c:forEach var="producto" items="${carritoVentas}">
                            <tr>
                                <td>${producto.id}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.cantidad}</td>
                                <td>$${producto.precio}</td>
                                <td>$${producto.cantidad * producto.precio}</td> <!-- Calcular subtotal -->
                                <td>
                                    <form action="${pageContext.request.contextPath}/NuevaVentaServlet" method="POST" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="idProducto" value="${producto.id}"> <!-- Corregido para usar el ID correcto -->
                                        <button type="submit" class="btn Borrar">Borrar</button>
                                    </form>
                                </td>
                            </tr>
                            <c:set var="total" value="${total +(producto.cantidad * producto.precio)}"/>
                            </c:forEach>
                            </c:if>
                            <c:if test="${empty carritoVentas}">
                                <tr>
                                    <td colspan="6">No hay productos en el carrito.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    </div>
                    <div class="total" id="totalAmount">
                        <p>Total: $<c:out value="${total}"/></p>
                        <!-- Aquí se debe calcular el total de la compra -->

                    </div>
                <div class="botones">
                    <!-- Formulario para boton generar venta -->
                    <form action="NuevaVentaServlet" method="POST">
                        <input type="hidden" name="action" value="generateInvoice">
                        <button type="submit" class="btn generar-venta">Generar Venta</button>
                    </form>

                    <!-- Formulario para boton cancelar la venta -->
                    <form action="${pageContext.request.contextPath}/NuevaVentaServlet" method="POST">
                        <input type="hidden" name="action" value="cancel"> <!-- Campo oculto para definir la acción "cancel" -->
                        <button type="submit" class="btn cancelar-venta">Cancelar Compra</button>
                    </form>
                </div>  
            </div>
        </div>
    </div>
</body>
</html>
