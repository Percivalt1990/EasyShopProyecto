package com.mycompany.easyshopproyecto.servlets;

import com.mycompany.easyshopproyecto.dao.ConexionDB;
import com.mycompany.easyshopproyecto.dao.NuevaVentaDAO;
import com.mycompany.easyshopproyecto.logica.ProductosVenta;
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

class NuevaVentaServletTest {

    private NuevaVentaServlet nuevaVentaServlet;
    private NuevaVentaDAO nuevaVentaDAO;

    @BeforeEach
    void setUp() throws Exception {
        // Configurar la conexión a la base de datos
        Connection connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");

        nuevaVentaDAO = new NuevaVentaDAO();
        nuevaVentaServlet = new NuevaVentaServlet();
        nuevaVentaServlet.init();

        // Limpiar registros previos de prueba y configurar datos de prueba
        ProductosVenta producto = new ProductosVenta(1, "Producto Test", 15000, 30);
        nuevaVentaDAO.obtenerProductoPorId(producto.getId()); // Limpia si existe el ID
        nuevaVentaDAO.actualizarInventario(producto.getId(), +1);
    }

    @Test
    @DisplayName("Test: Agregar producto al carrito")
    void testAgregarProductoAlCarrito() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Configurar parámetros del formulario
        request.setParameter("action", "add");
        request.setParameter("idProducto", "1");
        request.setParameter("cantidad", "5");

        // Simular la sesión
        HttpSession session = request.getSession(true);
        List<ProductosVenta> carrito = new ArrayList<>();
        session.setAttribute("carritoVentas", carrito);

        // Ejecutar el servlet
        nuevaVentaServlet.doPost(request, response);

        // Verificar el carrito
        carrito = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        assertNotNull(carrito, "El carrito de compras no debe ser nulo.");
        assertFalse(carrito.isEmpty(), "El carrito debe contener productos.");
        assertEquals(1, carrito.size(), "El carrito debe contener un producto.");
    }

    @Test
    @DisplayName("Test: Generar factura")
    void testGenerarFactura() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Configurar el carrito en la sesión
        HttpSession session = request.getSession(true);
        List<ProductosVenta> carrito = new ArrayList<>();
        carrito.add(new ProductosVenta(1, "Producto Test", 15000, 3));
        session.setAttribute("carritoVentas", carrito);

        // Configurar usuario y método de pago
        session.setAttribute("usuarioId", 1);
        session.setAttribute("nombreUsuario", "Usuario Test");
        request.setParameter("action", "generateInvoice");
        request.setParameter("metodoPago", "Tarjeta");

        // Ejecutar el servlet
        nuevaVentaServlet.doPost(request, response);

        // Verificar que el tipo de contenido sea PDF
        assertEquals("application/pdf", response.getContentType(), "El tipo de contenido debe ser 'application/pdf'.");
    }

    @Test
    @DisplayName("Test: Cancelar venta")
    void testCancelarVenta() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Configurar el carrito en la sesión
        HttpSession session = request.getSession(true);
        List<ProductosVenta> carrito = new ArrayList<>();
        carrito.add(new ProductosVenta(1, "Producto Test", 15000, 3));
        session.setAttribute("carritoVentas", carrito);

        // Configurar acción
        request.setParameter("action", "cancel");

        // Ejecutar el servlet
        nuevaVentaServlet.doPost(request, response);

        // Verificar que el carrito esté vacío
        carrito = (List<ProductosVenta>) session.getAttribute("carritoVentas");
        assertNull(carrito, "El carrito debe estar vacío después de cancelar la venta.");
    }
}
