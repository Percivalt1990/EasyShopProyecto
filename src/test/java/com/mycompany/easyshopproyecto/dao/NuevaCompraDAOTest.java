package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.ProductosCompra;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NuevaCompraDAOTest {

    private Connection connection;
    private NuevaCompraDAO nuevaCompraDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection(); 
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        nuevaCompraDAO = new NuevaCompraDAO();
    }

    @Test
    @DisplayName("Test listarProductosCompra")
    void testListarProductosCompra() {
        List<ProductosCompra> productos = nuevaCompraDAO.listarProductosCompra();
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto en la lista.");
    }

    @Test
    @DisplayName("Test registrarFactura")
    void testRegistrarFactura() {
        List<ProductosCompra> carrito = new ArrayList<>();
        carrito.add(new ProductosCompra(1, "Proveedor1", "Producto1", 100.0, 5));
        carrito.add(new ProductosCompra(2, "Proveedor2", "Producto2", 200.0, 3));

        boolean result = nuevaCompraDAO.registrarFactura(carrito, 1100L);
        assertTrue(result, "El registro de la factura debería ser exitoso.");
    }

    @Test
    @DisplayName("Test buscarProductosCompra")
    void testBuscarProductosCompra() {
        String searchQuery = "quesito"; //Se debe ajustar segun a la base de datos
        List<ProductosCompra> productos = nuevaCompraDAO.buscarProductosCompra(searchQuery);
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto que coincida con la búsqueda.");
    }

    @Test
    @DisplayName("Test actualizarInventario")
    void testActualizarInventario() {
        int idProducto = 1; //Se debe ajustar segun a la base de datos
        int cantidadComprada = 10;

        boolean result = nuevaCompraDAO.actualizarInventario(idProducto, cantidadComprada);
        assertTrue(result, "La actualización del inventario debería ser exitosa.");
    }

    @Test
    @DisplayName("Test obtenerProductoPorId")
    void testObtenerProductoPorId() {
        int idProducto = 1; //Se debe ajustar segun a la base de datos.
        ProductosCompra producto = nuevaCompraDAO.obtenerProductoPorId(idProducto);

        assertNotNull(producto, "El producto no debe ser nulo.");
        assertEquals(idProducto, producto.getId(), "El ID del producto debe coincidir.");
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
