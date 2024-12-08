package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Clientes;
import com.mycompany.easyshopproyecto.logica.ProductosVenta;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NuevaVentaDAOTest {

    private Connection connection;
    private NuevaVentaDAO nuevaVentaDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        nuevaVentaDAO = new NuevaVentaDAO();
    }

    @Test
    @DisplayName("Test listarProductos")
    void testListarProductos() {
        List<ProductosVenta> productos = nuevaVentaDAO.listarProductos();
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto en la lista.");
    }

    @Test
    @DisplayName("Test buscarProductos")
    void testBuscarProductos() {
        String searchQuery = "quesito"; //Se debe ajustar segun a la base de datos
        List<ProductosVenta> productos = nuevaVentaDAO.buscarProductos(searchQuery);
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto que coincida con la busqueda.");
    }

    @Test
    @DisplayName("Test registrarFactura")
    void testRegistrarFactura() {
        int clienteId = 3; //Se debe ajustar segun a la base de datos
        int usuarioId = 6; //Se debe ajustar segun a la base de datos
        String formaPago = "Efectivo";
        long totalVenta = 5000;
        String pdfUrl = "factura de prueba.pdf";

        boolean result = nuevaVentaDAO.registrarFactura(clienteId, usuarioId, formaPago, totalVenta, pdfUrl);
        assertTrue(result, "El registro de la factura deberia ser exitoso.");
    }

    @Test
    @DisplayName("Test actualizarInventario")
    void testActualizarInventario() {
        int idProducto = 1; //Se debe ajustar segun a la base de datos
        int cantidadVendida = 5;

        boolean result = nuevaVentaDAO.actualizarInventario(idProducto, cantidadVendida);
        assertTrue(result, "La actualizacion del inventario deberia ser exitosa.");
    }

    @Test
    @DisplayName("Test obtenerProductoPorId")
    void testObtenerProductoPorId() {
        int idProducto = 1; //Se debe ajustar segun a la base de datos
        ProductosVenta producto = nuevaVentaDAO.obtenerProductoPorId(idProducto);

        assertNotNull(producto, "El producto no debe ser nulo.");
        assertEquals(idProducto, producto.getId(), "El ID del producto debe coincidir.");
    }

    @Test
    @DisplayName("Test buscarClientePorDocumento")
    void testBuscarClientePorDocumento() {
        String documento = "1234567"; //Se debe ajustar segun a la base de datos
        Clientes cliente = nuevaVentaDAO.buscarClientePorDocumento(documento);

        assertNotNull(cliente, "El cliente no debe ser nulo.");
        assertEquals(documento, cliente.getDocumento(), "El documento del cliente debe coincidir.");
    }

    @AfterAll
    void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
