package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.NuevaCompraDAO;
import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NuevaCompraServletTest {

    private NuevaCompraServlet nuevaCompraServlet;
    private NuevaCompraDAO nuevaCompraDAO;

    @BeforeEach
    void setUp() throws Exception {
        Connection connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");

        nuevaCompraDAO = new NuevaCompraDAO();
        nuevaCompraServlet = new NuevaCompraServlet();
        nuevaCompraServlet.init();

        // Insertar datos de prueba en la base de datos
        nuevaCompraDAO.actualizarInventario(1, -nuevaCompraDAO.obtenerProductoPorId(1).getCantidad()); // Limpia inventario
        ProductosCompra producto = new ProductosCompra(1, "Proveedor Test", "Producto Test", 1000, 50);
        nuevaCompraDAO.actualizarInventario(1, 50);
    }

    @Test
    @DisplayName("Test de listado de productos")
    void testListarProductos() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Simular llamada GET
        nuevaCompraServlet.doGet(request, response);

        // Verificar que se listan productos
        assertNotNull(request.getAttribute("listaProductosCompra"), "Debe haber una lista de productos en el request.");
    }

    @Test
    @DisplayName("Test de añadir producto al carrito")
    void testAñadirProductoAlCarrito() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Simular parámetros para añadir al carrito
        request.setParameter("action", "add");
        request.setParameter("idProducto", "1");
        request.setParameter("cantidad", "10");

        HttpSession session = request.getSession();
        nuevaCompraServlet.doPost(request, response);

        // Verificar que el producto se añadió al carrito
        assertNotNull(session.getAttribute("carritoCompras"), "El carrito de compras no debe ser nulo.");
        assertEquals(1, ((List<ProductosCompra>) session.getAttribute("carritoCompras")).size(), "Debe haber un producto en el carrito.");
    }

    @Test
    @DisplayName("Test de eliminar producto del carrito")
    void testEliminarProductoDelCarrito() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        HttpSession session = request.getSession();
        List<ProductosCompra> carrito = new ArrayList<>();
        carrito.add(new ProductosCompra(1, "Proveedor Test", "Producto Test", 1000, 10));
        session.setAttribute("carritoCompras", carrito);

        // Simular parámetros para eliminar del carrito
        request.setParameter("action", "delete");
        request.setParameter("idProducto", "1");

        nuevaCompraServlet.doPost(request, response);

        // Verificar que el producto se eliminó del carrito
        List<ProductosCompra> carritoActualizado = (List<ProductosCompra>) session.getAttribute("carritoCompras");
        assertTrue(carritoActualizado.isEmpty(), "El carrito debe estar vacío después de eliminar el producto.");
    }

    @Test
    @DisplayName("Test de generar factura")
    void testGenerarFactura() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        HttpSession session = request.getSession();
        List<ProductosCompra> carrito = new ArrayList<>();
        carrito.add(new ProductosCompra(1, "Proveedor Test", "Producto Test", 1000, 10));
        session.setAttribute("carritoCompras", carrito);

        // Simular parámetros para generar factura
        request.setParameter("action", "generateInvoice");

        nuevaCompraServlet.doPost(request, response);

        // Verificar que la factura se generó
        assertNull(session.getAttribute("carritoCompras"), "El carrito debe estar vacío después de generar la factura.");
    }

    @Test
    @DisplayName("Test de cancelar compra")
    void testCancelarCompra() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        HttpSession session = request.getSession();
        List<ProductosCompra> carrito = new ArrayList<>();
        carrito.add(new ProductosCompra(1, "Proveedor Test", "Producto Test", 1000, 10));
        session.setAttribute("carritoCompras", carrito);

        // Simular parámetros para cancelar la compra
        request.setParameter("action", "cancel");

        nuevaCompraServlet.doPost(request, response);

        // Verificar que el carrito se haya limpiado
        assertNull(session.getAttribute("carritoCompras"), "El carrito debe estar vacío después de cancelar la compra.");
    }
}
