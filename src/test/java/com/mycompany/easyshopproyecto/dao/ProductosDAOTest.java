package com.mycompany.easyshopproyecto.dao;

import com.mycompany.easyshopproyecto.logica.Productos;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductosDAOTest {

    private Connection connection;
    private ProductosDAO productosDAO;

    @BeforeAll
    void setUp() {
        connection = ConexionDB.getConnection();
        assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");
        productosDAO = new ProductosDAO();
    }

    @Test
    @DisplayName("Test crearProducto")
    void testCrearProducto() {
        Productos producto = new Productos(22, "1", "ProductoPrueba", 100, 8000, "Descripcion del producto", "1");
        boolean result = productosDAO.crearProducto(producto);
        assertTrue(result, "El producto debería ser creado correctamente.");
    }

    @Test
    @DisplayName("Test listarProductos")
    void testListarProductos() {
        List<Productos> productos = productosDAO.listarProductos();
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto en la lista.");
    }

    @Test
    @DisplayName("Test buscarProductos")
    void testBuscarProductos() {
        String search = "quesito"; //Se debe ajustar segun a la base de datos
        List<Productos> productos = productosDAO.buscarProductos(search);
        assertNotNull(productos, "La lista de productos no debe ser nula.");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto que coincida con la búsqueda.");
    }

    @Test
    @DisplayName("Test actualizarProducto")
    void testActualizarProducto() {
        Productos producto = new Productos(22, "1", "ProductoPrueba", 100, 9000, "Descripcion del producto", "1");
        boolean result = productosDAO.actualizarProducto(producto);
        assertTrue(result, "El producto debería ser actualizado correctamente.");
    }

    @Test
    @DisplayName("Test eliminarProducto")
    void testEliminarProducto() {
        int idProducto = 22; //Se debe ajustar segun a la base de datos
        boolean result = productosDAO.eliminarProducto(idProducto);
        assertTrue(result, "El producto debería ser eliminado correctamente.");
    }

    @Test
    @DisplayName("Test obtenerProductoPorId")
    void testObtenerProductoPorId() {
        int idProducto = 1; //Se debe ajustar segun a la base de datos
        Productos producto = productosDAO.obtenerProductoPorId(idProducto);
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
